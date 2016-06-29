package com.koushik.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.koushik.entity.Reservation;
import com.koushik.entity.Seat;
import com.koushik.entity.SeatHold;
import com.koushik.repository.SeatRepository;
import com.koushik.repository.TicketRepository;
import com.koushik.util.SeatStatus;
import com.koushik.util.Validator;
import java.util.Collections;

@Service
@Transactional
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private SeatService seatService;

    @Autowired
    private SeatRepository seatRepository;

    @Override
    public int getAvailableSeats(Set<Integer> venueLevels) {

        return ticketRepository.getAvailableSeats(venueLevels);
    }

    @Override
    public SeatHold findAndHoldSeats(int noOfSeats, Set<Integer> minLevels, Set<Integer> maxLevels,
            String customerEmail) {
        SeatHold seatHold = new SeatHold();
        seatHold.setCustomerEmail(customerEmail);
        seatHold.setHoldDate(new Date());
        List<Seat> seats;
        int availableInMinLevel = getAvailableSeats(minLevels);
        int availableInMaxLevel = getAvailableSeats(maxLevels);
        if (availableInMaxLevel >= noOfSeats) {
            seats = ticketRepository.holdSeats(maxLevels, noOfSeats);
        } else if (availableInMinLevel >= noOfSeats) {
            seats = ticketRepository.holdSeats(minLevels, noOfSeats);
        } else if ((availableInMinLevel + availableInMaxLevel) >= noOfSeats) {
            maxLevels.addAll(minLevels);
            seats = ticketRepository.holdSeats(maxLevels, noOfSeats);
        } else {
            seats = Collections.emptyList();
        }
        if (Validator.isCollectionNotEmpty(seats)) {
            seatService.changeSeatStatus(seats, SeatStatus.hold());
            seatService.updateSeats(seats, seatHold, false);
            seatHold.setSeats(seats);
            seatHold = seatRepository.saveSeatHold(seatHold);
            return seatHold;
        } else {
            return null;
        }
    }

    @Override
    //public Reservation reserveSeats(SeatHold seatHold) {
    public String reserveSeats(int seatHoldId, String customerEmail) {
        SeatHold seatHoldObj = seatRepository.findSeatHoldByIdNEmail(seatHoldId, customerEmail);
        if (seatHoldObj != null) {
            Reservation reservation = new Reservation();
            reservation.setCustomerEmail(customerEmail);
            reservation.setReservationDate(new Date());
            reservation.setReservationToken(System.nanoTime());
            reservation = ticketRepository.partialSaveReservation(reservation);
            List<Seat> seats = seatHoldObj.getSeats();
            seatService.changeSeatStatus(seats, SeatStatus.reserved());
            seatService.updateSeats(seats, reservation, false);
            reservation.setReservedSeats(seats);
            seatRepository.removeSeatHold(seatHoldObj);
            return ticketRepository.reserveSeats(reservation).getReservationToken() + "";
        }
        return null;
    }
}

package com.koushik.service;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.koushik.entity.Reservation;
import com.koushik.entity.Seat;
import com.koushik.entity.SeatHold;
import com.koushik.repository.SeatRepository;
import com.koushik.util.SeatStatus;
import com.koushik.util.Validator;

@Service
@Transactional
public class SeatServiceImpl implements SeatService {

    @Autowired
    private SeatRepository seatRepository;

    @Override
    public SeatHold getSeatHold(long seatHoldId) {

        return seatRepository.findSeatHoldById(seatHoldId);
    }

    @Override
    public void changeSeatStatus(List<Seat> seats, int status) {
        if (Validator.isCollectionNotEmpty(seats)) {
            for (Seat seat : seats) {
                seat.setStatus(status);
            }
        }
    }

    @Override
    public void removeSeatHold(SeatHold seatHold) {
        seatRepository.removeSeatHold(seatHold);
    }

    @Override
    public void updateSeats(List<Seat> seats, SeatHold seatHold, boolean remove) {
        if (Validator.isCollectionNotEmpty(seats)) {
            for (Seat seat : seats) {
                if (remove) {
                    seat.setSeatHold(null);
                } else {
                    seat.setSeatHold(seatHold);
                }
                seat.setReservation(null);
                seatRepository.saveSeat(seat);
            }
        }
    }

    @Override
    public void updateSeats(List<Seat> seats, Reservation reservation, boolean remove) {
        if (Validator.isCollectionNotEmpty(seats) && reservation != null) {
            seats.forEach(seat -> {
                if (remove) {
                    seat.setReservation(null);
                } else {
                    seat.setReservation(reservation);
                }
                seat.setSeatHold(null);
                seatRepository.saveSeat(seat);
            });
        }
    }

    @Override
    public void removeExpiredSeatHolds() {
        Date currentDate = DateTime.now().toDate();
        List<SeatHold> holdedSeats = seatRepository.getCreatedSeatHoldObjects(currentDate);
        if (Validator.isCollectionNotEmpty(holdedSeats)) {
            for (SeatHold seatHold : holdedSeats) {
                int minutes = Minutes.minutesBetween(new DateTime(seatHold.getHoldDate()), new DateTime(currentDate)).getMinutes();
                if (minutes >= 1) {
                    List<Seat> seats = seatHold.getSeats();
                    changeSeatStatus(seats, SeatStatus.available());
                    updateSeats(seats, seatHold, true);
                    seatRepository.removeSeatHold(seatHold);
                }
            }
        }
    }

}

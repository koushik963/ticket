/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.koushik.service;

import com.koushik.TestConfig;
import com.koushik.entity.Reservation;
import com.koushik.entity.Seat;
import com.koushik.entity.SeatHold;
import com.koushik.repository.SeatRepository;
import com.koushik.repository.TicketRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class, classes = {TestConfig.class})
@WebAppConfiguration
public class TicketServiceTest {

    @InjectMocks
    TicketServiceImpl ticketService = new TicketServiceImpl();
    @Mock
    TicketRepository ticketRepository;
    @Mock
    SeatRepository seatRepository;
    @Mock
    SeatService seatService;

    Set<Integer> minLevels = new HashSet<Integer>();
    Set<Integer> maxLevels = new HashSet<Integer>();
    String customerEmail;

    @Before
    public void setUp() {
        minLevels.add(1);
        minLevels.add(2);
        maxLevels.add(3);
        maxLevels.add(4);
        customerEmail = "customerEmail";
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findAndHoldSeatsTestCase1() {
        Integer noOfSeats = 30;
        Integer availableMinSeats = 10;
        Integer availableMaxSeats = 8;
        List<Seat> seats = new ArrayList<Seat>();
        Seat s1 = Mockito.mock(Seat.class);
        seats.add(s1);
        Mockito.when(ticketRepository.getAvailableSeats(minLevels)).thenReturn(availableMinSeats);
        Mockito.when(ticketRepository.getAvailableSeats(maxLevels)).thenReturn(availableMaxSeats);
        SeatHold response = ticketService.findAndHoldSeats(noOfSeats, minLevels, maxLevels, customerEmail);
        assertNull(response);
    }

    @Test
    public void findAndHoldSeatsTestCase2() {
        Integer noOfSeats = 20;
        Integer availableMinSeats = 10;
        Integer availableMaxSeats = 12;
        List<Seat> seats = new ArrayList<Seat>();
        Seat s1 = Mockito.mock(Seat.class);
        seats.add(s1);
        SeatHold seatHold = new SeatHold();
        seatHold.setCustomerEmail(customerEmail);
        seatHold.setId(0);
        seatHold.setSeats(seats);
        Mockito.when(ticketRepository.getAvailableSeats(minLevels)).thenReturn(availableMinSeats);
        Mockito.when(ticketRepository.getAvailableSeats(maxLevels)).thenReturn(availableMaxSeats);
        Mockito.when(ticketRepository.holdSeats(maxLevels, noOfSeats)).thenReturn(seats);
        Mockito.when(seatRepository.saveSeatHold(Mockito.any(SeatHold.class))).thenReturn(seatHold);
        Mockito.doNothing().when(seatService).changeSeatStatus(seats, 1);
        Mockito.doNothing().when(seatService).updateSeats(seats, seatHold, false);
        seatService.updateSeats(seats, seatHold, false);
        SeatHold response = ticketService.findAndHoldSeats(noOfSeats, minLevels, maxLevels, customerEmail);
        assertEquals(customerEmail, response.getCustomerEmail());
        assertEquals(1, response.getSeats().size());
    }

    @Test
    public void findAndHoldSeatsTestCase3() {
        Integer noOfSeats = 8;
        Integer availableMinSeats = 10;
        Integer availableMaxSeats = 12;
        List<Seat> seats = new ArrayList<Seat>();
        Seat s1 = Mockito.mock(Seat.class);
        Seat s2 = Mockito.mock(Seat.class);
        seats.add(s1);
        seats.add(s2);
        SeatHold seatHold = new SeatHold();
        seatHold.setCustomerEmail(customerEmail);
        seatHold.setId(0);
        seatHold.setSeats(seats);
        Mockito.when(ticketRepository.getAvailableSeats(minLevels)).thenReturn(availableMinSeats);
        Mockito.when(ticketRepository.getAvailableSeats(maxLevels)).thenReturn(availableMaxSeats);
        Mockito.when(ticketRepository.holdSeats(maxLevels, noOfSeats)).thenReturn(seats);
        Mockito.when(seatRepository.saveSeatHold(Mockito.any(SeatHold.class))).thenReturn(seatHold);
        Mockito.doNothing().when(seatService).changeSeatStatus(seats, 1);
        Mockito.doNothing().when(seatService).updateSeats(seats, seatHold, false);
        seatService.updateSeats(seats, seatHold, false);
        SeatHold response = ticketService.findAndHoldSeats(noOfSeats, minLevels, maxLevels, customerEmail);
        assertEquals(customerEmail, response.getCustomerEmail());
        assertEquals(2, response.getSeats().size());
    }

    @Test
    public void findAndHoldSeatsTestCase4() {
        Integer noOfSeats = 8;
        Integer availableMinSeats = 10;
        Integer availableMaxSeats = 6;
        List<Seat> seats = new ArrayList<Seat>();
        Seat s1 = Mockito.mock(Seat.class);
        Seat s2 = Mockito.mock(Seat.class);
        Seat s3 = Mockito.mock(Seat.class);
        seats.add(s1);
        seats.add(s2);
        seats.add(s3);
        SeatHold seatHold = new SeatHold();
        seatHold.setCustomerEmail(customerEmail);
        seatHold.setId(0);
        seatHold.setSeats(seats);
        Mockito.when(ticketRepository.getAvailableSeats(minLevels)).thenReturn(availableMinSeats);
        Mockito.when(ticketRepository.getAvailableSeats(maxLevels)).thenReturn(availableMaxSeats);
        Mockito.when(ticketRepository.holdSeats(minLevels, noOfSeats)).thenReturn(seats);
        Mockito.when(seatRepository.saveSeatHold(Mockito.any(SeatHold.class))).thenReturn(seatHold);
        Mockito.doNothing().when(seatService).changeSeatStatus(seats, 1);
        Mockito.doNothing().when(seatService).updateSeats(seats, seatHold, false);
        seatService.updateSeats(seats, seatHold, false);
        SeatHold response = ticketService.findAndHoldSeats(noOfSeats, minLevels, maxLevels, customerEmail);
        assertEquals(customerEmail, response.getCustomerEmail());
        assertEquals(3, response.getSeats().size());
    }

    @Test
    public void reserveSeatsTestCase1() {
        int seatHoldId = 10;
        Mockito.when(seatRepository.findSeatHoldByIdNEmail(seatHoldId, customerEmail)).thenReturn(null);
        String result = ticketService.reserveSeats(seatHoldId, customerEmail);
        assertNull(result);
    }

    @Test
    public void reserveSeatsTestCase2() {
        int seatHoldId = 10;
        List<Seat> seats = new ArrayList<Seat>();
        Seat s1 = Mockito.mock(Seat.class);
        seats.add(s1);
        SeatHold seatHoldObj = new SeatHold();
        seatHoldObj.setCustomerEmail(customerEmail);
        seatHoldObj.setId(seatHoldId);
        seatHoldObj.setSeats(seats);
        Reservation r1 = new Reservation();
        r1.setReservationToken(100);
        r1.setCustomerEmail(customerEmail);
        Mockito.when(ticketRepository.partialSaveReservation(Mockito.any(Reservation.class))).thenReturn(r1);
        Mockito.when(seatRepository.findSeatHoldByIdNEmail(seatHoldId, customerEmail)).thenReturn(seatHoldObj);
        Mockito.when(ticketRepository.reserveSeats(r1)).thenReturn(r1);
        Mockito.doNothing().when(seatService).changeSeatStatus(seats, 1);
        Mockito.doNothing().when(seatService).updateSeats(seats, seatHoldObj, false);
        Mockito.doNothing().when(seatRepository).removeSeatHold(seatHoldId);
        String result = ticketService.reserveSeats(seatHoldId, customerEmail);
        assertEquals(result, "100");
    }

    @Test
    public void reserveSeatsTestCase3() {
        int seatHoldId = 10;
        List<Seat> seats = new ArrayList<Seat>();
        Seat s1 = Mockito.mock(Seat.class);
        seats.add(s1);
        SeatHold seatHoldObj = new SeatHold();
        seatHoldObj.setCustomerEmail(customerEmail);
        seatHoldObj.setId(seatHoldId);
        seatHoldObj.setSeats(seats);
        Reservation r1 = new Reservation();
        r1.setReservationToken(100);
        r1.setCustomerEmail(customerEmail);
        Mockito.when(ticketRepository.partialSaveReservation(Mockito.any(Reservation.class))).thenReturn(r1);
        Mockito.when(seatRepository.findSeatHoldByIdNEmail(seatHoldId, null)).thenReturn(seatHoldObj);
        Mockito.when(ticketRepository.reserveSeats(r1)).thenReturn(r1);
        Mockito.doNothing().when(seatService).changeSeatStatus(seats, 1);
        Mockito.doNothing().when(seatService).updateSeats(seats, seatHoldObj, false);
        Mockito.doNothing().when(seatRepository).removeSeatHold(seatHoldId);
        String result = ticketService.reserveSeats(seatHoldId, null);
        assertEquals(result, "100");
    }
}

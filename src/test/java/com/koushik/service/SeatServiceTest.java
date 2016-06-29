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
import com.koushik.util.SeatStatus;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.Minutes;
import static org.junit.Assert.assertEquals;
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
public class SeatServiceTest {

    @Mock
    SeatRepository seatRepository;

    @InjectMocks
    SeatService seatService = new SeatServiceImpl();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testChangeSeatStatus() {
        Seat expectedSeat = new Seat();
        expectedSeat.setId(123456);
        expectedSeat.setSeatNo(123);
        expectedSeat.setStatus(0);
        seatService.changeSeatStatus(Arrays.asList(expectedSeat), 1);
        assertEquals(expectedSeat.getStatus(), 1);
    }

    @Test
    public void testUpdateSeatsSeatHold() {
        Seat s1 = new Seat();
        s1.setId(123456);
        s1.setSeatNo(123);
        s1.setStatus(0);

        Seat s2 = new Seat();
        s2.setId(123456);
        s2.setSeatNo(123);
        s2.setStatus(0);

        SeatHold sh = new SeatHold();
        sh.setCustomerEmail("test@mock.com");
        sh.setHoldDate(new Date());
        sh.setId(123);

        Reservation r1 = new Reservation();
        r1.setCustomerEmail("test2@mock.com");
        r1.setId(111);
        r1.setReservationDate(new Date());
        r1.setReservationToken(789456123);
        Mockito.when(seatRepository.saveSeat(s1)).thenReturn(s1);
        Mockito.when(seatRepository.saveSeat(s2)).thenReturn(s2);
        seatService.updateSeats(Arrays.asList(s1, s2), sh, true);
        Arrays.asList(s1, s2).forEach(actualSeat -> {
            assertEquals(actualSeat.getSeatHold(), null);
            assertEquals(actualSeat.getReservation(), null);
        });
        seatService.updateSeats(Arrays.asList(s1, s2), sh, false);
        Arrays.asList(s1, s2).forEach(actualSeat -> {
            assertEquals(actualSeat.getSeatHold(), sh);
            assertEquals(actualSeat.getReservation(), null);
        });

        seatService.updateSeats(Arrays.asList(s1, s2), r1, true);
        Arrays.asList(s1, s2).forEach(actualSeat -> {
            assertEquals(actualSeat.getSeatHold(), null);
            assertEquals(actualSeat.getReservation(), null);
        });
        seatService.updateSeats(Arrays.asList(s1, s2), r1, false);
        Arrays.asList(s1, s2).forEach(actualSeat -> {
            assertEquals(actualSeat.getSeatHold(), null);
            assertEquals(actualSeat.getReservation(), r1);
        });
    }

   // @Test
    public void testRemoveExpiredSeatHolds() throws ParseException {
        Date d1 = new Date();//new SimpleDateFormat().parse("06/28/2016 22:09:58");
        Date d2 = new Date();//new SimpleDateFormat().parse("06/28/2016 22:10:58");
        Date d3 = new Date();//new SimpleDateFormat().parse("06/28/2016 08:30:58");

        SeatHold sh1 = new SeatHold();
        sh1.setCustomerEmail("test@mock.com");
        sh1.setHoldDate(d1);
        sh1.setId(123);

        SeatHold sh2 = new SeatHold();
        sh1.setCustomerEmail("test@mock.com");
        sh1.setHoldDate(d2);
        sh1.setId(123);

        Seat s1 = new Seat();
        s1.setId(123456);
        s1.setSeatNo(123);
        s1.setStatus(SeatStatus.hold());

        Seat s2 = new Seat();
        s2.setId(123456);
        s2.setSeatNo(123);
        s2.setStatus(SeatStatus.hold());

        sh1.setSeats(Arrays.asList(s1));
        sh2.setSeats(Arrays.asList(s2));
        Mockito.when(seatRepository.getCreatedSeatHoldObjects(d3)).thenReturn(Arrays.asList(sh1, sh2));
        Mockito.when(seatRepository.saveSeat(s1)).thenReturn(null);
        Mockito.when(seatRepository.saveSeat(s2)).thenReturn(null);
        Mockito.when(Minutes.minutesBetween(new DateTime(sh1.getHoldDate()), new DateTime(d1)).getMinutes()).thenReturn(2);
        Mockito.when(Minutes.minutesBetween(new DateTime(sh2.getHoldDate()), new DateTime(d2)).getMinutes()).thenReturn(2);
        seatService.removeExpiredSeatHolds();
        Arrays.asList(sh1,sh2).forEach(seatHold -> {
            seatHold.getSeats().forEach(seat -> {
                assertEquals(seat.getStatus(), SeatStatus.available());
                assertEquals(seat.getSeatHold(), null);
            });
        });
        Mockito.verify(seatRepository, Mockito.atLeastOnce()).removeSeatHold(sh1);
        Mockito.verify(seatRepository, Mockito.atLeastOnce()).removeSeatHold(sh2);
    }
}

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

}

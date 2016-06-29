/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.koushik.controller;

import com.koushik.TestConfig;
import com.koushik.entity.SeatHold;
import com.koushik.entity.SeatHoldContext;
import com.koushik.service.TicketServiceImpl;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
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
public class ServiceControllerTest {

    @InjectMocks
    ServiceController serviceController = new ServiceController();
    @Mock
    TicketServiceImpl ticketService = new TicketServiceImpl();
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
        System.out.println("******************");
    }

    @Test
    public void findAndHoldSeatsTestCase1() throws InterruptedException, ExecutionException {
        int noOfSeats = 10;
        SeatHoldContext seatHoldContext = new SeatHoldContext();
        seatHoldContext.setCustomerEmail(customerEmail);
        seatHoldContext.setMaxLevels(maxLevels);
        seatHoldContext.setMinLevels(minLevels);
        seatHoldContext.setNoOfSeats(noOfSeats);
        SeatHold seatHold = new SeatHold();
        seatHold.setCustomerEmail(customerEmail);
        seatHold.setId(0);
        Mockito.when(ticketService.findAndHoldSeats(noOfSeats, minLevels, maxLevels, customerEmail)).thenReturn(seatHold);
        Future<SeatHold> result = serviceController.findAndHoldSeats(seatHoldContext);
        assertEquals(customerEmail, result.get().getCustomerEmail());
    }

    @Test
    public void findAndHoldSeatsTestCase2() {
        Future<SeatHold> result = serviceController.findAndHoldSeats(null);
        assertNull(result);
    }

    @Test
    public void findAndHoldSeatsTestCase3() {
        int noOfSeats = -1;
        SeatHoldContext seatHoldContext = new SeatHoldContext();
        seatHoldContext.setCustomerEmail(customerEmail);
        seatHoldContext.setMaxLevels(maxLevels);
        seatHoldContext.setMinLevels(minLevels);
        seatHoldContext.setNoOfSeats(noOfSeats);
        Future<SeatHold> result = serviceController.findAndHoldSeats(seatHoldContext);
        assertNull(result);
    }

    @Test
    public void findAndHoldSeatsTestCase4() {
        int noOfSeats = 10;
        SeatHoldContext seatHoldContext = new SeatHoldContext();
        seatHoldContext.setCustomerEmail(customerEmail);
        seatHoldContext.setMaxLevels(new HashSet<Integer>());
        seatHoldContext.setMinLevels(new HashSet<Integer>());
        seatHoldContext.setNoOfSeats(noOfSeats);
        Future<SeatHold> result = serviceController.findAndHoldSeats(seatHoldContext);
        assertNull(result);
    }

    @Test
    public void findAndHoldSeatsTestCase5() {
        int noOfSeats = 10;
        SeatHoldContext seatHoldContext = new SeatHoldContext();
        seatHoldContext.setCustomerEmail(null);
        seatHoldContext.setMaxLevels(maxLevels);
        seatHoldContext.setMinLevels(minLevels);
        seatHoldContext.setNoOfSeats(noOfSeats);
        Future<SeatHold> result = serviceController.findAndHoldSeats(seatHoldContext);
        assertNull(result);
    }

    @Test
    public void reserveSeatsTestCase1() throws InterruptedException, ExecutionException {
        String reserved = "reserved";
        SeatHold seatHold = new SeatHold();
        seatHold.setCustomerEmail(customerEmail);
        seatHold.setId(100);
        Mockito.when(ticketService.reserveSeats((int) seatHold.getId(), customerEmail)).thenReturn(reserved);
        Future<String> result = serviceController.reserveSeats(seatHold);
        assertEquals(reserved, result.get());
    }

    @Test
    public void reserveSeatsTestCase2() {
        String reserved = "reserved";
        SeatHold seatHold = new SeatHold();
        seatHold.setCustomerEmail(customerEmail);
        seatHold.setId(-100);
        Mockito.when(ticketService.reserveSeats((int) seatHold.getId(), customerEmail)).thenReturn(reserved);
        Future<String> result = serviceController.reserveSeats(seatHold);
        assertNull(result);
    }

    @Test
    public void reserveSeatsTestCase3() {
        String reserved = "reserved";
        SeatHold seatHold = new SeatHold();
        seatHold.setCustomerEmail(null);
        seatHold.setId(100);
        Mockito.when(ticketService.reserveSeats((int) seatHold.getId(), customerEmail)).thenReturn(reserved);
        Future<String> result = serviceController.reserveSeats(seatHold);
        assertNull(result);
    }

}

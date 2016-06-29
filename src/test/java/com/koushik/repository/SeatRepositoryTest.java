/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.koushik.repository;

import com.koushik.TestConfig;
import com.koushik.entity.Seat;
import com.koushik.entity.SeatHold;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
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
public class SeatRepositoryTest {

    @Mock
    EntityManager em;

    @InjectMocks
    SeatRepository seatRepo = new SeatRepositoryImpl();

    SeatHold expectedSeatHold = new SeatHold();
    Seat expectedSeat = new Seat();

    @Mock
    TypedQuery query;

    @Mock
    TypedQuery<SeatHold> querySeatHold;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        expectedSeatHold.setCustomerEmail("test@mock.com");
        expectedSeatHold.setId(123456);
        expectedSeatHold.setHoldDate(new Date());
        expectedSeatHold.setSeats(Arrays.asList(expectedSeat));
    }

    @Test
    public void testSaveSeatHold() {
        SeatHold actual = seatRepo.saveSeatHold(expectedSeatHold);
        Mockito.verify(em).persist(expectedSeatHold);
        assertEquals(actual, expectedSeatHold);
    }

    @Test
    public void testFindSeatHoldById() {
        Mockito.when(em.find(SeatHold.class, expectedSeatHold.getId())).thenReturn(expectedSeatHold);
        SeatHold actualSeatHold = seatRepo.findSeatHoldById(expectedSeatHold.getId());
        assertEquals(actualSeatHold, expectedSeatHold);
    }

    @Test
    public void testFindSeatHoldByIdNEmail() {
        Mockito.when(em.createNamedQuery("SeatHold.findByIdNEmail", SeatHold.class)).thenReturn(query);
        Mockito.when(query.getSingleResult()).thenReturn(expectedSeatHold);
        SeatHold actualSeatHold = seatRepo.findSeatHoldByIdNEmail(expectedSeatHold.getId(), expectedSeatHold.getCustomerEmail());
        assertEquals(expectedSeatHold, actualSeatHold);
        Mockito.verify(query, Mockito.atLeastOnce()).getSingleResult();
    }

    @Test
    public void testRemoveSeatHold() {
        seatRepo.removeSeatHold(expectedSeatHold);
        Mockito.verify(em).remove(expectedSeatHold);
    }

    @Test
    public void testSaveSeat() {
        Seat actualSeat = seatRepo.saveSeat(expectedSeat);
        Mockito.verify(em).persist(expectedSeat);
        assertEquals(expectedSeat, actualSeat);
    }

    @Test
    public void testGetCreatedSeatHoldObjects() {
        Mockito.when(em.createQuery("SELECT sh FROM SeatHold sh WHERE sh.holdDate < :holdDate", SeatHold.class)).thenReturn(querySeatHold);
        Mockito.when(querySeatHold.getResultList()).thenReturn(Arrays.asList(expectedSeatHold));
        List<SeatHold> actualSeatHold = seatRepo.getCreatedSeatHoldObjects(new Date());
        assertEquals(Arrays.asList(expectedSeatHold), actualSeatHold);
        Mockito.verify(querySeatHold, Mockito.atLeastOnce()).getResultList();
    }
}

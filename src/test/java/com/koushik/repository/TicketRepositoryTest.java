/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.koushik.repository;

import com.koushik.TestConfig;
import com.koushik.entity.Reservation;
import com.koushik.entity.Seat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
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
public class TicketRepositoryTest {

    @Mock
    private EntityManager em;

    @InjectMocks
    TicketRepository ticketRepository = new TicketRepositoryImpl();

    @Mock
    TypedQuery query, query2;

    @Mock
    TypedQuery<Seat> query3;

    List<Integer> levels = new ArrayList<>();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        levels.add(1);
        levels.add(3);
        levels.add(2);
        levels.add(4);
    }

    @Test
    public void testGetAvailableSeatsNull() {
        // levels.add(1);
        Set<Integer> venueLevel = null;
        int expected = 4;
        Mockito.when(em.createQuery("SELECT DISTINCT id FROM Level")).thenReturn(query);
        Mockito.when(query.getResultList()).thenReturn(levels);
        Mockito.when(em.createNamedQuery("Seat.countByStatusAndLevelId")).thenReturn(query2);
        Mockito.when(query2.getSingleResult()).thenReturn(expected);
        int actual = ticketRepository.getAvailableSeats(venueLevel);
        assertEquals(actual, expected);
        Mockito.verify(query, Mockito.times(1)).getResultList();
        Mockito.verify(query2, Mockito.times(1)).getSingleResult();
    }

    @Test
    public void testGetAvailableSeatsNotNull() {
        int expected = 2;
        Set<Integer> venueLevel = new HashSet<>();
        venueLevel.add(1);
        venueLevel.add(2);
        Mockito.when(em.createQuery("SELECT DISTINCT id FROM Level")).thenReturn(query);
        Mockito.when(query.getResultList()).thenReturn(levels);
        Mockito.when(em.createNamedQuery("Seat.countByStatusAndLevelId")).thenReturn(query2);
        Mockito.when(query2.getSingleResult()).thenReturn(expected);
        int actual = ticketRepository.getAvailableSeats(venueLevel);
        assertEquals(actual, expected);
        Mockito.verify(query, Mockito.times(0)).getResultList();
        Mockito.verify(query2, Mockito.times(1)).getSingleResult();
    }

    @Test
    public void testHoldSeats() {
        Set<Integer> levelIds = new HashSet<>();
        levelIds.add(1);
        levelIds.add(2);
        List<Seat> expected = new ArrayList<>();
        expected.add(new Seat());
        expected.add(new Seat());
        expected.add(new Seat());
        Mockito.when(em.createNamedQuery("Seat.findByStatusAndLevelId", Seat.class)).thenReturn(query3);
        Mockito.when(query3.getResultList()).thenReturn(expected);
        List<Seat> actual = ticketRepository.holdSeats(levelIds, 3);
        Mockito.verify(query3, Mockito.atLeastOnce()).getResultList();
        assertEquals(actual, expected);
        assertEquals(actual.size(), 3);
    }

    @Test
    public void testReserveSeats() {
        Reservation expected = new Reservation();
        Reservation actual = ticketRepository.reserveSeats(expected);
        Mockito.verify(em).merge(expected);
        assertEquals(expected, actual);
    }

    @Test
    public void testPartialSaveReservation() {
        Reservation expected = new Reservation();
        Reservation actual = ticketRepository.partialSaveReservation(expected);
        Mockito.verify(em).persist(expected);
        assertEquals(expected, actual);
    }
}

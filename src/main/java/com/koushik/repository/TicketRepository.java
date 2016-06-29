package com.koushik.repository;

import java.util.List;
import java.util.Set;

import com.koushik.entity.Reservation;
import com.koushik.entity.Seat;

public interface TicketRepository {

    /**
     * Returns Available Seats
     *
     * @param venueLevel
     * @return
     */
    int getAvailableSeats(Set<Integer> venueLevel);

    /**
     * Holds seats on the base of levels defined
     *
     * @param noOfSeats
     * @param levelIds
     * @return
     */
    public List<Seat> holdSeats(Set<Integer> levelIds, int noOfSeats);

    /**
     * Reserves selected seats
     *
     * @param reservation
     * @return
     */
    public Reservation reserveSeats(Reservation reservation);

    public Reservation partialSaveReservation(Reservation reservation);

}

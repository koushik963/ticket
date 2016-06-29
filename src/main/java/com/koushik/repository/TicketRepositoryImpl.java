package com.koushik.repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.koushik.entity.Reservation;
import com.koushik.entity.Seat;
import com.koushik.util.SeatStatus;
import com.koushik.util.Validator;

@Repository
public class TicketRepositoryImpl implements TicketRepository {

    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    @Override
    public int getAvailableSeats(Set<Integer> venueLevels) {

        Collection<Integer> levels = venueLevels;
        if (Validator.isCollectionEmpty(venueLevels)) {
            levels = em.createQuery("SELECT DISTINCT id FROM Level").getResultList();
        }

        Query query = em.createNamedQuery("Seat.countByStatusAndLevelId");
        query.setParameter("status", SeatStatus.available());
        query.setParameter("levelIds", levels);
        return ((Number) query.getSingleResult()).intValue();
    }

    @Override
    public List<Seat> holdSeats(Set<Integer> levelIds, int noOfSeats) {
        TypedQuery<Seat> query = em.createNamedQuery("Seat.findByStatusAndLevelId", Seat.class);
        query.setParameter("status", SeatStatus.available());
        query.setParameter("levelIds", levelIds);
        query.setMaxResults(noOfSeats);
        return query.getResultList();
    }

    @Override
    public Reservation reserveSeats(Reservation reservation) {
        em.merge(reservation);
        return reservation;
    }

    @Override
    public Reservation partialSaveReservation(Reservation reservation) {
        em.persist(reservation);
        return reservation;
    }

}

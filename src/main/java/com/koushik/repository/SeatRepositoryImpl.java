package com.koushik.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.koushik.entity.Seat;
import com.koushik.entity.SeatHold;

@Repository
public class SeatRepositoryImpl implements SeatRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public SeatHold saveSeatHold(SeatHold seatHold) {
		em.persist(seatHold);
		return seatHold;
	}

	@Override
	public SeatHold findSeatHoldById(long id) {
		return em.find(SeatHold.class, id);
	}

	@Override
	public void removeSeatHold(long seatHoldId) {
		removeSeatHold(findSeatHoldById(seatHoldId));
	}

	@Override
	public SeatHold findSeatHoldByIdNEmail(long id, String customerEmail) {
		TypedQuery<SeatHold> query = em.createNamedQuery("SeatHold.findByIdNEmail", SeatHold.class);
		query.setParameter("id", id);
		query.setParameter("customerEmail", customerEmail);
		query.setMaxResults(1);
		return query.getSingleResult();
	}

	@Override
	public void removeSeatHold(SeatHold seatHold) {
		em.remove(seatHold);
	}

	@Override
	public Seat saveSeat(Seat seat) {
		em.persist(seat);
		return seat;
	}

	public List<SeatHold> getCreatedSeatHoldObjects(Date currentDate) {
		TypedQuery<SeatHold> query = em.createQuery("SELECT sh FROM SeatHold sh WHERE sh.holdDate < :holdDate", SeatHold.class);
		query.setParameter("holdDate", currentDate);
		return query.getResultList();
	}
}

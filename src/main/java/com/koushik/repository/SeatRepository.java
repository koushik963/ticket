package com.koushik.repository;

import java.util.Date;
import java.util.List;

import com.koushik.entity.Seat;
import com.koushik.entity.SeatHold;

public interface SeatRepository {

	public SeatHold findSeatHoldById(long id);

	public SeatHold findSeatHoldByIdNEmail(long id, String customerEmail);

	public void removeSeatHold(long seatHoldId);

	public void removeSeatHold(SeatHold seatHold);

	public SeatHold saveSeatHold(SeatHold seatHold);

	public Seat saveSeat(Seat seat);

	public List<SeatHold> getCreatedSeatHoldObjects(Date currentDate);
}

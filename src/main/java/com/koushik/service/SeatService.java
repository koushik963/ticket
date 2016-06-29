package com.koushik.service;

import java.util.List;

import com.koushik.entity.Reservation;
import com.koushik.entity.Seat;
import com.koushik.entity.SeatHold;

public interface SeatService {

	public SeatHold getSeatHold(long seatHoldId);

	public void changeSeatStatus(List<Seat> seats, int status);

	public void removeSeatHold(SeatHold seatHold);

	public void updateSeats(List<Seat> seats, SeatHold seatHold, boolean remove);

	public void updateSeats(List<Seat> seats, Reservation reservation, boolean remove);
	
	public void removeExpiredSeatHolds();
}

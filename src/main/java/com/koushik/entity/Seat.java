package com.koushik.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table
@NamedQueries({
		@NamedQuery(name = "Seat.countByStatusAndLevelId", query = "SELECT COUNT(s) AS availableSeats FROM Seat s INNER JOIN LevelRow lr ON lr.id = s.row INNER JOIN Level l ON l.id = lr.level WHERE s.status = :status AND l.id IN (:levelIds)"),
		@NamedQuery(name = "Seat.findByStatusAndLevelId", query = "SELECT s AS availableSeats FROM Seat s INNER JOIN LevelRow lr ON lr.id = s.row INNER JOIN Level l ON l.id = lr.level WHERE s.status = :status AND l.id IN (:levelIds)") })
public class Seat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "seatId")
	private long id;

	private int seatNo;

	private int status;

	@ManyToOne
	@JoinColumn(name = "seatHoldId")
	@JsonIgnore
	private SeatHold seatHold;

	@ManyToOne
	@JoinColumn(name = "reservationId")
	@JsonIgnore
	private Reservation reservation;

	@ManyToOne
	@JoinColumn(name = "rowId")
	@JsonIgnore
	private LevelRow row;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public SeatHold getSeatHold() {
		return seatHold;
	}

	public void setSeatHold(SeatHold seatHold) {
		this.seatHold = seatHold;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public int getSeatNo() {
		return seatNo;
	}

	public void setSeatNo(int seatNo) {
		this.seatNo = seatNo;
	}

	public LevelRow getRow() {
		return row;
	}

	public void setRow(LevelRow row) {
		this.row = row;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}

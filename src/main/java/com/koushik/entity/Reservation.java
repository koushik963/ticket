package com.koushik.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reservationId")
	private long id;

	private String customerEmail;

	@OneToMany(mappedBy = "reservation", fetch = FetchType.LAZY)
	private List<Seat> reservedSeats;

	private Date reservationDate;

	private long reservationToken;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public List<Seat> getReservedSeats() {
		return reservedSeats;
	}

	public void setReservedSeats(List<Seat> reservedSeats) {
		this.reservedSeats = reservedSeats;
	}

	public Date getReservationDate() {
		return reservationDate;
	}

	public void setReservationDate(Date reservationDate) {
		this.reservationDate = reservationDate;
	}

	public long getReservationToken() {
		return reservationToken;
	}

	public void setReservationToken(long reservationToken) {
		this.reservationToken = reservationToken;
	}

	@Override
	public String toString() {
		return "Reservation [id=" + id + ", customerEmail=" + customerEmail + ", reservedSeats=" + reservedSeats
				+ ", reservationDate=" + reservationDate + ", reservationToken=" + reservationToken + "]";
	}

}

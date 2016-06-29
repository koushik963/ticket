package com.koushik.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
@NamedQueries({
		@NamedQuery(name = "SeatHold.findByIdNEmail", query = "SELECT sh FROM SeatHold sh WHERE sh.id = :id AND sh.customerEmail = :customerEmail") })
public class SeatHold {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String customerEmail;

	private Date holdDate;

	@OneToMany(mappedBy = "seatHold", fetch = FetchType.LAZY)
	private List<Seat> seats;

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

	public Date getHoldDate() {
		return holdDate;
	}

	public void setHoldDate(Date holdDate) {
		this.holdDate = holdDate;
	}

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

}

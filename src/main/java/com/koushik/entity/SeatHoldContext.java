package com.koushik.entity;

import java.util.Set;

public class SeatHoldContext {

	private int noOfSeats;

	private Set<Integer> minLevels;

	private Set<Integer> maxLevels;

	private String customerEmail;

	public int getNoOfSeats() {
		return noOfSeats;
	}

	public void setNoOfSeats(int noOfSeats) {
		this.noOfSeats = noOfSeats;
	}

	public Set<Integer> getMinLevels() {
		return minLevels;
	}

	public void setMinLevels(Set<Integer> minLevels) {
		this.minLevels = minLevels;
	}

	public Set<Integer> getMaxLevels() {
		return maxLevels;
	}

	public void setMaxLevels(Set<Integer> maxLevels) {
		this.maxLevels = maxLevels;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

}

package com.koushik.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class LevelRow {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rowId")
	private long id;

	@ManyToOne
	@JoinColumn(name = "levelId")
	@JsonIgnore
	private Level level;

	@OneToMany(mappedBy = "row", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Seat> seats;

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

}

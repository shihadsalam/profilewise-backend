package com.boot.angular.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "USER_CAREER")
public class UserCareer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "MATCHES")
	private BigInteger matches;

	@Column(name = "RUNS")
	private BigInteger runs;
	
	@Column(name = "BATTING_AVG")
	private BigDecimal battingAvg;
	
	@Column(name = "HIGH_SCORE")
	private BigInteger highScore;

	@Column(name = "WICKETS")
	private BigInteger wickets;

	@Column(name = "BOWLING_AVG")
	private BigDecimal bowlingAvg;
	
	@Column(name = "BEST_BOWL")
	private String bestBowling;

	@Column(name = "CATCHES")
	private BigInteger catches;
	
	
	public UserCareer() {
		// hibernate
	}

	public UserCareer(User user, BigInteger matches, BigInteger runs, BigDecimal battingAvg, BigInteger highScore,
			BigInteger wickets, BigDecimal bowlingAvg, String bestBowling, BigInteger catches) {
		super();
		this.user = user;
		this.matches = matches;
		this.runs = runs;
		this.battingAvg = battingAvg;
		this.highScore = highScore;
		this.wickets = wickets;
		this.bowlingAvg = bowlingAvg;
		this.bestBowling = bestBowling;
		this.catches = catches;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public BigInteger getMatches() {
		return matches;
	}

	public void setMatches(BigInteger matches) {
		this.matches = matches;
	}

	public BigInteger getRuns() {
		return runs;
	}

	public void setRuns(BigInteger runs) {
		this.runs = runs;
	}

	public BigDecimal getBattingAvg() {
		return battingAvg;
	}

	public void setBattingAvg(BigDecimal battingAvg) {
		this.battingAvg = battingAvg;
	}

	public BigInteger getHighScore() {
		return highScore;
	}

	public void setHighScore(BigInteger highScore) {
		this.highScore = highScore;
	}

	public BigInteger getWickets() {
		return wickets;
	}

	public void setWickets(BigInteger wickets) {
		this.wickets = wickets;
	}

	public BigDecimal getBowlingAvg() {
		return bowlingAvg;
	}

	public void setBowlingAvg(BigDecimal bowlingAvg) {
		this.bowlingAvg = bowlingAvg;
	}

	public String getBestBowling() {
		return bestBowling;
	}

	public void setBestBowling(String bestBowling) {
		this.bestBowling = bestBowling;
	}

	public BigInteger getCatches() {
		return catches;
	}

	public void setCatches(BigInteger catches) {
		this.catches = catches;
	}

	public UserCareer update(UserCareer userCareer) {
		if (null != userCareer.getMatches()) {
			this.matches = userCareer.getMatches();
		}
		if (null != userCareer.getRuns()) {
			this.runs = userCareer.getRuns();
		}
		if (null != userCareer.getBattingAvg()) {
			this.battingAvg = userCareer.getBattingAvg();
		}
		if (null != userCareer.getHighScore()) {
			this.highScore = userCareer.getHighScore();
		}
		if (null != userCareer.getWickets()) {
			this.wickets = userCareer.getWickets();
		}
		if (null != userCareer.getBowlingAvg()) {
			this.bowlingAvg = userCareer.getBowlingAvg();
		}
		if (!StringUtils.isEmpty(userCareer.getBestBowling())) {
			this.bestBowling = userCareer.getBestBowling();
		}
		if (null != userCareer.getCatches()) {
			this.catches = userCareer.getCatches();
		}
		return this;
	}

}

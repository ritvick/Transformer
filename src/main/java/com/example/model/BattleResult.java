package com.example.model;

import java.util.List;

public class BattleResult {
	
	private int numberOfBattles;
	private String winningTeam;
	private List<String> winners;
	private List<String> survivors;
	
	public int getNumberOfBattles() {
		return numberOfBattles;
	}
	public void setNumberOfBattles(int numberOfBattles) {
		this.numberOfBattles = numberOfBattles;
	}
	public String getWinningTeam() {
		return winningTeam;
	}
	public void setWinningTeam(String winningTeam) {
		this.winningTeam = winningTeam;
	}
	public List<String> getWinners() {
		return winners;
	}
	public void setWinners(List<String> winners) {
		this.winners = winners;
	}
	public List<String> getSurvivors() {
		return survivors;
	}
	public void setSurvivors(List<String> survivors) {
		this.survivors = survivors;
	}
	
	

}

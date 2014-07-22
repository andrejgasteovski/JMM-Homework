package com.example.jmm_homework;

public class TopScorer {
	private String name;
	private String club;
	private int goals;
	
	public TopScorer(String name, String club, int goals) {
		super();
		this.name = name;
		this.club = club;
		this.goals = goals;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClub() {
		return club;
	}

	public void setClub(String club) {
		this.club = club;
	}

	public int getGoals() {
		return goals;
	}

	public void setGoals(int goals) {
		this.goals = goals;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("%s  %s  %d\n", name, club, goals);
	}
}
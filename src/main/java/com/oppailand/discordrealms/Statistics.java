package com.oppailand.discordrealms;

public class Statistics {
	private double totalJourneys;
	private int totalGemsFromJourneys;
	private int totalPlayers;

	public void incTotalJourneys() {
		totalJourneys++;
	}

	public double getAvgJourneysPerPlayer() {
		return totalJourneys / totalPlayers;
	}

	public double getAvgGemsPerJourney() {
		return totalGemsFromJourneys / totalJourneys;
	}

	public double getAvgGemsPerPlayerFromJourneys() {
		return totalGemsFromJourneys / totalPlayers;
	}
}

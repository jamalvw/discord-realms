package com.oppailand.discordrealms.journey;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the basic implementation of farming - Journeys.
 * The entity selects from an available list of
 * randomly generated journeys, each with different benefits.
 * <p>
 * Completing a journey takes a long time as this is meant
 * to be an idle game. After the journey is finished,
 * you can review your rewards, losses, etc.
 */
public abstract class Journey {
	private final String id;
	private final List<JourneyNode> nodes = new ArrayList<>();
	private int level = 0;
	private int exp = 0;

	public Journey(String id) {
		this.id = id;
	}

	public String getID() {
		return id;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public List<JourneyNode> getNodes() {
		return nodes;
	}

	public abstract void setup();
}

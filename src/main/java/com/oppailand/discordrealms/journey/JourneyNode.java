package com.oppailand.discordrealms.journey;

import com.oppailand.discordrealms.entity.Monster;

import java.util.ArrayList;
import java.util.List;

public class JourneyNode {
	private List<Monster> monsters = new ArrayList<>();
	private JourneyLoot loot = new JourneyLoot();

	public List<Monster> getMonsters() {
		return monsters;
	}

	public void setMonsters(List<Monster> monsters) {
		this.monsters = monsters;
	}

	public JourneyLoot getLoot() {
		return loot;
	}

	public void setLoot(JourneyLoot loot) {
		this.loot = loot;
	}
}

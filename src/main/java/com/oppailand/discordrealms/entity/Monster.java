package com.oppailand.discordrealms.entity;

public class Monster extends Mob {
	private Tier tier;

	public Monster(Tier t) {
		tier = t;
	}

	public Tier getTier() {
		return tier;
	}

	public void setTier(Tier t) {
		tier = t;
	}
}

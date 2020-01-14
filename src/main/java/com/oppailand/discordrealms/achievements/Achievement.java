package com.oppailand.discordrealms.achievements;

/**
 * Created by Silvre on 3/9/2017.
 * Project: discordrealms
 * If you are reading this - you can read this
 */
public class Achievement {
	//maybe we should do this when we get other shit done
	//!!!!
	private String name;
	private String description;
	private int expGained;

	public Achievement() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getExpGained() {
		return expGained;
	}

	public void setExpGained(int expGained) {
		this.expGained = expGained;
	}
}

package com.oppailand.discordrealms.journey;

import java.awt.*;

public enum JourneyState {
	SUCCESS("Journey Success", "You have finished your journey.", Color.GREEN),
	FLED("Journey Fled", "You have fled from your journey.", Color.YELLOW),
	FAILED("Journey Failed", "You have died on your journey.", Color.RED);

	private final String title;
	private final String msg;
	private final Color color;

	JourneyState(String title, String msg, Color color) {
		this.title = title;
		this.msg = msg;
		this.color = color;
	}

	public String getTitle() {
		return title;
	}

	public String getMessage() {
		return msg;
	}

	public Color getColor() {
		return color;
	}
}

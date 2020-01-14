package com.oppailand.discordrealms.util;

import java.util.Random;

public class IntPossibility extends Possibility<Integer> {
	private int min = 0;
	private int max = 1;

	public IntPossibility() {

	}

	public IntPossibility(int min, int max) {
		this.min = min;
		this.max = max;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	@Override
	public Integer getRandom() {
		return min == max ? min : min + new Random().nextInt(max - min);
	}
}

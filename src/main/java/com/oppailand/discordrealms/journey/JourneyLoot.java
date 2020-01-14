package com.oppailand.discordrealms.journey;

import com.oppailand.discordrealms.entity.armor.Item;

import java.util.ArrayList;
import java.util.List;

public class JourneyLoot {
	private List<Item> items = new ArrayList<>();
	private int exp = 0;
	private int gems = 0;

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public void addItem(Item item) {
		items.add(item);
	}

	public int getExp() {
		return exp;
	}

	public void addExp(int exp) {
		this.exp += exp;
	}

	public int getGems() {
		return gems;
	}

	public void addGems(int gems) {
		this.gems += gems;
	}
}

package com.oppailand.discordrealms.entity.armor;

import com.oppailand.discordrealms.objects.ItemObject;

public class Item {
	private String name;

	public Item(String name) {
		this.name = name;
	}

	public Item(ItemObject o) {
		this.name = o.name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return getName();
	}

	public String toFullString() {
		return getName();
	}

	public ItemObject toSavedObject() {
		ItemObject o = new ItemObject();
		o.name = name;
		return o;
	}
}

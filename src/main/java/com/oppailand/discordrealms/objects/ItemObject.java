package com.oppailand.discordrealms.objects;

public class ItemObject extends BaseObject {
	public String name;

	public ItemObject() {
		object_type = Type.ITEM;
	}

	public ItemObject(ItemObject o) {
		object_type = Type.ITEM;
		name = o.name;
	}
}

package com.oppailand.discordrealms.objects;

public abstract class BaseObject {
	public Type object_type;

	public enum Type {
		MOB,
		PLAYER,
		ITEM,
		WEAPON,
		ARMOR
	}
}

package com.oppailand.discordrealms.objects;

public class MobObject extends BaseObject {
	public ArmorObject armor;
	public WeaponObject weapon;
	public int level;

	public MobObject() {
		object_type = Type.MOB;
	}

	public MobObject(MobObject o) {
		object_type = Type.MOB;
		armor = o.armor;
		weapon = o.weapon;
		level = o.level;
	}
}

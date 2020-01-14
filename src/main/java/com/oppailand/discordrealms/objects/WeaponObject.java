package com.oppailand.discordrealms.objects;

public class WeaponObject extends ItemObject {
	public int tier_id;
	public int type;
	public int min_dmg;
	public int max_dmg;
	public int fire_dmg;
	public int ice_dmg;
	public int dura;

	public WeaponObject() {
		object_type = Type.WEAPON;
	}

	public WeaponObject(ItemObject o) {
		super(o);
		object_type = Type.WEAPON;
	}

	public WeaponObject(WeaponObject o) {
		super(o);
		object_type = Type.WEAPON;
		tier_id = o.tier_id;
		type = o.type;
		min_dmg = o.min_dmg;
		max_dmg = o.max_dmg;
		fire_dmg = o.fire_dmg;
		ice_dmg = o.ice_dmg;
		dura = o.dura;
	}
}

package com.oppailand.discordrealms.objects;

public class ArmorObject extends ItemObject {
	public int tier_id;
	public int hp;
	public int hp_regen;
	public int energy_regen;
	public int fire_res;
	public int ice_res;
	public int dura;

	public ArmorObject() {
		object_type = Type.ARMOR;
	}

	public ArmorObject(ItemObject o) {
		super(o);
		object_type = Type.ARMOR;
	}

	public ArmorObject(ArmorObject o) {
		super(o);
		object_type = Type.ARMOR;
		tier_id = o.tier_id;
		hp = o.hp;
		hp_regen = o.hp_regen;
		energy_regen = o.energy_regen;
		fire_res = o.fire_res;
		ice_res = o.ice_res;
		dura = o.dura;
	}
}

package com.oppailand.discordrealms.entity.armor;

import com.oppailand.discordrealms.entity.Tier;
import com.oppailand.discordrealms.objects.ArmorObject;
import com.oppailand.discordrealms.util.Helper;

public class Armor extends Item {
	private final Tier tier;
	private int hp = 1;
	private int hpRegen = 0;
	private int energyRegen = 0;
	private int fireRes = 0;
	private int iceRes = 0;
	private int dura = 0;

	public Armor(Tier tier) {
		this(tier.getArmor().getName(), tier);
	}

	public Armor(String name, Tier tier) {
		this(name, tier, tier.getMaxDurability());
	}

	public Armor(String name, Tier tier, int dura) {
		super(name);
		this.tier = tier;
		this.dura = dura;
	}

	public Armor(ArmorObject o) {
		super(o);
		this.tier = Tier.fromId(o.tier_id);
		this.hp = o.hp;
		this.hpRegen = o.hp_regen;
		this.energyRegen = o.energy_regen;
		this.fireRes = o.fire_res;
		this.iceRes = o.ice_res;
		this.dura = o.dura;
	}

	public Tier getTier() {
		return tier;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getHpRegen() {
		return hpRegen;
	}

	public void setHpRegen(int hpRegen) {
		this.hpRegen = hpRegen;
	}

	public int getEnergyRegen() {
		return energyRegen;
	}

	public void setEnergyRegen(int energyRegen) {
		this.energyRegen = energyRegen;
	}

	public int getFireRes() {
		return fireRes;
	}

	public void setFireRes(int fireRes) {
		this.fireRes = fireRes;
	}

	public int getIceRes() {
		return iceRes;
	}

	public void setIceRes(int iceRes) {
		this.iceRes = iceRes;
	}

	public int getDurability() {
		return dura;
	}

	public void setDurability(int d) {
		this.dura = d;
	}

	public void repair() {
		this.dura = tier.getMaxDurability();
	}

	public void addDurability(int d) {
		this.dura += d;
	}

	public void subtractDurability(int d) {
		this.dura -= d;
	}

	@Override
	public String toString() {
		return getName() + " (+" + getHp() + ") (" + Helper.getPercent(dura, tier.getMaxDurability()) + "%)";
	}

	@Override
	public String toFullString() {
		String stats = getName() + " (" + Helper.getPercent(dura, tier.getMaxDurability()) + "%)\nHP: +" + getHp();
		if (hpRegen > 0) stats += "\nHP REGEN: +" + getHpRegen() + "/s";
		if (energyRegen > 0) stats += "\nENERGY REGEN: +" + getEnergyRegen() + "%";
		if (fireRes > 0) stats += "\nFIRE RESISTANCE: +" + getFireRes() + "%";
		if (iceRes > 0) stats += "\nICE RESISTANCE: +" + getIceRes() + "%";
		return stats;
	}

	@Override
	public ArmorObject toSavedObject() {
		ArmorObject o = new ArmorObject(super.toSavedObject());
		o.tier_id = tier.getId();
		o.hp = hp;
		o.hp_regen = hpRegen;
		o.energy_regen = energyRegen;
		o.fire_res = fireRes;
		o.ice_res = iceRes;
		o.dura = dura;
		return o;
	}
}

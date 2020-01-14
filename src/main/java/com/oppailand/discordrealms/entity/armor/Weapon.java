package com.oppailand.discordrealms.entity.armor;

import com.oppailand.discordrealms.entity.Tier;
import com.oppailand.discordrealms.objects.WeaponObject;
import com.oppailand.discordrealms.util.Helper;
import com.oppailand.discordrealms.util.IntPossibility;

public class Weapon extends Item {
	public static final int SWORD = 0;
	public static final int AXE = 1;
	public static final int POLEARM = 2;

	private final Tier tier;
	private final int type;
	private IntPossibility dmg = new IntPossibility();
	private int fireDmg = 0;
	private int iceDmg = 0;
	private int dura = 0;

	public Weapon(Tier tier, int type) {
		this(tier.getWeapon(type).getName(), tier, type);
	}

	public Weapon(String name, Tier tier, int type) {
		this(name, tier, type, tier.getMaxDurability());
	}

	public Weapon(String name, Tier tier, int type, int dura) {
		super(name);
		this.tier = tier;
		this.type = type;
		this.dura = Math.min(dura, tier.getMaxDurability());
	}

	public Weapon(WeaponObject o) {
		super(o);
		this.tier = Tier.fromId(o.tier_id);
		this.type = o.type;
		this.dmg = new IntPossibility(o.min_dmg, o.max_dmg);
		this.fireDmg = o.fire_dmg;
		this.iceDmg = o.ice_dmg;
		this.dura = o.dura;
	}

	public Tier getTier() {
		return tier;
	}

	public int getType() {
		return type;
	}

	public IntPossibility getDmg() {
		return dmg;
	}

	public int getFireDmg() {
		return fireDmg;
	}

	public void setFireDmg(int fireDmg) {
		this.fireDmg = fireDmg;
	}

	public int getIceDmg() {
		return iceDmg;
	}

	public void setIceDmg(int iceDmg) {
		this.iceDmg = iceDmg;
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

	public int getEnergyUsage() {
		return 2 + (tier.getId() * 3);
	}

	@Override
	public String toString() {
		return getName() + " (" + getDmg().getMin() + " - " + getDmg().getMax() + ") (" + Math.round(((float) dura / (float) tier.getMaxDurability()) * 100) + "%)";
	}

	@Override
	public String toFullString() {
		String stats = getName() + " (" + Helper.getPercent(dura, tier.getMaxDurability()) + "%)";
		stats += "\nDMG: " + getDmg().getMin() + " - " + getDmg().getMax();
		if (fireDmg > 0) stats += "\nFIRE DMG: +" + getFireDmg();
		if (iceDmg > 0) stats += "\nICE DMG: +" + getIceDmg();
		return stats;
	}

	@Override
	public WeaponObject toSavedObject() {
		WeaponObject o = new WeaponObject(super.toSavedObject());
		o.tier_id = tier.getId();
		o.type = type;
		o.min_dmg = dmg.getMin();
		o.max_dmg = dmg.getMax();
		o.fire_dmg = fireDmg;
		o.ice_dmg = iceDmg;
		o.dura = dura;
		return o;
	}
}

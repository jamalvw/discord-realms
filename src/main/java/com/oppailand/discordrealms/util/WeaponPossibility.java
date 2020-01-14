package com.oppailand.discordrealms.util;

import com.oppailand.discordrealms.entity.Tier;
import com.oppailand.discordrealms.entity.armor.Weapon;

import java.util.Random;

public class WeaponPossibility extends Possibility<Weapon> {
	private final Tier tier;
	private final int type;
	private String name;
	private IntPossibility minDmg = new IntPossibility();
	private int dmgRange;
	private IntPossibility fireDmg = new IntPossibility();
	private IntPossibility iceDmg = new IntPossibility();

	public WeaponPossibility(Tier tier, int type) {
		this.tier = tier;
		this.type = type;
	}

	public Tier getTier() {
		return tier;
	}

	public int getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public IntPossibility getMinDmg() {
		return minDmg;
	}

	public void setMinDmg(IntPossibility minDmg) {
		this.minDmg = minDmg;
	}

	public int getDmgRange() {
		return dmgRange;
	}

	public void setDmgRange(int dmgRange) {
		this.dmgRange = dmgRange;
	}

	public IntPossibility getFireDmg() {
		return fireDmg;
	}

	public void setFireDmg(IntPossibility fireDmg) {
		this.fireDmg = fireDmg;
	}

	public IntPossibility getIceDmg() {
		return iceDmg;
	}

	public void setIceDmg(IntPossibility iceDmg) {
		this.iceDmg = iceDmg;
	}

	@Override
	public Weapon getRandom() {
		Weapon weapon = new Weapon(tier, type);
		Random random = new Random();
		weapon.getDmg().setMin(minDmg.getRandom());
		weapon.getDmg().setMax(weapon.getDmg().getMin() + random.nextInt(dmgRange + 1));
		if (random.nextFloat() < 0.15) weapon.setFireDmg(fireDmg.getRandom());
		if (random.nextFloat() < 0.15) weapon.setIceDmg(iceDmg.getRandom());
		return weapon;
	}
}

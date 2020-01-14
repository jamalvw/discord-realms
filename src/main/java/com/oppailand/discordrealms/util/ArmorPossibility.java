package com.oppailand.discordrealms.util;

import com.oppailand.discordrealms.entity.Tier;
import com.oppailand.discordrealms.entity.armor.Armor;

import java.util.Random;

public class ArmorPossibility extends Possibility<Armor> {
	private final Tier tier;

	private String name;
	private IntPossibility hp = new IntPossibility();
	private IntPossibility hpRegen = new IntPossibility();
	private IntPossibility energyRegen = new IntPossibility();
	private IntPossibility fireRes = new IntPossibility();
	private IntPossibility iceRes = new IntPossibility();

	public ArmorPossibility(Tier tier) {
		this.tier = tier;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Tier getTier() {
		return tier;
	}

	public IntPossibility getHp() {
		return hp;
	}

	public void setHp(IntPossibility hp) {
		this.hp = hp;
	}

	public IntPossibility getHpRegen() {
		return hpRegen;
	}

	public void setHpRegen(IntPossibility hpRegen) {
		this.hpRegen = hpRegen;
	}

	public IntPossibility getEnergyRegen() {
		return energyRegen;
	}

	public void setEnergyRegen(IntPossibility energyRegen) {
		this.energyRegen = energyRegen;
	}

	public IntPossibility getFireRes() {
		return fireRes;
	}

	public void setFireRes(IntPossibility fireRes) {
		this.fireRes = fireRes;
	}

	public IntPossibility getIceRes() {
		return iceRes;
	}

	public void setIceRes(IntPossibility iceRes) {
		this.iceRes = iceRes;
	}

	@Override
	public Armor getRandom() {
		Armor armor = new Armor(tier);
		Random random = new Random();

		armor.setHp(hp.getRandom());
		if (random.nextBoolean()) armor.setHpRegen(hpRegen.getRandom());
		else armor.setEnergyRegen(energyRegen.getRandom());
		if (random.nextFloat() < 0.1) armor.setFireRes(fireRes.getRandom());
		if (random.nextFloat() < 0.1) armor.setIceRes(iceRes.getRandom());

		return armor;
	}
}

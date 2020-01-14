package com.oppailand.discordrealms.entity;

import com.oppailand.discordrealms.entity.armor.Armor;
import com.oppailand.discordrealms.entity.armor.Weapon;
import com.oppailand.discordrealms.objects.MobObject;

public class Mob {
	private Armor armor;
	private Weapon weapon;
	private int level = 0;

	public Mob() {

	}

	public Mob(MobObject o) {
		this.armor = o.armor != null ? new Armor(o.armor) : null;
		this.weapon = o.weapon != null ? new Weapon(o.weapon) : null;
		this.level = o.level;
	}

	public Armor getArmor() {
		return armor;
	}

	public void setArmor(Armor armor) {
		this.armor = armor;
	}

	public boolean hasArmor() {
		return armor != null;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	public boolean hasWeapon() {
		return weapon != null;
	}

	public int getMaxHp() {
		return 1 + (hasArmor() ? getArmor().getHp() : 0);
	}

	public int getHpRegen() {
		return 1 + (hasArmor() ? getArmor().getHpRegen() : 0);
	}

	public int getEnergyRegen() {
		return (hasArmor() ? getArmor().getEnergyRegen() : 0);
	}

	public float getFireRes() {
		return (hasArmor() ? getArmor().getFireRes() : 0);
	}

	public float getIceRes() {
		return (hasArmor() ? getArmor().getIceRes() : 0);
	}

	public int getMinDmg() {
		return 1 + (hasWeapon() ? getWeapon().getDmg().getMin() : 0);
	}

	public int getMaxDmg() {
		return 1 + (hasWeapon() ? getWeapon().getDmg().getMax() : 0);
	}

	public int getFireDmg() {
		return (hasWeapon() ? getWeapon().getFireDmg() : 0);
	}

	public int getIceDmg() {
		return (hasWeapon() ? getWeapon().getIceDmg() : 0);
	}

	public int getRandomDmg() {
		return 1 + (hasWeapon() ? getWeapon().getDmg().getRandom() : 0);
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void incLevel() {
		level++;
	}

	public MobObject toSavedObject() {
		MobObject o = new MobObject();
		o.armor = armor != null ? armor.toSavedObject() : null;
		o.weapon = weapon != null ? weapon.toSavedObject() : null;
		o.level = level;
		return o;
	}
}

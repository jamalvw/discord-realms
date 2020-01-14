package com.oppailand.discordrealms.entity;

import com.oppailand.discordrealms.entity.armor.Weapon;
import com.oppailand.discordrealms.util.ArmorPossibility;
import com.oppailand.discordrealms.util.IntPossibility;
import com.oppailand.discordrealms.util.ListPossibility;
import com.oppailand.discordrealms.util.WeaponPossibility;

public enum Tier {
	BASIC(0, "Basic", 0, new IntPossibility(50, 80), new IntPossibility(1, 8), 0.06f, 1200) {
		@Override
		public ArmorPossibility getArmor() {
			ArmorPossibility p = new ArmorPossibility(this);
			p.setName("Leather Armor");
			p.setHp(new IntPossibility(45, 125));
			p.setHpRegen(new IntPossibility(2, 9));
			p.setEnergyRegen(new IntPossibility(1, 2));
			p.setFireRes(new IntPossibility(1, 5));
			p.setIceRes(new IntPossibility(1, 5));
			return p;
		}

		@Override
		public WeaponPossibility getWeapon(int type) {
			WeaponPossibility p = new WeaponPossibility(this, type);
			p.setFireDmg(new IntPossibility(1, 5));
			p.setIceDmg(new IntPossibility(1, 5));
			switch (type) {
				case Weapon.SWORD:
					p.setName("Short Sword");
					p.setMinDmg(new IntPossibility(1, 12));
					p.setDmgRange(6);
					break;
				case Weapon.AXE:
					p.setName("Hatchet");
					p.setMinDmg(new IntPossibility(1, 14));
					p.setDmgRange(4);
					break;
				case Weapon.POLEARM:
					p.setName("Spear");
					p.setMinDmg(new IntPossibility(1, 4));
					p.setDmgRange(2);
					break;
			}
			return p;
		}
	},
	ADVANCED(1, "Advanced", 10, new IntPossibility(80, 120), new IntPossibility(10, 25), 0.02f, 2600) {
		@Override
		public ArmorPossibility getArmor() {
			ArmorPossibility p = new ArmorPossibility(this);
			p.setName("Chain Armor");
			p.setHp(new IntPossibility(75, 390));
			p.setHpRegen(new IntPossibility(10, 22));
			p.setEnergyRegen(new IntPossibility(1, 3));
			p.setFireRes(new IntPossibility(1, 10));
			p.setIceRes(new IntPossibility(1, 10));
			return p;
		}

		@Override
		public WeaponPossibility getWeapon(int type) {
			WeaponPossibility p = new WeaponPossibility(this, type);
			p.setFireDmg(new IntPossibility(3, 15));
			p.setIceDmg(new IntPossibility(3, 15));
			switch (type) {
				case Weapon.SWORD:
					p.setName("Great Sword");
					p.setMinDmg(new IntPossibility(5, 32));
					p.setDmgRange(16);
					break;
				case Weapon.AXE:
					p.setName("Great Axe");
					p.setMinDmg(new IntPossibility(7, 38));
					p.setDmgRange(12);
					break;
				case Weapon.POLEARM:
					p.setName("Halberd");
					p.setMinDmg(new IntPossibility(3, 20));
					p.setDmgRange(8);
					break;
			}
			return p;
		}
	},
	MAGIC(2, "Magic", 20, new IntPossibility(120, 180), new IntPossibility(25, 45), 0.01f, 4200) {
		@Override
		public ArmorPossibility getArmor() {
			ArmorPossibility p = new ArmorPossibility(this);
			p.setName("Iron Armor");
			p.setHp(new IntPossibility(130, 860));
			p.setHpRegen(new IntPossibility(24, 44));
			p.setEnergyRegen(new IntPossibility(2, 5));
			p.setFireRes(new IntPossibility(1, 15));
			p.setIceRes(new IntPossibility(1, 15));
			return p;
		}

		@Override
		public WeaponPossibility getWeapon(int type) {
			WeaponPossibility p = new WeaponPossibility(this, type);
			p.setFireDmg(new IntPossibility(8, 25));
			p.setIceDmg(new IntPossibility(8, 25));
			switch (type) {
				case Weapon.SWORD:
					p.setName("Magic Sword");
					p.setMinDmg(new IntPossibility(22, 82));
					p.setDmgRange(38);
					break;
				case Weapon.AXE:
					p.setName("Magic Axe");
					p.setMinDmg(new IntPossibility(30, 90));
					p.setDmgRange(32);
					break;
				case Weapon.POLEARM:
					p.setName("Magic Polearm");
					p.setMinDmg(new IntPossibility(16, 52));
					p.setDmgRange(26);
					break;
			}
			return p;
		}
	},
	ANCIENT(3, "Ancient", 30, new IntPossibility(180, 240), new IntPossibility(60, 90), 0.002f, 8400) {
		@Override
		public ArmorPossibility getArmor() {
			ArmorPossibility p = new ArmorPossibility(this);
			p.setName("Diamond Armor");
			p.setHp(new IntPossibility(700, 2350));
			p.setHpRegen(new IntPossibility(56, 70));
			p.setEnergyRegen(new IntPossibility(4, 7));
			p.setFireRes(new IntPossibility(1, 20));
			p.setIceRes(new IntPossibility(1, 20));
			return p;
		}

		@Override
		public WeaponPossibility getWeapon(int type) {
			WeaponPossibility p = new WeaponPossibility(this, type);
			p.setFireDmg(new IntPossibility(18, 34));
			p.setIceDmg(new IntPossibility(18, 34));
			switch (type) {
				case Weapon.SWORD:
					p.setName("Ancient Sword");
					p.setMinDmg(new IntPossibility(68, 162));
					p.setDmgRange(68);
					break;
				case Weapon.AXE:
					p.setName("Ancient Axe");
					p.setMinDmg(new IntPossibility(74, 170));
					p.setDmgRange(56);
					break;
				case Weapon.POLEARM:
					p.setName("Ancient Polearm");
					p.setMinDmg(new IntPossibility(36, 100));
					p.setDmgRange(32);
					break;
			}
			return p;
		}
	},
	LEGENDARY(4, "Legendary", 40, new IntPossibility(240, 300), new IntPossibility(110, 150), 0.0006f, 16400) {
		@Override
		public ArmorPossibility getArmor() {
			ArmorPossibility p = new ArmorPossibility(this);
			p.setName("Gold Armor");
			p.setHp(new IntPossibility(960, 4750));
			p.setHpRegen(new IntPossibility(100, 200));
			p.setEnergyRegen(new IntPossibility(6, 11));
			p.setFireRes(new IntPossibility(1, 25));
			p.setIceRes(new IntPossibility(1, 25));
			return p;
		}

		@Override
		public WeaponPossibility getWeapon(int type) {
			WeaponPossibility p = new WeaponPossibility(this, type);
			p.setFireDmg(new IntPossibility(25, 50));
			p.setIceDmg(new IntPossibility(25, 50));
			switch (type) {
				case Weapon.SWORD:
					p.setName("Legendary Sword");
					p.setMinDmg(new IntPossibility(110, 276));
					p.setDmgRange(80);
					break;
				case Weapon.AXE:
					p.setName("Legendary Axe");
					p.setMinDmg(new IntPossibility(122, 290));
					p.setDmgRange(68);
					break;
				case Weapon.POLEARM:
					p.setName("Legendary Polearm");
					p.setMinDmg(new IntPossibility(92, 143));
					p.setDmgRange(46);
					break;
			}
			return p;
		}
	};

	private final int id;
	private final String name;
	private final int level;
	private final IntPossibility exp;
	private final IntPossibility gems;
	private final float itemChance;
	private final int maxDura;

	Tier(int id, String name, int level, IntPossibility exp, IntPossibility gems, float itemChance, int maxDura) {
		this.id = id;
		this.name = name;
		this.level = level;
		this.exp = exp;
		this.gems = gems;
		this.itemChance = itemChance;
		this.maxDura = maxDura;
	}

	public static Tier fromId(int id) {
		for (Tier t : values())
			if (t.id == id) return t;
		return null;
	}

	public static Tier fromLevel(int level) {
		if (level >= LEGENDARY.level) return LEGENDARY;
		else if (level >= ANCIENT.level) return ANCIENT;
		else if (level >= MAGIC.level) return MAGIC;
		else if (level >= ADVANCED.level) return ADVANCED;
		else return BASIC;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getLevel() {
		return level;
	}

	public IntPossibility getExp() {
		return exp;
	}

	public IntPossibility getGems() {
		return gems;
	}

	public WeaponPossibility getWeapon() {
		return getWeapon(new ListPossibility<>(Weapon.SWORD, Weapon.AXE, Weapon.POLEARM).getRandom());
	}

	public abstract WeaponPossibility getWeapon(int type);

	public abstract ArmorPossibility getArmor();

	public float getItemChance() {
		return itemChance;
	}

	public int getMaxDurability() {
		return maxDura;
	}
}

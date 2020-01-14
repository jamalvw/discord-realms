package com.oppailand.discordrealms.journey;

import com.oppailand.discordrealms.entity.Monster;
import com.oppailand.discordrealms.entity.Tier;
import com.oppailand.discordrealms.entity.armor.Armor;
import com.oppailand.discordrealms.entity.armor.Weapon;

import java.util.concurrent.ThreadLocalRandom;

public class RandomJourney extends Journey {
	public RandomJourney(String id) {
		super(id);
	}

	@Override
	public void setup() {
		getNodes().clear();
		for (int a = 0; a < 2 + ((getLevel() + 1) * (2 + ThreadLocalRandom.current().nextFloat() * 2)); a++) {
			JourneyNode n = new JourneyNode();

			for (int b = 0; b < 3; b++) {
				Monster m = new Monster(Tier.fromLevel(getLevel()));

				Weapon weapon = m.getTier().getWeapon().getRandom();
				weapon.setDurability(ThreadLocalRandom.current().nextInt(1, weapon.getTier().getMaxDurability()));
				m.setWeapon(weapon);

				Armor armor = m.getTier().getArmor().getRandom();
				armor.setDurability(ThreadLocalRandom.current().nextInt(1, armor.getTier().getMaxDurability()));
				m.setArmor(armor);

				n.getMonsters().add(m);
			}

			getNodes().add(n);
		}

		setExp(Math.round((getLevel() * 12) * (1 + ((float) getNodes().size() / 2))));
	}
}

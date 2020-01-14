package com.oppailand.discordrealms.util;

import com.oppailand.discordrealms.entity.Mob;
import com.oppailand.discordrealms.entity.Player;
import com.oppailand.discordrealms.entity.Tier;
import com.oppailand.discordrealms.entity.armor.Armor;
import com.oppailand.discordrealms.entity.armor.Item;
import com.oppailand.discordrealms.entity.armor.Weapon;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.util.EmbedBuilder;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Helper {
	public static String[] getArguments(String[] split) {
		return split.length > 1 ? Arrays.copyOfRange(split, 1, split.length) : new String[0];
	}

	public static int getPercent(int a, int b) {
		return getPercent((float) a, (float) b);
	}

	public static int getPercent(float a, float b) {
		return Math.round((a / b) * 100);
	}

	public static Color getRatingColor(int rating) {
		if (rating < 500) return Color.LIGHT_GRAY;
		else if (rating < 1000) return Color.GREEN;
		else if (rating <= 1750) return Color.MAGENTA;
		else if (rating <= 2500) return Color.CYAN;
		else return Color.YELLOW;
	}

	public static int calcMaxExp(int level) {
		return (int) (400 * Math.pow(level + 1, 1.68));
	}

	public static Map<Integer, Item> buildItemListings(List<Item> items) {
		return buildItemListings(items, i -> true);
	}

	public static Map<Integer, Item> buildItemListings(List<Item> items, Predicate<Item> p) {
		return items.stream().filter(p).collect(Collectors.toMap(items::indexOf, i -> i));
	}

	//TODO cleanup
	public static int getRepairCost(Armor a) {
		double cost;
		Tier tier = a.getTier();
		int tiermultiplier = 0;
		switch (tier) {
			case BASIC:
				tiermultiplier = 5;
				break;
			case ADVANCED:
				tiermultiplier = 20;
				break;
			case MAGIC:
				tiermultiplier = 100;
				break;
			case ANCIENT:
				tiermultiplier = 500;
				break;
			case LEGENDARY:
				tiermultiplier = 3000;
				break;
		}
		double durapercent = (double) a.getDurability() / tier.getMaxDurability() * 100;
//        System.out.println(a.getDurability());
//        System.out.println(durapercent);
		cost = ((100 - durapercent) / 98 + Math.log10(100 - durapercent) + 1) * tiermultiplier;
//        System.out.println(cost);
		//TODO implement rarity multiplier
		return (int) cost;
	}

	public static int getRepairCost(Weapon w) {
		double cost;
		Tier tier = w.getTier();
		int tiermultiplier = 0;
		switch (tier) {
			case BASIC:
				tiermultiplier = 6;
				break;
			case ADVANCED:
				tiermultiplier = 25;
				break;
			case MAGIC:
				tiermultiplier = 120;
				break;
			case ANCIENT:
				tiermultiplier = 600;
				break;
			case LEGENDARY:
				tiermultiplier = 3500;
				break;
		}
		double durapercent = (double) w.getDurability() / tier.getMaxDurability() * 100;
//        System.out.println(w.getDurability());
//        System.out.println(durapercent);
		cost = ((100 - durapercent) / 95 + Math.log10(100 - durapercent) + 1) * tiermultiplier;
//        System.out.println(cost);
		//TODO implement rarity multiplier
		return (int) cost;
	}

	public static EmbedObject displayItems(Player p) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.withTitle("Items");
		builder.withDesc("You currently have **" + p.getBank().size() + "** item(s).");
		builder.withAuthorName(p.getUser().getName());
		builder.withAuthorIcon(p.getUser().getAvatarURL());

		if (p.hasWeapon()) builder.appendField("Weapon", p.getWeapon().toFullString(), true);
		if (p.hasArmor()) builder.appendField("Armor", p.getArmor().toFullString(), true);

		Map<Integer, Item> items = Helper.buildItemListings(p.getBank());
		if (!items.isEmpty()) builder.appendField("All Items", items.entrySet().stream()
				.map(e -> e.getKey() + ": " + e.getValue())
				.collect(Collectors.joining("\n")), false);

		builder.withTimestamp(LocalDateTime.now());

		return builder.build();
	}

	/**
	 * Calculates the actual damage dealt against a mob accounting all statistics.
	 *
	 * @param a The attacker.
	 * @param v The victim.
	 * @return The actual damage dealt.
	 */
	public static int calcHit(Mob a, Mob v) {
		int phyDmg = a.getRandomDmg();
		int magDmg = 0;

		// Fire Damage
		int fireDmg = a.getFireDmg();
		//fireDmg *= (100.0f - (float) v.getFireRes()) / 100;
		magDmg += fireDmg;

		// Ice Damage
		int iceDmg = a.getIceDmg();
		//iceDmg *= (100.0f - (float) v.getIceRes()) / 100;
		magDmg += iceDmg;

		return phyDmg + magDmg;
	}
}

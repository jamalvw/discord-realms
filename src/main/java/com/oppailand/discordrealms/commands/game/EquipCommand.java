package com.oppailand.discordrealms.commands.game;

import com.oppailand.discordrealms.DiscordRealms;
import com.oppailand.discordrealms.commands.Command;
import com.oppailand.discordrealms.entity.Player;
import com.oppailand.discordrealms.entity.armor.Armor;
import com.oppailand.discordrealms.entity.armor.Item;
import com.oppailand.discordrealms.entity.armor.Weapon;
import com.oppailand.discordrealms.util.Helper;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.util.Map;

public class EquipCommand implements Command {
	@Override
	public void execute(String alias, String[] args, Player p, IMessage msg) throws DiscordException, MissingPermissionsException, RateLimitException {
		IChannel channel = p.getUser().getOrCreatePMChannel();
		if (args.length < 1)
			channel.sendMessage(":x: You must specify a slot.");
		else {
			Map<Integer, Item> items = Helper.buildItemListings(p.getBank());
			try {
				int slot = Integer.parseInt(args[0]);
				if (!items.containsKey(slot))
					channel.sendMessage(":x: Invalid slot.");
				else {
					Item i = items.get(slot);
					if (i instanceof Weapon) {
						if (p.hasWeapon()) p.getBank().add(p.getWeapon());
						p.setWeapon((Weapon) i);
						p.getBank().remove(i);
						channel.sendMessage(":white_check_mark: You have equipped **" + i.getName() + "**.", Helper.displayItems(p), false);
						DiscordRealms.savePlayer(p);
					} else if (i instanceof Armor) {
						if (p.hasArmor()) p.getBank().add(p.getArmor());
						p.setArmor((Armor) i);
						p.getBank().remove(i);
						channel.sendMessage(":white_check_mark: You have equipped **" + i.getName() + "**.", Helper.displayItems(p), false);
						DiscordRealms.savePlayer(p);
					} else {
						channel.sendMessage(":x: **" + i.getName() + "** cannot be equipped.", Helper.displayItems(p), false);
					}
				}
			} catch (NumberFormatException e) {
				channel.sendMessage(":x: Invalid slot.");
			}
		}
	}

	@Override
	public String getName() {
		return "equip";
	}
}

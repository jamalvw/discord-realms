package com.oppailand.discordrealms.commands.game;

import com.oppailand.discordrealms.commands.Command;
import com.oppailand.discordrealms.entity.Player;
import com.oppailand.discordrealms.entity.armor.Item;
import com.oppailand.discordrealms.util.Helper;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.util.Map;

public class InspectCommand implements Command {

	@Override
	public void execute(String alias, String[] args, Player p, IMessage msg) throws DiscordException, MissingPermissionsException, RateLimitException {
		IChannel channel = msg.getChannel();
		if (args.length < 1)
			channel.sendMessage(":x: You must specify a slot.");
		else {
			if (args[0].equalsIgnoreCase("weapon")) {
				if (!p.hasWeapon()) channel.sendMessage(":x: You do not have a weapon equipped.");
				else {
					Item i = p.getWeapon();
					channel.sendMessage(":mag: Inspecting **" + i.getName() + "**:\n" + i.toFullString());
				}
			} else if (args[0].equalsIgnoreCase("armor")) {
				if (!p.hasArmor()) channel.sendMessage(":x: You do not have armor equipped.");
				else {
					Item i = p.getArmor();
					channel.sendMessage(":mag: Inspecting **" + i.getName() + "**:\n" + i.toFullString());
				}
			} else {
				Map<Integer, Item> items = Helper.buildItemListings(p.getBank());
				try {
					int slot = Integer.parseInt(args[0]);
					if (!items.containsKey(slot))
						channel.sendMessage(":x: Invalid slot.");
					else {
						Item i = items.get(slot);
						channel.sendMessage(":mag: Inspecting **" + i.getName() + "**:\n" + i.toFullString());
					}
				} catch (NumberFormatException e) {
					channel.sendMessage(":x: Invalid slot.");
				}
			}
		}
	}

	@Override
	public String getName() {
		return "inspect";
	}
}

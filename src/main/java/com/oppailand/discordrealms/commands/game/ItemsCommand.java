package com.oppailand.discordrealms.commands.game;

import com.oppailand.discordrealms.commands.Command;
import com.oppailand.discordrealms.entity.Player;
import com.oppailand.discordrealms.util.Helper;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

public class ItemsCommand implements Command {
	@Override
	public void execute(String alias, String[] args, Player p, IMessage msg) throws DiscordException, MissingPermissionsException, RateLimitException {
		IChannel channel = p.getUser().getOrCreatePMChannel();
		channel.sendMessage(":mag: Viewing items.", Helper.displayItems(p), false);
	}

	@Override
	public String getName() {
		return "items";
	}

	@Override
	public String[] getAliases() {
		return new String[]{"bank", "equipment", "item", "gimmeshit"};
	}
}

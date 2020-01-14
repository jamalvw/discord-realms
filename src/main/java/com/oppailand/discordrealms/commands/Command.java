package com.oppailand.discordrealms.commands;

import com.oppailand.discordrealms.entity.Player;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

public interface Command {
	void execute(String alias, String[] args, Player p, IMessage msg) throws DiscordException, MissingPermissionsException, RateLimitException;

	String getName();

	default String[] getAliases() {
		return new String[0];
	}

	default String getDescription() {
		return "";
	}

	default String getUsage() {
		return "";
	}
}

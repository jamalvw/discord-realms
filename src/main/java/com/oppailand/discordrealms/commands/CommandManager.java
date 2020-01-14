package com.oppailand.discordrealms.commands;

import com.oppailand.discordrealms.entity.Player;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.util.HashMap;

public class CommandManager extends HashMap<String, Command> {
	public void registerCommand(Command c) {
		put(c.getName(), c);
		for (String alias : c.getAliases())
			put(alias, c);
	}

	public void executeCommand(String c, String[] args, Player p, IMessage m) throws RateLimitException, DiscordException, MissingPermissionsException {
		if (containsKey(c.trim().toLowerCase()))
			get(c.trim().toLowerCase()).execute(c, args, p, m);
	}
}
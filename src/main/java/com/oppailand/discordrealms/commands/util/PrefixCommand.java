package com.oppailand.discordrealms.commands.util;

import com.oppailand.discordrealms.commands.Command;
import com.oppailand.discordrealms.entity.Player;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

public class PrefixCommand implements Command {
	@Override
	public void execute(String alias, String[] args, Player p, IMessage msg) throws DiscordException, MissingPermissionsException, RateLimitException {
		IChannel channel = msg.getChannel();
		if (args.length < 0)
			channel.sendMessage(":x: You must specify a new custom prefix.");
		else {
			String prefix = String.join(" ", args);
			if (prefix.equalsIgnoreCase("reset")) {
				p.setPrefix("");
				channel.sendMessage(":white_check_mark: Your custom prefix has been reset.");
			} else if (prefix.trim().isEmpty())
				channel.sendMessage(":x: Custom prefix cannot be empty.");
			else if (prefix.length() > 6)
				channel.sendMessage(":x: Custom prefix must be less than 6 characters.");
			else if (prefix.contains("`"))
				channel.sendMessage(":x: Your custom prefix contains invalid characters.");
			else {
				p.setPrefix(prefix);
				channel.sendMessage(":white_check_mark: Your custom prefix has been to `" + prefix + "`.");
			}
		}
	}

	@Override
	public String getName() {
		return "prefix";
	}
}

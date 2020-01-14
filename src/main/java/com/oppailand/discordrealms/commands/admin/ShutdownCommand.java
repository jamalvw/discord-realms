package com.oppailand.discordrealms.commands.admin;

import com.oppailand.discordrealms.commands.Command;
import com.oppailand.discordrealms.entity.Player;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

public class ShutdownCommand implements Command {
	@Override
	public void execute(String alias, String[] args, Player p, IMessage msg) throws DiscordException, MissingPermissionsException, RateLimitException {
		IDiscordClient client = msg.getClient();
		IChannel channel = msg.getChannel();
		IUser user = p.getUser();

		if (!user.equals(client.getApplicationOwner()))
			channel.sendMessage(":x: You are not the application owner.");

		channel.sendMessage(":arrow_down: Shutting down.");
		System.exit(0);
	}

	@Override
	public String getName() {
		return "shutdown";
	}
}

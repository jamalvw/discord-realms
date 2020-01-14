package com.oppailand.discordrealms.commands.game.party;

import com.oppailand.discordrealms.commands.Command;
import com.oppailand.discordrealms.entity.Party;
import com.oppailand.discordrealms.entity.Player;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

public class PCreateCommand implements Command {

	@Override
	public void execute(String alias, String[] args, Player p, IMessage msg) throws DiscordException, MissingPermissionsException, RateLimitException {
		IDiscordClient client = msg.getClient();
		IChannel channel = msg.getChannel();
		if (p.isInParty())
			channel.sendMessage(":x: You are already in a party.");
		else {
			p.setParty(new Party(p));
			channel.sendMessage(":white_check_mark: You have created a party.");
		}
	}

	@Override
	public String getName() {
		return "pcreate";
	}
}

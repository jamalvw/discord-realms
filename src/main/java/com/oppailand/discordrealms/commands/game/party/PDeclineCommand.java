package com.oppailand.discordrealms.commands.game.party;

import com.oppailand.discordrealms.commands.Command;
import com.oppailand.discordrealms.entity.Player;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

public class PDeclineCommand implements Command {
	@Override
	public void execute(String alias, String[] args, Player p, IMessage msg) throws DiscordException, MissingPermissionsException, RateLimitException {
		IChannel channel = msg.getChannel();
		if (!p.hasPartyInviter())
			channel.sendMessage(":x: You do not have a pending party invite.");
		else {
			channel.sendMessage(":regional_indicator_x: You have declined the party request.");
			p.setPartyInviter(null);
		}
	}

	@Override
	public String getName() {
		return "pdecline";
	}
}

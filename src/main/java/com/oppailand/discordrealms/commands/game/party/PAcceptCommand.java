package com.oppailand.discordrealms.commands.game.party;

import com.oppailand.discordrealms.commands.Command;
import com.oppailand.discordrealms.entity.Player;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

public class PAcceptCommand implements Command {
	@Override
	public void execute(String alias, String[] args, Player p, IMessage msg) throws DiscordException, MissingPermissionsException, RateLimitException {
		IChannel channel = msg.getChannel();
		if (p.isInParty())
			channel.sendMessage(":x: You are already in a party.");
		else if (!p.hasPartyInviter())
			channel.sendMessage(":x: You do not have a pending party invite.");
		else if (p.getPartyInviter().getParty() == null) {
			channel.sendMessage(":x: That party no longer exists.");
			p.setPartyInviter(null);
		} else if (p.getPartyInviter().getParty().getLeader().isOnJourney()) {
			channel.sendMessage(":x: That party is currently on a journey.");
			p.setPartyInviter(null);
		} else {
			p.getPartyInviter().getParty().add(p);
			p.setPartyInviter(null);
		}
	}

	@Override
	public String getName() {
		return "paccept";
	}
}

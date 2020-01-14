package com.oppailand.discordrealms.commands.game.party;

import com.oppailand.discordrealms.commands.Command;
import com.oppailand.discordrealms.entity.Player;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

public class PLeaveCommand implements Command {
	@Override
	public void execute(String alias, String[] args, Player p, IMessage msg) throws DiscordException, MissingPermissionsException, RateLimitException {
		IChannel channel = msg.getChannel();
		if (!p.isInParty())
			channel.sendMessage(":x: You are not in a party.");
		else {
			if (p.getParty().getLeader().equals(p))
				p.setJourney(null);
			channel.sendMessage(":regional_indicator_x: You have left the party.");
			p.getParty().remove(p);
		}
	}

	@Override
	public String getName() {
		return "pleave";
	}
}

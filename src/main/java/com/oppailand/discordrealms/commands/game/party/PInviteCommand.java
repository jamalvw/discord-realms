package com.oppailand.discordrealms.commands.game.party;

import com.oppailand.discordrealms.DiscordRealms;
import com.oppailand.discordrealms.commands.Command;
import com.oppailand.discordrealms.entity.Party;
import com.oppailand.discordrealms.entity.Player;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Presences;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

public class PInviteCommand implements Command {
	@Override
	public void execute(String alias, String[] args, Player p, IMessage msg) throws DiscordException, MissingPermissionsException, RateLimitException {
		IChannel channel = msg.getChannel();
		IDiscordClient client = msg.getClient();

		if (!p.isInParty()) {
			p.setParty(new Party(p));
			channel.sendMessage(":white_check_mark: You have created a party.");
		}

		if (!p.getParty().getLeader().equals(p))
			channel.sendMessage(":x: You are not the party leader.");
		else if (args.length < 1)
			channel.sendMessage(":x: You must specify a user to invite.");
		else if (p.getParty().getLeader().isOnJourney())
			channel.sendMessage(":x: You cannot invite players while on a journey.");
		else {
			IUser invitee = client.getUsers().stream()
					.filter(n -> n.getName().toLowerCase().startsWith(
							String.join(" ", args).toLowerCase()))
					.findAny().orElse(null);
			if (invitee == null || invitee.isBot())
				channel.sendMessage(":x: That user either does not exist or is not currently able to play.");
			else {
				Player other = DiscordRealms.getPlayer(invitee);
				if (p.equals(other))
					channel.sendMessage(":x: Don't invite yourself. That's weird.");
				else if (invitee.getPresence().equals(Presences.OFFLINE))
					channel.sendMessage(":x: That player is currently offline or unavailable.");
				else if (other.isInParty())
					channel.sendMessage(":x: That player is already in a party.");
				else if (other.hasPartyInviter())
					channel.sendMessage(":x: That player already has a pending party invite.");
				else {
					other.setPartyInviter(p);
					channel.sendMessage(":white_check_mark: Party invite sent to **" + invitee.getName() + "**.");
					other.getUser().getOrCreatePMChannel().sendMessage(":sparkle: You have received a party invitation from **" + p.getUser().getName() + "**.\nYou may enter `paccept` to join, or `pdecline` to reject.");
				}
			}
		}
	}

	@Override
	public String getName() {
		return "pinvite";
	}
}

package com.oppailand.discordrealms.commands.game;

import com.oppailand.discordrealms.DiscordRealms;
import com.oppailand.discordrealms.commands.Command;
import com.oppailand.discordrealms.entity.Player;
import com.oppailand.discordrealms.journey.Journey;
import com.oppailand.discordrealms.journey.JourneyState;
import com.oppailand.discordrealms.util.Helper;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class JourneyCommand implements Command {
	@Override
	public void execute(String alias, String[] args, Player p, IMessage msg) throws RateLimitException, DiscordException, MissingPermissionsException {
		IChannel channel = msg.getChannel();
		if (args.length < 1) {
			if ((p.isInParty() && !p.getParty().getLeader().isOnJourney()) && !p.isOnJourney()) {
				int recId = Math.min(Math.max(Math.round(p.getLevel() / 5), 1), 20);
				channel.sendMessage(":information_source: Your current journey of interest is **" + recId + "**." +
						"\nYou can embark on this journey using `journey " + recId + "`.");
			} else {
				Player o = p.isInParty() ? p.getParty().getLeader() : p;
				EmbedBuilder builder = new EmbedBuilder();
				builder.withTitle("Ongoing Journey");
				builder.withColor(Color.YELLOW);
				int s = (o.getJourney().getNodes().size() - o.getJourneyNode()) * 10;
				builder.withDesc("You have about " + String.format("%02d:%02d", s / 60, s % 60) + " remaining.");
				builder.withAuthorName(o.getUser().getName());
				builder.withAuthorIcon(o.getUser().getAvatarURL());
				builder.appendField("Completion", Helper.getPercent(o.getJourneyNode(), o.getJourney().getNodes().size()) + "%", true);
				builder.withTimestamp(LocalDateTime.now());
				channel.sendMessage("", builder.build(), false);
			}
		} else {
			if (args[0].equalsIgnoreCase("end")) {
				if (p.isInParty() && !p.getParty().getLeader().equals(p))
					channel.sendMessage(":x: You are not the party leader.");
				else if (!p.isOnJourney())
					channel.sendMessage(":x: You are not on a journey.");
				else
					p.getJourneyThread().end(JourneyState.FLED);
			} else if (p.isInParty() && !p.getParty().getLeader().equals(p))
				channel.sendMessage(":x: You are not the party leader.");
			else if (p.isOnJourney())
				channel.sendMessage(":x: You are already on a journey.");
			else {
				Journey j = DiscordRealms.getJourney(args[0].toLowerCase());
				if (j == null)
					channel.sendMessage(":x: Invalid journey ID.");
				else {
					if (p.getLevel() < j.getLevel() - 1)
						channel.sendMessage(":x: You must be level " + j.getLevel() + " or higher to go on this journey.");
					else {
						j.setup();
						p.setJourney(j);
						String weapon = p.getCurrentGroup().stream().map(o -> o.getWeapon().toString()).collect(Collectors.joining("\n"));
						String armor = p.getCurrentGroup().stream().map(o -> o.getArmor().toString()).collect(Collectors.joining("\n"));

						for (Player o : p.getCurrentGroup()) {
							DiscordRealms.savePlayer(o);
							EmbedBuilder builder = new EmbedBuilder();
							builder.withAuthorName(p.getUser().getName());
							builder.withAuthorIcon(p.getUser().getAvatarURL());
							builder.withTitle("Journey Started");
							builder.withColor(Color.CYAN);
							int s = j.getNodes().size() * 10;
							builder.withDescription("Please, return soon to view the results of the journey. Good luck!" +
									"\nCompletion Time: **" + String.format("%02d:%02d", s / 60, s % 60) + "**" +
									"\nPlayers: " + p.getCurrentGroup().stream().map(Object::toString).collect(Collectors.joining(", ")));
							if (p.hasWeapon()) builder.appendField("Weapon(s)", weapon, true);
							if (p.hasArmor()) builder.appendField("Armor", armor, true);
							builder.withTimestamp(LocalDateTime.now());
							o.getUser().getOrCreatePMChannel().sendMessage("", builder.build(), false);
						}
					}
				}
			}
		}
	}

	@Override
	public String getName() {
		return "journey";
	}

	@Override
	public String[] getAliases() {
		return new String[]{"play"};
	}

	@Override
	public String getDescription() {
		return "Embark on a journey.";
	}
}

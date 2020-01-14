package com.oppailand.discordrealms.entity;

import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.util.HashSet;
import java.util.Set;

public class Party {
	private Player leader;
	private Set<Player> members = new HashSet<>();

	public Party(Player leader) {
		this.leader = leader;
		members.add(leader);
	}

	public Player getLeader() {
		return leader;
	}

	public void setLeader(Player p) {
		if (p != leader)
			remove(leader);
		leader = p;
		add(leader);
	}

	public Set<Player> getMembers() {
		return members;
	}

	public void add(Player p) {
		members.add(p);
		p.setParty(this);

		for (Player other : members)
			try {
				other.getUser().getOrCreatePMChannel().sendMessage(":white_check_mark: **" + p.getUser().getName() + "** has joined the party.");
			} catch (MissingPermissionsException | RateLimitException | DiscordException e) {
				e.printStackTrace();
			}
	}

	public void remove(Player p) {
		members.remove(p);
		leader = members.stream().findAny().orElse(null);
		p.setParty(null);

		for (Player other : members)
			try {
				other.getUser().getOrCreatePMChannel().sendMessage(":regional_indicator_x: **" + p.getUser().getName() + "** has left the party.");
			} catch (MissingPermissionsException | RateLimitException | DiscordException e) {
				e.printStackTrace();
			}
	}
}

package com.oppailand.discordrealms.commands.game;

import com.oppailand.discordrealms.commands.Command;
import com.oppailand.discordrealms.entity.Player;
import com.oppailand.discordrealms.util.Helper;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.time.LocalDateTime;

public class StatsCommand implements Command {

	public void execute(String alias, String[] args, Player p, IMessage msg) throws RateLimitException, DiscordException, MissingPermissionsException {
		IChannel channel = msg.getChannel();

		EmbedBuilder builder = new EmbedBuilder();
		builder.withTitle("Statistics");
		builder.withDesc("**DMG**: " + p.getMinDmg() + " - " + p.getMaxDmg() + "\n" + "**HP**: " + p.getMaxHp());
		builder.withAuthorName(p.getUser().getName());
		builder.withAuthorIcon(p.getUser().getAvatarURL());
		builder.withDesc("Everything you'd ever want to know about " + p.getUser().getName() + "!");
		builder.appendField("Level", (p.getLevel() + 1) + " (" + p.getCurrentExp() + " / " + Helper.calcMaxExp(p.getLevel()) + ")", true);
		builder.appendField("Gems", p.getGems() + "", false);
		if (p.hasWeapon()) builder.appendField("Weapon", p.getWeapon().toFullString(), true);
		if (p.hasArmor()) builder.appendField("Armor", p.getArmor().toFullString(), false);
		builder.withTimestamp(LocalDateTime.now());

		channel.sendMessage("", builder.build(), false);
	}

	@Override
	public String getName() {
		return "stats";
	}

	@Override
	public String[] getAliases() {
		return new String[]{"myinfo", "me", "whothefuckiam"};
	}
}

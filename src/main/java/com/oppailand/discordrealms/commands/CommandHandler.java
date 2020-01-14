package com.oppailand.discordrealms.commands;

import com.oppailand.discordrealms.DiscordRealms;
import com.oppailand.discordrealms.entity.Player;
import com.oppailand.discordrealms.util.Helper;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

public class CommandHandler {
	private final CommandManager cmds;

	public CommandHandler(CommandManager cmds) {
		this.cmds = cmds;
	}

	@EventSubscriber
	public void onMessage(MessageReceivedEvent event) throws RateLimitException, DiscordException, MissingPermissionsException {
		IDiscordClient client = event.getClient();
		IMessage message = event.getMessage();
		IUser author = message.getAuthor();

		if (author.isBot()) return;

		String content = message.getContent().trim();
		Player player = DiscordRealms.getPlayer(author);

		if (player.hasCommandSelection()) {
			String[] args = content.split(" ");
			player.getCommandSelection().execute(args, player, message);
		} else {
			boolean command = false;

			if (content.startsWith(player.getPrefix())) {
				content = content.replaceFirst(player.getPrefix(), "");
				command = true;
			}

			if (!command && content.startsWith("_realms.")) {
				content = content.replaceFirst("_realms.", "");
				command = true;
			}

			if (command) {
				String[] split = content.split(" ");
				String alias = split[0];
				String[] args = Helper.getArguments(split);
				System.out.println(author.getName() + ": " + content);
				cmds.executeCommand(alias, args, player, message);
			}
		}
	}
}

package com.oppailand.discordrealms.journey;

import com.oppailand.discordrealms.DiscordRealms;
import com.oppailand.discordrealms.entity.Monster;
import com.oppailand.discordrealms.entity.Player;
import com.oppailand.discordrealms.entity.armor.Item;
import com.oppailand.discordrealms.util.Helper;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

public class JourneyThread implements Runnable {
	private final Player player;
	private Journey journey;
	private JourneyLoot loot;
	private int node;
	private int hp;
	private int maxHp;

	public JourneyThread(Player p) {
		player = p;
	}

	@Override
	public void run() {
		JourneyNode n = journey.getNodes().get(node);

		if (!n.getMonsters().isEmpty()) {
			for (int i = 0; i < n.getMonsters().size(); i++) {
				Monster m = n.getMonsters().get(i);
				int mobHp = m.getMaxHp();

				while (mobHp > 0 && hp > 0) {
					Player p = player.getCurrentGroup().get(ThreadLocalRandom.current().nextInt(player.getCurrentGroup().size()));

					for (int a = 0; a < p.getSwings() && mobHp > 0; i++) {
						mobHp -= Math.max(Helper.calcHit(p, m), 1);
						if (p.hasWeapon()) {
							p.getWeapon().subtractDurability(1);
							if (p.getWeapon().getDurability() <= 0)
								p.setWeapon(null);
						}
					}

					hp -= Math.max(Helper.calcHit(m, player), 1);

					if (p.hasArmor()) {
						p.getArmor().subtractDurability(1);
						if (p.getArmor().getDurability() <= 0)
							p.setArmor(null);
					}
				}

				if (hp <= 0) {
					end(JourneyState.FAILED);
					return;
				} else {
					loot.addExp(m.getTier().getExp().getRandom());
					if (ThreadLocalRandom.current().nextFloat() < 0.28f)
						loot.addGems(m.getTier().getGems().getRandom());
					if (ThreadLocalRandom.current().nextFloat() < m.getTier().getItemChance()) {
						Item item = ThreadLocalRandom.current().nextBoolean() ? m.getWeapon() : m.getArmor();
						if (item != null) loot.addItem(item);
					}
				}
			}
		}

		node++;
		if (node >= journey.getNodes().size())
			end(JourneyState.SUCCESS);
		else
			hp = maxHp;
	}

	public void end(JourneyState state) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.withTitle(state.getTitle());
		builder.withColor(state.getColor());
		builder.withAuthorName(player.getUser().getName());
		builder.withAuthorIcon(player.getUser().getAvatarURL());
		builder.withDesc(state.getMessage());

		int exp = loot.getExp();

		if (state.equals(JourneyState.SUCCESS)) {
			exp += journey.getExp() * (1 + ((player.getCurrentGroup().size() - 1) / 2));

			if (loot.getGems() > 0) {
				for (Player o : player.getCurrentGroup())
					o.addGems(loot.getGems() / player.getCurrentGroup().size());
				builder.appendField("Gems", loot.getGems() / player.getCurrentGroup().size() + "/p", true);
			}
			if (!loot.getItems().isEmpty()) {
				String itemList = "";
				for (Item i : loot.getItems()) {
					Player p = player.getCurrentGroup().get(ThreadLocalRandom.current()
							.nextInt(player.getCurrentGroup().size()));
					p.addItem(i);
					itemList += i + " [G: " + p.getUser().getName() + "]";
				}
				builder.appendField("Items", itemList, false);
			}
		} else {
			builder.appendField("Completion", Helper.getPercent(node, journey.getNodes().size()) + "%", true);
		}

		if (loot.getExp() > 0) {
			builder.appendField("EXP", exp / player.getCurrentGroup().size() + "/p", true);
			for (Player o : player.getCurrentGroup())
				o.addExp(exp / player.getCurrentGroup().size());
		}

		builder.withTimestamp(LocalDateTime.now());
		try {
			for (Player o : player.getCurrentGroup())
				o.getUser().getOrCreatePMChannel().sendMessage("", builder.build(), false);
		} catch (RateLimitException | DiscordException | MissingPermissionsException e) {
			e.printStackTrace();
		}

		DiscordRealms.savePlayer(player);
		journey = null;
		player.endJourney();
	}

	public Player getPlayer() {
		return player;
	}

	public Journey getJourney() {
		return journey;
	}

	public void setJourney(Journey j) {
		journey = j;
		loot = new JourneyLoot();
		node = 0;
		maxHp = player.getCurrentGroup().stream().mapToInt(Player::getMaxHp).sum();
		hp = maxHp;
	}

	public JourneyLoot getLoot() {
		return loot;
	}

	public int getNode() {
		return node;
	}

	public int getHP() {
		return hp;
	}
}

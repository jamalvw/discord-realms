package com.oppailand.discordrealms;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oppailand.discordrealms.commands.CommandHandler;
import com.oppailand.discordrealms.commands.CommandManager;
import com.oppailand.discordrealms.commands.game.*;
import com.oppailand.discordrealms.commands.game.party.*;
import com.oppailand.discordrealms.commands.util.PrefixCommand;
import com.oppailand.discordrealms.entity.Monster;
import com.oppailand.discordrealms.entity.Player;
import com.oppailand.discordrealms.entity.Tier;
import com.oppailand.discordrealms.entity.armor.Armor;
import com.oppailand.discordrealms.entity.armor.Weapon;
import com.oppailand.discordrealms.journey.Journey;
import com.oppailand.discordrealms.journey.JourneyNode;
import com.oppailand.discordrealms.journey.RandomJourney;
import com.oppailand.discordrealms.objects.PlayerObject;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.Image;
import sx.blah.discord.util.RateLimitException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;

public class DiscordRealms {
	public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	public static final String DEFAULT_PREFIX = "=";
	private static final List<Player> players = new ArrayList<>();
	private static final List<Journey> journeys = new ArrayList<>();
	private static String token;
	private static ScheduledExecutorService scheduler;
	private static CommandHandler cmdHandler;
	private static IDiscordClient client;

	public static void main(String[] args) throws DiscordException, RateLimitException {
		loadConfig();
		setupCommandManager();
		loginClient();
	}

	private static void loadConfig() {
		Properties p = new Properties();
		File cfg = new File("config.cfg");
		try {
			if (cfg.createNewFile()) try (FileWriter fw = new FileWriter(cfg)) {
				token = (String) p.setProperty("token", "");
				scheduler = Executors.newScheduledThreadPool((int) (p.setProperty("core_pool_size", "2")));
				p.store(fw, "Configuration");
			}
			else try (FileReader fr = new FileReader(cfg)) {
				p.load(fr);
				token = p.getProperty("token", "");
				scheduler = Executors.newScheduledThreadPool(Integer.parseInt(p.getProperty("core_pool_size", "2")));
				fr.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void loadPlayerData() {
		File folder = new File("players/");
		if (!folder.mkdir() && folder.exists())
			for (File f : folder.listFiles())
				if (f.getName().endsWith("json"))
					try (FileReader fr = new FileReader(f)) {
						Player player = new Player(GSON.fromJson(fr, PlayerObject.class));
						players.add(player);
					} catch (IOException e) {
						e.printStackTrace();
					}
	}

	private static void setupCommandManager() {
		CommandManager cmds = new CommandManager();
		cmds.clear();
		cmds.registerCommand(new PAcceptCommand());
		cmds.registerCommand(new PCreateCommand());
		cmds.registerCommand(new PDeclineCommand());
		cmds.registerCommand(new PInviteCommand());
		cmds.registerCommand(new PLeaveCommand());
		cmds.registerCommand(new EquipCommand());
		cmds.registerCommand(new InspectCommand());
		cmds.registerCommand(new ItemsCommand());
		cmds.registerCommand(new JourneyCommand());
		cmds.registerCommand(new PrefixCommand());
		cmds.registerCommand(new RepairCommand());
		cmds.registerCommand(new ScrapCommand());
		cmds.registerCommand(new StatsCommand());
		cmdHandler = new CommandHandler(cmds);
	}

	/**
	 * Initialize array of journeys
	 */
	private static void loadJourneys() {
		for (int i = 0; i < 20; i++) {
			Journey j = new RandomJourney(String.valueOf(i + 1));
			j.setLevel(i * 5);

			journeys.add(j);
		}

		Journey j = new RandomJourney("nevermore") {
			@Override
			public void setup() {
				super.setup();

				JourneyNode n = new JourneyNode();
				for (int b = 0; b < 5; b++) {
					Monster m = new Monster(Tier.fromLevel(getLevel()));

					Weapon weapon = m.getTier().getWeapon().getRandom();
					weapon.setDurability(ThreadLocalRandom.current().nextInt(1, weapon.getTier().getMaxDurability()));
					m.setWeapon(weapon);

					Armor armor = m.getTier().getArmor().getRandom();
					armor.setDurability(ThreadLocalRandom.current().nextInt(1, armor.getTier().getMaxDurability()));
					m.setArmor(armor);

					n.getMonsters().add(m);
				}

				Monster m = new Monster(Tier.fromLevel(getLevel()));

				Weapon weapon = new Weapon("Nevermore's Tooth", Tier.ADVANCED, Weapon.SWORD);
				weapon.setDurability(ThreadLocalRandom.current().nextInt(1, weapon.getTier().getMaxDurability()));
				weapon.setIceDmg(ThreadLocalRandom.current().nextInt(15, 40));
				weapon.setFireDmg(0);
				weapon.getDmg().setMin(ThreadLocalRandom.current().nextInt(25, 40));
				weapon.getDmg().setMax(weapon.getDmg().getMin() + ThreadLocalRandom.current().nextInt(20, 50));
				m.setWeapon(weapon);

				Armor armor = m.getTier().getArmor().getRandom();
				armor.setDurability(ThreadLocalRandom.current().nextInt(1, armor.getTier().getMaxDurability()));
				m.setArmor(armor);

				n.getMonsters().add(m);
			}
		};
		j.setLevel(16);

		journeys.add(j);
	}

	private static void loginClient() throws DiscordException, RateLimitException {
		client = new ClientBuilder().withToken(token).build();
		client.getDispatcher().registerListener(new DiscordRealms());
		client.login();
	}

	public static ScheduledExecutorService getScheduler() {
		return scheduler;
	}

	public static Player getPlayer(IUser u) {
		return players.stream().filter(p -> p.getUser().equals(u)).findAny().orElse(createPlayer(u));
	}

	public static Player createPlayer(IUser u) {
		Player p = new Player(u);
		players.add(p);
		return p;
	}

	public static void savePlayer(Player p) {
		try (FileWriter fw = new FileWriter("players/" + p.getUser().getID() + ".json")) {
			GSON.toJson(p.toSavedObject(), fw);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static IDiscordClient getClient() {
		return client;
	}

	/**
	 * Gets a journey by it's ID.
	 *
	 * @param id The ID.
	 * @return The journey.
	 */
	public static Journey getJourney(String id) {
		return journeys.stream().filter(j -> j.getID().equalsIgnoreCase(id)).findAny().orElse(null);
	}

	@EventSubscriber
	public void onReady(ReadyEvent event) throws RateLimitException, DiscordException {
		loadPlayerData();
		loadJourneys();
		client.getDispatcher().registerListener(cmdHandler);
		client.changeAvatar(Image.forUrl("jpg", "https://i.ytimg.com/vi/SlzTvDI_d7I/maxresdefault.jpg"));
	}
}

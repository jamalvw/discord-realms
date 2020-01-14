package com.oppailand.discordrealms.entity;

import com.oppailand.discordrealms.DiscordRealms;
import com.oppailand.discordrealms.commands.CommandSelection;
import com.oppailand.discordrealms.entity.armor.Armor;
import com.oppailand.discordrealms.entity.armor.Item;
import com.oppailand.discordrealms.entity.armor.Weapon;
import com.oppailand.discordrealms.journey.Journey;
import com.oppailand.discordrealms.journey.JourneyThread;
import com.oppailand.discordrealms.objects.*;
import com.oppailand.discordrealms.util.Helper;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Player extends Mob {
	private final IUser user;
	private final JourneyThread journeyThread = new JourneyThread(this);
	private List<Item> bank = new ArrayList<>();
	private int gems = 0;
	private int currExp = 0;
	private ScheduledFuture threadFuture;
	private String prefix = "";
	private CommandSelection select;
	private Party party;
	private Player partyInviter;

	public Player(IUser u) {
		user = u;
		setWeapon(new Weapon("Training Dagger", Tier.BASIC, Weapon.SWORD));
		getWeapon().getDmg().setMin(3);
		getWeapon().getDmg().setMax(5);
		setArmor(new Armor("Training Armor", Tier.BASIC));
		getArmor().setHp(38);
		getArmor().setHpRegen(3);
	}

	public Player(PlayerObject o) {
		super(o);
		user = DiscordRealms.getClient().getUserByID(o.user_id);
		bank = Arrays.stream(o.bank).map(i -> {
			if (i.object_type.equals(BaseObject.Type.WEAPON))
				return new Weapon((WeaponObject) i);
			else if (i.object_type.equals(BaseObject.Type.ARMOR))
				return new Armor((ArmorObject) i);
			else
				return new Item(i);
		}).collect(Collectors.toList());
		gems = o.gems;
		currExp = o.curr_exp;
		prefix = o.prefix;
	}

	public IUser getUser() {
		return user;
	}

	public int getCurrentExp() {
		return currExp;
	}

	public void addExp(int exp) {
		currExp += exp;
		boolean leveled = false;
		while (currExp >= Helper.calcMaxExp(getLevel())) {
			currExp -= Helper.calcMaxExp(getLevel());
			incLevel();
			leveled = true;
		}
		if (leveled) {
			EmbedBuilder builder = new EmbedBuilder();
			builder.withTitle("New Level Reached");
			builder.withColor(Color.MAGENTA);
			builder.withAuthorName(user.getName());
			builder.withAuthorIcon(user.getAvatarURL());
			builder.withDesc("You are now level " + (getLevel() + 1) + "!");
			builder.withTimestamp(LocalDateTime.now());
			try {
				user.getOrCreatePMChannel().sendMessage("", builder.build(), false);
			} catch (RateLimitException | MissingPermissionsException | DiscordException e1) {
				e1.printStackTrace();
			}
		}
		DiscordRealms.savePlayer(this);
	}

	public boolean isOnJourney() {
		return journeyThread.getJourney() != null;
	}

	public Journey getJourney() {
		return journeyThread.getJourney();
	}

	public void setJourney(Journey j) {
		setSelection(null);
		journeyThread.setJourney(j);
		if (j != null) {
			threadFuture = DiscordRealms.getScheduler().scheduleAtFixedRate(journeyThread, 10, 10, TimeUnit.SECONDS);
		} else if (threadFuture != null) {
			threadFuture.cancel(false);
			threadFuture = null;
		}
	}

	public void endJourney() {
		setJourney(null);
	}

	public JourneyThread getJourneyThread() {
		return journeyThread;
	}

	public int getJourneyNode() {
		return journeyThread.getNode();
	}

	public int getGems() {
		return gems;
	}

	public void setGems(int gems) {
		this.gems = gems;
		DiscordRealms.savePlayer(this);
	}

	public void addGems(int g) {
		gems += g;
		DiscordRealms.savePlayer(this);
	}

	public void addItem(Item i) {
		bank.add(i);
		DiscordRealms.savePlayer(this);
	}

	public void addItems(Collection<Item> items) {
		bank.addAll(items);
		DiscordRealms.savePlayer(this);
	}

	public List<Item> getBank() {
		return bank;
	}

	public String getPrefix() {
		return prefix.isEmpty() ? DiscordRealms.DEFAULT_PREFIX : prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
		DiscordRealms.savePlayer(this);
	}

	public CommandSelection getCommandSelection() {
		return select;
	}

	public void setSelection(CommandSelection s) {
		select = s;
	}

	public boolean hasCommandSelection() {
		return select != null;
	}

	@Override
	public int getMaxHp() {
		return 50 + super.getMaxHp();
	}

	@Override
	public int getHpRegen() {
		return 5 + super.getHpRegen();
	}

	@Override
	public PlayerObject toSavedObject() {
		PlayerObject o = new PlayerObject(super.toSavedObject());
		o.user_id = user.getID();
		o.bank = bank.stream().map(Item::toSavedObject).collect(Collectors.toList())
				.toArray(new ItemObject[bank.size()]);
		o.gems = gems;
		o.curr_exp = currExp;
		o.prefix = prefix;
		return o;
	}

	public int getSwings() {
		return 10; //- (hasWeapon() ? getWeapon().getEnergyUsage() : 0) + getEnergyRegen();
	}

	public Party getParty() {
		return party;
	}

	public void setParty(Party party) {
		this.party = party;
	}

	/**
	 * Returns a list containing the player and their party members if they are in one.
	 *
	 * @return The player's current group.
	 */
	public List<Player> getCurrentGroup() {
		List<Player> players = new ArrayList<>();
		if (isInParty()) players.addAll(party.getMembers());
		else players.add(this);
		return players;
	}

	public boolean isInParty() {
		return party != null;
	}

	public Player getPartyInviter() {
		return partyInviter;
	}

	public void setPartyInviter(Player p) {
		partyInviter = p;
	}

	public boolean hasPartyInviter() {
		return partyInviter != null;
	}

	@Override
	public String toString() {
		return user.getName();
	}
}
package com.oppailand.discordrealms.commands.game;

import com.oppailand.discordrealms.commands.Command;
import com.oppailand.discordrealms.entity.Player;
import com.oppailand.discordrealms.entity.armor.Armor;
import com.oppailand.discordrealms.entity.armor.Item;
import com.oppailand.discordrealms.entity.armor.Weapon;
import com.oppailand.discordrealms.util.Helper;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

/**
 * Created by Silvre on 3/15/2017.
 * Project: discordrealms
 * If you are reading this - you can read this
 */
public class RepairCommand implements Command {

	//TODO Fancy it up with embed messages instead? Open for discussion.
	//TODO also open for discussion : Should players be allowed to repair while on journeys?
	@Override
	public void execute(String alias, String[] args, Player p, IMessage msg) throws DiscordException, MissingPermissionsException, RateLimitException {
		if (args.length == 0 || args.length > 2) return;
		else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("weapon") && p.getWeapon().getDurability() == p.getWeapon().getTier().getMaxDurability())
				msg.getChannel().sendMessage(":white_check_mark: Your " + p.getWeapon().getName() + " is already at full durability!");
			else if (args[0].equalsIgnoreCase("armor") && p.getArmor().getDurability() == p.getArmor().getTier().getMaxDurability())
				msg.getChannel().sendMessage(":white_check_mark: Your " + p.getArmor().getName() + " is already at full durability!");
			else if (args[0].equalsIgnoreCase("weapon"))
				msg.getChannel().sendMessage("You currently equipped weapon costs " + Helper.getRepairCost(p.getWeapon()) + "g to repair.");
			else if (args[0].equalsIgnoreCase("armor"))
				msg.getChannel().sendMessage("You currently equipped armor costs " + Helper.getRepairCost(p.getArmor()) + "g to repair.");
			else {
				int index;
				try {
					index = Integer.parseInt(args[0]);
				} catch (Exception e) {
					msg.getChannel().sendMessage(":no_entry_sign: Invalid slot.");
					return;
				}
				if (index >= p.getBank().size() || index < 0) {
					msg.getChannel().sendMessage(":no_entry_sign: Invalid slot.");
					return;
				}
				Item i = p.getBank().get(index);
				if (i instanceof Armor && ((Armor) i).getDurability() < ((Armor) i).getTier().getMaxDurability())
					msg.getChannel().sendMessage("Your " + i.getName() + "costs " + Helper.getRepairCost((Armor) i) + "g to repair.");
				else if (i instanceof Weapon && ((Weapon) i).getDurability() < (((Weapon) i).getTier().getMaxDurability()))
					msg.getChannel().sendMessage("Your " + i.getName() + "costs " + Helper.getRepairCost((Weapon) i) + "g to repair.");
				else if (i instanceof Armor && ((Armor) i).getDurability() == ((Armor) i).getTier().getMaxDurability())
					msg.getChannel().sendMessage(":white_check_mark: Your " + i.getName() + " is already at full durability!");
				else if (i instanceof Weapon && ((Weapon) i).getDurability() < (((Weapon) i).getTier().getMaxDurability()))
					msg.getChannel().sendMessage(":white_check_mark: Your " + i.getName() + " is already at full durability!");
				else msg.getChannel().sendMessage(":no_entry_sign: You cannot repair this item.");
			}
		} else if (args.length == 2) {
			if (args[0].equalsIgnoreCase("weapon") && args[1].equalsIgnoreCase("y") && p.getWeapon().getDurability() == p.getWeapon().getTier().getMaxDurability())
				msg.getChannel().sendMessage(":white_check_mark: Your " + p.getWeapon().getName() + " is already at full durability!");
			else if (args[0].equalsIgnoreCase("armor") && args[1].equalsIgnoreCase("y") && p.getArmor().getDurability() == p.getArmor().getTier().getMaxDurability())
				msg.getChannel().sendMessage(":white_check_mark: Your " + p.getArmor().getName() + " is already at full durability!");
			else if (args[0].equalsIgnoreCase("weapon") && args[1].equalsIgnoreCase("y") && Helper.getRepairCost(p.getWeapon()) <= p.getGems()) {
				int cost = Helper.getRepairCost(p.getWeapon());
				p.setGems(p.getGems() - Helper.getRepairCost(p.getWeapon()));
				p.getWeapon().setDurability(p.getWeapon().getTier().getMaxDurability());
				msg.getChannel().sendMessage(":white_check_mark: Successfully repaired " + p.getWeapon().getName() + ".\n\t\t***-" + cost + "G***");
			} else if (args[0].equalsIgnoreCase("weapon") && args[1].equalsIgnoreCase("y") && Helper.getRepairCost(p.getWeapon()) > p.getGems()) {
				int cost = Helper.getRepairCost(p.getWeapon());
				msg.getChannel().sendMessage(":no_entry_sign: You do not have enough gems to repair your " +
						p.getWeapon().getName() + ":\t\t***" +
						cost + "G***");
			} else if (args[0].equalsIgnoreCase("armor") && args[1].equalsIgnoreCase("y") && Helper.getRepairCost(p.getArmor()) <= p.getGems()) {
				int cost = Helper.getRepairCost(p.getArmor());
				p.setGems(p.getGems() - Helper.getRepairCost(p.getArmor()));
				p.getArmor().setDurability(p.getArmor().getTier().getMaxDurability());
				msg.getChannel().sendMessage(":white_check_mark: Successfully repaired " + p.getArmor().getName() + ".\n\t\t***-" + cost + "G***");
			} else if (args[0].equalsIgnoreCase("armor") && args[1].equalsIgnoreCase("y") && Helper.getRepairCost(p.getArmor()) > p.getGems()) {
				int cost = Helper.getRepairCost(p.getArmor());
				msg.getChannel().sendMessage(":no_entry_sign: You do not have enough gems to repair your " +
						p.getArmor().getName() + ":\t\t***" +
						cost + "G***");
			} else {
				int index;
				try {
					index = Integer.parseInt(args[0]);
				} catch (Exception e) {
					msg.getChannel().sendMessage(":no_entry_sign: Invalid slot.");
					return;
				}
				if (index >= p.getBank().size() || index < 0) {
					msg.getChannel().sendMessage(":no_entry_sign: Invalid slot.");
					return;
				}
				Item i = p.getBank().get(index);
				if (i instanceof Weapon && args[1].equalsIgnoreCase("y") && ((Weapon) i).getDurability() == ((Weapon) i).getTier().getMaxDurability())
					msg.getChannel().sendMessage(":white_check_mark: Your " + i.getName() + " is already at full durability!");
				else if (i instanceof Armor && args[1].equalsIgnoreCase("y") && ((Armor) i).getDurability() == ((Armor) i).getTier().getMaxDurability())
					msg.getChannel().sendMessage(":white_check_mark: Your " + i.getName() + " is already at full durability!");
				if (i instanceof Armor && args[1].equalsIgnoreCase("y") && Helper.getRepairCost((Armor) i) <= p.getGems()) {
					int cost = Helper.getRepairCost((Armor) i);
					p.setGems(p.getGems() - Helper.getRepairCost((Armor) i));
					((Armor) i).setDurability(((Armor) i).getTier().getMaxDurability());
					msg.getChannel().sendMessage(":white_check_mark: Successfully repaired " + i.getName() + ".\n\t\t***-" + cost + "G***");
				} else if (i instanceof Armor && args[1].equalsIgnoreCase("y") && Helper.getRepairCost((Armor) i) > p.getGems()) {
					msg.getChannel().sendMessage(":no_entry_sign: You do not have enough gems to repair your " +
							i.getName() + ":\t\t ***" +
							Helper.getRepairCost((Armor) i) + "G***");
				} else if (i instanceof Weapon && args[1].equalsIgnoreCase("y") && Helper.getRepairCost((Weapon) i) <= p.getGems()) {
					int cost = Helper.getRepairCost((Weapon) p.getBank().get(index));
					p.setGems(p.getGems() - Helper.getRepairCost((Weapon) i));
					((Weapon) i).setDurability(((Weapon) i).getTier().getMaxDurability());
					msg.getChannel().sendMessage(":white_check_mark: Successfully repaired " + i.getName() + ".\n\t\t***-" + cost + "G***");
				} else if (i instanceof Weapon && args[1].equalsIgnoreCase("y") && Helper.getRepairCost((Weapon) i) > p.getGems()) {
					msg.getChannel().sendMessage(":no_entry_sign: You do not have enough gems to repair your " +
							i.getName() + ":\t\t ***" +
							Helper.getRepairCost((Weapon) i) + "G***");
				} else msg.getChannel().sendMessage(":no_entry_sign: You cannot repair this item.");
			}
		}
	}

	@Override
	public String getName() {
		return "repair";
	}

	@Override
	public String[] getAliases() {
		return new String[]{"repair", "fix", "fuckingrepairmate"};
	}

	@Override
	public String getDescription() {
		return "Repair equipment and also check the prices to repair weapons/armor.";
	}

	@Override
	public String getUsage() {
		return "repair <weapon|armor> : Display repair costs of the currently equipped weapon / armor.\n" +
				"repair <weapon|armor> <y/n> : Repair the equipped weapon / armor.\n" +
				"repair <id> : Display repair costs of the item with the associated ID. \n" +
				"repair <id> <y/n> : Repair the item with the associated ID.";
	}
}

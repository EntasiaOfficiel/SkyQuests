package fr.entasia.questcore.api;

import fr.entasia.apis.nbt.ItemNBT;
import fr.entasia.apis.nbt.NBTComponent;
import fr.entasia.questcore.Main;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class QuestUtils {

	public static ConfigurationSection getSection(Player p, int id) {
		return getSection(p.getName(), id);
	}
	public static ConfigurationSection getSection(String name, int id) {
		ConfigurationSection sec = Main.main.getConfig().getConfigurationSection("quests."+name+"."+id);
//		if(sec==null){
//			sec = Main.main.getConfig().createSection("quests."+name+"."+id);
//		}
		return sec;
	}

	public static void delSection(Player p, int id) {
		delSection(p.getName(), id);
	}

	public static void delSection(String name, int id) {
		Main.main.getConfig().set("quests." + name + "." + id, null);
	}

	public static void markQItem(ItemStack item, int questID, int itemID){
		ItemNBT.addNBT(item, new NBTComponent(String.format("{quest:1,questid:%d,itemid:%d}", questID, itemID)));
	}

	public static int getQItemID(ItemStack item, int questID) {
		NBTComponent nbt = ItemNBT.getNBTSafe(item);
		if ("1".equals(nbt.getKeyString("quest"))) {
			if (questID == -1 || Integer.parseInt(nbt.getKeyStringSafe("questid")) == questID) {
				return Integer.parseInt(nbt.getKeyStringSafe("itemid"));
			}
		}
		return -1;
	}

	public static ArrayList<ItemStack> getQItem(Player p, int questID, int itemID){
		ArrayList<ItemStack> list = new ArrayList<>();
		for(ItemStack item : p.getInventory().getContents()){
			if(getQItemID(item, questID)==itemID) list.add(item);
		}
		return list;
	}

	public static void delQItem(Player p, int questID, int itemID){
		for(ItemStack item : p.getInventory().getContents()){
			if(getQItemID(item, questID)==itemID){
				p.getInventory().remove(item);
			}
		}
	}

	public static void delAllQItem(Player p, int questID){
		for(ItemStack item : p.getInventory().getContents()){
			NBTComponent nbt = ItemNBT.getNBTSafe(item);
			if("1".equals(nbt.getKeyString("quest"))) {
				String s = nbt.getKeyString("questid");
				if(s==null)continue;
				if(Integer.parseInt(s) == questID) {
					p.getInventory().remove(item);
				}
			}
		}
	}
}
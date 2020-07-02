package fr.entasia.questcore;

import fr.entasia.apis.nbt.ItemNBT;
import fr.entasia.apis.nbt.NBTComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Utils {

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

	public static void markQuestItem(Player p, ItemStack item, int questID, int itemID){
		ItemNBT.addNBT(item, new NBTComponent(String.format("{quest:1,questid:%d,itemid:%d}", questID, itemID)));
	}

	public static int getItemID(ItemStack item, int questID){
		NBTComponent nbt = ItemNBT.getNBTSafe(item);
		if("1".equals(nbt.getKeyString("quest"))){
			if(questID==-1||Integer.parseInt(nbt.getKeyStringSafe("questid"))==questID){
				return Integer.parseInt(nbt.getKeyStringSafe("itemid"));
			}
		}
		return -1;
	}

	public static ArrayList<ItemStack> getQuestItems(Player p, int questID, int itemID){
		NBTComponent nbt;
		ArrayList<ItemStack> list = new ArrayList<>();
		for(ItemStack item : p.getInventory().getContents()){
			if(getItemID(item, questID)==itemID) list.add(item);
		}
		return list;
	}
}
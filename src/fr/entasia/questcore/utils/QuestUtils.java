package fr.entasia.questcore.utils;

import fr.entasia.apis.nbt.ItemNBT;
import fr.entasia.apis.nbt.NBTComponent;
import fr.entasia.apis.nbt.NBTTypes;
import fr.entasia.questcore.Main;
import fr.entasia.questcore.utils.enums.Quests;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class QuestUtils {

	public static QuestSection getSection(Player p, Quests quest) {
		return getSection(p.getName(), quest);
	}
	public static QuestSection getSection(String name, Quests quest) {
		QuestSection qs = new QuestSection();
		qs.sec = Main.main.getConfig().getConfigurationSection("quests."+name+"."+quest.id);
		if(qs.sec==null){ // why c'était commenté ?
			qs.sec = Main.main.getConfig().createSection("quests."+name+"."+quest.id);
		}
		return qs;
	}

	public static void delSection(Player p, Quests quest) {
		delSection(p.getName(), quest);
	}

	public static void delSection(String name, Quests quest) {
		Main.main.getConfig().set("quests." + name + "." + quest.id, null);
	}

	public static void markQItem(ItemStack item, int questID, int itemID){
		ItemNBT.addNBT(item, new NBTComponent(String.format("{quest:1,questid:%d,itemid:%d}", questID, itemID)));
	}

	public static int getQItemID(ItemStack item, Quests quest) {
		NBTComponent nbt = ItemNBT.getNBTSafe(item);
		if ("1".equals(nbt.getValue(NBTTypes.String, "quest"))) {
			if (quest.id != -1 || (int)nbt.getValue(NBTTypes.Int, "questid") == quest.id) {
				return (int) nbt.getValue(NBTTypes.Int, "itemid");
			}
		}
		return -1;
	}

	public static ArrayList<ItemStack> getQItem(Player p, Quests quest, int itemID){
		ArrayList<ItemStack> list = new ArrayList<>();
		for(ItemStack item : p.getInventory().getContents()){
			if(getQItemID(item, quest)==itemID) list.add(item);
		}
		return list;
	}

	public static void delQItem(Player p, Quests quest, int itemID){
		for(ItemStack item : p.getInventory().getContents()){
			if(getQItemID(item, quest)==itemID){
				p.getInventory().remove(item);
			}
		}
	}

	public static void delAllQItem(Player p, int questID){
		for(ItemStack item : p.getInventory().getContents()){
			NBTComponent nbt = ItemNBT.getNBTSafe(item);
			if(nbt.getValue(NBTTypes.String, "quest").equals("1")) {
				String s = (String) nbt.getValue(NBTTypes.String, "questid");
				if(s==null)continue;
				if(Integer.parseInt(s) == questID) {
					p.getInventory().remove(item);
				}
			}
		}
	}
}
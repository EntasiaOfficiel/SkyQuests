package fr.entasia.questcore.utils;

import fr.entasia.apis.nbt.ItemNBT;
import fr.entasia.apis.nbt.NBTComponent;
import fr.entasia.apis.nbt.NBTTypes;
import fr.entasia.questcore.Main;
import fr.entasia.questcore.utils.enums.Quests;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QuestUtils {


	private static void createSection(String name, Quests quest) {
		Main.sqlite.fastUpdate("INSERT INTO actives (player, id, when) VALUES(?, ?, ?)", name, quest.id, System.currentTimeMillis());
	}

	public static void delSection(String name, Quests quest) {
		Main.sqlite.fastUpdate("DELETE FROM actives WHERE player=? AND WHERE id=?", name, quest.id);
	}


	public static int getProgress(String name, Quests quest){
		try{
			ResultSet rs = Main.sqlite.fastSelectUnsafe("SELECT progress FROM actives WHERE player=? AND id=?", name, quest.id);
			if(rs.next()){
				return rs.getInt(1);
			}else{
				createSection(name, quest);
				return 0;
			}
		}catch(SQLException e){
			e.printStackTrace();
			return -1;
		}
	}

	public static void setProgress(String name, Quests quest, int progress){
		Main.sqlite.fastUpdate("UPDATE actives SET progress=? WHERE player=? AND id=?", progress, name, quest.id);
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

	public static void delAllQItems(Player p, int questID){
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
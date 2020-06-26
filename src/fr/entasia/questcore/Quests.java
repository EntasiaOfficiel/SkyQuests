package fr.entasia.questcore;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public enum Quests {
	A(1, "Bob", "Suzie"){
		@Override
		public void onNPCClick(Player p, String name) {
			if(name.equals("Bob")){
				p.sendMessage("§aSalut, je suis Bob !");
			}else{
				p.sendMessage("§aC'est bob qui t'envoit ? Qu'il est mignon !");
			}
		}
	},
	B(2, "Suzie"),
	SECRET(3, "Thibault"){
		@Override
		public void onEvent(Player p, int id) {
			if(id==0){
				p.sendMessage("§cTu as trouvé la zone de secrète ! Ton colonnel est fier de toi");
				p.sendMessage("§cMaintenant, fait la exploser !");
				p.getInventory().addItem(new ItemStack(Material.TNT, 5));
			}
		}
		@Override
		public void onNPCClick(Player p, String name) {
			p.sendMessage("§cSalut, j'ai trouvé une base militaire ennemie près d'ici. J'ai besoin que tu y aille, et que tu la détruise");
			p.sendMessage("§cElle est située près du Grand Chêne, a gauche de L'île à Moteurs");
		}
	},
	E(4, "Ichii"){
		public void onNPCClick(Player p, String name) {
			p.sendMessage("§cYEET");
		}
	},
	;


	public String[] npcs;
	private int quest_id;

	Quests(int quest_id, String... npcs){
		this.npcs = npcs;
		this.quest_id = quest_id;
	}

	public void onEvent(Player p, int id) { // can be abstract, better if no default interaction
		Bukkit.broadcastMessage("This is the default event trigger (Event ID:"+id);
	}


	public void onNPCClick(Player p, String name){ // can be abstract, better if no default interaction
		Bukkit.broadcastMessage("This is the default interaction code with NPCs");
	}

	public static Quests getQuestByID(int id) {
		for (Quests q: Quests.values()) {
			if (q.quest_id==id) return q;
		}
		return null;
	}

}

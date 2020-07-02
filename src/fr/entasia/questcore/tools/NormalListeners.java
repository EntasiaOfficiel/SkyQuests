package fr.entasia.questcore.tools;

import fr.entasia.apis.regionManager.api.Region;
import fr.entasia.apis.regionManager.api.RegionManager;
import fr.entasia.apis.regionManager.events.RegionEnterEvent;
import fr.entasia.apis.utils.ItemUtils;
import fr.entasia.questcore.Utils;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class NormalListeners implements Listener {

    /*
    Bloc note :

    Quête 0 : officier -> faire sauter une base
     */

	public static Region zoneSecrete = RegionManager.getRegionByName("zone_secrete");

	@EventHandler
	public void a(PlayerInteractEntityEvent e){
		if(e.getRightClicked().getType()== EntityType.VILLAGER){
			String name = e.getRightClicked().getCustomName();
			if(name==null||name.equals(""))return;
			Player p = e.getPlayer();
			int progress;
			switch(name){
				case "Offier":{
					ConfigurationSection sec = Utils.getSection(p, 0);
					progress = sec.getInt("progress");
					if(progress==0){
						p.sendMessage("§cAu rapport, soldat ! On vient de me signaler la présence d'une base militaire ennemie !");
						p.sendMessage("§cJ'ai besoin de toi pour aller la détruire. Il n'y a qu'un problème.. j'ignore ou elle se trouve.");
						p.sendMessage("§cElle est quelque part dans le §6Spawn§c. peux-tu la trouver, et la §4détruire§c ?");
						sec.set("progress", 1);
					}else if(progress==1){
						p.sendMessage("§cQue fait-tu encore la soldat ? Va donc détruire cette base !");
						p.sendMessage("§cElle est quelque part dans le §6Spawn§c.");
					}else if(progress==2){
						p.sendMessage("§cBon sang, mais détruit donc cette base !");
						p.sendMessage("§cJe t'ai donné une §4TNT§c, il te suffit de la poser à la base !");
					}else if(progress==3){
						p.sendMessage("§cBravo soldat ! La base est détruite, voici ta récompense :");
						ItemUtils.giveOrDrop(p, new ItemStack(Material.DIAMOND, 4));
						Utils.delSection(p, 0);
						// TODO coodown de quête
					}
					break;
				}
			}
		}
	}

//    CUSTOM

	@EventHandler
	public void a(RegionEnterEvent e){
		if(e.getRegion()==zoneSecrete){
			ConfigurationSection sec = Utils.getSection(e.getPlayer(), 0);
			if(sec.getInt("progress")==1||sec.getInt("progress")==2){
				Player p = e.getPlayer();
				if(Utils.getQuestItems(p, 0, 0).size()==0){
					p.sendMessage("§cBien joué ! Tu as trouvé la base militaire ! Détruit la maintenant ! (pose la tnt)");
					ItemStack item = new ItemStack(Material.TNT);
					Utils.markQuestItem(p, item, 0, 0);
					p.getInventory().addItem(item);
					sec.set("progress", 2);
				}
			}
		}
	}

	@EventHandler
	public void a(BlockPlaceEvent e){
		if(e.getBlock().getType()==Material.TNT&&Utils.getItemID(e.getItemInHand(), 0)==0){
			ConfigurationSection sec = Utils.getSection(e.getPlayer(), 0);
			sec.set("progress", 3);
			// TODO explosion feux d'artifice
			e.getPlayer().sendMessage("§c§lBOUM ! §cTu as réussi soldat ! Reviens vite pour voir pour ta récompense !");
		}
	}
}

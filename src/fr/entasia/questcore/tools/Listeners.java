package fr.entasia.questcore.tools;

import com.destroystokyo.paper.event.entity.EntityPathfindEvent;
import fr.entasia.apis.other.InstantFirework;
import fr.entasia.apis.regionManager.api.RegionManager;
import fr.entasia.apis.regionManager.events.RegionEnterEvent;
import fr.entasia.apis.utils.ItemUtils;
import fr.entasia.questcore.Main;
import fr.entasia.questcore.utils.QuestUtils;
import fr.entasia.questcore.utils.enums.Quests;
import fr.entasia.questcore.utils.enums.Regions;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class Listeners implements org.bukkit.event.Listener {

    /*
    Bloc note :

    Quête 0 : officier -> faire sauter une base
     */

	@EventHandler
	public void a(PlayerInteractEntityEvent e){
		if(e.getRightClicked().getType()== EntityType.VILLAGER){
			String name = e.getRightClicked().getCustomName();
			if(name==null||name.equals(""))return;
			Player p = e.getPlayer();
			int progress;
			switch(name){
				case "Offier":{
					progress = QuestUtils.getProgress(p.getName(), Quests.BASE_SECRETE);
					if(progress==0){
						p.sendMessage("§cAu rapport, soldat ! On vient de me signaler la présence d'une base militaire ennemie !");
						p.sendMessage("§cJ'ai besoin de toi pour aller la détruire. Il n'y a qu'un problème.. j'ignore ou elle se trouve.");
						p.sendMessage("§cElle est quelque part dans le §6Spawn§c. peux-tu la trouver, et la §4détruire§c ?");
						QuestUtils.setProgress(p.getName(), Quests.BASE_SECRETE, 1);
					}else if(progress==1){
						p.sendMessage("§cQue fait-tu encore la soldat ? Va donc détruire cette base !");
						p.sendMessage("§cElle est quelque part dans le §6Spawn§c.");
					}else if(progress==2){
						p.sendMessage("§cBon sang, mais détruit donc cette base !");
						p.sendMessage("§cJe t'ai donné une §4TNT§c, il te suffit de la poser à la base !");
					}else if(progress==3){
						p.sendMessage("§cBravo soldat ! La base est détruite, voici ta récompense :");
						ItemUtils.giveOrDrop(p, new ItemStack(Material.DIAMOND, 4));
						QuestUtils.delSection(p.getName(), Quests.BASE_SECRETE);
						// TODO coodown de quête
					}
					break;
				}
				case "Combattant":{
					progress = QuestUtils.getProgress(p.getName(), Quests.MINEURS);
					if(progress==0){
						p.sendMessage("§cJ'ai besoin que tu apporte ca à des mineurs. Allez va y, basta");
						QuestUtils.setProgress(p.getName(), Quests.MINEURS, 1);
					}else if(progress==1){
						p.sendMessage("§cAllez, va y !");
					}
					break;
				}
				case "Mineur":{
					progress = QuestUtils.getProgress(p.getName(), Quests.MINEURS);
					if(progress==1) {
						p.sendMessage("§cThx !");
						QuestUtils.delSection(p.getName(), Quests.MINEURS);
					}else{
						p.sendMessage("§cBonjour !");
					}
					break;
				}
			}
		}
	}

//    CUSTOM

	@EventHandler
	public void a(RegionEnterEvent e){
		Player p = e.getPlayer();
		if(e.getRegion()==Regions.BASE_SECRETE.region){
			int progress = QuestUtils.getProgress(p.getName(), Quests.MINEURS);
			if(progress==1||progress==2){
				if(QuestUtils.getQItem(p, Quests.BASE_SECRETE, 0).size()==0){
					p.sendMessage("§cBien joué ! Tu as trouvé la base militaire ! Détruit la maintenant ! (pose la tnt)");
					ItemStack item = new ItemStack(Material.TNT);
					QuestUtils.markQItem(item, 0, 0);
					p.getInventory().addItem(item);
					QuestUtils.setProgress(p.getName(), Quests.MINEURS, 2);
				}
			}
		}else if(e.getRegion()==Regions.MINE_PVE.region){
			p.setHealth(e.getPlayer().getMaxHealth());

			int progress = QuestUtils.getProgress(p.getName(), Quests.MINEURS);
			if(progress==1){
				p.sendMessage("§cTu entres dans une zone dangereuse, attention ! Les mineurs se sont peut-être retrouvés piégés ici ?");
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void a(EntityDamageByEntityEvent e){
		if(!(e.getEntity() instanceof Player && e.getDamager() instanceof Player)){
			if(RegionManager.getRegionsAt(e.getEntity().getLocation()).contains(Regions.MINE_PVE.region)) {
				if (RegionManager.getRegionsAt(e.getDamager().getLocation()).contains(Regions.MINE_PVE.region)) {
					e.setCancelled(false);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public static void antiSpawn(EntitySpawnEvent e){
		if(e.getLocation().getWorld()== Main.world){
			if (RegionManager.getRegionsAt(e.getLocation()).contains(Regions.MINE_PVE.region)) {
				e.setCancelled(false);
			}
		}
	}


	@EventHandler
	public void a(EntityPathfindEvent e) {
		if (!RegionManager.getRegionsAt(e.getLoc()).contains(Regions.MINE_PVE.region)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void a(BlockPlaceEvent e) {
		if (e.getBlock().getType() == Material.TNT && QuestUtils.getQItemID(e.getItemInHand(), Quests.BASE_SECRETE) == 0) {
			int progress = QuestUtils.getProgress(e.getPlayer().getName(), Quests.MINEURS);
			if (progress == 1 || progress == 2) {
				QuestUtils.setProgress(e.getPlayer().getName(), Quests.MINEURS, 3);
				QuestUtils.delQItem(e.getPlayer(), Quests.BASE_SECRETE, 0);
				InstantFirework.explode(e.getPlayer().getLocation(), FireworkEffect.builder().withColor(Color.BLUE, Color.GREEN).build());
				e.getPlayer().sendMessage("§c§lBOUM ! §cTu as réussi soldat ! Reviens vite pour voir pour ta récompense !");
			}
		}
	}
}

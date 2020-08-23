package fr.entasia.questcore.tools;

import fr.entasia.apis.regionManager.api.RegionManager;
import fr.entasia.apis.regionManager.events.RegionEnterEvent;
import fr.entasia.apis.utils.ItemUtils;
import fr.entasia.questcore.utils.QuestSection;
import fr.entasia.questcore.utils.QuestUtils;
import fr.entasia.questcore.utils.enums.Quests;
import fr.entasia.questcore.utils.enums.Regions;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class NormalListeners implements Listener {

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
					QuestSection qs = QuestUtils.getSection(p, Quests.BASE_SECRETE);
					progress = qs.getProgress();
					if(progress==0){
						p.sendMessage("§cAu rapport, soldat ! On vient de me signaler la présence d'une base militaire ennemie !");
						p.sendMessage("§cJ'ai besoin de toi pour aller la détruire. Il n'y a qu'un problème.. j'ignore ou elle se trouve.");
						p.sendMessage("§cElle est quelque part dans le §6Spawn§c. peux-tu la trouver, et la §4détruire§c ?");
						qs.setProgress(1);
					}else if(progress==1){
						p.sendMessage("§cQue fait-tu encore la soldat ? Va donc détruire cette base !");
						p.sendMessage("§cElle est quelque part dans le §6Spawn§c.");
					}else if(progress==2){
						p.sendMessage("§cBon sang, mais détruit donc cette base !");
						p.sendMessage("§cJe t'ai donné une §4TNT§c, il te suffit de la poser à la base !");
					}else if(progress==3){
						p.sendMessage("§cBravo soldat ! La base est détruite, voici ta récompense :");
						ItemUtils.giveOrDrop(p, new ItemStack(Material.DIAMOND, 4));
						QuestUtils.delSection(p, Quests.BASE_SECRETE);
						// TODO coodown de quête
					}
					break;
				}
				case "Combattant":{
					QuestSection qs = QuestUtils.getSection(p, Quests.MINEURS);
					progress = qs.getProgress();
					if(progress==0){
						p.sendMessage("§cJ'ai besoin que tu apporte ca à des mineurs. Allez va y, basta");
						qs.setProgress(1);
					}else if(progress==1){
						p.sendMessage("§cAllez, va y !");
					}else if(progress==2){
						p.sendMessage("§cBon sang, mais détruit donc cette base !");
						p.sendMessage("§cJe t'ai donné une §4TNT§c, il te suffit de la poser à la base !");
					}else if(progress==3){
						p.sendMessage("§cBravo soldat ! La base est détruite, voici ta récompense :");
						ItemUtils.giveOrDrop(p, new ItemStack(Material.DIAMOND, 4));
						QuestUtils.delSection(p, Quests.MINEURS);
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
		Player p = e.getPlayer();
		if(e.getRegion()==Regions.BASE_SECRETE.region){
			QuestSection qs = QuestUtils.getSection(p, Quests.BASE_SECRETE);
			if(qs.getProgress()==1||qs.getProgress()==2){
				if(QuestUtils.getQItem(p, Quests.BASE_SECRETE, 0).size()==0){
					p.sendMessage("§cBien joué ! Tu as trouvé la base militaire ! Détruit la maintenant ! (pose la tnt)");
					ItemStack item = new ItemStack(Material.TNT);
					QuestUtils.markQItem(item, 0, 0);
					p.getInventory().addItem(item);
					qs.setProgress(2);
				}
			}
		}else if(e.getRegion()==Regions.MINE_PVE.region){
			p.setHealth(e.getPlayer().getMaxHealth());
			QuestSection qs = QuestUtils.getSection(p, Quests.MINEURS);
			if(qs.getProgress()==1){
				p.sendMessage("§cTu entres dans une zone dangereuse, attention ! Les mineurs se sont peut-être retrouvés piégés ici ?");
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void a(EntityDamageByEntityEvent e){
		if(!(e.getEntity() instanceof Player && e.getDamager() instanceof Player)){
			if(RegionManager.getRegionsAt(e.getEntity().getLocation()).contains(Regions.MINE_PVE.region)){
				e.setCancelled(false);
			}
		}
	}

	@EventHandler
	public void a(EntityTargetEvent e){
		e.getEntity().sou
		if(!(e.getEntity() instanceof Player && e.getDamager() instanceof Player)){
			if(RegionManager.getRegionsAt(e.getEntity().getLocation()).contains(Regions.MINE_PVE.region)){
				e.setCancelled(false);
			}
		}
	}

	@EventHandler
	public void a(BlockPlaceEvent e) {
		if (e.getBlock().getType() == Material.TNT && QuestUtils.getQItemID(e.getItemInHand(), Quests.BASE_SECRETE) == 0) {
			QuestSection qs = QuestUtils.getSection(e.getPlayer(), Quests.BASE_SECRETE);
			if (qs.getProgress() == 1 || qs.getProgress() == 2) {
				qs.setProgress(3);
				QuestUtils.delQItem(e.getPlayer(), Quests.BASE_SECRETE, 0);
				// TODO explosion feux d'artifice
				e.getPlayer().sendMessage("§c§lBOUM ! §cTu as réussi soldat ! Reviens vite pour voir pour ta récompense !");
			}
		}
	}
}

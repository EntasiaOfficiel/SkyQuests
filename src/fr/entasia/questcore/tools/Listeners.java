package fr.entasia.questcore.tools;

import com.destroystokyo.paper.event.entity.EntityPathfindEvent;
import fr.entasia.apis.other.InstantFirework;
import fr.entasia.apis.other.ItemBuilder;
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
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

public class Listeners implements org.bukkit.event.Listener {

    /*
    Bloc note :

    Quête 0 : officier -> faire sauter une base
     */

	@EventHandler
	public void a(PlayerInteractEntityEvent e){
		if(e.getHand()!= EquipmentSlot.HAND)return;
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
				case "Gnobinette":{
					progress = QuestUtils.getProgress(p.getName(), Quests.TACOS);
					if(progress==0) {
						p.sendMessage("§cHey ! Eh toi ! J'ai besoin de ton aide. Cela fait un moment que je cherche le Tacos du Saint Narcos.");
						p.sendMessage("§cC'est une arme puissante que j'aimerais rajouter à mon Gnobo's Museum.");
						p.sendMessage("§cOn dit qu'il est caché dans la Grotte de Narcos ! Cette fameuse grotte est cachée quelque part dans ce monde.");
						p.sendMessage("§cPersonne n'a encore trouvé la grotte ! Si tu le trouves, tu auras tout le mérite et une grande partie de ma fortune !");
						p.sendMessage("§cBonne chance !");
						QuestUtils.setProgress(p.getName(), Quests.TACOS, 1);
					}else if(progress==1){
						p.sendMessage("§cQu'attends-tu ? Fonce ! Va chercher le Tacos du Saint Narcos !");
					}else if(progress==2){
						p.sendMessage("§c.. Je.. Tu l'as trouvé ! Je n'en reviens pas !!!");
						p.sendMessage("§cTiens ! Prend une partie de ma fortune comme convenu ! Le Gnobo's Museum te remercie du fond du coeur !");
						ItemUtils.giveOrDrop(p, new ItemStack(Material.DIAMOND, 2));
						QuestUtils.delSection(p.getName(), Quests.TACOS);
						QuestUtils.delQItem(p, Quests.TACOS, 1);
					}
					break;
				}
				case "Travis Kalanick":{
					progress = QuestUtils.getProgress(p.getName(), Quests.UBER_EAT);
					if(progress==0){
						p.sendMessage("§cHey, je suis le président-directeur général d'Uber !");
						p.sendMessage("§cPlusieurs livreur chez Uber Eat sont en grève et ceux qui travaillent sont débordé !");
						p.sendMessage("§cJ'ai besoin de toi pour aller livrer ce McEntaNugget et ces McEntaFrites.");
						p.sendMessage("§cEn échange, je te donne un peu de sous et de la nourriture.");
						QuestUtils.setProgress(p.getName(), Quests.UBER_EAT, 1);
						ItemStack item = new ItemBuilder(Material.COOKED_CHICKEN).name("§cMcEntaNugget").lore("§aUn euuh.. Nugget ?").build();
						ItemStack item1 = new ItemBuilder(Material.POTATO).name("§6McEntaFrites").lore("§aElles ont pas l'air cuite ces frites..").build();
						QuestUtils.markQItem(item, Quests.UBER_EAT, 2);
						QuestUtils.markQItem(item1, Quests.UBER_EAT, 3);
						p.getInventory().addItem(item, item1);

					}else if(progress==1){
						p.sendMessage("§cAllez, plus vite que ça ! ");
					}else if(progress==2){
						p.sendMessage("§cBravo membre de la plè.. Euh bravo jeune personne, grâce à toi on a pu gagner un peu de temps ! Reviens quand tu veux.");
						p.sendMessage("§cJ'ai failli oublier ! Tiens cette pomme qu'un inconnu m'a donné.. Euh je veut dire que j'ai collecté moi même de mon jardin !");
						p.sendMessage("§cFait pas attention à sa texture, c'est l'intérieur qui compte comme on dit.");
						ItemUtils.giveOrDrop(p, new ItemStack(Material.GOLDEN_APPLE, 1));
						QuestUtils.delSection(p.getName(), Quests.UBER_EAT);
					}
					break;
				}
				case "???":{
					progress = QuestUtils.getProgress(p.getName(), Quests.SECRET);
					if(progress==0){
						p.sendMessage("§cPsst eh.. Mec eho.. Nan je vends pas de drogue, mais j'ai une super info pour toi !");
						p.sendMessage("§cY'a une rumeur qui dit qu'il y a une grotte où Narcos produit ses vidéos..");
						p.sendMessage("§cOn dit même qu'on peut trouver certains staff caché, et que si tu leurs parlent, chacun te donnera une récompense !");
						p.sendMessage("§cà toi de me croire ou non, si tu refuses la quête reparle moi.");
						QuestUtils.setProgress(p.getName(), Quests.SECRET, 1);
					}else if(progress==1){
						p.sendMessage("§cOh? You're approaching me? Instead of running away, you're coming right to me?.. Hum désolé, j'ai toujours voulu sortir cette phrase..");
						p.sendMessage("§cDucoup j'imagine que si tu me parles, c'est pour refuser cette quête ? Aucun problème, bonne continuation jeune padawan !");
						QuestUtils.delSection(p.getName(), Quests.SECRET);
					}else if(progress==2||progress==3||progress==4||progress==5||progress==6){
						p.sendMessage("§cALLEZ, FINI VITE CETTE QUÊTE !");
					}
					break;
				}
				case "Narcos":{
					progress = QuestUtils.getProgress(p.getName(), Quests.SECRET);
					if(progress==0||progress==1){
						p.sendMessage("§7T'es qui ?");
					}else if(progress==2){
						p.sendMessage("§7Malheureusement je n'ai rien à te donner sauf un accès à la VideoZone. C'est une zone avec des moments de mes vidéos..");
						p.sendMessage("§7Je retire ce que j'ai dit, tu ne le mérites point encore.. Les autres te donneront sûrement quelque chose.");
						QuestUtils.setProgress(p.getName(), Quests.SECRET, 3);
						p.sendMessage("§7Ah et aussi, tu dois les parler par ordre ! Voici l'ordre:");
						p.sendMessage("§7iTrooz_; Raph9213; Samourai_Mouton");
					}else if(progress==3||progress==4){
						p.sendMessage("§7Ne me dérange pas, je cherche des idées pour ma futur vidéo..");
						p.sendMessage("§7Si tu as oublié, voici l'ordre des 'visites':");
						p.sendMessage("§7iTrooz_; Raph9213; Samourai_Mouton");
					}else if(progress==6){
						p.sendMessage("§71) TU DIRAS A MOUTON QUE CHUI OCCUPÉ");
						p.sendMessage("§72) Bravo, tu as pu parler à tout le monde ! Pour te féliciter, tiens cette modeste récompense!");
						ItemUtils.giveOrDrop(p, new ItemStack(Material.IRON_INGOT, 2));
						p.sendMessage("§7Très très modeste, chui pas riche moi.");
						p.sendMessage("§7Je te laisse le droit d'entrer dans ma VideoZone également..");
						QuestUtils.delSection(p.getName(), Quests.SECRET);
					}
					break;
				}
				case "iTrooz_":{
					progress = QuestUtils.getProgress(p.getName(), Quests.SECRET);
					if(progress==0||progress==1||progress==2||progress==4||progress==5||progress==6){
						p.sendMessage("§3Euh.. Ne dit à personne que je suis ici ou je te mets en cellule !");
					}else if(progress==3){
						p.sendMessage("§3CODE CODE, NARCOS CODE OU VIDÉO ! Euh.. Hum désolé, je me suis laissé emporté..");
						p.sendMessage("§3On m'a dit de donner quelque chose aux gens qui me trouvait, ducoup bah.. Tiens!");
						ItemUtils.giveOrDrop(p, new ItemStack(Material.BOOK, 6));
						p.sendMessage("§3Avec, tu pourras apprendre le java !");
						QuestUtils.setProgress(p.getName(), Quests.SECRET, 4);
					}
					break;
				}
				case "Raph9213":{
					progress = QuestUtils.getProgress(p.getName(), Quests.SECRET);
					if(progress==0||progress==1||progress==2||progress==3||progress==5||progress==6){
						p.sendMessage("§aWowiwowiwo nitro");
					}else if(progress==4){
						p.sendMessage("§aWOOLWARS !! Hehe.. Tiens prends ça !");
						ItemUtils.giveOrDrop(p, new ItemStack(Material.SHEEP_SPAWN_EGG, 1));
						p.sendMessage("§aDit à personne que je t'ai donné ça, on va me taper sur les doigts sinon..");
						QuestUtils.setProgress(p.getName(), Quests.SECRET, 5);
					}
					break;
				}
				case "Samourai_Mouton":{
					progress = QuestUtils.getProgress(p.getName(), Quests.SECRET);
					if(progress==0||progress==1||progress==2||progress==3||progress==4||progress==6){
						p.sendMessage("§6Dans la cité on m'appelle le Pro Bedwars..");
					}else if(progress== 5){
						p.sendMessage("§6Mon chat, ce gros obèse, s'est allongé sur mon bras et me l'a cassé car j'ai essayé de le soulever avec ce bras..");
						p.sendMessage("§6Tiens prend ça, je le gardais en souvenir pour ce moment mais j'en ai marre de le trimballer partout!");
						ItemUtils.giveOrDrop(p, new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, 1));
						QuestUtils.setProgress(p.getName(), Quests.SECRET, 6);
						p.sendMessage("§6Va retrouver Narcos ! Et dit lui qu'il doit m'aider pour les events aussi..");
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
			int progress = QuestUtils.getProgress(p.getName(), Quests.BASE_SECRETE);
			if(progress==1||progress==2){
				if(QuestUtils.getQItem(p, Quests.BASE_SECRETE, 0).size()==0){
					p.sendMessage("§cBien joué ! Tu as trouvé la base militaire ! Détruit la maintenant ! (pose la tnt)");
					ItemStack item = new ItemStack(Material.TNT);
					QuestUtils.markQItem(item, Quests.BASE_SECRETE, 0);
					p.getInventory().addItem(item);
				}
				QuestUtils.setProgress(p.getName(), Quests.MINEURS, 2);
			}
		}else if(e.getRegion()==Regions.MINE_PVE.region){
			p.setHealth(e.getPlayer().getMaxHealth());

			int progress = QuestUtils.getProgress(p.getName(), Quests.MINEURS);
			if(progress==1){
				p.sendMessage("§cTu entres dans une zone dangereuse, attention ! Les mineurs se sont peut-être retrouvés piégés ici ?");
			}
		}else if(e.getRegion()==Regions.GROTTE_NARCOS.region){
			int progress = QuestUtils.getProgress(p.getName(), Quests.TACOS);
			if(progress==1){
				p.sendMessage("§cBravo ! Tu as trouvé la Grotte de Narcos !! Maintenant, sors vite avec le Tacos du Saint Narcos avant que les monstres t'attaquent !");
				QuestUtils.setProgress(p.getName(), Quests.TACOS, 2);
				ItemStack item = new ItemBuilder(Material.BREAD).name("§2Tacos").lore("§cLe Tacos du Saint Narcos !", "§aN'essaye pas de le manger, ça te portera malchance.").build();
				QuestUtils.markQItem(item, Quests.TACOS, 1);
				p.getInventory().addItem(item);
			}
		}else if(e.getRegion()==Regions.UBER_EAT.region){
			int progress = QuestUtils.getProgress(p.getName(), Quests.UBER_EAT);
			if(progress==1){
				p.sendMessage("§c*Une personne obèse sors de la maison, récupère la livraison et te donne l'argent, sans dire un mot.*");
				p.sendMessage("§cTéléphone: Livraison effectué !");
				QuestUtils.setProgress(p.getName(), Quests.UBER_EAT, 2);
				QuestUtils.delQItem(p, Quests.UBER_EAT, 2);
				QuestUtils.delQItem(p, Quests.UBER_EAT, 3);

			}
		}else if(e.getRegion()==Regions.GROTTE_SECRETE.region){
			int progress = QuestUtils.getProgress(p.getName(), Quests.SECRET);
			if(progress==1){
				p.sendMessage("§c*Une voix résonne* HAHAHA.. ENFIN ! Bienvenue à ma zone de travail, jeune joueur !");
				p.sendMessage("§cC'est ici que je produit mes vidéos, et que certains staff me rendent visite ! Viens me parler à moi, Narcos !");
				QuestUtils.setProgress(p.getName(), Quests.SECRET, 2);
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
	public static void plsSpawn(EntitySpawnEvent e){
		if(e.getLocation().getWorld()== Main.world){
			if (RegionManager.getRegionsAt(e.getLocation()).contains(Regions.MINE_PVE.region)) {
				if(e.getEntityType() == EntityType.ZOMBIE){
					e.setCancelled(false);
				}
			}
		}
	}


	@EventHandler
	public void a(EntityPathfindEvent e) {
		if(e.getLoc().getWorld()==Main.world){
			if (!RegionManager.getRegionsAt(e.getLoc()).contains(Regions.MINE_PVE.region)) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void a(BlockPlaceEvent e) {
		if (e.getBlock().getType() == Material.TNT && QuestUtils.getQItemID(e.getItemInHand(), Quests.BASE_SECRETE) == 0) {
			int progress = QuestUtils.getProgress(e.getPlayer().getName(), Quests.MINEURS);
			if (progress == 2) {
				QuestUtils.setProgress(e.getPlayer().getName(), Quests.MINEURS, 3);
				QuestUtils.delQItem(e.getPlayer(), Quests.BASE_SECRETE, 0);
				InstantFirework.explode(e.getPlayer().getLocation(), FireworkEffect.builder().withColor(Color.BLUE, Color.GREEN).build());
				e.getPlayer().sendMessage("§c§lBOUM ! §cTu as réussi soldat ! Reviens vite pour voir pour ta récompense !");
			}
		}
	}
}

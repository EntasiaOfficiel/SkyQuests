package fr.entasia.questcore;

import fr.entasia.questcore.tools.NormalListeners;
import fr.entasia.questcore.tools.QCCmd;
import fr.entasia.questcore.utils.SaveTask;
import fr.entasia.questcore.utils.enums.Regions;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	public static Main main;
	public static World world;

	@Override
	public void onEnable() {
		try{
			main = this;
			world = Bukkit.getWorlds().get(0);

			getCommand("questcore").setExecutor(new QCCmd());

			getServer().getPluginManager().registerEvents(new NormalListeners(), this);

			new SaveTask().runTaskTimer(this, 0 ,20*60*2);

			Regions.init();

			getLogger().info("Plugin activé !");
		}catch(Throwable e){
			e.printStackTrace();
			getLogger().severe("Une erreur est survenue ! ARRET DU SERVEUR");
			getServer().shutdown();
		}
	}
}

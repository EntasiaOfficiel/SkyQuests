package fr.entasia.questcore;

import fr.entasia.questcore.tools.APIListeners;
import fr.entasia.questcore.tools.QCCmd;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	public static Main main;

	@Override
	public void onEnable() {
		try{
			main = this;
			getCommand("questCore").setExecutor(new QCCmd());
			getServer().getPluginManager().registerEvents(new APIListeners(), this);
			getLogger().info("Plugin activé !");
		}catch(Throwable e){
			e.printStackTrace();
			getLogger().severe("Une erreur est survenue ! ARRET DU SERVEUR !");
			getServer().shutdown();
		}
	}
}

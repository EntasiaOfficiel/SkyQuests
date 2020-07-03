package fr.entasia.questcore;

import fr.entasia.questcore.api.APIListeners;
import fr.entasia.questcore.api.SaveTask;
import fr.entasia.questcore.tools.NormalListeners;
import fr.entasia.questcore.tools.QCCmd;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	public static Main main;

	@Override
	public void onEnable() {
		try{
			main = this;
			getCommand("questCore").setExecutor(new QCCmd());
			getServer().getPluginManager().registerEvents(new NormalListeners(), this);
			getLogger().info("Plugin activé !");

			// API
			getServer().getPluginManager().registerEvents(new APIListeners(), this);
			new SaveTask().runTaskTimer(this, 0 ,20*60*2);

		}catch(Throwable e){
			e.printStackTrace();
			getLogger().severe("Une erreur est survenue ! ARRET DU SERVEUR !");
			getServer().shutdown();
		}
	}
}

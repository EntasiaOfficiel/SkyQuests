package fr.entasia.questcore;

import fr.entasia.questcore.tools.NormalListeners;
import fr.entasia.questcore.tools.QCCmd;
import fr.entasia.questcore.utils.SaveTask;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	public static Main main;

	@Override
	public void onEnable() {
		try{
			main = this;

			getCommand("questCore").setExecutor(new QCCmd());

			getServer().getPluginManager().registerEvents(new NormalListeners(), this);

			new SaveTask().runTaskTimer(this, 0 ,20*60*2);

			getLogger().info("Plugin activé !");
		}catch(Throwable e){
			e.printStackTrace();
			getLogger().severe("Une erreur est survenue ! ARRET DU SERVEUR");
			getServer().shutdown();
		}
	}
}

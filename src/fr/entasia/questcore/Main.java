package fr.entasia.questcore;

import fr.entasia.apis.sql.SQLConnection;
import fr.entasia.questcore.tools.Listeners;
import fr.entasia.questcore.tools.QCCmd;
import fr.entasia.questcore.utils.enums.Regions;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main extends JavaPlugin {

	public static Main main;
	public static World world;
	public static SQLConnection sqlite;

	@Override
	public void onEnable() {
		try{
			main = this;
			world = Bukkit.getWorlds().get(0);
			if(!new File(getDataFolder(), "database.db").exists()){
				saveResource("database.db", false);
			}
			sqlite = SQLConnection.sqlite(getDataFolder(), "database.db");

			getCommand("questcore").setExecutor(new QCCmd());

			getServer().getPluginManager().registerEvents(new Listeners(), this);

			Regions.init();

			getLogger().info("Plugin activé !");
		}catch(Throwable e){
			e.printStackTrace();
			getLogger().severe("Une erreur est survenue ! ARRET DU SERVEUR");
			getServer().shutdown();
		}
	}
}

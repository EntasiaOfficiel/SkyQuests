package fr.entasia.questcore;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		System.out.println("Activation du plugin QuestCore");
		getServer().getPluginManager().registerEvents(new Utils(), this);
	}

	@Override
	public void onDisable() {
		System.out.println("Désactivation du plugin QuestCore");
	}

}

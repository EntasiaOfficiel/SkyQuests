package fr.entasia.questcore.api;

import fr.entasia.questcore.Main;
import org.bukkit.scheduler.BukkitRunnable;

public class SaveTask extends BukkitRunnable {
	@Override
	public void run() {
		Main.main.saveConfig();
	}
}

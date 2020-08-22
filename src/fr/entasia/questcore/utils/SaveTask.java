package fr.entasia.questcore.utils;

import fr.entasia.questcore.Main;
import org.bukkit.scheduler.BukkitRunnable;

public class SaveTask extends BukkitRunnable {
	@Override
	public void run() {
		Main.main.saveConfig();
	}
}

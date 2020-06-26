package fr.entasia.questcore;

import fr.entasia.apis.regionManager.api.Region;
import fr.entasia.apis.regionManager.api.RegionManager;
import fr.entasia.apis.regionManager.events.RegionEnterEvent;
import org.bukkit.event.Listener;

public class Utils implements Listener {

	public static Region zoneSecrete = RegionManager.getRegionByName("zone_secrete");

	public void a(RegionEnterEvent e){
		if(e.getRegion()==zoneSecrete){
			Quests.SECRET.onEvent(e.getPlayer(), 0);
		}
	}

}

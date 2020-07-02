package fr.entasia.questcore.tools;

import fr.entasia.apis.regionManager.api.Region;
import fr.entasia.apis.regionManager.api.RegionManager;
import fr.entasia.apis.regionManager.events.RegionEnterEvent;
import fr.entasia.questcore.Quests;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class Listeners implements Listener {

    public static Region zoneSecrete = RegionManager.getRegionByName("zone_secrete");

    @EventHandler
    public void a(RegionEnterEvent e){
        if(e.getRegion()==zoneSecrete){
            Quests.SECRET.onEvent(e.getPlayer(), 0);
        }
    }
}

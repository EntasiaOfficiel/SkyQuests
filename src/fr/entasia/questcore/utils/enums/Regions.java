package fr.entasia.questcore.utils.enums;

import fr.entasia.apis.regionManager.api.Region;
import fr.entasia.apis.regionManager.api.RegionManager;
import fr.entasia.apis.utils.BasicLocation;
import fr.entasia.questcore.Main;

public enum Regions {
	BASE_SECRETE("base_secrete", new BasicLocation(5, 5, 5), new BasicLocation(6, 6, 6)),
	MINE_PVE("mine_pve", new BasicLocation(161, 145, 25), new BasicLocation(207, 123, -13)),
	GROTTE_NARCOS("grotte_narcos", new BasicLocation(7, 5, 7), new BasicLocation(7, 5, 7)),
	UBER_EAT("uber_eat", new BasicLocation(3, 5, 3), new BasicLocation(3, 4, 3)),
	GROTTE_SECRETE("grotte_secrete", new BasicLocation(10, 5, 10),new BasicLocation(20, 10, 20)),


	;

	public Region region;

	Regions(String name, BasicLocation b1, BasicLocation b2){
		this.region = RegionManager.registerRegion(name, Main.world, b1, b2);
	}


	public static void init(){ // to init enum

	}



}

package fr.entasia.questcore.utils.enums;

import fr.entasia.apis.regionManager.api.Region;
import fr.entasia.apis.regionManager.api.RegionManager;
import fr.entasia.apis.utils.BasicLocation;
import fr.entasia.questcore.Main;

public enum Regions {
	BASE_SECRETE("base_secrete", new BasicLocation(5, 5, 5), new BasicLocation(6, 6, 6)),
	MINE_PVE("mine_pve", new BasicLocation(213, 150, -12), new BasicLocation(176, 113, 22)),


	;

	public Region region;

	Regions(String name, BasicLocation b1, BasicLocation b2){
		this.region = RegionManager.registerRegion(name, Main.world, b1, b2);
	}


	public static void init(){ // to init enum

	}



}

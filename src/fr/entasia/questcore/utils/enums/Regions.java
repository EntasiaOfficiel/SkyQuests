package fr.entasia.questcore.utils.enums;

import fr.entasia.apis.regionManager.api.Region;
import fr.entasia.apis.regionManager.api.RegionManager;

public enum Regions {
	BASE_SECRETE("base_secrete"),
	MINE_PVE("mine_pve"),


	;

	public Region region;

	Regions(String region){
		this.region = RegionManager.getRegionByName(region);
	}

}

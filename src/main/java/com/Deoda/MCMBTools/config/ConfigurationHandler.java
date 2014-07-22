package com.Deoda.MCMBTools.config;


import java.io.File;

import com.Deoda.MCMBTools.items.ItemMCMBBuildingWand;
import com.Deoda.MCMBTools.reference.Default;
import com.Deoda.MCMBTools.reference.ID;

import net.minecraftforge.common.Configuration;

public class ConfigurationHandler {
	public static void init(File configFile){
		Configuration config = new Configuration(configFile);
		try {
			config.load();

			ItemMCMBBuildingWand.maxWall=config.get(Configuration.CATEGORY_GENERAL,"maxWall",Default.MAX_WALL,"the number of MB a building wand can place in wall mode").getInt();
			
			ID.itemMCMBT = config.get(Configuration.CATEGORY_ITEM,"buildingWand",Default.BUILDING_WAND_ID,"").getInt();
			
		} catch (Exception e) {}
		finally{
			config.save();
		}
	}
}

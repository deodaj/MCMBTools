package com.Deoda.MCMBTools.config;


import java.io.File;

import com.Deoda.MCMBTools.items.ItemMCMBBuildingWand;
import com.Deoda.MCMBTools.items.ItemMCMBExpandingWand;
import com.Deoda.MCMBTools.reference.Default;
import com.Deoda.MCMBTools.reference.ID;
import com.Deoda.MCMBTools.utility.AutoCraft;

import net.minecraftforge.common.Configuration;

public class ConfigurationHandler {
	public static void init(File configFile){
		Configuration config = new Configuration(configFile);
		try {
			config.load();

			ItemMCMBBuildingWand.maxWall=config.get(Configuration.CATEGORY_GENERAL,"maxWall",Default.MAX_WALL,"the number of MB a building wand can place").getInt();
			ItemMCMBExpandingWand.maxExpand=config.get(Configuration.CATEGORY_GENERAL,"maxExpand",Default.MAX_EXPAND,"the number of MB a expanding wand can place").getInt();
			AutoCraft.isAutocrafting=config.get(Configuration.CATEGORY_GENERAL,"autoCrafting",Default.AUTOCRAFT,"allow the building wand to craft missing microblock from your inventory").getBoolean(Default.AUTOCRAFT);

			ID.itemMCMBBuildingWand = config.get(Configuration.CATEGORY_ITEM,"buildingWand",Default.BUILDING_WAND_ID,"").getInt();
			ID.itemMCMBExpandingWand = config.get(Configuration.CATEGORY_ITEM,"expandingWand",Default.EXPAND_WAND_ID,"").getInt();
			ID.itemMCMBDeconstructingWand = config.get(Configuration.CATEGORY_ITEM,"deconstructingWand",Default.DECONSTRUCT_WAND_ID,"").getInt();
			
		} catch (Exception e) {}
		finally{
			config.save();
		}
	}
}

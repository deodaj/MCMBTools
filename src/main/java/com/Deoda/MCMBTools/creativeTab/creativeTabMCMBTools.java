package com.Deoda.MCMBTools.creativeTab;

import com.Deoda.MCMBTools.init.ModItems;
import com.Deoda.MCMBTools.reference.Reference;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class creativeTabMCMBTools {

	public static final CreativeTabs MCMBT_TAB = new CreativeTabs(Reference.MOD_ID) {
		
		@Override
		public Item getTabIconItem() {
			return ModItems.MCMBBuildingWand;
		}

		@Override
		public String getTranslatedTabLabel() {
			return Reference.MOD_NAME;
		}
		
		
	};
	
}

package com.Deoda.MCMBTools.init;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.oredict.ShapedOreRecipe;

import com.Deoda.MCMBTools.items.ItemMCMBBuildingWand;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModItems {
	
	public static ItemMCMBBuildingWand MCMBBuildingWand = new ItemMCMBBuildingWand();
	
	public static void PreInit(){
		GameRegistry.registerItem(MCMBBuildingWand, "MCMBBuildingWand");
	}
	
	public static void Init(){
		GameRegistry.addRecipe(new ShapedOreRecipe(MCMBBuildingWand, "RMR","RSR"," S ",'R',"stoneRod",'M',Item.ingotIron,'S',Item.stick));
	}
}

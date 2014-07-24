package com.Deoda.MCMBTools.init;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.oredict.ShapedOreRecipe;
import codechicken.microblock.CornerMicroClass;
import codechicken.microblock.ItemMicroPart;
import codechicken.microblock.MicroMaterialRegistry;
import codechicken.microblock.MicroRecipe;

import com.Deoda.MCMBTools.items.ItemMCMBBuildingWand;
import com.Deoda.MCMBTools.items.ItemMCMBDeconstructingWand;
import com.Deoda.MCMBTools.items.ItemMCMBExpandingWand;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModItems {

	public static ItemMCMBBuildingWand MCMBBuildingWand = new ItemMCMBBuildingWand();
	public static ItemMCMBExpandingWand MCMBExpandingWand = new ItemMCMBExpandingWand();
	public static ItemMCMBDeconstructingWand MCMBDeconstructingWand = new ItemMCMBDeconstructingWand();
	public static void PreInit(){
		GameRegistry.registerItem(MCMBBuildingWand, "MCMBBuildingWand");
		GameRegistry.registerItem(MCMBExpandingWand, "MCMBExpandingWand");
		GameRegistry.registerItem(MCMBDeconstructingWand, "MCMBDeconstructingWand");
	}
	
	public static void Init(){
		ItemStack goldCorner = ItemMicroPart.create((CornerMicroClass.classID()<<8)+4, "tile.blockGold");
		ItemStack redCorner = ItemMicroPart.create((CornerMicroClass.classID()<<8)+4, "tile.blockRedstone");
		ItemStack diamondCorner = ItemMicroPart.create((CornerMicroClass.classID()<<8)+4, "tile.blockDiamond");
		GameRegistry.addRecipe(new ShapedOreRecipe(MCMBBuildingWand, "x","s","s",'s',"stoneRod",'x',diamondCorner));
		GameRegistry.addRecipe(new ShapedOreRecipe(MCMBExpandingWand, "x","s","s",'s',"stoneRod",'x',redCorner));
		GameRegistry.addRecipe(new ShapedOreRecipe(MCMBDeconstructingWand, "x","s","s",'s',"stoneRod",'x',goldCorner));
		
	}
}

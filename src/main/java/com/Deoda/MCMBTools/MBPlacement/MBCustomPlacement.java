package com.Deoda.MCMBTools.MBPlacement;

import com.Deoda.MCMBTools.items.ItemMCMBBuildingWand;
import com.Deoda.MCMBTools.utility.AutoCraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import codechicken.microblock.ItemMicroPart;
import codechicken.microblock.MicroblockClass;
import codechicken.microblock.MicroblockClassRegistry;
import codechicken.microblock.PlacementGrid;
import codechicken.microblock.PlacementProperties;

public abstract class MBCustomPlacement extends PlacementProperties {

	public static ItemStack inPlayerInventory(EntityPlayer player, MicroblockClass mcrClass, int size, int material,boolean auto){
		ItemStack ret = AutoCraft.isAlreadyThere(player, mcrClass.classID(), size, material);
		if (auto && ret==null && AutoCraft.isAutocrafting && AutoCraft.autoCraft(player, mcrClass.classID(), size, material) ){
			ret = AutoCraft.isAlreadyThere(player, mcrClass.classID(), size, material);
		}
		return ret;
	}
	
	public static void decreaseStack(EntityPlayer player, ItemStack stack){
		stack.stackSize--;
		if (stack.stackSize==0){
			ItemStack[] inv = player.inventory.mainInventory;
			int i=0;
			for (i = 0; i < inv.length; i++) {
				if (inv[i]==stack){
					inv[i]=null;
				}
			}
		}
	}

}

package com.Deoda.MCMBTools.MBPlacement;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import codechicken.microblock.ItemMicroPart;
import codechicken.microblock.MicroblockClass;
import codechicken.microblock.MicroblockClassRegistry;
import codechicken.microblock.PlacementGrid;
import codechicken.microblock.PlacementProperties;

public abstract class MBCustomPlacement extends PlacementProperties {

	public static ItemStack inPlayerInventory(EntityPlayer player, MicroblockClass mcrClass, int size, int material){
		ItemStack[] inv = player.inventory.mainInventory;
		for (int i = 0; i < inv.length; i++) {
			if (inv[i]!=null && inv[i].getItem() instanceof ItemMicroPart){
				ItemMicroPart item= (ItemMicroPart)inv[i].getItem();
				if (ItemMicroPart.getMaterialID(inv[i])==material &&
						MicroblockClassRegistry.getMicroClass(inv[i].getItemDamage())==mcrClass &&
						size==(inv[i].getItemDamage()&0xFF)){
					return inv[i];
				}
			}
		}
		
		return null;
	}
	
	public static void decreaseStack(EntityPlayer player, ItemStack stack){
		stack.stackSize--;
		if (stack.stackSize==0){
			ItemStack[] inv = player.inventory.mainInventory;
			for (int i = 0; i < inv.length; i++) {
				if (inv[i]==stack){
					inv[i]=null;
				}
			}
		}
	}

}

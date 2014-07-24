package com.Deoda.MCMBTools.utility;

import com.Deoda.MCMBTools.reference.Default;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import codechicken.microblock.CornerMicroClass;
import codechicken.microblock.EdgeMicroClass;
import codechicken.microblock.FaceMicroClass;
import codechicken.microblock.ItemMicroPart;
import codechicken.microblock.MicroRecipe;
import codechicken.microblock.MicroblockClass;
import codechicken.microblock.MicroblockClassRegistry;
import codechicken.microblock.Saw;
import cpw.mods.fml.common.FMLCommonHandler;

public class AutoCraft {
	public static boolean isAutocrafting = Default.AUTOCRAFT;
	
	public static boolean canAutoCraft(EntityPlayer player, int mcrClass, int size, int material){
		if (size>8 || size<0) return false;
		return isAlreadyThere(player,mcrClass,size,material)!=null||
				canAutoCraftByThinning(player,mcrClass,size,material) ||
				canAutoCraftBySpliting(player,mcrClass,size,material);
	}

	public static ItemStack isAlreadyThere(EntityPlayer player, int mcrClass, int size, int material) {
		ItemStack[] inv = player.inventory.mainInventory;
		for (int i = 0; i < inv.length; i++) {
			if (inv[i]!=null){
				if (MicroRecipe.microMaterial(inv[i])==material &&
						MicroRecipe.microClass(inv[i])==mcrClass &&
						MicroRecipe.microSize(inv[i])==size){
					return inv[i];
				}
			}
		}
		return null;
	}
	
	public static boolean canAutoCraftBySpliting(EntityPlayer player, int mcrClass, int size, int material) {
		if (mcrClass == CornerMicroClass.classID()){
			return goodSaw(player, material)!=null && canAutoCraft(player, EdgeMicroClass.classID(), size, material);
		}
		if (mcrClass == EdgeMicroClass.classID()){
			return goodSaw(player, material)!=null && canAutoCraft(player, FaceMicroClass.classID(), size, material);
		}
		return false;
	}

	public static boolean canAutoCraftByThinning(EntityPlayer player,int mcrClass, int size, int material) {
		return goodSaw(player, material)!=null && canAutoCraft(player, mcrClass, size*2, material);
	}


	public static ItemStack goodSaw(EntityPlayer player, int material) {
		ItemStack[] inv = player.inventory.mainInventory;
		for (int i = 0; i < inv.length; i++) {
			if (inv[i]!=null && inv[i].getItem() instanceof Saw){
				if (MicroRecipe.canCut((Saw) inv[i].getItem(), inv[i], material)){
					return inv[i];
				}
			}
		}
		return null;
	}

	public static int find(EntityPlayer player,ItemStack stack){
		ItemStack[] inv = player.inventory.mainInventory;
		for (int i = 0; i < inv.length; i++) {
			if (inv[i]==stack){
				return i;
			}
		}
		return -1;
	}
	
	public static boolean autoCraft(EntityPlayer player, int mcrClass, int size, int material){

		if(!canAutoCraft(player, mcrClass, size, material)){
			return false;
		}
		ItemStack[] inv = player.inventory.mainInventory;
		if (isAlreadyThere(player, mcrClass, size, material)!=null){
			return true;
		}
		int splitMat = FaceMicroClass.classID();
		if (mcrClass==CornerMicroClass.classID()) splitMat=EdgeMicroClass.classID();
		if ( canAutoCraftBySpliting(player, mcrClass, size, material) && autoCraft(player, splitMat, size, material)){
			ItemStack ingredient = isAlreadyThere(player, splitMat, size, material);
			ItemStack itemSaw = goodSaw(player, material);
			int resultSlot=find(player,null);
			if (resultSlot==-1 && ingredient.stackSize>1 && itemSaw.getItemDamage()!=itemSaw.getItem().getMaxDamage(itemSaw)-1) return false;
			ingredient.stackSize--;
			if (ingredient.stackSize==0) {inv[find(player,ingredient)]=null;}
			itemSaw.setItemDamage(itemSaw.getItemDamage()+1);
			if (itemSaw.getItemDamage()==itemSaw.getItem().getMaxDamage(itemSaw)) {inv[find(player,itemSaw)]=null;}
			inv[find(player,null)] = MicroRecipe.create(2, mcrClass, size, material);
			return true;
		} else if ( canAutoCraftByThinning(player, mcrClass, size, material) && autoCraft(player, mcrClass, size*2, material)){
			ItemStack ingredient = isAlreadyThere(player, mcrClass, size*2, material);
			ItemStack itemSaw = goodSaw(player, material);
			int resultSlot=find(player,null);
			if (resultSlot==-1 && ingredient.stackSize>1 && itemSaw.getItemDamage()!=itemSaw.getItem().getMaxDamage(itemSaw)-1) return false;
			ingredient.stackSize--;
			if (ingredient.stackSize==0) {inv[find(player,ingredient)]=null;}
			itemSaw.setItemDamage(itemSaw.getItemDamage()+1);
			if (itemSaw.getItemDamage()==itemSaw.getItem().getMaxDamage(itemSaw)) {inv[find(player,itemSaw)]=null;}
			inv[find(player,null)] = MicroRecipe.create(2, mcrClass, size, material);
			return true;
		}
		return false;		
	}
}

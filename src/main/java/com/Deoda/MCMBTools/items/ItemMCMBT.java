package com.Deoda.MCMBTools.items;

import com.Deoda.MCMBTools.creativeTab.creativeTabMCMBTools;
import com.Deoda.MCMBTools.reference.ID;
import com.Deoda.MCMBTools.reference.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemMCMBT extends Item {
	public ItemMCMBT() {
		super(ID.itemMCMBT);
		this.setMaxStackSize(1);
		this.setNoRepair();
		this.setCreativeTab(creativeTabMCMBTools.MCMBT_TAB);
	}

	@Override
	public String getUnlocalizedName() {
		return String.format("item.%s:%s",Reference.MOD_ID.toLowerCase(),getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}

	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack) {
		return String.format("item.%s:%s",Reference.MOD_ID.toLowerCase(),getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}


	protected String getUnwrappedUnlocalizedName(String unlocalizedName) {
		return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
	}
	
}

package com.Deoda.MCMBTools.handler;

import com.Deoda.MCMBTools.init.ModItems;
import com.Deoda.MCMBTools.items.ItemMCMBDeconstructingWand;

import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class EventHandler {
	@ForgeSubscribe
	public void onClick(PlayerInteractEvent event){
		if (event.action==PlayerInteractEvent.Action.LEFT_CLICK_BLOCK){
			if (event.entityPlayer.getHeldItem()!=null && event.entityPlayer.getHeldItem().getItem() instanceof ItemMCMBDeconstructingWand){
				ModItems.MCMBDeconstructingWand.click(event.entityPlayer.getHeldItem(),event.entityPlayer,event.x,event.y,event.z);
			}
		}
	}
}

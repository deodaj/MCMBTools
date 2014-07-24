package com.Deoda.MCMBTools.items;

import com.Deoda.MCMBTools.reference.Default;
import com.Deoda.MCMBTools.reference.ID;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import codechicken.lib.raytracer.RayTracer;
import codechicken.lib.vec.BlockCoord;
import codechicken.microblock.Microblock;
import codechicken.multipart.BlockMultipart;
import codechicken.multipart.TMultiPart;
import codechicken.multipart.TileMultipart;

public class ItemMCMBDeconstructingWand extends ItemMCMBT {

	public ItemMCMBDeconstructingWand() {
		super(ID.itemMCMBDeconstructingWand);
		this.setUnlocalizedName("MCMBDeconstructingWand");
		this.setTextureName("mcmbtools:MCMBDeconstructingWand");
	}

	public void click(ItemStack heldItem, EntityPlayer player, int x,
			int y, int z) {
		World world=player.worldObj;
		TileMultipart tile = TileMultipart.getTile(world, new BlockCoord(x,y,z));
		MovingObjectPosition hit = RayTracer.retraceBlock(world, player, x, y, z);

		if (hit!=null && tile!=null){
			Object index = BlockMultipart.reduceMOP(hit)._1;
			TMultiPart mp = tile.partList().apply(index);
			if (mp instanceof Microblock){
				Microblock mib = (Microblock)mp;
				mib.harvest(hit, player);
			}
		}
	}
}

package com.Deoda.MCMBTools.MBPlacement;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import codechicken.microblock.FaceMicroClass;
import codechicken.microblock.FacePlacement;
import codechicken.microblock.MicroblockClass;
import codechicken.microblock.MicroblockPlacement;
import codechicken.microblock.PlacementGrid;

public class MBFacePlacement extends MBCustomPlacement {
	
	public static boolean place(EntityPlayer player, World world, int x, int y, int z, int slot, int size, int material){
		ItemStack stack=null;
		if(!player.capabilities.isCreativeMode){
			stack=inPlayerInventory(player, FacePlacement.microClass(), size, material);
			if (stack==null || stack.stackSize<1){
				return false;
			}
		}
		
		MovingObjectPosition hit = new MovingObjectPosition(x, y+1, z, 0,Vec3.createVectorHelper(-0.5+x,-0.5+y+1,-0.5) );
		MicroblockPlacement placement= new MicroblockPlacement(world, player, hit, size, material, false, new MBFacePlacement(slot));
		if( placement.oppMod()){
			return false;
		}
		if(placement.apply()!=null){
			if(!player.capabilities.isCreativeMode){
				decreaseStack(player, stack);
			}
			if (!world.isRemote){
				placement.apply().place(world, player, null);
			}
			return true;
		}
		return false;
	}
	
	public int slot;
	
	public MBFacePlacement(int slot) {
		super();
		this.slot = slot;
	}

	@Override
	public MicroblockClass microClass() {
		return FacePlacement.microClass();
	}

	@Override
	public int opposite(int arg0, int arg1) {
		return FacePlacement.opposite(arg0, arg1);
	}

	@Override
	public PlacementGrid placementGrid() {
		return new SlotPlacement(slot);
	}

	@Override
	public boolean expand(int slot, int side) {
		return FacePlacement.expand(slot, side);
	}

	@Override
	public boolean sneakOpposite(int slot, int side) {
		return FacePlacement.sneakOpposite(slot, side);
	}
}

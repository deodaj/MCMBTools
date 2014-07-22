package com.Deoda.MCMBTools.MBPlacement;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import codechicken.microblock.EdgePlacement;
import codechicken.microblock.ExecutablePlacement;
import codechicken.microblock.FacePlacement;
import codechicken.microblock.MicroblockClass;
import codechicken.microblock.MicroblockPlacement;
import codechicken.microblock.PlacementGrid;

public class MBEdgePlacement extends MBCustomPlacement {

	public int slot;
	
	public static boolean place(EntityPlayer player, World world, int x, int y, int z, int side, int slot, int size, int material){
		ItemStack stack=null;
		if(!player.capabilities.isCreativeMode){
			stack=inPlayerInventory(player, EdgePlacement.microClass(), size, material);
			if (stack==null || stack.stackSize<1){
				return false;
			}
		}
		side-=side%2;
		int px=(side==4)?1:0;
		int py=(side==0)?1:0;
		int pz=(side==2)?1:0;
		MovingObjectPosition hit = new MovingObjectPosition(x+px, y+py, z+pz, side,Vec3.createVectorHelper(-0.5+x+px,-0.5+y+py,-0.5+z+pz) );
		MicroblockPlacement placement= new MicroblockPlacement(world, player, hit, size, material, false, new MBEdgePlacement(slot));
		if( placement.oppMod()){
			return false;
		}
		if( placement.doExpand()){
				System.out.println("expand");
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
	
	
	
	public MBEdgePlacement(int slot) {
		super();
		this.slot = slot;
	}

	@Override
	public ExecutablePlacement customPlacement(MicroblockPlacement pmt) {
		return EdgePlacement.customPlacement(pmt);
	}



	@Override
	public MicroblockClass microClass() {
		return EdgePlacement.microClass();
	}

	@Override
	public int opposite(int arg0, int arg1) {
		return EdgePlacement.opposite(arg0, arg1);
	}

	@Override
	public PlacementGrid placementGrid() {
		return new SlotPlacement(slot);
	}

	@Override
	public boolean expand(int slot, int side) {
		return EdgePlacement.expand(slot, side);
	}

	@Override
	public boolean sneakOpposite(int slot, int side) {
		return FacePlacement.sneakOpposite(slot, side);
	}

}

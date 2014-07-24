package com.Deoda.MCMBTools.MBPlacement;

import codechicken.lib.vec.BlockCoord;
import codechicken.microblock.CornerMicroClass;
import codechicken.microblock.CornerMicroblock;
import codechicken.microblock.EdgeMicroClass;
import codechicken.microblock.EdgeMicroblock;
import codechicken.microblock.FaceMicroClass;
import codechicken.microblock.FaceMicroblock;
import codechicken.microblock.Microblock;
import codechicken.microblock.PostMicroblock;
import codechicken.multipart.TMultiPart;
import codechicken.multipart.TileMultipart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class PlacementUtility {
	 
	public static boolean place(EntityPlayer player, World world, int x, int y, int z,int side, int slot, int size, int mcrClass, int material,boolean edge,boolean autocraft){
		if(mcrClass==FaceMicroClass.classID()){
			return MBFacePlacement.place(player, world, x, y, z,slot, size, material,autocraft);
		}else if (mcrClass==EdgeMicroClass.classID()){
			if (edge){
				return MBEdgePlacement.place(player, world, x, y, z,0, slot, size, material,autocraft);
			}else{
				return MBEdgePlacement.place(player, world, x, y, z,side, -1, size, material,autocraft);
			}
		}else if (mcrClass==CornerMicroClass.classID()){
			return MBCornerPlacement.place(player, world, x, y, z,0, slot, size, material,autocraft);
		}
		return false;
	}

	public static boolean IsValidSupport(World world, int x, int y, int z, FaceMicroblock fmb) {
		TileMultipart tmp = TileMultipart.getTile(world, new BlockCoord(x, y, z));
		if (tmp!=null){
			for (TMultiPart mp : tmp.jPartList()) {
				if (mp instanceof FaceMicroblock){
					FaceMicroblock mb = (FaceMicroblock)mp;
					if(mb.getMaterial()==fmb.getMaterial() && mb.getSlot()==fmb.getSlot() && mb.getSize()==fmb.getSize()){
						return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean IsValidSupport(World world, int x, int y, int z, EdgeMicroblock fmb) {
		TileMultipart tmp = TileMultipart.getTile(world, new BlockCoord(x, y, z));
		if (tmp!=null){
			for (TMultiPart mp : tmp.jPartList()) {
				if (mp instanceof EdgeMicroblock){
					EdgeMicroblock mb = (EdgeMicroblock)mp;
					if(mb.getMaterial()==fmb.getMaterial() && mb.getSlot()==fmb.getSlot() && mb.getSize()==fmb.getSize()){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static boolean IsValidSupport(World world, int x, int y, int z, PostMicroblock fmb) {
		TileMultipart tmp = TileMultipart.getTile(world, new BlockCoord(x, y, z));
		if (tmp!=null){
			for (TMultiPart mp : tmp.jPartList()) {
				if (mp instanceof PostMicroblock){
					PostMicroblock mb = (PostMicroblock)mp;
					if(mb.getMaterial()==fmb.getMaterial() && mb.getShape()==fmb.getShape() && mb.getSize()==fmb.getSize()){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static boolean IsValidSupport(World world, int x, int y, int z, CornerMicroblock fmb) {
		TileMultipart tmp = TileMultipart.getTile(world, new BlockCoord(x, y, z));
		if (tmp!=null){
			for (TMultiPart mp : tmp.jPartList()) {
				if (mp instanceof CornerMicroblock){
					CornerMicroblock mb = (CornerMicroblock)mp;
					if(mb.getMaterial()==fmb.getMaterial() && mb.getSlot()==fmb.getSlot() && mb.getSize()==fmb.getSize()){
						return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean isSlotOnSide(int slot, int side) {
		boolean ret=false;
		switch (side) {
		case 0:
			ret=true;
		case 1:
			switch (slot) {
			case 19:ret=!ret;break;
			case 20:ret=!ret;break;
			case 23:ret=!ret;break;
			case 25:ret=!ret;break;

			case 7:ret=!ret;break;
			case 9:ret=!ret;break;
			case 11:ret=!ret;break;
			case 13:ret=!ret;break;
			default:
				break;
			}
			break;
		case 2:
			ret=true;
		case 3:
			switch (slot) {
			case 15:ret=!ret;break;
			case 17:ret=!ret;break;
			case 23:ret=!ret;break;
			case 24:ret=!ret;break;

			case 7:ret=!ret;break;
			case 8:ret=!ret;break;
			case 11:ret=!ret;break;
			case 12:ret=!ret;break;
			default:
				break;
			}			
			break;
		case 4:
			ret=true;
		case 5:
			switch (slot) {
			case 15:ret=!ret;break;
			case 16:ret=!ret;break;
			case 19:ret=!ret;break;
			case 21:ret=!ret;break;

			case 7:ret=!ret;break;
			case 8:ret=!ret;break;
			case 9:ret=!ret;break;
			case 10:ret=!ret;break;
			default:
				break;
			}					
			break;

		default:
			break;
		}
		return ret;
	}

	public static int getAdjacentSlot(int slot, int side) {
		int ret=slot;
		switch (side) {
		case 0:
		case 1:
			switch (slot) {
			case 7:ret=8;break;
			case 8:ret=7;break;
			case 9:ret=10;break;
			case 10:ret=9;break;
			case 11:ret=12;break;
			case 12:ret=11;break;
			case 13:ret=14;break;
			case 14:ret=13;break;
			
			case 19:ret=21;break;
			case 20:ret=22;break;
			case 21:ret=19;break;
			case 22:ret=20;break;
			case 23:ret=24;break;
			case 24:ret=23;break;
			case 25:ret=26;break;
			case 26:ret=25;break;
			default:
				break;
			}
			break;
		case 2:
		case 3:
			switch (slot) {
			case 7:ret=9;break;
			case 8:ret=10;break;
			case 9:ret=7;break;
			case 10:ret=8;break;
			case 11:ret=13;break;
			case 12:ret=14;break;
			case 13:ret=11;break;
			case 14:ret=12;break;
			
			case 15:ret=16;break;
			case 16:ret=15;break;
			case 17:ret=18;break;
			case 18:ret=17;break;
			case 23:ret=25;break;
			case 24:ret=26;break;
			case 25:ret=23;break;
			case 26:ret=24;break;
			default:
				break;
			}			
			break;
		case 4:
		case 5:
			switch (slot) {
			case 7:ret=11;break;
			case 8:ret=12;break;
			case 9:ret=13;break;
			case 10:ret=14;break;
			case 11:ret=7;break;
			case 12:ret=8;break;
			case 13:ret=9;break;
			case 14:ret=10;break;
			
			case 15:ret=17;break;
			case 16:ret=18;break;
			case 17:ret=15;break;
			case 18:ret=16;break;
			case 19:ret=20;break;
			case 20:ret=19;break;
			case 21:ret=22;break;
			case 22:ret=21;break;
			default:
				break;
			}					
			break;

		default:
			break;
		}
		return ret;
	}
}

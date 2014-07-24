package com.Deoda.MCMBTools.items;

import com.Deoda.MCMBTools.MBPlacement.MBEdgePlacement;
import com.Deoda.MCMBTools.MBPlacement.MBFacePlacement;
import com.Deoda.MCMBTools.MBPlacement.PlacementUtility;
import com.Deoda.MCMBTools.MBPlacement.SlotPlacement;
import com.Deoda.MCMBTools.init.ModItems;
import com.Deoda.MCMBTools.reference.Default;
import com.Deoda.MCMBTools.reference.ID;
import com.Deoda.MCMBTools.utility.AutoCraft;

import codechicken.lib.raytracer.RayTracer;
import codechicken.lib.vec.BlockCoord;
import codechicken.microblock.EdgeMicroClass;
import codechicken.microblock.EdgeMicroblock;
import codechicken.microblock.FaceMicroClass;
import codechicken.microblock.FaceMicroblock;
import codechicken.microblock.FacePlacement;
import codechicken.microblock.MicroRecipe;
import codechicken.microblock.Microblock;
import codechicken.microblock.MicroblockClassRegistry;
import codechicken.microblock.MicroblockPlacement;
import codechicken.microblock.PostMicroblock;
import codechicken.multipart.BlockMultipart;
import codechicken.multipart.TMultiPart;
import codechicken.multipart.TileMultipart;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;


public class ItemMCMBBuildingWand extends ItemMCMBT {

	public static int maxWall = Default.MAX_WALL;
	public ItemMCMBBuildingWand() {
		super(ID.itemMCMBBuildingWand);
		this.setUnlocalizedName("MCMBBuildingWand");
		this.setTextureName("mcmbtools:MCMBBuildingWand");
	}

	@Override
	public boolean onItemUse(ItemStack itemStack,
			EntityPlayer player, World world, int x, int y,
			int z, int side, float hitx, float hity, float hitz) {
		TileMultipart tile = TileMultipart.getTile(world, new BlockCoord(x,y,z));
		MovingObjectPosition hit = RayTracer.retraceBlock(world, player, x, y, z);

		if (hit!=null && tile!=null){
			Object index = BlockMultipart.reduceMOP(hit)._1;
			TMultiPart mp = tile.partList().apply(index);
			if (mp instanceof FaceMicroblock){
				FaceMicroblock mb = (FaceMicroblock)mp;
				int slot=mb.getSlot();
				int size=mb.getSize();
				int material=mb.getMaterial();
				int oppSide=side;
				if (side%2==0) oppSide++; else oppSide--;
				if (side==slot|| oppSide==slot){
					return false;
				}
				int px=(side==4)?-1:(side==5)?+1:0;
				int py=(side==0)?-1:(side==1)?+1:0;
				int pz=(side==2)?-1:(side==3)?+1:0;
				if (PlacementUtility.place(player, world, x+px, y+py, z+pz,side,slot, size,FaceMicroClass.classID(), material,false,AutoCraft.isAutocrafting )){
					//let's go
					int tside = (side%2==0)?side:side-1;
					int tslot = (slot%2==0)?slot:slot-1;
					int dir = (tslot+tside==2)?4:(tslot+tside==4)?2:0;
					int dx=(dir==4)?1:0;
					int dy=(dir==0)?1:0;
					int dz=(dir==2)?1:0;
					int nbPlace=1;
					int du=0;
					int dd=0;
					boolean su=true;
					boolean sd=true;
					while ((su||sd)&&(nbPlace<maxWall)){
						if (su){
							du++;
							if (PlacementUtility.IsValidSupport(world,x+du*dx,y+du*dy,z+du*dz,mb)){
								su=PlacementUtility.place(player, world, x+px+du*dx, y+py+du*dy, z+pz+du*dz,side,slot, size,FaceMicroClass.classID(), material,false,AutoCraft.isAutocrafting);
								nbPlace=su?nbPlace+1:nbPlace;
							}else{
								su=false;
							}
						}
						if (sd && (nbPlace<9)){
							dd--;
							if (PlacementUtility.IsValidSupport(world,x+dd*dx,y+dd*dy,z+dd*dz,mb)){
								sd=PlacementUtility.place(player, world, x+px+dd*dx, y+py+dd*dy, z+pz+dd*dz,side,slot, size,FaceMicroClass.classID(), material,false,AutoCraft.isAutocrafting);
								nbPlace=sd?nbPlace+1:nbPlace;
							}else{
								sd=false;
							}
						}
					}
					
				};
				return true;
			}else if (mp instanceof EdgeMicroblock){
				EdgeMicroblock mb = (EdgeMicroblock)mp;
				int slot=mb.getSlot();
				int size=mb.getSize();
				int material=mb.getMaterial();
				int tpside=side/2;
				int tpslot=(slot-15)/4;
				if (tpside==tpslot || size!=4){
					return false;
				}
				
				int px=(side==4)?-1:(side==5)?+1:0;
				int py=(side==0)?-1:(side==1)?+1:0;
				int pz=(side==2)?-1:(side==3)?+1:0;
				int realSlot=PlacementUtility.getAdjacentSlot(slot, side);
				if (PlacementUtility.isSlotOnSide(slot, side)){
					px=py=pz=0;
				}
				if (PlacementUtility.place(player, world, x+px, y+py, z+pz,side,realSlot, size,EdgeMicroClass.classID(), material,true,AutoCraft.isAutocrafting )){
					//let's go
					tpslot=(slot-15)/4;
					int dir = tpslot*2;
					int dx=(dir==4)?1:0;
					int dy=(dir==0)?1:0;
					int dz=(dir==2)?1:0;
					int nbPlace=1;
					int du=0;
					int dd=0;
					boolean su=true;
					boolean sd=true;
					while ((su||sd)&&(nbPlace<maxWall)){
						if (su){
							du++;
							if (PlacementUtility.IsValidSupport(world,x+du*dx,y+du*dy,z+du*dz,mb)){
								su=PlacementUtility.place(player, world, x+px+du*dx, y+py+du*dy, z+pz+du*dz,side,realSlot, size,EdgeMicroClass.classID(), material,true,AutoCraft.isAutocrafting);
								nbPlace=su?nbPlace+1:nbPlace;
							}else{
								su=false;
							}
						}
						if (sd && (nbPlace<9)){
							dd--;
							if (PlacementUtility.IsValidSupport(world,x+dd*dx,y+dd*dy,z+dd*dz,mb)){
								sd=PlacementUtility.place(player, world, x+px+dd*dx, y+py+dd*dy, z+pz+dd*dz,side,realSlot, size,EdgeMicroClass.classID(), material,true,AutoCraft.isAutocrafting);
								nbPlace=sd?nbPlace+1:nbPlace;
							}else{
								sd=false;
							}
						}
					}
					
				};
				return true;
			}
		
		}
		
		return false;
	}	
}

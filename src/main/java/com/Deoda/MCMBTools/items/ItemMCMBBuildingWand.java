package com.Deoda.MCMBTools.items;

import com.Deoda.MCMBTools.MBPlacement.MBEdgePlacement;
import com.Deoda.MCMBTools.MBPlacement.MBFacePlacement;
import com.Deoda.MCMBTools.MBPlacement.SlotPlacement;
import com.Deoda.MCMBTools.init.ModItems;
import com.Deoda.MCMBTools.reference.Default;

import codechicken.lib.raytracer.RayTracer;
import codechicken.lib.vec.BlockCoord;
import codechicken.microblock.EdgeMicroblock;
import codechicken.microblock.FaceMicroClass;
import codechicken.microblock.FaceMicroblock;
import codechicken.microblock.FacePlacement;
import codechicken.microblock.Microblock;
import codechicken.microblock.MicroblockPlacement;
import codechicken.microblock.PostMicroblock;
import codechicken.multipart.BlockMultipart;
import codechicken.multipart.TMultiPart;
import codechicken.multipart.TileMultipart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;


public class ItemMCMBBuildingWand extends ItemMCMBT {

	public static int maxWall = Default.MAX_WALL;
	public static int maxPillar = Default.MAX_PILLAR;
	public ItemMCMBBuildingWand() {
		super();
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
			/////// Wall Mode ///////
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
				if (MBFacePlacement.place(player, world, x+px, y+py, z+pz,slot, size, material )&& !player.isSneaking()){
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
							if (IsValidSupport(world,x+du*dx,y+du*dy,z+du*dz,slot,size,material)){
								su=MBFacePlacement.place(player, world, x+px+du*dx, y+py+du*dy, z+pz+du*dz,slot, size, material);
								nbPlace=su?nbPlace+1:nbPlace;
							}else{
								su=false;
							}
						}
						if (sd && (nbPlace<9)){
							dd--;
							if (IsValidSupport(world,x+dd*dx,y+dd*dy,z+dd*dz,slot,size,material)){
								sd=MBFacePlacement.place(player, world, x+px+dd*dx, y+py+dd*dy, z+pz+dd*dz,slot, size, material);
								nbPlace=sd?nbPlace+1:nbPlace;
							}else{
								sd=false;
							}
						}
					}
					
				};
				return true;
			}
			//////// Pillar Mode 1 ////////
			else if (mp instanceof EdgeMicroblock){

				EdgeMicroblock mb = (EdgeMicroblock)mp;
				int slot=mb.getSlot();
				int size=mb.getSize();
				int material=mb.getMaterial();
				//return if side isn't top of pillar
				int tpside=side/2;
				int tpslot=(slot-15)/4;
				if (tpside!=tpslot){
					return false;
				}
				
				int px=(side==4)?-1:(side==5)?+1:0;
				int py=(side==0)?-1:(side==1)?+1:0;
				int pz=(side==2)?-1:(side==3)?+1:0;
				if (MBEdgePlacement.place(player, world, x+px, y+py, z+pz,0,slot, size, material )&& !player.isSneaking()){
					//let's go
					int nbPlace=1;
					int du=1;
					boolean su=true;
					while (su&&(nbPlace<maxPillar)){
						if (su){
							du++;
							//always a valid support
							su=MBEdgePlacement.place(player, world, x+du*px, y+du*py, z+du*pz,0,slot, size, material);
							nbPlace=su?nbPlace+1:nbPlace;
						}
					}
					
				};
				return true;
			}
			//// Pillar mode 2 /////
			else if (mp instanceof PostMicroblock){
				PostMicroblock mb = (PostMicroblock)mp;
				int slot=mb.getShape();
				int size=mb.getSize();
				int material=mb.getMaterial();
				//return if side isn't top of pillar
				if (slot!=side/2){
					return false;
				}
				int px=(side==4)?-1:(side==5)?+1:0;
				int py=(side==0)?-1:(side==1)?+1:0;
				int pz=(side==2)?-1:(side==3)?+1:0;
				if (MBEdgePlacement.place(player, world, x+px, y+py, z+pz,side,-1, size, material )&& !player.isSneaking()){
					//let's go
					int nbPlace=1;
					int du=1;
					boolean su=true;
					while (su&&(nbPlace<maxPillar)){
						if (su){
							du++;
							//always a valid support
							su=MBEdgePlacement.place(player, world, x+du*px, y+du*py, z+du*pz,side,-1, size, material);
							nbPlace=su?nbPlace+1:nbPlace;
						}
					}
					
				};
				return true;
			}
		
		}
		
		return false;
	}

	protected boolean IsValidSupport(World world, int x, int y, int z, int slot,
			int size, int material) {
		TileMultipart tmp = TileMultipart.getTile(world, new BlockCoord(x, y, z));
		if (tmp!=null){
			for (TMultiPart mp : tmp.jPartList()) {
				if (mp instanceof FaceMicroblock){
					FaceMicroblock mb = (FaceMicroblock)mp;
					if(mb.getMaterial()==material && mb.getSlot()==slot && mb.getSize()==size){
						return true;
					}
				}
			}
		}
		return false;
	}
}

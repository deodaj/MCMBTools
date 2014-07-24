package com.Deoda.MCMBTools.items;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.entity.item.ItemEvent;
import codechicken.lib.raytracer.RayTracer;
import codechicken.lib.vec.BlockCoord;
import codechicken.microblock.CornerMicroClass;
import codechicken.microblock.CornerMicroblock;
import codechicken.microblock.EdgeMicroClass;
import codechicken.microblock.EdgeMicroblock;
import codechicken.microblock.FaceMicroClass;
import codechicken.microblock.FaceMicroblock;
import codechicken.microblock.ItemMicroPart;
import codechicken.microblock.MicroMaterialRegistry;
import codechicken.microblock.Microblock;
import codechicken.microblock.PostMicroblock;
import codechicken.multipart.BlockMultipart;
import codechicken.multipart.TMultiPart;
import codechicken.multipart.TileMultipart;

import com.Deoda.MCMBTools.MBPlacement.PlacementUtility;
import com.Deoda.MCMBTools.init.ModItems;
import com.Deoda.MCMBTools.reference.Default;
import com.Deoda.MCMBTools.reference.ID;
import com.Deoda.MCMBTools.reference.Reference;
import com.Deoda.MCMBTools.utility.AutoCraft;

public class ItemMCMBExpandingWand extends ItemMCMBT {
 
	public static int maxExpand = Default.MAX_EXPAND;
	public ItemMCMBExpandingWand() {
		super(ID.itemMCMBExpandingWand);
		this.setUnlocalizedName("MCMBExpandingWand");
		this.setTextureName("mcmbtools:MCMBExpandingWand");
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return Integer.MAX_VALUE;
	}

	
	
	@Override
	public boolean onItemUse(ItemStack stack,
			EntityPlayer player, World world, int x, int y,
			int z, int side, float hitx, float hity, float hitz) {
		if (stack.getTagCompound()==null) stack.setTagCompound(new NBTTagCompound());
		if (stack.getTagCompound().getBoolean("use")){
			return false;
		}
		
		TileMultipart tile = TileMultipart.getTile(world, new BlockCoord(x,y,z));
		MovingObjectPosition hit = RayTracer.retraceBlock(world, player, x, y, z);

		if (hit!=null && tile!=null){
			Object index = BlockMultipart.reduceMOP(hit)._1;
			TMultiPart mp = tile.partList().apply(index);
			if (mp instanceof Microblock){
				Microblock mib = (Microblock)mp;
				stack.getTagCompound().setInteger("placed", 0);
				stack.getTagCompound().setInteger("mat", mib.getMaterial());
				stack.getTagCompound().setInteger("size", mib.getSize());
				stack.getTagCompound().setInteger("side",side);
				stack.getTagCompound().setInteger("x",x);
				stack.getTagCompound().setInteger("y",y);
				stack.getTagCompound().setInteger("z",z);
				stack.getTagCompound().setBoolean("ok", false);
				stack.getTagCompound().setBoolean("edge", true);
				if (mp instanceof FaceMicroblock){
					FaceMicroblock mb = (FaceMicroblock)mp;
					int slot=mb.getSlot();
					int oppSide=side;
					if (side%2==0) oppSide++; else oppSide--;
					if (side!=slot && oppSide!=slot){
						stack.getTagCompound().setInteger("slot", mb.getSlot());
						stack.getTagCompound().setInteger("class", FaceMicroClass.classID());
						stack.getTagCompound().setBoolean("ok", true);
						stack.getTagCompound().setBoolean("use", true);
						stack.getTagCompound().setBoolean("internal", false);
					}
				}
				if (mp instanceof EdgeMicroblock){
					EdgeMicroblock mb = (EdgeMicroblock)mp;
					int slot=mb.getSlot();
					int size=mb.getSize();
					int tpside=side/2;
					int tpslot=(slot-15)/4;
					if (tpside==tpslot){
						stack.getTagCompound().setInteger("slot", mb.getSlot());
						stack.getTagCompound().setInteger("class", EdgeMicroClass.classID());
						stack.getTagCompound().setBoolean("ok", true);
						stack.getTagCompound().setBoolean("use", true);
						stack.getTagCompound().setBoolean("internal", false);
					}else if(mb.getSize()==4){
						stack.getTagCompound().setInteger("slot", mb.getSlot());
						stack.getTagCompound().setInteger("class", EdgeMicroClass.classID());
						stack.getTagCompound().setBoolean("ok", true);
						stack.getTagCompound().setBoolean("use", true);
						stack.getTagCompound().setBoolean("internal", true);		
					}
				}
				if (mp instanceof PostMicroblock){
					PostMicroblock mb = (PostMicroblock)mp;
					int slot=mb.getShape();
					if (slot==side/2){
						stack.getTagCompound().setInteger("slot", mb.getShape());
						stack.getTagCompound().setInteger("class", EdgeMicroClass.classID());
						stack.getTagCompound().setBoolean("edge", false);
						stack.getTagCompound().setBoolean("ok", true);
						stack.getTagCompound().setBoolean("use", true);
						stack.getTagCompound().setBoolean("internal", false);
					}
				}
				if (mp instanceof CornerMicroblock){
					CornerMicroblock mb = (CornerMicroblock)mp;
					if (mb.getSize()==4){
						stack.getTagCompound().setInteger("slot", mb.getSlot());
						stack.getTagCompound().setInteger("class", CornerMicroClass.classID());
						stack.getTagCompound().setBoolean("edge", false);
						stack.getTagCompound().setBoolean("ok", true);
						stack.getTagCompound().setBoolean("use", true);
						stack.getTagCompound().setBoolean("internal", true);
					}
				}
			}
		}
		return false;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world,
			EntityPlayer player) {
		if (stack.getTagCompound()==null) stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setInteger("tick", 0);
		if (stack.getTagCompound().getBoolean("ok")){
			int place=stack.getTagCompound().getInteger("placed")+1;
			int y=stack.getTagCompound().getInteger("y");
			int x=stack.getTagCompound().getInteger("x");
			int z=stack.getTagCompound().getInteger("z");
			int side=stack.getTagCompound().getInteger("side");
			int size=stack.getTagCompound().getInteger("size");
			int mat=stack.getTagCompound().getInteger("mat");
			int slot=stack.getTagCompound().getInteger("slot");
			int mcrClass=stack.getTagCompound().getInteger("class");
			boolean edge=stack.getTagCompound().getBoolean("edge");
			boolean internal=stack.getTagCompound().getBoolean("internal");
			int px=(side==4)?-1:(side==5)?+1:0;
			int py=(side==0)?-1:(side==1)?+1:0;
			int pz=(side==2)?-1:(side==3)?+1:0;
			if(!internal){
				if (PlacementUtility.place(player, world, x+place*px, y+place*py, z+place*pz, side, slot, size, mcrClass, mat,edge, AutoCraft.isAutocrafting)){
					stack.getTagCompound().setInteger("placed", place);
					if (place>=maxExpand){
						stack.getTagCompound().setBoolean("ok", false);
						stack.getTagCompound().setInteger("placed", 0);
						stack.getTagCompound().setBoolean("use", false);
					}
				}else{
					stack.getTagCompound().setBoolean("ok", false);
					stack.getTagCompound().setInteger("placed", 0);
					stack.getTagCompound().setBoolean("use", false);
					
				}
			}else{
				int dp =place+((PlacementUtility.isSlotOnSide(slot,side))?0:1);
				dp = (dp-dp%2)/2;
				int realSlot=(place%2==1)?PlacementUtility.getAdjacentSlot(slot,side):slot;
				if (PlacementUtility.place(player, world, x+dp*px, y+dp*py, z+dp*pz, side, realSlot, size, mcrClass, mat,edge, AutoCraft.isAutocrafting)){
					stack.getTagCompound().setInteger("placed", place);
					if (place>=maxExpand){
						stack.getTagCompound().setBoolean("ok", false);
						stack.getTagCompound().setInteger("placed", 0);
						stack.getTagCompound().setBoolean("use", false);
					}
				}else{
					stack.getTagCompound().setBoolean("ok", false);
					stack.getTagCompound().setInteger("placed", 0);
					stack.getTagCompound().setBoolean("use", false);
					
				}
			}
		}
		return super.onItemRightClick(stack, world, player);
	}

	@Override
	public void onUpdate(ItemStack stack, World world,
			Entity par3Entity, int par4, boolean par5) {
		if (stack.getTagCompound()==null) stack.setTagCompound(new NBTTagCompound());
		int tick = stack.getTagCompound().getInteger("tick");
		if(tick>5){
			stack.getTagCompound().setBoolean("ok", false);
			stack.getTagCompound().setInteger("placed", 0);
			stack.getTagCompound().setBoolean("use", false);
		}else{
			stack.getTagCompound().setInteger("tick",tick+1);			
		}
		super.onUpdate(stack, world, par3Entity, par4, par5);///Yeap, i know i shouldn't do this
	}

}

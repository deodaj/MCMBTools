package com.Deoda.MCMBTools.MBPlacement;

import codechicken.lib.vec.Vector3;
import codechicken.microblock.PlacementGrid;

public class SlotPlacement implements PlacementGrid {

	public int slot;

	
	public SlotPlacement(int slot) {
		super();
		this.slot = slot;
	}

	@Override
	public int getHitSlot(Vector3 arg0, int arg1) {
		return slot;
	}

	@Override
	public void glTransformFace(Vector3 arg0, int arg1) {}
	@Override
	public void render(Vector3 arg0, int arg1) {}

}

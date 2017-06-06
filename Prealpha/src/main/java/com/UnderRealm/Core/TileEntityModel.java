package com.UnderRealm.Core;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityModel extends TileEntity {
	/** 'host' block used to determine the type of complex block we are **/
	private Block host;
	private String ModelID;
	
	TileEntityModel(Block h, String _ModelID)
	{
		super();
		this.host=h;
		this.ModelID=_ModelID;
	}
	
	public Block GetHost() 		{ return host; 		}
	public String GetModelID()  { return ModelID; 	}
	
	/**
     * Determines if this TileEntity requires update calls.
     * @return True if you want updateEntity() to be called, false if not
     */
    public boolean canUpdate()
    {
        return false;
    }
	
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
		//Stub: Prevents base class complaining, this class for now requires no NBT.
    }
}

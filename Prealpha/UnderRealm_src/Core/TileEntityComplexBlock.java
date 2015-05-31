package mod.UnderRealm.Core;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntityComplexBlock extends TileEntity {
	/** 'host' block used to determine the type of complex block we are **/
	private Block host;
	
	TileEntityComplexBlock(Block h)
	{
		super();
		this.host=h;
	}
	
	public Block GetHost() { return host; }
	
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

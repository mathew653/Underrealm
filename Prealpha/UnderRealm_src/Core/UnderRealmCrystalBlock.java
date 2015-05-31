package mod.UnderRealm.Core;

import static net.minecraftforge.common.ForgeDirection.DOWN;
import static net.minecraftforge.common.ForgeDirection.EAST;
import static net.minecraftforge.common.ForgeDirection.NORTH;
import static net.minecraftforge.common.ForgeDirection.SOUTH;
import static net.minecraftforge.common.ForgeDirection.UP;
import static net.minecraftforge.common.ForgeDirection.WEST;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mod.UnderRealm.Client.UnderRealm_ClientCore;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class UnderRealmCrystalBlock extends UnderRealmBaseBlock {

	public UnderRealmCrystalBlock(int par1, Material par2Material) {
		super(par1, par2Material);
		
		this.setTickRandomly(true);
	}
	
	/**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
    	try {
    		return UnderRealm_ClientCore.CrystalRenderID;
    	} 
    	catch (java.lang.NoClassDefFoundError ex)
    	{
    		//Dedicated server shielding.
    		if (MinecraftServer.getServer().isDedicatedServer())
    		{
    			if (ex.getMessage().compareTo("net/minecraft/client/renderer/tileentity/TileEntitySpecialRenderer") == 0) { return -1; }
    		}
    		
    		System.out.println("[underrealm]Warning : Class not found exception : " + ex.getMessage());
    		return -1; //Swallow class not found exception, but still warn.
    	}
    }
    
    @SideOnly(Side.CLIENT)
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
		par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
        par3List.add(new ItemStack(par1, 1, 2));
    }
    
    @SideOnly(Side.CLIENT)

    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        int l;
        float f;
        float f1;
        float f2;

        for (l = 0; l < 3; ++l)
        {
            f = (float)par2 + par5Random.nextFloat();
            f1 = (float)par3 + par5Random.nextFloat() * 0.5F + 0.5F;
            f2 = (float)par4 + par5Random.nextFloat();
            par1World.spawnParticle("smoke", (double)f, (double)f1, (double)f2, 0.0D, 0.0D, 0.0D);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public float[] GetCrystalColor(int meta) {  
    	float[] color = {1.0f,1.0f,1.0f};
    	switch (meta)
    	{
    		//Red(fire)
    		case 0: 
    			color[0]=0.9f;
    			color[1]=0.0f;
    			color[2]=0.0f;
    			break;
    		//Green(earth)
    		case 1: 
    			color[0]=0.0f;
    			color[1]=0.9f;
    			color[2]=0.0f;
    			break;
    		//Blue(water)
    		case 2: 
    			color[0]=0.0f;
    			color[1]=0.0f;
    			color[2]=0.9f;
    			break;
    		default:
    			break;
    	}
        return color;   
    }
    
    @SideOnly(Side.CLIENT)

    /**
     * Returns which pass should this block be rendered on. 0 for solids and 1 for alpha
     */
    public int getRenderBlockPass()
    {
        return 1;
    }
}

package mod.UnderRealm.Core;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mod.UnderRealm.Client.UnderRealm_ClientCore;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

//TODO: Add a client side tile entity to this.
//TODO #2: How do we make the tile entity only tick for the client and why does the server need to know about this?
//Note : TE should use CanUpdate return false.
//Note : TE's still sync location(if there is a way to create a render only object it'll be better).
public class EthrealWall extends UnderRealmBaseBlockContainer {

	public EthrealWall(int par1, Material par2Material) {
		super(par1, par2Material);
		
		//this.setTickRandomly(true);
	}
	
	/**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    @SideOnly(Side.CLIENT)

    /**
     * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
     * coordinates.  Args: blockAccess, x, y, z, side
     */
    public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
    	//Get local player.
    	//EntityPlayer localPlayer = Minecraft.getMinecraft().thePlayer;
    	
    	//TODO : Fix render bugs on air boundrys(disable/invert culling for cirtain faces?).
   		//Draw no sides next to own type of block(disabled for now for now, untill dual side rendering can be sorted).
    	/*
   		//side 0 = -y
   		if ((par5 == 0) &&(par1IBlockAccess.getBlockId(par2, par3-1, par4) == this.blockID)) { return false; }
   		//side 1 = +y
   		if ((par5 == 1) &&(par1IBlockAccess.getBlockId(par2, par3+1, par4) == this.blockID)) { return false; }
   		//side 2 = -z
   		if ((par5 == 2) &&(par1IBlockAccess.getBlockId(par2, par3, par4-1) == this.blockID)) { return false; }
   		//side 3 = +z
   		if ((par5 == 3) &&(par1IBlockAccess.getBlockId(par2, par3, par4+1) == this.blockID)) { return false; }
   		//side 4 = -x
   		if ((par5 == 4) &&(par1IBlockAccess.getBlockId(par2-1, par3, par4) == this.blockID)) { return false; }
   		//side 5 = +x
   		if ((par5 == 5) &&(par1IBlockAccess.getBlockId(par2+1, par3, par4) == this.blockID)) { return false; }
   		*/

        return super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
    }
    
    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    @Override
    public int getRenderType()
    {
        return -1;
    }
    
    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileEntityComplexBlock(this);
    }
    
    /**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
    	boolean safe=false;
    	if (!par1World.isRemote)
    	{
    		if (par5Entity instanceof EntityPlayer)
    		{
    			EntityPlayer Player=(EntityPlayer)par5Entity;
    			/*
    			if (Player.getHeldItem() != null)
    			{
    				if (Player.getHeldItem().getItem() instanceof ItemEtherealRing)
    				{
    					safe = true;
    				}
    			}
    			*/
    			if (Player.isPotionActive(UnderRealmPotionList.EtherealShift)) { safe = true; }
    		
    			if ((safe == false) && 
    					(Math.abs(Player.posX-par2) < 1.0f) && (Math.abs(Player.posY-par3) < 1.0f) && (Math.abs(Player.posZ-par4) < 1.0f))
    			{ 
    				System.out.println("Debug = " + Math.abs(par2-Player.posX) + ":"+ Math.abs(par3-Player.posY) + ":"+ Math.abs(par4-Player.posZ));
    				Player.attackEntityFrom(DamageSource.inWall, 1.0f);
    			}
    		}
    	}
    }

	/**
     * Adds all intersecting collision boxes to a list. (Be sure to only add boxes to the list if they intersect the
     * mask.) Parameters: World, X, Y, Z, mask, list, colliding entity
     */
    public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
    {
    	boolean isSolid=true;
    	
    	//TODO: Test measure, real measure will use potion effects.
    	if (par7Entity instanceof EntityPlayer)
    	{
    		EntityPlayer Player=(EntityPlayer)par7Entity;
    		/*
    		if (Player.getHeldItem() != null)
    		{
    			if (Player.getHeldItem().getItem() instanceof ItemEtherealRing)
    			{
    				isSolid=false;
    			}
    		}
    		*/
    		
    		if (Player.isPotionActive(UnderRealmPotionList.EtherealShift)) { isSolid = false; }

    		//Player.getActivePotionEffect(par1Potion)
    	}

    	if (isSolid)
    	{
    		AxisAlignedBB axisalignedbb1 = this.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
    		
    		if (axisalignedbb1 != null && par5AxisAlignedBB.intersectsWith(axisalignedbb1))
    		{
    			par6List.add(axisalignedbb1);
    		}
    	}
    }
}

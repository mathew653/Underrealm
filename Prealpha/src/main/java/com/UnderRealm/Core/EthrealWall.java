package com.UnderRealm.Core;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.UnderRealm.Api.UnderRealm_Shared;
import com.UnderRealm.Client.TileEntityEtherealWallRenderer;
import com.UnderRealm.Client.UnderRealm_ClientCore;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

//TODO: Add a client side tile entity to this.
//TODO #2: How do we make the tile entity only tick for the client and why does the server need to know about this?
//Note : TE should use CanUpdate return false.
//Note : TE's still sync location(if there is a way to create a render only object it'll be better).
public class EthrealWall extends UnderRealmBaseBlockContainer {

	public EthrealWall(int par1, Material par2Material) {
		super(par1, par2Material);
		
		this.setTickRandomly(true);
	}
	
	/**
     * A randomly called display update to be able to add particles or other items for display
     */
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
		//This might be changed in the future...
		if (TileEntityEtherealWallRenderer.IsHoldingRingItem())
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
    }
    
	/**
     * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
     * coordinates.  Args: blockAccess, x, y, z, side
     */
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_)
    {
        Block block = p_149646_1_.getBlock(p_149646_2_, p_149646_3_, p_149646_4_);

        if (this == UnderRealm_Shared.EthrealWall.get())
        {
            if (p_149646_1_.getBlockMetadata(p_149646_2_, p_149646_3_, p_149646_4_) != p_149646_1_.getBlockMetadata(p_149646_2_ - Facing.offsetsXForSide[p_149646_5_], p_149646_3_ - Facing.offsetsYForSide[p_149646_5_], p_149646_4_ - Facing.offsetsZForSide[p_149646_5_]))
            {
                return true;
            }

            if (block == this)
            {
                return false;
            }
        }

        return block == this ? false : super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_);
    }
    
    /**
     * Returns which pass should this block be rendered on. 0 for solids and 1 for alpha
     */
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass()
    {
        return 1;
    }
    
    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    @Override
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
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityComplexBlock(this);
	}
    
	//At the moment we will block movement for all entity types, our types whom can move through these blocks will use a adjusted path finding measure. 
	@Override
	public boolean getBlocksMovement(IBlockAccess p_149655_1_, int p_149655_2_, int p_149655_3_, int p_149655_4_)
    {
		return true;
       // return !this.blockMaterial.blocksMovement();
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
    				//System.out.println("Debug = " + Math.abs(par2-Player.posX) + ":"+ Math.abs(par3-Player.posY) + ":"+ Math.abs(par4-Player.posZ));
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

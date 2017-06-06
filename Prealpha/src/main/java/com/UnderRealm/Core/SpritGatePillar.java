package com.UnderRealm.Core;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class SpritGatePillar extends UnderRealmBaseWall {
	@SideOnly(Side.CLIENT)
    private IIcon[] iconArray;
	
	public SpritGatePillar(int par1, Material par2Material) {
		//super(par1, Block.stone); //Work around for derriving from wall type.
		
		super(par1, Blocks.stone); //Work around for derriving from wall type.
		setCreativeTab(UnderRealmBlockList.tabUnderRealm);
	}

	
	@SideOnly(Side.CLIENT)

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public IIcon getIcon(int par1, int par2)
    {
		if (par1 == 1) return this.iconArray[1];
        return this.iconArray[0];
    }
	
	/**
     * Determines if a torch can be placed on the top surface of this block.
     * Useful for creating your own block that torches can be on, such as fences.
     *
     * @param world The current world
     * @param x X Position
     * @param y Y Position
     * @param z Z Position
     * @return True to allow the torch to be placed
     */
    public boolean canPlaceTorchOnTop(World world, int x, int y, int z)
    {
        return true;
    }
	
	/**
     * Return whether an adjacent block can connect to a wall.
     */
    public boolean canConnectWallTo(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return false;
    }
    
    @SideOnly(Side.CLIENT)

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    //public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List par3List)
    {
        par3List.add(new ItemStack(p_149666_1_, 1, 0));
    }
    
    @SideOnly(Side.CLIENT)

    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    //public void registerIcons(IIconRegister par1IconRegister)
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
    	this.iconArray = new IIcon[2];
		//Understone meta types.
		this.iconArray[0] = par1IconRegister.registerIcon("underrealmmod:SpiritPillar_Wall");
		this.iconArray[1] = par1IconRegister.registerIcon("underrealmmod:SpiritPillar_Wall_top");
    }
}

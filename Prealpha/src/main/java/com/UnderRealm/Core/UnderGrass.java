package com.UnderRealm.Core;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import com.UnderRealm.Api.UnderRealm_Shared;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class UnderGrass extends UnderRealmBaseBlock {
	@SideOnly(Side.CLIENT)
	private int GrassTypes=1;
    private IIcon[] iconArray_top;
    private IIcon[] iconArray_side;
	private Block[] DirtTypes;
	
	public UnderGrass(int par1, Material par2Material) {
		super(par1, par2Material);
		//this.setTickRandomly(true);
		
		DirtTypes      = new Block[this.GrassTypes];
		DirtTypes[0]   = UnderRealm_Shared.Underdirt.get();	//Ruins dirt.
	}
	
	@SideOnly(Side.CLIENT)
	//public void registerIcons(IIconRegister par1IconRegister)
	public void registerBlockIcons(IIconRegister par1IconRegister)
    {
		iconArray_top  = new IIcon[this.GrassTypes];
		iconArray_side = new IIcon[this.GrassTypes];
		
		this.iconArray_top[0]  = par1IconRegister.registerIcon("underrealmmod:Ruins_grass");
		this.iconArray_side[0] = par1IconRegister.registerIcon("underrealmmod:Ruins_grass_side");
    }

	@SideOnly(Side.CLIENT)
    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public IIcon getIcon(int par1, int par2)
    {
		int Meta=par2;
        return par1 == 1 ? this.iconArray_top[Meta] : (par1 == 0 ? DirtTypes[Meta].getBlockTextureFromSide(par1) : this.iconArray_side[Meta]);
    }
	
	//TODO : Implment dirt type spreading..
}

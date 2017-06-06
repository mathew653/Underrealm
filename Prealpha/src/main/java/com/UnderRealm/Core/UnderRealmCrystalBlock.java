package com.UnderRealm.Core;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.IVMCLoader.ILVF_Colorable;
import com.UnderRealm.Client.UnderRealm_ClientCore;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

public class UnderRealmCrystalBlock extends UnderRealmBaseBlockContainer implements ILVF_Colorable {

	@SideOnly(Side.CLIENT)
    protected IIcon HUD_0;
	
	@SideOnly(Side.CLIENT)
    protected IIcon Particles_0;
	
	/*
	 * fix: Creative mode selection.
	 */
	public int damageDropped(int par1)
    {
		return par1;
    }
	
	public int GetRGBFromMesh(int meta)
	{
		float []colors=GetMeshColor(meta);
		return rgb888(colors[0],colors[1],colors[2]);
	}
	
	/*
	 * TODO: Refine this into a single var
	 */
	@SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess p_149720_1_, int p_149720_2_, int p_149720_3_, int p_149720_4_)
    {
		float []colors=GetMeshColor(p_149720_1_.getBlockMetadata(p_149720_2_, p_149720_3_, p_149720_4_));
		return rgb888(colors[0],colors[1],colors[2]);
		/*switch (p_149720_1_.getBlockMetadata(p_149720_2_, p_149720_3_, p_149720_4_))
		{
			case 0: return 0xFF0000;
			case 1: return 0x00FF00;
			case 2: return 0x0000FF;
			default: return 0xFFFFFF;
		}*/
    }
	
    @Override
    public IIcon getIcon(int side, int meta) {
    	if (side == 1)
    	{
    		return HUD_0;
    	}
    	else
    	{
    		return Particles_0;
    	}
    }
   
    @SideOnly(Side.CLIENT)
    @Override
    //public void registerIcons(IconRegister register) {
    public void registerBlockIcons(IIconRegister register) {
    	HUD_0 = register.registerIcon("underrealmmod:Crystal_Shard");
    	
    	Particles_0 = register.registerIcon("underrealmmod:Crystal_break");
    }
    
	
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
    	return -1;
    	/*try {
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
    	}*/
    }
    
    @Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityModel(this,"models/w_Crystal.ILMC");
	}
    
    @SideOnly(Side.CLIENT)
	//public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List par3List)
    {
		//par3List.add(new ItemStack(par1, 1, 0));
        //par3List.add(new ItemStack(par1, 1, 1));
        //par3List.add(new ItemStack(par1, 1, 2));
    	par3List.add(new ItemStack(p_149666_1_, 1, 0));
        par3List.add(new ItemStack(p_149666_1_, 1, 1));
        par3List.add(new ItemStack(p_149666_1_, 1, 2));
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
    public String GetMeshTexture(int meta) {
    	return "textures/blocks/Crystal_grey.png";
    }
    
    //Util for converting float to RGB number
    private int rgb888 (float r, float g, float b) {
    	return ((int)(r * 255) << 16) | ((int)(g * 255) << 8) | (int)(b * 255);
    }
    
    @SideOnly(Side.CLIENT)
    public float[] GetMeshColor(int meta) {  
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

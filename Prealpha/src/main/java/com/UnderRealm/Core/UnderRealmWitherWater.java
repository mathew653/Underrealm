package com.UnderRealm.Core;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class UnderRealmWitherWater extends UnderRealmBaseFluidBlock {

    @SideOnly(Side.CLIENT)
    protected IIcon stillIcon;
    @SideOnly(Side.CLIENT)
    protected IIcon flowingIcon;
   
    public UnderRealmWitherWater(int id, Fluid fluid, Material material) {
            super(id, fluid, material);
            //setCreativeTab(CreativeTabs.tabMisc);
    }
   
    @Override
    public IIcon getIcon(int side, int meta) {
            return (side == 0 || side == 1)? stillIcon : flowingIcon;
    }
   
    @SideOnly(Side.CLIENT)
    @Override
    //public void registerIcons(IconRegister register) {
    public void registerBlockIcons(IIconRegister register) {
            stillIcon = register.registerIcon("underrealmmod:witherwater");
            flowingIcon = register.registerIcon("underrealmmod:witherwater_flow");
    }
    
    /**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
    	int meta = par1World.getBlockMetadata(par2, par3, par4);

    	if (par5Entity instanceof EntityLivingBase)
    	{
    		EntityLivingBase LivingEntity=(EntityLivingBase)par5Entity;
    		if (!LivingEntity.isPotionActive(Potion.wither))
    		{
    			LivingEntity.addPotionEffect(new PotionEffect(Potion.wither.id, 100));
    		}
    	}
    	
    	//Starve those not in need of food till they need it.
    	//If you will the soul hungers at first for the life slipping away.
    	if (par5Entity instanceof EntityPlayerMP)
    	{
    		EntityPlayerMP PlayerEntity=(EntityPlayerMP)par5Entity;
    		if (!PlayerEntity.getFoodStats().needFood())
    		{
    			PlayerEntity.addPotionEffect(new PotionEffect(Potion.hunger.id, 100));
    		}
    	}
    }
   
    @Override
    public boolean canDisplace(IBlockAccess world, int x, int y, int z) {
            //if (world.getBlockMaterial(x,  y,  z).isLiquid()) return false;
    		if (world.getBlock(x,  y,  z).getMaterial().isLiquid()) return false;
            return super.canDisplace(world, x, y, z);
    }
   
    @Override
    public boolean displaceIfPossible(World world, int x, int y, int z) {
            //if (world.getBlockMaterial(x,  y,  z).isLiquid()) return false;
    		if (world.getBlock(x,  y,  z).getMaterial().isLiquid()) return false;
            return super.displaceIfPossible(world, x, y, z);
    }
   
}
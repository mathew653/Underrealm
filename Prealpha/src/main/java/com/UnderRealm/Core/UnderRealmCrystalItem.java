package com.UnderRealm.Core;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class UnderRealmCrystalItem extends ItemBlock {

	//public UnderRealmCrystalItem(int par1) {
	public UnderRealmCrystalItem(Block par1) {
		//super(par1);
		super(par1);
		setHasSubtypes(true);
	}
	
	public String getUnlocalizedName(ItemStack itemstack)
	{
		switch (itemstack.getItemDamage())
		{
			case 0: return "UnderRealm.crystalfire";
			case 1: return "UnderRealm.crystalearth";
			case 2: return "UnderRealm.crystalwater";
			default: return getUnlocalizedName();
		}
	}
	
	@SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack itemstack, int renderpass)
    {
		if (this.field_150939_a instanceof UnderRealmCrystalBlock)
		{
			UnderRealmCrystalBlock crystal=(UnderRealmCrystalBlock)this.field_150939_a;
			return crystal.GetRGBFromMesh(itemstack.getItemDamage());
		}
		else
		{
			return 0xFFFFFF;
		}
    }

	
	public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
	{
		//Only allow placement in creative mode.
		if (p_77648_2_.capabilities.isCreativeMode) { return super.onItemUse(p_77648_1_, p_77648_2_, p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_); }
		else { return false; }
	}
	
	/**
     * Gets an icon index based on an item's damage value
     */
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int p_77617_1_)
    {
        return this.field_150939_a.getIcon(1, p_77617_1_);
    }

	public int getMetadata(int par1)
    {
		return par1;
	}
}
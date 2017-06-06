package com.UnderRealm.Core;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class UnderStoneItem extends ItemBlock {

	//public UnderStoneItem(int par1) {
	public UnderStoneItem(Block par1) {
		super(par1);
		setHasSubtypes(true);
	}
	
	public String getUnlocalizedName(ItemStack itemstack)
	{
		switch(itemstack.getItemDamage())
		{
			case 0:	return getUnlocalizedName();
			case 1:	return "UnderRealm.UnderCobble";
			case 2:	return "UnderRealm.UnderStoneFace";
			case 3:	return "UnderRealm.UnderStoneHeated";
			default: return getUnlocalizedName();
		}
	}

	public int getMetadata(int par1)
    {
		return par1;
	}
}

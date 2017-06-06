package com.UnderRealm.Core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
//import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class UnderRealmFeatureTestItem extends Item {

	public UnderRealmFeatureTestItem(int par1) {
		//super(par1);
		super();
	}

	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		if (!par2World.isRemote)
		{
			MovingObjectPosition pos = par3EntityPlayer.rayTrace(200, 1.0f);
			//if (pos.typeOfHit != EnumMovingObjectType.ENTITY)
			if (pos.typeOfHit != MovingObjectPosition.MovingObjectType.ENTITY)
			{
				//pos.blockX
				//pos.blockY
				//pos.blockZ
			
				UnderRealmGenCrystalPit hCrystalPit=new UnderRealmGenCrystalPit();
				hCrystalPit.generate(par2World, pos.blockX, pos.blockY, pos.blockZ);
			}
		}
        return par1ItemStack;
    }
}

package com.UnderRealm.Core;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class EtherealShiftPotionEffect extends UnderRealmPotionBase {
	private final ResourceLocation texture = new ResourceLocation("underrealmmod", "textures/debuffs.png");
	
	protected EtherealShiftPotionEffect(int par1, boolean par2, int par3) {
		super(par1, par2, par3);
		
		setEffectiveness(0.25D);
	}
	
	@SideOnly(Side.CLIENT)
	public int getStatusIconIndex()
	{
		 Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		 return super.getStatusIconIndex();
	}

}

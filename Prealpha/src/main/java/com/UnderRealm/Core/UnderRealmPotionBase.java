package com.UnderRealm.Core;

import net.minecraft.potion.Potion;

public class UnderRealmPotionBase extends Potion {
	protected UnderRealmPotionBase(int id, boolean IsBad, int Color) {
		super(id, IsBad, Color);
	}

	public Potion _p_setIconIndex(int p_76399_1_, int p_76399_2_)
	{
		return setIconIndex(p_76399_1_,p_76399_2_);
	}
}

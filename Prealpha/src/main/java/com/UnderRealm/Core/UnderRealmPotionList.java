package com.UnderRealm.Core;

import net.minecraft.potion.Potion;

public class UnderRealmPotionList {
	public static Potion EtherealShift = null; 
	
	public static void Init(int baseID)
	{
		if (GetFreePotionID() != 0)
		{
			System.out.println("[UnderrealmCore]Buff EtherealShift added at : " + GetFreePotionID());
			//EtherealShift = (new EtherealShiftPotionEffect(GetFreePotionID(), false, 5149489)).setPotionName("potion.EtherealShift").setIconIndex(0, 0);
			EtherealShift = ((UnderRealmPotionBase) (new EtherealShiftPotionEffect(GetFreePotionID(), false, 4718847)).setPotionName("potion.EtherealShift"))._p_setIconIndex(0, 0);
		}
	}
	
	public static int GetFreePotionID()
	{
		int FreeSlot=0;
		for (int i=0; i < Potion.potionTypes.length; i++) 
		{	
			if (i > 0)
			{
				if (FreeSlot == 0)
				{
					if (Potion.potionTypes[i] == null) 
					{ 
						System.out.println("[UnderrealmCore]First Free potion slot is " + i);
						FreeSlot=i;
					}
				}
			}
		}
		
		return FreeSlot;
	}
}

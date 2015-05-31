package mod.UnderRealm.Core;

import com.google.common.base.Optional;

import mod.UnderRealm.api.UnderRealm_Shared;
import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class UnderRealmItemList {
	public static Item FTI;	//Internal testing items, no shared requirements.
	
	public static void Init(int baseID)
	{
		FTI=new UnderRealmFeatureTestItem(baseID+1)
		.setFull3D()
		.setUnlocalizedName("FeatureTestItem")
		.setCreativeTab(UnderRealmBlockList.tabUnderRealm)
		.setTextureName("stick");
		GameRegistry.registerItem(FTI, "FeatureTestItem");
		
		//Ethereal Ring
		UnderRealm_Shared.EthrealRing=Optional.of((new ItemEtherealRing(baseID+2).setTextureName("underrealmmod:ethereal_ring").setUnlocalizedName("EtherealRing").setCreativeTab(UnderRealmBlockList.tabUnderRealm)));
		GameRegistry.registerItem(UnderRealm_Shared.EthrealRing.get(), "Ethereal_Ring");
		LanguageRegistry.instance().addName(UnderRealm_Shared.EthrealRing.get(), "Ring of ethereal travel");
	}
}

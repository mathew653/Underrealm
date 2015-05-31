package mod.UnderRealm.Core;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class UnderRealmCrystalItem extends ItemBlock {

	public UnderRealmCrystalItem(int par1) {
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

	public int getMetadata(int par1)
    {
		return par1;
	}
}
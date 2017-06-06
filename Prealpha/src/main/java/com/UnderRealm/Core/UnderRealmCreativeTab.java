package com.UnderRealm.Core;

import com.UnderRealm.Api.UnderRealm_Shared;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class UnderRealmCreativeTab extends CreativeTabs
{
	UnderRealmCreativeTab(String par2Str)
    {
        super(getNextID(), par2Str);
    }

    @SideOnly(Side.CLIENT)

    /**
     * the itemID for the item to be displayed on the tab
     */
    public Item getTabIconItem()
    {
        return Item.getItemFromBlock(UnderRealm_Shared.Understone.get());
    }
    
    //public int getTabIconItemIndex()
    //{
    //    return UnderRealm_Shared.Understone.get().blockID;
    //}
}

package com.UnderRealm.Core;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemEtherealRing extends Item {
	IIcon IconInnert=null;
	public ItemEtherealRing(int par1) {
		//super(par1);
		super();
		setMaxStackSize(1);
	}
	
	@SideOnly(Side.CLIENT)

    /**
     * Returns the icon index of the stack given as argument.
     */
	//Note : Applied to quick bar icon.
    public IIcon getIconIndex(ItemStack par1ItemStack)
    {
		if (par1ItemStack.hasTagCompound())
		{
			int cooldown = par1ItemStack.stackTagCompound.getInteger("cooldown");
			if (cooldown <= 0)
			{
				return this.itemIcon;
			}
			else
			{
				return this.IconInnert;
			}
		}
		
		return this.itemIcon;
    }
	
	@SideOnly(Side.CLIENT)

    /**
     * Gets an icon index based on an item's damage value
     */
	//TODO: The quickbar icon is playing up with this, need to fix that.
	//Note: Applied to in hand item.
	public IIcon getIcon(ItemStack stack, int pass)
    {
		if (stack.hasTagCompound())
		{
			int cooldown = stack.stackTagCompound.getInteger("cooldown");
			if (cooldown <= 0)
			{
				return this.itemIcon;
			}
			else
			{
				return this.IconInnert;
			}
		}
		
		return this.itemIcon;
    }
	
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon(this.getIconString());
        this.IconInnert = par1IconRegister.registerIcon("underrealmmod:ethereal_ring_innert");
    }
	
	@SideOnly(Side.CLIENT)

    /**
     * allows items to add custom lines of information to the mouseover description
     */
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
		par3List.add(EnumChatFormatting.GRAY + "The face is always staring.");
		
		//Only display cool down if item has the right bits defined.
		if (par1ItemStack.hasTagCompound())
		{
			if (par1ItemStack.stackTagCompound.hasKey("cooldown"))
			{
				int cooldown = par1ItemStack.stackTagCompound.getInteger("cooldown");
				par3List.add(EnumChatFormatting.RED + "Cooldown : " + cooldown);
			}
		}
    }
	
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		if (par1ItemStack.hasTagCompound())
		{
			int cooldown = par1ItemStack.stackTagCompound.getInteger("cooldown");
			if (cooldown <= 0)
			{
				if (!par3EntityPlayer.isPotionActive(UnderRealmPotionList.EtherealShift))
				{
					//TODO : There maybe particular items that will cancel these effects when used.
					List<ItemStack> CurableItems=new ArrayList<ItemStack>();
					PotionEffect Effect=new PotionEffect(UnderRealmPotionList.EtherealShift.id, 130 * 20, 0);
					Effect.setCurativeItems(CurableItems);
			
					par3EntityPlayer.addPotionEffect(Effect);
				
					//Tests had this at 140(seems a good initial cool down just slightly too quick).
					cooldown += 150 * 20; //Add cool down.
					par1ItemStack.stackTagCompound.setInteger("cooldown", (int)cooldown);
				}
			}
			else
			{
				if (!par2World.isRemote)
				{
					//par3EntityPlayer.addChatMessage(EnumChatFormatting.RED + "This item is still cooling down!" + EnumChatFormatting.RESET);
					par3EntityPlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "This item is still cooling down!" + EnumChatFormatting.RESET));
				}
			}
		}
        return par1ItemStack;
    }
	
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) 
	{
		//System.out.println("Stats : " + par1ItemStack + " : " + par3Entity);
		if (par1ItemStack.hasTagCompound())
		{
			//If we do not have a cool down, add one.
			if (!par1ItemStack.stackTagCompound.hasKey("cooldown"))
			{
				par1ItemStack.stackTagCompound.setInteger("cooldown", (int)0);	//Inital cooldown is zero.
			}
			
			int cooldown = par1ItemStack.stackTagCompound.getInteger("cooldown");
			if (cooldown > 0)
			{
				cooldown -= 1;
				par1ItemStack.stackTagCompound.setInteger("cooldown", (int)cooldown);
			}
		}
		else
		{
			//No item tags list create it.
			par1ItemStack.stackTagCompound = new NBTTagCompound();
		}
	}

}

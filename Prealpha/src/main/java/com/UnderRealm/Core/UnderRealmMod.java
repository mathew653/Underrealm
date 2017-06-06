package com.UnderRealm.Core;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

//import org.bouncycastle.util.Arrays;

import com.UnderRealm.Client.UnderRealm_ClientCore;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.potion.Potion;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
//import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.*;

@Mod(modid = "UnderRealmCore", name = "Under Realm Mod", version = "1.0", dependencies = "required-after:ILMCLoader_Forge")
//@NetworkMod(clientSideRequired = true, serverSideRequired = true)

//Client side stuff
//MinecraftForgeClient.registerItemRenderer(int itemID, IItemRenderer renderer)
//MinecraftForgeclient.getItemRenderer(ItemStack item, ItemRenderType type)

//Common stuff
//@ForgeSubscribe for even subscribing.

public class UnderRealmMod  {
	public static UnderRealmConfig config;
	
	public static BiomeGenBase URBareBiome;
	public static BiomeGenBase.Height height_URBareBiome;
	
	@Instance("UnderRealmCore")
	public static UnderRealmMod instance;
	
	// Says where the client and server 'proxy' code is loaded.
    @SidedProxy(clientSide = "com.UnderRealm.Client.UnderRealm_ClientCore", serverSide = "com.UnderRealm.Core.UnderRealm_ServerCore")
    public static UnderRealm_ServerCore cs_proxy;
    
    //Container arrays.
    Potion[] potionTypes = null;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		System.out.println("[UnderrealmCore]Under realm mod preinit.");
		
		int TotalBlocks=5; //Number of block types(Stone, Ore, Faces, crystals, Wither water)
		config=new UnderRealmConfig(event.getSuggestedConfigurationFile());
		
		config.load();
		config.setupEnvironment(0);
		
		UnderRealmBlockList.Init(config.BaseID);
		UnderRealmItemList.Init(config.BaseItemID);
		
		//Potion reflection work around(Reports tell me the arrays limits are 255 aka 0xFF).
		//Tutorial notes 256, likely a working value.
		if (Potion.potionTypes.length < 256)
		{
			for (Field f : Potion.class.getDeclaredFields()) {
				try {
					//1.6.4 field_76425_a,potionTypes
					//1.6.4 and 1.7.10 use the same mapping.
					if (f.getName().equals("potionTypes") || f.getName().equals("field_76425_a")) { //1.6.4 and 1.7.10 use the same mapping.
						Field modfield = Field.class.getDeclaredField("modifiers");
						modfield.setAccessible(true);
						modfield.setInt(f, f.getModifiers() & ~Modifier.FINAL);
						
						potionTypes = (Potion[])f.get(null);
						final Potion[] newPotionTypes = new Potion[256];
						System.arraycopy(potionTypes, 0, newPotionTypes, 0, potionTypes.length);
						f.set(null, newPotionTypes);
						
						System.out.println("[UnderrealmCore]Extended potion array to 256");
					}
				}
				catch (Exception e) {
					System.err.println("[UnderrealmCore]Failed to extend potion array reported error :");
					System.err.println(e);
				}
			}
		}
		
		UnderRealmPotionList.Init(0);
		
		cs_proxy.init();
		//System.out.println("Test - Crystal render ID is : " + UnderRealm_ClientCore.CrystalRenderID);
		
		//241 out of 256
		height_URBareBiome = new BiomeGenBase.Height(0.1F, 0.2F);
		URBareBiome = (new UnderRealmBareBiome(config.BaseBiomeID+1))
				      .setColor(16421912)
				      .setBiomeName("Under realm bare land")
				      .setDisableRain()
				      .setTemperatureRainfall(2.0F, 0.0F)
				      .setHeight(height_URBareBiome);
//				      .setMinMaxHeight(-1.0F, 0.2F);
		
		config.save();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		System.out.println("[UnderrealmCore]Under realm mod init.");
	}
	
	@EventHandler
	public void load(FMLInitializationEvent event)
	{
		System.out.println("[UnderrealmCore]Under realm mod load.");
		//Register stuff here after blocks are setup.
		
		//Register the under realm dimension and setup its provider and dim ID correctly.
		UnderRealmDimProvider.ProviderDimID=config.BaseDimID;
		DimensionManager.registerProviderType(config.BaseDimID, UnderRealmDimProvider.class, false);
		DimensionManager.registerDimension(config.BaseDimID, config.BaseDimID);
	}

	@EventHandler
	public static void postInit(FMLPostInitializationEvent event) {
		//Can use to hook various events that might be handy in usage of tracking player actions and mob spawning.
		//MinecraftForge.EVENT_BUS.register(new UnderRealmEventHandler());
		
		//Not available under 1.6.4 it seems
		//FMLCommonHandler.instance().bus().register(new UnderRealmEventHandler_FMLCH());
	}
}

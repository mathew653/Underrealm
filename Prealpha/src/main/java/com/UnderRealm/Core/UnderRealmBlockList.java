package com.UnderRealm.Core;

import com.UnderRealm.Api.UnderRealm_Shared;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.oredict.OreDictionary;

import com.google.common.base.Optional;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class UnderRealmBlockList {
	
	//TODO : Give this its own icon and such.
	public static final CreativeTabs tabUnderRealm = new UnderRealmCreativeTab("UnderRealm");
	
	public static final Material EtherealMaterial = (new Ethreal_Material(MapColor.stoneColor));
	
	public static void Init(int BaseID)
	{
		//Understone
		//UnderRealm_Shared.Understone=Optional.of((new UnderStone(BaseID+1, Material.rock)).setHardness(1.0f).setUnlocalizedName("UnderRealm.UnderStone").setCreativeTab(tabUnderRealm)); //601
		UnderRealm_Shared.Understone=Optional.of((new UnderStone(BaseID+1, Material.rock)).setHardness(1.0f).setBlockName("UnderRealm.UnderStone").setCreativeTab(tabUnderRealm)); //601
		GameRegistry.registerBlock(UnderRealm_Shared.Understone.get(), UnderStoneItem.class, "Understone");
		LanguageRegistry.instance().addName(new ItemStack(UnderRealm_Shared.Understone.get(), 1, 0), "Understone");
		LanguageRegistry.instance().addName(new ItemStack(UnderRealm_Shared.Understone.get(), 1, 1), "Understone cobble");
		LanguageRegistry.instance().addName(new ItemStack(UnderRealm_Shared.Understone.get(), 1, 2), "Understone with face");
		LanguageRegistry.instance().addName(new ItemStack(UnderRealm_Shared.Understone.get(), 1, 3), "Heated understone");
		
		OreDictionary.registerOre("cobblestone", new ItemStack(UnderRealm_Shared.Understone.get(), 1, 1));
		
		//FurnaceRecipes.smelting().addSmelting(UnderRealm_Shared.Understone.get().blockID, 1, new ItemStack(UnderRealm_Shared.Understone.get(), 1, 0), 0.1F);
		GameRegistry.addSmelting(UnderRealm_Shared.Understone.get(), new ItemStack(UnderRealm_Shared.Understone.get(), 1, 0), 0.1F);
		
		//TODO : Does heated understone have any use in crafting?(assuming it is silk mined)
		
		//Wither water
		UnderRealm_Shared.WitherWaterFluid = new UnderRealmWitherWaterFluid();
		//UnderRealm_Shared.Wither_Water = Optional.of((new UnderRealmWitherWater(BaseID+2, UnderRealm_Shared.WitherWaterFluid, Material.water)).setLightValue(0.325F).setCreativeTab(tabUnderRealm));
		UnderRealm_Shared.Wither_Water = Optional.of((new UnderRealmWitherWater(BaseID+2, UnderRealm_Shared.WitherWaterFluid, Material.water)).setLightLevel(0.325F).setCreativeTab(tabUnderRealm));
		GameRegistry.registerBlock(UnderRealm_Shared.Wither_Water.get(), "Wither_Water");
		LanguageRegistry.instance().addName(UnderRealm_Shared.Wither_Water.get(), "Wither Water");
		
		//UnderRealm_Shared.Crystals = Optional.of((new UnderRealmCrystalBlock(BaseID+3, Material.glass)).setHardness(1.0f).setLightValue(0.525F).setStepSound(Block.soundGlassFootstep).setCreativeTab(tabUnderRealm));
		UnderRealm_Shared.Crystals = Optional.of((new UnderRealmCrystalBlock(BaseID+3, Material.glass)).setHardness(1.0f).setLightLevel(0.525F).setStepSound(Block.soundTypeGlass).setCreativeTab(tabUnderRealm));
		GameRegistry.registerBlock(UnderRealm_Shared.Crystals.get(), UnderRealmCrystalItem.class, "UnderRealm_Crystal");
		LanguageRegistry.instance().addName(new ItemStack(UnderRealm_Shared.Crystals.get(), 1, 0), "Crystal(fire)");
		LanguageRegistry.instance().addName(new ItemStack(UnderRealm_Shared.Crystals.get(), 1, 1), "Crystal(earth)");
		LanguageRegistry.instance().addName(new ItemStack(UnderRealm_Shared.Crystals.get(), 1, 2), "Crystal(water)");
		
		//Spirit gate stuff.
		//UnderRealm_Shared.SpiritGatePillar = Optional.of((new SpritGatePillar(BaseID+4, Material.rock)).setHardness(1.0f).setUnlocalizedName("UnderRealm.Spirit_Pillar"));
		UnderRealm_Shared.SpiritGatePillar = Optional.of((new SpritGatePillar(BaseID+4, Material.rock)).setHardness(1.0f).setBlockName("UnderRealm.Spirit_Pillar"));
		GameRegistry.registerBlock(UnderRealm_Shared.SpiritGatePillar.get(), "Spirit_Pillar");
		LanguageRegistry.instance().addName(UnderRealm_Shared.SpiritGatePillar.get(), "Spirit gate pillar");
		
		//Dungeon stuff
		//Ethereal Wall, will have a custom material and step sound, so that when hit, broke and such it makes custom noises.
		//TODO : Dummy texture, this block will mimic various dungeon blocks.
		//UnderRealm_Shared.EthrealWall = Optional.of((new EthrealWall(BaseID+5, EtherealMaterial)).setHardness(1.0f).setLightOpacity(15).setUnlocalizedName("UnderRealm.EthrealWall").setTextureName("minecraft:stone").setCreativeTab(tabUnderRealm));
		UnderRealm_Shared.EthrealWall = Optional.of((new EthrealWall(BaseID+5, EtherealMaterial)).setHardness(1.0f).setLightOpacity(0).setBlockName("UnderRealm.EthrealWall").setBlockTextureName("minecraft:stone").setCreativeTab(tabUnderRealm));
		GameRegistry.registerBlock(UnderRealm_Shared.EthrealWall.get(), "Ethereal_Wall");
		LanguageRegistry.instance().addName(new ItemStack(UnderRealm_Shared.EthrealWall.get(), 1, 0), "Ethereal wall");
		
		//Underdirt
		//UnderRealm_Shared.Underdirt=Optional.of((new UnderDirt(BaseID+6, Material.ground)).setStepSound(Block.soundGravelFootstep).setHardness(1.0f).setUnlocalizedName("UnderRealm.UnderDirt").setCreativeTab(tabUnderRealm).setTextureName("underrealmmod:Ruins_dirt"));
		UnderRealm_Shared.Underdirt=Optional.of((new UnderDirt(BaseID+6, Material.ground)).setStepSound(Block.soundTypeGravel).setHardness(1.0f).setBlockName("UnderRealm.UnderDirt").setCreativeTab(tabUnderRealm).setBlockTextureName("underrealmmod:Ruins_dirt"));
		GameRegistry.registerBlock(UnderRealm_Shared.Underdirt.get(), "UnderDirt");
		LanguageRegistry.instance().addName(new ItemStack(UnderRealm_Shared.Underdirt.get(), 1, 0), "Ruins Dirt");
		
		//Undergrass
		//Note : Texture needs improvement.
		//UnderRealm_Shared.Undergrass=Optional.of((new UnderGrass(BaseID+7, Material.grass)).setStepSound(Block.soundGrassFootstep).setHardness(1.0f).setUnlocalizedName("UnderRealm.UnderGrass").setCreativeTab(tabUnderRealm));
		UnderRealm_Shared.Undergrass=Optional.of((new UnderGrass(BaseID+7, Material.grass)).setStepSound(Block.soundTypeGrass).setHardness(1.0f).setBlockName("UnderRealm.UnderGrass").setCreativeTab(tabUnderRealm));
		GameRegistry.registerBlock(UnderRealm_Shared.Undergrass.get(), "UnderGrass");
		LanguageRegistry.instance().addName(new ItemStack(UnderRealm_Shared.Undergrass.get(), 1, 0), "Ruins Grass");
		
		//Plants.
		//UnderRealm_Shared.UnderPlants=Optional.of(reference);
		
		//itemGroup.UnderRealmBlocks
		LanguageRegistry.instance().addStringLocalization("itemGroup.UnderRealm", "Under realm");
	}
}

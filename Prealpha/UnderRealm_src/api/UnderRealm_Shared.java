package mod.UnderRealm.api;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.Fluid;

import com.google.common.base.Optional;

//Interface class for interacting with blocks and items.
public class UnderRealm_Shared {
	//!Blocks
	public static Optional<? extends Block> Understone   = Optional.absent();	//Stone type blocks. | Face block atm has no trigger type.
	public static Optional<? extends Block> Undergrass   = Optional.absent();	//Grass type blocks.
	public static Optional<? extends Block> Underdirt    = Optional.absent();	//Dirt type blocks.
	public static Optional<? extends Block> Wither_Water = Optional.absent();	//Almost entirely implemented, maybe custom debuff instead of hunger.
	public static Fluid WitherWaterFluid;										//Fluid class for wither water.
	
	public static Optional<? extends BlockFlower> UnderPlants = Optional.absent();	//Plants for underrealm.
	
	public static Optional<? extends Block> Crystals     = Optional.absent();   //These need to drop shards and have a chance to spawn a cirtain monster.
	
	//~Spirit gate elements.
	//TODO : Spirit gates need to be both implemented and spawn in.
	//public static Optional<? extends Block> SpiritGateRift = Optional.absent();
	//public static Optional<? extends Block> SpiritGateStairs = Optional.absent();
	public static Optional<? extends Block> SpiritGatePillar = Optional.absent();
	
	//~Dungeon elements.
	public static Optional<? extends Block> EthrealWall = Optional.absent();
	
	//!Items
	//~Dungeon elements.
	public static Optional<? extends Item> EthrealRing = Optional.absent();
}

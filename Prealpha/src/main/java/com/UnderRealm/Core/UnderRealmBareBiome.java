package com.UnderRealm.Core;

import java.util.Random;

import com.UnderRealm.Api.UnderRealm_Shared;
import net.minecraft.block.Block;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class UnderRealmBareBiome extends BiomeGenBase
{
    public UnderRealmBareBiome(int par1)
    {
        super(par1);
        this.spawnableCreatureList.clear();			//Clear passive mob spawning list
        this.spawnableMonsterList.clear();			//Clear agressive mob spawning list
        this.spawnableCaveCreatureList.clear();		//Clear mobs that spawn in caves list
        this.spawnableWaterCreatureList.clear();	//Clear mobs that spawn in water list
        //Normal mob spawn list
        //this.spawnableCreatureList.add(new SpawnListEntry(EntitySheep.class, 12, 4, 4));
        //this.spawnableCreatureList.add(new SpawnListEntry(EntityPig.class, 10, 4, 4));
        //this.spawnableCreatureList.add(new SpawnListEntry(EntityChicken.class, 10, 4, 4));
        //this.spawnableCreatureList.add(new SpawnListEntry(EntityCow.class, 8, 4, 4));
        //this.spawnableMonsterList.add(new SpawnListEntry(EntitySpider.class, 10, 4, 4));
        //this.spawnableMonsterList.add(new SpawnListEntry(EntityZombie.class, 10, 4, 4));
        //this.spawnableMonsterList.add(new SpawnListEntry(EntitySkeleton.class, 10, 4, 4));
        //this.spawnableMonsterList.add(new SpawnListEntry(EntityCreeper.class, 10, 4, 4));
        //this.spawnableMonsterList.add(new SpawnListEntry(EntitySlime.class, 10, 4, 4));
        //this.spawnableMonsterList.add(new SpawnListEntry(EntityEnderman.class, 1, 1, 4));
        //this.spawnableWaterCreatureList.add(new SpawnListEntry(EntitySquid.class, 10, 4, 4));
        //this.spawnableCaveCreatureList.add(new SpawnListEntry(EntityBat.class, 10, 8, 8));
        
        this.spawnableMonsterList.add(new SpawnListEntry(EntitySkeleton.class, 10, 1, 2));
        
        //this.topBlock = (byte)UnderRealm_Shared.Understone.get().blockID;
        //this.fillerBlock = (byte)UnderRealm_Shared.Understone.get().blockID;
        this.topBlock = UnderRealm_Shared.Understone.get();
        this.fillerBlock = UnderRealm_Shared.Understone.get();
        this.theBiomeDecorator.treesPerChunk = -999;
        this.theBiomeDecorator.deadBushPerChunk = 2;
        this.theBiomeDecorator.reedsPerChunk = 50;
        //this.theBiomeDecorator.cactiPerChunk = 10;
    }

    public void decorate(World par1World, Random par2Random, int par3, int par4)
    {
        super.decorate(par1World, par2Random, par3, par4);

        //if (par2Random.nextInt(1000) == 0)
        //{
        //    int k = par3 + par2Random.nextInt(16) + 8;
        //    int l = par4 + par2Random.nextInt(16) + 8;
        //    WorldGenDesertWells worldgendesertwells = new WorldGenDesertWells();
        //    worldgendesertwells.generate(par1World, par2Random, k, par1World.getHeightValue(k, l) + 1, l);
        //}
    }
}


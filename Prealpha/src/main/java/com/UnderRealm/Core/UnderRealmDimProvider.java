package com.UnderRealm.Core;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
//import net.minecraft.util.Vec3Pool;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.biome.WorldChunkManagerHell;

public class UnderRealmDimProvider extends WorldProvider
{
	static int ProviderDimID=0;	//Set on mod initialisation before mod adds provider for dimension
    public UnderRealmDimProvider()
    {
    }

    public void registerWorldChunkManager()
    {
    	//This might change to allow more biome types.
        //this.worldChunkMgr = new WorldChunkManagerHell(UnderRealmMod.URBareBiome, 1.0F, 1.0F);
    	this.worldChunkMgr = new WorldChunkManagerHell(UnderRealmMod.URBareBiome, 1.0F);
    	
        this.hasNoSky = true;
        this.dimensionId = this.ProviderDimID;
    }

    /**
     * Returns a new chunk provider which generates chunks for this world
     */
    public IChunkProvider createChunkGenerator()
    {
        return new UnderRealmChunkProvider(this.worldObj, this.worldObj.getSeed());
    }

    public String getDimensionName()
    {
        return "The Under Realm";
    }

    public boolean canRespawnHere()
    {
        return false;
    }
    
    /**
     * Calculates the angle of sun and moon in the sky relative to a specified time (usually worldTime)
     */
    public float calculateCelestialAngle(long par1, float par3)
    {
        return 0.5F;
    }
    
    @SideOnly(Side.CLIENT)
    /**
     * Returns true if the given X,Z coordinate should show environmental fog.
     */
    public boolean doesXZShowFog(int par1, int par2)
    {
        return true;
    }
    
    //Fixup, set world sky color to black.
    @SideOnly(Side.CLIENT)
    public Vec3 getSkyColor(Entity cameraEntity, float partialTicks)
    {
    	return Vec3.createVectorHelper(0.0f, 0.0f, 0.0f);
        //return this.worldObj.getWorldVec3Pool().getVecFromPool(0.0f, 0.0f, 0.0f);
    }
}

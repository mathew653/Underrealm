package com.UnderRealm.Core;

//import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.SHROOM;
//import static net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.FIRE;
//import static net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.GLOWSTONE;
//import static net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.NETHER_LAVA;

import java.util.List;
import java.util.Random;

import com.UnderRealm.Api.UnderRealm_Shared;

import cpw.mods.fml.common.eventhandler.Event.Result;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderHell;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.feature.WorldGenFire;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenGlowStone1;
import net.minecraft.world.gen.feature.WorldGenGlowStone2;
import net.minecraft.world.gen.feature.WorldGenHellLava;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
//import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.terraingen.ChunkProviderEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

public class UnderRealmChunkProvider extends ChunkProviderHell {

	/** RNG. */
    private Random rand;
	
    private World worldObj;
    
	private double[] noiseField;
    
	/** A NoiseGeneratorOctaves used in generating nether terrain */
    private NoiseGeneratorOctaves netherNoiseGen1;
    private NoiseGeneratorOctaves netherNoiseGen2;
    private NoiseGeneratorOctaves netherNoiseGen3;
	
	/** A NoiseGeneratorOctaves used in generating terrain */
    private NoiseGeneratorOctaves noiseGen4;
	
	double[] noiseData1;
    double[] noiseData2;
    double[] noiseData3;
    double[] noiseData4;
    double[] noiseData5;
    
    private double[] stoneNoise = new double[256];
    
    /** The biomes that are used to generate the chunk */
    private BiomeGenBase[] biomesForGeneration;
    
    //private static int StoneID;
    //private static int FluidID;
    private static Block StoneBlock;
    private static Block FluidBlock;
    
    private WorldGenLakes WitherWaterLakeGenerator;
    private UnderRealmGenSpikes spikeGen;
    private UnderRealmGenCrystalPit CrystalPitGen;

	public UnderRealmChunkProvider(World par1World, long par2) {
		super(par1World, par2);
		
		//ID for stone and fluid
		//TODO: Add meta data for these here.
	    //FluidID=UnderRealm_Shared.Wither_Water.get().blockID; // Wither water.
	    //StoneID=UnderRealm_Shared.Understone.get().blockID;   // Understone.
		FluidBlock=UnderRealm_Shared.Wither_Water.get(); // Wither water.
	    StoneBlock=UnderRealm_Shared.Understone.get();   // Understone.
		
		worldObj=par1World;
		
		this.rand = new Random(par2);
		
		this.netherNoiseGen1 = new NoiseGeneratorOctaves(this.rand, 16);
        this.netherNoiseGen2 = new NoiseGeneratorOctaves(this.rand, 16);
		this.netherNoiseGen3 = new NoiseGeneratorOctaves(this.rand, 8);
		
		this.WitherWaterLakeGenerator = new WorldGenLakes(FluidBlock);
		
		this.spikeGen = new UnderRealmGenSpikes(StoneBlock);
		
		this.CrystalPitGen = new UnderRealmGenCrystalPit();
	}
	
	/**
     * Populates chunk with ores etc etc
     * Removed hell world generation code sofar.
     */
    public void populate(IChunkProvider par1IChunkProvider, int par2, int par3)
    {
        BlockSand.fallInstantly = true;

        //MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(par1IChunkProvider, worldObj, hellRNG, par2, par3, false));

        int k = par2 * 16;
        int l = par3 * 16;
        //this.genNetherBridge.generateStructuresInChunk(this.worldObj, this.hellRNG, par2, par3);
        int i1;
        int j1;
        int k1;
        int l1;
        int i2;
        
        if (this.spikeGen != null && this.rand.nextInt(4) == 0)
        {
        	l1 = k + this.rand.nextInt(16) + 8;
            k1 = this.rand.nextInt(128);
            i2 = l + this.rand.nextInt(16) + 8;
        	this.spikeGen.generate(this.worldObj, this.rand, l1, k1, i2);
        }
        
        if (this.WitherWaterLakeGenerator != null && this.rand.nextInt(4) == 0)
        {
            l1 = k + this.rand.nextInt(16) + 8;
            k1 = this.rand.nextInt(128);
            i2 = l + this.rand.nextInt(16) + 8;
            this.WitherWaterLakeGenerator.generate(this.worldObj, this.rand, l1, k1, i2);
        }
        
        if (this.CrystalPitGen != null && this.rand.nextInt(4) == 0)
        {
        	l1 = k + this.rand.nextInt(16) + 8;
        	k1 = this.rand.nextInt(128);
        	i2 = l + this.rand.nextInt(16) + 8;
        	
        	this.CrystalPitGen.generate(this.worldObj, this.rand, l1, k1, i2);
        }

        //boolean doGen = TerrainGen.populate(par1IChunkProvider, worldObj, hellRNG, par2, par3, false, NETHER_LAVA);
        //for (i1 = 0; doGen && i1 < 8; ++i1)
        //{
        //    j1 = k + this.hellRNG.nextInt(16) + 8;
        //    k1 = this.hellRNG.nextInt(120) + 4;
        //    l1 = l + this.hellRNG.nextInt(16) + 8;
        //    (new WorldGenHellLava(Block.lavaMoving.blockID, false)).generate(this.worldObj, this.hellRNG, j1, k1, l1);
        //}

        //i1 = this.hellRNG.nextInt(this.hellRNG.nextInt(10) + 1) + 1;
        //int i2;

        //doGen = TerrainGen.populate(par1IChunkProvider, worldObj, hellRNG, par2, par3, false, FIRE);
        //for (j1 = 0; doGen && j1 < i1; ++j1)
        //{
        //    k1 = k + this.hellRNG.nextInt(16) + 8;
        //    l1 = this.hellRNG.nextInt(120) + 4;
        //    i2 = l + this.hellRNG.nextInt(16) + 8;
        //    (new WorldGenFire()).generate(this.worldObj, this.hellRNG, k1, l1, i2);
        //}

        //i1 = this.hellRNG.nextInt(this.hellRNG.nextInt(10) + 1);

        //doGen = TerrainGen.populate(par1IChunkProvider, worldObj, hellRNG, par2, par3, false, GLOWSTONE);
        //for (j1 = 0; doGen && j1 < i1; ++j1)
        //{
        //    k1 = k + this.hellRNG.nextInt(16) + 8;
        //    l1 = this.hellRNG.nextInt(120) + 4;
        //    i2 = l + this.hellRNG.nextInt(16) + 8;
        //    (new WorldGenGlowStone1()).generate(this.worldObj, this.hellRNG, k1, l1, i2);
        //}

        //for (j1 = 0; doGen && j1 < 10; ++j1)
        //{
        //    k1 = k + this.hellRNG.nextInt(16) + 8;
        //    l1 = this.hellRNG.nextInt(128);
        //    i2 = l + this.hellRNG.nextInt(16) + 8;
        //    (new WorldGenGlowStone2()).generate(this.worldObj, this.hellRNG, k1, l1, i2);
        //}

        //MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(worldObj, hellRNG, k, l));
        
        //doGen = TerrainGen.decorate(worldObj, hellRNG, k, l, SHROOM);
        //if (doGen && this.hellRNG.nextInt(1) == 0)
        //{
        //    j1 = k + this.hellRNG.nextInt(16) + 8;
        //    k1 = this.hellRNG.nextInt(128);
        //    l1 = l + this.hellRNG.nextInt(16) + 8;
        //    (new WorldGenFlowers(Block.mushroomBrown.blockID)).generate(this.worldObj, this.hellRNG, j1, k1, l1);
        //}

        //if (doGen && this.hellRNG.nextInt(1) == 0)
        //{
        //    j1 = k + this.hellRNG.nextInt(16) + 8;
        //    k1 = this.hellRNG.nextInt(128);
        //    l1 = l + this.hellRNG.nextInt(16) + 8;
        //    (new WorldGenFlowers(Block.mushroomRed.blockID)).generate(this.worldObj, this.hellRNG, j1, k1, l1);
        //}

        //WorldGenMinable worldgenminable = new WorldGenMinable(Block.oreNetherQuartz.blockID, 13, Block.netherrack.blockID);
        int j2;
        //
        //for (k1 = 0; k1 < 16; ++k1)
        //{
        //    l1 = k + this.hellRNG.nextInt(16);
        //    i2 = this.hellRNG.nextInt(108) + 10;
        //    j2 = l + this.hellRNG.nextInt(16);
        //    worldgenminable.generate(this.worldObj, this.hellRNG, l1, i2, j2);
        //}

        //for (k1 = 0; k1 < 16; ++k1)
        //{
        //    l1 = k + this.hellRNG.nextInt(16);
        //    i2 = this.hellRNG.nextInt(108) + 10;
        //    j2 = l + this.hellRNG.nextInt(16);
        //    (new WorldGenHellLava(Block.lavaMoving.blockID, true)).generate(this.worldObj, this.hellRNG, l1, i2, j2);
        //}

        //MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(worldObj, hellRNG, k, l));
        //MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Post(par1IChunkProvider, worldObj, hellRNG, par2, par3, false));

        BlockSand.fallInstantly = false;
    }
    
    /**
     * Will return back a chunk, if it doesn't exist and its not a MP client it will generates all the blocks for the
     * specified chunk from the map seed and chunk seed
     */
    public Chunk provideChunk(int par1, int par2)
    {
        this.rand.setSeed((long)par1 * 341873128712L + (long)par2 * 132897987541L);
        
        //The original function used a byte for the abyte array where as we are using a short as our blockID's go over 255
        //We can also manipulate meta data here also.
        byte[]  mbyte = new byte[32768];  	// Meta data.
        //short[] abyte = new short[32768]; // Block IDs(1.6.4).
        Block[] ablock = new Block[32768];	// Block handles(1.7.10).
        
        this.biomesForGeneration = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, par1 * 16, par2 * 16, 16, 16);
        
        this.generateUnderRealmTerrain(par1, par2, ablock, mbyte);
        //this.EXreplaceBlocksForBiome(par1, par2, abyte, this.biomesForGeneration);
        this.WriteBedRock(par1, par2, ablock);
        
        //this.netherCaveGenerator.generate(this, this.worldObj, par1, par2, abyte);
        //this.genNetherBridge.generate(this, this.worldObj, par1, par2, abyte);
        //Chunk chunk = new UnderRealmChunk(this.worldObj, abyte, mbyte, par1, par2); // Fixup : Allow additional block range via custom chunk class
        Chunk chunk = new UnderRealmChunk(this.worldObj, ablock, mbyte, par1, par2); // Fixup : Allow additional block range via custom chunk class
        byte[] abyte1 = chunk.getBiomeArray();

        for (int k = 0; k < abyte1.length; ++k)
        {
            abyte1[k] = (byte)this.biomesForGeneration[k].biomeID;
        }

        //chunk.resetRelightChecks();
        chunk.generateSkylightMap();
        return chunk;
    }
    
    /**
     * Returns a list of creatures of the specified type that can spawn at the given location.
     */
    public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4)
    {
        //if (par1EnumCreatureType == EnumCreatureType.monster)
        //{
            //if (this.genNetherBridge.hasStructureAt(par2, par3, par4))
            //{
            //    return this.genNetherBridge.getSpawnList();
            //}

            //if (this.genNetherBridge.func_142038_b(par2, par3, par4) && this.worldObj.getBlockId(par2, par3 - 1, par4) == Block.netherBrick.blockID)
            //{
            //    return this.genNetherBridge.getSpawnList();
            //}
        //}

        BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(par2, par4);
        return biomegenbase == null ? null : biomegenbase.getSpawnableList(par1EnumCreatureType);
    }
    
    /**
     * Generates the shape of the terrain in the nether.
     */
    //Custom chunk class uses a short value and supports meta data.
    //TODO : The end water level might be lower.
    //public void generateUnderRealmTerrain(int par1, int par2, short[] par3ArrayOfShort, byte[] MetaArray)
    public void generateUnderRealmTerrain(int par1, int par2, Block[] par3ArrayOfBlock, byte[] MetaArray)
    {
    	byte b0 = 4;
        byte WaterLevel = 32;
        int k = b0 + 1;
        byte b2 = 17;
        int l = b0 + 1;
        this.noiseField = this.initializeNoiseField(this.noiseField, par1 * b0, 0, par2 * b0, k, b2, l);

        for (int x1 = 0; x1 < b0; ++x1)
        {
            for (int z1 = 0; z1 < b0; ++z1)
            {
                for (int y1 = 0; y1 < 16; ++y1)
                {
                    double d0 = 0.125D;
                    double d1 = this.noiseField[((x1 + 0) * l + z1 + 0) * b2 + y1 + 0];
                    double d2 = this.noiseField[((x1 + 0) * l + z1 + 1) * b2 + y1 + 0];
                    double d3 = this.noiseField[((x1 + 1) * l + z1 + 0) * b2 + y1 + 0];
                    double d4 = this.noiseField[((x1 + 1) * l + z1 + 1) * b2 + y1 + 0];
                    double d5 = (this.noiseField[((x1 + 0) * l + z1 + 0) * b2 + y1 + 1] - d1) * d0;
                    double d6 = (this.noiseField[((x1 + 0) * l + z1 + 1) * b2 + y1 + 1] - d2) * d0;
                    double d7 = (this.noiseField[((x1 + 1) * l + z1 + 0) * b2 + y1 + 1] - d3) * d0;
                    double d8 = (this.noiseField[((x1 + 1) * l + z1 + 1) * b2 + y1 + 1] - d4) * d0;

                    for (int l1 = 0; l1 < 8; ++l1)
                    {
                        double d9 = 0.25D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;

                        for (int i2 = 0; i2 < 4; ++i2)
                        {
                            int j2 = i2 + x1 * 4 << 11 | 0 + z1 * 4 << 7 | y1 * 8 + l1;
                            
                            short short1 = 128;
                            double d14 = 0.25D;
                            double d15 = d10;
                            double d16 = (d11 - d10) * d14;

                            for (int k2 = 0; k2 < 4; ++k2)
                            {
                                //int l2 = 0;
                            	Block block = null;
                                int meta = 0;

                                if (y1 * 8 + l1 < WaterLevel)
                                {
                                    //l2 = FluidID;
                                	block = FluidBlock;
                                }

                                if (d15 > 0.0D)
                                {
                                    //l2 = StoneID;
                                	block = StoneBlock;
                                }

                                //par3ArrayOfShort[j2] = (short)l2;
                                par3ArrayOfBlock[j2] = block;
                                MetaArray[j2]=(byte)meta;
                                j2 += short1;
                                d15 += d16;
                            }

                            d10 += d12;
                            d11 += d13;
                        }

                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }
                }
            }
        }
    }

    /**
     * name based on ChunkProviderGenerate
     */
    //public void WriteBedRock(int par1, int par2, short[] par3ArrayOfByte)
    public void WriteBedRock(int par1, int par2, Block[] par3ArrayOfBlock)
    {
    	byte b0 = 64;
        double d0 = 0.03125D;

        for (int k = 0; k < 16; ++k)
        {
            for (int l = 0; l < 16; ++l)
            {
                for (int k1 = 127; k1 >= 0; --k1)
                {
                    int l1 = (l * 16 + k) * 128 + k1;

                    if (k1 < 127 - this.rand.nextInt(5) && k1 > 0 + this.rand.nextInt(5))
                    {
                    }
                    else
                    {
                    	//par3ArrayOfByte[l1]=(short)Block.bedrock.blockID;
                    	par3ArrayOfBlock[l1]=Blocks.bedrock;
                    }
                }
            }
        }
    }
    
    /**
     * name based on ChunkProviderGenerate
     */
    //TODO : Fix wrap around in biome data caused by byte being passed 601(>255)
    public void EXreplaceBlocksForBiome(int par1, int par2, short[] par3ArrayOfByte, BiomeGenBase[] par4ArrayOfBiomeGenBase)
    {
//        ChunkProviderEvent.ReplaceBiomeBlocks event = new ChunkProviderEvent.ReplaceBiomeBlocks(this, par1, par2, par3ArrayOfByte, par4ArrayOfBiomeGenBase);
        //MinecraftForge.EVENT_BUS.post(event);
        //if (event.getResult() == Result.DENY) return;
//
//        byte b0 = 63;
//        double d0 = 0.03125D;
//        this.stoneNoise = this.noiseGen4.generateNoiseOctaves(this.stoneNoise, par1 * 16, par2 * 16, 0, 16, 16, 1, d0 * 2.0D, d0 * 2.0D, d0 * 2.0D);
//
//        for (int k = 0; k < 16; ++k)
//        {
//            for (int l = 0; l < 16; ++l)
//            {
//                BiomeGenBase biomegenbase = par4ArrayOfBiomeGenBase[l + k * 16];
//                float f = biomegenbase.getFloatTemperature();
//                int i1 = (int)(this.stoneNoise[k + l * 16] / 3.0D + 3.0D + this.rand.nextDouble() * 0.25D);
//                int j1 = -1;
//                byte b1 = biomegenbase.topBlock;
//                byte b2 = biomegenbase.fillerBlock;
//
//                for (int k1 = 127; k1 >= 0; --k1)
//                {
//                    int l1 = (l * 16 + k) * 128 + k1;
//
//                    if (k1 <= 0 + this.rand.nextInt(5))
//                    {
//                        par3ArrayOfByte[l1] = (byte)Block.bedrock.blockID;
//                    }
//                    else
//                    {
//                        byte b3 = par3ArrayOfByte[l1];
//
//                        if (b3 == 0)
//                        {
//                            j1 = -1;
//                        }
//                        else if (b3 == StoneID)
//                        {
//                            if (j1 == -1)
//                            {
//                                if (i1 <= 0)
//                                {
//                                    b1 = 0;
//                                    b2 = (byte)StoneID;
//                                }
//                                else if (k1 >= b0 - 4 && k1 <= b0 + 1)
//                                {
//                                    b1 = biomegenbase.topBlock;
//                                    b2 = biomegenbase.fillerBlock;
//                                }
//
//                                if (k1 < b0 && b1 == 0)
//                                {
//                                    if (f < 0.15F)
//                                    {
//                                        b1 = (byte)Block.ice.blockID;
//                                    }
//                                    else
//                                    {
//                                        b1 = (byte)FluidID;
//                                    }
//                                }
//
//                                j1 = i1;
//
//                                if (k1 >= b0 - 1)
//                                {
//                                    par3ArrayOfByte[l1] = b1;
//                                }
//                                else
//                                {
//                                    par3ArrayOfByte[l1] = b2;
//                                }
//                            }
//                            else if (j1 > 0)
//                            {
//                                --j1;
//                                par3ArrayOfByte[l1] = b2;

                                //Atm unused.
                                //if (j1 == 0 && b2 == Block.sand.blockID)
                                //{
                                //    j1 = this.rand.nextInt(4);
                                //    b2 = (byte)Block.sandStone.blockID;
                                //}
//                            }
//                        }
//                    }
//                }
//            }
//        }
    }

    /**
     * generates a subset of the level's terrain data. Takes 7 arguments: the [empty] noise array, the position, and the
     * size.
     */
    private double[] initializeNoiseField(double[] par1ArrayOfDouble, int par2, int par3, int par4, int par5, int par6, int par7)
    {
        ChunkProviderEvent.InitNoiseField event = new ChunkProviderEvent.InitNoiseField(this, par1ArrayOfDouble, par2, par3, par4, par5, par6, par7);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.getResult() == Result.DENY) return event.noisefield;
        if (par1ArrayOfDouble == null)
        {
            par1ArrayOfDouble = new double[par5 * par6 * par7];
        }

        double d0 = 684.412D;
        double d1 = 2053.236D;
        this.noiseData4 = this.netherNoiseGen6.generateNoiseOctaves(this.noiseData4, par2, par3, par4, par5, 1, par7, 1.0D, 0.0D, 1.0D);
        this.noiseData5 = this.netherNoiseGen7.generateNoiseOctaves(this.noiseData5, par2, par3, par4, par5, 1, par7, 100.0D, 0.0D, 100.0D);
        this.noiseData1 = this.netherNoiseGen3.generateNoiseOctaves(this.noiseData1, par2, par3, par4, par5, par6, par7, d0 / 80.0D, d1 / 60.0D, d0 / 80.0D);
        this.noiseData2 = this.netherNoiseGen1.generateNoiseOctaves(this.noiseData2, par2, par3, par4, par5, par6, par7, d0, d1, d0);
        this.noiseData3 = this.netherNoiseGen2.generateNoiseOctaves(this.noiseData3, par2, par3, par4, par5, par6, par7, d0, d1, d0);
        int k1 = 0;
        int l1 = 0;
        double[] adouble1 = new double[par6];
        int i2;

        for (i2 = 0; i2 < par6; ++i2)
        {
            adouble1[i2] = Math.cos((double)i2 * Math.PI * 6.0D / (double)par6) * 2.0D;
            double d2 = (double)i2;

            if (i2 > par6 / 2)
            {
                d2 = (double)(par6 - 1 - i2);
            }

            if (d2 < 4.0D)
            {
                d2 = 4.0D - d2;
                adouble1[i2] -= d2 * d2 * d2 * 10.0D;
            }
        }

        for (i2 = 0; i2 < par5; ++i2)
        {
            for (int j2 = 0; j2 < par7; ++j2)
            {
                double d3 = (this.noiseData4[l1] + 256.0D) / 512.0D;

                if (d3 > 1.0D)
                {
                    d3 = 1.0D;
                }

                double d4 = 0.0D;
                double d5 = this.noiseData5[l1] / 8000.0D;

                if (d5 < 0.0D)
                {
                    d5 = -d5;
                }

                d5 = d5 * 3.0D - 3.0D;

                if (d5 < 0.0D)
                {
                    d5 /= 2.0D;

                    if (d5 < -1.0D)
                    {
                        d5 = -1.0D;
                    }

                    d5 /= 1.4D;
                    d5 /= 2.0D;
                    d3 = 0.0D;
                }
                else
                {
                    if (d5 > 1.0D)
                    {
                        d5 = 1.0D;
                    }

                    d5 /= 6.0D;
                }

                d3 += 0.5D;
                d5 = d5 * (double)par6 / 16.0D;
                ++l1;

                for (int k2 = 0; k2 < par6; ++k2)
                {
                    double d6 = 0.0D;
                    double d7 = adouble1[k2];
                    double d8 = this.noiseData2[k1] / 512.0D;
                    double d9 = this.noiseData3[k1] / 512.0D;
                    double d10 = (this.noiseData1[k1] / 10.0D + 1.0D) / 2.0D;

                    if (d10 < 0.0D)
                    {
                        d6 = d8;
                    }
                    else if (d10 > 1.0D)
                    {
                        d6 = d9;
                    }
                    else
                    {
                        d6 = d8 + (d9 - d8) * d10;
                    }

                    d6 -= d7;
                    double d11;

                    if (k2 > par6 - 4)
                    {
                        d11 = (double)((float)(k2 - (par6 - 4)) / 3.0F);
                        d6 = d6 * (1.0D - d11) + -10.0D * d11;
                    }

                    if ((double)k2 < d4)
                    {
                        d11 = (d4 - (double)k2) / 4.0D;

                        if (d11 < 0.0D)
                        {
                            d11 = 0.0D;
                        }

                        if (d11 > 1.0D)
                        {
                            d11 = 1.0D;
                        }

                        d6 = d6 * (1.0D - d11) + -10.0D * d11;
                    }

                    par1ArrayOfDouble[k1] = d6;
                    ++k1;
                }
            }
        }

        return par1ArrayOfDouble;
    }
    
}

package com.UnderRealm.Core;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

public class UnderRealmChunk extends Chunk {
	
	/**
     * Used to store block IDs, block MSBs, Sky-light maps, Block-light maps, and metadata. Each entry corresponds to a
     * logical segment of 16x16x16 blocks, stacked vertically.
     */
    private ExtendedBlockStorage[] storageArrays;
	
	//public void SetupChunk(World world, short[] ids, byte[] metadata, int chunkX, int chunkZ)
    public void SetupChunk(World world, Block[] blks, byte[] metadata, int chunkX, int chunkZ)
    {
        //this(world, chunkX, chunkZ);
        //int k = ids.length / 256;
    	int k = blks.length / 256;

        for (int x = 0; x < 16; ++x)
        {
            for (int z = 0; z < 16; ++z)
            {
                for (int y = 0; y < k; ++y)
                {
                    //int idx = x << 11 | z << 7 | y;
                    ////int id = ids[idx] & 0xFF;
                    //int id = ids[idx] & 0xFFFFFF;
                    //int meta = metadata[idx];
                	
                	int k1 = x * k * 16 | z * k | y;
                	Block block = blks[k1];
                	int meta = metadata[k1];

                    //if (id != 0)
                	if (block != null && block != Blocks.air)
                    {
                        int l = y >> 4;

                        if (this.storageArrays[l] == null)
                        {
                            this.storageArrays[l] = new ExtendedBlockStorage(l << 4, !world.provider.hasNoSky);
                        }

                        //this.storageArrays[l].setExtBlockID(x, y & 15, z, id);
                        this.storageArrays[l].func_150818_a(x, y & 15, z, block);
                        this.storageArrays[l].setExtBlockMetadata(x, y & 15, z, meta);
                    }
                }
            }
        }
        
        super.setStorageArrays(this.storageArrays);
    }

	//public UnderRealmChunk(World world, short[] ids, byte[] metadata, int chunkX, int chunkZ) {
	public UnderRealmChunk(World world, Block[] blks, byte[] metadata, int chunkX, int chunkZ) {
		//super(world, ids, metadata, chunkX, chunkZ);
		super(world, chunkX, chunkZ);
		
		this.storageArrays = new ExtendedBlockStorage[16];
		
		//SetupChunk(world, ids, metadata, chunkX, chunkZ);
		SetupChunk(world, blks, metadata, chunkX, chunkZ);
	}

}

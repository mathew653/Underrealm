package mod.UnderRealm.Core;

import java.util.Random;

import mod.UnderRealm.api.UnderRealm_Shared;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class UnderRealmGenCrystalPit
{
	public Random rng = new Random();
	
    public UnderRealmGenCrystalPit()
    {
    	rng.setSeed(System.nanoTime());
    }
    
    public int Crystal_RandomiseMeta()
    {
    	int Value=rng.nextInt(2);
    	switch (rng.nextInt(10))
    	{
    		case 2: Value=0; break;
    		case 4: Value=1; break;
    		case 6: Value=2; break;
    		default: break;
    	}
    	return Value;
    }

    public boolean generate(World par1World, Random par2Random, int wx, int wy, int wz)
    {
    	rng=par2Random;
    	return generate(par1World, wx, wy, wz);
    }
    
    public boolean generate(World par1World, int wx, int wy, int wz)
    {
    	int radius=32;
    	int depth=32;
    	int noseMax=2;
    	int XZNoise=rng.nextInt(noseMax);
    	int Curve=3;
    	int Curvex=0;
    	int Curvez=0;
    	
    	//Check to see if all area where feature is generated is solid.
    	for (int y=0;y < depth; y++)
    	{
    		for (int x=0; x < (radius/2)+Curvex+noseMax-y; x++) //Dimish only y
    		{
    			for (int z=0; z < (radius/2)+Curvez+noseMax-y-x; z++) //Dimish y axis and x axis.
    			{
    				//+x,+z
    				if (!par1World.getBlockMaterial(wx+x, wy-y, wz+z).isSolid()) { return false; }
    				//-x,+z
    				if (!par1World.getBlockMaterial(wx-x, wy-y, wz+z).isSolid()) { return false; }
    				//+x,-z
    				if (!par1World.getBlockMaterial(wx+x, wy-y, wz-z).isSolid()) { return false; }
    				//-x,-z
    				if (!par1World.getBlockMaterial(wx-x, wy-y, wz-z).isSolid()) { return false; }
    				
    				Curvex=x % Curve;
    				Curvez=z % Curve;
    			}
    		}
    	}
    	
    	int blockID=-1;
    	for (int y=0;y < depth; y++)
    	{
    		if (y <= 8)       { blockID=-1; }
    		else              { blockID=UnderRealm_Shared.Wither_Water.get().blockID; }
    		
    		for (int x=0; x < (radius/2)+Curvex+XZNoise-y; x++) //Dimish only y
    		{
    			for (int z=0; z < (radius/2)+Curvez+XZNoise-y-x; z++) //Dimish y axis and x axis.
    			{
    				//+x,+z
    				if (par1World.getBlockMaterial(wx+x, wy-y, wz+z).isSolid())
    				{
    					if (blockID == -1)
    					{
    						par1World.setBlockToAir(wx+x, wy-y, wz+z);
    					}
    					else
    					{
    						par1World.setBlock(wx+x, wy-y, wz+z, blockID);
    					}
    				}
    				
    				//-x,+z
    				if (par1World.getBlockMaterial(wx-x, wy-y, wz+z).isSolid())
    				{
    					if (blockID == -1)
    					{
    						par1World.setBlockToAir(wx-x, wy-y, wz+z);
    					}
    					else
    					{
    						par1World.setBlock(wx-x, wy-y, wz+z, blockID);
    					}
    				}
    				
    				//+x,-z
    				if (par1World.getBlockMaterial(wx+x, wy-y, wz-z).isSolid())
    				{
    					if (blockID == -1)
    					{
    						par1World.setBlockToAir(wx+x, wy-y, wz-z);
    					}
    					else
    					{
    						par1World.setBlock(wx+x, wy-y, wz-z, blockID);
    					}
    				}
    				
    				//-x,-z
    				if (par1World.getBlockMaterial(wx-x, wy-y, wz-z).isSolid())
    				{
    					if (blockID == -1)
    					{
    						par1World.setBlockToAir(wx-x, wy-y, wz-z);
    					}
    					else
    					{
    						par1World.setBlock(wx-x, wy-y, wz-z, blockID);
    					}
    				}
    				
    				Curvex=x % Curve;
    				Curvez=z % Curve;
    			}
    		}
    		XZNoise=rng.nextInt(noseMax);
    	}
    	
    	//TODO : Add correct block and create vars for generation features.
    	for (int y=0;y < depth; y++)
    	{
    		if (y < 9)
    		{
    			for (int x=0; x < (radius/2)+Curvex+XZNoise-y; x++) //Dimish only y
    			{
    				for (int z=0; z < (radius/2)+Curvez+XZNoise-y-x; z++) //Dimish y axis and x axis.
    				{
    					if ((rng.nextInt(20) == 1) && par1World.getBlockMaterial(wx+x, wy-y-1, wz+z).isSolid() 
    											   && !par1World.getBlockMaterial(wx+x, wy-y-1, wz+z).isLiquid()) 
    					{ par1World.setBlock(wx+x, wy-y, wz+z, UnderRealm_Shared.Crystals.get().blockID,Crystal_RandomiseMeta(),3); }
    					
    					if ((rng.nextInt(20) == 1) && par1World.getBlockMaterial(wx-x, wy-y-1, wz+z).isSolid() 
    							                   && !par1World.getBlockMaterial(wx-x, wy-y-1, wz+z).isLiquid()) 
    					{ par1World.setBlock(wx-x, wy-y, wz+z, UnderRealm_Shared.Crystals.get().blockID,Crystal_RandomiseMeta(),3); }
    					
    					if ((rng.nextInt(20) == 1) && par1World.getBlockMaterial(wx+x, wy-y-1, wz-z).isSolid() 
    											   && !par1World.getBlockMaterial(wx+x, wy-y-1, wz-z).isLiquid()) 
    					{ par1World.setBlock(wx+x, wy-y, wz-z, UnderRealm_Shared.Crystals.get().blockID,Crystal_RandomiseMeta(),3); }
    					
    					if ((rng.nextInt(20) == 1) && par1World.getBlockMaterial(wx-x, wy-y-1, wz-z).isSolid() 
    							                   && !par1World.getBlockMaterial(wx-x, wy-y-1, wz-z).isLiquid()) 
    					{ par1World.setBlock(wx-x, wy-y, wz-z, UnderRealm_Shared.Crystals.get().blockID,Crystal_RandomiseMeta(),3); }
    				
    					Curvex=x % Curve;
    					Curvez=z % Curve;
    				}
    			}
    		}
    	}
    	
    	//Punch through to surface(max of 16 blocks.)
    	int slope=8;
    	for (int yh=1;yh < slope; yh++)
    	{
    		for (int x=0; x < (radius/2)+Curvex+XZNoise; x++) //Dimish only y
			{
				for (int z=0; z < (radius/2)+Curvez+XZNoise-x; z++) //Dimish y axis and x axis.
				{
					//+x,+z
    				if (par1World.getBlockMaterial(wx+x, wy+yh, wz+z).isSolid()) { par1World.setBlockToAir(wx+x, wy+yh, wz+z); }
    				//-x,+z
    				if (par1World.getBlockMaterial(wx-x, wy+yh, wz+z).isSolid()) { par1World.setBlockToAir(wx-x, wy+yh, wz+z); }
    				//+x,-z
    				if (par1World.getBlockMaterial(wx+x, wy+yh, wz-z).isSolid()) { par1World.setBlockToAir(wx+x, wy+yh, wz-z); }
    				//-x,-z
    				if (par1World.getBlockMaterial(wx-x, wy+yh, wz-z).isSolid()) { par1World.setBlockToAir(wx-x, wy+yh, wz-z); }
					
					Curvex=x % Curve;
					Curvez=z % Curve;
				}
			}
    		if ((slope - yh) > 0)
    		{
    			slope=slope - yh;
    		}
    	}
    	
    	par1World.setBlock(wx, 148, wz, Block.blockLapis.blockID);
    	return true;
    }
}


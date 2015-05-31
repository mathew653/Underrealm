package mod.UnderRealm.Core;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class UnderStone extends UnderRealmBaseBlock {
	public static final byte UnderStoneTypeCount=4+7;
    @SideOnly(Side.CLIENT)
    private Icon[] iconArray;

	public UnderStone(int par1, Material par2Material) {
		super(par1, par2Material);
	}
	
	public int damageDropped(int par1)
    {
		switch (par1)
		{
			case 3: return par1;	//Heated won't actually break but for now it will drop its self.
			
			//Spirit pillars drop them self'ves(allows middle click op to work)
			case 4:  return par1; 
			case 5:  return par1; 
    		case 6:  return par1;
    		case 7:  return par1;
    		case 8:  return par1;
    		case 9:  return par1;
    		case 10: return par1;
    		
			default: return 1;		//Unless otherwise specified, we should shatter into cobble.
		}
    }
	
	public int quantityDropped(int meta, int fortune, Random random)
    {
        if (meta == 3) { return 0; }
        return 1;
    }
	
	/**
     * Called when a player removes a block.  This is responsible for
     * actually destroying the block, and the block is intact at time of call.
     * This is called regardless of whether the player can harvest the block or
     * not.
     *
     * Return true if the block is actually destroyed.
     *
     * Note: When used in multiplayer, this is called on both client and
     * server sides!
     *
     */
	// Heated is harvastable if you use silk touch, otherwise it turns to lava.
    public boolean removeBlockByPlayer(World world, EntityPlayer player, int x, int y, int z)
    {
    	int meta=this.getDamageValue(world, x, y, z);
    	if (meta == 3)
    	{
    		if (this.canSilkHarvest(world, player, x, y, z, meta) && EnchantmentHelper.getSilkTouchModifier(player))
    		{
    			return world.setBlockToAir(x, y, z);
    		}
    		else
    		{
    			if (!world.isRemote) { world.markBlockForUpdate(x, y, z); }
    			return world.setBlock(x, y, z, Block.lavaStill.blockID,0,3);
    		}
    	}
    	else
    	{
    		return world.setBlockToAir(x, y, z);
    	}
    }
    
    public int getLightValue(IBlockAccess world, int x, int y, int z)
    {
    	int meta=world.getBlockMetadata(x, y, z);
    	if (meta == 3)
    	{
    		return (int)(15.0F * 0.5f); // Emit 0.5F light value if we are heated.
    	}
    	
    	/*If we don't need special handling, ask forge and hence vanilla what to do about lighting.*/
        Block block = blocksList[world.getBlockId(x, y, z)];
        if (block != null && block != this)
        {
            return block.getLightValue(world, x, y, z);
        }
        return lightValue[blockID];
    }
    
    public boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int metadata)
    {
    	return true;
    }
    
    /**
     * Called when the block is destroyed by an explosion.
     * Useful for allowing the block to take into account tile entities,
     * metadata, etc. when exploded, before it is removed.
     *
     * @param world The current world
     * @param x X Position
     * @param y Y Position
     * @param z Z Position
     * @param Explosion The explosion instance affecting the block
     */
    public void onBlockExploded(World world, int x, int y, int z, Explosion explosion)
    {
    	int meta=world.getBlockMetadata(x, y, z);
    	if (meta == 3)
    	{
    		world.setBlock(x, y, z, Block.lavaStill.blockID,0,3);
    	}
    	else
    	{
    		world.setBlockToAir(x, y, z);
    	}
        onBlockDestroyedByExplosion(world, x, y, z, explosion);
    }
    
    /**
     * Returns the block hardness at a location. Args: world, x, y, z
     */
    //TODO: Implement spirit pillar properties.
    public float getBlockHardness(World par1World, int par2, int par3, int par4)
    {
        return this.blockHardness;
    }
	
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int par1, int par2)
    {
        if (par2 < 0 || par2 >= this.iconArray.length)
        {
            par2 = 0;
        }
        
        if (par1 != 1) //Check to see if we do not have a top side.
        {
        	//If we have a rune block, we should make all sides short of top blank.
        	switch (par2)
        	{
        		case 5:  return this.iconArray[4]; 
        		case 6:  return this.iconArray[4];
        		case 7:  return this.iconArray[4];
        		case 8:  return this.iconArray[4];
        		case 9:  return this.iconArray[4];
        		case 10: return this.iconArray[4];
        		default: break;
        	}
        }

        return this.iconArray[par2];
    }
	
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
		for (byte i=0; i < UnderStoneTypeCount; i++)
		{
			par3List.add(new ItemStack(par1, 1, i));
		}
		//par3List.add(new ItemStack(par1, 1, 0));
        //par3List.add(new ItemStack(par1, 1, 1));
        //par3List.add(new ItemStack(par1, 1, 2));
        //par3List.add(new ItemStack(par1, 1, 3));
    }
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
    {
		this.iconArray = new Icon[UnderStoneTypeCount];
		//Understone meta types.
		this.iconArray[0] = par1IconRegister.registerIcon("underrealmmod:Understone");
		this.iconArray[1] = par1IconRegister.registerIcon("underrealmmod:Understone_cobble");
		this.iconArray[2] = par1IconRegister.registerIcon("underrealmmod:Understone_face");
		this.iconArray[3] = par1IconRegister.registerIcon("underrealmmod:Understone_heated_anim");
		
		//Spirit pillar meta types.
		this.iconArray[4]  = par1IconRegister.registerIcon("underrealmmod:SpiritPillarRune_Blank");
		this.iconArray[5]  = par1IconRegister.registerIcon("underrealmmod:SpiritPillarRune_A");
		this.iconArray[6]  = par1IconRegister.registerIcon("underrealmmod:SpiritPillarRune_B");
		this.iconArray[7]  = par1IconRegister.registerIcon("underrealmmod:SpiritPillarRune_C");
		this.iconArray[8]  = par1IconRegister.registerIcon("underrealmmod:SpiritPillarRune_d");
		this.iconArray[9]  = par1IconRegister.registerIcon("underrealmmod:SpiritPillarRune_e");
		this.iconArray[10] = par1IconRegister.registerIcon("underrealmmod:SpiritPillarRune_f");
    }

}

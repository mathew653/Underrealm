package mod.UnderRealm.Core;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mod.UnderRealm.api.UnderRealm_Shared;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;

public class UnderGrass extends Block {
	@SideOnly(Side.CLIENT)
	private int GrassTypes=1;
    private Icon[] iconArray_top;
    private Icon[] iconArray_side;
	private Block[] DirtTypes;
	
	public UnderGrass(int par1, Material par2Material) {
		super(par1, par2Material);
		//this.setTickRandomly(true);
		
		DirtTypes      = new Block[this.GrassTypes];
		DirtTypes[0]   = UnderRealm_Shared.Underdirt.get();	//Ruins dirt.
	}
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
    {
		iconArray_top  = new Icon[this.GrassTypes];
		iconArray_side = new Icon[this.GrassTypes];
		
		this.iconArray_top[0]  = par1IconRegister.registerIcon("underrealmmod:Ruins_grass");
		this.iconArray_side[0] = par1IconRegister.registerIcon("underrealmmod:Ruins_grass_side");
    }

	@SideOnly(Side.CLIENT)
    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getIcon(int par1, int par2)
    {
		int Meta=par2;
        return par1 == 1 ? this.iconArray_top[Meta] : (par1 == 0 ? DirtTypes[Meta].getBlockTextureFromSide(par1) : this.iconArray_side[Meta]);
    }
	
	//TODO : Implment dirt type spreading..
}

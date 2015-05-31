package mod.UnderRealm.Client;

import java.net.URL;

import org.lwjgl.opengl.GL11;

import mod.IVMCLoader.IVMC_Container;
import mod.UnderRealm.Core.UnderRealmCrystalBlock;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.client.model.ModelFormatException;
import net.minecraftforge.client.model.obj.Face;
import net.minecraftforge.client.model.obj.GroupObject;
import net.minecraftforge.client.model.obj.TextureCoordinate;
import net.minecraftforge.client.model.obj.Vertex;
import net.minecraftforge.client.model.obj.WavefrontObject;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.ReflectionHelper;

public class UnderRealmCrystalRenderer implements ISimpleBlockRenderingHandler {
	//private IVMC_Container CrystalMesh;
	private IModelCustom CrystalMesh;
	private IVMC_Container CrystalMesh_container;
	private final ResourceLocation texture = new ResourceLocation("underrealmmod", "textures/blocks/Crystal_grey.png");
	
	UnderRealmCrystalRenderer()
	{
		try 
		{
			CrystalMesh=AdvancedModelLoader.loadModel("/assets/underrealmmod/models/w_Crystal.ILMC");
			//CrystalMesh=AdvancedModelLoader.loadModel(new ResourceLocation("underrealmmod", "models/w_Crystal.ILMC"));
			if (CrystalMesh instanceof IVMC_Container) { CrystalMesh_container=(IVMC_Container)CrystalMesh; }
		}
		catch (IllegalArgumentException e)
		{
			System.out.println("[underrealm]Error: " + e.getMessage());
		}
		catch (ModelFormatException e)
		{
			System.out.println("[underrealm]Error: " + e.getMessage());
		}
	}
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) 
	{
	}
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) 
	{
		/**
	     * Renders a standard cube block at the given coordinates, with a given color ratio.  Args: block, x, y, z, r, g, b
	     */
		//renderer.renderStandardBlockWithColorMultiplier(Block par1Block, int par2, int par3, int par4, float par5, float par6, float par7)
		//renderer.renderStandardBlock(Block.glowStone, x, y, z);
		
		Tessellator tessellator = Tessellator.instance;
		
		
        // Stop the tesselator from messing with us
        int previousGLRenderType = Tessellator.instance.drawMode;
        Tessellator.instance.draw();
        
        // Draw our stuff
        GL11.glPushMatrix();
        GL11.glTranslated((double)x + .5f, (double)y + .5f, (double)z + .5f);
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture);
        
        //Draw ILVF mesh.
        if (block instanceof UnderRealmCrystalBlock)
        {
        	int meta=world.getBlockMetadata(x, y, z);
        	UnderRealmCrystalBlock CrystalBlock=(UnderRealmCrystalBlock)block;
        	float []colors=CrystalBlock.GetCrystalColor(meta);
        	GL11.glColor4f(colors[0], colors[1], colors[2], 0.8f);
        }
        
        //Crystals can render fullbright, as it provides a rather cool effect.
        //CrystalMesh_container.SetLighting(block.getMixedBrightnessForBlock(world, x, y, z));
        CrystalMesh.renderAll();
        
        //ReflectionHelper.getPrivateValue(Wavefront_LightHack.class, CrystalMesh, "currentGroupObject");

        GL11.glPopMatrix();

        // Restart the Tesselator
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        Tessellator.instance.startDrawing(previousGLRenderType);
		
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory() 
	{
		return false;
	}

	@Override
	public int getRenderId() {
		return UnderRealm_ClientCore.CrystalRenderID;
	}

}

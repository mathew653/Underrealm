package com.UnderRealm.Client;

import org.lwjgl.opengl.GL11;

import com.IVMCLoader.ILVF_Colorable;
import com.IVMCLoader.IVMC_Container;
import com.UnderRealm.Core.TileEntityComplexBlock;
import com.UnderRealm.Core.TileEntityModel;
import com.UnderRealm.Core.UnderRealmCrystalBlock;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class TileEntityModelRenderer extends TileEntitySpecialRenderer {
	//private ResourceLocation texture = new ResourceLocation("underrealmmod", "textures/blocks/Crystal_grey.png");
	private ResourceLocation texture = null;
	private IModelCustom Mesh = null;
	private IVMC_Container Mesh_container = null;
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
		TileEntityModel hModel=null;
		Block host_block=null;
		
		//This needs stream lining into a more usable system
		//Thinking base class with IsFullbright, GetModel, GetTexture and GetColor
		
		if (tileentity instanceof TileEntityModel)
		{
			hModel=(TileEntityModel)tileentity;
			host_block=hModel.GetHost();
			if (Mesh == null)
			{
				Mesh=AdvancedModelLoader.loadModel(new ResourceLocation("underrealmmod", "models/w_Crystal.ILMC"));
				if (Mesh instanceof IVMC_Container) { Mesh_container=(IVMC_Container)Mesh; }
				//System.out.print("!Initalised model");
			}
			
			this.bindTexture(TextureMap.locationBlocksTexture);
			//TODO: Map block texture here.
		}
		
        // Draw our stuff
        GL11.glPushMatrix();
        GL11.glTranslated((double)x + .5f, (double)y + .5f, (double)z + .5f);
        //GL11.glTranslated((double)((float)x - (float)tileentity.xCoord), (double)((float)y - (float)tileentity.yCoord), (double)((float)z - (float)tileentity.zCoord)); // This is necessary to make our rendering happen in the right place.
        
        //Draw ILVF mesh.
        if (host_block instanceof ILVF_Colorable)
        {
        	//int meta=world.getBlockMetadata(x, y, z);
        	int meta=hModel.getBlockMetadata();
        	ILVF_Colorable CrystalBlock=(ILVF_Colorable)host_block;
        	
        	if (texture == null) { texture = new ResourceLocation("underrealmmod", CrystalBlock.GetMeshTexture(meta)); }
            FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture);
        	
        	float []colors=CrystalBlock.GetMeshColor(meta);
        	//GL11.glColor4f(colors[0], colors[1], colors[2], 0.8f);
        	Mesh_container.SetColor(colors[0], colors[1], colors[2], 0.8f);
        	
        	//Disabling lighting should fix lighting bug when looking at the object at certain angles.
        	//Forcing blend seems to have fixed alpha bug.
        	
        	boolean ForcedBlend=false;
        	if (!GL11.glIsEnabled(GL11.GL_BLEND)) { GL11.glEnable(GL11.GL_BLEND); ForcedBlend=true; }
        	
        	GL11.glDisable(GL11.GL_LIGHTING);
        	//GL11.glDisable(GL11.GL_BLEND);
            
        	Mesh.renderAll();
        	
        	//GL11.glEnable(GL11.GL_BLEND);
        	//770 == GL_SRC_ALPHA
        	//771 == GL_ONE_MINUS_SRC_ALPHA
        	//OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        	GL11.glEnable(GL11.GL_LIGHTING);
        	
        	if (ForcedBlend == true) { GL11.glDisable(GL11.GL_BLEND); }
        }
        else
        {
        	//Stub for now.
        }
        
        //Crystals can render fullbright, as it provides a rather cool effect.
        //CrystalMesh_container.SetLighting(block.getMixedBrightnessForBlock(world, x, y, z));
        
        //Mesh.renderAll();

        GL11.glPopMatrix();

        // Restart the Tesselator
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        //Tessellator.instance.startDrawing(previousGLRenderType);
	}

}

package com.UnderRealm.Client;

import org.lwjgl.opengl.GL11;

import com.UnderRealm.Core.ItemEtherealRing;
import com.UnderRealm.Core.TileEntityComplexBlock;
import com.UnderRealm.Core.UnderRealmPotionList;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class TileEntityEtherealWallRenderer extends TileEntitySpecialRenderer {
	
	/** instance of RenderBlocks used to draw the piston base and extension. */
    //private RenderBlocks blockRenderer;
	private BlockRendererRGBA blockRenderer;
	
    static public boolean IsHoldingRingItem()
    {
    	//Get local players held item.
    	EntityPlayer localPlayer = Minecraft.getMinecraft().thePlayer;
    	
    	if (localPlayer.isPotionActive(UnderRealmPotionList.EtherealShift))
    	{
    		return true;
    	}
    	
    	/*
    	if (localPlayer.getHeldItem() != null)
        {
        	if (localPlayer.getHeldItem().getItem() instanceof ItemEtherealRing)
        	{
        		return true;
        	}
        }
    	*/
    	
    	return false;
    }
    
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
		TileEntityComplexBlock hComplex=null;
		
		// locationBlocksTexture is a "ResourceLocation" that points to a texture made of many block "icons".
	    // It will look very ugly, but creating our own ResourceLocation is beyond the scope of this tutorial.
		if (tileentity instanceof TileEntityComplexBlock)
		{
			hComplex=(TileEntityComplexBlock)tileentity;
			Block host=hComplex.GetHost();
		}
		
		Tessellator tessellator = Tessellator.instance;
        this.bindTexture(TextureMap.locationBlocksTexture);
        RenderHelper.disableStandardItemLighting();
        
        //We can use glget according to : https://msdn.microsoft.com/en-gb/library/windows/desktop/dd318368%28v=vs.85%29.aspx
        //For snapshoting our blend vars prior to editing them.
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_BLEND);
        //GL11.glDisable(GL11.GL_CULL_FACE); // Make both sides show.

        if (Minecraft.isAmbientOcclusionEnabled())
        {
            GL11.glShadeModel(GL11.GL_SMOOTH);
        }
        else
        {
            GL11.glShadeModel(GL11.GL_FLAT);
        }

        tessellator.startDrawingQuads();
        GL11.glPushMatrix();
        GL11.glTranslated((double)((float)x - (float)tileentity.xCoord), (double)((float)y - (float)tileentity.yCoord), (double)((float)z - (float)tileentity.zCoord)); // This is necessary to make our rendering happen in the right place.
        tessellator.setColorOpaque(1, 1, 1);
        
        /** The minimum X value for rendering (default 0.0). */
        this.blockRenderer.renderMinX=0.0f;

        /** The maximum X value for rendering (default 1.0). */
        this.blockRenderer.renderMaxX=1.0f;

        /** The minimum Y value for rendering (default 0.0). */
        this.blockRenderer.renderMinY=0.0f;

        /** The maximum Y value for rendering (default 1.0). */
        this.blockRenderer.renderMaxY=1.0f;

        /** The minimum Z value for rendering (default 0.0). */
        this.blockRenderer.renderMinZ=0.0f;

        /** The maximum Z value for rendering (default 1.0). */
        this.blockRenderer.renderMaxZ=1.0f;
        
        if (this.IsHoldingRingItem()) 
        { 
        	this.blockRenderer.SetAlpha(0.5f); 
        }
    	else
    	{ 
    		this.blockRenderer.SetAlpha(1.0f); 
    	}
        this.blockRenderer.renderStandardBlock(hComplex.getBlockType(), tileentity.xCoord, tileentity.yCoord, tileentity.zCoord);
        
        tessellator.draw();
        
        //Restore prior render state after drawing.
        //GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_BLEND);
        
        GL11.glPopMatrix();
        RenderHelper.enableStandardItemLighting();
	}
	
	/**
     * Called when the ingame world being rendered changes (e.g. on world -> nether travel) due to using one renderer
     * per tile entity type, rather than instance
     */
    //public void onWorldChange(World par1World)
	public void  func_147496_a(World par1World)
    {
		if (this.blockRenderer == null)
		{
			//this.blockRenderer = new RenderBlocks(par1World);
			this.blockRenderer = new BlockRendererRGBA(par1World);
		}
    }
}

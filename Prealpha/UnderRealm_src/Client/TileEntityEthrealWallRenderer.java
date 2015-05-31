package mod.UnderRealm.Client;

import org.lwjgl.opengl.GL11;

import mod.UnderRealm.Core.ItemEtherealRing;
import mod.UnderRealm.Core.TileEntityComplexBlock;
import mod.UnderRealm.Core.UnderRealmPotionList;
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
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class TileEntityEthrealWallRenderer extends TileEntitySpecialRenderer {
	
	/** instance of RenderBlocks used to draw the piston base and extension. */
    private RenderBlocks blockRenderer;
	
    private boolean IsHoldingRingItem()
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
			
			this.bindTexture(TextureMap.locationBlocksTexture);
			//TODO: Map block texture here.
		}
		else
		{
			this.bindTexture(TextureMap.locationBlocksTexture);
		}
		
		int _l = hComplex.getBlockType().colorMultiplier(this.blockRenderer.blockAccess, tileentity.xCoord, tileentity.yCoord, tileentity.zCoord);
        float _f = (float)(_l >> 16 & 255) / 255.0F;
        float _f1 = (float)(_l >> 8 & 255) / 255.0F;
        float _f2 = (float)(_l & 255) / 255.0F;

        if (EntityRenderer.anaglyphEnable)
        {
            float f3 = (f * 30.0F + _f1 * 59.0F + _f2 * 11.0F) / 100.0F;
            float f4 = (f * 30.0F + _f1 * 70.0F) / 100.0F;
            float f5 = (f * 30.0F + _f2 * 70.0F) / 100.0F;
            _f = f3;
            _f1 = f4;
            _f2 = f5;
        }
		
		float f3 = 0.5F;
        float f4 = 1.0F;
        float f5 = 0.8F;
        float f6 = 0.6F;
        float f7 = f4 * _f;
        float f8 = f4 * _f1;
        float f9 = f4 * _f2;
        float f10 = f3;
        float f11 = f5;
        float f12 = f6;
        float f13 = f3;
        float f14 = f5;
        float f15 = f6;
        float f16 = f3;
        float f17 = f5;
        float f18 = f6;
		
	    
		Tessellator tessellator = Tessellator.instance;
        this.bindTexture(TextureMap.locationBlocksTexture);
        RenderHelper.disableStandardItemLighting();
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
        
        //this.blockRenderer.renderAllFaces = true;
        //this.blockRenderer.renderStandardBlockWithColorMultiplier(hComplex.getBlockType(), tileentity.xCoord, tileentity.yCoord, tileentity.zCoord, 1.0f, 1.0f, 1.0f);
        //this.blockRenderer.renderAllFaces = false;
        
        int l = hComplex.getBlockType().getMixedBrightnessForBlock(this.blockRenderer.blockAccess, tileentity.xCoord, tileentity.yCoord, tileentity.zCoord);
        
        if (this.blockRenderer.renderAllFaces || hComplex.getBlockType().shouldSideBeRendered(this.blockRenderer.blockAccess, tileentity.xCoord, tileentity.yCoord - 1, tileentity.zCoord, 0))
        {
        	//tessellator.setColorOpaque_F(1.0f, 1.0f, 1.0f);
        	if (this.IsHoldingRingItem()) { tessellator.setColorRGBA_F(f10, f13, f16, 0.5f); }
        	else						  { tessellator.setColorRGBA_F(f10, f13, f16, 1.0f); }

        	tessellator.setBrightness(this.blockRenderer.renderMinY > 0.0D ? l : hComplex.getBlockType().getMixedBrightnessForBlock(this.blockRenderer.blockAccess, tileentity.xCoord, tileentity.yCoord - 1, tileentity.zCoord));
        	this.blockRenderer.renderFaceYNeg(hComplex.getBlockType(), (double)tileentity.xCoord, (double)tileentity.yCoord, (double)tileentity.zCoord, this.blockRenderer.getBlockIcon(hComplex.getBlockType(), this.blockRenderer.blockAccess, tileentity.xCoord, tileentity.yCoord, tileentity.zCoord, 0));
        }
        
        if (this.blockRenderer.renderAllFaces || hComplex.getBlockType().shouldSideBeRendered(this.blockRenderer.blockAccess, tileentity.xCoord, tileentity.yCoord + 1, tileentity.zCoord, 1))
        {
        	if (this.IsHoldingRingItem()) { tessellator.setColorRGBA_F(f7, f8, f9, 0.5f); }
        	else						  { tessellator.setColorRGBA_F(f7, f8, f9, 1.0f); }
        
        	tessellator.setBrightness(this.blockRenderer.renderMaxY < 1.0D ? l : hComplex.getBlockType().getMixedBrightnessForBlock(this.blockRenderer.blockAccess, tileentity.xCoord, tileentity.yCoord + 1, tileentity.zCoord));
        	this.blockRenderer.renderFaceYPos(hComplex.getBlockType(), (double)tileentity.xCoord, (double)tileentity.yCoord, (double)tileentity.zCoord, this.blockRenderer.getBlockIcon(hComplex.getBlockType(), this.blockRenderer.blockAccess, tileentity.xCoord, tileentity.yCoord, tileentity.zCoord, 1));
        }
        
        Icon icon;
        
        if (this.blockRenderer.renderAllFaces || hComplex.getBlockType().shouldSideBeRendered(this.blockRenderer.blockAccess, tileentity.xCoord, tileentity.yCoord, tileentity.zCoord - 1, 2))
        {
        	if (this.IsHoldingRingItem()) { tessellator.setColorRGBA_F(f11, f14, f17, 0.5f); }
        	else						  { tessellator.setColorRGBA_F(f11, f14, f17, 1.0f); }
        
        	tessellator.setBrightness(this.blockRenderer.renderMinZ > 0.0D ? l : hComplex.getBlockType().getMixedBrightnessForBlock(this.blockRenderer.blockAccess, tileentity.xCoord, tileentity.yCoord, tileentity.zCoord - 1));
        	icon = this.blockRenderer.getBlockIcon(hComplex.getBlockType(), this.blockRenderer.blockAccess, tileentity.xCoord, tileentity.yCoord, tileentity.zCoord, 2);
        	this.blockRenderer.renderFaceZNeg(hComplex.getBlockType(), (double)tileentity.xCoord, (double)tileentity.yCoord, (double)tileentity.zCoord, icon);
        }
        
        if (this.blockRenderer.renderAllFaces || hComplex.getBlockType().shouldSideBeRendered(this.blockRenderer.blockAccess, tileentity.xCoord, tileentity.yCoord, tileentity.zCoord + 1, 3))
        {
        	if (this.IsHoldingRingItem()) { tessellator.setColorRGBA_F(f11, f14, f17, 0.5f); }
        	else						  { tessellator.setColorRGBA_F(f11, f14, f17, 1.0f); }
        
        	tessellator.setBrightness(this.blockRenderer.renderMaxZ < 1.0D ? l : hComplex.getBlockType().getMixedBrightnessForBlock(this.blockRenderer.blockAccess, tileentity.xCoord, tileentity.yCoord, tileentity.zCoord + 1));
        	icon = this.blockRenderer.getBlockIcon(hComplex.getBlockType(), this.blockRenderer.blockAccess, tileentity.xCoord, tileentity.yCoord, tileentity.zCoord, 3);
        	this.blockRenderer.renderFaceZPos(hComplex.getBlockType(), (double)tileentity.xCoord, (double)tileentity.yCoord, (double)tileentity.zCoord, icon);
        }
        
        if (this.blockRenderer.renderAllFaces || hComplex.getBlockType().shouldSideBeRendered(this.blockRenderer.blockAccess, tileentity.xCoord - 1, tileentity.yCoord, tileentity.zCoord, 4))
        {
        	if (this.IsHoldingRingItem()) { tessellator.setColorRGBA_F(f12, f15, f18, 0.5f); }
        	else						  { tessellator.setColorRGBA_F(f12, f15, f18, 1.0f); }

        	tessellator.setBrightness(this.blockRenderer.renderMinX > 0.0D ? l : hComplex.getBlockType().getMixedBrightnessForBlock(this.blockRenderer.blockAccess, tileentity.xCoord - 1, tileentity.yCoord, tileentity.zCoord));
        	icon = this.blockRenderer.getBlockIcon(hComplex.getBlockType(), this.blockRenderer.blockAccess, tileentity.xCoord, tileentity.yCoord, tileentity.zCoord, 4);
        	this.blockRenderer.renderFaceXNeg(hComplex.getBlockType(), (double)tileentity.xCoord, (double)tileentity.yCoord, (double)tileentity.zCoord, icon);
        }
        
        if (this.blockRenderer.renderAllFaces || hComplex.getBlockType().shouldSideBeRendered(this.blockRenderer.blockAccess, tileentity.xCoord + 1, tileentity.yCoord, tileentity.zCoord, 5))
        {
        	if (this.IsHoldingRingItem()) { tessellator.setColorRGBA_F(f12, f15, f18, 0.5f); }
        	else						  { tessellator.setColorRGBA_F(f12, f15, f18, 1.0f); }
        
        	tessellator.setBrightness(this.blockRenderer.renderMaxX < 1.0D ? l : hComplex.getBlockType().getMixedBrightnessForBlock(this.blockRenderer.blockAccess, tileentity.xCoord + 1, tileentity.yCoord, tileentity.zCoord));
        	icon = this.blockRenderer.getBlockIcon(hComplex.getBlockType(), this.blockRenderer.blockAccess, tileentity.xCoord, tileentity.yCoord, tileentity.zCoord, 5);
        	this.blockRenderer.renderFaceXPos(hComplex.getBlockType(), (double)tileentity.xCoord, (double)tileentity.yCoord, (double)tileentity.zCoord, icon);
        }

        tessellator.draw();
        GL11.glPopMatrix();
        RenderHelper.enableStandardItemLighting();

	    //Tessellator tessellator = Tessellator.instance;
	    //GL11.glPushMatrix();
	    //GL11.glTranslated(x, y, z); // Move to our location.
	    //tessellator.startDrawingQuads();
	    //tessellator.addVertexWithUV(0, 0, 0, 0, 0);
	    //tessellator.addVertexWithUV(0, 1, 0, 0, 1);
	    //tessellator.addVertexWithUV(1, 1, 0, 1, 1);
	    //tessellator.addVertexWithUV(1, 0, 0, 1, 0);

	    //tessellator.addVertexWithUV(0, 0, 0, 0, 0);
	    //tessellator.addVertexWithUV(1, 0, 0, 1, 0);
	    //tessellator.addVertexWithUV(1, 1, 0, 1, 1);
	    //tessellator.addVertexWithUV(0, 1, 0, 0, 1);

	    //tessellator.draw();
	    //GL11.glPopMatrix();
	}
	
	/**
     * Called when the ingame world being rendered changes (e.g. on world -> nether travel) due to using one renderer
     * per tile entity type, rather than instance
     */
    public void onWorldChange(World par1World)
    {
        this.blockRenderer = new RenderBlocks(par1World);
    }
}

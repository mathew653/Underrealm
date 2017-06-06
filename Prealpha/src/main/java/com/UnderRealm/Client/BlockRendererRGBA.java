package com.UnderRealm.Client;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class BlockRendererRGBA extends RenderBlocks {
	//Fix up functionality to allow manipulation of alpha channel on blocks.
	private float AlphaValue;
	private float RValue;
	private float GValue;
	private float BValue;
	public void SetAlpha(float a) { this.AlphaValue = a; }
	
	public BlockRendererRGBA(IBlockAccess p_i1251_1_)
    {
        super(p_i1251_1_);
    }

    public BlockRendererRGBA()
    {
        super();
    }
	
    //tessellator.setColorRGBA_F(f7, f8, f9, this.AlphaValue);
    
    /**
     * Renders a standard cube block at the given coordinates
     */
    @Override
    public boolean renderStandardBlock(Block p_147784_1_, int p_147784_2_, int p_147784_3_, int p_147784_4_)
    {
        int l = p_147784_1_.colorMultiplier(this.blockAccess, p_147784_2_, p_147784_3_, p_147784_4_);
        float f = (float)(l >> 16 & 255) / 255.0F;
        float f1 = (float)(l >> 8 & 255) / 255.0F;
        float f2 = (float)(l & 255) / 255.0F;

        if (EntityRenderer.anaglyphEnable)
        {
            float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
            float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
            float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
            f = f3;
            f1 = f4;
            f2 = f5;
        }
        
        RValue = f;//f = r
        GValue = f1;//f1 = g
        BValue = f2;//f2 = b

        return Minecraft.isAmbientOcclusionEnabled() && p_147784_1_.getLightValue() == 0 ? (this.partialRenderBounds ? this.renderStandardBlockWithAmbientOcclusionPartial(p_147784_1_, p_147784_2_, p_147784_3_, p_147784_4_, f, f1, f2) : this.renderStandardBlockWithAmbientOcclusion(p_147784_1_, p_147784_2_, p_147784_3_, p_147784_4_, f, f1, f2)) : this.renderStandardBlockWithColorMultiplier(p_147784_1_, p_147784_2_, p_147784_3_, p_147784_4_, f, f1, f2);
    }
    
    /**
     * Renders the given texture to the bottom face of the block. Args: block, x, y, z, texture
     */
    @Override
    public void renderFaceYNeg(Block p_147768_1_, double p_147768_2_, double p_147768_4_, double p_147768_6_, IIcon p_147768_8_)
    {
        Tessellator tessellator = Tessellator.instance;
        tessellator.setColorRGBA_F(RValue, GValue, BValue, AlphaValue);

        if (this.hasOverrideBlockTexture())
        {
            p_147768_8_ = this.overrideBlockTexture;
        }

        double d3 = (double)p_147768_8_.getInterpolatedU(this.renderMinX * 16.0D);
        double d4 = (double)p_147768_8_.getInterpolatedU(this.renderMaxX * 16.0D);
        double d5 = (double)p_147768_8_.getInterpolatedV(this.renderMinZ * 16.0D);
        double d6 = (double)p_147768_8_.getInterpolatedV(this.renderMaxZ * 16.0D);

        if (this.renderMinX < 0.0D || this.renderMaxX > 1.0D)
        {
            d3 = (double)p_147768_8_.getMinU();
            d4 = (double)p_147768_8_.getMaxU();
        }

        if (this.renderMinZ < 0.0D || this.renderMaxZ > 1.0D)
        {
            d5 = (double)p_147768_8_.getMinV();
            d6 = (double)p_147768_8_.getMaxV();
        }

        double d7 = d4;
        double d8 = d3;
        double d9 = d5;
        double d10 = d6;

        if (this.uvRotateBottom == 2)
        {
            d3 = (double)p_147768_8_.getInterpolatedU(this.renderMinZ * 16.0D);
            d5 = (double)p_147768_8_.getInterpolatedV(16.0D - this.renderMaxX * 16.0D);
            d4 = (double)p_147768_8_.getInterpolatedU(this.renderMaxZ * 16.0D);
            d6 = (double)p_147768_8_.getInterpolatedV(16.0D - this.renderMinX * 16.0D);
            d9 = d5;
            d10 = d6;
            d7 = d3;
            d8 = d4;
            d5 = d6;
            d6 = d9;
        }
        else if (this.uvRotateBottom == 1)
        {
            d3 = (double)p_147768_8_.getInterpolatedU(16.0D - this.renderMaxZ * 16.0D);
            d5 = (double)p_147768_8_.getInterpolatedV(this.renderMinX * 16.0D);
            d4 = (double)p_147768_8_.getInterpolatedU(16.0D - this.renderMinZ * 16.0D);
            d6 = (double)p_147768_8_.getInterpolatedV(this.renderMaxX * 16.0D);
            d7 = d4;
            d8 = d3;
            d3 = d4;
            d4 = d8;
            d9 = d6;
            d10 = d5;
        }
        else if (this.uvRotateBottom == 3)
        {
            d3 = (double)p_147768_8_.getInterpolatedU(16.0D - this.renderMinX * 16.0D);
            d4 = (double)p_147768_8_.getInterpolatedU(16.0D - this.renderMaxX * 16.0D);
            d5 = (double)p_147768_8_.getInterpolatedV(16.0D - this.renderMinZ * 16.0D);
            d6 = (double)p_147768_8_.getInterpolatedV(16.0D - this.renderMaxZ * 16.0D);
            d7 = d4;
            d8 = d3;
            d9 = d5;
            d10 = d6;
        }

        double d11 = p_147768_2_ + this.renderMinX;
        double d12 = p_147768_2_ + this.renderMaxX;
        double d13 = p_147768_4_ + this.renderMinY;
        double d14 = p_147768_6_ + this.renderMinZ;
        double d15 = p_147768_6_ + this.renderMaxZ;

        if (this.renderFromInside)
        {
            d11 = p_147768_2_ + this.renderMaxX;
            d12 = p_147768_2_ + this.renderMinX;
        }

        if (this.enableAO)
        {
            tessellator.setColorRGBA_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft, this.AlphaValue);
            tessellator.setBrightness(this.brightnessTopLeft);
            tessellator.addVertexWithUV(d11, d13, d15, d8, d10);
            tessellator.setColorRGBA_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft, this.AlphaValue);
            tessellator.setBrightness(this.brightnessBottomLeft);
            tessellator.addVertexWithUV(d11, d13, d14, d3, d5);
            tessellator.setColorRGBA_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight, this.AlphaValue);
            tessellator.setBrightness(this.brightnessBottomRight);
            tessellator.addVertexWithUV(d12, d13, d14, d7, d9);
            tessellator.setColorRGBA_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight, this.AlphaValue);
            tessellator.setBrightness(this.brightnessTopRight);
            tessellator.addVertexWithUV(d12, d13, d15, d4, d6);
        }
        else
        {
            tessellator.addVertexWithUV(d11, d13, d15, d8, d10);
            tessellator.addVertexWithUV(d11, d13, d14, d3, d5);
            tessellator.addVertexWithUV(d12, d13, d14, d7, d9);
            tessellator.addVertexWithUV(d12, d13, d15, d4, d6);
        }
    }

    /**
     * Renders the given texture to the top face of the block. Args: block, x, y, z, texture
     */
    @Override
    public void renderFaceYPos(Block p_147806_1_, double p_147806_2_, double p_147806_4_, double p_147806_6_, IIcon p_147806_8_)
    {
        Tessellator tessellator = Tessellator.instance;
        tessellator.setColorRGBA_F(RValue, GValue, BValue, AlphaValue);

        if (this.hasOverrideBlockTexture())
        {
            p_147806_8_ = this.overrideBlockTexture;
        }

        double d3 = (double)p_147806_8_.getInterpolatedU(this.renderMinX * 16.0D);
        double d4 = (double)p_147806_8_.getInterpolatedU(this.renderMaxX * 16.0D);
        double d5 = (double)p_147806_8_.getInterpolatedV(this.renderMinZ * 16.0D);
        double d6 = (double)p_147806_8_.getInterpolatedV(this.renderMaxZ * 16.0D);

        if (this.renderMinX < 0.0D || this.renderMaxX > 1.0D)
        {
            d3 = (double)p_147806_8_.getMinU();
            d4 = (double)p_147806_8_.getMaxU();
        }

        if (this.renderMinZ < 0.0D || this.renderMaxZ > 1.0D)
        {
            d5 = (double)p_147806_8_.getMinV();
            d6 = (double)p_147806_8_.getMaxV();
        }

        double d7 = d4;
        double d8 = d3;
        double d9 = d5;
        double d10 = d6;

        if (this.uvRotateTop == 1)
        {
            d3 = (double)p_147806_8_.getInterpolatedU(this.renderMinZ * 16.0D);
            d5 = (double)p_147806_8_.getInterpolatedV(16.0D - this.renderMaxX * 16.0D);
            d4 = (double)p_147806_8_.getInterpolatedU(this.renderMaxZ * 16.0D);
            d6 = (double)p_147806_8_.getInterpolatedV(16.0D - this.renderMinX * 16.0D);
            d9 = d5;
            d10 = d6;
            d7 = d3;
            d8 = d4;
            d5 = d6;
            d6 = d9;
        }
        else if (this.uvRotateTop == 2)
        {
            d3 = (double)p_147806_8_.getInterpolatedU(16.0D - this.renderMaxZ * 16.0D);
            d5 = (double)p_147806_8_.getInterpolatedV(this.renderMinX * 16.0D);
            d4 = (double)p_147806_8_.getInterpolatedU(16.0D - this.renderMinZ * 16.0D);
            d6 = (double)p_147806_8_.getInterpolatedV(this.renderMaxX * 16.0D);
            d7 = d4;
            d8 = d3;
            d3 = d4;
            d4 = d8;
            d9 = d6;
            d10 = d5;
        }
        else if (this.uvRotateTop == 3)
        {
            d3 = (double)p_147806_8_.getInterpolatedU(16.0D - this.renderMinX * 16.0D);
            d4 = (double)p_147806_8_.getInterpolatedU(16.0D - this.renderMaxX * 16.0D);
            d5 = (double)p_147806_8_.getInterpolatedV(16.0D - this.renderMinZ * 16.0D);
            d6 = (double)p_147806_8_.getInterpolatedV(16.0D - this.renderMaxZ * 16.0D);
            d7 = d4;
            d8 = d3;
            d9 = d5;
            d10 = d6;
        }

        double d11 = p_147806_2_ + this.renderMinX;
        double d12 = p_147806_2_ + this.renderMaxX;
        double d13 = p_147806_4_ + this.renderMaxY;
        double d14 = p_147806_6_ + this.renderMinZ;
        double d15 = p_147806_6_ + this.renderMaxZ;

        if (this.renderFromInside)
        {
            d11 = p_147806_2_ + this.renderMaxX;
            d12 = p_147806_2_ + this.renderMinX;
        }

        if (this.enableAO)
        {
            tessellator.setColorRGBA_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft, this.AlphaValue);
            tessellator.setBrightness(this.brightnessTopLeft);
            tessellator.addVertexWithUV(d12, d13, d15, d4, d6);
            tessellator.setColorRGBA_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft, this.AlphaValue);
            tessellator.setBrightness(this.brightnessBottomLeft);
            tessellator.addVertexWithUV(d12, d13, d14, d7, d9);
            tessellator.setColorRGBA_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight, this.AlphaValue);
            tessellator.setBrightness(this.brightnessBottomRight);
            tessellator.addVertexWithUV(d11, d13, d14, d3, d5);
            tessellator.setColorRGBA_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight, this.AlphaValue);
            tessellator.setBrightness(this.brightnessTopRight);
            tessellator.addVertexWithUV(d11, d13, d15, d8, d10);
        }
        else
        {
            tessellator.addVertexWithUV(d12, d13, d15, d4, d6);
            tessellator.addVertexWithUV(d12, d13, d14, d7, d9);
            tessellator.addVertexWithUV(d11, d13, d14, d3, d5);
            tessellator.addVertexWithUV(d11, d13, d15, d8, d10);
        }
    }

    /**
     * Renders the given texture to the north (z-negative) face of the block.  Args: block, x, y, z, texture
     */
    @Override
    public void renderFaceZNeg(Block p_147761_1_, double p_147761_2_, double p_147761_4_, double p_147761_6_, IIcon p_147761_8_)
    {
        Tessellator tessellator = Tessellator.instance;
        tessellator.setColorRGBA_F(RValue, GValue, BValue, AlphaValue);

        if (this.hasOverrideBlockTexture())
        {
            p_147761_8_ = this.overrideBlockTexture;
        }

        double d3 = (double)p_147761_8_.getInterpolatedU(this.renderMinX * 16.0D);
        double d4 = (double)p_147761_8_.getInterpolatedU(this.renderMaxX * 16.0D);

        if (this.field_152631_f)
        {
            d4 = (double)p_147761_8_.getInterpolatedU((1.0D - this.renderMinX) * 16.0D);
            d3 = (double)p_147761_8_.getInterpolatedU((1.0D - this.renderMaxX) * 16.0D);
        }

        double d5 = (double)p_147761_8_.getInterpolatedV(16.0D - this.renderMaxY * 16.0D);
        double d6 = (double)p_147761_8_.getInterpolatedV(16.0D - this.renderMinY * 16.0D);
        double d7;

        if (this.flipTexture)
        {
            d7 = d3;
            d3 = d4;
            d4 = d7;
        }

        if (this.renderMinX < 0.0D || this.renderMaxX > 1.0D)
        {
            d3 = (double)p_147761_8_.getMinU();
            d4 = (double)p_147761_8_.getMaxU();
        }

        if (this.renderMinY < 0.0D || this.renderMaxY > 1.0D)
        {
            d5 = (double)p_147761_8_.getMinV();
            d6 = (double)p_147761_8_.getMaxV();
        }

        d7 = d4;
        double d8 = d3;
        double d9 = d5;
        double d10 = d6;

        if (this.uvRotateEast == 2)
        {
            d3 = (double)p_147761_8_.getInterpolatedU(this.renderMinY * 16.0D);
            d4 = (double)p_147761_8_.getInterpolatedU(this.renderMaxY * 16.0D);
            d5 = (double)p_147761_8_.getInterpolatedV(16.0D - this.renderMinX * 16.0D);
            d6 = (double)p_147761_8_.getInterpolatedV(16.0D - this.renderMaxX * 16.0D);
            d9 = d5;
            d10 = d6;
            d7 = d3;
            d8 = d4;
            d5 = d6;
            d6 = d9;
        }
        else if (this.uvRotateEast == 1)
        {
            d3 = (double)p_147761_8_.getInterpolatedU(16.0D - this.renderMaxY * 16.0D);
            d4 = (double)p_147761_8_.getInterpolatedU(16.0D - this.renderMinY * 16.0D);
            d5 = (double)p_147761_8_.getInterpolatedV(this.renderMaxX * 16.0D);
            d6 = (double)p_147761_8_.getInterpolatedV(this.renderMinX * 16.0D);
            d7 = d4;
            d8 = d3;
            d3 = d4;
            d4 = d8;
            d9 = d6;
            d10 = d5;
        }
        else if (this.uvRotateEast == 3)
        {
            d3 = (double)p_147761_8_.getInterpolatedU(16.0D - this.renderMinX * 16.0D);
            d4 = (double)p_147761_8_.getInterpolatedU(16.0D - this.renderMaxX * 16.0D);
            d5 = (double)p_147761_8_.getInterpolatedV(this.renderMaxY * 16.0D);
            d6 = (double)p_147761_8_.getInterpolatedV(this.renderMinY * 16.0D);
            d7 = d4;
            d8 = d3;
            d9 = d5;
            d10 = d6;
        }

        double d11 = p_147761_2_ + this.renderMinX;
        double d12 = p_147761_2_ + this.renderMaxX;
        double d13 = p_147761_4_ + this.renderMinY;
        double d14 = p_147761_4_ + this.renderMaxY;
        double d15 = p_147761_6_ + this.renderMinZ;

        if (this.renderFromInside)
        {
            d11 = p_147761_2_ + this.renderMaxX;
            d12 = p_147761_2_ + this.renderMinX;
        }

        if (this.enableAO)
        {
            tessellator.setColorRGBA_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft, this.AlphaValue);
            tessellator.setBrightness(this.brightnessTopLeft);
            tessellator.addVertexWithUV(d11, d14, d15, d7, d9);
            tessellator.setColorRGBA_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft, this.AlphaValue);
            tessellator.setBrightness(this.brightnessBottomLeft);
            tessellator.addVertexWithUV(d12, d14, d15, d3, d5);
            tessellator.setColorRGBA_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight, this.AlphaValue);
            tessellator.setBrightness(this.brightnessBottomRight);
            tessellator.addVertexWithUV(d12, d13, d15, d8, d10);
            tessellator.setColorRGBA_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight, this.AlphaValue);
            tessellator.setBrightness(this.brightnessTopRight);
            tessellator.addVertexWithUV(d11, d13, d15, d4, d6);
        }
        else
        {
            tessellator.addVertexWithUV(d11, d14, d15, d7, d9);
            tessellator.addVertexWithUV(d12, d14, d15, d3, d5);
            tessellator.addVertexWithUV(d12, d13, d15, d8, d10);
            tessellator.addVertexWithUV(d11, d13, d15, d4, d6);
        }
    }

    /**
     * Renders the given texture to the south (z-positive) face of the block.  Args: block, x, y, z, texture
     */
    @Override
    public void renderFaceZPos(Block p_147734_1_, double p_147734_2_, double p_147734_4_, double p_147734_6_, IIcon p_147734_8_)
    {
        Tessellator tessellator = Tessellator.instance;
        tessellator.setColorRGBA_F(RValue, GValue, BValue, AlphaValue);

        if (this.hasOverrideBlockTexture())
        {
            p_147734_8_ = this.overrideBlockTexture;
        }

        double d3 = (double)p_147734_8_.getInterpolatedU(this.renderMinX * 16.0D);
        double d4 = (double)p_147734_8_.getInterpolatedU(this.renderMaxX * 16.0D);
        double d5 = (double)p_147734_8_.getInterpolatedV(16.0D - this.renderMaxY * 16.0D);
        double d6 = (double)p_147734_8_.getInterpolatedV(16.0D - this.renderMinY * 16.0D);
        double d7;

        if (this.flipTexture)
        {
            d7 = d3;
            d3 = d4;
            d4 = d7;
        }

        if (this.renderMinX < 0.0D || this.renderMaxX > 1.0D)
        {
            d3 = (double)p_147734_8_.getMinU();
            d4 = (double)p_147734_8_.getMaxU();
        }

        if (this.renderMinY < 0.0D || this.renderMaxY > 1.0D)
        {
            d5 = (double)p_147734_8_.getMinV();
            d6 = (double)p_147734_8_.getMaxV();
        }

        d7 = d4;
        double d8 = d3;
        double d9 = d5;
        double d10 = d6;

        if (this.uvRotateWest == 1)
        {
            d3 = (double)p_147734_8_.getInterpolatedU(this.renderMinY * 16.0D);
            d6 = (double)p_147734_8_.getInterpolatedV(16.0D - this.renderMinX * 16.0D);
            d4 = (double)p_147734_8_.getInterpolatedU(this.renderMaxY * 16.0D);
            d5 = (double)p_147734_8_.getInterpolatedV(16.0D - this.renderMaxX * 16.0D);
            d9 = d5;
            d10 = d6;
            d7 = d3;
            d8 = d4;
            d5 = d6;
            d6 = d9;
        }
        else if (this.uvRotateWest == 2)
        {
            d3 = (double)p_147734_8_.getInterpolatedU(16.0D - this.renderMaxY * 16.0D);
            d5 = (double)p_147734_8_.getInterpolatedV(this.renderMinX * 16.0D);
            d4 = (double)p_147734_8_.getInterpolatedU(16.0D - this.renderMinY * 16.0D);
            d6 = (double)p_147734_8_.getInterpolatedV(this.renderMaxX * 16.0D);
            d7 = d4;
            d8 = d3;
            d3 = d4;
            d4 = d8;
            d9 = d6;
            d10 = d5;
        }
        else if (this.uvRotateWest == 3)
        {
            d3 = (double)p_147734_8_.getInterpolatedU(16.0D - this.renderMinX * 16.0D);
            d4 = (double)p_147734_8_.getInterpolatedU(16.0D - this.renderMaxX * 16.0D);
            d5 = (double)p_147734_8_.getInterpolatedV(this.renderMaxY * 16.0D);
            d6 = (double)p_147734_8_.getInterpolatedV(this.renderMinY * 16.0D);
            d7 = d4;
            d8 = d3;
            d9 = d5;
            d10 = d6;
        }

        double d11 = p_147734_2_ + this.renderMinX;
        double d12 = p_147734_2_ + this.renderMaxX;
        double d13 = p_147734_4_ + this.renderMinY;
        double d14 = p_147734_4_ + this.renderMaxY;
        double d15 = p_147734_6_ + this.renderMaxZ;

        if (this.renderFromInside)
        {
            d11 = p_147734_2_ + this.renderMaxX;
            d12 = p_147734_2_ + this.renderMinX;
        }

        if (this.enableAO)
        {
            tessellator.setColorRGBA_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft, this.AlphaValue);
            tessellator.setBrightness(this.brightnessTopLeft);
            tessellator.addVertexWithUV(d11, d14, d15, d3, d5);
            tessellator.setColorRGBA_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft, this.AlphaValue);
            tessellator.setBrightness(this.brightnessBottomLeft);
            tessellator.addVertexWithUV(d11, d13, d15, d8, d10);
            tessellator.setColorRGBA_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight, this.AlphaValue);
            tessellator.setBrightness(this.brightnessBottomRight);
            tessellator.addVertexWithUV(d12, d13, d15, d4, d6);
            tessellator.setColorRGBA_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight, this.AlphaValue);
            tessellator.setBrightness(this.brightnessTopRight);
            tessellator.addVertexWithUV(d12, d14, d15, d7, d9);
        }
        else
        {
            tessellator.addVertexWithUV(d11, d14, d15, d3, d5);
            tessellator.addVertexWithUV(d11, d13, d15, d8, d10);
            tessellator.addVertexWithUV(d12, d13, d15, d4, d6);
            tessellator.addVertexWithUV(d12, d14, d15, d7, d9);
        }
    }

    /**
     * Renders the given texture to the west (x-negative) face of the block.  Args: block, x, y, z, texture
     */
    @Override
    public void renderFaceXNeg(Block p_147798_1_, double p_147798_2_, double p_147798_4_, double p_147798_6_, IIcon p_147798_8_)
    {
        Tessellator tessellator = Tessellator.instance;
        tessellator.setColorRGBA_F(RValue, GValue, BValue, AlphaValue);

        if (this.hasOverrideBlockTexture())
        {
            p_147798_8_ = this.overrideBlockTexture;
        }

        double d3 = (double)p_147798_8_.getInterpolatedU(this.renderMinZ * 16.0D);
        double d4 = (double)p_147798_8_.getInterpolatedU(this.renderMaxZ * 16.0D);
        double d5 = (double)p_147798_8_.getInterpolatedV(16.0D - this.renderMaxY * 16.0D);
        double d6 = (double)p_147798_8_.getInterpolatedV(16.0D - this.renderMinY * 16.0D);
        double d7;

        if (this.flipTexture)
        {
            d7 = d3;
            d3 = d4;
            d4 = d7;
        }

        if (this.renderMinZ < 0.0D || this.renderMaxZ > 1.0D)
        {
            d3 = (double)p_147798_8_.getMinU();
            d4 = (double)p_147798_8_.getMaxU();
        }

        if (this.renderMinY < 0.0D || this.renderMaxY > 1.0D)
        {
            d5 = (double)p_147798_8_.getMinV();
            d6 = (double)p_147798_8_.getMaxV();
        }

        d7 = d4;
        double d8 = d3;
        double d9 = d5;
        double d10 = d6;

        if (this.uvRotateNorth == 1)
        {
            d3 = (double)p_147798_8_.getInterpolatedU(this.renderMinY * 16.0D);
            d5 = (double)p_147798_8_.getInterpolatedV(16.0D - this.renderMaxZ * 16.0D);
            d4 = (double)p_147798_8_.getInterpolatedU(this.renderMaxY * 16.0D);
            d6 = (double)p_147798_8_.getInterpolatedV(16.0D - this.renderMinZ * 16.0D);
            d9 = d5;
            d10 = d6;
            d7 = d3;
            d8 = d4;
            d5 = d6;
            d6 = d9;
        }
        else if (this.uvRotateNorth == 2)
        {
            d3 = (double)p_147798_8_.getInterpolatedU(16.0D - this.renderMaxY * 16.0D);
            d5 = (double)p_147798_8_.getInterpolatedV(this.renderMinZ * 16.0D);
            d4 = (double)p_147798_8_.getInterpolatedU(16.0D - this.renderMinY * 16.0D);
            d6 = (double)p_147798_8_.getInterpolatedV(this.renderMaxZ * 16.0D);
            d7 = d4;
            d8 = d3;
            d3 = d4;
            d4 = d8;
            d9 = d6;
            d10 = d5;
        }
        else if (this.uvRotateNorth == 3)
        {
            d3 = (double)p_147798_8_.getInterpolatedU(16.0D - this.renderMinZ * 16.0D);
            d4 = (double)p_147798_8_.getInterpolatedU(16.0D - this.renderMaxZ * 16.0D);
            d5 = (double)p_147798_8_.getInterpolatedV(this.renderMaxY * 16.0D);
            d6 = (double)p_147798_8_.getInterpolatedV(this.renderMinY * 16.0D);
            d7 = d4;
            d8 = d3;
            d9 = d5;
            d10 = d6;
        }

        double d11 = p_147798_2_ + this.renderMinX;
        double d12 = p_147798_4_ + this.renderMinY;
        double d13 = p_147798_4_ + this.renderMaxY;
        double d14 = p_147798_6_ + this.renderMinZ;
        double d15 = p_147798_6_ + this.renderMaxZ;

        if (this.renderFromInside)
        {
            d14 = p_147798_6_ + this.renderMaxZ;
            d15 = p_147798_6_ + this.renderMinZ;
        }

        if (this.enableAO)
        {
            tessellator.setColorRGBA_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft, this.AlphaValue);
            tessellator.setBrightness(this.brightnessTopLeft);
            tessellator.addVertexWithUV(d11, d13, d15, d7, d9);
            tessellator.setColorRGBA_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft, this.AlphaValue);
            tessellator.setBrightness(this.brightnessBottomLeft);
            tessellator.addVertexWithUV(d11, d13, d14, d3, d5);
            tessellator.setColorRGBA_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight, this.AlphaValue);
            tessellator.setBrightness(this.brightnessBottomRight);
            tessellator.addVertexWithUV(d11, d12, d14, d8, d10);
            tessellator.setColorRGBA_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight, this.AlphaValue);
            tessellator.setBrightness(this.brightnessTopRight);
            tessellator.addVertexWithUV(d11, d12, d15, d4, d6);
        }
        else
        {
            tessellator.addVertexWithUV(d11, d13, d15, d7, d9);
            tessellator.addVertexWithUV(d11, d13, d14, d3, d5);
            tessellator.addVertexWithUV(d11, d12, d14, d8, d10);
            tessellator.addVertexWithUV(d11, d12, d15, d4, d6);
        }
    }

    /**
     * Renders the given texture to the east (x-positive) face of the block.  Args: block, x, y, z, texture
     */
    @Override
    public void renderFaceXPos(Block p_147764_1_, double p_147764_2_, double p_147764_4_, double p_147764_6_, IIcon p_147764_8_)
    {
        Tessellator tessellator = Tessellator.instance;
        tessellator.setColorRGBA_F(RValue, GValue, BValue, AlphaValue);

        if (this.hasOverrideBlockTexture())
        {
            p_147764_8_ = this.overrideBlockTexture;
        }

        double d3 = (double)p_147764_8_.getInterpolatedU(this.renderMinZ * 16.0D);
        double d4 = (double)p_147764_8_.getInterpolatedU(this.renderMaxZ * 16.0D);

        if (this.field_152631_f)
        {
            d4 = (double)p_147764_8_.getInterpolatedU((1.0D - this.renderMinZ) * 16.0D);
            d3 = (double)p_147764_8_.getInterpolatedU((1.0D - this.renderMaxZ) * 16.0D);
        }

        double d5 = (double)p_147764_8_.getInterpolatedV(16.0D - this.renderMaxY * 16.0D);
        double d6 = (double)p_147764_8_.getInterpolatedV(16.0D - this.renderMinY * 16.0D);
        double d7;

        if (this.flipTexture)
        {
            d7 = d3;
            d3 = d4;
            d4 = d7;
        }

        if (this.renderMinZ < 0.0D || this.renderMaxZ > 1.0D)
        {
            d3 = (double)p_147764_8_.getMinU();
            d4 = (double)p_147764_8_.getMaxU();
        }

        if (this.renderMinY < 0.0D || this.renderMaxY > 1.0D)
        {
            d5 = (double)p_147764_8_.getMinV();
            d6 = (double)p_147764_8_.getMaxV();
        }

        d7 = d4;
        double d8 = d3;
        double d9 = d5;
        double d10 = d6;

        if (this.uvRotateSouth == 2)
        {
            d3 = (double)p_147764_8_.getInterpolatedU(this.renderMinY * 16.0D);
            d5 = (double)p_147764_8_.getInterpolatedV(16.0D - this.renderMinZ * 16.0D);
            d4 = (double)p_147764_8_.getInterpolatedU(this.renderMaxY * 16.0D);
            d6 = (double)p_147764_8_.getInterpolatedV(16.0D - this.renderMaxZ * 16.0D);
            d9 = d5;
            d10 = d6;
            d7 = d3;
            d8 = d4;
            d5 = d6;
            d6 = d9;
        }
        else if (this.uvRotateSouth == 1)
        {
            d3 = (double)p_147764_8_.getInterpolatedU(16.0D - this.renderMaxY * 16.0D);
            d5 = (double)p_147764_8_.getInterpolatedV(this.renderMaxZ * 16.0D);
            d4 = (double)p_147764_8_.getInterpolatedU(16.0D - this.renderMinY * 16.0D);
            d6 = (double)p_147764_8_.getInterpolatedV(this.renderMinZ * 16.0D);
            d7 = d4;
            d8 = d3;
            d3 = d4;
            d4 = d8;
            d9 = d6;
            d10 = d5;
        }
        else if (this.uvRotateSouth == 3)
        {
            d3 = (double)p_147764_8_.getInterpolatedU(16.0D - this.renderMinZ * 16.0D);
            d4 = (double)p_147764_8_.getInterpolatedU(16.0D - this.renderMaxZ * 16.0D);
            d5 = (double)p_147764_8_.getInterpolatedV(this.renderMaxY * 16.0D);
            d6 = (double)p_147764_8_.getInterpolatedV(this.renderMinY * 16.0D);
            d7 = d4;
            d8 = d3;
            d9 = d5;
            d10 = d6;
        }

        double d11 = p_147764_2_ + this.renderMaxX;
        double d12 = p_147764_4_ + this.renderMinY;
        double d13 = p_147764_4_ + this.renderMaxY;
        double d14 = p_147764_6_ + this.renderMinZ;
        double d15 = p_147764_6_ + this.renderMaxZ;

        if (this.renderFromInside)
        {
            d14 = p_147764_6_ + this.renderMaxZ;
            d15 = p_147764_6_ + this.renderMinZ;
        }

        if (this.enableAO)
        {
            tessellator.setColorRGBA_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft, this.AlphaValue);
            tessellator.setBrightness(this.brightnessTopLeft);
            tessellator.addVertexWithUV(d11, d12, d15, d8, d10);
            tessellator.setColorRGBA_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft, this.AlphaValue);
            tessellator.setBrightness(this.brightnessBottomLeft);
            tessellator.addVertexWithUV(d11, d12, d14, d4, d6);
            tessellator.setColorRGBA_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight, this.AlphaValue);
            tessellator.setBrightness(this.brightnessBottomRight);
            tessellator.addVertexWithUV(d11, d13, d14, d7, d9);
            tessellator.setColorRGBA_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight, this.AlphaValue);
            tessellator.setBrightness(this.brightnessTopRight);
            tessellator.addVertexWithUV(d11, d13, d15, d3, d5);
        }
        else
        {
            tessellator.addVertexWithUV(d11, d12, d15, d8, d10);
            tessellator.addVertexWithUV(d11, d12, d14, d4, d6);
            tessellator.addVertexWithUV(d11, d13, d14, d7, d9);
            tessellator.addVertexWithUV(d11, d13, d15, d3, d5);
        }
    }
}

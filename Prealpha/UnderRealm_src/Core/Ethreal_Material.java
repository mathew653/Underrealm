package mod.UnderRealm.Core;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class Ethreal_Material extends Material {

	public Ethreal_Material(MapColor par1MapColor) {
		super(par1MapColor);
		
		
	}

	/**
     * Indicate if the material is opaque
     */
    public boolean isOpaque()
    {
        return false;
    }

    /**
     * Returns true if the material can be harvested without a tool (or with the wrong tool)
     */
    public boolean isToolNotRequired()
    {
        return false;
    }
	
	/**
     * Returns if this material is considered solid or not
     */
    public boolean blocksMovement()
    {
        return false;
    }
}

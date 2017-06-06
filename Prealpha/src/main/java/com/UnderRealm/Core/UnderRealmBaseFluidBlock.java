package com.UnderRealm.Core;

import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class UnderRealmBaseFluidBlock extends BlockFluidClassic {

	public UnderRealmBaseFluidBlock(int id, Fluid fluid, Material material) {
		//super(id, fluid, material);
		super(fluid, material);
	}

}

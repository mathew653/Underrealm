package mod.UnderRealm.Core;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class UnderRealmBaseBlockContainer extends BlockContainer {

	protected UnderRealmBaseBlockContainer(int par1, Material par2Material) {
		super(par1, par2Material);
	}
}

package mod.UnderRealm.Core;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class UnderRealmWitherWaterFluid extends Fluid {

	public UnderRealmWitherWaterFluid() {
		super("Wither Water");
		
		setDensity(10); 					// How tick the fluid is, affects movement inside the liquid.
		setViscosity(1000); 				// How fast the fluid flows.
		FluidRegistry.registerFluid(this); 	// Registering inside it self, keeps things neat :)
	}

}

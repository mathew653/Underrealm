package mod.UnderRealm.Client;

import java.util.Collection;

import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Loader;
import mod.UnderRealm.Core.EthrealWall;
import mod.UnderRealm.Core.TileEntityComplexBlock;
import mod.UnderRealm.Core.UnderRealm_ServerCore;
import mod.UnderRealm.Client.TileEntityEthrealWallRenderer;

//Client side proxy for server/client stuff
public class UnderRealm_ClientCore extends UnderRealm_ServerCore {
	static public int CrystalRenderID;
	public void init()
	{
		System.out.println("[UnderrealmCore]Under realm mod client init.");
		
		if (Loader.isModLoaded("IVMCLoader_Forge")) { System.out.println("[underrealm]Found IVMC model loader."); }
		
		Collection<String> FormatList=AdvancedModelLoader.getSupportedSuffixes();
		boolean _Continue=false;
		
		for (String Format : FormatList)
		{
			if (Format == "ILMC") 
			{ 
				System.out.println("[UnderrealmCore]Forge reports ILMC support.");
				_Continue=true;
			}
		}
		
		if (_Continue == false) 
		{ 
			System.out.println("[UnderrealmCore]Warning: Forge setup might not be equiped to run this mod.");
		}
		SetupRenders();
	}
	
	public void SetupRenders()
	{
		// Assign this prior to creating an instance to the renderer
		CrystalRenderID=RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new UnderRealmCrystalRenderer());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityComplexBlock.class, new TileEntityEthrealWallRenderer());
	}
}

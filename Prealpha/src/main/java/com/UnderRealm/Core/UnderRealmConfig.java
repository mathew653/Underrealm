package com.UnderRealm.Core;

import java.io.File;

import net.minecraft.block.Block;
//import net.minecraftforge.common.Config;
import net.minecraftforge.common.config.Configuration;

public class UnderRealmConfig extends Configuration {
	public int BaseID;
	public int BaseItemID;
	public int BaseDimID;
	public int BaseBiomeID;
	public UnderRealmConfig(File file)
    {
		super(file);
    }
	
	public void setupEnvironment(int blockCount)
	{
		System.out.println("[UnderrealmCore]Registered a total of " + blockCount + " blocks.");
		BaseID     = this.get("BaseData","BaseBlockID", 600).getInt();
		BaseItemID = this.get("BaseData","BaseItemID" , 650).getInt();
		BaseDimID  = this.get("BaseData","BaseDimID"  , -5 ).getInt();
		BaseBiomeID = this.get("BaseData","BaseBiomeID", 240 ).getInt();
	}
}

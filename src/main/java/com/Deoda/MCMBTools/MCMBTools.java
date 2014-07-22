package com.Deoda.MCMBTools;

import com.Deoda.MCMBTools.config.ConfigurationHandler;
import com.Deoda.MCMBTools.init.ModItems;
import com.Deoda.MCMBTools.proxy.IProxy;
import com.Deoda.MCMBTools.reference.Reference;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID ,name = Reference.MOD_NAME, version = Reference.VERSION, dependencies ="required-before:ForgeMicroblock", acceptedMinecraftVersions = "[1.6.4]")
public class MCMBTools {

	@Mod.Instance(Reference.MOD_ID)
	public static MCMBTools instance;

	@SidedProxy(clientSide="com.Deoda.MCMBTools.proxy.ClientProxy" ,serverSide="com.Deoda.MCMBTools.proxy.ServerProxy")
	public static IProxy proxy;
	
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event){
		ConfigurationHandler.init(event.getSuggestedConfigurationFile());
		ModItems.PreInit();
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event){
		ModItems.Init();
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event){
		
	}
}

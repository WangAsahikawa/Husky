package net.husky.logic;

import net.husky.protobuf.Plugin.PluginData;
import net.husky.protobuf.Plugin.PluginRequestData;

public abstract class Plugin {
	
	public Plugin()
	{
		
	}
	
	public String type()
	{
		return "Plugin";
	}
	
	public abstract PluginData invoke(PluginRequestData data,User user)throws Exception;
	
	public abstract String register();
	
}

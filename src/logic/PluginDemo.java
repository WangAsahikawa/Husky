package logic;

import net.husky.logic.Plugin;
import net.husky.logic.User;
import net.husky.protobuf.Plugin.PluginData;
import net.husky.protobuf.Plugin.PluginRequestData;

public class PluginDemo extends Plugin{

	@Override
	public PluginData invoke(PluginRequestData data, User user)
			throws Exception {
		System.out.println("plugindemo invoke");
		return null;
	}

	@Override
	public String register() {
		return "PluginDemo";
	}
	
	public PluginData demo(PluginRequestData data, User user)
	{
		System.out.println("plugindemo demo");
		return null;
	}

}

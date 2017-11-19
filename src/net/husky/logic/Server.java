package net.husky.logic;

import java.nio.ByteBuffer;

import net.husky.core.AbstractServer;
import net.husky.core.ExtensionsManager;

public class Server {

	public static void sendResponse(int methodId,ByteBuffer message,Object channel) throws Exception
	{
		AbstractServer.callbackHandler.sendResponse(methodId, message, channel);
	}

	public static void sendResponse(ByteBuffer message, Object channel,boolean isClose) throws Exception {
		AbstractServer.callbackHandler.sendResponse(message, channel, isClose);	
	}
	
	public static Plugin getPlugin(String name)
	{
		return ExtensionsManager.getPlugin(name);
	}
	
}

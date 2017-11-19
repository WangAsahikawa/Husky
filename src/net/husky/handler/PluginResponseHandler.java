package net.husky.handler;

import net.husky.protobuf.Protocol.MessageHandler;

public class PluginResponseHandler {
	
	public static int invoke(Object message,Object channel)
	{
		return MessageHandler.PluginResponse_VALUE;
	}

}

package net.husky.handler;

import net.husky.protobuf.Protocol.MessageHandler;

public class ModuleDemoRequestHandler {

	public static int invoke(Object message,Object channel)
	{
		System.out.println("["+channel.hashCode()+"]["+MessageHandler.ModuleDemoRequest_VALUE
				+"]ModuleDomeRequestHandler");
		return MessageHandler.ModuleDemoRequest_VALUE;
	}
	
}

package net.husky.handler;

import net.husky.protobuf.ServerError.ReturnStatus;

public class InvalidRequestHandler {
	
	public static int invoke(Object message,Object channel)
	{
		System.out.println("["+channel.hashCode()+"][0]InvalidRequestHandler");
		return ServerErrorResponseHandler.invoke(ReturnStatus.INVALID_REQUEST, channel);
	}

}

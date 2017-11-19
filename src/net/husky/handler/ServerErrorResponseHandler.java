package net.husky.handler;

import java.nio.ByteBuffer;

import net.husky.logic.Message;
import net.husky.logic.Server;
import net.husky.protobuf.Protocol.MessageHandler;
import net.husky.protobuf.ServerError.ReturnStatus;

public class ServerErrorResponseHandler {
	
	public static int invoke(Object message,Object channel)
	{
		System.out.println("["+channel.hashCode()+"]["+MessageHandler.ServerErrorResponse_VALUE+"]"
				+"ServerErrorResponse:"
				+ReturnStatus.valueOf((int)message).name());
		try
		{
			Server.sendResponse(MessageHandler.ServerErrorResponse_VALUE,
					ByteBuffer.wrap(Message.toByteArray((int)message)), channel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageHandler.ServerErrorResponse_VALUE;
	}

}

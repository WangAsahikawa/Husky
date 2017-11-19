package net.husky.handler;

import java.nio.ByteBuffer;

import net.husky.logic.Server;
import net.husky.protobuf.Protocol.MessageHandler;

public class HttpGetRequestHandler {

	public static int invoke(Object message,Object channel) throws Exception
	{
		System.out.println("["+channel.hashCode()+"]["+MessageHandler.HttpGetRequest_VALUE
				+"]HttpGetRequestHandler");
		ByteBuffer buffer=(ByteBuffer)message;
		int length=buffer.remaining();
		byte [] data=new byte[length];
		buffer.get(data);
		String s=new String(data);
		System.out.println(s);
		Server.sendResponse(ByteBuffer.wrap(("HTTP/1.1 200 OK\n"
				+ "Content-Type: text/html; charset=utf-8\n"
				+ "Content-Length: 31\n\n"
				+ "<html><h1>Hello Get</h1></html>").getBytes()),channel,true);
		return MessageHandler.HttpGetRequest_VALUE;
	}
	
}

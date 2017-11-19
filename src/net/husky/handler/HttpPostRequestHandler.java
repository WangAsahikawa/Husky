package net.husky.handler;

import java.nio.ByteBuffer;

import net.husky.logic.Server;
import net.husky.protobuf.Protocol.MessageHandler;

public class HttpPostRequestHandler {

	public static int invoke(Object message,Object channel) throws Exception {
		System.out.println("["+channel.hashCode()+"]["+MessageHandler.HttpPostRequest_VALUE
				+"]HttpPostRequestHandler");
		ByteBuffer buffer=(ByteBuffer)message;
		int length=buffer.remaining();
		byte [] data=new byte[length];
		buffer.get(data);
		String s=new String(data);
		System.out.println(s);
		Server.sendResponse(ByteBuffer.wrap(("HTTP/1.1 200 OK\n"
				+ "Content-Type: text/html; charset=utf-8\n"
				+ "Content-Length: 33\n\n"
				+ "<html><h1>Hello Post</h1></html>").getBytes()),channel,true);
		return MessageHandler.HttpPostRequest_VALUE;
	}
	
}

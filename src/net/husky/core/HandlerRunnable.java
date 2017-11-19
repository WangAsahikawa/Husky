package net.husky.core;

import java.nio.ByteBuffer;

import net.husky.handler.ServerErrorResponseHandler;
import net.husky.protobuf.Protocol.MessageHandler;
import net.husky.protobuf.ServerError.ReturnStatus;
import net.husky.util.DumpTools;
import net.husky.util.Reflection;

public class HandlerRunnable implements Runnable{
	
	ByteBuffer buffer;
	
	Object channel;
	
	public HandlerRunnable(ByteBuffer buffer,Object channel)
	{
		this.buffer=buffer;
		this.channel=channel;
	}

	@Override
	public void run() {
		System.out.println("handler run");
		try {
			if(buffer.remaining()<4)
			{
				return;
			}
			short methodId=buffer.getShort();
			short length=buffer.getShort();
			if(methodId<1024&&buffer.remaining()<length)
			{
				buffer.rewind();
				return;
			}
			System.out.println("methodId:"+methodId+"\tlength:"+length+"\tremaining:"+buffer.remaining());
			
			String name=MessageHandler.valueOf(methodId).name();
			if(name==null)
			{
				name="InvalidRequest";
			}
			StringBuffer protoName=new StringBuffer(AbstractServer.protobufClassPath)
													.append(name.substring(0, name.length()-7))
													.append("$").append(name).append("Data");
			StringBuffer handlerName=new StringBuffer(AbstractServer.handlerClassPath)
													.append(name).append("Handler");
			Object message=decodeMessage(protoName.toString(), buffer, methodId, length);
			
			if (ExtensionsManager.isModule(methodId))
			{
				ExtensionsManager.getModule(methodId).invoke(message, channel);
			}
			else
			{
				Reflection.invokeStaticMethod(handlerName.toString(), "invoke",
						new Object[]{message,channel}, new Class<?>[]{Object.class,Object.class});
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			ServerErrorResponseHandler.invoke(ReturnStatus.INVALID_REQUEST_VALUE, channel);
		}
	}
	
	private Object decodeMessage(String protoName, ByteBuffer buffer, int methodId,int length)
			throws Exception
	{
		if (methodId>512)
			return buffer;
		byte[] message = new byte[length];	
		buffer.get(message);
		DumpTools.printHex(message);
		return Reflection.invokeStaticMethod(protoName, "parseFrom", new Object[] {message});
	}

}

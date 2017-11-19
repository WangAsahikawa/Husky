package net.husky.network;

import java.nio.ByteBuffer;

import net.husky.core.AbstractHandler;
import net.husky.core.AbstractServer;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class MinaHandler extends IoHandlerAdapter implements AbstractHandler{

	public MinaHandler()
	{
		AbstractServer.callbackHandler=this;
	}
	
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		AbstractServer.connected(session);
	}
	
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		AbstractServer.execute(((IoBuffer)message).buf(), session);
	}
	
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		AbstractServer.errorCatch(session);
	}
	
	@Override
	public void sessionClosed(IoSession session) throws Exception {
		AbstractServer.disConnected(session);
	}
	
	@Override
	public void sendResponse(int methodId, ByteBuffer message, Object channel)
			throws Exception {
		ByteBuffer buffer=ByteBuffer.allocate(message.remaining()+4);
		buffer.putShort((short)methodId);
		buffer.putShort((short)message.remaining());
		buffer.put(message);
		buffer.flip();
		((IoSession)channel).write(IoBuffer.wrap(buffer));
		
	}

	@Override
	public void sendResponse(ByteBuffer message, Object channel, boolean isClose)
			throws Exception {
		WriteFuture future=((IoSession)channel).write(IoBuffer.wrap(message));
		if(isClose)
		{
			future.addListener(IoFutureListener.CLOSE);
		}
		
	}

	@Override
	public void close(Object channel) throws Exception {
		((IoSession)channel).close(true);
	}

	@Override
	public String getIp(Object channel) {
		String ip=((IoSession)channel).getRemoteAddress().toString();
		return ip.substring(1, ip.lastIndexOf(":"));
	}

	@Override
	public int getServerPort(Object channel) {
		String ip=((IoSession)channel).getLocalAddress().toString();
		return Integer.parseInt(ip.substring(ip.lastIndexOf(":")+1));
	}

}

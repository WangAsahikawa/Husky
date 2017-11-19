package net.husky.network;

import java.io.IOException;
import java.nio.ByteBuffer;

import net.husky.core.AbstractHandler;
import net.husky.core.AbstractServer;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.filterchain.BaseFilter;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.NextAction;

public class GrizzlyHandler extends BaseFilter implements AbstractHandler{

	public GrizzlyHandler()
	{
		AbstractServer.callbackHandler=this;
	}
	
	@Override
	public NextAction handleAccept(FilterChainContext ctx) throws IOException {
		AbstractServer.connected(ctx.getConnection());
		return super.handleAccept(ctx);
	}
	
	@Override
	public NextAction handleRead(FilterChainContext ctx) throws IOException {
		AbstractServer.execute(((Buffer)ctx.getMessage()).toByteBuffer(), ctx.getConnection());
		return super.handleRead(ctx);
	}
	
	@Override
	public void exceptionOccurred(FilterChainContext ctx, Throwable error) {
		AbstractServer.errorCatch(ctx.getConnection());
	}
	
	@Override
	public NextAction handleClose(FilterChainContext ctx) throws IOException {
		AbstractServer.disConnected(ctx.getConnection());
		return super.handleClose(ctx);
	}
	
	@Override
	public void sendResponse(int methodId, ByteBuffer message, Object channel)
			throws Exception {
		Buffer buffer=((Connection<?>)channel).getTransport().getMemoryManager().allocate(message.remaining()+4);
		buffer.allowBufferDispose(true);
		buffer.putShort((short)methodId);
		buffer.putShort((short)message.remaining());
		buffer.put(message);
		buffer.flip();
		((Connection<?>)channel).write(buffer);
	}

	@Override
	public void sendResponse(ByteBuffer message, Object channel, boolean isClose)
			throws Exception {
		Buffer buffer=((Connection<?>)channel).getTransport().getMemoryManager().allocate(message.remaining());
		buffer.allowBufferDispose(true);
		buffer.put(message);
		buffer.flip();
		((Connection<?>)channel).write(buffer);
		if(isClose)
		{
			((Connection<?>)channel).close();
		}
	}

	@Override
	public void close(Object channel) throws Exception {
		((Connection<?>)channel).close();
	}

	@Override
	public String getIp(Object channel) {
		String ip=((Connection<?>)channel).getPeerAddress().toString();
		return ip.substring(1, ip.lastIndexOf(":"));
	}

	@Override
	public int getServerPort(Object channel) {
		String ip=((Connection<?>)channel).getLocalAddress().toString();
		return Integer.parseInt(ip.substring(ip.lastIndexOf(":")+1));
	}

}

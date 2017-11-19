package net.husky.network;

import java.nio.ByteBuffer;

import net.husky.core.AbstractHandler;
import net.husky.core.AbstractServer;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

public class NettyHandler extends SimpleChannelUpstreamHandler implements AbstractHandler{

	public NettyHandler()
	{
		AbstractServer.callbackHandler=this;
	}
	
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		AbstractServer.connected(e.getChannel());
	}
	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		AbstractServer.execute(((ChannelBuffer)e.getMessage()).toByteBuffer(), e.getChannel());
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		AbstractServer.errorCatch(e.getChannel());
	}
	
	@Override
	public void channelDisconnected(ChannelHandlerContext ctx,
			ChannelStateEvent e) throws Exception {
		AbstractServer.disConnected(e.getChannel());
	}
	
	@Override
	public void sendResponse(int methodId, ByteBuffer message, Object channel)
			throws Exception {
		ChannelBuffer buffer = ChannelBuffers.directBuffer(message.remaining()+4);
		buffer.writeShort(methodId);
		buffer.writeShort(message.remaining());
		buffer.writeBytes(message);
		((Channel)channel).write(buffer);
	}

	@Override
	public void sendResponse(ByteBuffer message, Object channel, boolean isClose)
			throws Exception {
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		buffer.writeBytes(message);
		ChannelFuture future=((Channel)channel).write(buffer);
		if(isClose)
		{
			future.addListener(ChannelFutureListener.CLOSE);
		}		
	}

	@Override
	public void close(Object channel) throws Exception {
		((Channel)channel).close();
		
	}

	@Override
	public String getIp(Object channel) {
		String ip=((Channel)channel).getRemoteAddress().toString();
		return ip.substring(1, ip.lastIndexOf(":"));
	}

	@Override
	public int getServerPort(Object channel) {
		String ip=((Channel)channel).getLocalAddress().toString();
		return Integer.valueOf(ip.substring(ip.lastIndexOf(":")+1));
	}

}

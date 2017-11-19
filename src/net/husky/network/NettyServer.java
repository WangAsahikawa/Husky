package net.husky.network;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import net.husky.core.AbstractServer;

public class NettyServer extends AbstractServer{

	private final ServerBootstrap bootstrap;
	
	public NettyServer()
	{
		bootstrap=new ServerBootstrap(
					new NioServerSocketChannelFactory(
						Executors.newCachedThreadPool(), 
						Executors.newCachedThreadPool()));
		
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				return Channels.pipeline(new NettyHandler());
			}
		});
		
		bootstrap.setOption("child.tcpNoDelay", true);
	}

	@Override
	public void bind(InetSocketAddress inetSocketAddress) throws Exception 
	{
		bootstrap.bind(inetSocketAddress);
	}

	@Override
	public void start() throws Exception
	{
		System.out.println("Netty server start");
	}
	
	public static void main(String[] args) throws Exception 
	{
		AbstractServer.network="netty-3.2.6";
		NettyServer server=new NettyServer();
		server.networkBind();
		server.start();
		server.console();
	}

}

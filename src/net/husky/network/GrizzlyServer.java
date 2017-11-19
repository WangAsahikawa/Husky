package net.husky.network;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.glassfish.grizzly.filterchain.FilterChainBuilder;
import org.glassfish.grizzly.filterchain.TransportFilter;
import org.glassfish.grizzly.nio.transport.TCPNIOTransport;
import org.glassfish.grizzly.nio.transport.TCPNIOTransportBuilder;
import org.glassfish.grizzly.strategies.SameThreadIOStrategy;
import org.glassfish.grizzly.threadpool.GrizzlyExecutorService;
import org.glassfish.grizzly.threadpool.ThreadPoolConfig;

import net.husky.core.AbstractServer;

public class GrizzlyServer extends AbstractServer{

	private final TCPNIOTransport transport;
	private ThreadPoolExecutor threadPool;
	
	public GrizzlyServer()
	{
		threadPool = (ThreadPoolExecutor)Executors.newCachedThreadPool();
		ThreadPoolConfig config=ThreadPoolConfig.defaultConfig().copy()
				.setCorePoolSize(threadPool.getCorePoolSize())
				.setMaxPoolSize(threadPool.getMaximumPoolSize())
				.setPoolName("GRIZZLY-SERVER");
		GrizzlyExecutorService.createInstance(config);
		FilterChainBuilder filterChainBuilder=FilterChainBuilder.stateless();
		filterChainBuilder.add(new TransportFilter());
		filterChainBuilder.add(new GrizzlyHandler());
		TCPNIOTransportBuilder builder=TCPNIOTransportBuilder.newInstance();
		builder.setIOStrategy(SameThreadIOStrategy.getInstance());
		builder.setTcpNoDelay(true);
		transport=builder.build();
		transport.setProcessor(filterChainBuilder.build());
	}
	
	@Override
	public void bind(InetSocketAddress inetSocketAddress) throws Exception {
		transport.bind(inetSocketAddress);
	}

	@Override
	public void start() throws Exception {
		transport.start();
	}

	public static void main(String[] args) throws Exception {
		AbstractServer.network="grizzly-2.1.1";
		GrizzlyServer server=new GrizzlyServer();
		server.networkBind();
		server.start();
		server.console();
	}

}

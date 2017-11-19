package net.husky.network;

import java.net.InetSocketAddress;

import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import net.husky.core.AbstractServer;

public class MinaServer extends AbstractServer{

	private final SocketAcceptor acceptor;
	
	public MinaServer()
	{
		acceptor=new NioSocketAcceptor(Runtime.getRuntime().availableProcessors()+1);
		acceptor.setReuseAddress(true);
		acceptor.getSessionConfig().setTcpNoDelay(true);
		acceptor.setHandler(new MinaHandler());
	}
	
	@Override
	public void bind(InetSocketAddress inetSocketAddress) throws Exception {
		acceptor.bind(inetSocketAddress);		
	}

	@Override
	public void start() throws Exception {
		System.out.println("Mina server start");	
	}
	

	public static void main(String[] args) throws Exception {
		AbstractServer.network="mina-2.0.4";
		MinaServer server=new MinaServer();
		server.networkBind();
		server.start();
		server.console();
	}

}

package net.husky.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import net.husky.handler.InvalidRequestHandler;
import net.husky.protobuf.Protocol;
import net.husky.util.NetworkBind;

public abstract class AbstractServer {
	
	public static int port=5611;
	
	public static String network;
	
	public static AbstractHandler callbackHandler;

	public static ExecutorService threadPool;
	
	public static ScheduledExecutorService scheduledPool;
	
	public static String handlerClassPath;
	
	public static String protobufClassPath;
	
	public static boolean autoLoad=true;
	
	public static BufferedReader consoleInput;
	
	private ExtensionsManager extensionsManager;
	
	public AbstractServer()
	{
		printTitle();
		threadPool = Executors.newCachedThreadPool();
		scheduledPool = Executors.newScheduledThreadPool(2);
		handlerClassPath=InvalidRequestHandler.class.getName()
				.substring(0, InvalidRequestHandler.class.getName().lastIndexOf(".")+1);
		protobufClassPath=Protocol.class.getName()
				.substring(0,Protocol.class.getName().lastIndexOf(".")+1);
		loadExtension();
		
	}
	
	private void printTitle()
	{
		System.out.println("*********************************************************");
		System.out.println("*               Husky Game Server V1.1.0                *");
		System.out.println("*                                                       *");
		System.out.println("*                     Huichuan.Wang                     *");
		System.out.println("*                                                       *");
		System.out.println("*                         2017                          *");
		System.out.println("*                                                       *");
		System.out.println("*********************************************************");
	}
	
	public void networkBind()
	{
		if(!NetworkBind.bind(this, port))
		{
			System.exit(1);
		}
	}
	
	public static void connected(Object channel)
	{
		System.out.println("abstract server connected");
	}
	
	public static void disConnected(Object channel)
	{
		System.out.println("abstract server disconnected");
	}
	
	public static void execute(ByteBuffer buffer,Object channel)
	{
		System.out.println("abstract server execute");
		threadPool.execute(new HandlerRunnable(buffer, channel));
	}
	
	public static void errorCatch(Object channel)
	{
		System.out.println("abstract server error");
	}
	
	public void console()
	{
		consoleInput=new BufferedReader(new InputStreamReader(System.in));
		while(true)
		{
			String line;
			try {
				line=consoleInput.readLine();
				System.out.println(line);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void loadExtension()
	{
		extensionsManager=new ExtensionsManager();
		if(autoLoad)
		{
			extensionsManager.start();
		}
		else
		{
			extensionsManager.process();
		}
	}
	
	public abstract void bind(InetSocketAddress inetSocketAddress) throws Exception;
	
	public abstract void start() throws Exception;
}

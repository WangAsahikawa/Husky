package net.husky.util;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import net.husky.core.AbstractServer;

public class NetworkBind {
	
	public static boolean bind(AbstractServer server,int port)
	{
		int successed=0;
		try 
		{
			Enumeration<NetworkInterface> enumeration=NetworkInterface.getNetworkInterfaces();
			while (enumeration.hasMoreElements()) 
			{
				NetworkInterface networkInterface = (NetworkInterface) enumeration.nextElement();
				Enumeration<InetAddress> enumeration1=networkInterface.getInetAddresses();
				while (enumeration1.hasMoreElements())
				{
					InetAddress inetAddress = (InetAddress) enumeration1.nextElement();
					InetSocketAddress inetSocketAddress=new InetSocketAddress(inetAddress, port);
					String address=inetSocketAddress.toString().substring(1, inetSocketAddress.toString().length());
					if (inetAddress.toString().indexOf(".")>0) 
					{
						try 
						{
							server.bind(inetSocketAddress);
							System.out.println("server is listening on "+address);
							successed=1;
						} catch (Exception e) {
							System.out.println("server could not bind "+address);
						}						
					}					
				}				
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(successed==0)
		{
			System.out.println("no networkinterface is free");
			return false;
		}
		return true;
	}
	
}

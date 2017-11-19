package net.husky.core;

import java.nio.ByteBuffer;

public interface AbstractHandler {

	public abstract void sendResponse(int methodId,ByteBuffer message,Object channel) throws Exception;
	
	public abstract void sendResponse(ByteBuffer message,Object channel,boolean isClose) throws Exception;
	
	public abstract void close(Object channel) throws Exception;
	
	public abstract String getIp(Object channel);
	
	public abstract int getServerPort(Object channel);
	
}

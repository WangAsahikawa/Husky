package net.husky.handler;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;

import net.husky.core.UserManager;
import net.husky.logic.Plugin;
import net.husky.logic.Server;
import net.husky.logic.User;
import net.husky.protobuf.Plugin.PluginData;
import net.husky.protobuf.Plugin.PluginRequestData;
import net.husky.protobuf.Plugin.PluginResponseData;
import net.husky.protobuf.Protocol.MessageHandler;
import net.husky.protobuf.ServerError.ReturnStatus;

public class PluginRequestHandler {
	
	public static int invoke(Object message,Object channel) throws Exception
	{
		System.out.println("["+channel.hashCode()+"]["+MessageHandler.PluginRequest_VALUE
				+"]PluginRequestHandler");
		PluginRequestData data=(PluginRequestData)message;
		if(!data.hasName())
		{
			return ServerErrorResponseHandler.invoke(ReturnStatus.PLUGIN_DATA_NOT_FOUND_VALUE, channel);
		}
		if(!data.hasAction())
		{
			return ServerErrorResponseHandler.invoke(ReturnStatus.PLUGIN_ACTION_NOT_FOUND_VALUE, channel);
		}
		Plugin plugin=Server.getPlugin(data.getName());
		if(plugin==null)
		{
			return ServerErrorResponseHandler.invoke(ReturnStatus.PLUGIN_NOT_FOUND_VALUE, channel);
		}
		User user=UserManager.getUser(channel);
		Class<?> clazz=plugin.getClass();
		PluginData result;
		try {
			Method method=clazz.getMethod(data.getAction(),
					new Class[]{PluginRequestData.class,User.class});
			result=(PluginData)method.invoke(plugin, new Object[]{data,user});
		} catch (NoSuchMethodException | SecurityException e) {
			result=plugin.invoke(data, user);
		}
		if(result==null)
		{
			return MessageHandler.PluginRequest_VALUE;
		}
		PluginResponseData.Builder builder=PluginResponseData.newBuilder();
		builder.setData(result);
		PluginResponseData reMessage=builder.build();
		PluginResponseHandler.invoke(ByteBuffer.wrap(reMessage.toByteArray()), channel);
		return MessageHandler.PluginRequest_VALUE;
	}

}

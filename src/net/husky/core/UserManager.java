package net.husky.core;

import java.util.concurrent.ConcurrentHashMap;

import net.husky.logic.User;

public class UserManager {

	private final static ConcurrentHashMap<Object, User> usersKeyChannel=
			new ConcurrentHashMap<Object, User>();
	private final static ConcurrentHashMap<Integer, User> usersKeyId=
			new ConcurrentHashMap<Integer, User>();
	private final static ConcurrentHashMap<String, User> usersKeyName=
			new ConcurrentHashMap<String, User>();
	
	public static void addUser(User user)
	{
		usersKeyChannel.put(user.getChannel(), user);
		usersKeyId.put(user.getId(), user);
		usersKeyName.put(user.getName(), user);
	}
	
	public static User getUser(Object channel)
	{
		return usersKeyChannel.get(channel);
	}
	
	public static User getUser(int id)
	{
		return usersKeyId.get(id);
	}
	
	public static User getUser(String name)
	{
		return usersKeyName.get(name);
	}
	
	public static void disConnected(Object channel)
	{
		User user=usersKeyChannel.get(channel);
		if(user!=null)
		{
			usersKeyChannel.remove(channel);
			usersKeyId.remove(user.getId());
			usersKeyName.remove(user.getName());
		}
	}
}

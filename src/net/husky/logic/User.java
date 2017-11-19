package net.husky.logic;

import java.util.concurrent.ConcurrentHashMap;

public class User {
	private final int id;
	private final String name;
	private final Object channel;
	private final ConcurrentHashMap<String, String> properites;
	private Room room;
	
	public User(int id,String name,Object channel)
	{
		this.id=id;
		this.name=name;
		this.channel=channel;
		properites=new ConcurrentHashMap<String, String>();		
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Object getChannel() {
		return channel;
	}

	public ConcurrentHashMap<String, String> getProperites() {
		return properites;
	}
	
}

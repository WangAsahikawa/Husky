package net.husky.logic;

public abstract class Scheduled implements Runnable,Cloneable{

	public Scheduled()
	{
		
	}
	
	public String type()
	{
		return "Scheduled";
	}
	
	public abstract int register();
	
}

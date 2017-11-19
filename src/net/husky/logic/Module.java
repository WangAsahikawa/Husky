package net.husky.logic;

public abstract class Module {

	public Module()
	{
		
	}
	
	public String type()
	{
		return "Module";
	}
	
	public abstract int invoke(Object message,Object channel)throws Exception;
	
	public abstract int register();
	
}

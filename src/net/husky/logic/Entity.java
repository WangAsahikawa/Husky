package net.husky.logic;

public abstract class Entity {

	public Entity()
	{
		
	}
	
	public String type()
	{
		return "Entity";
	}
	
	public abstract Object invoke();
	
	public abstract String register();
	
}

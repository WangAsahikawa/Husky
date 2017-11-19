package logic;

import net.husky.logic.Entity;
import net.husky.logic.User;

public class EntityDemo extends Entity{

	@Override
	public Object invoke() {
		// TODO Auto-generated method stub
		return new User(0, "", null);
	}

	@Override
	public String register() {
		return "User";
	}

}

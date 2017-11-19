package logic;

import net.husky.logic.Scheduled;

public class ScheduledDemo extends Scheduled{

	@Override
	public void run() {
		System.out.println("scheduled Dome");
		
	}

	@Override
	public int register() {
		return 200;
	}

}

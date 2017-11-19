package logic;

import net.husky.logic.Module;
import net.husky.protobuf.Protocol.MessageHandler;

public class ModuleDemo extends Module{

	@Override
	public int invoke(Object message, Object channel) throws Exception {
		System.out.println("["+channel.hashCode()+"]["+MessageHandler.ModuleDemoRequest_VALUE
				+"]NewModuleDomeRequestHandler");
		return MessageHandler.ModuleDemoRequest_VALUE;
	}

	@Override
	public int register() {
		return MessageHandler.ModuleDemoRequest_VALUE;
	}

}

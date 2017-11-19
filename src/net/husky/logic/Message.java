package net.husky.logic;

public class Message {
	
	private final String message;
	private final User user;
	private final int router;

	public Message(String message, User sender, int router) {
		this.message = message;
		this.user = sender;
		this.router = router;
	}

	public String getMessage() {
		return message;
	}

	public User getUser() {
		return user;
	}

	public int getRouter() {
		return router;
	}

	public static byte[] toByteArray(int i) {
		byte[] bytes = new byte[4];
		bytes[3] = (byte) i;
		bytes[2] = (byte) (i >> 8);
		bytes[1] = (byte) (i >> 16);
		bytes[0] = (byte) (i >> 24);
		return bytes;
	}

	public static byte[] toByteArray(long i) {
		byte[] bytes = new byte[8];
		bytes[7] = (byte) i;
		bytes[6] = (byte) (i >> 8);
		bytes[5] = (byte) (i >> 16);
		bytes[4] = (byte) (i >> 24);
		bytes[3] = (byte) (i >> 32);
		bytes[2] = (byte) (i >> 40);
		bytes[1] = (byte) (i >> 48);
		bytes[0] = (byte) (i >> 56);		
		return bytes;
	}

	public static byte[] toByteArray(char i) {
		byte[] bytes = new byte[2];
		bytes[0] = (byte) i;
		bytes[1] = (byte) (i >> 8);
		return bytes;
	}

	public static byte[] toByteArray(short i) {
		byte[] bytes = new byte[2];
		bytes[0] = (byte) i;
		bytes[1] = (byte) (i >> 8);
		return bytes;
	}

	public static byte[] toByteArray(byte i) {
		byte[] bytes = new byte[1];
		bytes[0] = i;
		return bytes;
	}

}

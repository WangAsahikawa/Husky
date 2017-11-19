package net.husky.util;

import java.nio.ByteBuffer;

public class DumpTools {

	public static void printHex(byte[] bytes) {
		for (int i = 0; i < bytes.length; i++) {
			if (i % 16 == 0)
				System.out.println();
			if (bytes[i] < 0)
				System.out.print(Integer.toHexString(bytes[i]).substring(5, 7)
						.toUpperCase());
			else if (bytes[i] < 16)
				System.out.print("0"
						+ Integer.toHexString(bytes[i]).toUpperCase());
			else
				System.out.print(Integer.toHexString(bytes[i]).toUpperCase());
			System.out.print(" ");
		}
		System.out.println();
	}

	public static void printHex(ByteBuffer buf) {
		for (int i = 0; i < buf.capacity(); i++) {
			if (i % 16 == 0)
				System.out.println();
			byte b = buf.get();
			if (b < 0)
				System.out.print(Integer.toHexString(b).substring(5, 7)
						.toUpperCase());
			else if (b < 16)
				System.out.print("0" + Integer.toHexString(b).toUpperCase());
			else
				System.out.print(Integer.toHexString(b).toUpperCase());
			System.out.print(" ");
		}
		System.out.println();
	}
	
}

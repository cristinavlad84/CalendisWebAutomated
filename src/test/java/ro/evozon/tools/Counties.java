package ro.evozon.tools;

import java.util.Random;

public enum Counties {

	CLUJ(20), BUCURESTI(9), ALBA(21),;
	private int value;

	Counties(final int value) {
		this.value = value;

	}

	public int getOption() {
		return value;
	}

	private static final Counties[] VALUES = values();
	private static final int SIZE = VALUES.length;
	private static final Random RANDOM = new Random();

	public static Counties getRandomCounty() {
		return VALUES[RANDOM.nextInt(SIZE)];
	}
}

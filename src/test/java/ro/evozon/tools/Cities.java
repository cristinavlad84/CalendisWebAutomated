package ro.evozon.tools;

import java.util.Random;

public enum Cities {

	CLUJ_NAPOCA(5587), ALBA_IULIA(6021), BUCURESTI(2715),;
	private int value;

	Cities(final int value) {
		this.value = value;

	}

	public int getOption() {
		return value;
	}

	private static final Cities[] VALUES = values();
	private static final int SIZE = VALUES.length;
	private static final Random RANDOM = new Random();

	public static Cities getRandomCounty() {
		return VALUES[RANDOM.nextInt(SIZE)];
	}
}

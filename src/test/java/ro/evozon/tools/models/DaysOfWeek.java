package ro.evozon.tools.models;

public enum DaysOfWeek {
	LUNI("Luni"), MARTI("Marti"), MIERCURI("Miercuri"), JOI("Joi"), VINERI("Vineri"), SAMBATA("Sambata"), DUMINICA(
			"Duminica");
	// static final int days=values().length;

	private String dayOfWeek;

	DaysOfWeek(String day) {
		this.dayOfWeek = day;
	}

	public String dayOfWeekValue() {
		return dayOfWeek;
	}

	public int getNumericDayOfWeek() {
		switch (this) {
		case LUNI:
			return 1;
		case MARTI:
			return 2;
		case MIERCURI:
			return 3;
		case JOI:
			return 4;
		case VINERI:
			return 5;
		case SAMBATA:
			return 6;
		case DUMINICA:
			return 7;
		default:
			throw new AssertionError("Unknown day" + this);
		}
	}

	public static int getNumericValueDaysOfWeek(String day) {
		switch (day) {
		case "Luni":
			return 1;
		case "Marti":
			return 2;
		case "Miercuri":
			return 3;
		case "Joi":
			return 4;
		case "Vineri":
			return 5;
		case "Sambata":
			return 6;
		case "Duminica":
			return 7;
		default:
			throw new AssertionError("Unknown day" + day);
		}
	}

	public static Enum getEnumFromString(String day) {
		switch (day) {
		case "Luni":
			return Enum.valueOf(DaysOfWeek.class, "LUNI");
		case "Marti":
			return Enum.valueOf(DaysOfWeek.class, "MARTI");
		case "Miercuri":
			return Enum.valueOf(DaysOfWeek.class, "MIERCURI");
		case "Joi":
			return Enum.valueOf(DaysOfWeek.class, "JOI");
		case "Vineri":
			return Enum.valueOf(DaysOfWeek.class, "VINERI");
		case "Sambata":
			return Enum.valueOf(DaysOfWeek.class, "SAMBATA");
		case "Duminica":
			return Enum.valueOf(DaysOfWeek.class, "DUMINICA");
		default:
			throw new AssertionError("Unknown day" + day);
		}

	}
}

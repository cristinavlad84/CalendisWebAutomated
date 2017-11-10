package ro.evozon.tools.enums;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public enum Cities {

	CLUJ("5587", "5588", "5608", "5611"), ALBA("6021", "6029", "6040"), BUCURESTI("2715"),;
	private List<String> values;

	Cities(String... valuess) {

		this.values = Arrays.asList(valuess);

	}

	public List<String> getOption() {
		return values;
	}

	@Override
	public String toString() {
		return super.toString();
	}

	
}

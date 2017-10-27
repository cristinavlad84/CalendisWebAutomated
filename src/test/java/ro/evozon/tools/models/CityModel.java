package ro.evozon.tools.models;

import java.util.ArrayList;
import java.util.List;

public class CityModel {
	private String name;
	private String id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public static List<CityModel> createCityModelFrom(List<String> cityNames, List<String> cityIds) {
		List<CityModel> mapData = new ArrayList<>();
		for (int i = 0; i < cityNames.size(); i++) {

			CityModel cityObj = new CityModel();
			cityObj.setName(cityNames.get(i));
			cityObj.setId(cityIds.get(i));
			mapData.add(cityObj);
		}
		return mapData;
	}

}

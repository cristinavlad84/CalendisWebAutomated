package ro.evozon.tools.models;

import java.util.ArrayList;
import java.util.List;

public class RegionModel {
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

	public static List<RegionModel> createRegionModelFrom(List<String> regionNames, List<String> regionIds) {
		List<RegionModel> mapData = new ArrayList<>();
		for (int i = 0; i < regionNames.size(); i++) {

			RegionModel cityObj = new RegionModel();
			cityObj.setName(regionNames.get(i));
			cityObj.setId(regionIds.get(i));
			mapData.add(cityObj);
		}
		return mapData;
	}

}

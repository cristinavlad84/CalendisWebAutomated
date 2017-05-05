package ro.evozon.tools.models;

public class ListingItem {
	private final String name;
	private final String price;

	public ListingItem(String name, String price) {
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public String getPrice() {
		return price;
	}

}

package ro.evozon.tools.models;

public class PriceList {
	private String serviceName;
	private String servicePrice;

	public String getServiceName() {
		return serviceName;
	}

	public String getServicePrice() {
		return servicePrice;
	}

	public PriceList(String serviceName, String servicePrice) {
		super();
		this.serviceName = serviceName;
		this.servicePrice = servicePrice;
	}


}

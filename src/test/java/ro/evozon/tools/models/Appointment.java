package ro.evozon.tools.models;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Appointment {
	protected LocalTime startHour;
	protected LocalTime endHour;
	public List<String> serviceName = new ArrayList<String>();;
	protected String domainName;
	protected String specialist;
	public List<String> clientName = new ArrayList<String>();

	public LocalTime getStartHour() {
		return startHour;
	}

	public void setStartHour(LocalTime startHour) {
		this.startHour = startHour;
	}

	public LocalTime getEndHour() {
		return endHour;
	}

	public void setEndHour(LocalTime endHour) {
		this.endHour = endHour;
	}

	public List<String> getServiceName() {
		return serviceName;
	}

	public List<String> getClientName() {
		return clientName;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getSpecialist() {
		return specialist;
	}

	public void setSpecialist(String specialist) {
		this.specialist = specialist;
	}

}

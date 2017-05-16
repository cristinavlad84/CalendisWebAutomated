package ro.evozon.tools;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Pattern;

import ro.evozon.tools.models.Appointment;

public class AppointmentPatternMatcher {
	public Appointment appointment = new Appointment();

	public AppointmentPatternMatcher() {

	}

	public Appointment extractAppointmentDetailsFrom(String details) {
		details = details.replaceAll("\\s+", ""); // replace all white spaces
		System.out.println(details);
		Scanner sc = new Scanner(details);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm", Locale.ENGLISH);
		Pattern p = Pattern.compile("[\\.\\s+-]");//"." and "-" delimiter
		sc = sc.useDelimiter(p);
		String startTime = sc.next();//first group 
		LocalTime date = LocalTime.parse(startTime, formatter);
		appointment.setStartHour(date);
		System.out.println("start" + date);
		String endTime = sc.next(); //second group
		LocalTime date2 = LocalTime.parse(endTime, formatter);
		appointment.setEndHour(date2);
		System.out.println("end " + date2);
		String clientName = sc.next(); //3'rd group
		System.out.println("client " + clientName);
		appointment.clientName.add(clientName);
		String service = sc.next();
		appointment.serviceName.add(service); //4'th group
		System.out.println("service " + service);
		return appointment;
	}
	public void main(String[] args){
		extractAppointmentDetailsFrom("19:40 - 20:40 - LBZMYsSZ JywtSiqE - Aqswd");
	}
}

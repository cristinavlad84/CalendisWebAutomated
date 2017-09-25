package ro.evozon.tools.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ro.evozon.tools.ConfigUtils;
import ro.evozon.tools.Constants;

public class ReadCSV {
	public static List<List<String>> readFromCsvFile(String fileName) {
		List<List<String>> values = new ArrayList<List<String>>();
		try (Stream<String> lines = Files.lines(Paths.get(fileName))) {
			values = lines.map(line -> Arrays.asList(line.split(","))).collect(Collectors.toList());
			values.forEach(value -> System.out.println(value));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return values;
	}

	public static String get_domain_location_by_domain_name(List<List<String>> valuesList, String numeDomeniu) {
		String locatieDomeniu = "";
		for (int i = 0; i < valuesList.size(); i++) {
			if (valuesList.get(i).get(0).contains(numeDomeniu)) {
				locatieDomeniu = valuesList.get(i).get(1);
			}
		}
		return locatieDomeniu;
	}

	public static void main(String[] args) {

		String fileName = Constants.OUTPUT_PATH_DATA_DRIVEN + ConfigUtils.getOutputFileNameForDomain();
		List<List<String>> values = readFromCsvFile(fileName);
		String locatie = get_domain_location_by_domain_name(values, "Scoala 5-8");
		System.out.println("locatia este" + locatie);
		// List<String> sediu = values.stream().flatMap(pList ->
		// pList.stream()).collect(Collectors.toList());
		// System.out.println("flatten list" + sediu);
		// List<Optional<String>> pList = values.stream()
		// .map(k -> k.stream().filter(s -> s.contains("Scoala
		// 1-5")).findFirst()).collect(Collectors.toList());
		// pList.stream().forEach((k->System.out.println("serch for" + k));
		// System.out.println("search for" + pList);

	}

}

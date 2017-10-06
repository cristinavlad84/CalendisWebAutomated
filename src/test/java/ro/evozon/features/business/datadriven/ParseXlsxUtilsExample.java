package ro.evozon.features.business.datadriven;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;

import ro.evozon.tools.ConfigUtils;
import ro.evozon.tools.Constants;

public class ParseXlsxUtilsExample {
	public static void main(String[] args) throws Exception {
		ParseXlsxUtils.parseTestDataFromExcelFile();
		ParseXlsxUtilsExample parse = new ParseXlsxUtilsExample();
		// read records and header from csv file, put all records in a list of
		// strings
//		String csvFileReceptie = Constants.OUTPUT_PATH_DATA_DRIVEN + ConfigUtils.getOutputFileNameForReceptionist();
//		List<String> recordsList = ParseXlsxUtils.readFile(csvFileReceptie);
//		// for (String s : recordsList) {
//		// System.out.println("record " + s);
//		// }
//		String columnHeaderToAdd = "parola receptionist";
//		List<String> recordToAddList = Arrays.asList("Calendis01", "Brutus01", "Keops01");
//		// write again all records and append new column with their records
//		ParseXlsxUtils.appendToCsvFile(recordsList, columnHeaderToAdd, recordToAddList, csvFileReceptie);

	}

}

package ro.evozon.tools.utils.fileutils;

import ro.evozon.tools.models.Location;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class CSVUtils {
	private static final char DEFAULT_SEPARATOR = ',';

	public static void writeLine(Writer w, List<String> values) throws IOException {
		writeLine(w, values, DEFAULT_SEPARATOR, ' ');
	}


	public static void writeLine(Writer w, List<String> values, char separators) throws IOException {
		writeLine(w, values, separators, ' ');
	}

	// https://tools.ietf.org/html/rfc4180
	private static String followCVSformat(String value) {

		String result = value;
		if (result.contains("\"")) {
			result = result.replace("\"", "\"\"");
		}
		return result;

	}

	public static void writeLine(Writer w, List<String> values, char separators, char customQuote) throws IOException {

		boolean first = true;

		// default customQuote is empty

		if (separators == ' ') {
			separators = DEFAULT_SEPARATOR;
		}

		StringBuilder sb = new StringBuilder();
		for (String value : values) {
			if (!first) {
				sb.append(separators);
			}
			if (customQuote == ' ') {
				sb.append(followCVSformat(value));
			} else {
				sb.append(customQuote).append(followCVSformat(value)).append(customQuote);
			}

			first = false;
		}
		sb.append("\n");
		w.append(sb.toString());

	}

	public static String listToString(List<String> itemList) {
			String actual = itemList.stream().
				reduce((t, u) -> t + "," + u).
				get();
		return actual;
	}
	public String objectsToString(List<Location>mList){
		String str=  mList.stream().map(Location::toString).collect(Collectors.joining(","));
		return str;
	}
	public static int getFileNoOfRows(String inputFile){
		int linenumber = 0;
		try{

			File file =new File(inputFile);

			if(file.exists()){

				FileReader fr = new FileReader(file);
				LineNumberReader lnr = new LineNumberReader(fr);



				while (lnr.readLine() != null){
					linenumber++;
				}

				System.out.println("Total number of lines : " + linenumber);

				lnr.close();


			}else{
				System.out.println("File does not exists!");
			}

		}catch(IOException e){
			e.printStackTrace();
		}
		System.out.println("no of rows in file "+linenumber);
		return linenumber;

	}
}

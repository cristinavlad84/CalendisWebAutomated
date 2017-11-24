package ro.evozon.features.business.datadriven;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ro.evozon.tools.ConfigUtils;
import ro.evozon.tools.FieldGenerators;
import ro.evozon.tools.FieldGenerators.Mode;
import ro.evozon.tools.utils.fileutils.ReadFromExcelFile;
import ro.evozon.tools.utils.fileutils.CSVUtils;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class ParseXlsxUtils {

	public static String emailContAdministrator;
	public static String telefonAfacere;
	public static String businessPassword = ro.evozon.tools.Constants.BUSINESS_PASSWORD_API_TEST_ACCOUNT;
	public static String categorieAfacere;
	public static String numeAfacere;
	public static String adresaAfacere;
	public static String domeniuAfacere, locatieDomeniu;
	public static String judetLocatie, orasLocatie, telefonLocatie, numeLocatie;
	public static String serviciuPrincipal, angajatPrincipal;
	public String firstServiceDuration;
	public static String firstServicePrice;
	public static String firstServiceMaxPersons, firtstServiceDuration;
	public static String telefonAngajatPrincipal;
	public static String emailAngajatPrincipal;
	public static String parolaAngajatPrincipal;
	public static String domeniuPrincipal, adresaLocatiePrincipala, numeLocatiePrincipala, judetLocatiePrincipala,
			orasLocatiePrincipala;
	public static String orar_sediu_luni, orar_sediu_marti, orar_sediu_miercuri, orar_sediu_joi, orar_sediu_vineri,
			orar_sediu_sambata, orar_sediu_duminica;
	public static String orar_angajat_luni, orar_angajat_marti, orar_angajat_miercuri, orar_angajat_joi, orar_angajat_vineri,
			orar_angajat_sambata, orar_angajat_duminica;

	static {
		parolaAngajatPrincipal = FieldGenerators.generateRandomString(8, Mode.ALPHANUMERIC);
		System.out.println("@constructor@");
	}

	public ParseXlsxUtils() {
	}

	public static List<String> readFile(String filename) {
		List<String> records = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = reader.readLine()) != null) {
				records.add(line);
			}
			reader.close();
			return records;
		} catch (Exception e) {
			System.err.format("Exception occurred trying to read '%s'.", filename);
			e.printStackTrace();
			return null;
		}
	}

	public static void appendToCsvFile(List<String> records, String header, List<String> recordsToAddList,
			String csvFile) throws IOException {

		FileWriter writerReceptie = new FileWriter(csvFile);
		// write header on first line
		String headingListReceptie = records.get(0);
		CSVUtils.writeLine(writerReceptie, Arrays.asList(headingListReceptie, header));
		for (int i = 1; i < records.size(); i++) {

			CSVUtils.writeLine(writerReceptie, Arrays.asList(records.get(i), recordsToAddList.get(i)));
		}

		writerReceptie.flush();
		writerReceptie.close();
	}

	public static List<LinkedHashMap<String, Cell>> getDataFromExcelSheet(String fileName, String sheetName) {
		List<LinkedHashMap<String, Cell>> myDataCollection = new ArrayList<LinkedHashMap<String, Cell>>();
		FileInputStream excelFile;
		try {
			excelFile = new FileInputStream(new File(fileName));
			Workbook workbook;
			try {
				workbook = new XSSFWorkbook(excelFile);
				Sheet datatypeSheet = workbook.getSheet(sheetName);
				myDataCollection = ReadFromExcelFile.rowsFrom(datatypeSheet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return myDataCollection;
	}

	public static void parseExcelFile(String pathAndNameForExcelFile, String pathToFolderToSaveCsvFiles) {
		try {
			System.out.println("parse Excel File");
			DataFormatter fmt = new DataFormatter();
			String fileName = pathAndNameForExcelFile;
			FileInputStream excelFile = new FileInputStream(new File(fileName));
			Workbook workbook = null;
			try {
				workbook = WorkbookFactory.create(new File(fileName));
			} catch (InvalidFormatException e) {
				e.printStackTrace();
			}
			// read data from Condadministrator sheet and write them on
			// properties file (on @After method)
			Sheet datatypeSheet = workbook.getSheet("Cont adminstrator");
			List<LinkedHashMap<String, Cell>> myDataCollection = ReadFromExcelFile.rowsFrom(datatypeSheet);
			myDataCollection.forEach(k -> System.out.println(k));
			numeAfacere = fmt.formatCellValue(myDataCollection.get(0).get("Nume Afacere"));
			categorieAfacere = fmt.formatCellValue(myDataCollection.get(0).get("Categorie Afacere"));
			emailContAdministrator = fmt.formatCellValue(myDataCollection.get(0).get("Email Cont Administrator"));

			telefonAfacere = fmt.formatCellValue(myDataCollection.get(0).get("Telefon afacere"));
			// read data from Locatii sheet
			Sheet datatypeSheet2 = workbook.getSheet("Locatii");
			List<LinkedHashMap<String, Cell>> myDataCollection2 = ReadFromExcelFile.rowsFrom(datatypeSheet2);
			myDataCollection2.forEach(k -> System.out.println(k));
			adresaLocatiePrincipala = fmt.formatCellValue(myDataCollection2.get(0).get("Adresa locatie"));
			numeLocatiePrincipala = fmt.formatCellValue(myDataCollection2.get(0).get("Nume locatie"));
			judetLocatiePrincipala = fmt.formatCellValue(myDataCollection2.get(0).get("Judet locatie"));
			orasLocatiePrincipala = fmt.formatCellValue(myDataCollection2.get(0).get("Localitate locatie"));

			orar_sediu_luni = fmt.formatCellValue(myDataCollection2.get(0).get("Luni"));
			orar_sediu_marti = fmt.formatCellValue(myDataCollection2.get(0).get("Marti"));
			orar_sediu_miercuri = fmt.formatCellValue(myDataCollection2.get(0).get("Miercuri"));
			orar_sediu_joi = fmt.formatCellValue(myDataCollection2.get(0).get("Joi"));
			orar_sediu_vineri = fmt.formatCellValue(myDataCollection2.get(0).get("Vineri"));
			orar_sediu_sambata = fmt.formatCellValue(myDataCollection2.get(0).get("Sambata"));
			orar_sediu_duminica = fmt.formatCellValue(myDataCollection2.get(0).get("Duminica"));
			// read all lines from sheet and write them to csv file
			String csvFile = pathToFolderToSaveCsvFiles + ConfigUtils.getOutputFileNameForLocation();
			FileWriter writer = new FileWriter(csvFile);
			// write header on first line
			List<String> headingList = ReadFromExcelFile.getKeys(myDataCollection2.get(0));
			CSVUtils.writeLine(writer, headingList);
			for (int i = 1; i < myDataCollection2.size(); i++) {
				adresaAfacere = fmt.formatCellValue(myDataCollection2.get(i).get("Adresa locatie"));
				judetLocatie = fmt.formatCellValue(myDataCollection2.get(i).get("Judet locatie"));
				orasLocatie = fmt.formatCellValue(myDataCollection2.get(i).get("Localitate locatie"));
				telefonLocatie = fmt.formatCellValue(myDataCollection2.get(i).get("Telefon locatie"));
				numeLocatie = fmt.formatCellValue(myDataCollection2.get(i).get("Nume locatie"));
				String luni = fmt.formatCellValue(myDataCollection2.get(i).get("Luni"));
				String marti = fmt.formatCellValue(myDataCollection2.get(i).get("Marti"));
				String miercuri = fmt.formatCellValue(myDataCollection2.get(i).get("Miercuri"));
				String joi = fmt.formatCellValue(myDataCollection2.get(i).get("Joi"));
				String vineri = fmt.formatCellValue(myDataCollection2.get(i).get("Vineri"));
				String sambata = fmt.formatCellValue(myDataCollection2.get(i).get("Sambata"));
				String duminica = fmt.formatCellValue(myDataCollection2.get(i).get("Duminica"));
				CSVUtils.writeLine(writer, Arrays.asList(adresaAfacere, judetLocatie, orasLocatie, telefonLocatie,
						numeLocatie, luni, marti, miercuri, joi, vineri, sambata, duminica));
			}

			writer.flush();
			writer.close();
			// read data from Receptie sheet
			Sheet datatypeSheetRec = workbook.getSheet("Receptie");
			System.out.println("sheet " + datatypeSheetRec.getSheetName());
			List<LinkedHashMap<String, Cell>> myDataCollectionRec = ReadFromExcelFile.rowsFrom(datatypeSheetRec);
			myDataCollectionRec.forEach(k -> System.out.println(k));
			domeniuPrincipal = fmt.formatCellValue(myDataCollectionRec.get(0).get("Nume domeniu"));
			// read all lines from sheet and write them to csv file
			String csvFileReceptie = pathToFolderToSaveCsvFiles+ ConfigUtils.getOutputFileNameForReceptionist();
			FileWriter writerReceptie = new FileWriter(csvFileReceptie);
			// write header on first line
			List<String> headingListReceptie = ReadFromExcelFile.getKeys(myDataCollectionRec.get(0));
			CSVUtils.writeLine(writerReceptie, headingListReceptie);
			for (int i = 0; i < myDataCollectionRec.size(); i++) {
				String numeReceptionist = fmt.formatCellValue(myDataCollectionRec.get(i).get("Nume receptionist"));
				String emailReceptionist = fmt.formatCellValue(myDataCollectionRec.get(i).get("Email receptionist"));
				String telefonReceptionist = fmt
						.formatCellValue(myDataCollectionRec.get(i).get("Telefon receptionist"));
				CSVUtils.writeLine(writerReceptie,
						Arrays.asList(numeReceptionist, emailReceptionist, telefonReceptionist));
			}

			writerReceptie.flush();
			writerReceptie.close();
			// read data from Domenii sheet
			Sheet datatypeSheet3 = workbook.getSheet("Domenii");
			System.out.println("sheet " + datatypeSheet3.getSheetName());
			List<LinkedHashMap<String, Cell>> myDataCollection3 = ReadFromExcelFile.rowsFrom(datatypeSheet3);
			myDataCollection2.forEach(k -> System.out.println(k));
			domeniuPrincipal = fmt.formatCellValue(myDataCollection3.get(0).get("Nume domeniu"));
			// read all lines from sheet and write them to csv file
			String csvFileDomenii = pathToFolderToSaveCsvFiles + ConfigUtils.getOutputFileNameForDomain();
			FileWriter writerDomenii = new FileWriter(csvFileDomenii);
			// write header on first line
			List<String> headingListDomenii = ReadFromExcelFile.getKeys(myDataCollection3.get(0));
			headingListDomenii.add("Locatie id");
			CSVUtils.writeLine(writerDomenii, headingListDomenii);
			for (int i = 1; i < myDataCollection3.size(); i++) {
				domeniuAfacere = fmt.formatCellValue(myDataCollection3.get(i).get("Nume domeniu"));
				locatieDomeniu = fmt.formatCellValue(myDataCollection3.get(i).get("Locatia domeniului"));
				CSVUtils.writeLine(writerDomenii, Arrays.asList(domeniuAfacere, locatieDomeniu,"tbd"));
			}

			writerDomenii.flush();
			writerDomenii.close();
			// read all values from line 0 for domain association
			String csvFileAllDomains = pathToFolderToSaveCsvFiles + ConfigUtils.getOutputFileNameForAllDomains();
			FileWriter writerAllDomains = new FileWriter(csvFileAllDomains);
			// write header on first line
			List<String> headingListAllDomenains = ReadFromExcelFile.getKeys(myDataCollection3.get(0));
			CSVUtils.writeLine(writerAllDomains, headingListAllDomenains);
			for (int i = 0; i < myDataCollection3.size(); i++) {
				domeniuAfacere = fmt.formatCellValue(myDataCollection3.get(i).get("Nume domeniu"));
				locatieDomeniu = fmt.formatCellValue(myDataCollection3.get(i).get("Locatia domeniului"));
				CSVUtils.writeLine(writerAllDomains, Arrays.asList(domeniuAfacere, locatieDomeniu));
			}

			writerAllDomains.flush();
			writerAllDomains.close();

			/***
			 * read data from Servicii sheet
 			 */

			Sheet datatypeSheet4 = workbook.getSheet("Servicii");
			System.out.println("sheet " + datatypeSheet4.getSheetName());
			List<LinkedHashMap<String, Cell>> myDataCollection4 = ReadFromExcelFile.rowsFrom(datatypeSheet4);
			myDataCollection4.forEach(k -> System.out.println(k));
			serviciuPrincipal = myDataCollection4.get(0).get("Serviciu").toString();
			firstServiceMaxPersons = fmt.formatCellValue(myDataCollection4.get(0).get("Persoane serviciu"));
			firtstServiceDuration = fmt.formatCellValue(myDataCollection4.get(0).get("Durata serviciu"));
			firstServicePrice = fmt.formatCellValue(myDataCollection4.get(0).get("Pret serviciu"));

			// read all lines from sheet and write them to csv file
			String csvFileServicii = pathToFolderToSaveCsvFiles + ConfigUtils.getOutputFileNameForService();
			FileWriter writerServicii = new FileWriter(csvFileServicii);
			// write header on first line
			List<String> headingListServicii = ReadFromExcelFile.getKeys(myDataCollection4.get(0));
			headingListServicii.add("Domeniu id");
			headingListServicii.add("Locatie id");
			CSVUtils.writeLine(writerServicii, headingListServicii);
			for (int i = 1; i < myDataCollection4.size(); i++) {
				String domeniuServiciuAsociat = myDataCollection4.get(i).get("Domeniul asociat").toString();
				String serviciu = fmt.formatCellValue(myDataCollection4.get(i).get("Serviciu"));
				String durataServiciu = fmt.formatCellValue((myDataCollection4.get(i).get("Durata serviciu")));
				String pretServiciu = fmt.formatCellValue(myDataCollection4.get(i).get("Pret serviciu"));
				String persoaneServiciu = fmt.formatCellValue(myDataCollection4.get(i).get("Persoane serviciu"));

				CSVUtils.writeLine(writerServicii, Arrays.asList(domeniuServiciuAsociat, serviciu, durataServiciu,
						pretServiciu, persoaneServiciu, "tbd","tbd"));
			}

			writerServicii.flush();
			writerServicii.close();
			/**
			 * read data from angajati sheet
			 */

			Sheet datatypeSheet5 = workbook.getSheet("Angajati");
			System.out.println("sheet " + datatypeSheet5.getSheetName());
			List<LinkedHashMap<String, Cell>> myDataCollection5 = ReadFromExcelFile.rowsFrom(datatypeSheet5);
			myDataCollection5.forEach(k -> System.out.println(k));
			angajatPrincipal = fmt.formatCellValue(myDataCollection5.get(0).get("Nume angajat"));
			emailAngajatPrincipal = fmt.formatCellValue(myDataCollection5.get(0).get("Email angajat"));
			telefonAngajatPrincipal = fmt.formatCellValue(myDataCollection5.get(0).get("Telefon angajat"));
			orar_angajat_luni = fmt.formatCellValue(myDataCollection5.get(0).get("Luni"));
			orar_angajat_marti = fmt.formatCellValue(myDataCollection5.get(0).get("Marti"));
			orar_angajat_miercuri = fmt.formatCellValue(myDataCollection5.get(0).get("Miercuri"));
			orar_angajat_joi = fmt.formatCellValue(myDataCollection5.get(0).get("Joi"));
			orar_angajat_vineri = fmt.formatCellValue(myDataCollection5.get(0).get("Vineri"));
			orar_angajat_sambata = fmt.formatCellValue(myDataCollection5.get(0).get("Sambata"));
			orar_angajat_duminica = fmt.formatCellValue(myDataCollection5.get(0).get("Duminica"));
			// read all lines from sheet and write them to csv file
			String csvFileAngajati = pathToFolderToSaveCsvFiles + ConfigUtils.getOutputFileNameForStaff();

			FileWriter writerAngajati = new FileWriter(csvFileAngajati);
			// write header on first line
			List<String> headingListAngajati = ReadFromExcelFile.getKeys(myDataCollection5.get(0));
			headingListAngajati.add("Serviciu id");
			headingListAngajati.add("Domeniu id");
			headingListAngajati.add("Locatie id");
			headingListAngajati.add("Staff id");
			CSVUtils.writeLine(writerAngajati, headingListAngajati);
			for (int i = 1; i < myDataCollection5.size(); i++) {
				String numeAngajat = fmt.formatCellValue(myDataCollection5.get(i).get("Nume angajat"));
				String emailAngajat = fmt.formatCellValue(myDataCollection5.get(i).get("Email angajat"));
				String telefonAngajat = fmt.formatCellValue(myDataCollection5.get(i).get("Telefon angajat"));
				String luni = fmt.formatCellValue(myDataCollection5.get(i).get("Luni"));
				String marti = fmt.formatCellValue(myDataCollection5.get(i).get("Marti"));
				String miercuri = fmt.formatCellValue(myDataCollection5.get(i).get("Miercuri"));
				String joi = fmt.formatCellValue(myDataCollection5.get(i).get("Joi"));
				String vineri = fmt.formatCellValue(myDataCollection5.get(i).get("Vineri"));
				String sambata = fmt.formatCellValue(myDataCollection5.get(i).get("Sambata"));
				String duminica = fmt.formatCellValue(myDataCollection5.get(i).get("Duminica"));
				String serviciuAsignat = fmt.formatCellValue(myDataCollection5.get(i).get("Serviciu asignat"));
				CSVUtils.writeLine(writerAngajati, Arrays.asList(numeAngajat, emailAngajat, telefonAngajat, luni, marti,
						miercuri, joi, vineri, sambata, duminica, serviciuAsignat,"tbd","tbd","tbd","tbd"));
			}

			writerAngajati.flush();
			writerAngajati.close();
			// read data from Permisiuni sheet
			Sheet datatypeSheet6 = workbook.getSheet("Permisiuni");
			System.out.println("sheet " + datatypeSheet6.getSheetName());
			List<LinkedHashMap<String, Cell>> myDataCollection6 = ReadFromExcelFile.rowsFrom(datatypeSheet6);
			myDataCollection6.forEach(k -> System.out.println(k));
			String csvFilePermisiuni = pathToFolderToSaveCsvFiles + ConfigUtils.getOutputFileNameForPermission();
			FileWriter writerPermisiuni = new FileWriter(csvFilePermisiuni);
			// write header on first line
			List<String> headingListPermisiuni = ReadFromExcelFile.getKeys(myDataCollection6.get(0));
			CSVUtils.writeLine(writerPermisiuni, headingListPermisiuni);
			for (int i = 0; i < myDataCollection6.size(); i++) {
				String appCreate  = "";

				if(myDataCollection6.get(i).get("Creare programari")==null) {
					appCreate ="tbd"; //parsing csv with serenity dd requires non empty values
				}else {
					appCreate = fmt.formatCellValue(myDataCollection6.get(i).get("Creare programari")).toString();
				}
				String appEditFuture = "";
				if(myDataCollection6.get(i).get("Modificari programari in viitor") ==null){
					appEditFuture="tbd";
				}else
				{
					appEditFuture=fmt
						.formatCellValue(myDataCollection6.get(i).get("Modificari programari in viitor")).toString();
				}


				String appEditPast = "";
				if(myDataCollection6.get(i).get("Modificari programari in trecut")==null){
					appEditPast="tbd";
				}else {
					appEditPast=	fmt
							.formatCellValue(myDataCollection6.get(i).get("Modificari programari in trecut")).toString();
				}

				String calendarView = "";
				if(myDataCollection6.get(i).get("Vizualizare calendar")==null){
					calendarView="tbd";
				}
				else
				{
					calendarView=fmt.formatCellValue(myDataCollection6.get(i).get("Vizualizare calendar"))
						.toString();
				}

				String appCreateForOthers = "";
				if(myDataCollection6.get(i).get("Creare programari alti specialisti")==null) {
					appCreateForOthers="tbd";
				}
				else{
					appCreateForOthers=fmt
							.formatCellValue(myDataCollection6.get(i).get("Creare programari alti specialisti")).toString();
				}

				String appEditFutureOthers = "";
				if(myDataCollection6.get(i).get("Modificari programari in viitor alti specialisti")==null) {
					appEditFutureOthers="tbd";
				}
				else
				{
					appEditFutureOthers=fmt
							.formatCellValue(
									myDataCollection6.get(i).get("Modificari programari in viitor alti specialisti"))
							.toString();
				}

				String appEditPastOthers = "";
				if(myDataCollection6.get(i).get("Modificari programari in trecut alti specialisti") ==null) {
					appEditPastOthers="tbd";
				}
				else
				{
					appEditPastOthers=fmt
							.formatCellValue(
									myDataCollection6.get(i).get("Modificari programari in trecut alti specialisti"))
							.toString();
				}

				String clientContacts = "";
				if(myDataCollection6.get(i).get("Date de contact clienti")==null) {
					clientContacts="tbd";
				}
				else
				{
					clientContacts=fmt.formatCellValue(myDataCollection6.get(i).get("Date de contact clienti"))
							.toString();
				}
				String databaseView = "";
				if(myDataCollection6.get(i).get("Vizualizare baza de date clienti")==null) {
					databaseView="tbd";
				}
				else
				{
					databaseView=fmt
							.formatCellValue(myDataCollection6.get(i).get("Vizualizare baza de date clienti")).toString();
				}
				String clientInfoEdit ="";
				if(myDataCollection6.get(i).get("Editare informatii clienti")==null) {
					clientInfoEdit="tbd";
				}
				else
				{
					clientInfoEdit=fmt.formatCellValue(myDataCollection6.get(i).get("Editare informatii clienti"))
							.toString();
				}
				String schedule = "";
				if(myDataCollection6.get(i).get("Setari orar")==null) {
					schedule="tbd";
				}
				else
				{
					schedule=fmt.formatCellValue(myDataCollection6.get(i).get("Setari orar")).toString();
				}

				String exceptions = "";
				if(myDataCollection6.get(i).get("Setari exceptii")==null) {
					exceptions="tbd";
				}
				else
				{
					exceptions=fmt.formatCellValue(myDataCollection6.get(i).get("Setari exceptii")).toString();
				}

				CSVUtils.writeLine(writerPermisiuni,
						Arrays.asList(appCreate, appEditFuture, appEditPast, calendarView, appCreateForOthers,
								appEditFutureOthers, appEditPastOthers, clientContacts, databaseView, clientInfoEdit,
								schedule, exceptions));

			}
			writerPermisiuni.flush();
			writerPermisiuni.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeToPropertiesFile(String pathToFile, String propertiesFileName) {

		try {
			String fileName = pathToFile+ propertiesFileName;
			Properties props = new Properties();
			PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));
			// FileWriter writer = new FileWriter(fileName);

			props.setProperty("businessName", ConfigUtils.removeAccents(numeAfacere));
			props.setProperty("businessCategory", ConfigUtils.removeAccents(categorieAfacere));
			props.setProperty("businessEmail", emailContAdministrator);
			props.setProperty("businessPhoneNo", telefonAfacere);
			props.setProperty("businessPassword", businessPassword);
			props.setProperty("businessAddress", ConfigUtils.removeAccents(adresaLocatiePrincipala));
			props.setProperty("businessMainLocation", ConfigUtils.removeAccents(numeLocatiePrincipala));
			props.setProperty("businessMainLocationCity", ConfigUtils.removeAccents(orasLocatiePrincipala));
			props.setProperty("businessMainLocationCounty", ConfigUtils.removeAccents(judetLocatiePrincipala));
			props.setProperty("businessMainDomain", ConfigUtils.removeAccents(domeniuPrincipal));
			props.setProperty("businessFirstService", ConfigUtils.removeAccents(serviciuPrincipal));
			props.setProperty("firstServiceMaxPersons", firstServiceMaxPersons);
			props.setProperty("businessFirstServicePrice", firstServicePrice);
			props.setProperty("businessFirstServiceDuration", firtstServiceDuration);
			props.setProperty("firstAddedSpecialistName", ConfigUtils.removeAccents(angajatPrincipal));
			props.setProperty("firstAddedSpecialistEmail", emailAngajatPrincipal);
			props.setProperty("firstAddedSpecialistPhone", telefonAngajatPrincipal);
			props.setProperty("firstAddedSpecialistPassword", parolaAngajatPrincipal);
			props.setProperty("orar_sediu_luni", orar_sediu_luni);
			props.setProperty("orar_sediu_marti", orar_sediu_marti);
			props.setProperty("orar_sediu_miercuri", orar_sediu_miercuri);
			props.setProperty("orar_sediu_joi", orar_sediu_joi);
			props.setProperty("orar_sediu_vineri", orar_sediu_vineri);
			props.setProperty("orar_sediu_sambata", orar_sediu_sambata);
			props.setProperty("orar_sediu_duminica", orar_sediu_duminica);
			props.setProperty("orar_angajat_luni", orar_angajat_luni);
			props.setProperty("orar_angajat_marti", orar_angajat_marti);
			props.setProperty("orar_angajat_miercuri", orar_angajat_miercuri);
			props.setProperty("orar_angajat_joi", orar_angajat_joi);
			props.setProperty("orar_angajat_vineri", orar_angajat_vineri);
			props.setProperty("orar_angajat_sambata", orar_angajat_sambata);
			props.setProperty("orar_angajat_duminica", orar_angajat_duminica);
			props.list(pw);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}



}

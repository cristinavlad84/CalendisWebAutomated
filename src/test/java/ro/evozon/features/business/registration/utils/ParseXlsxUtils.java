package ro.evozon.features.business.registration.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Issue;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.ManagedPages;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Steps;
import net.thucydides.junit.annotations.Qualifier;
import net.thucydides.junit.annotations.UseTestDataFrom;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import ro.evozon.tools.ConfigUtils;
import ro.evozon.tools.Constants;
import ro.evozon.tools.FieldGenerators;
import ro.evozon.tools.FieldGenerators.Mode;
import ro.evozon.tools.utils.CSVUtils;
import ro.evozon.tools.PhonePrefixGenerators;
import ro.evozon.tools.ReadFromExcelFile;
import ro.evozon.tools.Tools;
import ro.evozon.steps.serenity.business.BusinessWizardSteps;
import ro.evozon.steps.serenity.business.LoginBusinessAccountSteps;
import ro.evozon.steps.serenity.business.NewBusinessAccountSteps;
import ro.evozon.steps.serenity.business.StaffSteps;
import ro.evozon.tests.BaseTest;

public class ParseXlsxUtils {

	public static String emailContAdministrator;
	public static String telefonAfacere;
	public static String businessPassword = FieldGenerators.generateRandomString(8, Mode.ALPHANUMERIC);
	public static String categorieAfacere;
	public static String numeAfacere;
	public static String adresaAfacere;
	public static String domeniuAfacere, locatieDomeniu;
	public static String judetLocatie, telefonLocatie, numeLocatie;
	public static String serviciuPrincipal, angajatPrincipal;
	public static String domeniuServiciuAsociat, serviciu;
	public static String durataServiciu;
	public static String pretServiciu, firstServicePrice;
	public static String firstServiceMaxPersons, firtstServiceDuration;
	public static String persoaneServiciu;
	public static String numeAngajat;
	public static String emailAngajat,emailAngajatPrincipal;
	public static String telefonAngajat;
	public static String parolaAngajat = FieldGenerators.generateRandomString(8, Mode.ALPHANUMERIC);
	public static String domeniuPrincipal, adresaLocatiePrincipala, numeLocatiePrincipala, judetLocatiePrincipala,
			orasLocatiePrincipala;
	public static String luni, marti, miercuri, joi, vineri, sambata, duminica, orar_sediu_luni, orar_sediu_marti,
			orar_sediu_miercuri, orar_sediu_joi, orar_sediu_vineri, orar_sediu_sambata, orar_sediu_duminica,
			orar_angajat_luni, orar_angajat_marti, orar_angajat_miercuri, orar_angajat_joi, orar_angajat_vineri,
			orar_angajat_sambata, orar_angajat_duminica;

	public ParseXlsxUtils() {

		// this.businessPassword = FieldGenerators.generateRandomString(8,
		// Mode.ALPHANUMERIC);
		// this.parolaAngajat = FieldGenerators.generateRandomString(8,
		// Mode.ALPHANUMERIC);

	}

	public static void parseExcelFile() {
		try {
			DataFormatter fmt = new DataFormatter();
			String fileName = Constants.OUTPUT_PATH + ConfigUtils.getOutputFileNameForXlsxFile();
			FileInputStream excelFile = new FileInputStream(new File(fileName));
			Workbook workbook = new XSSFWorkbook(excelFile);

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
			String csvFile = Constants.OUTPUT_PATH_DATA_DRIVEN + ConfigUtils.getOutputFileNameForLocation();
			FileWriter writer = new FileWriter(csvFile);
			// write header on first line
			List<String> headingList = ReadFromExcelFile.getKeys(myDataCollection2.get(0));
			CSVUtils.writeLine(writer, headingList);
			for (int i = 1; i < myDataCollection2.size(); i++) {
				adresaAfacere = fmt.formatCellValue(myDataCollection2.get(i).get("Adresa locatie"));
				judetLocatie = fmt.formatCellValue(myDataCollection2.get(i).get("Judet locatie"));
				orasLocatiePrincipala = fmt.formatCellValue(myDataCollection2.get(i).get("Localitate locatie"));
				telefonLocatie = fmt.formatCellValue(myDataCollection2.get(i).get("Telefon locatie"));
				numeLocatie = fmt.formatCellValue(myDataCollection2.get(i).get("Nume locatie"));
				luni = fmt.formatCellValue(myDataCollection2.get(i).get("Luni"));
				marti = fmt.formatCellValue(myDataCollection2.get(i).get("Marti"));
				miercuri = fmt.formatCellValue(myDataCollection2.get(i).get("Miercuri"));
				joi = fmt.formatCellValue(myDataCollection2.get(i).get("Joi"));
				vineri = fmt.formatCellValue(myDataCollection2.get(i).get("Vineri"));
				sambata = fmt.formatCellValue(myDataCollection2.get(i).get("Sambata"));
				duminica = fmt.formatCellValue(myDataCollection2.get(i).get("Duminica"));
				CSVUtils.writeLine(writer, Arrays.asList(adresaAfacere, judetLocatie, telefonLocatie, numeLocatie, luni,
						marti, miercuri, joi, vineri, sambata, duminica));
			}

			writer.flush();
			writer.close();
			// read data from Domenii sheet
			Sheet datatypeSheet3 = workbook.getSheet("Domenii");
			System.out.println("sheet " + datatypeSheet3.getSheetName());
			List<LinkedHashMap<String, Cell>> myDataCollection3 = ReadFromExcelFile.rowsFrom(datatypeSheet3);
			myDataCollection2.forEach(k -> System.out.println(k));
			domeniuPrincipal = fmt.formatCellValue(myDataCollection3.get(0).get("Nume domeniu"));
			// read all lines from sheet and write them to csv file
			String csvFileDomenii = Constants.OUTPUT_PATH_DATA_DRIVEN + ConfigUtils.getOutputFileNameForDomain();
			FileWriter writerDomenii = new FileWriter(csvFileDomenii);
			// write header on first line
			List<String> headingListDomenii = ReadFromExcelFile.getKeys(myDataCollection3.get(0));
			CSVUtils.writeLine(writerDomenii, headingListDomenii);
			for (int i = 1; i < myDataCollection3.size(); i++) {
				domeniuAfacere = fmt.formatCellValue(myDataCollection3.get(i).get("Nume domeniu"));
				locatieDomeniu = fmt.formatCellValue(myDataCollection3.get(i).get("Locatia domeniului"));
				CSVUtils.writeLine(writerDomenii, Arrays.asList(domeniuAfacere, locatieDomeniu));
			}

			writerDomenii.flush();
			writerDomenii.close();
			// read data from Servicii sheet
			Sheet datatypeSheet4 = workbook.getSheet("Servicii");
			System.out.println("sheet " + datatypeSheet4.getSheetName());
			List<LinkedHashMap<String, Cell>> myDataCollection4 = ReadFromExcelFile.rowsFrom(datatypeSheet4);
			myDataCollection4.forEach(k -> System.out.println(k));
			serviciuPrincipal = myDataCollection4.get(0).get("Serviciu").toString();
			firstServiceMaxPersons = fmt.formatCellValue(myDataCollection4.get(0).get("Persoane serviciu"));
			firtstServiceDuration = fmt.formatCellValue(myDataCollection4.get(0).get("Durata serviciu"));
			firstServicePrice = fmt.formatCellValue(myDataCollection4.get(0).get("Pret serviciu"));
			// read all lines from sheet and write them to csv file
			String csvFileServicii = Constants.OUTPUT_PATH_DATA_DRIVEN + ConfigUtils.getOutputFileNameForService();
			FileWriter writerServicii = new FileWriter(csvFileServicii);
			// write header on first line
			List<String> headingListServicii = ReadFromExcelFile.getKeys(myDataCollection4.get(0));
			CSVUtils.writeLine(writerServicii, headingListServicii);
			for (int i = 1; i < myDataCollection4.size(); i++) {
				domeniuServiciuAsociat = myDataCollection4.get(i).get("Domeniul asociat").toString();
				serviciu = fmt.formatCellValue(myDataCollection4.get(i).get("Serviciu"));
				durataServiciu = fmt.formatCellValue((myDataCollection4.get(i).get("Durata serviciu")));
				pretServiciu = fmt.formatCellValue(myDataCollection4.get(i).get("Pret serviciu"));
				persoaneServiciu = myDataCollection4.get(i).get("Persoane serviciu").toString();
				CSVUtils.writeLine(writerServicii, Arrays.asList(domeniuServiciuAsociat, serviciu, durataServiciu,
						pretServiciu, persoaneServiciu));
			}

			writerServicii.flush();
			writerServicii.close();

			// read data from angajati sheet
			Sheet datatypeSheet5 = workbook.getSheet("Angajati");
			System.out.println("sheet " + datatypeSheet5.getSheetName());
			List<LinkedHashMap<String, Cell>> myDataCollection5 = ReadFromExcelFile.rowsFrom(datatypeSheet5);
			myDataCollection5.forEach(k -> System.out.println(k));
			angajatPrincipal = fmt.formatCellValue(myDataCollection5.get(0).get("Nume angajat"));
			emailAngajatPrincipal=fmt.formatCellValue(myDataCollection5.get(0).get("Email angajat"));
			// read all lines from sheet and write them to csv file
			String csvFileAngajati = Constants.OUTPUT_PATH_DATA_DRIVEN + ConfigUtils.getOutputFileNameForStaff();

			FileWriter writerAngajati = new FileWriter(csvFileAngajati);
			// write header on first line
			List<String> headingListAngajati = ReadFromExcelFile.getKeys(myDataCollection5.get(0));
			CSVUtils.writeLine(writerAngajati, headingListAngajati);
			for (int i = 1; i < myDataCollection5.size(); i++) {
				numeAngajat = fmt.formatCellValue(myDataCollection5.get(i).get("Nume angajat"));
				emailAngajat = fmt.formatCellValue(myDataCollection5.get(i).get("Email angajat"));
				telefonAngajat = fmt.formatCellValue(myDataCollection5.get(i).get("Telefon angajat"));
				luni = fmt.formatCellValue(myDataCollection5.get(i).get("Luni"));
				marti = fmt.formatCellValue(myDataCollection5.get(i).get("Marti"));
				miercuri = fmt.formatCellValue(myDataCollection5.get(i).get("Miercuri"));
				joi = fmt.formatCellValue(myDataCollection5.get(i).get("Joi"));
				vineri = fmt.formatCellValue(myDataCollection5.get(i).get("Vineri"));
				sambata = fmt.formatCellValue(myDataCollection5.get(i).get("Sambata"));
				duminica = fmt.formatCellValue(myDataCollection5.get(i).get("Duminica"));
				CSVUtils.writeLine(writerAngajati, Arrays.asList(numeAngajat, emailAngajat, telefonAngajat, luni, marti,
						miercuri, joi, vineri, sambata, duminica));
			}

			writerAngajati.flush();
			writerAngajati.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeToPropertiesFile() {

		try {
			String fileName = Constants.OUTPUT_PATH + ConfigUtils.getOutputFileNameForNewBusinessFromXlsx();
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
			props.setProperty("businessFirstServiceDuration", durataServiciu);
			props.setProperty("firstAddedSpecialistName", ConfigUtils.removeAccents(angajatPrincipal));
			props.setProperty("firstAddedSpecialistEmail", emailAngajatPrincipal);
			props.setProperty("firstAddedSpecialistPhone", telefonAngajat);
			props.setProperty("firstAddedSpecialistPassword", parolaAngajat);
			props.setProperty("orar_sediu_luni", orar_sediu_luni);
			props.setProperty("orar_sediu_marti", orar_sediu_marti);
			props.setProperty("orar_sediu_miercuri", orar_sediu_miercuri);
			props.setProperty("orar_sediu_joi", orar_sediu_joi);
			props.setProperty("orar_sediu_vineri", orar_sediu_vineri);
			props.setProperty("orar_sediu_sambata", orar_sediu_sambata);
			props.setProperty("orar_sediu_duminica", orar_sediu_duminica);
			props.list(pw);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void parseTestDataFromExcelFile() {
		parseExcelFile();
		writeToPropertiesFile();

	}

}

package ro.evozon.features.business.datadriven.api;

import io.restassured.http.Cookies;
import io.restassured.response.Response;
import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.thucydides.core.annotations.Issue;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.junit.annotations.Qualifier;
import net.thucydides.junit.annotations.UseTestDataFrom;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import ro.evozon.tests.BaseApiTest;
import ro.evozon.tools.ConfigUtils;
import ro.evozon.tools.Constants;
import ro.evozon.tools.models.CityModel;
import ro.evozon.tools.models.Domain;
import ro.evozon.tools.models.Location;
import ro.evozon.tools.models.RegionModel;
import ro.evozon.tools.utils.CSVUtils;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ro.evozon.features.business.datadriven.api.AddNewLocationDataDrivenAPIStory.DataPersistence.processInputFile;

import static ro.evozon.tools.api.PayloadDataGenerator.createJsonObjectForLocationRequestPayload;

@Narrative(text = { "In order toadd new location to business account", "As business user ",
		"I want to be able to add new location and then see location saved" })
@RunWith(SerenityParameterizedRunner.class)
@UseTestDataFrom(value = "$DATADIR/locatii.csv")
public class AddNewLocationDataDrivenAPIStory extends BaseApiTest {

	private String adresaLocatie;
	private String judetLocatie;
	private String localitateLocatie;
	private String telefonLocatie;
	private String numeLocatie;
	private String luni;

	private String marti;
	private String miercuri;
	private String joi;
	private String vineri;
	private String sambata;
	private String duminica;
	private String businessLocationId;
	public static List<Domain> myDomainsList;

	private static int noOfRuns;
	private static String csvFile ;
	private static FileWriter writer;
	//private static File fileInput= new File(Constants.OUTPUT_PATH_DATA_DRIVEN_API+ ConfigUtils.getOutputFileNameForDomain());
	// private String
	public String  newLocationName, newLocationStreet, newLocationPhone;

	/**
	 * intialization block
	 * this run once for each data driven iteartion
	 * static for not being attached to any object
	 */
	static{

		myDomainsList=processInputFile(Constants.OUTPUT_PATH_DATA_DRIVEN_API+ ConfigUtils.getOutputFileNameForDomain());

		System.out.println("size is "+myDomainsList.size());
		noOfRuns =myDomainsList.size();
	}
	public AddNewLocationDataDrivenAPIStory() {
		super();

	}

	public void setLocationStreet(String locationStreet) {
		this.adresaLocatie = locationStreet;
	}

	public void setLocationRegion(String locationRegion) {
		this.judetLocatie = locationRegion;
	}

	public void setLocationCity(String locationCity) {
		this.localitateLocatie = locationCity;
	}

	public void setLuni(String luni) {
		this.luni = luni;
	}

	public void setMarti(String marti) {
		this.marti = marti;
	}

	public void setMiercuri(String miercuri) {
		this.miercuri = miercuri;
	}

	public void setJoi(String joi) {
		this.joi = joi;
	}

	public void setVineri(String vineri) {
		this.vineri = vineri;
	}

	public void setSambata(String sambata) {
		this.sambata = sambata;
	}

	public void setDuminica(String duminica) {
		this.duminica = duminica;
	}

	@Qualifier
	public String qualifier() {
		return adresaLocatie + "=>" + judetLocatie + "=>" + localitateLocatie + "=>" + telefonLocatie + "=>"
				+ numeLocatie + "=>" + luni + "=>" + marti + "=>" + miercuri + "=>" + joi + "=>" + vineri + "=>"
				+ sambata + "=>" + duminica;
	}




	@After
	public  void appendBusinessLocationId(){
		noOfRuns=noOfRuns-1;
		String line="";
		List<String > lineValuesList;

		/**
		 * at last run write to csv file
		 */
		System.out.println("now no of runs is "+noOfRuns);
		if(noOfRuns == 0){
		//List<Location> locationL = myList.stream().filter(l->l.getNumeLocatie())

			List<String> headingList = new ArrayList<String>(Arrays.asList("Nume domeniu", "Locatia domeniului","locatie_id"));
			try {
				String csvFile = Constants.OUTPUT_PATH_DATA_DRIVEN_API+ ConfigUtils.getOutputFileNameForDomain();
				writer = new FileWriter(csvFile);
				CSVUtils.writeLine(writer, headingList);
			} catch (IOException e) {
				e.printStackTrace();
			}
			myDomainsList.stream().forEach(f ->System.out.println("at last run "+f.getLocatieId()));
			for(int i=0; i<myDomainsList.size(); i++) {
				 line = myDomainsList.get(i).toString();
				String [] lineValuesArray = line.split(",");
				lineValuesList = new ArrayList<String>(Arrays.asList(lineValuesArray));
				try {
					CSVUtils.writeLine(writer, lineValuesList);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				writer.flush();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}


		}

	}

	@Issue("#CLD-038")
	@Test
	public void add_new_location_then_verify_saved() {


		//get list of all region id's

		Cookies cck = businessLogin(businessEmail, businessPassword);
		restSteps.setupRequestSpecBuilder(cck);
		/**
		 * get model for region object which holds region's id and name
		 */

		Optional<RegionModel>  regionDataModel = getRegionDetailsByName(judetLocatie);
		System.out.println("from excel city_id " + regionDataModel.get().getId());
		/**
		 * add region and city
		 */

		Response addCountyResponse = restSteps.addCounty(regionDataModel.get().getId());
		//System.out.print("add county response: " + addCountyResponse.prettyPrint());
		/**
		 * get cities list as object with names and ids from add county response
		 */
		List<String> citiesNamesList = addCountyResponse.body().jsonPath().get("cities.name");
		List<String> citiesIdsList = addCountyResponse.body().jsonPath().get("cities.id");
		List<CityModel> mapData = new ArrayList<CityModel>();
		mapData = CityModel.createCityModelFrom(citiesNamesList, citiesIdsList);
		//  mapData.stream().forEach(k -> System.out.println("city name" + k.getName() + "city id " + k.getId()));
		Optional<CityModel> businessCityModel = mapData.stream().filter(p -> ConfigUtils.removeAccents(p.getName()).equalsIgnoreCase(localitateLocatie)).findFirst();
		//end

		System.out.println("from excel city_id " + businessCityModel.get().getId());
		/**
		 * add location
		 */
		String locationContent = createJsonObjectForLocationRequestPayload(luni, marti, miercuri, joi, vineri, sambata, duminica, adresaLocatie, businessCityModel.get().getId(), businessCityModel.get().getName(), regionDataModel.get().getId(), telefonLocatie, numeLocatie);
		Response addLocationResponse = restSteps.addLocationParameterized(locationContent.toString());
		System.out.print("add location response: " + addLocationResponse.prettyPrint());
		// // Response addLocationResponse = restSteps
		businessLocationId = addLocationResponse.body().jsonPath().get("id");
		System.out.println("business location id " + businessLocationId);


		myDomainsList.forEach(f ->{if(f.getLocatiaDomeniului().contentEquals(numeLocatie) ){f.setLocatieId(businessLocationId);System.out.println("set business id for "+numeLocatie+" being "+businessLocationId);} if(f.getLocatiaDomeniului().contentEquals(businessMainLocation)){f.setLocatieId(businessMainLocationId);}});
		myDomainsList.stream().forEach(f ->{if(f.getLocatiaDomeniului().contentEquals(numeLocatie)){System.out.println("modified "+f.getLocatieId());}});

		restSteps.assertAll();

	}
	static  class DataPersistence {

		public static int getNoOfRuns() {
			return noOfRuns;
		}
		public static List<Domain> processInputFile(String inputFilePath){
			List<Domain> inputList = new ArrayList<Domain>();
			try{

				File inputF = new File(inputFilePath);
				InputStream inputFS = new FileInputStream(inputF);
				BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));
				inputList = br.lines().skip(1).map(mapToItem).collect(Collectors.toList());
				br.close();
			}catch (IOException e) {
				e.printStackTrace();
			}
			return inputList;
		}
		private static Function<String, Domain> mapToItem = (String line) -> {
			String[] p = line.split(",");// a CSV has comma separated lines
			Domain item = new Domain();

			if (p[0] != null && p[0].trim().length() > 0) {
				item.setNumeDomeniu(p[0]);//<-- this is the first column in the csv file
			}
			if (p[1] != null && p[1].trim().length() > 0) {
				item.setLocatiaDomeniului(p[1]);//<-- this is the first column in the csv file
			}

			item.setLocatieId("");
			//more initialization goes here
			return item;
		};

		}

	}



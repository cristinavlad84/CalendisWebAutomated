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
import ro.evozon.tools.models.Domain;
import ro.evozon.tools.models.Service;
import ro.evozon.tools.utils.CSVUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ro.evozon.features.business.datadriven.api.DataPersistence.DomainDataPersistence.processInputFile;
import static ro.evozon.features.business.datadriven.api.DataPersistence.ServiceDataPersistence.processServiceInputFile;

@Narrative(text = { "In order toadd new location to business account", "As business user ",
		"I want to be able to add new location and then see location saved" })
@RunWith(SerenityParameterizedRunner.class)
@UseTestDataFrom(value = "src/test/resources/output/datadriven/api/domenii.csv")
public class AddNewDomainDataDrivenAPIStory extends BaseApiTest {

	public void setNumeDomeniu(String numeDomeniu) {
		this.numeDomeniu = numeDomeniu;
	}

	public void setLocatiaDomeniului(String locatiaDomeniului) {
		this.locatiaDomeniului = locatiaDomeniului;
	}
	public void setLocatieId(String locatieId) {
		this.locatieId = locatieId;
	}



	private String numeDomeniu;
	private String locatiaDomeniului;



	private String locatieId;
	private String businessLocationId;
	public static List<Service> myServicesList;
	public static List<Domain> allDomainsList;
	private static int noOfRuns;
	private static String csvFile ;
	private static FileWriter writer;


	/**
	 * intialization block
	 * this run once for each data driven iteration
	 * static for not being attached to any object
	 */
	static{

		myServicesList =processServiceInputFile(Constants.OUTPUT_PATH_DATA_DRIVEN_API+ ConfigUtils.getOutputFileNameForService());
		allDomainsList=processInputFile(Constants.OUTPUT_PATH_DATA_DRIVEN_API+ ConfigUtils.getOutputFileNameForDomain());
		System.out.println("size is "+ myServicesList.size());
		noOfRuns = CSVUtils.getFileNoOfRows(Constants.OUTPUT_PATH_DATA_DRIVEN_API+ ConfigUtils.getOutputFileNameForDomain())-1; //-1 for headers row
	}
	public AddNewDomainDataDrivenAPIStory() {
		super();

	}



	@Qualifier
	public String qualifier() {
		return locatieId+"=>"+numeDomeniu + "=>" + locatiaDomeniului;
	}




	@After
	public  void appendDomainId(){
		noOfRuns=noOfRuns-1;
		String line="";
		List<String > lineValuesList;

		/**
		 * at last run write to csv file
		 */
		System.out.println("now no of runs is "+noOfRuns);
		if(noOfRuns == 0){
		//List<Location> locationL = myList.stream().filter(l->l.getNumeLocatie())

			List<String> headingList = new ArrayList<String>(Arrays.asList("Domeniul asociat", "Serviciu","Durata serviciu", "Pret serviciu", "Persoane serviciu","Domeniu id","Locatie id"));
			CsvObject<Service> myServiceObj = new CsvObject<Service>(myServicesList);
			String csvFile = Constants.OUTPUT_PATH_DATA_DRIVEN_API+ ConfigUtils.getOutputFileNameForService();
			try {
				writer = new FileWriter(csvFile);
			} catch (IOException e) {
				e.printStackTrace();
			}

			myServiceObj.writeToCsvFile(writer, headingList,myServicesList );
		}

	}

	@Issue("#CLD-038")
	@Test
	public void add_new_location_then_verify_saved() {


		System.out.println("locatie_id "+locatieId+numeDomeniu + "=>" + locatiaDomeniului);

		Cookies cck = businessLogin(businessEmail, businessPassword);
		restSteps.setupRequestSpecBuilder(cck);
		/**
		 * add domain for location
		 */
		Response adDdomainResponse = restSteps.addDomain(locatieId, numeDomeniu);
		System.out.print("add domain response: " + adDdomainResponse.prettyPrint());
		businessDomainId = adDdomainResponse.body().jsonPath().getString("id");
		System.out.println("domain id " + businessDomainId);

		/**
		 * iterate over services list and set domain id and location id for all objects in list
		 */
		myServicesList.forEach(f ->
			{
				if (f.getAsociatedDomain().contentEquals(numeDomeniu))
					{
						f.setDomainId(businessDomainId);
						allDomainsList.forEach(k ->
							{
								if (k.getNumeDomeniu().contentEquals(numeDomeniu))
									{
										System.out.println("set location id for "+numeDomeniu+" with "+k.getLocatieId() );
										f.setLocationId((k.getLocatieId()));
									}
							});

					}
				if(f.getAsociatedDomain().contentEquals(businessMainDomain))
					{
						f.setDomainId(businessMainDomainId);
						allDomainsList.forEach(k ->
							{
								if (k.getNumeDomeniu().contentEquals(numeDomeniu))
									{
										f.setLocationId(businessMainLocationId);
									}
							});
					}

			}
		);



		restSteps.assertAll();

	}


	}



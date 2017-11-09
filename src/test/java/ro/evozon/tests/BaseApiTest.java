package ro.evozon.tests;

import io.restassured.http.Cookies;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;
import org.junit.Before;
import ro.evozon.steps.serenity.rest.RestSteps;
import ro.evozon.tools.ConfigUtils;
import ro.evozon.tools.Constants;
import ro.evozon.tools.models.RegionModel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

import static ro.evozon.tools.api.ExtractRegionsNamesAndIds.getRegionsNamesAndIdsFromResponse;

public class BaseApiTest {

	public String businessEmail;
	public String businessPassword;
	public String businessName;
	public static String businessMainDomain, businessMainLocation,businessDomainId;
	public static String businessMainLocationId, businessMainDomainId, businessFirstAddedSpecialistId, businessFirstServiceId, businessFirstService,businessFirstAddedSpecialist;

	@Steps
	public RestSteps restSteps;


	@Before
	public void readFromFile() {
		String fileName = Constants.OUTPUT_PATH + ConfigUtils.getOutputFileNameForNewBusinessApiFromXlsx();
		Properties props = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream(fileName);
			props.load(input);
			businessName = props.getProperty("businessName", businessName);
			businessEmail = props.getProperty("businessEmail", businessEmail);
			businessPassword = "Calendis";
					//props.getProperty("businessPassword", businessPassword);
			businessMainLocationId= props.getProperty("businessLocationId", businessMainLocationId);
			businessMainDomainId=props.getProperty("businessDomainId",businessMainDomainId);
			businessFirstAddedSpecialist=props.getProperty("firstAddedSpecialistName",businessFirstAddedSpecialist);
			businessFirstAddedSpecialistId=props.getProperty("staffId",businessFirstAddedSpecialistId);
			businessMainDomain=props.getProperty("businessMainDomain",businessMainDomain);
			businessMainLocation=props.getProperty("businessMainLocation",businessMainLocation);
			businessFirstServiceId=props.getProperty("serviceId",businessFirstServiceId);
			businessFirstService=props.getProperty("businessFirstService",businessFirstService);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}git 

	public Cookies businessLogin(String email, String password){

		Response loginResponse = restSteps.login(email, password);
		return loginResponse.getDetailedCookies();

	}

	public Optional<RegionModel> getRegionDetailsByName(String countyName){

		return getRegionsNamesAndIdsFromResponse(countyName);

	}

}

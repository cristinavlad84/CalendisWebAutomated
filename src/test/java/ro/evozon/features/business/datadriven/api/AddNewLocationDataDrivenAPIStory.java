package ro.evozon.features.business.datadriven.api;

import com.sun.corba.se.impl.orb.ParserTable;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.thucydides.core.annotations.Issue;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.junit.annotations.Qualifier;
import net.thucydides.junit.annotations.UseTestDataFrom;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import ro.evozon.tests.BaseApiTest;
import ro.evozon.tools.ConfigUtils;
import ro.evozon.tools.Constants;
import ro.evozon.tools.models.CityModel;
import ro.evozon.tools.models.RegionModel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

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
	// private String
	public String  newLocationName, newLocationStreet, newLocationPhone;

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
		String businessLocationId = addLocationResponse.body().jsonPath().get("id");
		System.out.println("business location id " + businessLocationId);
		restSteps.assertAll();

	}

}
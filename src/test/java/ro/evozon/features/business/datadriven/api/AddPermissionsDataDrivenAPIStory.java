package ro.evozon.features.business.datadriven.api;

import io.restassured.http.Cookies;
import io.restassured.response.Response;
import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.thucydides.core.annotations.Issue;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Steps;
import net.thucydides.junit.annotations.UseTestDataFrom;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import ro.evozon.features.business.datadriven.api.deserializer.PermissionResponseData;
import ro.evozon.features.business.datadriven.api.serializer.Permissions;
import ro.evozon.steps.serenity.business.AddPermissionsStep;
import ro.evozon.steps.serenity.business.BusinessWizardSteps;
import ro.evozon.steps.serenity.business.LoginBusinessAccountSteps;
import ro.evozon.steps.serenity.business.StaffSteps;
import ro.evozon.tests.BaseApiTest;
import ro.evozon.tests.BaseTest;
import ro.evozon.tools.ConfigUtils;
import ro.evozon.tools.Constants;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

@Narrative(text = { "In order to set permission for specialist", "As business user ",
		"I want to be able to add permission for specialist accounts" })
@RunWith(SerenityParameterizedRunner.class)
@UseTestDataFrom(value = "src/test/resources/output/datadriven/api/permisiuni.csv")
public class AddPermissionsDataDrivenAPIStory extends BaseApiTest {
	private String creareProgramari;
	private String modificariProgramariInViitor;
	private String modificariProgramariInTrecut;
	private String vizualizareCalendar;
	private String creareProgramariAltiSpecialisti;
	private String modificariProgramariInViitorAltiSpecialisti;
	private String modificariProgramariInTrecutAltiSpecialisti;
	private String dateDeContactClienti;
	private String vizualizareBazaDeDateClienti;
	private String editareInformatiiClienti;
	private String setariOrar;
	private String setariExceptii;
	private String businessName, businessEmail, businessPassword, businessMainLocation;
	public String userName, userId;
	public Map<String, String> userIdsMap;
	public void setCreareProgramari(String creareProgramari) {
		this.creareProgramari = creareProgramari;
	}

	public void setModificariProgramariInViitor(String modificariProgramariInViitor) {
		this.modificariProgramariInViitor = modificariProgramariInViitor;
	}

	public void setModificariProgramariInTrecut(String modificariProgramariInTrecut) {
		this.modificariProgramariInTrecut = modificariProgramariInTrecut;
	}

	public void setVizualizareCalendar(String vizualizareCalendar) {
		this.vizualizareCalendar = vizualizareCalendar;
	}

	public void setCreareProgramariAltiSpecialisti(String creareProgramariAltiSpecialisti) {
		this.creareProgramariAltiSpecialisti = creareProgramariAltiSpecialisti;
	}

	public void setModificariProgramariInViitorAltiSpecialisti(String modificariProgramariInViitorAltiSpecialisti) {
		this.modificariProgramariInViitorAltiSpecialisti = modificariProgramariInViitorAltiSpecialisti;
	}

	public void setModificariProgramariInTrecutAltiSpecialisti(String modificariProgramariInTrecutAltiSpecialisti) {
		this.modificariProgramariInTrecutAltiSpecialisti = modificariProgramariInTrecutAltiSpecialisti;
	}

	public void setSetariOrar(String setariOrar) {
		this.setariOrar = setariOrar;
	}

	public void setSetariExceptii(String setariExceptii) {
		this.setariExceptii = setariExceptii;
	}

	public AddPermissionsDataDrivenAPIStory() {
		super();

	}

	@Before
	public void readFromFile() {
		String fileName = Constants.OUTPUT_PATH + ConfigUtils.getOutputFileNameForNewBusinessFromXlsx();
		Properties props = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream(fileName);
			props.load(input);
			businessName = props.getProperty("businessName", businessName);
			businessEmail = props.getProperty("businessEmail", businessEmail);
			businessPassword = props.getProperty("businessPassword", businessPassword);
			businessMainLocation = props.getProperty("businessMainLocation", businessMainLocation);

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
		/**
		 * read user name and id from userIds.properties
		 */
		fileName = Constants.OUTPUT_PATH + ConfigUtils.getOutputFileNameForUsersIds();
		Properties properties = new Properties();
		userIdsMap = properties.entrySet().stream().collect(
				Collectors.toMap(
						e -> e.getKey().toString(),
						e -> e.getValue().toString()
				)
		);

	}

	@Ignore
	@Issue("#CLD; #CLD")
	@Test
	public void add_permission() throws Exception {
		Cookies cck = businessLogin(businessEmail, businessPassword);
		restSteps.setupRequestSpecBuilder(cck);
		Response allPermissionResponse = restSteps.getAllPermissionIds();
		PermissionResponseData permissionData=allPermissionResponse.as(PermissionResponseData.class);
		Permissions requestPayloadForAddPermission = new Permissions();
		requestPayloadForAddPermission.setUserID(Integer.parseInt(userIdsMap.get(creareProgramari)));
		//permissionData.getName()
		requestPayloadForAddPermission.setPermissionID(PermissionIDConstants.CREATE_APPOINTMENT);
		restSteps.addUserPermission(requestPayloadForAddPermission);

	}

}
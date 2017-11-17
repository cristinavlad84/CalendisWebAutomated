package ro.evozon.features.business.datadriven.api;


import io.restassured.http.Cookies;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.thucydides.core.annotations.Issue;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.junit.annotations.UseTestDataFrom;
import org.json.JSONObject;
import org.junit.Before;

import org.junit.Test;
import org.junit.runner.RunWith;
import ro.evozon.AbstractApiSteps;
import ro.evozon.features.business.datadriven.api.serializer.Permissions;
import ro.evozon.tests.BaseApiTest;
import ro.evozon.tools.ConfigUtils;
import ro.evozon.tools.Constants;

import ro.evozon.tools.enums.PermissionEnum;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hamcrest.Matcher;
import io.restassured.path.json.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@Narrative(text = {"In order to set permission for specialist", "As business user ",
        "I want to be able to add permission for specialist accounts"})
@RunWith(SerenityParameterizedRunner.class)
@UseTestDataFrom(value = "src/test/resources/output/datadriven/api/permisiuni.csv")
public class AddPermissionsDataDrivenAPIStory extends BaseApiTest {
    public Map<String, String> userIdsMap;
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

    public AddPermissionsDataDrivenAPIStory() {
        super();

    }

    public static String doFilter(Map<String, String> map, String name) {
        String str= map.entrySet().stream().filter(x -> x.getKey().equalsIgnoreCase(name)).map(j -> j.getValue()).collect(Collectors.joining());
        return str;

    }

    public static String getPermissionIdByName(String jsonContentString, String permissionName) {
        List<Map<String, ?>> allStuffL = JsonPath.with(jsonContentString).param("name", permissionName).get("permissions.findAll{permission->permission.name==name}");
        final String permissionIdToReturn = (String) allStuffL.get(0).get("id");
        System.out.println("!!!!!!!!!!!!!!!permisisonId from response " + permissionIdToReturn);
        return permissionIdToReturn;
    }

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

    public void readFromUserIdFile() {

        /**
         * read user name and id from userIds.properties
         */
        System.out.println("keys!!!!!!!!!!!!!!!!!!");
        String fileName = Constants.OUTPUT_PATH + ConfigUtils.getOutputFileNameForUsersIds();
        Properties properties = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream(fileName);
            properties.load(input);
            userIdsMap = properties.entrySet().stream().collect(
                    Collectors.toMap(
                            e -> e.getKey().toString(),
                            e -> e.getValue().toString()
                    )

            );
            userIdsMap.entrySet().forEach(x -> System.out.println("keys " + x.getKey() + x.getValue()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //@Ignore
    @Issue("#CLD; #CLD")
    @Test
    public void add_permission() throws Exception {

        readFromUserIdFile();
        System.out.println("login with " + getBusinessEmail() + getBusinessPassword());
        Cookies cck = businessLogin(getBusinessEmail(), getBusinessPassword());
        AbstractApiSteps.setupRequestSpecBuilder(cck);

        Response allPermissionResponse = restSteps.getAllPermissionIds();
        /**
         * get all enum values in a list to iterate over for adding permission
         */

        List<String> enumValues = PermissionEnum.getAllValues();
        /**
         * get all values from rows in a list ->from csv input file =>to iterate over it in test
         */
        List<String> rowCandidates = new ArrayList<>(Arrays.asList(creareProgramari,modificariProgramariInViitor, modificariProgramariInTrecut,vizualizareCalendar,
                creareProgramariAltiSpecialisti,modificariProgramariInViitorAltiSpecialisti,modificariProgramariInTrecutAltiSpecialisti,
                dateDeContactClienti,vizualizareBazaDeDateClienti,editareInformatiiClienti,setariOrar,setariExceptii));
        for(int i=0; i<enumValues.size(); i++) {
            String permissionName = enumValues.get(i);
            System.out.println("values  is " + permissionName);
            final String idActual = getPermissionIdByName(allPermissionResponse.asString(), permissionName);
            if(rowCandidates.get(i).contentEquals("tbd")){
                //don't add permission
            }
            else {
                String userIdFromMap = doFilter(userIdsMap, rowCandidates.get(i));
                System.out.println("!!!!!!!!!!!!!!!user id from user ids properties file " + userIdFromMap);
                restSteps.addUserPermission(idActual, userIdFromMap);
            }
        }
        restSteps.assertAll();

    }

}
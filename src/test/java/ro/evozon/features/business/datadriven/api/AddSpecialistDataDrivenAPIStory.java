package ro.evozon.features.business.datadriven.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.thucydides.core.annotations.Issue;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Steps;
import net.thucydides.junit.annotations.Qualifier;
import net.thucydides.junit.annotations.UseTestDataFrom;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import ro.evozon.steps.serenity.business.AddStaffToBusinessStep;
import ro.evozon.steps.serenity.business.BusinessWizardSteps;
import ro.evozon.steps.serenity.business.LoginBusinessAccountSteps;
import ro.evozon.steps.serenity.business.StaffSteps;
import ro.evozon.steps.serenity.rest.RestSteps;
import ro.evozon.tests.BaseTest;
import ro.evozon.tools.ConfigUtils;
import ro.evozon.tools.Constants;
import ro.evozon.tools.StaffType;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Narrative(text = {"In order to login to business account as specialist", "As business user ",
        "I want to be able to add new specialist and then login into specialist account"})
@RunWith(SerenityParameterizedRunner.class)
@UseTestDataFrom(value = "$DATADIR/angajati.csv")
public class AddSpecialistDataDrivenAPIStory extends BaseTest {
    public static RequestSpecBuilder builder;
    public static RequestSpecification requestSpec;
    private String numeAngajat;
    private String emailAngajat;
    private String telefonAngajat;
    private String luni, marti, miercuri, joi, vineri, sambata, duminica;
    private String serviciuAsignat;
    private String businessName, businessEmail, businessPassword, businessMainLocation;

    public AddSpecialistDataDrivenAPIStory() {
        super();

    }
    public void setNumeAngajat(String numeAngajat) {
        this.numeAngajat = numeAngajat;
    }

    public void setEmailAngajat(String emailAngajat) {
        this.emailAngajat = emailAngajat;
    }

    public void setTelefonAngajat(String telefonAngajat) {
        this.telefonAngajat = telefonAngajat;
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
    public void setServiciuAsignat(String serviciuAsignat) {
        this.serviciuAsignat = serviciuAsignat;
    }

    @Qualifier
    public String qualifier() {
        return numeAngajat + "=>" + emailAngajat + "=>" + telefonAngajat + "=>" + luni + "=>" + marti + "=>" + miercuri + "=>" + joi + "=>" + vineri + "=>" + sambata +"=>"+ duminica+"=>"+serviciuAsignat;
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

    }
    public void setupRequestSpecBuilder(Cookies cck) {
        builder = new RequestSpecBuilder();
        builder.addCookies(cck);
        requestSpec = builder.build();
    }
    @Steps
    public RestSteps restSteps;
    @Issue("#CLD-030; #CLD-043")
    @Test
    public void add_specialist_then_set_psw_and_login_into_specialist_account() throws Exception {

        Response loginResponse = restSteps.login(businessEmail, businessPassword);
        System.out.print("login response: " + loginResponse.prettyPrint());
        Cookies cck = loginResponse.getDetailedCookies();
        setupRequestSpecBuilder(cck);
    }

}
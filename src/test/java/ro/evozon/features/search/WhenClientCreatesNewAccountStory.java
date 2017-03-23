package ro.evozon.features.search;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Issue;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Pending;
import net.thucydides.core.annotations.Steps;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import ro.evozon.tools.ConfigUtils;
import ro.evozon.steps.serenity.ClientNewAccountSteps;
import ro.evozon.steps.serenity.EndUserSteps;
import ro.evozon.tests.BaseTest;
@Narrative(text={"In order to choose the best vegetables",                      
        "As end user ",
        "I want to be able to search for specific terms"})
@RunWith(SerenityRunner.class)
public class WhenClientCreatesNewAccountStory extends BaseTest{

	private String clientLastName, clientFirstName, clientEmail, clientPhonNo;
	
	public WhenClientCreatesNewAccountStory(){
		this.clientLastName = df.getLastName();
		this.clientFirstName = df.getFirstName();
		this.clientEmail = df.getRandomWord().concat("@yopmail.com");
	
	}
    @Steps
    public ClientNewAccountSteps endUser;

    @Issue("#WIKI-1")
    @Test
    public void creating_new_account_as_client_should_display_the_success_email_message() {
    	//endUser.is_the_home_page();
    	endUser.navigateTo(ConfigUtils.getBaseUrl());
    	endUser.clicks_on_intra_in_cont_link();
    	endUser.click_on_creeaza_un_cont_nou();
    	endUser.fill_in_client_details(clientLastName,clientFirstName,clientEmail, "0251456213" );
    	
    	endUser.click_on_create_account_button();
    	endUser.should_see_success_message("Felicitări! Contul tău a fost creat!");

    }

    
    
} 
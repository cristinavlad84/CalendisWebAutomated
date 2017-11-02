package ro.evozon.features.business.datadriven.api;

import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.thucydides.core.annotations.Issue;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Steps;
import net.thucydides.junit.annotations.Qualifier;
import net.thucydides.junit.annotations.UseTestDataFrom;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import ro.evozon.steps.serenity.business.AddDomainToBusinessStep;
import ro.evozon.steps.serenity.business.AddItemToBusinessSteps;
import ro.evozon.steps.serenity.business.BusinessWizardSteps;
import ro.evozon.steps.serenity.business.LoginBusinessAccountSteps;
import ro.evozon.tests.BaseApiTest;
import ro.evozon.tests.BaseTest;
import ro.evozon.tools.ConfigUtils;
import ro.evozon.tools.Constants;
import ro.evozon.tools.FieldGenerators;
import ro.evozon.tools.FieldGenerators.Mode;

import java.io.*;
import java.util.Map;
import java.util.Properties;

@Narrative(text = { "In order to have new domain in business account", "As business user ",
		"I want to be able to add new domain and then see domain saved" })
@RunWith(SerenityParameterizedRunner.class)
@UseTestDataFrom(value = "$DATADIR/domenii.csv")
public class AddNewDomainDataDrivenAPIStory extends BaseApiTest {
	private String numeDomeniu;
	private String locatiaDomeniului;

	public void setLocatiaDomeniului(String locatiaDomeniului) {
		this.locatiaDomeniului = locatiaDomeniului;
	}



	public void setDomainName(String domainName) {
		this.numeDomeniu = domainName;
	}

	public AddNewDomainDataDrivenAPIStory() {
		super();

		this.numeDomeniu = FieldGenerators.generateRandomString(8, Mode.ALPHA);
	}

	@Qualifier
	public String qualifier() {
		return numeDomeniu + "=>" + locatiaDomeniului;
	}

	@Steps
	public LoginBusinessAccountSteps loginStep;

	@Steps
	public AddDomainToBusinessStep addDomainSteps;
	@Steps
	public AddItemToBusinessSteps addItemToBusinessStep;
	@Steps
	BusinessWizardSteps businessWizardSteps;

	@Issue("#CLD-039")
	@Test
	public void add_new_domain_then_verify_saved() throws Exception {

	}

}
package ro.evozon.steps.serenity.business;

import net.thucydides.core.annotations.Step;
import ro.evozon.AbstractSteps;
import ro.evozon.pages.business.DomainPage;
import ro.evozon.pages.business.ServicesPage;
import ro.evozon.pages.business.SettingsPage;

public class AddDomainToBusinessStep extends AbstractSteps {
	DomainPage domainPage;
	SettingsPage settingsPage;

	@Step
	public void click_on_add_domain() {
		domainPage.click_on_add_new_domain();
	}

	@Step
	public String select_random_location_in_domain_form() {
		return domainPage.select_random_location_domain_form();
	}
	
	@Step
	public void select_location_in_domain_form(String location) {
		domainPage.select_location_domain_form(location);
	}

	@Step
	public void fill_in_domain_name(String name) {
		domainPage.fill_in_domain_name(name);
	}

	@Step
	public void click_on_save_domain_button() {
		domainPage.click_on_save_domain();
	}

	@Step
	public void verify_domain_name_appears_in_domain_section(String domainName) {

		softly.assertThat(domainPage.is_domain_name_displayed(domainName)).as("domain Name").isTrue();
	}

	@Step
	public void verify_domain_not_displayed_in_domain_section(String domainName) {

		softly.assertThat(domainPage.is_domain_name_displayed(domainName)).as("domain Name")
				.as("should not be displayed").isFalse();
	}

	@Step
	public void click_on_delete_domain(String domainName) {
		domainPage.click_on_delete_domain_link(domainName);
	}

}

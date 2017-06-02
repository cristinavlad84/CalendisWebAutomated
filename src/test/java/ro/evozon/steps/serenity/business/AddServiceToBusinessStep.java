package ro.evozon.steps.serenity.business;

import java.util.List;
import java.util.Optional;

import net.thucydides.core.annotations.Step;
import ro.evozon.AbstractSteps;
import ro.evozon.pages.business.ServicesPage;
import ro.evozon.pages.business.SettingsPage;
import ro.evozon.tools.models.PriceList;

public class AddServiceToBusinessStep extends AbstractSteps {
	ServicesPage servicesPage;
	SettingsPage settingsPage;

	@Step
	public void click_on_price_list_tab() {
		settingsPage.click_on_price_list_tab();
	}

	@Step
	public String select_random_domain_to_add_service() {
		return servicesPage.select_random_domain_for_service();
	}
	@Step
	public void select_domain_to_add_service(String domain){
		servicesPage.select_domain_for_service(domain);
	}

	@Step
	public void click_on_add_service() {
		servicesPage.click_on_add_new_service();
	}

	@Step
	public void click_on_add_price_list() {
		servicesPage.click_on_add_price_list();
	}

	@Step
	public void fill_in_service_name(String name) {
		servicesPage.fill_in_service_name(name);
	}

	@Step
	public void fill_in_service_max_persons(String maxPersons) {
		servicesPage.fill_in_service_max_persons(maxPersons);
	}

	@Step
	public void fill_in_service_duration(String serviceDuration) {
	
		servicesPage.fill_in_service_duration(serviceDuration);
	}
	
	@Step
	public void fill_in_service_price(String price) {
		servicesPage.fill_in_service_price(price);

	}

	@Step
	public void fill_in_price_list_name(String priceListName) {
		servicesPage.fill_in_price_list_name(priceListName);
	}

	@Step
	public void fill_in_new_service_price_in_price_list_form(String price, int selected) {
		servicesPage.fill_in_new_price_price_list_form(price, selected);

	}

	@Step
	public String select_random_service_duration() {
		return servicesPage.select_random_service_duration();
	}
	
	@Step
	public void save_new_price_list() {
		servicesPage.save_new_price_list();
	}

	@Step
	public void click_on_modify_price_list(String priceListName) {
		servicesPage.click_on_modify_price_list(priceListName);
	}

	@Step
	public List<String> fill_in_all_prices_in_new_price_list_form() {
		return servicesPage.fill_in_all_prices_in_new_price_list_form();
	}

	@Step
	public String select_random_max_persons_per_service() {
		return servicesPage.select_random_max_persons_per_service();
	}

	@Step
	public void fill_in_max_persons_per_service(String persons) {
		servicesPage.fill_in_max_persons_per_service(persons);
	}
	@Step
	public void fill_in_service_duration_per_service(String duration) {
		servicesPage.fill_in_duration_per_service(duration);
	}

	@Step
	public void click_on_save_service_edit_form() {
		servicesPage.click_on_save_service_edit_form();
	}

	@Step
	public void click_on_save_service_button() {
		servicesPage.click_on_save_service();
	}

	@Step
	public void verify_service_name_appears_in_service_section(String serviceName) {

		softly.assertThat(servicesPage.is_service_name_displayed(serviceName)).as("service name").isTrue();
	}

	@Step
	public void verify_service_name_not_displayed_in_service_section(String serviceName) {

		softly.assertThat(servicesPage.is_service_name_displayed(serviceName)).as("service name" + serviceName)
				.isFalse();
	}

	@Step
	public Optional<PriceList> getPriceListFor(String sName) {
		return servicesPage.getPricesListFor(sName);

	}

	@Step
	public List<String> get_saved_prices_list() {
		return servicesPage.getServicesPrices();
	}

	@Step
	public void verify_service_details_appears_in_service_section(String serviceName, String servicePrice,
			String serviceDuration, String serviceMaxPersons) {

		softly.assertThat(servicesPage.is_service_detail_displayed_in_service_section(serviceName, servicePrice))
				.as("servicePrice").isTrue();
		softly.assertThat(servicesPage.is_service_detail_displayed_in_service_section(serviceName, serviceDuration))
				.as("serviceDuration").isTrue();
		softly.assertThat(servicesPage.is_service_detail_displayed_in_service_section(serviceName, serviceMaxPersons))
				.as("serviceMaxPersons").isTrue();

	}

	@Step
	public void click_on_modify_service_link(String serviceName) {
		servicesPage.click_on_modify_service_link(serviceName);
	}

	@Step
	public void click_on_delete_service_link(String serviceName) {
		servicesPage.click_on_delete_service_link(serviceName);
	}

}

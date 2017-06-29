package ro.evozon.steps.serenity.business;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.openqa.selenium.WebElement;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.matchers.BeanMatcher;
import ro.evozon.AbstractSteps;
import ro.evozon.pages.business.ServicesPage;
import ro.evozon.pages.business.SettingsPage;
import ro.evozon.tools.ConfigUtils;
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
	public void select_domain_to_add_service(String domain) {
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
	public List<Map<String, WebElement>> get_prices_elements_for_services_from_price_list_form() {
		List<Map<String, WebElement>> optList = servicesPage.get_prices_for_services();
		return optList;
	}

	@Step
	public List<Map<String, String>> get_prices_values_as_strings_for_services_from_price_list_form(
			List<Map<String, WebElement>> optList) {
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		Map<String, String> pricesContent = new HashMap<String, String>();
		for (Map<String, WebElement> map : optList) {
			map.forEach((k, v) -> pricesContent.put(k, v.getAttribute("value")));
			resultList.add(pricesContent);
		}
		return resultList;
	}

	@Step
	public void compareListsOfPrices(List<Map<String, String>> listOne, List<Map<String, String>> listTwo) {
		Map<String, String> mapOne = ConfigUtils.convertListToMap(listOne);
		Map<String, String> mapTwo = ConfigUtils.convertListToMap(listTwo);
		softly.assertThat(mapOne.equals(mapTwo)).isTrue();

	}

	@Step
	public List<String> get_saved_prices_list() {
		return servicesPage.getServicesPrices();
	}

	@Step
	public List<Map<String, String>> fill_in_all_prices_in_new_price_list_form() {
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
	public void edit_service_duration_per_service(String newDuration) {
		servicesPage.edit_duration_per_service(newDuration);
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
	public List<WebElement> get_service_in_table_matching(BeanMatcher... matchers) {
		return servicesPage.get_service_element_matching_criteria(matchers).get();
	}

	@Step
	public void verify_service_name_is_displayed_in_service_section(String serviceName) {

		softly.assertThat(servicesPage.is_service_found_in_list(serviceName)).isTrue();
	}

	@Step
	public void verify_service_name_not_displayed_in_service_section(String serviceName) {

		softly.assertThat(servicesPage.is_service_found_in_list(serviceName)).isFalse();
	}

	@Step
	public void verify_price_list_displayed_in_list(String priceListName) {
		servicesPage.is_price_list_found_in_list(priceListName);
	}

	@Step
	public WebElement get_service_webelement_in_list(BeanMatcher... matchers) {
		List<WebElement> elList = servicesPage.get_service_element_matching_criteria(matchers).get();
		softly.assertThat(elList.size()).isGreaterThan(0);
		return elList.get(0);
	}

	@Step
	public void verify_service_details_appears_in_service_section(WebElement serviceEl, String servicePrice,
			String serviceDuration, String serviceMaxPersons) {
		softly.assertThat(servicesPage.is_service_detail_present(serviceEl, serviceMaxPersons)).as("serviceMaxPersons")
		.isTrue();
		softly.assertThat(servicesPage.is_service_detail_present(serviceEl, servicePrice)).as("servicePrice").isTrue();
		softly.assertThat(servicesPage.is_service_detail_present(serviceEl, serviceDuration)).as("serviceDuration")
				.isTrue();
	

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

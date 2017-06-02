package ro.evozon.steps.serenity.business;

import java.util.List;
import java.util.Optional;

import net.thucydides.core.annotations.Step;
import ro.evozon.AbstractSteps;
import ro.evozon.pages.business.ServicesPage;
import ro.evozon.pages.business.SettingsPage;
import ro.evozon.pages.business.VoucherPage;
import ro.evozon.tools.models.PriceList;

public class AddVoucherToBusinessStep extends AbstractSteps {
	VoucherPage voucherPage;
	SettingsPage settingsPage;

	@Step
	public void click_on_add_voucher() {
		voucherPage.click_on_add_new_voucher();
	}

	@Step
	public void fill_in_voucher_name(String name) {
		voucherPage.fill_in_voucher_name(name);
	}

	@Step
	public void click_on_save_voucher_button() {
		voucherPage.click_on_save_voucher_edit_form();
	}
	@Step
	public void select_location_for_voucher(String domainName){
		voucherPage.select_location_for_voucher(domainName);
	}
	@Step
	public void verify_voucher_name_appears_in_domain_section(String voucherName) {

		softly.assertThat(voucherPage.is_voucher_name_displayed(voucherName)).as("voucher Name").isTrue();
	}
//	@Step
//	public void verify_service_name_appears_in_service_section(String serviceName) {
//
//		softly.assertThat(servicesPage.is_service_name_displayed(serviceName)).as("service name").isTrue();
//	}
//
//	@Step
//	public void verify_service_name_not_displayed_in_service_section(String serviceName) {
//
//		softly.assertThat(servicesPage.is_service_name_displayed(serviceName)).as("service name" + serviceName)
//				.isFalse();
//	}
//
//	@Step
//	public Optional<PriceList> getPriceListFor(String sName) {
//		return servicesPage.getPricesListFor(sName);
//
//	}
//
//	@Step
//	public List<String> get_saved_prices_list() {
//		return servicesPage.getServicesPrices();
//	}
//
//	@Step
//	public void verify_service_details_appears_in_service_section(String serviceName, String servicePrice,
//			String serviceDuration, String serviceMaxPersons) {
//
//		softly.assertThat(servicesPage.is_service_detail_displayed_in_service_section(serviceName, servicePrice))
//				.as("servicePrice").isTrue();
//		softly.assertThat(servicesPage.is_service_detail_displayed_in_service_section(serviceName, serviceDuration))
//				.as("serviceDuration").isTrue();
//		softly.assertThat(servicesPage.is_service_detail_displayed_in_service_section(serviceName, serviceMaxPersons))
//				.as("serviceMaxPersons").isTrue();
//
//	}

	

}

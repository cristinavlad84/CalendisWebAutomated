package ro.evozon.pages.business;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import net.serenitybdd.core.pages.WebElementFacade;
import ro.evozon.AbstractPage;
import ro.evozon.tools.Constants;
import ro.evozon.tools.FieldGenerators;
import ro.evozon.tools.models.PriceList;

public class VoucherPage extends AbstractPage {
	public void click_on_add_new_voucher() {
		WebElementFacade el = find(By.cssSelector("a[class='add-new-partner-voucher']"));
		scroll_in_view_then_click_on_element(el);
	}

	public void fill_in_voucher_name(String name) {
		enter(name).into(find(By.id("settings-input-partner-voucher")));
	}

	public void click_on_save_voucher_edit_form() {
		WebElementFacade elem = find(
				By.cssSelector("button[class='validation_button client_side_btn_l save-new-partner-voucher']"));
		scroll_in_view_then_click_on_element(elem);
	}

	public WebElementFacade getLocationElementFromVoucherForm() {
		WebElementFacade locationElem = null;
		List<WebElementFacade> singleLocationList = findAll(By.cssSelector(
				"div#add-partner-voucher > div[class='settings-section'] div[class='input-calendis col-xs-3 col-xs-offset-9'] >  div:nth-of-type(1) > div:nth-of-type(2) > input"));
		List<WebElementFacade> multipleLocationsList = findAll(By.cssSelector(
				"div#add-partner-voucher > div[class='settings-section'] div[class='input-calendis col-xs-3 col-xs-offset-9'] >  div:nth-of-type(1) > div:nth-of-type(2) > select"));
		if (singleLocationList.size() > 0) {
			locationElem = singleLocationList.get(0);
		} else if (multipleLocationsList.size() > 0) {
			locationElem = multipleLocationsList.get(0);
		}

		return locationElem;
	}

	public String select_random_domain_for_voucher() {
		String str = "";
		WebElementFacade elemList = getLocationElementFromVoucherForm();
		if (elemList.getTagName().contentEquals("input")) {
			// do nothing
		} else if (elemList.getTagName().contentEquals("select")) {
			str = select_random_option_in_dropdown(elemList);
		}
		return str;
	}

	public void select_location_for_voucher(String domain) {
		WebElementFacade elemList = getLocationElementFromVoucherForm();
		String str = domain.toLowerCase();
		String output = str.substring(0, 1).toUpperCase() + str.substring(1);
		if (elemList.getTagName().contentEquals("select")) {
			select_option_in_dropdown(elemList, output);
		}

	}

	public boolean is_voucher_name_displayed(String voucherName) {
		return is_element_present_in_elements_list("div#domains > div[class^='domain'] div[class='location-view']",
				"div[class='edit-information']:first-child> div[class='domain-name']:nth-child(1) > span:first-child",
				voucherName);

	}

	// modify from here
	public List<WebElementFacade> getServicesNamesElementsList() {

		return findAll(
				".//div[@class='input-calendis from-services col-xs-4 col-xs-offset-8']//div[@class='clearfix custom-pick price-lists']/div[@class='col-md-6']//h4[@class='service-name-list']");
	}

	public List<WebElementFacade> getPricesElementsList() {
		return findAll(
				".//div[@class='input-calendis from-services col-xs-4 col-xs-offset-8']//div[@class='clearfix custom-pick price-lists']/div[@class='col-md-4 price-input-amount']/input");
	}

	public List<PriceList> getServicesNameAndPrice() {

		List<String> namesL = getServicesNames();
		List<String> pricesL = getServicesPrices();

		List<PriceList> finalList = new ArrayList<PriceList>();
		for (int i = 0; i < namesL.size(); i++) {
			PriceList custom = new PriceList(namesL.get(i), pricesL.get(i));
			finalList.add(custom);
		}

		finalList.forEach(k -> System.out.println(k.getServiceName()));
		finalList.forEach(k -> System.out.println(k.getServicePrice()));
		return finalList;
	}

	public List<String> getServicesPrices() {
		List<WebElementFacade> listServicesPrices = new ArrayList<WebElementFacade>(getPricesElementsList());
		List<String> pricesL = listServicesPrices.stream().map(p -> p.getAttribute("value").trim())
				.collect(Collectors.toList());

		return pricesL;
	}

	public List<String> getServicesNames() {
		List<WebElementFacade> listServicesNames = new ArrayList<WebElementFacade>(getServicesNamesElementsList());
		List<String> namesL = listServicesNames.stream().map(p -> p.getText().trim()).collect(Collectors.toList());
		return namesL;
	}

	public Optional<PriceList> getPricesListFor(String selectedItem) {
		return getServicesNameAndPrice().stream().filter(item -> item.getServiceName().contains(selectedItem))
				.findFirst();
	}

	public WebElementFacade getPriceEditBoxElementFor(int serviceSelected) {
		return getPricesElementsList().get(serviceSelected);
	}

	public void fill_in_new_price_price_list_form(String newPrice, int selectedOpt) {

		enter(newPrice).into(getPriceEditBoxElementFor(selectedOpt));
	}

	public void fill_in_price_list_name(String priceListName) {
		enter(priceListName).into(find(By.id("list_name")));
	}

	public List<String> fill_in_all_prices_in_new_price_list_form() {
		List<WebElementFacade> pList = getPricesElementsList();
		List<String> newPricesList = new ArrayList<String>();
		for (WebElementFacade el : pList) {
			String priceN = new DecimalFormat("#.00").format(FieldGenerators
					.getRandomDoubleBetween(Constants.MIN_SERVICE_PRICE, Double.parseDouble(el.getValue()) - 1));
			enter(priceN).into(el);
			newPricesList.add(priceN);
		}
		return newPricesList;
	}

	public void save_new_price_list() {
		scroll_in_view_then_click_on_element(find(By.id("save-list")));

	}

	public List<WebElementFacade> get_all_prices_lists_elements() {
		return findAll(By
				.cssSelector("div[class='saved-services-details clearfix'] > div div[class='edit-information'] > h4"));
	}

	public WebElementFacade find_element_by(List<WebElementFacade> mList, String name) {
		Optional<WebElementFacade> fList = mList.stream()
				.filter(item -> item.getText().toLowerCase().contains(name.toLowerCase())).findFirst();
		// System.out.println("HIGUI!!!!!!!!!!!!" + fList.get().getText());
		return fList.get();
	}

	public void click_on_modify_price_list(String priceListName) {

		scroll_in_view_then_click_on_element(
				get_price_list_element_in_page(priceListName).find(By.cssSelector("span > a > i")));

	}

	public WebElementFacade get_price_list_element_in_page(String priceListName) {
		List<WebElementFacade> mList = new ArrayList<WebElementFacade>(get_all_prices_lists_elements());
		return find_element_by(mList, priceListName);
	}

	public String select_random_service_duration() {
		return select_random_option_in_dropdown(
				find(By.cssSelector("select[class='pick-me pick-duration-settings pull-left']")).waitUntilVisible());
	}

	public String select_random_max_persons_per_service() {
		return select_random_option_in_dropdown(
				find(By.cssSelector("select[class='pick-me pick-user-settings']")).waitUntilVisible());
	}

	public void fill_in_max_persons_per_service(String personsNo) {
		clickOn(find(By.cssSelector("select[class='pick-me pick-user-settings'] > option[data-other='1']")));
		enter(personsNo).into(find(By.cssSelector("input[class='pick-user-settings-input pull-left']")));
	}

	public void fill_in_duration_per_service(String duration) {
		clickOn(find(
				By.cssSelector("select[class='pick-me pick-duration-settings pull-left'] > option[data-other='1']")));
		enter(duration).into(find(By.cssSelector("input[class='pick-duration-settings-input pull-left']")));
	}

	public WebElementFacade getServiceWebElement(String serviceName) {
		return get_element_from_elements_list("div[class^='saved-services-details'] div[class='edit-information']",
				"h4[class='loc-address service-name']:first-child > span:nth-of-type(1)", serviceName);
	}

	public boolean is_service_detail_displayed_in_service_section(String serviceName, String serviceDetail) {
		WebElementFacade elementContainer = getServiceWebElement(serviceName);
		return is_item_displayed_through_found_element(elementContainer, serviceDetail, "span[class='location-phone']");
	}

	public boolean is_service_name_displayed(String serviceName) {

		return is_element_present_in_elements_list("div[class^='saved-services-details'] div[class='edit-information']",
				"h4[class='loc-address service-name']:first-child> span:nth-of-type(1)", serviceName);

	}

	public void click_on_modify_service_link(String serviceName) {
		WebElementFacade elem = getServiceWebElement(serviceName);
		WebElementFacade service = elem.find(By.cssSelector(
				"div[class='edit-information'] > h4[class='loc-address service-name'] > span:nth-child(2) > a[class='edit-info update-service'] > i:first-child"));
		scroll_in_view_then_click_on_element(service);
	}

	public void click_on_delete_service_link(String serviceName) {
		WebElementFacade elem = getServiceWebElement(serviceName);
		WebElementFacade service = elem.find(By.cssSelector(
				"div[class='edit-information'] > h4[class='loc-address service-name'] > span:nth-child(2) > a[class='edit-info delete-service'] > i:first-child"));
		scroll_in_view_then_click_on_element(service);
	}

}

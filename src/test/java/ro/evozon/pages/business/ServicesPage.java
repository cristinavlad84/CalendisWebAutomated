package ro.evozon.pages.business;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import net.serenitybdd.core.pages.WebElementFacade;
import ro.evozon.AbstractPage;
import ro.evozon.tools.Constants;
import ro.evozon.tools.FieldGenerators;
import ro.evozon.tools.models.ListingItem;
import ro.evozon.tools.models.PriceList;

public class ServicesPage extends AbstractPage {
	public void click_on_add_new_service() {
		WebElementFacade el = find(By.cssSelector("a[class='add-more new-service']"));
		scroll_in_view_then_click_on_element(el);
	}

	public void click_on_add_price_list() {
		WebElementFacade el = find(By.cssSelector("a[class='add-more new-pricelist']"));
		scroll_in_view_then_click_on_element(el);
	}

	public void fill_in_service_name(String name) {
		enter(name).into(find(By.id("service-name")));
	}

	public void fill_in_service_duration(String duration) {
		enter(duration).into(find(By.cssSelector("input[class='pick-duration-settings-input pull-left']")));
	}

	public void fill_in_service_max_persons(String maxPers) {
		enter(maxPers).into(find(By.cssSelector("input[class='pick-user-settings-input pull-left']")));
	}

	public void fill_in_service_price(String price) {
		enter(price).into(find(By.id("service-price")));
	}

	public void click_on_save_service_edit_form() {
		WebElementFacade elem = find(
				By.cssSelector("button[class='validation_button client_side_btn_m save-new-service']"));
		scroll_in_view_then_click_on_element(elem);
	}

	public void click_on_save_service() {
		WebElementFacade elem = find(
				By.cssSelector("button[class='validation_button client_side_btn_m save-new-service']"));
		scroll_in_view_then_click_on_element(elem);
	}

	public List<WebElementFacade> verify_single_or_multiple_location() {
		List<WebElementFacade> domainList = null;
		List<WebElementFacade> singleLocationList = findAll(By.cssSelector(
				"div[class^='modify-service input-calendis form-services '] > div:nth-of-type(2) > div:nth-of-type(2) > input"));
		List<WebElementFacade> multipleLocationList = findAll(By.cssSelector(
				"div[class^='modify-service input-calendis form-services '] > div:nth-of-type(2) > div:nth-of-type(2) > select"));
		if (singleLocationList.size() > 0) {
			domainList = singleLocationList;
		} else if (multipleLocationList.size() > 0) {
			domainList = multipleLocationList;
		}
		return domainList;
	}

	public String select_domain_for_service() {
		String str = "";
		List<WebElementFacade> elemList = verify_single_or_multiple_location();
		if (elemList.get(0).getTagName().contentEquals("input")) {
			// do nothing
		} else if (elemList.get(0).getTagName().contentEquals("select")) {
			str = select_random_option_in_dropdown(elemList.get(0));
		}
		return str;
	}

	private static ListingItem pricesToListingItem(PriceList priceList) {
		return new ListingItem(priceList.getServiceName(), priceList.getServicePrice());
	}

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
		System.out.println("HIGUI!!!!!!!!!!!!" + fList.get().getText());
		return fList.get();
	}

	public void click_on_modify_price_list(String priceListName) {
		waitforAllert();
		scroll_in_view_then_click_on_element(
				get_price_list_element_in_page(priceListName).find(By.cssSelector("span > a > i")));

	}

	public void waitforAllert() {
		WebDriverWait wait = new WebDriverWait(getDriver(), 5);
		wait.until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='main']/div/div/div[@id='myAlert']")));

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

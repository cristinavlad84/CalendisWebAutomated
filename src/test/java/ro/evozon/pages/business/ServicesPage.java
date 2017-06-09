package ro.evozon.pages.business;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.matchers.BeanMatcher;
import ro.evozon.AbstractPage;
import ro.evozon.tools.ConfigUtils;
import ro.evozon.tools.Constants;
import ro.evozon.tools.ElementsList;
import ro.evozon.tools.FieldGenerators;
import ro.evozon.tools.models.ListingItem;
import ro.evozon.tools.models.PriceList;
import static net.thucydides.core.matchers.BeanMatchers.the;
import static org.hamcrest.Matchers.containsString;

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
		clickOn(find(
				By.cssSelector("select[class='pick-me pick-duration-settings pull-left'] > option[data-other='1']")));
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

	public String select_random_domain_for_service() {
		String str = "";
		List<WebElementFacade> elemList = verify_single_or_multiple_location();
		if (elemList.get(0).getTagName().contentEquals("input")) {
			// do nothing
		} else if (elemList.get(0).getTagName().contentEquals("select")) {
			str = select_random_option_in_dropdown(elemList.get(0));
		}
		return str;
	}

	public void select_domain_for_service(String domain) {
		List<WebElementFacade> elemList = verify_single_or_multiple_location();
		String str = domain.toLowerCase();
		String output = str.substring(0, 1).toUpperCase() + str.substring(1);
		if (elemList.get(0).getTagName().contentEquals("select")) {
			select_option_in_dropdown(elemList.get(0), output);
		}
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
	public List<WebElementFacade> getPricesElementsList() {
		return findAll(
				".//div[@class='input-calendis from-services col-xs-4 col-xs-offset-8']//div[@class='clearfix custom-pick price-lists']/div[@class='col-md-4 price-input-amount']/input");
	}
	public List<String> getServicesPrices() {
		List<WebElementFacade> listServicesPrices = new ArrayList<WebElementFacade>(getPricesElementsList());
		List<String> pricesL = listServicesPrices.stream().map(p -> p.getAttribute("value").trim())
				.collect(Collectors.toList());

		return pricesL;
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

	public List<WebElement> get_service_element_matching_criteria(BeanMatcher... matchers) {
		WebElement table = find(By.xpath(".//div[@id='services']"));
		ElementsList.headingLocator = ".//li[@id='services-list']";
		ElementsList.rowContainerLocator = ".//div[starts-with(@class,'saved-services-details')]//div[@class='edit-information']";
		ElementsList.rowLocator = ".//h4[@class='loc-address service-name'][position()=1]/span[position()=1]";
		ElementsList.rowForHeadingLocator = ".//div[starts-with(@class,'saved-services-details')]//div[@class='edit-information'][h4][count(h4)>=";
		List<WebElement> matchingRows = ElementsList.filterRows(table, matchers);
		
		if (matchingRows.size() == 0) {
			throw new AssertionError("Service" + matchers + " not found");
		}
		System.out.println("found x " + matchingRows.size());
		System.out.println("!!!!!found " + matchingRows.get(0).getText());
		return matchingRows;
	}

	public WebElement get_web_element_for_service_in_list(String serviceName) {
		return get_service_element_matching_criteria(the("Servicii individuale", containsString(serviceName))).get(0);

	}

	public WebElement get_web_element_for_price_list(String priceListName) {
		return get_price_list_element_matching_criteria(the("Liste de prețuri", containsString(priceListName))).get(0);
	}

	public List<WebElement> get_price_list_element_matching_criteria(BeanMatcher... matchers) {
		WebElement table = find(By.xpath(".//div[@id='services']"));
		ElementsList.headingLocator = ".//li[@id='price-lists']";
		ElementsList.rowContainerLocator = ".//div[starts-with(@class,'saved-services-details')]//div[@class='edit-information']";
		ElementsList.rowLocator = ".//h4[@class='service-name loc-address'][position()=1]";
		ElementsList.rowForHeadingLocator = ".//div[starts-with(@class,'saved-services-details')]//div[@class='edit-information'][h4][count(h4)>=";
		List<WebElement> matchingRows = ElementsList.filterRows(table, matchers);
		
		if (matchingRows.size() == 0) {
			throw new AssertionError("Price list" + matchers + " not found");
		}
		System.out.println("found prize list size " + matchingRows.size());
		System.out.println("!!!!!found price list" + matchingRows.get(0).getText());
		return matchingRows;
	}

	public boolean is_price_list_found_in_list(String priceListName) {
		boolean isPresent = false;
		List<WebElement> elemL = get_price_list_element_matching_criteria(
				the("Liste de prețuri", containsString(priceListName)));
		if (elemL.size() > 0) {
			isPresent = true;
		}
		return isPresent;
	}

	public boolean is_service_found_in_list(String serviceName) {
		boolean isPresent = false;
		List<WebElement> elemL = get_service_element_matching_criteria(
				the("Servicii individuale", containsString(serviceName)));
		if (elemL.size() > 0) {
			isPresent = true;
		}
		return isPresent;
	}

	public boolean is_service_detail_present(String serviceName, String detail) {
		boolean isPresent = false;
		WebElement elem = get_service_element_matching_criteria(
				the("Servicii individuale", containsString(serviceName))).get(0);
		String serviceContent = elem.getText();
		if (ConfigUtils.removeAccents(serviceContent).toLowerCase().contains(detail.toLowerCase())) {
			isPresent = true;
		}
		return isPresent;
	}

	public boolean is_price_list_detail_present(String priceListName, String detail) {
		boolean isPresent = false;
		WebElement elem = get_price_list_element_matching_criteria(
				the("Liste de prețuri", containsString(priceListName))).get(0);
		String serviceContent = elem.getText();
		if (ConfigUtils.removeAccents(serviceContent).toLowerCase().contains(detail.toLowerCase())) {
			isPresent = true;
		}
		return isPresent;
	}

	public WebElementFacade find_element_by(List<WebElementFacade> mList, String name) {
		Optional<WebElementFacade> fList = mList.stream()
				.filter(item -> item.getText().toLowerCase().contains(name.toLowerCase())).findFirst();
		// System.out.println("HIGUI!!!!!!!!!!!!" + fList.get().getText());
		return fList.get();
	}

	public Optional<Map<Object, String>> search_for_service_in_table(String serviceName) {
		WebElement table = find(By.xpath(".//div[@id='services']"));
		ElementsList.headingLocator = ".//li[@id='services-list']";
		ElementsList.rowContainerLocator = ".//div[starts-with(@class,'saved-services-details')]//div[@class='edit-information']";
		ElementsList.rowLocator = ".//h4[@class='loc-address service-name'][position()=1]/span[position()=1]";
		ElementsList.rowForHeadingLocator = ".//div[starts-with(@class,'saved-services-details')]//div[@class='edit-information'][h4][count(h4)>=";
		List<Map<Object, String>> tableRows = ElementsList.withColumns("Servicii individuale").readRowsFrom(table);
		tableRows.forEach(System.out::println);
		// tableRows.forEach(p -> System.out.println(p.get("NUME GRUP")));
		// tableRows.forEach(p -> System.out.println(p.get("DISCOUNT GRUP")));
		Optional<Map<Object, String>> result = tableRows.stream()
				.filter(u -> (u.get("Servicii individuale").contains(serviceName))).findFirst();
		// .forEach(u -> System.out.println(u.get("NUME GRUP")));
		System.out.println("Servicii individuale " + result.get().get("Servicii individuale"));

		return result;
	}

	public void click_on_modify_price_list(String priceListName) {
		WebElement elem = get_web_element_for_price_list(priceListName);
		scroll_in_view_then_click_on_element(elem.findElement(By.cssSelector("span > a > i")));

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

	public void click_on_modify_service_link(String serviceName) {
		WebElement elem = get_web_element_for_service_in_list(
				serviceName);
		WebElement service = elem.findElement(By.cssSelector(
				"div[class='edit-information'] > h4[class='loc-address service-name'] > span:nth-child(2) > a[class='edit-info update-service'] > i:first-child"));
		scroll_in_view_then_click_on_element(service);
	}

	public void click_on_delete_service_link(String serviceName) {
		WebElement elem = get_web_element_for_service_in_list(serviceName);
		WebElement service = elem.findElement(By.cssSelector(
				"div[class='edit-information'] > h4[class='loc-address service-name'] > span:nth-child(2) > a[class='edit-info delete-service'] > i:first-child"));
		scroll_in_view_then_click_on_element(service);
	}

}

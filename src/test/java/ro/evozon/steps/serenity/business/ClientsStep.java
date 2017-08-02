package ro.evozon.steps.serenity.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.serenitybdd.core.annotations.findby.FindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.By;

import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.matchers.BeanMatcher;

import ro.evozon.AbstractSteps;
import ro.evozon.pages.business.ClientsPage;

public class ClientsStep extends AbstractSteps {
	ClientsPage clientsPage;

	@Step
	public void click_on_add_new_client() {
		clientsPage.click_on_add_client();
	}

	@Step
	public void fill_in_client_first_name(String firstName) {
		clientsPage.fill_in_clients_first_name(firstName);
	}

	@Step
	public void fill_in_client_last_name(String lastName) {
		clientsPage.fill_in_clients_last_name(lastName);
	}

	@Step
	public void fill_in_client_email(String clientEmail) {
		clientsPage.fill_in_clients_email(clientEmail);
	}

	@Step
	public void fill_in_client_phone_no(String clientPhone) {
		clientsPage.fill_in_client_phone(clientPhone);
	}

	@Step
	public void click_on_save_client_button() {
		clientsPage.click_on_save_client();
	}

	// @Step
	// public void verify_client_saved__in_table(String lastName, String
	// firstName) {
	// int size = clientsPage.getClientsPagesSize();
	//
	// System.out.println("size of clients list " + size);
	// Optional<List<WebElement>> mList = Optional.empty();
	// while (size >= 1) {
	// mList = clientsPage.get_client_in_table(lastName, firstName);
	// clientsPage.clickOnNextClientPage();
	// size--;
	// if (mList.get().size() == 1) {
	// System.out.println("found in table " +mList.get().get(0).getText());
	// break;
	// }
	//
	// }
	//
	// softly.assertThat(mList.get().size()).isEqualTo(1);
	//
	// }

	@Step
	public void select_client_in_table(String lastName, String firstName) {
		clientsPage.select_client_from_table(lastName, firstName);
	}

	@Step
	public void click_on_add_client_to_group() {
		clientsPage.click_on_add_to_group();
	}

	@Step
	public void select_group_for_client_to_add_to(String priceListName) {
		clientsPage.select_group_for_client_to_add_to(priceListName);
	}

	@Step
	public void click_on_save_adding_client_to_group() {
		clientsPage.click_on_save_adding_client_to_group_modal();
	}

	@Step
	public WebElement get_row_web_element_with_group(BeanMatcher... matchers) {
		List<WebElement> mList = clientsPage.get_row_web_element_with_group(matchers);
		softly.assertThat(mList.size()).isEqualTo(1);
		return mList.get(0);

	}

	@Step
	public void check_group_label(WebElement groupEl) {
		clientsPage.check_group_label(groupEl);
	}

	@Step
	public WebElement get_client_web_element_containig_client(BeanMatcher... matchers) {

		Optional<List<WebElement>> mList = Optional.empty();
		int pagesNo = clientsPage.getClientsPagesSize();
		int clicks_no = 0;
		do {

			// clientsPage.wait_until_table_loaded();
			mList = clientsPage.get_row_web_element_containig_client(matchers);

			if (mList.get().size() == 1) {
				System.out.println("found in table " + mList.get().get(0).getText());
				break;
			}
			System.out.println("checked");
			clientsPage.clickOnNextClientPage();
			clicks_no++;
			pagesNo--;

		} while (pagesNo >= 1);
		System.out.println("no of clicks" + clicks_no);
		softly.assertThat(mList.get().size()).isEqualTo(1);
		return mList.get().get(0);
	}

	@Step
	public void search_group(String groupName) {
		Optional<Map<String, WebElement>> optionalL = clientsPage.search_for_group_in_table(groupName);

	}
}

package ro.evozon.steps.serenity.business;

import java.util.Map;
import java.util.Optional;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.matchers.BeanMatcher;
import ro.evozon.AbstractSteps;
import ro.evozon.pages.business.BusinessHomePage;
import ro.evozon.pages.business.ClientsPage;

public class ClientsStep extends AbstractSteps {
	ClientsPage clientsPage;

	@Step
	public void click_on_clients_group_tab() {
		clientsPage.click_on_groups_tab();
	}

	@Step
	public void click_on_add_new_group() {
		clientsPage.click_an_add_new_group();
	}

	@Step
	public void fill_in_client_group_name(String groupName) {
		clientsPage.fill_in_clients_group_name(groupName);
	}

	@Step
	public void select_price_list(String listName) {
		clientsPage.select_price_list(listName);
	}

	@Step
	public void fill_in_discount_group_value(String discountValue) {
		clientsPage.fill_in_clients_group_discount(discountValue);
	}

	@Step
	public void click_on_save_grouo_button() {
		clientsPage.click_on_save_group();
	}

	@Step
	public void search_for_saved_group(String groupName, String discountValue) {
		Optional<Map<Object, String>> list = Optional.empty();
		list = clientsPage.search_for_list_in_table(groupName, discountValue);
		softly.assertThat(list.get().isEmpty()).isFalse();

	}


}

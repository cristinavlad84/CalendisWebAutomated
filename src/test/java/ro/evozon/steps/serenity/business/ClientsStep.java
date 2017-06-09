package ro.evozon.steps.serenity.business;

import java.util.Map;
import java.util.Optional;

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
	public void click_on_save_client_button() {
		clientsPage.click_on_save_client();
	}

	@Step
	public void search_for_saved_client_in_table(String lastName, String firstName) {
		Optional<Map<Object, String>> list = Optional.empty();
		list = clientsPage.search_for_client_in_table(lastName, firstName);
		softly.assertThat(list.get().isEmpty()).isFalse();

	}

}

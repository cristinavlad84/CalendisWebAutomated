package ro.evozon.steps.serenity.business;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.openqa.selenium.WebElement;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.matchers.BeanMatcher;
import ro.evozon.AbstractSteps;
import ro.evozon.pages.business.GroupPage;

public class AddGroupToBusinessStep extends AbstractSteps {
	GroupPage groupPage;

	@Step
	public void click_on_add_group() {
		groupPage.click_on_add_new_group();
	}

	@Step
	public void fill_in_group_name(String groupName) {
		groupPage.fill_in_group_name(groupName);
	}

	@Step
	public String get_group_name() {
		return groupPage.get_group_name();
	}

	@Step
	public void select_price_list(String priceList) {
		groupPage.select_price_list_for_group(priceList);
	}

	@Step
	public String get_selected_option_group_dropdown() {
		return groupPage.get_selected_option_price_dropdown();
	}

	@Step
	public void fill_in_discount_value(String discountValue) {
		groupPage.fill_in_discount_value_for_group(discountValue);
	}

	@Step
	public String get_discount_value() {
		return groupPage.get_discount_value_for_group();
	}

	@Step
	public void verify_groupName(String groupName) {
		softly.assertThat(groupPage.get_group_name()).as(" group name found on web page")
				.isEqualToIgnoringCase(groupName);
	}

	@Step
	public void verify_discountValue(String discountValue) {
		softly.assertThat(groupPage.get_discount_value_for_group()).as(" discount value found on web page")
				.isEqualToIgnoringCase(discountValue);
	}

	@Step
	public void verify_selected_option_in_price_list_dropdown(String priceList) {
		softly.assertThat(groupPage.get_selected_option_price_dropdown()).as("selected option on web page")
				.isEqualToIgnoringCase(priceList);
	}

	@Step
	public void click_on_save_group() {
		groupPage.click_on_save_group_form();
	}

	@Step
	public void search_for_saved_group(String groupName) {
		Optional<String> listGroup = Optional.empty();
		listGroup = groupPage.getGroupFor(groupName);
		softly.assertThat(listGroup.get().isEmpty()).isFalse();

	}

	@Step
	public void search_for_group_in_table(BeanMatcher... matchers) {
		List<WebElement> mList = groupPage.get_group_element_on_settings_page_matching_criteria(matchers);
		softly.assertThat(mList.size()).as("list").isGreaterThan(0);
	}

	@Step
	public WebElement get_row_element_containing_group(BeanMatcher... matchers) {
		WebElement el =groupPage.get_element_containing_group(matchers);
		System.out.println("!!!!!!!!!!!"+el.getText());
		return el;
	}

	@Step
	public void click_on_modify(WebElement modifyLink) {
		groupPage.click_on_modify_group(modifyLink);
	}
}

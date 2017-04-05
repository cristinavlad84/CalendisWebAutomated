package ro.evozon.steps.serenity.client;



import ro.evozon.pages.client.DictionaryPage;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;
import ro.evozon.AbstractSteps;

public class EndUserSteps extends AbstractSteps{

    DictionaryPage dictionaryPage;

    @Step
    public void enters(String keyword) {
        dictionaryPage.enter_keywords(keyword);
    }

    @Step
    public void starts_search() {
        dictionaryPage.lookup_terms();
    }

    @Step
    public void should_see_definition(String definition) {
        assertThat(dictionaryPage.getDefinitions(), hasItem(containsString(definition)));
    }

    @Step
    public void open_the_home_page_with_param() {
  
        dictionaryPage.open();
    }



	@Step
    public void looks_for(String term) {
        enters(term);
        starts_search();
    }
}
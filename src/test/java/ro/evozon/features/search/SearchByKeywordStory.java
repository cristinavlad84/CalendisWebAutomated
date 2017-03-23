package ro.evozon.features.search;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Issue;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Pending;
import net.thucydides.core.annotations.Steps;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import ro.evozon.tools.ConfigUtils;
import ro.evozon.steps.serenity.EndUserSteps;
import ro.evozon.tests.BaseTest;
@Narrative(text={"In order to choose the best vegetables",                      
        "As end user ",
        "I want to be able to search for specific terms"})
@RunWith(SerenityRunner.class)
public class SearchByKeywordStory extends BaseTest{



    @Steps
    public EndUserSteps endUser;

    @Issue("#WIKI-1")
    @Test
    public void searching_by_keyword_apple_should_display_the_corresponding_article() {
    	//endUser.is_the_home_page();
    	endUser.navigateTo(ConfigUtils.getBaseUrl());
    	endUser.looks_for("apple");
    	endUser.should_see_definition("A common, round fruit produced by the tree Malus domestica, cultivated in temperate climates.");

    }

    @Test
    public void searching_by_keyword_banana_should_display_the_corresponding_article() {
    	endUser.navigateTo(ConfigUtils.getBaseUrl());
    	endUser.looks_for("pear");
    	endUser.should_see_definition("An edible fruit produced by the pear tree, similar to an apple but elongated towards the stem.");
    }

    @Pending @Test
    public void searching_by_ambiguious_keyword_should_display_the_disambiguation_page() {
    }
} 
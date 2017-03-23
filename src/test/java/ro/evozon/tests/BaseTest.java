package ro.evozon.tests;

import net.thucydides.core.annotations.Managed;

import org.fluttercode.datafactory.impl.DataFactory;
import org.openqa.selenium.WebDriver;

public class BaseTest {

	@Managed(uniqueSession = true)
	public WebDriver webdriver;
	public DataFactory df = new DataFactory();

//	@Before
//	public void variableSetup() {
//		System.setProperty("webdriver.chrome.driver", ConfigUtils.getChromePath());
//	}
}

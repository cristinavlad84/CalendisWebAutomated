package ro.evozon.tests;

import net.thucydides.core.annotations.Managed;

import org.junit.Before;
import org.openqa.selenium.WebDriver;
import ro.evozon.tools.ConfigUtils;

public class BaseTest {

	@Managed(uniqueSession = true)
	public WebDriver webdriver;

	 @Before
	 public void variableSetup() {
	 System.setProperty("webdriver.chrome.driver",
	 "/usr/local/calendis/CalendisWebAutomated/CalendisWebAutomated/src/test/resources/drivers/chromedriverlinux");
	 }
}

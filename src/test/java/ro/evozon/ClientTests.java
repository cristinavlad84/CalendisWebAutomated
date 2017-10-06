package ro.evozon;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ro.evozon.features.client.login.ForgotPasswordForClientAccountStory;
import ro.evozon.features.client.login.LoginIntoClientAccountStory;
import ro.evozon.features.client.login.LoginIntoClientWithExistingBusinessAccountStory;
import ro.evozon.features.client.registration.CreateNewClientAccountStory;
import ro.evozon.features.client.registration.CreateNewClientWithExistingBusinessAccountStory;

@RunWith(Suite.class)
@SuiteClasses({ CreateNewClientAccountStory.class,
		CreateNewClientWithExistingBusinessAccountStory.class,
		LoginIntoClientAccountStory.class,
		LoginIntoClientWithExistingBusinessAccountStory.class,
		ForgotPasswordForClientAccountStory.class

})
public class ClientTests {

}

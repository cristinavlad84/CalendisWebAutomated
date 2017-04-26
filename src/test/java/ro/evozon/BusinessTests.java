package ro.evozon;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ro.evozon.features.business.login.LoginAsColaboratorStory;
import ro.evozon.features.business.login.LoginAsReceptionistStory;
import ro.evozon.features.business.login.LoginAsSpecialistStory;
import ro.evozon.features.business.login.LoginIntoBusinessAccountStory;
import ro.evozon.features.business.login.LoginIntoStaffAccountAddedByBusinessWizardStory;
import ro.evozon.features.business.registration.BusinessRegistrationWizardStory;
import ro.evozon.features.business.registration.CreateNewBusinessAccountStory;
import ro.evozon.features.business.settings.AddColaboratorFromBusinessAccountStory;
import ro.evozon.features.business.settings.AddNewLocationFromBusinessAccountStory;
import ro.evozon.features.business.settings.AddReceptionistFromBusinessAccountStory;
import ro.evozon.features.business.settings.AddSpecialistFromBusinessAccountStory;
import ro.evozon.features.business.settings.EditSpecialistFromBusinessAccountStory;


@RunWith(Suite.class)
@SuiteClasses({ CreateNewBusinessAccountStory.class,
	//BusinessRegistrationWizardStory.class,
	LoginIntoBusinessAccountStory.class,
	LoginIntoStaffAccountAddedByBusinessWizardStory.class,
	AddColaboratorFromBusinessAccountStory.class,
	AddNewLocationFromBusinessAccountStory.class,
	AddReceptionistFromBusinessAccountStory.class,
	AddSpecialistFromBusinessAccountStory.class,
	LoginAsSpecialistStory.class,
	LoginAsReceptionistStory.class,
	LoginAsColaboratorStory.class,
	
	EditSpecialistFromBusinessAccountStory.class

})
public class BusinessTests {

}

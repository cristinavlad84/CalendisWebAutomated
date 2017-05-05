package ro.evozon;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ro.evozon.features.business.login.LoginAsColaboratorStory;
import ro.evozon.features.business.login.LoginAsReceptionistStory;
import ro.evozon.features.business.login.LoginAsSpecialistStory;
import ro.evozon.features.business.login.LoginIntoBusinessAccountStory;
import ro.evozon.features.business.login.LoginIntoStaffAccountAddedByBusinessWizardStory;
import ro.evozon.features.business.registration.CreateNewBusinessAccountStory;
import ro.evozon.features.business.settings.AddColaboratorFromBusinessAccountStory;
import ro.evozon.features.business.settings.AddNewDomainFromBusinessAccountStory;
import ro.evozon.features.business.settings.AddNewLocationFromBusinessAccountStory;
import ro.evozon.features.business.settings.AddNewServiceFromBusinessAccountStory;
import ro.evozon.features.business.settings.AddPriceListFromBusinessAccountStory;
import ro.evozon.features.business.settings.AddReceptionistFromBusinessAccountStory;
import ro.evozon.features.business.settings.AddSpecialistFromBusinessAccountStory;
import ro.evozon.features.business.settings.DeleteDomainFromBusinessAccountStory;
import ro.evozon.features.business.settings.DeleteLocationFromBusinessAccountStory;
import ro.evozon.features.business.settings.DeleteServiceFromBusinessAccountStory;
import ro.evozon.features.business.settings.EditLocationFromBusinessAccountStory;
import ro.evozon.features.business.settings.EditServiceFromBusinessAccountStory;
import ro.evozon.features.business.settings.EditSpecialistFromBusinessAccountStory;

@RunWith(Suite.class)
@SuiteClasses({ CreateNewBusinessAccountStory.class, LoginIntoBusinessAccountStory.class,
		LoginIntoStaffAccountAddedByBusinessWizardStory.class, AddColaboratorFromBusinessAccountStory.class,
		AddNewLocationFromBusinessAccountStory.class, EditLocationFromBusinessAccountStory.class,
		DeleteLocationFromBusinessAccountStory.class, AddNewDomainFromBusinessAccountStory.class,
		DeleteDomainFromBusinessAccountStory.class, AddReceptionistFromBusinessAccountStory.class,
		AddSpecialistFromBusinessAccountStory.class, EditSpecialistFromBusinessAccountStory.class,
		AddNewServiceFromBusinessAccountStory.class, EditServiceFromBusinessAccountStory.class,
		DeleteServiceFromBusinessAccountStory.class, LoginAsSpecialistStory.class, LoginAsReceptionistStory.class,
		LoginAsColaboratorStory.class, EditSpecialistFromBusinessAccountStory.class,
		AddNewDomainFromBusinessAccountStory.class, AddNewServiceFromBusinessAccountStory.class,
		AddPriceListFromBusinessAccountStory.class

})
public class BusinessTests {

}

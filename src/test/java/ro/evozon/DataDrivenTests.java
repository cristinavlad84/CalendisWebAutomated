package ro.evozon;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ro.evozon.features.business.datadriven.AddNewDomainDataDrivenStory;
import ro.evozon.features.business.datadriven.AddNewLocationDataDrivenStory;
import ro.evozon.features.business.datadriven.AddNewServiceDataDrivenStory;
import ro.evozon.features.business.datadriven.AddPermissionsDataDrivenStory;
import ro.evozon.features.business.datadriven.AddReceptionistDataDrivenStory;
import ro.evozon.features.business.datadriven.AddSpecialistDataDrivenStory;
import ro.evozon.features.business.datadriven.CreateNewBusinessAccountWithRealTestDataStory;

@RunWith(Suite.class)
@SuiteClasses({ CreateNewBusinessAccountWithRealTestDataStory.class, AddNewLocationDataDrivenStory.class,
		AddNewDomainDataDrivenStory.class, AddNewServiceDataDrivenStory.class, AddSpecialistDataDrivenStory.class,
		AddReceptionistDataDrivenStory.class, AddPermissionsDataDrivenStory.class

})
public class DataDrivenTests {

}

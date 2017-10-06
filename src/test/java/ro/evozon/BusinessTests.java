package ro.evozon;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ro.evozon.features.business.appointments.AddMultipleClientsAppointmentStory;
import ro.evozon.features.business.appointments.AddMultipleServicesAppointmentStory;
import ro.evozon.features.business.appointments.AddNewQuickAppointmentStory;
import ro.evozon.features.business.appointments.CollectPaymentAppointmentStory;
import ro.evozon.features.business.appointments.CollectPaymentMultipleServicesAppointmentStory;
import ro.evozon.features.business.appointments.CollectPaymentSpecialPriceListWithVoucherAndAdditionalCostsStory;
import ro.evozon.features.business.appointments.CollectPaymentWithVoucherAndAdditionalCostsStory;
import ro.evozon.features.business.appointments.CollectPaymentWithVoucherAppointmentStory;
import ro.evozon.features.business.appointments.EditAppointmentStory;
import ro.evozon.features.business.appointments.PartialPaymentWithVoucherAndAdditionalCostStory;
import ro.evozon.features.business.login.LoginAsColaboratorStory;
import ro.evozon.features.business.login.LoginAsReceptionistStory;
import ro.evozon.features.business.login.LoginAsSpecialistStory;
import ro.evozon.features.business.login.LoginIntoBusinessAccountStory;
import ro.evozon.features.business.login.LoginIntoStaffAccountAddedByBusinessWizardStory;
import ro.evozon.features.business.registration.CreateNewBusinessAccountStory;
import ro.evozon.features.business.settings.AddClientToGroupStory;
import ro.evozon.features.business.settings.AddColaboratorFromBusinessAccountStory;
import ro.evozon.features.business.settings.AddDiscountGroupStory;
import ro.evozon.features.business.settings.AddNewClientStory;
import ro.evozon.features.business.settings.AddNewDomainFromBusinessAccountStory;
import ro.evozon.features.business.settings.AddNewLocationFromBusinessAccountStory;
import ro.evozon.features.business.settings.AddNewServiceFromBusinessAccountStory;
import ro.evozon.features.business.settings.AddPriceListFromBusinessAccountStory;
import ro.evozon.features.business.settings.AddReceptionistFromBusinessAccountStory;
import ro.evozon.features.business.settings.AddSpecialPriceGroupStory;
import ro.evozon.features.business.settings.AddSpecialistFromBusinessAccountStory;
import ro.evozon.features.business.settings.AddVoucherCodeStory;
import ro.evozon.features.business.settings.AssignServiceToSpecialistStory;
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
		LoginAsColaboratorStory.class, AddPriceListFromBusinessAccountStory.class, AddNewQuickAppointmentStory.class,
		EditAppointmentStory.class, AddNewClientStory.class, AddDiscountGroupStory.class, AddClientToGroupStory.class,
		AddSpecialPriceGroupStory.class, AddVoucherCodeStory.class, AssignServiceToSpecialistStory.class,
		AddMultipleClientsAppointmentStory.class, AddMultipleServicesAppointmentStory.class
//		CollectPaymentAppointmentStory.class, CollectPaymentMultipleServicesAppointmentStory.class,
//		CollectPaymentSpecialPriceListWithVoucherAndAdditionalCostsStory.class,
//		CollectPaymentWithVoucherAndAdditionalCostsStory.class, CollectPaymentWithVoucherAppointmentStory.class,
//		PartialPaymentWithVoucherAndAdditionalCostStory.class

})
public class BusinessTests {

}

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
import ro.evozon.features.business.settings.*;

@RunWith(Suite.class)
@SuiteClasses({ CreateNewBusinessAccountStory.class, LoginIntoBusinessAccountStory.class,
		LoginIntoStaffAccountAddedByBusinessWizardStory.class, AddColaboratorFromBusinessAccountStory.class,
		AddNewLocationFromBusinessAccountStory.class, EditLocationFromBusinessAccountStory.class,
		DeleteLocationFromBusinessAccountStory.class, AddNewDomainFromBusinessAccountStory.class,
		DeleteDomainFromBusinessAccountStory.class, AddReceptionistFromBusinessAccountStory.class,
		AddSpecialistFromBusinessAccountStory.class, EditSpecialistFromBusinessAccountStory.class,
		AddNewServiceFromBusinessAccountStory.class, EditServiceFromBusinessAccountStory.class,
		DeleteServiceFromBusinessAccountStory.class, AddServicePacketStory.class, LoginAsSpecialistStory.class, LoginAsReceptionistStory.class,
		LoginAsColaboratorStory.class, AddPriceListFromBusinessAccountStory.class, AddNewQuickAppointmentStory.class,
		EditAppointmentStory.class, AddNewClientStory.class, AddDiscountGroupStory.class, AddClientToGroupStory.class,
		AddSpecialPriceGroupStory.class, AddVoucherCodeStory.class, AssignServiceToSpecialistStory.class,
		AddMultipleClientsAppointmentStory.class, AddMultipleServicesAppointmentStory.class,
		CollectPaymentAppointmentStory.class, CollectPaymentMultipleServicesAppointmentStory.class,
		CollectPaymentSpecialPriceListWithVoucherAndAdditionalCostsStory.class,
		CollectPaymentWithVoucherAndAdditionalCostsStory.class, CollectPaymentWithVoucherAppointmentStory.class,
		PartialPaymentWithVoucherAndAdditionalCostStory.class

})
public class BusinessTests {

}

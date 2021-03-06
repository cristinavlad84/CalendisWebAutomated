package ro.evozon.tools.enums;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum PermissionEnum {
    CREATE_APPOINTMENT("create_appointment"),
    UPDATE_APPOINTMEN("update_appointment"),
    EDIT_PAST_APPOINTMENTS("edit_past_appointments"),
    VIEW_OTHERS_CALENDAR("view_others_calendar"),
    CREATE_OTHERS_APOINTMENT("create_others_appointment"),
    UPDATE_OTHERS_APPOINTMENT("update_others_appointment"),
    EDIT_PAST_APPOINTMENTS_OTHERS("edit_past_appointments_others"),
    CONTACTS_DATA("contact_data"),
    VIEW_CLIENT_DATA_BASE("attachments"),
    EDIT_CLIENT_INFO("edit_contact_data"),
    SET_SCHEDULE("schedule"),
    SET_EXCEPTIONS("schedule_exceptions");

    private final String stringValue;

    PermissionEnum(final String name) {
        this.stringValue = name;
    }

    public String getStringValue() {
        return stringValue;
    }
    public static List<String> getAllValues(){
        return Stream.of(PermissionEnum.values())
                .map(PermissionEnum::getStringValue)
                .collect(Collectors.toList());
    }


}

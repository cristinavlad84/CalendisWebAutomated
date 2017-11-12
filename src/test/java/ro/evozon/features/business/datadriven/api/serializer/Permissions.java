package ro.evozon.features.business.datadriven.api.serializer;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "userID",
        "permissionID"
})
public class Permissions {

    @JsonProperty("userID")
    private Integer userID;
    @JsonProperty("permissionID")
    private Integer permissionID;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("userID")
    public Integer getUserID() {
        return userID;
    }

    @JsonProperty("userID")
    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    @JsonProperty("permissionID")
    public Integer getPermissionID() {
        return permissionID;
    }

    @JsonProperty("permissionID")
    public void setPermissionID(Integer permissionID) {
        this.permissionID = permissionID;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
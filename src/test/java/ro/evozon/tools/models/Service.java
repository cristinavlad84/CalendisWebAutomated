package ro.evozon.tools.models;

public class Service {
    private String asociatedDomain;
    private String serviceName;
    private String serviceDuration;
    private String servicePrice;
    private String persPerService;
    private String domainId;

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    private String locationId;

    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }



    public String getAsociatedDomain() {
        return asociatedDomain;
    }

    public void setAsociatedDomain(String asociatedDomain) {
        this.asociatedDomain = asociatedDomain;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceDuration() {
        return serviceDuration;
    }

    public void setServiceDuration(String serviceDuration) {
        this.serviceDuration = serviceDuration;
    }

    public String getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getPersPerService() {
        return persPerService;
    }

    public void setPersPerService(String persPerService) {
        this.persPerService = persPerService;
    }

    public String toString() {
        return asociatedDomain + "," + serviceName + "," + serviceDuration + "," + servicePrice + "," + persPerService + "," + domainId+","+ locationId;
    }


}

package ro.evozon.tools.models;

public class Domain {
    public void setNumeDomeniu(String numeDomeniu) {
        this.numeDomeniu = numeDomeniu;
    }

    public void setLocatiaDomeniului(String locatiaDomeniului) {
        this.locatiaDomeniului = locatiaDomeniului;
    }

    public String numeDomeniu;

    public String getNumeDomeniu() {
        return numeDomeniu;
    }

    public String getLocatiaDomeniului() {
        return locatiaDomeniului;
    }

    public String locatiaDomeniului;

    public void setLocatie_id(String locatie_id) {
        this.locatie_id = locatie_id;
    }

    public String locatie_id;


    public String getLocatieId()
    {
        return locatie_id;
    }
    public void setLocatieId(String locatie_id){
        this.locatie_id = locatie_id;
    }

    @Override
    public String toString() {
        return  numeDomeniu+","+locatiaDomeniului+","+ locatie_id;
    }
}

package ro.evozon.tools.models;

public class Location {
    public String adresaLocatie, judetLocatie, localitateLocatie, telefonLocatie, numeLocatie, luni, marti, miercuri, joi, vineri, sambata, duminica, locatie_id;

    public String getAdresaLocatie() {
        return adresaLocatie;
    }
    public String getLocatieId()
    {
        return locatie_id;
    }
    public void setLocatieId(String locatie_id){
        this.locatie_id = locatie_id;
    }
    public void setAdresaLocatie(String adresaLocatie) {
        this.adresaLocatie = adresaLocatie;
    }

    public String getJudetLocatie() {
        return judetLocatie;
    }

    public void setJudetLocatie(String judetLocatie) {
        this.judetLocatie = judetLocatie;
    }

    public String getLocalitateLocatie() {
        return localitateLocatie;
    }

    public void setLocalitateLocatie(String localitateLocatie) {
        this.localitateLocatie = localitateLocatie;
    }

    public String getTelefonLocatie() {
        return telefonLocatie;
    }

    public void setTelefonLocatie(String telefonLocatie) {
        this.telefonLocatie = telefonLocatie;
    }

    public String getNumeLocatie() {
        return numeLocatie;
    }

    public void setNumeLocatie(String numeLocatie) {
        this.numeLocatie = numeLocatie;
    }

    public String getLuni() {
        return luni;
    }

    public void setLuni(String luni) {
        this.luni = luni;
    }

    public String getMarti() {
        return marti;
    }

    public void setMarti(String marti) {
        this.marti = marti;
    }

    public String getMiercuri() {
        return miercuri;
    }

    public void setMiercuri(String miercuri) {
        this.miercuri = miercuri;
    }

    public String getJoi() {
        return joi;
    }

    public void setJoi(String joi) {
        this.joi = joi;
    }

    public String getVineri() {
        return vineri;
    }

    public void setVineri(String vineri) {
        this.vineri = vineri;
    }

    public String getSambata() {
        return sambata;
    }

    public void setSambata(String sambata) {
        this.sambata = sambata;
    }

    public String getDuminica() {
        return duminica;
    }

    public void setDuminica(String duminica) {
        this.duminica = duminica;
    }
    @Override
    public String toString() {
        return  adresaLocatie+","+judetLocatie+","+ localitateLocatie+","+telefonLocatie+","+numeLocatie+","+luni+marti+","+miercuri+","+joi+","+vineri+","+sambata+","+duminica+","+locatie_id;
    }
}

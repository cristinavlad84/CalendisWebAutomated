package ro.evozon.tools.models;

public class Staff {
    private String numeAngajat;
    private String emailAngajat;
    private String telefonAngajat;
    private String luni;
    private String marti;
    private String miercuri;
    private String joi;
    private String vineri;
    private String sambata;
    private String duminica;
    private String serviciuAsignat;
    private String serviciuId;
    private String domeniuId;
    private String locatieId;

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    private String staffId;
    public String getDomeniuId() {
        return domeniuId;
    }

    public void setDomeniuId(String domeniuId) {
        this.domeniuId = domeniuId;
    }

    public String getLocatieId() {
        return locatieId;
    }

    public void setLocatieId(String locatieId) {
        this.locatieId = locatieId;
    }

    public String getNumeAngajat() {
        return numeAngajat;
    }

    public void setNumeAngajat(String numeAngajat) {
        this.numeAngajat = numeAngajat;
    }

    public String getEmailAngajat() {
        return emailAngajat;
    }

    public void setEmailAngajat(String emailAngajat) {
        this.emailAngajat = emailAngajat;
    }

    public String getTelefonAngajat() {
        return telefonAngajat;
    }

    public void setTelefonAngajat(String telefonAngajat) {
        this.telefonAngajat = telefonAngajat;
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

    public String getServiciuAsignat() {
        return serviciuAsignat;
    }

    public void setServiciuAsignat(String serviciuAsignat) {
        this.serviciuAsignat = serviciuAsignat;
    }

    public String getServiciuId() {
        return serviciuId;
    }

    public void setServiciuId(String serviciuId) {
        this.serviciuId = serviciuId;
    }

    public String toString() {
        return numeAngajat + "," + emailAngajat + "," + telefonAngajat + "," + luni + "," + marti + "," + miercuri + "," + joi + "," + vineri + "," + sambata + "," + duminica + "," + serviciuAsignat + "," + serviciuId + "," + domeniuId + "," + locatieId+","+staffId;
    }
}

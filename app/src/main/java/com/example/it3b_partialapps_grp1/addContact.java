package com.example.it3b_partialapps_grp1;

public class addContact {

    private String contact;
    private String location;
    private String typeOfContact;
    private String uId;
    private String uEmail;
    private String datenow;
    private String nofFacility;
    private String nAddress;


    public addContact(String contact, String location, String typeOfContact,String uId, String uEmail, String datenow, String nofFacility, String nAddress) {
        this.contact = contact;
        this.location = location;
        this.typeOfContact = typeOfContact;
        this.uId = uId;
        this.uEmail = uEmail;
        this.datenow = datenow;
        this.nofFacility = nofFacility;
        this.nAddress = nAddress;
    }

    public String getNofFacility() {
        return nofFacility;
    }

    public void setNofFacility(String nofFacility) {
        this.nofFacility = nofFacility;
    }

    public String getnAddress() {
        return nAddress;
    }

    public void setnAddress(String nAddress) {
        this.nAddress = nAddress;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setTypeOfContact(String typeOfContact) {
        this.typeOfContact = typeOfContact;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getuEmail() {
        return uEmail;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public String getDatenow() {
        return datenow;
    }

    public void setDatenow(String datenow) {
        this.datenow = datenow;
    }



    public String getContact() {
        return contact;
    }

    public String getLocation() {
        return location;
    }

    public String getTypeOfContact() {
        return typeOfContact;
    }
}

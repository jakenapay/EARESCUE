package com.example.it3b_partialapps_grp1;

public class Contact {
    private String contact;
    private String location;
    private String typeOfContact;

    public Contact(){

    }

    public Contact(String contact, String location, String typeOfContact) {
        this.contact = contact;
        this.location = location;
        this.typeOfContact = typeOfContact;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTypeOfContact() {
        return typeOfContact;
    }

    public void setTypeOfContact(String typeOfContact) {
        this.typeOfContact = typeOfContact;
    }
}

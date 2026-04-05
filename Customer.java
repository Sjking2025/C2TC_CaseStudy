package com.tnsif.bankingservice;

public class Customer {
    private int customerID;
    private String name;
    private String address;
    private String Contact;

    public Customer(int customerID, String name, String address, String contact) {
        this.customerID = customerID;
        this.name = name;
        this.address = address;
        this.Contact = contact;
    }

    public int getCustomerID()              { return customerID; }
    public void setCustomerID(int id)       { this.customerID = id; }

    public String getName()                 { return name; }
    public void setName(String name)        { this.name = name; }

    public String getAddress()              { return address; }
    public void setAddress(String address)  { this.address = address; }

    public String getContact()              { return Contact; }
    public void setContact(String contact)  { this.Contact = contact; }

    @Override
    public String toString() {
        return "Customer [customerID=" + customerID + ", name=" + name
                + ", address=" + address + ", Contact=" + Contact + "]";
    }
}
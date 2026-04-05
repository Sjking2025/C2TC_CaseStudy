package com.tnsif.bankingservice;

public class Beneficiary {
    private int beneficiaryID;
    private int customerID;
    private String name;
    private String accountNumber;
    private String bankDetails;

    public Beneficiary(int beneficiaryID, int customerID, String name,
                       String accountNumber, String bankDetails) {
        this.beneficiaryID = beneficiaryID;
        this.customerID = customerID;
        this.name = name;
        this.accountNumber = accountNumber;
        this.bankDetails = bankDetails;
    }

    public int getBeneficiaryID()                   { return beneficiaryID; }
    public void setBeneficiaryID(int id)            { this.beneficiaryID = id; }

    public int getCustomerID()                      { return customerID; }
    public void setCustomerID(int id)               { this.customerID = id; }

    public String getName()                         { return name; }
    public void setName(String name)                { this.name = name; }

    public String getAccountNumber()                { return accountNumber; }
    public void setAccountNumber(String num)        { this.accountNumber = num; }

    public String getBankDetails()                  { return bankDetails; }
    public void setBankDetails(String details)      { this.bankDetails = details; }

    @Override
    public String toString() {
        return "Beneficiary [beneficiaryID=" + beneficiaryID + ", customerID=" + customerID
                + ", name=" + name + ", accountNumber=" + accountNumber
                + ", bankDetails=" + bankDetails + "]";
    }
}
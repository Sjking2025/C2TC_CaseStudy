package com.tnsif.bankingservice;

public class Account {

    private int accountID;
    private int customerID;
    private String type;
    private Double balance;

    public Account(int accountID, int customerID, String type, Double balance) {
        this.accountID = accountID;
        this.customerID = customerID;
        this.type = type;
        this.balance = balance;
    }

    public int getAccountID()             { return accountID; }
    public void setAccountID(int id)      { this.accountID = id; }

    public int getCustomerID()            { return customerID; }
    public void setCustomerID(int id)     { this.customerID = id; }

    public String getType()               { return type; }
    public void setType(String type)      { this.type = type; }

    public Double getBalance()            { return balance; }
    public void setBalance(Double bal)    { this.balance = bal; }

    @Override
    public String toString() {
        return "Account [accountID=" + accountID + ", customerID=" + customerID
                + ", type=" + type + ", balance=" + balance + "]";
    }
}
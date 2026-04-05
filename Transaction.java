package com.tnsif.bankingservice;

import java.time.LocalDateTime;

public class Transaction {
    private int transactionID;
    private int accountID;
    private String type;
    private Double amount;
    private LocalDateTime timestamp;

    public Transaction(int accountID, String type, double amount) {
        this.accountID = accountID;
        this.type = type;
        this.amount = amount;
    }

    public int getTransactionID()                   { return transactionID; }
    public void setTransactionID(int id)            { this.transactionID = id; }

    public int getAccountID()                       { return accountID; }
    public void setAccountID(int id)                { this.accountID = id; }

    public String getType()                         { return type; }
    public void setType(String type)                { this.type = type; }

    public Double getAmount()                       { return amount; }
    public void setAmount(Double amount)            { this.amount = amount; }

    public LocalDateTime getTimestamp()             { return timestamp; }
    public void setTimestamp(LocalDateTime ts)      { this.timestamp = ts; }

    @Override
    public String toString() {
        return "Transaction [transactionID=" + transactionID + ", accountID=" + accountID
                + ", type=" + type + ", amount=" + amount + "]";
    }
}
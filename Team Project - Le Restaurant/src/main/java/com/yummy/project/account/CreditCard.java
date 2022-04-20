package com.yummy.project.account;

// Contains information for a credit card
public class CreditCard {
    private String cardHolderName;
    private String cardNumber;
    private String expirationDate;
    private String cvcCode;

    public CreditCard(String cardHolderName, String cardNumber, String expirationDate, String cvcCode) {
        this.cardHolderName = cardHolderName;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cvcCode = cvcCode;
    }

    public String getCardHolderName() {
        return this.cardHolderName;
    }

    public void setCardHolderName(String name) {
        this.cardHolderName = name;
    }

    public String getCardNumber() {
        return this.cardNumber;
    }

    public void setCardNumber(String num) {
        this.cardNumber = num;
    }

    public String getExpirationDate() {
        return this.expirationDate;
    }

    public void setExpirationDate(String exp) {
        this.expirationDate = exp;
    }

    public String getCvcCode() {
        return this.cvcCode;
    }

    public void setCvcCode(String code) {
        this.cvcCode = code;
    }

}

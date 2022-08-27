package com.mindhub.homebanking.dtos;

public class LoanApplicationDTO {

    private long id;
    private double amount; // Viene de Loans
    private int payment; // Viene de Loans
    private String destinationAccountNumber; // Viene de Account

    public LoanApplicationDTO(){}

    public LoanApplicationDTO(long id, double amount, int payment, String destinationAccountNumber){
        this.id = id;
        this.amount = amount;
        this.payment = payment;
        this.destinationAccountNumber = destinationAccountNumber;
    }

    public long getId() {return id;}

    public double getAmount() {return amount;}
    public void setAmount(double amount) {this.amount = amount;}

    public int getPayment() {return payment;}
    public void setPayment(int payment) {this.payment = payment;}

    public String getDestinationAccountNumber() {return destinationAccountNumber;}
    public void setDestinationAccountNumber(String destinationAccountNumber) {this.destinationAccountNumber = destinationAccountNumber;}
}

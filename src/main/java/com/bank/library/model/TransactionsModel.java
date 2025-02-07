package com.bank.library.model;

import lombok.Data;

@Data
public class TransactionsModel {

    private int transactionId;
    private int loanId;
    private int actionType;
    private String actionDate;
}

package com.bank.library.model;

import lombok.Data;

@Data
public class LoansModel {

    private int loanId;
    private int userId;
    private int bookId;
    private String loanDate;
    private String dueDate;
    private String returnDate;
    private int status;
}

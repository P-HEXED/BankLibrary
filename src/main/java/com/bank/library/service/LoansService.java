package com.bank.library.service;

import com.bank.library.model.LoansModel;

public interface LoansService {

    Integer addLoans(LoansModel loans) throws Exception;
    int checkBorrow(int user_id, int book_id, int status) throws Exception;
    Boolean returnBook(int status_type, int loan_id) throws Exception;
    int checkDueDateAndReturnDate(int loan_id) throws Exception;

}

package com.bank.library.service.impl;

import com.bank.library.model.LoansModel;
import com.bank.library.repository.LoansRepository;
import com.bank.library.service.LoansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoansServiceImpl implements LoansService {

    @Autowired
    private LoansRepository loansRepository;

    @Override
    public Integer addLoans(LoansModel loans) throws Exception {
        return loansRepository.addLoans(loans);
    }

    @Override
    public int checkBorrow(int user_id, int book_id, int status) throws Exception {
        return loansRepository.checkBorrow(user_id, book_id, status);
    }

    @Override
    public Boolean returnBook(int status_type, int loan_id) throws Exception {
        return loansRepository.returnBook(status_type, loan_id);
    }

    @Override
    public int checkDueDateAndReturnDate(int loan_id) throws Exception {
        return loansRepository.checkDueDateAndReturnDate(loan_id);
    }

}

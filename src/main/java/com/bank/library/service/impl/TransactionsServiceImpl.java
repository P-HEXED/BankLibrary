package com.bank.library.service.impl;

import com.bank.library.model.TransactionsModel;
import com.bank.library.repository.TransactionsRepository;
import com.bank.library.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionsServiceImpl implements TransactionsService {

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Override
    public Boolean addTransactions(TransactionsModel transactions) throws Exception {
        return transactionsRepository.addTransactions(transactions);
    }

}

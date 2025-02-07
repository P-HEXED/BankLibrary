package com.bank.library.service;

import com.bank.library.model.TransactionsModel;

public interface TransactionsService {

    Boolean addTransactions(TransactionsModel transactions) throws Exception;

}

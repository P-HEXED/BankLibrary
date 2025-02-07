package com.bank.library.repository;

import com.bank.library.model.TransactionsModel;

public interface TransactionsRepository {

    Boolean addTransactions(TransactionsModel transactions) throws Exception;

}

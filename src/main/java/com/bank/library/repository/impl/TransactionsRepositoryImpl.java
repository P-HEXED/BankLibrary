package com.bank.library.repository.impl;

import com.bank.library.model.TransactionsModel;
import com.bank.library.repository.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class TransactionsRepositoryImpl implements TransactionsRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Boolean addTransactions(TransactionsModel transactions) throws Exception {

        Boolean status = false;

        try {
            String sql = "INSERT INTO TRANSACTIONS (LOAN_ID, ACTION_TYPE) VALUES (?, ?)";

            int row = jdbcTemplate.update(sql, new PreparedStatementSetter() {
                public void setValues(PreparedStatement ps) throws SQLException {
                    int i = 1;
                    ps.setInt(i++, transactions.getLoanId());
                    ps.setInt(i++, transactions.getActionType());
                }
            });

            status = (row > 0)? true : false;

        } catch (Exception ex) {
            throw ex;
        }
        return status;
    }

}

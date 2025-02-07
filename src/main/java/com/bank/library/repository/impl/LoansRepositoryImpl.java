package com.bank.library.repository.impl;

import com.bank.library.model.LoansModel;
import com.bank.library.repository.LoansRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class LoansRepositoryImpl implements LoansRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Integer addLoans(LoansModel loans) throws Exception {

        Integer loanId = null;

        try {
            String sql = "INSERT INTO LOANS (USER_ID, BOOK_ID, LOAN_DATE, DUE_DATE, `STATUS`) VALUES (?, ?, ?, ?, ?)";

            jdbcTemplate.update(sql, new PreparedStatementSetter() {
                public void setValues(PreparedStatement ps) throws SQLException {
                    int i = 1;
                    ps.setInt(i++, loans.getUserId());
                    ps.setInt(i++, loans.getBookId());
                    ps.setString(i++, loans.getLoanDate());
                    ps.setString(i++, loans.getDueDate());
                    ps.setInt(i++, loans.getStatus());
                }
            });

            String getLoanIdSql = "SELECT LAST_INSERT_ID()";
            loanId = jdbcTemplate.queryForObject(getLoanIdSql, Integer.class);

        } catch (Exception ex) {
            throw ex;
        }

        return loanId;
    }

    @Override
    public int checkBorrow(int user_id, int book_id, int status) throws Exception {
        String sql = "SELECT LOAN_ID FROM LOANS WHERE USER_ID = ? AND BOOK_ID = ? AND `STATUS` = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{user_id, book_id, status}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    @Override
    public Boolean returnBook(int status_type, int loan_id) throws Exception {

        Boolean status = false;

        try {
            String sql = "UPDATE LOANS SET `STATUS` = ?, RETURN_DATE = CURRENT_DATE WHERE LOAN_ID = ?";

            int row = jdbcTemplate.update(sql, new PreparedStatementSetter() {
                public void setValues(PreparedStatement ps) throws SQLException {
                    int i = 1;
                    ps.setInt(i++, status_type);
                    ps.setInt(i++, loan_id);
                }
            });

            status = (row > 0)? true : false;

        } catch (Exception ex) {
            throw ex;
        }
        return status;
    }

    @Override
    public int checkDueDateAndReturnDate(int loan_id) throws Exception {
        String sql = "SELECT LOAN_ID FROM LOANS WHERE LOAN_ID = ? AND CURRENT_DATE > DUE_DATE";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{loan_id}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

}

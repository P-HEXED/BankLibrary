package com.bank.library.repository.impl;

import com.bank.library.model.AuthorModel;
import com.bank.library.model.CategoriesModel;
import com.bank.library.repository.AuthorRepository;
import com.bank.library.repository.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ArrayList<AuthorModel> getAuthorList() throws Exception {

        String sql = "SELECT * FROM `AUTHORS`";

        return (ArrayList<AuthorModel>) jdbcTemplate.query(sql, new RowMapper<AuthorModel>() {
            @Override
            public AuthorModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                AuthorModel item = new AuthorModel();

                item.setAuthor_id(rs.getInt("author_id"));
                item.setName(rs.getString("name"));
                item.setNationality(rs.getString("nationality"));

                return item;
            }
        });

    }

    @Override
    public Boolean addAuthor(AuthorModel author) throws Exception {

        Boolean status = false;

        try {
            String sql = "INSERT INTO `AUTHORS` (`NAME`, NATIONALITY) VALUES (?, ?)";

            int row = jdbcTemplate.update(sql, new PreparedStatementSetter() {
                public void setValues(PreparedStatement ps) throws SQLException {
                    int i = 1;
                    ps.setString(i++, author.getName());
                    ps.setString(i++, author.getNationality());
                }
            });

            status = (row > 0)? true : false;

        } catch (Exception ex) {
            throw ex;
        }
        return status;
    }

    @Override
    public Boolean editAuthor(AuthorModel author) throws Exception {

        Boolean status = false;

        try {
            String sql = "UPDATE `AUTHORS` SET `NAME` = ?, NATIONALITY = ? WHERE AUTHOR_ID = ?";

            int row = jdbcTemplate.update(sql, new PreparedStatementSetter() {
                public void setValues(PreparedStatement ps) throws SQLException {
                    int i = 1;
                    ps.setString(i++, author.getName());
                    ps.setString(i++, author.getNationality());
                    ps.setInt(i++, author.getAuthor_id());
                }
            });

            status = (row > 0)? true : false;

        } catch (Exception ex) {
            throw ex;
        }
        return status;
    }

    @Override
    public Boolean deleteAuthor(int author_id) throws Exception {

        Boolean status = false;

        try {
            String sql = "DELETE FROM `AUTHORS` WHERE AUTHOR_ID = ?";

            int row = jdbcTemplate.update(sql, new PreparedStatementSetter() {
                public void setValues(PreparedStatement ps) throws SQLException {
                    int i = 1;
                    ps.setInt(i++, author_id);

                }
            });

            status = (row > 0)? true : false;

        } catch (Exception ex) {
            throw ex;
        }
        return status;
    }

}

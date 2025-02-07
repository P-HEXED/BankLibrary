package com.bank.library.repository.impl;

import com.bank.library.model.CategoriesModel;
import com.bank.library.model.UserModel;
import com.bank.library.repository.CategoriesRepository;
import com.bank.library.repository.UserRepository;
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
public class CategoriesRepositoryImpl implements CategoriesRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ArrayList<CategoriesModel> getCategoriesList() throws Exception {

        String sql = "SELECT * FROM CATEGORIES";

        return (ArrayList<CategoriesModel>) jdbcTemplate.query(sql, new RowMapper<CategoriesModel>() {
            @Override
            public CategoriesModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                CategoriesModel item = new CategoriesModel();

                item.setCategoryId(rs.getInt("category_id"));
                item.setName(rs.getString("name"));
                item.setDescription(rs.getString("description"));

                return item;
            }
        });

    }

    @Override
    public Boolean addCategories(CategoriesModel categories) throws Exception {

        Boolean status = false;

        try {
            String sql = "INSERT INTO CATEGORIES (`NAME`, DESCRIPTION) VALUES (?, ?)";

            int row = jdbcTemplate.update(sql, new PreparedStatementSetter() {
                public void setValues(PreparedStatement ps) throws SQLException {
                    int i = 1;
                    ps.setString(i++, categories.getName());
                    ps.setString(i++, categories.getDescription());
                }
            });

            status = (row > 0)? true : false;

        } catch (Exception ex) {
            throw ex;
        }
        return status;
    }

    @Override
    public Boolean editCategories(CategoriesModel categories) throws Exception {

        Boolean status = false;

        try {
            String sql = "UPDATE CATEGORIES SET `NAME` = ?, DESCRIPTION = ? WHERE CATEGORY_ID = ?";

            int row = jdbcTemplate.update(sql, new PreparedStatementSetter() {
                public void setValues(PreparedStatement ps) throws SQLException {
                    int i = 1;
                    ps.setString(i++, categories.getName());
                    ps.setString(i++, categories.getDescription());
                    ps.setInt(i++, categories.getCategoryId());
                }
            });

            status = (row > 0)? true : false;

        } catch (Exception ex) {
            throw ex;
        }
        return status;
    }

    @Override
    public Boolean deleteCategories(int category_id) throws Exception {

        Boolean status = false;

        try {
            String sql = "DELETE FROM CATEGORIES WHERE CATEGORY_ID = ?";

            int row = jdbcTemplate.update(sql, new PreparedStatementSetter() {
                public void setValues(PreparedStatement ps) throws SQLException {
                    int i = 1;
                    ps.setInt(i++, category_id);

                }
            });

            status = (row > 0)? true : false;

        } catch (Exception ex) {
            throw ex;
        }
        return status;
    }

    @Override
    public int checkCategoriesIsExist(String name) throws Exception {
        String sql = "SELECT CATEGORY_ID FROM CATEGORIES WHERE `NAME` = ? LIMIT 1";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{name}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

}

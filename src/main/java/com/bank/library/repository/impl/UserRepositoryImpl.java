package com.bank.library.repository.impl;

import com.bank.library.model.UserModel;
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
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ArrayList<UserModel> getUserList() throws Exception {

        String sql = "SELECT * FROM USERS";

        return (ArrayList<UserModel>) jdbcTemplate.query(sql, new RowMapper<UserModel>() {
            @Override
            public UserModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                UserModel item = new UserModel();

                item.setUserId(rs.getInt("user_id"));
                item.setFirstname(rs.getString("firstname"));
                item.setLastname(rs.getString("lastname"));
                item.setPhone(rs.getString("phone"));
                item.setEmail(rs.getString("email"));
                item.setRole(rs.getInt("role"));
                item.setCreatedAt(rs.getString("created_at"));


                return item;
            }
        });

    }

    @Override
    public Boolean addUser(UserModel user) throws Exception {

        Boolean status = false;

        try {
            String sql = "INSERT INTO USERS (FIRSTNAME, LASTNAME, PHONE, EMAIL, ROLE) VALUES (?, ?, ?, ?, ?)";

            int row = jdbcTemplate.update(sql, new PreparedStatementSetter() {
                public void setValues(PreparedStatement ps) throws SQLException {
                    int i = 1;
                    ps.setString(i++, user.getFirstname());
                    ps.setString(i++, user.getLastname());
                    ps.setString(i++, user.getPhone());
                    ps.setString(i++, user.getEmail());
                    ps.setInt(i++, user.getRole());
                }
            });

            status = (row > 0)? true : false;

        } catch (Exception ex) {
            throw ex;
        }
        return status;
    }

    @Override
    public Boolean editUser(UserModel user) throws Exception {

        Boolean status = false;

        try {
            String sql = "UPDATE USERS SET FIRSTNAME = ?, LASTNAME = ?, PHONE = ?, EMAIL = ?, ROLE = ? WHERE USER_ID = ?";

            int row = jdbcTemplate.update(sql, new PreparedStatementSetter() {
                public void setValues(PreparedStatement ps) throws SQLException {
                    int i = 1;
                    ps.setString(i++, user.getFirstname());
                    ps.setString(i++, user.getLastname());
                    ps.setString(i++, user.getPhone());
                    ps.setString(i++, user.getEmail());
                    ps.setInt(i++, user.getRole());
                    ps.setInt(i++, user.getUserId());
                }
            });

            status = (row > 0)? true : false;

        } catch (Exception ex) {
            throw ex;
        }
        return status;
    }

    @Override
    public Boolean deleteUser(int user_id) throws Exception {

        Boolean status = false;

        try {
            String sql = "DELETE FROM USERS WHERE USER_ID = ?";

            int row = jdbcTemplate.update(sql, new PreparedStatementSetter() {
                public void setValues(PreparedStatement ps) throws SQLException {
                    int i = 1;
                    ps.setInt(i++, user_id);

                }
            });

            status = (row > 0)? true : false;

        } catch (Exception ex) {
            throw ex;
        }
        return status;
    }

    @Override
    public int checkUserIsExist(String firstname, String lastname, String email) throws Exception {
        String sql = "SELECT USER_ID FROM USERS WHERE FIRSTNAME = ? OR LASTNAME = ? OR EMAIL = ? LIMIT 1";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{firstname, lastname, email}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    @Override
    public Boolean selfEdit(UserModel user) throws Exception {

        Boolean status = false;

        try {
            String sql = "UPDATE USERS SET FIRSTNAME = ?, LASTNAME = ?, PHONE = ?, EMAIL = ? WHERE USER_ID = ?";

            int row = jdbcTemplate.update(sql, new PreparedStatementSetter() {
                public void setValues(PreparedStatement ps) throws SQLException {
                    int i = 1;
                    ps.setString(i++, user.getFirstname());
                    ps.setString(i++, user.getLastname());
                    ps.setString(i++, user.getPhone());
                    ps.setString(i++, user.getEmail());
                    ps.setInt(i++, user.getUserId());
                }
            });

            status = (row > 0)? true : false;

        } catch (Exception ex) {
            throw ex;
        }
        return status;
    }

    @Override
    public Boolean selfAdd(UserModel user) throws Exception {

        Boolean status = false;

        try {
            String sql = "INSERT INTO USERS (FIRSTNAME, LASTNAME, PHONE, EMAIL) VALUES (?, ?, ?, ?)";

            int row = jdbcTemplate.update(sql, new PreparedStatementSetter() {
                public void setValues(PreparedStatement ps) throws SQLException {
                    int i = 1;
                    ps.setString(i++, user.getFirstname());
                    ps.setString(i++, user.getLastname());
                    ps.setString(i++, user.getPhone());
                    ps.setString(i++, user.getEmail());
                }
            });

            status = (row > 0)? true : false;

        } catch (Exception ex) {
            throw ex;
        }
        return status;
    }

}

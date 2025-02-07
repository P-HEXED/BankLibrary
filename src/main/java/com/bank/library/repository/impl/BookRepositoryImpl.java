package com.bank.library.repository.impl;

import com.bank.library.model.BookModel;
import com.bank.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

@Repository
public class BookRepositoryImpl implements BookRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ArrayList<BookModel> getBookList() throws Exception {

        String sql = "SELECT * FROM BOOKS";

        return (ArrayList<BookModel>) jdbcTemplate.query(sql, new RowMapper<BookModel>() {
            @Override
            public BookModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                BookModel item = new BookModel();

                item.setBookId(rs.getInt("book_id"));
                item.setTitle(rs.getString("title"));
                item.setCategoryId(rs.getInt("category_id"));
                item.setTotalCopies(rs.getInt("total_copies"));
                item.setAvailableCopies(rs.getInt("available_copies"));
                item.setPublishedYear(rs.getInt("published_year"));
                item.setIsbn(rs.getString("isbn"));
                item.setCreatedAt(rs.getString("created_at"));

                return item;
            }
        });

    }

    @Override
    public ArrayList<BookModel> getBookByTitle(String title) throws Exception {

        String sql = "SELECT * FROM BOOKS WHERE TITLE LIKE ?";

        title = "%"+title+"%";

        return (ArrayList<BookModel>) jdbcTemplate.query(sql, new Object[]{title}, new RowMapper<BookModel>() {
            @Override
            public BookModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                BookModel item = new BookModel();

                item.setBookId(rs.getInt("book_id"));
                item.setTitle(rs.getString("title"));
                item.setCategoryId(rs.getInt("category_id"));
                item.setTotalCopies(rs.getInt("total_copies"));
                item.setAvailableCopies(rs.getInt("available_copies"));
                item.setPublishedYear(rs.getInt("published_year"));
                item.setIsbn(rs.getString("isbn"));
                item.setCreatedAt(rs.getString("created_at"));

                return item;
            }
        });

    }

    @Override
    public ArrayList<BookModel> getBookByAuthor(String author) throws Exception {

        String sql = "SELECT\n" +
                    "  BOOKS.*\n" +
                    "FROM BOOK_AUTHORS\n" +
                    "INNER JOIN `AUTHORS` ON BOOK_AUTHORS.AUTHOR_ID = `AUTHORS`.AUTHOR_ID\n" +
                    "INNER JOIN BOOKS ON BOOK_AUTHORS.BOOK_ID = BOOKS.BOOK_ID\n" +
                    "WHERE `AUTHORS`.`NAME` LIKE ?";

        author = "%"+author+"%";

        return (ArrayList<BookModel>) jdbcTemplate.query(sql, new Object[]{author}, new RowMapper<BookModel>() {
            @Override
            public BookModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                BookModel item = new BookModel();

                item.setBookId(rs.getInt("book_id"));
                item.setTitle(rs.getString("title"));
                item.setCategoryId(rs.getInt("category_id"));
                item.setTotalCopies(rs.getInt("total_copies"));
                item.setAvailableCopies(rs.getInt("available_copies"));
                item.setPublishedYear(rs.getInt("published_year"));
                item.setIsbn(rs.getString("isbn"));
                item.setCreatedAt(rs.getString("created_at"));

                return item;
            }
        });

    }

    @Override
    public ArrayList<BookModel> getBookByCategory(int category_id) throws Exception {

        String sql = "SELECT * FROM BOOKS WHERE CATEGORY_ID = ?";

        return (ArrayList<BookModel>) jdbcTemplate.query(sql, new Object[]{category_id}, new RowMapper<BookModel>() {
            @Override
            public BookModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                BookModel item = new BookModel();

                item.setBookId(rs.getInt("book_id"));
                item.setTitle(rs.getString("title"));
                item.setCategoryId(rs.getInt("category_id"));
                item.setTotalCopies(rs.getInt("total_copies"));
                item.setAvailableCopies(rs.getInt("available_copies"));
                item.setPublishedYear(rs.getInt("published_year"));
                item.setIsbn(rs.getString("isbn"));
                item.setCreatedAt(rs.getString("created_at"));

                return item;
            }
        });

    }

    @Override
    public ArrayList<BookModel> getBookByPublishedYear(int published_year) throws Exception {

        String sql = "SELECT * FROM BOOKS WHERE PUBLISHED_YEAR = ?";

        return (ArrayList<BookModel>) jdbcTemplate.query(sql, new Object[]{published_year}, new RowMapper<BookModel>() {
            @Override
            public BookModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                BookModel item = new BookModel();

                item.setBookId(rs.getInt("book_id"));
                item.setTitle(rs.getString("title"));
                item.setCategoryId(rs.getInt("category_id"));
                item.setTotalCopies(rs.getInt("total_copies"));
                item.setAvailableCopies(rs.getInt("available_copies"));
                item.setPublishedYear(rs.getInt("published_year"));
                item.setIsbn(rs.getString("isbn"));
                item.setCreatedAt(rs.getString("created_at"));

                return item;
            }
        });

    }

    @Override
    public ArrayList<BookModel> getBookByISBN(String isbn) throws Exception {

        String sql = "SELECT * FROM BOOKS WHERE ISBN LIKE ?";

        isbn = "%"+isbn+"%";

        return (ArrayList<BookModel>) jdbcTemplate.query(sql, new Object[]{isbn}, new RowMapper<BookModel>() {
            @Override
            public BookModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                BookModel item = new BookModel();

                item.setBookId(rs.getInt("book_id"));
                item.setTitle(rs.getString("title"));
                item.setCategoryId(rs.getInt("category_id"));
                item.setTotalCopies(rs.getInt("total_copies"));
                item.setAvailableCopies(rs.getInt("available_copies"));
                item.setPublishedYear(rs.getInt("published_year"));
                item.setIsbn(rs.getString("isbn"));
                item.setCreatedAt(rs.getString("created_at"));

                return item;
            }
        });

    }

    @Override
    public Boolean addBook(BookModel book) throws Exception {

        Boolean status = false;

        try {
            String sql = "INSERT INTO BOOKS(TITLE, CATEGORY_ID, TOTAL_COPIES, AVAILABLE_COPIES, PUBLISHED_YEAR, ISBN) VALUES(?, ?, ?, ?, ?, ?)";

            int row = jdbcTemplate.update(sql, new PreparedStatementSetter() {
                public void setValues(PreparedStatement ps) throws SQLException {
                    int i = 1;
                    ps.setString(i++, book.getTitle());
                    ps.setInt(i++, book.getCategoryId());
                    ps.setInt(i++, book.getTotalCopies());
                    ps.setInt(i++, book.getAvailableCopies());
                    ps.setInt(i++, book.getPublishedYear());
                    ps.setString(i++, book.getIsbn());
                }
            });

            status = (row > 0)? true : false;

        } catch (Exception ex) {
            throw ex;
        }
        return status;
    }

    @Override
    public int checkISBNIsExist(String isbn) throws Exception {
        String sql = "SELECT BOOK_ID FROM BOOKS WHERE ISBN = ? LIMIT 1";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{isbn}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    @Override
    public Boolean editBook(BookModel book) throws Exception {

        Boolean status = false;

        try {
            String sql = "UPDATE BOOKS SET TITLE = ?, CATEGORY_ID = ?, TOTAL_COPIES = ?, AVAILABLE_COPIES = ?, PUBLISHED_YEAR = ?, ISBN = ? WHERE BOOK_ID = ?";

            int row = jdbcTemplate.update(sql, new PreparedStatementSetter() {
                public void setValues(PreparedStatement ps) throws SQLException {
                    int i = 1;
                    ps.setString(i++, book.getTitle());
                    ps.setInt(i++, book.getCategoryId());
                    ps.setInt(i++, book.getTotalCopies());
                    ps.setInt(i++, book.getAvailableCopies());
                    ps.setInt(i++, book.getPublishedYear());
                    ps.setString(i++, book.getIsbn());
                    ps.setInt(i++, book.getBookId());
                }
            });

            status = (row > 0)? true : false;

        } catch (Exception ex) {
            throw ex;
        }
        return status;
    }

    @Override
    public Boolean deleteBook(int book_id) throws Exception {

        Boolean status = false;

        try {
            String sql = "DELETE FROM BOOKS WHERE BOOK_ID = ?";

            int row = jdbcTemplate.update(sql, new PreparedStatementSetter() {
                public void setValues(PreparedStatement ps) throws SQLException {
                    int i = 1;
                    ps.setInt(i++, book_id);

                }
            });

            status = (row > 0)? true : false;

        } catch (Exception ex) {
            throw ex;
        }
        return status;
    }

    @Override
    public int checkAvailableCopies(int book_id) throws Exception {
        String sql = "SELECT AVAILABLE_COPIES FROM BOOKS WHERE BOOK_ID = ? LIMIT 1";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{book_id}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    @Override
    public Boolean editBorrowAvailableCopies(int book_id) throws Exception {

        Boolean status = false;

        try {
            String sql = "UPDATE BOOKS SET AVAILABLE_COPIES = AVAILABLE_COPIES - 1 WHERE BOOK_ID = ?";

            int row = jdbcTemplate.update(sql, new PreparedStatementSetter() {
                public void setValues(PreparedStatement ps) throws SQLException {
                    int i = 1;
                    ps.setInt(i++, book_id);
                }
            });

            status = (row > 0)? true : false;

        } catch (Exception ex) {
            throw ex;
        }
        return status;
    }

    @Override
    public Boolean editReturnAvailableCopies(int book_id) throws Exception {

        Boolean status = false;

        try {
            String sql = "UPDATE BOOKS SET AVAILABLE_COPIES = AVAILABLE_COPIES + 1 WHERE BOOK_ID = ?";

            int row = jdbcTemplate.update(sql, new PreparedStatementSetter() {
                public void setValues(PreparedStatement ps) throws SQLException {
                    int i = 1;
                    ps.setInt(i++, book_id);
                }
            });

            status = (row > 0)? true : false;

        } catch (Exception ex) {
            throw ex;
        }
        return status;
    }

    @Override
    public Boolean addBookAndAuthor(int book_id, int author_id) throws Exception {

        Boolean status = false;

        try {
            String sql = "INSERT INTO BOOK_AUTHORS (BOOK_ID, AUTHOR_ID) VALUES (?, ?)";

            int row = jdbcTemplate.update(sql, new PreparedStatementSetter() {
                public void setValues(PreparedStatement ps) throws SQLException {
                    int i = 1;
                    ps.setInt(i++, book_id);
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

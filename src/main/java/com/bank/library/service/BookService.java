package com.bank.library.service;

import com.bank.library.model.BookModel;

import java.util.ArrayList;

public interface BookService {

    ArrayList<BookModel> getBookList() throws Exception;
    ArrayList<BookModel> getBookByTitle(String title) throws Exception;
    ArrayList<BookModel> getBookByAuthor(String author) throws Exception;
    ArrayList<BookModel> getBookByCategory(int category_id) throws Exception;
    ArrayList<BookModel> getBookByPublishedYear(int published_year) throws Exception;
    ArrayList<BookModel> getBookByISBN(String isbn) throws Exception;
    Boolean addBook(BookModel book) throws Exception;
    int checkISBNIsExist(String isbn) throws Exception;
    Boolean editBook(BookModel book) throws Exception;
    Boolean deleteBook(int book_id) throws Exception;
    int checkAvailableCopies(int book_id) throws Exception;
    Boolean editBorrowAvailableCopies(int book_id) throws Exception;
    Boolean editReturnAvailableCopies(int book_id) throws Exception;
    Boolean addBookAndAuthor(int book_id, int author_id) throws Exception;

}

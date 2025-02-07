package com.bank.library.service.impl;

import com.bank.library.model.BookModel;
import com.bank.library.repository.BookRepository;
import com.bank.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public ArrayList<BookModel> getBookList() throws Exception {
        return bookRepository.getBookList();
    }

    @Override
    public ArrayList<BookModel> getBookByTitle(String title) throws Exception {
        return bookRepository.getBookByTitle(title);
    }

    @Override
    public ArrayList<BookModel> getBookByAuthor(String author) throws Exception {
        return bookRepository.getBookByAuthor(author);
    }

    @Override
    public ArrayList<BookModel> getBookByCategory(int category_id) throws Exception {
        return bookRepository.getBookByCategory(category_id);
    }

    @Override
    public ArrayList<BookModel> getBookByPublishedYear(int published_year) throws Exception {
        return bookRepository.getBookByPublishedYear(published_year);
    }

    @Override
    public ArrayList<BookModel> getBookByISBN(String isbn) throws Exception {
        return bookRepository.getBookByISBN(isbn);
    }

    @Override
    public Boolean addBook(BookModel book) throws Exception {
        return bookRepository.addBook(book);
    }

    @Override
    public int checkISBNIsExist(String isbn) throws Exception {
        return bookRepository.checkISBNIsExist(isbn);
    }

    @Override
    public Boolean editBook(BookModel book) throws Exception {
        return bookRepository.editBook(book);
    }

    @Override
    public Boolean deleteBook(int book_id) throws Exception {
        return bookRepository.deleteBook(book_id);
    }

    @Override
    public int checkAvailableCopies(int book_id) throws Exception {
        return bookRepository.checkAvailableCopies(book_id);
    }

    @Override
    public Boolean editBorrowAvailableCopies(int book_id) throws Exception {
        return bookRepository.editBorrowAvailableCopies(book_id);
    }

    @Override
    public Boolean editReturnAvailableCopies(int book_id) throws Exception {
        return bookRepository.editReturnAvailableCopies(book_id);
    }

    @Override
    public Boolean addBookAndAuthor(int book_id, int author_id) throws Exception {
        return bookRepository.addBookAndAuthor(book_id, author_id);
    }
}

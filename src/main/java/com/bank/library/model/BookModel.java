package com.bank.library.model;

import lombok.Data;

@Data
public class BookModel {
    private int bookId;
    private String title;
    private int categoryId;
    private int totalCopies;
    private int availableCopies;
    private int publishedYear;
    private String isbn;
    private String createdAt;
}
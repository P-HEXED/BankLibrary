package com.bank.library.repository;

import com.bank.library.model.AuthorModel;

import java.util.ArrayList;

public interface AuthorRepository {

    ArrayList<AuthorModel> getAuthorList() throws Exception;
    Boolean addAuthor(AuthorModel author) throws Exception;
    Boolean editAuthor(AuthorModel author) throws Exception;
    Boolean deleteAuthor(int author_id) throws Exception;

}

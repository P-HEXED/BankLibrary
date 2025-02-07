package com.bank.library.service.impl;

import com.bank.library.model.AuthorModel;
import com.bank.library.repository.AuthorRepository;
import com.bank.library.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public ArrayList<AuthorModel> getAuthorList() throws Exception {
        return authorRepository.getAuthorList();
    }

    @Override
    public Boolean addAuthor(AuthorModel author) throws Exception {
        return authorRepository.addAuthor(author);
    }

    @Override
    public Boolean editAuthor(AuthorModel author) throws Exception {
        return authorRepository.editAuthor(author);
    }

    @Override
    public Boolean deleteAuthor(int author_id) throws Exception {
        return authorRepository.deleteAuthor(author_id);
    }

}

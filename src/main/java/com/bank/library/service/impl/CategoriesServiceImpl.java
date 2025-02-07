package com.bank.library.service.impl;

import com.bank.library.model.CategoriesModel;
import com.bank.library.model.UserModel;
import com.bank.library.repository.CategoriesRepository;
import com.bank.library.service.CategoriesService;
import com.bank.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CategoriesServiceImpl implements CategoriesService {

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Override
    public ArrayList<CategoriesModel> getCategoriesList() throws Exception {
        return categoriesRepository.getCategoriesList();
    }

    @Override
    public Boolean addCategories(CategoriesModel categories) throws Exception {
        return categoriesRepository.addCategories(categories);
    }

    @Override
    public Boolean editCategories(CategoriesModel categories) throws Exception {
        return categoriesRepository.editCategories(categories);
    }

    @Override
    public Boolean deleteCategories(int category_id) throws Exception {
        return categoriesRepository.deleteCategories(category_id);
    }

    @Override
    public int checkCategoriesIsExist(String name) throws Exception {
        return categoriesRepository.checkCategoriesIsExist(name);
    }

}

package com.bank.library.service;

import com.bank.library.model.CategoriesModel;

import java.util.ArrayList;

public interface CategoriesService {
    ArrayList<CategoriesModel> getCategoriesList() throws Exception;
    Boolean addCategories(CategoriesModel categories) throws Exception;
    Boolean editCategories(CategoriesModel categories) throws Exception;
    Boolean deleteCategories(int category_id) throws Exception;
    int checkCategoriesIsExist(String name) throws Exception;

}

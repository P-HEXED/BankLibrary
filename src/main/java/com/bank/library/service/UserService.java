package com.bank.library.service;

import com.bank.library.model.UserModel;

import java.util.ArrayList;

public interface UserService {
    ArrayList<UserModel> getUserList() throws Exception;
    Boolean addUser(UserModel user) throws Exception;
    Boolean editUser(UserModel user) throws Exception;
    Boolean deleteUser(int user_id) throws Exception;
    int checkUserIsExist(String firstname, String lastname, String email) throws Exception;
    Boolean selfEdit(UserModel user) throws Exception;
    Boolean selfAdd(UserModel user) throws Exception;

}

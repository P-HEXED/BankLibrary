package com.bank.library.service.impl;

import com.bank.library.model.UserModel;
import com.bank.library.repository.UserRepository;
import com.bank.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public ArrayList<UserModel> getUserList() throws Exception {
        return userRepository.getUserList();
    }

    @Override
    public Boolean addUser(UserModel user) throws Exception {
        return userRepository.addUser(user);
    }

    @Override
    public Boolean editUser(UserModel user) throws Exception {
        return userRepository.editUser(user);
    }

    @Override
    public Boolean deleteUser(int user_id) throws Exception {
        return userRepository.deleteUser(user_id);
    }

    @Override
    public int checkUserIsExist(String firstname, String lastname, String email) throws Exception {
        return userRepository.checkUserIsExist(firstname, lastname, email);
    }

    @Override
    public Boolean selfEdit(UserModel user) throws Exception {
        return userRepository.selfEdit(user);
    }

    @Override
    public Boolean selfAdd(UserModel user) throws Exception {
        return userRepository.selfAdd(user);
    }
}

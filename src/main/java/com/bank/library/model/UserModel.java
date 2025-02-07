package com.bank.library.model;

import lombok.Data;

@Data
public class UserModel {
    private int userId;
    private String firstname;
    private String lastname;
    private String phone;
    private String email;
    private int role;
    private String createdAt;
}
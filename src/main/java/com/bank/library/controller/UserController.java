package com.bank.library.controller;

import com.bank.library.model.UserModel;
import com.bank.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    @Lazy
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<?> getUserList() throws Exception {
        Map<String, Object> response = new LinkedHashMap<>();
        try {
            response.put("userList", userService.getUserList());
        } catch (Exception ex) {
            throw ex;
        }
        return ResponseEntity.ok(response);
    }

    //For user role 1 or 2
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody UserModel user) throws Exception {
        Map<String, Object> response = new LinkedHashMap<>();
        try {
            String errMsg = validateDataRequired(user, 1);
            if(!errMsg.isEmpty()) {
                return ResponseEntity.status(400).body("Bad Request: Missing required parameters.\n\n"+errMsg);
            }

            if(userService.checkUserIsExist(user.getFirstname(), user.getLastname(), user.getEmail()) > 0) {
                return ResponseEntity.status(409).body("Bad Request: Some data is already exist.");
            }

            if(userService.addUser(user)) {
                response.put("msg", "Data added successfully.");
                return ResponseEntity.status(201).body(response);
            }

            return ResponseEntity.status(500).body("Server Error: Unable to add user.");

        } catch (Exception ex) {
            throw ex;
        }
    }

    //For user role 3
    @PostMapping("/self/add")
    public ResponseEntity<?> selfAdd(@RequestBody UserModel user) throws Exception {
        Map<String, Object> response = new LinkedHashMap<>();
        try {
            String errMsg = validateDataRequired(user, 2);
            if(!errMsg.isEmpty()) {
                return ResponseEntity.status(400).body("Bad Request: Missing required parameters.\n\n"+errMsg);
            }

            if(userService.checkUserIsExist(user.getFirstname(), user.getLastname(), user.getEmail()) > 0) {
                return ResponseEntity.status(409).body("Bad Request: Some data is already exist.");
            }

            if(userService.selfAdd(user)) {
                response.put("msg", "Data added successfully.");
                return ResponseEntity.status(201).body(response);
            }

            return ResponseEntity.status(500).body("Server Error: Unable to add user.");

        } catch (Exception ex) {
            throw ex;
        }
    }


    //For user role 1 or 2
    @PutMapping("/edit/{user_id}")
    public ResponseEntity<?> edit(@PathVariable int user_id, @RequestBody UserModel user) throws Exception {
        Map<String, Object> response = new LinkedHashMap<>();
        try {
            String errMsg = validateDataRequired(user, 1);
            if (!errMsg.isEmpty()) {
                return ResponseEntity.status(400).body("Bad Request: Missing required parameters.\n\n" + errMsg);
            }

            int userId = userService.checkUserIsExist(user.getFirstname(), user.getLastname(), user.getEmail());
            if(userId > 0 && userId != user_id) {
                return ResponseEntity.status(409).body("Bad Request: Some data is already exist.");
            }

            user.setUserId(user_id);

            if(userService.editUser(user)) {
                response.put("msg", "Data edited successfully.");
                return ResponseEntity.ok(response);
            }

            return ResponseEntity.status(500).body("Server Error: Unable to update user.");

        } catch (Exception ex) {
            throw ex;
        }
    }

    //For user role 3
    @PutMapping("/self/edit/{user_id}")
    public ResponseEntity<?> selfEdit(@PathVariable int user_id, @RequestBody UserModel user) throws Exception {
        Map<String, Object> response = new LinkedHashMap<>();
        try {
            String errMsg = validateDataRequired(user, 2);
            if (!errMsg.isEmpty()) {
                return ResponseEntity.status(400).body("Bad Request: Missing required parameters.\n\n" + errMsg);
            }

            int userId = userService.checkUserIsExist(user.getFirstname(), user.getLastname(), user.getEmail());
            if(userId > 0 && userId != user_id) {
                return ResponseEntity.status(409).body("Bad Request: Some data is already exist.");
            }

            user.setUserId(user_id);

            if(userService.selfEdit(user)) {
                response.put("msg", "Data edited successfully.");
                return ResponseEntity.ok(response);
            }

            return ResponseEntity.status(500).body("Server Error: Unable to update user.");

        } catch (Exception ex) {
            throw ex;
        }
    }

    @DeleteMapping("/delete/{user_id}")
    public ResponseEntity<?> delete(@PathVariable int user_id) throws Exception {
        Map<String, Object> response = new LinkedHashMap<>();
        try {
            if (userService.deleteUser(user_id)) {
                response.put("msg", "Data deleted successfully.");
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(404).body("User not found.");
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    public String validateDataRequired(UserModel user, int roleCondition) {
        String errMsg = "";
        if(user.getFirstname() == null || user.getFirstname().isEmpty()) {
            errMsg += "- firstname\n";
        }

        if(user.getLastname() == null || user.getLastname().isEmpty()) {
            errMsg += "- lastname\n";
        }

        if(user.getPhone() == null || user.getPhone().isEmpty()) {
            errMsg += "- phone\n";
        }

        if(user.getEmail() == null || user.getEmail().isEmpty()) {
            errMsg += "- email\n";
        }

        if(roleCondition == 1) {
            if((Integer) user.getRole() == null || user.getRole() < 0) {
                errMsg += "- role";
            }
        }

        return errMsg;
    }

}

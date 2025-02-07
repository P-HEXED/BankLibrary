package com.bank.library.controller;

import com.bank.library.model.AuthorModel;
import com.bank.library.model.CategoriesModel;
import com.bank.library.service.AuthorService;
import com.bank.library.service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/author")
public class AuthorController {

    @Autowired
    @Lazy
    private AuthorService authorService;

    @GetMapping("/")
    public ResponseEntity<?> getAuthorList() throws Exception {
        Map<String, Object> response = new LinkedHashMap<>();
        try {
            response.put("authorList", authorService.getAuthorList());
        } catch (Exception ex) {
            throw ex;
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody AuthorModel author) throws Exception {
        Map<String, Object> response = new LinkedHashMap<>();
        try {
            String errMsg = validateDataRequired(author);
            if(!errMsg.isEmpty()) {
                return ResponseEntity.status(400).body("Bad Request: Missing required parameters.\n\n"+errMsg);
            }

            if(authorService.addAuthor(author)) {
                response.put("msg", "Data added successfully.");
                return ResponseEntity.status(201).body(response);
            }

            return ResponseEntity.status(500).body("Server Error: Unable to add author.");

        } catch (Exception ex) {
            throw ex;
        }
    }

    @PutMapping("/edit/{author_id}")
    public ResponseEntity<?> edit(@PathVariable int author_id, @RequestBody AuthorModel author) throws Exception {
        Map<String, Object> response = new LinkedHashMap<>();
        try {
            String errMsg = validateDataRequired(author);
            if (!errMsg.isEmpty()) {
                return ResponseEntity.status(400).body("Bad Request: Missing required parameters.\n\n" + errMsg);
            }

            author.setAuthor_id(author_id);

            if (authorService.editAuthor(author)) {
                response.put("msg", "Data edited successfully.");
                return ResponseEntity.ok(response);
            }

            return ResponseEntity.status(500).body("Server Error: Unable to update author.");

        } catch (Exception ex) {
            throw ex;
        }
    }

    @DeleteMapping("/delete/{author_id}")
    public ResponseEntity<?> delete(@PathVariable int author_id) throws Exception {
        Map<String, Object> response = new LinkedHashMap<>();
        try {
            if (authorService.deleteAuthor(author_id)) {
                response.put("msg", "Data deleted successfully.");
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(404).body("Author not found.");
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    public String validateDataRequired(AuthorModel author) {
        String errMsg = "";
        if(author.getName() == null || author.getName().isEmpty()) {
            errMsg += "- name\n";
        }

        return errMsg;
    }

}

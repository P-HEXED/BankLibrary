package com.bank.library.controller;

import com.bank.library.model.CategoriesModel;
import com.bank.library.service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
public class CategoriesController {

    @Autowired
    @Lazy
    private CategoriesService categoriesService;

    @GetMapping("/")
    public ResponseEntity<?> getCategoriesList() throws Exception {
        Map<String, Object> response = new LinkedHashMap<>();
        try {
            response.put("categoriesList", categoriesService.getCategoriesList());
        } catch (Exception ex) {
            throw ex;
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody CategoriesModel categories) throws Exception {
        Map<String, Object> response = new LinkedHashMap<>();
        try {
            String errMsg = validateDataRequired(categories);
            if(!errMsg.isEmpty()) {
                return ResponseEntity.status(400).body("Bad Request: Missing required parameters.\n\n"+errMsg);
            }

            if(categoriesService.checkCategoriesIsExist(categories.getName()) > 0) {
                return ResponseEntity.status(409).body("Bad Request: Categories is already exist.");
            }

            if(categoriesService.addCategories(categories)) {
                response.put("msg", "Data added successfully.");
                return ResponseEntity.status(201).body(response);
            }

            return ResponseEntity.status(500).body("Server Error: Unable to add categories.");

        } catch (Exception ex) {
            throw ex;
        }
    }

    @PutMapping("/edit/{category_id}")
    public ResponseEntity<?> edit(@PathVariable int category_id, @RequestBody CategoriesModel categories) throws Exception {
        Map<String, Object> response = new LinkedHashMap<>();
        try {
            String errMsg = validateDataRequired(categories);
            if (!errMsg.isEmpty()) {
                return ResponseEntity.status(400).body("Bad Request: Missing required parameters.\n\n" + errMsg);
            }

            int categoryId = categoriesService.checkCategoriesIsExist(categories.getName());
            if (categoryId > 0 && categoryId != category_id) {
                return ResponseEntity.status(409).body("Bad Request: Categories already exists.");
            }

            categories.setCategoryId(category_id);

            if (categoriesService.editCategories(categories)) {
                response.put("msg", "Data edited successfully.");
                return ResponseEntity.ok(response);
            }

            return ResponseEntity.status(500).body("Server Error: Unable to update categories.");

        } catch (Exception ex) {
            throw ex;
        }
    }

    @DeleteMapping("/delete/{category_id}")
    public ResponseEntity<?> delete(@PathVariable int category_id) throws Exception {
        Map<String, Object> response = new LinkedHashMap<>();
        try {
            if (categoriesService.deleteCategories(category_id)) {
                response.put("msg", "Data deleted successfully.");
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(404).body("Categories not found.");
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    public String validateDataRequired(CategoriesModel categories) {
        String errMsg = "";
        if(categories.getName() == null || categories.getName().isEmpty()) {
            errMsg += "- name\n";
        }

        return errMsg;
    }

}

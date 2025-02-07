package com.bank.library.controller;

import com.bank.library.model.LoansModel;
import com.bank.library.model.TransactionsModel;
import com.bank.library.service.BookService;
import com.bank.library.service.LoansService;
import com.bank.library.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/borrow")
public class BorrowController {

    @Autowired
    @Lazy
    private BookService bookService;

    @Autowired
    @Lazy
    private LoansService loansService;

    @Autowired
    @Lazy
    private TransactionsService transactionsService;

    //ยืมหนังสือ
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody LoansModel loans) throws Exception {
        Map<String, Object> response = new LinkedHashMap<>();
        try {
            String errMsg = validateDataRequired(loans);
            if(!errMsg.isEmpty()) {
                return ResponseEntity.status(400).body("Bad Request: Missing required parameters.\n\n"+errMsg);
            }
//            ตรวจสอบว่ามีจำนวนหนังสือเพียงพอหรือไม่
            if(bookService.checkAvailableCopies(loans.getBookId()) <= 0) {
                return ResponseEntity.status(409).body("Bad Request: There are not enough books.");
            }

//            บันทึกข้อมูลการยืม
            Integer loanId = loansService.addLoans(loans);
            if(loanId != null && loanId > 0) {
//                แก้ไขจำนวนหนังสือที่สามารถยืมได้ -1
                if(bookService.editBorrowAvailableCopies(loans.getBookId())) {
//                    บันทึกข้อมูลลง Transactions
                    TransactionsModel transactions = new TransactionsModel();
                    transactions.setLoanId(loanId);
                    transactions.setActionType(1);
                    if(transactionsService.addTransactions(transactions)) {
                        response.put("msg", "Borrow successfully.");
                        return ResponseEntity.status(201).body(response);
                    }
                }
            }

            return ResponseEntity.status(500).body("Server Error: Unable to add borrow.");

        } catch (Exception ex) {
            throw ex;
        }
    }

    public String validateDataRequired(LoansModel loans) {
        String errMsg = "";
        if(loans.getLoanDate() == null || loans.getLoanDate().isEmpty()) {
            errMsg += "- loanDate\n";
        }

        if(loans.getDueDate() == null || loans.getDueDate().isEmpty()) {
            errMsg += "- dueDate\n";
        }

        return errMsg;
    }

}

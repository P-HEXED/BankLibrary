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
@RequestMapping("/api/return")
public class ReturnController {

    @Autowired
    @Lazy
    private BookService bookService;

    @Autowired
    @Lazy
    private LoansService loansService;

    @Autowired
    @Lazy
    private TransactionsService transactionsService;

    //คืนหนังสือ
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody LoansModel loans) throws Exception {
        Map<String, Object> response = new LinkedHashMap<>();
        try {
//            ตรวจสอบว่ามีการยืมหนังสือหรือไม่
            int loanId = loansService.checkBorrow(loans.getUserId(), loans.getBookId(), 1);
            if(loanId <= 0) {
                return ResponseEntity.status(409).body("Bad Request: No book borrowing found.");
            }

//            ตรวจสอบกำหนดคืนกับวันที่คืนหนังสือจริง 2 = คืนตามวัน, 3 = คืนเกินวัน
            int pastTheDeadline = (loansService.checkDueDateAndReturnDate(loanId) == 0)? 2 : 3;
//            แก้ไขสถานะการยืมหนังสือ เป็นคืนหนังสือ
            if(loansService.returnBook(pastTheDeadline, loanId)) {
//                แก้ไขจำนวนหนังสือที่สามารถยืมได้ +1
                if(bookService.editReturnAvailableCopies(loans.getBookId())) {
//                    บันทึกข้อมูลลง Transactions
                    TransactionsModel transactions = new TransactionsModel();
                    transactions.setLoanId(loanId);
                    transactions.setActionType(2);
                    if(transactionsService.addTransactions(transactions)) {
                        response.put("msg", "Return successfully.");
                        return ResponseEntity.status(201).body(response);
                    }
                }
            }

            return ResponseEntity.status(500).body("Server Error: Unable to add return.");

        } catch (Exception ex) {
            throw ex;
        }
    }

}

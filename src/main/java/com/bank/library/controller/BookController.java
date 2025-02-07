package com.bank.library.controller;

import com.bank.library.model.BookAuthorModel;
import com.bank.library.model.BookModel;
import com.bank.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    @Lazy
    private BookService bookService;

    @GetMapping("/")
    public ResponseEntity<?> getBookList() throws Exception {
        Map<String, Object> response = new LinkedHashMap<>();
        try {
            response.put("bookList", bookService.getBookList());
        } catch (Exception ex) {
            throw ex;
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getBookByTitle")
    public ResponseEntity<?> getBookByTitle(@RequestParam String title) throws Exception {
        Map<String, Object> response = new LinkedHashMap<>();
        try {
            response.put("bookList", bookService.getBookByTitle(title));
        } catch (Exception ex) {
            throw ex;
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getBookByAuthor")
    public ResponseEntity<?> getBookByAuthor(@RequestParam String author) throws Exception {
        Map<String, Object> response = new LinkedHashMap<>();
        try {
            response.put("bookList", bookService.getBookByAuthor(author));
        } catch (Exception ex) {
            throw ex;
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getBookByCategory")
    public ResponseEntity<?> getBookByCategory(@RequestParam int category_id) throws Exception {
        Map<String, Object> response = new LinkedHashMap<>();
        try {
            response.put("bookList", bookService.getBookByCategory(category_id));
        } catch (Exception ex) {
            throw ex;
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getBookByPublishedYear")
    public ResponseEntity<?> getBookByPublishedYear(@RequestParam int published_year) throws Exception {
        Map<String, Object> response = new LinkedHashMap<>();
        try {
            response.put("bookList", bookService.getBookByPublishedYear(published_year));
        } catch (Exception ex) {
            throw ex;
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getBookByISBN")
    public ResponseEntity<?> getBookByISBN(@RequestParam String isbn) throws Exception {
        Map<String, Object> response = new LinkedHashMap<>();
        try {
            response.put("bookList", bookService.getBookByISBN(isbn));
        } catch (Exception ex) {
            throw ex;
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody BookModel book) throws Exception {
        Map<String, Object> response = new LinkedHashMap<>();
        try {
            String errMsg = validateDataRequired(book);
            if(!errMsg.isEmpty()) {
                return ResponseEntity.status(400).body("Bad Request: Missing required parameters.\n\n"+errMsg);
            }

            if(book.getIsbn() != null && !book.getIsbn().isEmpty()) {
                if(bookService.checkISBNIsExist(book.getIsbn()) > 0) {
                    return ResponseEntity.status(409).body("Bad Request: ISBN is already exist.");
                }
            } else {
                book.setIsbn(null);
            }

            if(bookService.addBook(book)) {
                response.put("msg", "Data added successfully.");
                return ResponseEntity.status(201).body(response);
            }

            return ResponseEntity.status(500).body("Server Error: Unable to add book.");

        } catch (Exception ex) {
            throw ex;
        }
    }

    @PutMapping("/edit/{book_id}")
    public ResponseEntity<?> edit(@PathVariable int book_id, @RequestBody BookModel book) throws Exception {
        Map<String, Object> response = new LinkedHashMap<>();
        try {
            String errMsg = validateDataRequired(book);
            if (!errMsg.isEmpty()) {
                return ResponseEntity.status(400).body("Bad Request: Missing required parameters.\n\n" + errMsg);
            }

            if (book.getIsbn() != null && !book.getIsbn().isEmpty()) {
                int bookId = bookService.checkISBNIsExist(book.getIsbn());
                if (bookId > 0 && bookId != book_id) {
                    return ResponseEntity.status(409).body("Bad Request: ISBN already exists.");
                }
            } else {
                book.setIsbn(null);
            }

            book.setBookId(book_id);

            if (bookService.editBook(book)) {
                response.put("msg", "Data edited successfully.");
                return ResponseEntity.ok(response);
            }

            return ResponseEntity.status(500).body("Server Error: Unable to update book.");

        } catch (Exception ex) {
            throw ex;
        }
    }

    @DeleteMapping("/delete/{book_id}")
    public ResponseEntity<?> delete(@PathVariable int book_id) throws Exception {
        Map<String, Object> response = new LinkedHashMap<>();
        try {
            if (bookService.deleteBook(book_id)) {
                response.put("msg", "Data deleted successfully.");
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(404).body("Book not found.");
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    public String validateDataRequired(BookModel book) {
        String errMsg = "";
        if(book.getTitle() == null || book.getTitle().isEmpty()) {
            errMsg += "- title\n";
        }

        if((Integer) book.getTotalCopies() == null || book.getTotalCopies() < 0) {
            errMsg += "- totalCopies\n";
        }

        if((Integer) book.getAvailableCopies() == null || book.getAvailableCopies() < 0) {
            errMsg += "- availableCopies";
        }

        return errMsg;
    }

    @PostMapping("/addBookAndAuthor")
    public ResponseEntity<?> addBookAndAuthor(@RequestBody BookAuthorModel bookAuthor) throws Exception {
        Map<String, Object> response = new LinkedHashMap<>();
        try {

            if(bookService.addBookAndAuthor(bookAuthor.getBookId(), bookAuthor.getAuthorId())) {
                response.put("msg", "Data added successfully.");
                return ResponseEntity.status(201).body(response);
            }

            return ResponseEntity.status(500).body("Server Error: Unable to add book and author relation.");

        } catch (Exception ex) {
            throw ex;
        }
    }

}

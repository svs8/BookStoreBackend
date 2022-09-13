package com.bridgelabz.bookstorebackend.controller;

import com.bridgelabz.bookstorebackend.dto.BookDTO;
import com.bridgelabz.bookstorebackend.dto.ResponseDTO;
import com.bridgelabz.bookstorebackend.model.Book;
import com.bridgelabz.bookstorebackend.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
//@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    IBookService bookService;

    @PostMapping("/save")
    public ResponseEntity<String> addBookToRepository(@Valid @RequestBody BookDTO bookDTO) {
        Book newBook = bookService.createBook(bookDTO);
        ResponseDTO responseDTO = new ResponseDTO("New Book Added in Book Store", newBook);

        return new ResponseEntity(responseDTO, HttpStatus.CREATED);
    }


    // Get All
    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAllBookData() {
        List<Book> listOfBooks = bookService.getAllBookData();
        ResponseDTO dto = new ResponseDTO("Data retrieved successfully :", listOfBooks);
        return new ResponseEntity(dto, HttpStatus.OK);
    }

    @GetMapping("/getBy/{bookId}")
    public ResponseEntity<ResponseDTO> getBookDataById(@PathVariable int bookId) {
        Book book = bookService.getBookDataById(bookId);
        ResponseDTO dto = new ResponseDTO("Data retrieved successfully by id :", book);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{bookId}")
    public String deleteRecordById(@PathVariable int bookId) {
        bookService.deleteRecordByBookId(bookId);
        return "Book Deleted.";
    }

    @PutMapping("/updateBookById/{bookId}")
    public ResponseEntity<ResponseDTO> updateRecordById(@PathVariable Integer bookId, @Valid @RequestBody BookDTO bookDTO) {
        Book updateRecord = bookService.updateRecordById(bookId, bookDTO);
        ResponseDTO dto = new ResponseDTO(" Book Record updated successfully by Id", updateRecord);
        return new ResponseEntity<>(dto, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "searchByBookName/{bookName}")
    public ResponseEntity getBookByName(@PathVariable String bookName) {
            List<Book> fetchedRecord = bookService.getBookByName(bookName);
            ResponseDTO dto = new ResponseDTO(" Book retreived  successfully by Name", fetchedRecord);
            return new ResponseEntity(dto, HttpStatus.ACCEPTED);
        }

    @GetMapping("/sortBookByAsc")
    public ResponseEntity<ResponseDTO> getBooksInAscendingOrder() {
        List<Book> bookList = bookService.sortedBooksInAscendingOrder();
        ResponseDTO dto = new ResponseDTO("Data retrieved successfully :", bookList);
        return new ResponseEntity(dto, HttpStatus.OK);

    }

    // sort books by price in descending order
    @GetMapping("/sortBookByDesc")
    public ResponseEntity<ResponseDTO> getBooksInDescendingOrder() {
        List<Book> listOfBooks = bookService.sortedBooksInDescendingOrder();
        ResponseDTO dto = new ResponseDTO("Data retrieved successfully :", listOfBooks);
        return new ResponseEntity(dto, HttpStatus.OK);

    }
    }



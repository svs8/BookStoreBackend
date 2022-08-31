package com.bridgelabz.bookstorebackend.service;

import com.bridgelabz.bookstorebackend.dto.BookDTO;
import com.bridgelabz.bookstorebackend.model.Book;

import java.util.List;


public interface IBookService {

    Book createBook(BookDTO bookDTO);

    List<Book> getAllBookData();

    Book getBookDataById(int bookId);

    void deleteRecordByBookId(int bookId);


    Book updateRecordById(Integer bookId, BookDTO bookDTO);

    List<Book> getBookByName(String name);

    List<Book> sortedBooksInAscendingOrder();

    List<Book> sortedBooksInDescendingOrder();

}

package com.bridgelabz.bookstorebackend.service;

import com.bridgelabz.bookstorebackend.dto.BookDTO;
import com.bridgelabz.bookstorebackend.model.Book;
import com.bridgelabz.bookstorebackend.repository.BookStoreRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BookService implements IBookService {

    @Autowired
    BookStoreRepository bookStoreRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Book createBook(BookDTO bookDTO) {
        Book newBook = modelMapper.map(bookDTO, Book.class);
        return  bookStoreRepository.save(newBook);
    }

    @Override
    public List<Book> getAllBookData() {
        List<Book> getBooks=bookStoreRepository.findAll();
        return getBooks;
    }

    @Override
    public Book getBookDataById(int bookId) {
        return bookStoreRepository.findByBookId(bookId);
    }

    @Override
    public void deleteRecordByBookId(int bookId) {
        bookStoreRepository.deleteById(bookId);
    }

    @Override
    public Book updateRecordById(Integer bookId, BookDTO bookDTO) {
        Book book = bookStoreRepository.findByBookId(bookId);
        modelMapper.map(bookDTO, book);
        return bookStoreRepository.save(book);
    }

    @Override
    public List<Book> getBookByName(String bookName) {
        List<Book> book = bookStoreRepository.findByBookNameContaining(bookName);
        return book;
    }

    @Override
    public List<Book> sortedBooksInAscendingOrder() {
        List<Book> SortedListAsc=  bookStoreRepository.getSortedListOfBooksInAsc();
        return SortedListAsc;
    }

    @Override
    public List<Book> sortedBooksInDescendingOrder() {
        List<Book> SortedListInDesc=  bookStoreRepository.getSortedListOfBooksInDesc();
        return SortedListInDesc;
    }


}

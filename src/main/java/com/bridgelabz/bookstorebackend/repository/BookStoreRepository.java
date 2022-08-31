package com.bridgelabz.bookstorebackend.repository;

import com.bridgelabz.bookstorebackend.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;


public interface BookStoreRepository extends JpaRepository<Book,Integer> {
    Book findByBookId(int bookId);

    List<Book> findByBookNameContaining(String bookName);

    @Query(value = "select * from book order by price", nativeQuery = true)
    List<Book> getSortedListOfBooksInAsc();

    @Query(value = "select * from book order by price desc", nativeQuery = true)
    List<Book> getSortedListOfBooksInDesc();

}

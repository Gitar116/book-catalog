package com.testtask.bookcatalog.repository;

import com.testtask.bookcatalog.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByOrderByTitleDesc();
}

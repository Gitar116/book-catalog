package com.testtask.bookcatalog.service;

import com.testtask.bookcatalog.model.TitleLetterCount;
import com.testtask.bookcatalog.model.Book;
import com.testtask.bookcatalog.model.GroupBook;
import java.util.List;

public interface BookService {

    List<Book> orderedBookListByTitle();

    Book createBook(Book book);

    List<GroupBook> groupedBookListByAuthor();

    List<TitleLetterCount> titleLetterCountList(String letter);

}

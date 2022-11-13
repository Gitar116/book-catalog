package com.testtask.bookcatalog.service.impl;

import com.testtask.bookcatalog.model.TitleLetterCount;
import com.testtask.bookcatalog.model.Book;
import com.testtask.bookcatalog.model.GroupBook;
import com.testtask.bookcatalog.repository.BookRepository;
import com.testtask.bookcatalog.service.BookService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.springframework.util.StringUtils.countOccurrencesOf;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private static final long LIMIT_AUTHOR_LIST = 10L;

    private static final int MIN_LETTER_COUNT = 0;

    private final BookRepository bookRepository;

    @Override
    public List<Book> orderedBookListByTitle() {
        return bookRepository.findAllByOrderByTitleDesc();
    }

    @Override
    public Book createBook(@NonNull final Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<GroupBook> groupedBookListByAuthor() {
        val bookList = bookRepository.findAll();
        val authorSet = bookList.stream().map(book -> book.getAuthor()).collect(toSet());

        return authorSet.stream().map(author -> createGroupBook(author, bookList)).collect(toList());
    }

    @Override
    public List<TitleLetterCount> titleLetterCountList(final String letter) {
        val bookList = bookRepository.findAll();
        val authorSet = bookList.stream().map(book -> book.getAuthor()).collect(toSet());

        return authorSet.stream()
                .map(author -> createTitleLetterCount(author, bookList, letter))
                .filter(titleLetterCount -> titleLetterCount.getLetterCount() > MIN_LETTER_COUNT)
                .limit(LIMIT_AUTHOR_LIST)
                .collect(toList());
    }

    private GroupBook createGroupBook(final String author, final List<Book> bookList) {
        return GroupBook.init()
                .author(author)
                .books(bookList.stream()
                        .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                        .collect(toList()))
                .build();
    }

    private TitleLetterCount createTitleLetterCount(
            final String author,
            final List<Book> bookList,
            final String letter
    ) {
        val letterCount = bookList.stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .map(book -> countOccurrencesOf(book.getTitle().toUpperCase(), letter.toUpperCase()))
                .mapToInt(Integer::intValue)
                .sum();

        return TitleLetterCount.init()
                .author(author)
                .letterCount(letterCount)
                .build();
    }
}

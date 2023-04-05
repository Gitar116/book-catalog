package com.testtask.bookcatalog.unit;

import com.testtask.bookcatalog.model.Book;
import com.testtask.bookcatalog.repository.BookRepository;
import com.testtask.bookcatalog.service.impl.BookServiceImpl;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class BookServiceTest {

    private final List<Book> bookList = asList(
            new Book(1L, "Crime and Punishment", "F. Dostoevsky", null),
            new Book(2L, "Anna Karenina", "L. Tolstoy", null),
            new Book(3L, "The Brothers Karamazov", "F. Dostoevsky", null),
            new Book(4L, "War and Peace", "L. Tolstoy", null),
            new Book(5L, "Dead Souls", "N. Gogol", null),
            new Book(5L, "aaaaaaa", "N. Gogol", null)
    );

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl service;

    @Test
    void orderedBookListByTitleTest() {
        service.orderedBookListByTitle();

        verify(bookRepository).findAllByOrderByTitleDesc();
    }

    @Test
    void createBookTest() {
        val book = new Book();

        when(bookRepository.save(book)).thenReturn(book);

        val actual = service.createBook(book);

        verify(bookRepository).save(book);

        assertEquals(book, actual);
    }

    @Test
    void groupedBookListByAuthorTest() {
        when(bookRepository.findAll()).thenReturn(bookList);

        val actual = service.groupedBookListByAuthor();

        verify(bookRepository).findAll();

        assertEquals(3, actual.size());
        assertEquals(
                "Crime and Punishment",
                actual.stream()
                        .filter(groupBook -> groupBook.getAuthor().equalsIgnoreCase("F. Dostoevsky"))
                        .findFirst().orElseThrow().getBooks().get(0).getTitle()
        );
        assertEquals(
                "The Brothers Karamazov",
                actual.stream()
                        .filter(groupBook -> groupBook.getAuthor().equalsIgnoreCase("F. Dostoevsky"))
                        .findFirst().orElseThrow().getBooks().get(1).getTitle()
        );
        assertEquals(
                2,
                actual.stream()
                        .filter(groupBook -> groupBook.getAuthor().equalsIgnoreCase("F. Dostoevsky"))
                        .findFirst().orElseThrow().getBooks().size()
        );
        assertEquals(
                "Anna Karenina",
                actual.stream()
                        .filter(groupBook -> groupBook.getAuthor().equalsIgnoreCase("L. Tolstoy"))
                        .findFirst().orElseThrow().getBooks().get(0).getTitle()
        );
        assertEquals(
                "War and Peace",
                actual.stream()
                        .filter(groupBook -> groupBook.getAuthor().equalsIgnoreCase("L. Tolstoy"))
                        .findFirst().orElseThrow().getBooks().get(1).getTitle()
        );
        assertEquals(
                2,
                actual.stream()
                        .filter(groupBook -> groupBook.getAuthor().equalsIgnoreCase("L. Tolstoy"))
                        .findFirst().orElseThrow().getBooks().size()
        );
        assertEquals(
                "Dead Souls",
                actual.stream()
                        .filter(groupBook -> groupBook.getAuthor().equalsIgnoreCase("N. Gogol"))
                        .findFirst().orElseThrow().getBooks().get(0).getTitle()
        );
        assertEquals(
                1,
                actual.stream()
                        .filter(groupBook -> groupBook.getAuthor().equalsIgnoreCase("N. Gogol"))
                        .findFirst().orElseThrow().getBooks().size()
        );
    }

    @Test
    void authorListByLetterTest() {
        when(bookRepository.findAll()).thenReturn(bookList);

        val actual = service.titleLetterCountList("a");

        verify(bookRepository).findAll();

        assertEquals(
                7,
                actual.stream()
                        .filter(author -> author.getAuthor().equalsIgnoreCase("L. Tolstoy"))
                        .findFirst().orElseThrow()
                        .getLetterCount()
        );
        assertEquals(
                4,
                actual.stream()
                        .filter(author -> author.getAuthor().equalsIgnoreCase("F. Dostoevsky"))
                        .findFirst().orElseThrow()
                        .getLetterCount()
        );
        assertEquals(
                8,
                actual.stream()
                        .filter(author -> author.getAuthor().equalsIgnoreCase("N. Gogol"))
                        .findFirst().orElseThrow()
                        .getLetterCount()
        );
        assertTrue(actual.get(0).getAuthor().equalsIgnoreCase("N. Gogol"));
        assertTrue(actual.get(1).getAuthor().equalsIgnoreCase("L. Tolstoy"));
        assertTrue(actual.get(2).getAuthor().equalsIgnoreCase("F. Dostoevsky"));
        assertEquals(3, actual.size());
    }
}
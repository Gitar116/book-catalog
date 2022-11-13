package com.testtask.bookcatalog.controller;

import com.testtask.bookcatalog.model.TitleLetterCount;
import com.testtask.bookcatalog.model.Book;
import com.testtask.bookcatalog.model.GroupBook;
import com.testtask.bookcatalog.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(path = "api", produces = APPLICATION_JSON_VALUE, method = POST)
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @RequestMapping("/ordered-books")
    public ResponseEntity<List<Book>> orderedBookListByTitle() {
        return ResponseEntity.ok().body(bookService.orderedBookListByTitle());
    }

    @RequestMapping("/book/create")
    public ResponseEntity<Book> createBook(@RequestBody final Book book) {
        return ResponseEntity.ok().body(bookService.createBook(book));
    }

    @RequestMapping("/grouped-books")
    public ResponseEntity<List<GroupBook>> groupedBookListByAuthor() {
        return ResponseEntity.ok().body(bookService.groupedBookListByAuthor());
    }

    @RequestMapping("/title/letter/count")
    public ResponseEntity<List<TitleLetterCount>> titleLetterCountList(@RequestBody final String letter) {
        return ResponseEntity.ok().body(bookService.titleLetterCountList(letter));
    }
}

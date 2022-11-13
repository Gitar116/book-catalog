package com.testtask.bookcatalog;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testtask.bookcatalog.model.Book;
import com.testtask.bookcatalog.model.GroupBook;
import com.testtask.bookcatalog.model.TitleLetterCount;
import com.testtask.bookcatalog.repository.BookRepository;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
class BookCatalogITTests {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void orderedBookListByTitleITTest() throws Exception {
        val response = mockMvc.perform(post("/api/ordered-books"))
                .andReturn().getResponse().getContentAsString();
        val responseBody = objectMapper.readValue(response, new TypeReference<List<Book>>() {});

        assertEquals("War and Peace", responseBody.get(0).getTitle());
        assertEquals("The Brothers Karamazov", responseBody.get(1).getTitle());
        assertEquals("Dead Souls", responseBody.get(2).getTitle());
        assertEquals("Crime and Punishment", responseBody.get(3).getTitle());
        assertEquals("Anna Karenina", responseBody.get(4).getTitle());
    }

    @Test
    public void createBookITTest() throws Exception {
        assertEquals(5, bookRepository.findAll().size());

        val book = new Book(6L, "Java: The Complete Reference", "H. Schildt", null);
        val response = mockMvc.perform(
                post("/api/book/create")
                        .content(objectMapper.writeValueAsString(book))
                        .contentType(APPLICATION_JSON)
        )
                .andReturn().getResponse().getContentAsString();
        val responseBody = objectMapper.readValue(response, Book.class);

        assertEquals(responseBody.getAuthor(), book.getAuthor());
        assertEquals(responseBody.getTitle(), book.getTitle());

        assertEquals(6, bookRepository.findAll().size());

        bookRepository.delete(book);
    }

    @Test
    public void groupedBookListByAuthorITTest() throws Exception {
        val response = mockMvc.perform(post("/api/grouped-books"))
                .andReturn().getResponse().getContentAsString();
        val responseBody = objectMapper.readValue(response, new TypeReference<List<GroupBook>>() {});

        assertEquals("L. Tolstoy", responseBody.get(0).getAuthor());
        assertEquals("Anna Karenina", responseBody.get(0).getBooks().get(0).getTitle());
        assertEquals("War and Peace", responseBody.get(0).getBooks().get(1).getTitle());

        assertEquals("F. Dostoevsky", responseBody.get(1).getAuthor());
        assertEquals("Crime and Punishment", responseBody.get(1).getBooks().get(0).getTitle());
        assertEquals("The Brothers Karamazov", responseBody.get(1).getBooks().get(1).getTitle());

        assertEquals("N. Gogol", responseBody.get(2).getAuthor());
        assertEquals("Dead Souls", responseBody.get(2).getBooks().get(0).getTitle());
    }

    @Test
    public void titleLetterCountListITTest() throws Exception {
        val response = mockMvc.perform(
                post("/api/title/letter/count")
                        .content("A")
                        .contentType(APPLICATION_JSON)
        )
                .andReturn().getResponse().getContentAsString();
        val responseBody = objectMapper.readValue(response, new TypeReference<List<TitleLetterCount>>() {});

        assertEquals("L. Tolstoy", responseBody.get(0).getAuthor());
        assertEquals(7, responseBody.get(0).getLetterCount());

        assertEquals("F. Dostoevsky", responseBody.get(1).getAuthor());
        assertEquals(4, responseBody.get(1).getLetterCount());

        assertEquals("N. Gogol", responseBody.get(2).getAuthor());
        assertEquals(1, responseBody.get(2).getLetterCount());

        assertEquals(3, responseBody.size());
    }
}

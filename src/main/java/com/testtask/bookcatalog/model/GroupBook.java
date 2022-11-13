package com.testtask.bookcatalog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "init")
public class GroupBook {

    private String author;

    private List<Book> books;
}

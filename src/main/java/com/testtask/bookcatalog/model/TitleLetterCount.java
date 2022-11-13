package com.testtask.bookcatalog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "init")
public class TitleLetterCount {

    private String author;

    private int letterCount;
}

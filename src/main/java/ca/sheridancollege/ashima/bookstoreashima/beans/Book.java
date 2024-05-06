package ca.sheridancollege.ashima.bookstoreashima.beans;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Book {
    private String title;
    private String author;
    private String isbn;
    private int price;
    private String description;
}


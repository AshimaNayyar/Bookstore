package ca.sheridancollege.ashima.bookstoreashima.beans;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Order {
    private Book book;
    private int quantity;
}

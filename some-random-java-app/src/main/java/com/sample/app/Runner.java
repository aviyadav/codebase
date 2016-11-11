package com.sample.app;

import com.sample.app.model.Book;
import com.sample.app.storage.BookStorage;

public class Runner {

    public static void main(String[] args) {
        BookStorage storage = new BookStorage();
        storage.getBooks().stream().forEach((Book b) -> System.out.println(b));
    }
}

package com.quangduy.bookservice.command.event;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.quangduy.bookservice.command.data.Book;
import com.quangduy.bookservice.command.data.BookRepository;

@Component
public class BookEventsHandler {
    private BookRepository bookRepository;

    public BookEventsHandler(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @EventHandler
    public void on(BookCreatedEvent event) {
        Book book = new Book();
        BeanUtils.copyProperties(event, book);
        bookRepository.save(book);
    }
}

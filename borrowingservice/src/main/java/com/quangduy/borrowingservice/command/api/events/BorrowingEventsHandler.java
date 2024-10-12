package com.quangduy.borrowingservice.command.api.events;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.quangduy.borrowingservice.command.api.data.Borrowing;
import com.quangduy.borrowingservice.command.api.data.BorrowingRepository;

@Component
public class BorrowingEventsHandler {
    private BorrowingRepository borrowingRepository;

    public BorrowingEventsHandler(BorrowingRepository borrowingRepository) {
        this.borrowingRepository = borrowingRepository;
    }

    @EventHandler
    public void on(BorrowCreatedEvent event) {
        Borrowing model = new Borrowing();
        BeanUtils.copyProperties(event, model);
        this.borrowingRepository.save(model);
    }
}

package com.quangduy.borrowingservice.command.api.aggregate;

import java.util.Date;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.quangduy.borrowingservice.command.api.command.CreateBorrowCommand;
import com.quangduy.borrowingservice.command.api.events.BorrowCreatedEvent;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Aggregate
@Getter
@Setter
@NoArgsConstructor
public class BorrowAggrgate {
    @AggregateIdentifier
    private String id;
    private String bookId;
    private String employeeId;
    private Date borrowingDate;
    private Date returnDate;
    private String message;

    @CommandHandler
    public BorrowAggrgate(CreateBorrowCommand command) {
        BorrowCreatedEvent event = new BorrowCreatedEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(BorrowCreatedEvent event) {
        this.bookId = event.getBookId();
        this.borrowingDate = event.getBorrowingDate();
        this.employeeId = event.getEmployeeId();
        this.id = event.getId();
    }
}

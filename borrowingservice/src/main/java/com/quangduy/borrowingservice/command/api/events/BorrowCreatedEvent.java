package com.quangduy.borrowingservice.command.api.events;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BorrowCreatedEvent {
    private String id;
    private String bookId;
    private String employeeId;
    private Date borrowingDate;
}

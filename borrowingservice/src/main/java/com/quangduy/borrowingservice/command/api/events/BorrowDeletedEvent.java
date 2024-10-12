package com.quangduy.borrowingservice.command.api.events;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BorrowDeletedEvent {
    private String id;
}

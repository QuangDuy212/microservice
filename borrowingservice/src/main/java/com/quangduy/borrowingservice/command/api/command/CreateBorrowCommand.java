package com.quangduy.borrowingservice.command.api.command;

import java.util.Date;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateBorrowCommand {
    @TargetAggregateIdentifier
    private String id;

    private String bookId;
    private String employeeId;
    private Date borrowingDate;
}

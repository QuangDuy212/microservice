package com.quangduy.borrowingservice.command.api.data;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "borrowing")
@Getter
@Setter
public class Borrowing {
    @Id
    private String id;

    private String bookId;

    private String employeeId;
    private Date borrowingDate;
    private Date returnDate;

}

package com.quangduy.borrowingservice.command.api.controller;

import java.util.Date;
import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quangduy.borrowingservice.command.api.command.CreateBorrowCommand;
import com.quangduy.borrowingservice.command.api.model.BorrowingRequestModel;

@RestController
@RequestMapping("/api/v1/borrowing")
public class BorrowCommandController {
    private CommandGateway commandGateway;

    public BorrowCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public String addBookBorrowing(@RequestBody BorrowingRequestModel model) {
        try {
            CreateBorrowCommand command = new CreateBorrowCommand();
            command.setBookId(model.getBookId());
            command.setBorrowingDate(new Date());
            command.setEmployeeId(model.getEmployeeId());
            command.setId(UUID.randomUUID().toString());
            commandGateway.sendAndWait(command);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "Created Book borrowing";
    }
}

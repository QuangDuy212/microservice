package com.quangduy.bookservice.command.controller;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quangduy.bookservice.command.command.CreateBookCommand;
import com.quangduy.bookservice.command.model.BookRequestModel;

@RestController
@RequestMapping("/api/v1/books")
public class BookCommandController {
    private CommandGateway commandGateway;

    public BookCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public String addBook(@RequestBody BookRequestModel model) {
        CreateBookCommand command = new CreateBookCommand(UUID.randomUUID().toString(), model.getName(),
                model.getAuthor(), true);
        commandGateway.sendAndWait(command);
        return "added Book";
    }
}

package com.quangduy.employeeservice.command.controller;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quangduy.employeeservice.command.command.CreateEmployeeCommand;
import com.quangduy.employeeservice.command.command.DeleteEmployeeCommand;
import com.quangduy.employeeservice.command.command.UpdateEmployeeCommand;
import com.quangduy.employeeservice.command.model.EmployeeRequestModel;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;

@RestController
@RequestMapping("/api/v1/employees")
@EnableBinding(Source.class)
public class EmployeeCommandController {
    private CommandGateway commandGateway;
    private MessageChannel output;

    public EmployeeCommandController(CommandGateway commandGateway, MessageChannel output) {
        this.commandGateway = commandGateway;
        this.output = output;
    }

    @PostMapping
    public String addEmployee(@RequestBody EmployeeRequestModel model) {
        CreateEmployeeCommand command = new CreateEmployeeCommand();
        command.setEmployeeId(UUID.randomUUID().toString());
        command.setFirstName(model.getFirstName());
        command.setIsDisciplined(false);
        command.setLastName(model.getLastName());
        command.setKin(model.getKin());
        commandGateway.sendAndWait(command);
        return "Employee added";
    }

    @PutMapping
    public String updateEmployee(@RequestBody EmployeeRequestModel model) {
        UpdateEmployeeCommand command = new UpdateEmployeeCommand();
        command.setEmployeeId(model.getEmployeeId());
        command.setFirstName(model.getFirstName());
        command.setLastName(model.getLastName());
        command.setIsDisciplined(model.getIsDisciplined());
        command.setKin(model.getKin());
        commandGateway.sendAndWait(command);
        return "Updated Employee";
    }

    @DeleteMapping("/{employeeId}")
    public String deleteEmployee(@PathVariable String employeeId) {
        DeleteEmployeeCommand command = new DeleteEmployeeCommand(employeeId);
        commandGateway.sendAndWait(command);
        return "Deleted Employee";
    }

    @PostMapping("/sendMessage")
    public void SendMessage(@RequestBody String message) {
        try {

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(message);
            output.send(MessageBuilder.withPayload(json).build());
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

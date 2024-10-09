package com.quangduy.employeeservice.command.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.quangduy.employeeservice.command.command.CreateEmployeeCommand;
import com.quangduy.employeeservice.command.command.DeleteEmployeeCommand;
import com.quangduy.employeeservice.command.command.UpdateEmployeeCommand;
import com.quangduy.employeeservice.command.event.EmployeeCreatedEvent;
import com.quangduy.employeeservice.command.event.EmployeeDeletedEvent;
import com.quangduy.employeeservice.command.event.EmployeeUpdatedEvent;

import net.bytebuddy.agent.builder.AgentBuilder;

@Aggregate
public class EmployeeAggregate {
    @AggregateIdentifier
    private String employeeId;
    private String firstName;
    private String lastName;
    private String kin;
    private Boolean isDisciplined;

    public EmployeeAggregate() {
    }

    @CommandHandler
    public EmployeeAggregate(CreateEmployeeCommand createEmployeeCommand) {
        EmployeeCreatedEvent employeeCreatedEvent = new EmployeeCreatedEvent();
        BeanUtils.copyProperties(createEmployeeCommand, employeeCreatedEvent);
        AggregateLifecycle.apply(employeeCreatedEvent);
    }

    @CommandHandler
    public void handle(UpdateEmployeeCommand updateEmployeeCommand) {
        EmployeeUpdatedEvent employeeUpdatedEvent = new EmployeeUpdatedEvent();
        BeanUtils.copyProperties(updateEmployeeCommand, employeeUpdatedEvent);
        AggregateLifecycle.apply(employeeUpdatedEvent);
    }

    @CommandHandler
    public void handle(DeleteEmployeeCommand deleteEmployeeCommand) {
        EmployeeDeletedEvent employeeDeletedEvent = new EmployeeDeletedEvent();
        BeanUtils.copyProperties(deleteEmployeeCommand, employeeDeletedEvent);
        AggregateLifecycle.apply(employeeDeletedEvent);
    }

    @EventSourcingHandler
    public void on(EmployeeCreatedEvent event) {
        this.employeeId = event.getEmployeeId();
        this.firstName = event.getFirstName();
        this.lastName = event.getLastName();
        this.kin = event.getKin();
        this.isDisciplined = event.getIsDisciplined();
    }

    @EventSourcingHandler
    public void on(EmployeeUpdatedEvent event) {
        this.employeeId = event.getEmployeeId();
        this.firstName = event.getFirstName();
        this.lastName = event.getLastName();
        this.kin = event.getKin();
        this.isDisciplined = event.getIsDisciplined();
    }

    @EventSourcingHandler
    public void on(EmployeeDeletedEvent event) {
        this.employeeId = event.getEmployeeId();
    }
}

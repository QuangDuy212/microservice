package com.quangduy.employeeservice.command.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class DeleteEmployeeCommand {
    @TargetAggregateIdentifier
    private String employeeId;

    public DeleteEmployeeCommand(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

}

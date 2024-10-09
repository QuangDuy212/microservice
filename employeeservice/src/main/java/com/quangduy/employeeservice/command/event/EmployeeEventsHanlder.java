package com.quangduy.employeeservice.command.event;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.quangduy.employeeservice.command.data.Employee;
import com.quangduy.employeeservice.command.data.EmployeeRepository;

@Component
public class EmployeeEventsHanlder {
    private EmployeeRepository employeeRepository;

    public EmployeeEventsHanlder(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @EventHandler
    public void on(EmployeeCreatedEvent event) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(event, employee);
        this.employeeRepository.save(employee);
    }

    @EventHandler
    public void on(EmployeeUpdatedEvent event) {
        Employee employee = this.employeeRepository.getById(event.getEmployeeId());
        employee.setFirstName(event.getFirstName());
        employee.setLastName(event.getLastName());
        employee.setIsDisciplined(event.getIsDisciplined());
        employee.setKin(event.getKin());
        this.employeeRepository.save(employee);
    }

    @EventHandler
    public void on(EmployeeDeletedEvent event) {
        this.employeeRepository.deleteById(event.getEmployeeId());
    }
}

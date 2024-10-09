package com.quangduy.employeeservice.query.controller;

import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quangduy.employeeservice.query.model.EmployeeResponseModel;
import com.quangduy.employeeservice.query.queries.GetAllEmployeesQuery;
import com.quangduy.employeeservice.query.queries.GetEmployeeQuery;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeQueryController {
    private QueryGateway queryGateway;

    public EmployeeQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping("/{employeeId}")
    public EmployeeResponseModel getEmployeeDetail(@PathVariable String employeeId) {
        GetEmployeeQuery getEmployeeQuery = new GetEmployeeQuery();
        getEmployeeQuery.setEmployeeId(employeeId);

        EmployeeResponseModel employeeResponseModel = queryGateway
                .query(getEmployeeQuery, ResponseTypes.instanceOf(EmployeeResponseModel.class))
                .join();
        return employeeResponseModel;
    }

    @GetMapping
    public List<EmployeeResponseModel> getAllEmployees() {
        GetAllEmployeesQuery getAllEmployeesQuery = new GetAllEmployeesQuery();
        List<EmployeeResponseModel> employeeResponseModel = queryGateway
                .query(getAllEmployeesQuery, ResponseTypes.multipleInstancesOf(EmployeeResponseModel.class))
                .join();
        return employeeResponseModel;
    }
}

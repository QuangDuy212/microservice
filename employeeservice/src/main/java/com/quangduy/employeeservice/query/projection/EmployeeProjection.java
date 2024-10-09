package com.quangduy.employeeservice.query.projection;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.quangduy.employeeservice.command.data.Employee;
import com.quangduy.employeeservice.command.data.EmployeeRepository;
import com.quangduy.employeeservice.query.model.EmployeeResponseModel;
import com.quangduy.employeeservice.query.queries.GetAllEmployeesQuery;
import com.quangduy.employeeservice.query.queries.GetEmployeeQuery;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeeProjection {
    private EmployeeRepository employeeRepository;

    public EmployeeProjection(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @QueryHandler
    public EmployeeResponseModel handle(GetEmployeeQuery getEmployeeQuery) {
        EmployeeResponseModel model = new EmployeeResponseModel();
        Employee employee = this.employeeRepository.getById(getEmployeeQuery.getEmployeeId());
        BeanUtils.copyProperties(employee, model);
        return model;
    }

    @QueryHandler
    public List<EmployeeResponseModel> handle(GetAllEmployeesQuery getAllEmployeesQuery) {
        List<Employee> listEntity = this.employeeRepository.findAll();
        List<EmployeeResponseModel> listModel = listEntity.stream().map(x -> {
            EmployeeResponseModel res = new EmployeeResponseModel();
            BeanUtils.copyProperties(x, res);
            return res;
        }).collect(Collectors.toList());
        return listModel;
    }
}

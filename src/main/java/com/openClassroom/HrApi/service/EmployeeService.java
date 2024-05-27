package com.openClassroom.HrApi.service;

import com.openClassroom.HrApi.model.Employee;
import com.openClassroom.HrApi.repository.EmployeeRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Data
@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee saveEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    public List<Employee> findAll(){
        return employeeRepository.findAll();
    }

    public void deleteEmployee(Long employeeId){
        employeeRepository.deleteById(employeeId);
    }

    public Optional<Employee> getEmployeeById(Long employeeId){
        return Optional.of(employeeRepository.findById(employeeId).get());
    }



}

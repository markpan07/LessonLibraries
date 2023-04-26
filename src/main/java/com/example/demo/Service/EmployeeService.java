package com.example.demo.Service;

import Exceptions.EmployeeAlreadyAddedException;
import Exceptions.EmployeeBadNameException;
import Exceptions.EmployeeNotFoundException;
import Exceptions.EmployeeStorageIsFullException;
import com.example.demo.Model.Employee;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    private static final int LIMIT = 10;
    private final List<Employee> employees = new ArrayList<>();
    private final String allowedChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public Employee add(String name,
                        String surname,
                        int department,
                        int salary) {
        if (!check(name) || !check(surname)) {
            throw new EmployeeBadNameException();
        }
        name = StringUtils.lowerCase(name);
        name = StringUtils.capitalize(name);
        surname = StringUtils.lowerCase(surname);
        surname = StringUtils.capitalize(surname);

        Employee employee = new Employee(name, surname, department, salary);

        if (employees.contains(employee)) {
            throw new EmployeeAlreadyAddedException();
        }
        if (employees.size() < LIMIT) {
            employees.add(employee);
            return employee;
        }
        throw new EmployeeStorageIsFullException();
    }

    public Employee remove(String name,
                           String surname,
                           int department,
                           int salary) {
        Employee employee = new Employee(name, surname, department, salary);
        if (!employees.contains(employee)) {
            throw new EmployeeNotFoundException();
        }
        employees.remove(employee);
        return employee;
    }

    public Employee find(String name,
                         String surname,
                         int department,
                         int salary) {
        Employee employee = new Employee(name, surname, department, salary);
        if (!employees.contains(employee)) {
            throw new EmployeeNotFoundException();
        }
        return employee;
    }

    public List<Employee> getAll() {
        return new ArrayList<>(employees);
    }

    private boolean check(String input) {
        if (StringUtils.containsOnly(input, allowedChars)) {
            return true;
        }
        return false;
    }

}



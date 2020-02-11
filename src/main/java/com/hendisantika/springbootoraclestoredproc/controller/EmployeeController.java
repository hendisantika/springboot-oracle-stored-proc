package com.hendisantika.springbootoraclestoredproc.controller;

import com.github.javafaker.Faker;
import com.hendisantika.springbootoraclestoredproc.domain.Employee;
import com.hendisantika.springbootoraclestoredproc.model.FunctionResult;
import com.hendisantika.springbootoraclestoredproc.model.ProcedureResult;
import com.hendisantika.springbootoraclestoredproc.repository.EmployeeRepository;
import com.hendisantika.springbootoraclestoredproc.repository.FunctionRepository;
import com.hendisantika.springbootoraclestoredproc.repository.ProcedureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * Project : springboot-oracle-stored-proc
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 10/02/20
 * Time: 06.57
 */
@RestController
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProcedureRepository procedureRepository;

    @Autowired
    private FunctionRepository functionRepository;

    @PutMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Employee addEmployee(@RequestBody Employee employee) {
        log.info("Came inside addEmployee");

        Random random = new Random();
        employee.setId(random.nextInt());
        employeeRepository.save(employee);
        log.info("Saved : " + employee.toString());
        return employee;
    }

    @PutMapping(path = "/random", consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Employee randomEmployee() {
        log.info("Came inside randomEmployee");
        Employee employee = generateEmployee();
        employee.setCreatedAt(new Date());
        employeeRepository.save(employee);
        log.info("Saved : " + employee.toString());
        return employee;
    }

    @PutMapping(path = "/procedure", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Employee procedureEmployee() {
        log.info("Came inside procedureEmployee");
        Employee employee = generateEmployee();
        ProcedureResult procedureResult = procedureRepository.addEmployeeThroughProcedure(employee.getFirstName(),
                employee.getLastName(), employee.getEmail());
        employee.setId(procedureResult.getId());
        employee.setEmail(procedureResult.getEmail());
        employee.setCreatedAt(procedureResult.getCreatedAt());
        log.info("Saved : " + employee.toString());
        return employee;
    }

    @PutMapping(path = "/function", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Employee functionEmployee() {
        log.info("Came inside functionEmployee");
        Employee employee = generateEmployee();
        FunctionResult functionResult = functionRepository.addEmployeeThroughFunction(
                employee.getFirstName(), employee.getLastName(), employee.getEmail());
        employee.setId(functionResult.getId());
        employee.setEmail(functionResult.getEmail());
        employee.setCreatedAt(functionResult.getCreatedAt());
        log.info("Saved : " + employee.toString());
        return employee;
    }

    private Employee generateEmployee() {
        Faker faker = new Faker();
        return Employee.builder()
                .id(faker.number().numberBetween(100, 9999))
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .build();
    }

}

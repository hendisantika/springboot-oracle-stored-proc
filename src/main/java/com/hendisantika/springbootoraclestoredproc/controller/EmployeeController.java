package com.hendisantika.springbootoraclestoredproc.controller;

import com.hendisantika.springbootoraclestoredproc.domain.Employee;
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

}

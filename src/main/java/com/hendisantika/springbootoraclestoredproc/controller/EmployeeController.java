package com.hendisantika.springbootoraclestoredproc.controller;

import com.hendisantika.springbootoraclestoredproc.repository.EmployeeRepository;
import com.hendisantika.springbootoraclestoredproc.repository.FunctionRepository;
import com.hendisantika.springbootoraclestoredproc.repository.ProcedureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

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
}

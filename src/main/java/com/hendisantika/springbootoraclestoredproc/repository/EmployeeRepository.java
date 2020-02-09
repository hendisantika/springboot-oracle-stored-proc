package com.hendisantika.springbootoraclestoredproc.repository;

import com.hendisantika.springbootoraclestoredproc.domain.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by IntelliJ IDEA.
 * Project : springboot-oracle-stored-proc
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 10/02/20
 * Time: 06.41
 */
@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
//    @Procedure(name = "addEmployeeThroughNamedStoredProcedureQuery")
//    ProcedureResult addEmployeeThroughNamedStoredProcedureQuery(@Param("FIRST_NAME") String firstName,
//                                                                @Param("LAST_NAME") String lastName,
//                                                                @Param("EMAIL") String email);
}
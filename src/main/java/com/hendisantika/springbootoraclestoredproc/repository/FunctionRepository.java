package com.hendisantika.springbootoraclestoredproc.repository;

import com.hendisantika.springbootoraclestoredproc.model.FunctionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

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
public class FunctionRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public FunctionResult addEmployeeThroughFunction(String firstName, String lastName, String email) {

        return jdbcTemplate.execute(
                (Connection c) -> {
                    try {
                        CallableStatement cs = c.prepareCall("{ ? = call EMPLOYEEFUNCTION(?, ?, ?, ?)}");
                        cs.registerOutParameter(1, Types.INTEGER); // or whatever type your function returns.
                        // Set your arguments
                        cs.setString(2, firstName); // first argument
                        cs.setString(3, lastName); // second argument
                        cs.setString(4, email); // third argument
                        cs.registerOutParameter(5, Types.DATE); // first OUT parameter
                        return cs;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                },
                (CallableStatement cs) -> {
                    cs.execute();
                    return FunctionResult.builder()
                            .id(cs.getInt(1))
                            .email(email)
                            .createdAt(cs.getDate(5))
                            .build();
                }
        );
    }
}

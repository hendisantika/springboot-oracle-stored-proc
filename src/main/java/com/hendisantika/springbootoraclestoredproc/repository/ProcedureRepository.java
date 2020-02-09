package com.hendisantika.springbootoraclestoredproc.repository;

import com.hendisantika.springbootoraclestoredproc.model.ProcedureResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Project : springboot-oracle-stored-proc
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 10/02/20
 * Time: 06.42
 */
@Repository
public class ProcedureRepository {

    @Autowired
    EntityManager entityManager;

    public ProcedureResult addEmployeeThroughProcedure(String firstName, String lastName, String email) {

        StoredProcedureQuery proc = entityManager.createStoredProcedureQuery(
                "EMPLOYEEPROCEDURE");
        proc.registerStoredProcedureParameter("FIRST_NAME", String.class, ParameterMode.IN);
        proc.registerStoredProcedureParameter("LAST_NAME", String.class, ParameterMode.IN);
        proc.registerStoredProcedureParameter("EMAIL", String.class, ParameterMode.INOUT);
        proc.registerStoredProcedureParameter("ID", Integer.class, ParameterMode.OUT);
        proc.registerStoredProcedureParameter("CREATED_AT", Date.class, ParameterMode.OUT);

        proc.setParameter("FIRST_NAME", firstName);
        proc.setParameter("LAST_NAME", lastName);
        proc.setParameter("EMAIL", email);
        proc.execute();

        return ProcedureResult.builder()
                .email((String) proc.getOutputParameterValue("EMAIL"))
                .id((Integer) proc.getOutputParameterValue("ID"))
                .createdAt((Date) proc.getOutputParameterValue("CREATED_AT"))
                .build();
    }

    public ProcedureResult addEmployeeThroughNamedStoredProcedureQuery(String firstName, String lastName,
                                                                       String email) {

        StoredProcedureQuery proc = entityManager.createNamedStoredProcedureQuery(
                "addEmployeeThroughNamedStoredProcedureQuery");
        proc.setParameter("FIRST_NAME", firstName);
        proc.setParameter("LAST_NAME", lastName);
        proc.setParameter("EMAIL", email);

        proc.execute();

        return ProcedureResult.builder()
                .email((String) proc.getOutputParameterValue("EMAIL"))
                .id((Integer) proc.getOutputParameterValue("ID"))
                .createdAt((Date) proc.getOutputParameterValue("CREATED_AT"))
                .build();
    }
}

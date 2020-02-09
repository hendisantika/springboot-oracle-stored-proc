package com.hendisantika.springbootoraclestoredproc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Project : springboot-oracle-stored-proc
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 10/02/20
 * Time: 06.39
 */
@Data
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@NamedStoredProcedureQuery(
        name = "addEmployeeThroughNamedStoredProcedureQuery",
        procedureName = "EMPLOYEEPROCEDURE",
        parameters = {
                @StoredProcedureParameter(name = "FIRST_NAME", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "LAST_NAME", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "EMAIL", mode = ParameterMode.INOUT, type = String.class),
                @StoredProcedureParameter(name = "ID", mode = ParameterMode.OUT, type = Integer.class),
                @StoredProcedureParameter(name = "CREATED_AT", mode = ParameterMode.OUT, type = Date.class),
        }
)
public class Employee implements Serializable {
    @Id
    private Integer id;

    private String firstName;
    private String lastName;
    private String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ")
    private Date createdAt;
}
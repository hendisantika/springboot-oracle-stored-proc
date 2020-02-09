package com.hendisantika.springbootoraclestoredproc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Project : springboot-oracle-stored-proc
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 10/02/20
 * Time: 06.40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FunctionResult implements Serializable {
    private Integer id;
    private String email;
    private Date createdAt;
}
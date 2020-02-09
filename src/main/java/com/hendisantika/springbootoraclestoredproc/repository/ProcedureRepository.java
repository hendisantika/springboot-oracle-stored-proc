package com.hendisantika.springbootoraclestoredproc.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

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
}

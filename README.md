# springboot-oracle-stored-proc

# SpringBoot Demo Project for Executing Oracle Stored Procedure

### Pre-requisite
- This requires Oracle DB. Follow this [article](https://medium.com/@brunoborges/setting-up-database-servers-for-development-on-mac-os-x-using-docker-b7f2fad056f3) to  spin an Oracle docker image. Alternatively, you can use the [docker-compose.yml](./src/main/docker/docker-compose.yml)
- Once the database is up and running, refer to [oracle-setup.sql](./docs/oracle-setup.sql) to set up the DB user, table and procedure.

We can invoke Oracle stored procedure in couple different ways.

1. Using [Spring Data JPA](https://github.com/spring-projects/spring-data-examples/tree/master/jpa/jpa21)
2. Using the _**javax.persistence**_. We used this method in our case

Irrespective of which way we choose, there are some key aspects/properties we should set when using spring-data (_**epsecially when we don't let Spring handle the Database related beans**_)

1. For the _@Entity_ classes to map properly to the right columns names to the database we need to set the [NamingStategy](https://docs.jboss.org/hibernate/orm/5.0/userguide/html_single/Hibernate_User_Guide.html#naming) appropriately. Since we created our Database Beans, spring **will not** take care of this. The following JPA properites must be set explicitly

    ```yml
    spring:
      application:
        name: spring-oracle-stored-proc
      datasource:
        username: pivotal
        password: bekind
        url: "jdbc:oracle:thin:@//localhost/ORCLPDB1.localdomain"
      jpa:
        hibernate:
          ddl-auto: none
          naming:
            physical-strategy: org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy
            implicit-strategy: org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy
        properties:
          hibernate:
            proc.param_null_passing: true
        show-sql: true
        database-platform: org.hibernate.dialect.Oracle10gDialect
    ```

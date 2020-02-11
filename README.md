# springboot-oracle-stored-proc

# SpringBoot Demo Project for Executing Oracle Stored Procedure

### Pre-requisite
- This requires Oracle DB. Follow this [article](https://medium.com/@brunoborges/setting-up-database-servers-for-development-on-mac-os-x-using-docker-b7f2fad056f3) to  spin an Oracle docker image. Alternatively, you can use the [docker-compose.yml](./src/main/docker/docker-compose.yml)
- Once the database is up and running, refer to [oracle-setup.sql](./docs/oracle-setup.sql) to set up the DB user, table and procedure.

We can invoke Oracle stored procedure in couple different ways.

1. Using [Spring Data JPA](https://github.com/spring-projects/spring-data-examples/tree/master/jpa/jpa21)
1. Using the _**javax.persistence**_. We used this method in our case

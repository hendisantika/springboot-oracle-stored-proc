# springboot-oracle-stored-proc

# SpringBoot Demo Project for Executing Oracle Stored Procedure

### Pre-requisite
- This requires Oracle DB. Follow this [article](https://medium.com/@brunoborges/setting-up-database-servers-for-development-on-mac-os-x-using-docker-b7f2fad056f3) to  spin an Oracle docker image. Alternatively, you can use the [docker-compose.yml](./docker-compose.yml)
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
 1. In scenarios where the database related beans are configured inside the Java class, refer below

    ```java
    @Configuration
    @EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class,
            DataSourceTransactionManagerAutoConfiguration.class})
    @EnableTransactionManagement
    @EnableJpaRepositories(basePackages = {"io.pivotal.storedproc.repository"})
    public class DBConfiguration {
    
        @Primary
        @Bean
        @ConfigurationProperties(prefix = "spring.datasource")
        public DataSource dataSource() {
            return DataSourceBuilder.create().build();
        }
    
        @Primary
        @Bean
        LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    
            HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
            vendorAdapter.setGenerateDdl(false);
            vendorAdapter.setShowSql(true);
    
            LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
            factoryBean.setDataSource(dataSource());
            factoryBean.setJpaVendorAdapter(vendorAdapter);
            factoryBean.setPackagesToScan("io.pivotal.storedproc.domain");
    
            Properties jpaProperties = new Properties();
            jpaProperties.put("hibernate.proc.param_null_passing", new Boolean(true));
            jpaProperties.put("hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class.getName());
            jpaProperties.put("hibernate.physical_naming_strategy", SpringPhysicalNamingStrategy.class.getName());
            factoryBean.setJpaProperties(jpaProperties);
    
            return factoryBean;
        }
    
        @Primary
        @Bean
        public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
            return new JpaTransactionManager(entityManagerFactory);
        }
    }
    ```

 1. Inside our _@Repository_ class we can declare the stored procedure as follows. In th example below, we have all 3 parameter types i.e. _**IN**_, _**INOUT**_ and _**OUT**_

    ```java
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
    }
    ```
 1. Another way to is to use _@NamedStoredProcedureQuery_ inside our Entity class.

    ```java
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
    ```

   1. Leveraging Functions can also be accomplished.  Inside our _@Repository_ class we can call the function as follows.
    ```java
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
                            cs.registerOutParameter(5, Types.DATE); // second OUT value
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
    ```

    ### Testing
Launch the application and open the home page in a browser to learn more about the APIs, how to test them, and what they do.
<!---
1. Perform a _PUT_ request to the endpoint `/add` ![SCREENSHOT](./docs/add.png)
1. Perform a _PUT_ request to the endpoint `/random` ![SCREENSHOT](./docs/random.png)
1. Perform a _PUT_ request to the endpoint `/procedure` ![SCREENSHOT](./docs/procedure.png)
1. Perform a _PUT_ request to the endpoint `/null`. This inserts _NULL_ value to the _**EMPLOYEE.LAST_NAME**_ column ![SCREENSHOT](./docs/null.png)
1. Perform a _GET_ request to the endpoint `/all` ![SCREENSHOT](./docs/all.png)
1. Perform a _DELETE_ request to the endpoint `/remove/{id}` ![SCREENSHOT](./docs/remove.png)
1. Perform a _PUT_ request to the endpoint `/named/procedure` ![SCREENSHOT](./docs/named.png)
1. Perform a _PUT_ request to the endpoint `/named/null`. This inserts _NULL_ value to the _**EMPLOYEE.LAST_NAME**_ column ![SCREENSHOT](./docs/named-null.png)
--->

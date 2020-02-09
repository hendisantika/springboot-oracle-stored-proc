package com.hendisantika.springbootoraclestoredproc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.swagger2.configuration.Swagger2DocumentationConfiguration;

/**
 * Created by IntelliJ IDEA.
 * Project : springboot-oracle-stored-proc
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 10/02/20
 * Time: 06.46
 */
@Configuration
@EnableSwagger2
@Import({Swagger2DocumentationConfiguration.class})
public class SwaggerConfig {

    @Value("classpath:/swagger.yml")
    private Resource swaggerResource;
}

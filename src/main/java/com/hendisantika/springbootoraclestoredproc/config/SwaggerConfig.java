package com.hendisantika.springbootoraclestoredproc.config;

import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Documentation;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.swagger2.configuration.Swagger2DocumentationConfiguration;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2MapperImpl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

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

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hendisantika.springbootoraclestoredproc.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public ServiceModelToSwagger2Mapper mapper() {
        return new ServiceModelToSwagger2MapperImpl() {
            public Swagger mapDocumentation(Documentation from) {
                try {
                    String swaggerContent =
                            StreamUtils.copyToString(SwaggerConfig.this.swaggerResource.getInputStream(),
                                    StandardCharsets.UTF_8);
                    return new SwaggerParser().parse(swaggerContent);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }
}

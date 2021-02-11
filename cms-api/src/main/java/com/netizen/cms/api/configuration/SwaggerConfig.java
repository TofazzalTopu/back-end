/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netizen.cms.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * @author Nasir
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.netizen.cms"))
                .build()
                .apiInfo(metaData());
    }

    private ApiInfo metaData() {
        ApiInfo apiInfo = new ApiInfo(
                "Content Management System",
                "A restfull api for Content Management System",
                "1.0",
                "Terms of service",
                new Contact("Netizen", "http://www.netizenbd.com/", "netizenbd.com"),
                "Netizen",
                "http://www.netizenbd.com/");
        return apiInfo;
    }
}

package com.Bootcamp.project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "bootcampservice";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(RESOURCE_ID);

    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/hello","/admin/**")
                .hasRole("ADMIN")
                .mvcMatchers("/address_add","/addressAdd","/customer/**")
                .hasAnyRole("CUSTOMER","ADMIN")
                .mvcMatchers("/seller/**")
                .hasAnyRole("SELLER","ADMIN")
                .antMatchers("/confirm","/registerCustomer","/registerSeller")
                .permitAll()

                .anyRequest()
                .permitAll()
                .and()
                .csrf()
                .disable();


    }
}

/*
 * package com.netizen.cms.api.configuration;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.security.config.annotation.authentication.builders.
 * AuthenticationManagerBuilder; import
 * org.springframework.security.config.annotation.web.builders.HttpSecurity;
 * import org.springframework.security.config.annotation.web.configuration.
 * WebSecurityConfigurerAdapter; import
 * org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; import
 * org.springframework.security.crypto.password.PasswordEncoder; import
 * org.springframework.web.cors.CorsConfiguration; import
 * org.springframework.web.cors.UrlBasedCorsConfigurationSource; import
 * org.springframework.web.filter.CorsFilter; import
 * org.springframework.web.servlet.config.annotation.CorsRegistry; import
 * org.springframework.web.servlet.config.annotation.WebMvcConfigurer; import
 * org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
 * 
 * @Configuration public class BasicSecurityConfig extends
 * WebSecurityConfigurerAdapter{
 * 
 * public static final String USER_ID = "neticms-web-user"; public static final
 * String USER_PASS = "neti-cms-web-user-pwd"; public static final String
 * ROLE_USER="USER";
 * 
 * 
 * @Bean public PasswordEncoder passwordEncoder() { return new
 * BCryptPasswordEncoder(); }
 * 
 * @Override protected void configure(HttpSecurity http) throws Exception { http
 * .csrf().disable()
 * 
 * .authorizeRequests() .antMatchers("/public/**", "/user/logout", "/swagger**",
 * "/webjars/springfox-swagger-ui/**", "/v2/api-docs",
 * "/swagger-resources/configuration/ui", "/swagger-resources",
 * "/swagger-resources/configuration/security", "/swagger-ui.html",
 * "/webjars/**").permitAll() .anyRequest().authenticated() .and() .httpBasic();
 * }
 * 
 * @Autowired public void configureGlobal(AuthenticationManagerBuilder auth)
 * throws Exception { auth .inMemoryAuthentication() .withUser(USER_ID)
 * .password(passwordEncoder().encode(USER_PASS)) .roles(ROLE_USER);
 * 
 * }
 * 
 * }
 */
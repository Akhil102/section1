package com.example.section1.config;

import com.example.section1.exceptionhandling.CustomBasicAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Profile("prod")
public class ProdProjectSecurityConfig {
    private String enco="{ldap}";
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception{
        http.requiresChannel(rcc -> rcc.anyRequest().requiresSecure() )
                .csrf((csf)-> csf.disable());
//        http.authorizeHttpRequests((request) -> request.anyRequest().denyAll());
        http.authorizeHttpRequests((request) -> request.requestMatchers("/myCards","/myLoans","/myAccount","/myBalance").authenticated()
                .requestMatchers("/notices","/contact","/error","/register").permitAll());
        http.formLogin(withDefaults());
        http.httpBasic(csb -> csb.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
        return http.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService(DataSource dataSource){
//       return new JdbcUserDetailsManager(dataSource);
//
//    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    
//To check if the password is compromised or not
//    @Bean
//    public CompromisedPasswordChecker compromisedPasswordChecker(){
//        return new HaveIBeenPwnedRestApiPasswordChecker();
//    }
//create table users(username varchar(50) not null primary key,password varchar(500) not null,enabled boolean not null);
//    create table authorities (username varchar(50) not null,authority varchar(50) not null,constraint fk_authorities_users foreign key(username) references users(username));
//    create unique index ix_auth_username on authorities (username,authority);
//INSERT INTO `customer`(`email`,`pwd`,`role`) VALUES('admin@gmail.com','{ldap}54321','admin');
}
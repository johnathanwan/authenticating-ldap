package com.example.authenticatingldap

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.*
import org.springframework.security.config.annotation.web.builders.*
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
class WebSecurityConfig: WebSecurityConfigurerAdapter() {
    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth!!
            .ldapAuthentication()
            .userDnPatterns("uid={0},ou=people")
            .groupSearchBase("ou=groups")
            .contextSource()
            .url("ldap://localhost:8389/dc=springframework,dc=org")
            .and()
            .passwordCompare()
            .passwordEncoder(BCryptPasswordEncoder())
            .passwordAttribute("userPassword")
    }

    override fun configure(http: HttpSecurity?) {
        http!!
            .authorizeRequests()
            .anyRequest()
            .fullyAuthenticated()
            .and()
            .formLogin()
    }
}
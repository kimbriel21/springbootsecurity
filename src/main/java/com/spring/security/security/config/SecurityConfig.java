package com.spring.security.security.config;

import com.spring.security.security.service.UserPrincipalDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserPrincipalDetailService userPrincipalDetailService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        inDBprovider(auth);
//        inMemoryAuthentication(auth);
    }

    private void inDBprovider(AuthenticationManagerBuilder auth){
        auth.authenticationProvider(authenticationProvider());
    }

    private void inMemoryAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("admin")
                .password("{noop}admin")
//                .roles("ADMIN")
                .authorities("ACCESS_TEST1","ACCESS_TEST2","ROLE_ADMIN")
                .and()
                .withUser("user")
                .password("{noop}user")
//                .roles("USER")
                .authorities("ACCESS_TEST1","ROLE_USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .authorizeRequests()
//                .anyRequest().permitAll()
//                .antMatchers("/**").permitAll()
                .antMatchers(HttpMethod.GET, "/home/**").hasAnyRole("USER")
//                .antMatchers("/api/public/**").authenticated()
                .antMatchers("/api/public/test2").hasAnyAuthority("ACCESS_TEST1")
                .antMatchers("/api/public/test1").hasAnyAuthority("ACCESS_TEST2")
                .antMatchers("/api/users").hasAnyAuthority("ACCESS_TEST2")
                .and()
                .csrf().disable();

        //antMatcher method permitAll, authenticated, hasRole, hasAnyRole
    }


    @Bean
    DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userPrincipalDetailService);
        return daoAuthenticationProvider;
    }

    @Bean
    PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();}
}

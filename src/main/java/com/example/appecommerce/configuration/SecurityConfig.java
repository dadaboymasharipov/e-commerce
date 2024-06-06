package com.example.appecommerce.configuration;

import com.example.appecommerce.security.JwtFilter;
import com.example.appecommerce.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Properties;

/**
 * We will configure any configurations in this class
 */
@EnableWebSecurity
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    CustomUserDetailsService customUserDetailsService;//This is our class which implemented UserDetailsService

    @Autowired
    JwtFilter jwtFilter;

    /**
     * Method to configure HttpSecurity
     *
     * @param http HttpSecurity object
     * @return SecurityFilterChain
     * @throws Exception Might throw exception on csrf
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/auth/**", "/api/open/**", "/api/user/verify").permitAll()//Open request paths
                .anyRequest()
                .authenticated();//Authenticate all other requests


        http.authenticationProvider(authenticationProvider());//giving authenticationProvider

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);//Set our own filter class before default
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//Make it stateless so
                                                                                            // after token expired user will not be able to enter system

        return http.build();
    }

    /**
     * To configure AuthenticationManagerBuilder class
     *
     * @return DaoAuthenticationProvider
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        //Creating DaoAuthenticationProvider object
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        //Setting passwordEncoder and UserDetailsService
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);

        return daoAuthenticationProvider;
    }

    /**
     * It is used to encode passwords before saving to DB or checking weather passwords are equal
     *
     * @return BCryptPasswordEncoder
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * We use it to authenticate Users
     *
     * @param authenticationConfiguration
     * @return AuthenticationManager
     * @throws Exception getAuthenticationManager() method might throw exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * This method helps us to configure email sending process
     *
     * @return JavaMailSender
     */
    @Bean
    public JavaMailSender javaMailSender() {
        //Creating JavaMailSenderImpl object
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        //Set its port
        javaMailSender.setPort(587);

        //Set its Host
        javaMailSender.setHost("smtp.gmail.com");

        //Set email and its password
        javaMailSender.setUsername("someEmail@gmail.com");
        javaMailSender.setPassword("");//Use app-password for your email

        //Get properties of javaMailSender
        Properties properties = javaMailSender.getJavaMailProperties();

        //Set properties
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.debug", "true");

        return javaMailSender;
    }
}

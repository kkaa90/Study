package com.main.app.config

import com.main.app.service.WorkerOauthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.header.writers.frameoptions.WhiteListedAllowFromStrategy
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter
import javax.servlet.Filter
import javax.servlet.http.HttpServletRequest

@Configuration
@EnableWebSecurity
class SecurityConfig{

    @Autowired
    private lateinit var workerOauthService: WorkerOauthService

    @Bean
    fun security(http: HttpSecurity) : SecurityFilterChain{
        http.formLogin().disable()
            .authorizeRequests().antMatchers("/console/**").permitAll()
            .and()
            .csrf().ignoringAntMatchers("/console/**")
            .and()
            .headers()
            .addHeaderWriter(
                XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)
            )
            .and()
            .oauth2Login()
            .userInfoEndpoint()
            .userService(workerOauthService)
        return http.build()
    }
}
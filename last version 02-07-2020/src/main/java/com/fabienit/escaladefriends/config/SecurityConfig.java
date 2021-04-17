package com.fabienit.escaladefriends.config;

import com.fabienit.escaladefriends.handler.UserAuthenticationSuccessHandler;
import com.fabienit.escaladefriends.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private UserAuthenticationSuccessHandler userAuthenticationSuccessHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/home").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/callFormInscription").permitAll()
                .antMatchers("/sites").permitAll()
                .antMatchers("/results").permitAll()
                .antMatchers("/membre/*").hasAnyRole("MEMBRE")
                .antMatchers("/utilisateurCo/*").hasAnyRole( "MEMBRE","UTILISATEURCONNECTE")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").failureUrl("/login?error=true")
                .usernameParameter("mail")
                .passwordParameter("mdp")
                .successHandler(userAuthenticationSuccessHandler)
                .and()
                .logout()
                .logoutSuccessUrl("/");

    }



    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth
                .userDetailsService(userServiceImpl)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    /*@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("admin").password("{noop}admin@2020").roles("MEMBRE");
        auth.inMemoryAuthentication().withUser("fabien").password("{noop}fabien@2020").roles("UTILISATEURCONNECTE");
    }*/

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources**/**", "/static**/**/**", "/css**/**", "/js/**", "/img/**", "/bootstrap/**");
    }
}

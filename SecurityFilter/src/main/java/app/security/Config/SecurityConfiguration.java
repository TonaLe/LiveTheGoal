package app.security.Config;


import app.security.Filter.JWTLoginFilter;
import app.security.Filter.TokenFilter;
import app.security.Service.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static app.security.Enum.UserRole.ADMIN;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final AccountServiceImpl accountService;

    @Autowired
    public SecurityConfiguration(AccountServiceImpl accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoauthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http.
               csrf().disable()
               .authorizeRequests()
               .antMatchers("/Todo/").hasRole(ADMIN.name())
               .antMatchers("/").permitAll()
               .anyRequest()
               .authenticated()
               .and()
               .formLogin()
               .loginPage("/login").permitAll()
               .and()
               .addFilterBefore(new JWTLoginFilter("/Admin/course",authenticationManager())
                       , UsernamePasswordAuthenticationFilter.class)
               .addFilterBefore(tokenFilter(), UsernamePasswordAuthenticationFilter.class)
               .headers().frameOptions().disable();
    }

    @Bean
    public DaoAuthenticationProvider daoauthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder(10));
        daoAuthenticationProvider.setUserDetailsService(accountService);
        return daoAuthenticationProvider;
    }

    @Bean
    public TokenFilter tokenFilter() throws Exception {
        TokenFilter token = new TokenFilter();
        token.setAuthenticationManager(authenticationManager());
        return token;
    }
}

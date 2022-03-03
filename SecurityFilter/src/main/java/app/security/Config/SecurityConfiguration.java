package app.security.Config;


import app.security.Filter.JWTLoginFilter;
import app.security.Filter.TokenFilter;
import app.security.Service.Impl.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static app.security.Enum.Role.ADMIN;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final AccountServiceImpl accountService;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public SecurityConfiguration(AccountServiceImpl accountService, final PasswordEncoder passwordEncoder) {
        this.accountService = accountService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoauthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http.
               csrf().disable()
               .cors()
               .and()
               .authorizeRequests()
               .antMatchers("/signup").permitAll()
               .antMatchers("/*").hasRole(ADMIN.name())
               .anyRequest()
               .authenticated()
               .and()
               .formLogin()
               .loginPage("/login").permitAll()
               .and()
               .addFilterBefore(new JWTLoginFilter("/",authenticationManager())
                       , UsernamePasswordAuthenticationFilter.class)
               .addFilterBefore(getTokenFilter(), UsernamePasswordAuthenticationFilter.class)
               .headers().frameOptions().disable();
    }

    @Bean
    public DaoAuthenticationProvider daoauthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(accountService);
        return daoAuthenticationProvider;
    }

    @Bean
    public TokenFilter getTokenFilter() throws Exception {
        TokenFilter token = new TokenFilter();
        token.setAuthenticationManager(authenticationManager());
        return token;
    }
}

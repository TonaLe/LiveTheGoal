package app.security.Config;


import app.security.Filter.JWTLoginFilter;
import app.security.Filter.TokenFilter;
import app.security.Service.Impl.AccountServiceImpl;
import com.google.common.collect.ImmutableList;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(ImmutableList.of("*"));
        configuration.setAllowedMethods(ImmutableList.of("HEAD",
                "GET", "POST", "PUT", "DELETE", "PATCH"));
        // setAllowCredentials(true) is important, otherwise:
        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
        configuration.setAllowCredentials(true);
        // setAllowedHeaders is important! Without it, OPTIONS preflight request
        // will fail with 403 Invalid CORS request
        configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

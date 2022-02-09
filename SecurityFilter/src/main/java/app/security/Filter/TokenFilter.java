package app.security.Filter;

import app.security.Service.AccountService;
import app.security.Service.TokenServiceImpl;
import com.nimbusds.jose.JOSEException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;

public class TokenFilter extends UsernamePasswordAuthenticationFilter {
    @Autowired
    private TokenServiceImpl token;

    @Autowired
    private AccountService accountService;
    private static final String HEADER = "Authorization";
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String authToken = request.getHeader(HEADER);
        LOG.info("Get username from token: ");
        try {
            if(token.validateTokenLogin(authToken)){
                String username = token.getUserNameFromToken(authToken);
                UserDetails user =  accountService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        } catch (ParseException | JOSEException e) {
            LOG.error(String.format("Cannot authenticate because of %s", e));
        }
        chain.doFilter(req,res);
    }




}

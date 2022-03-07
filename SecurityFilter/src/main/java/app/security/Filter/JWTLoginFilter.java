package app.security.Filter;


import app.security.Service.Impl.TokenServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * The type Jwt login filter.
 */
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    /**
     * The constant HEADER.
     */
    private static final String HEADER = "Authorization";

    /**
     * Instantiates a new Jwt login filter.
     *
     * @param defaultFilterProcessesUrl the default filter processes url
     * @param authenticationManager     the authentication manager
     */
    public JWTLoginFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse)
            throws AuthenticationException, IOException, ServletException {
        String bearerToken = httpServletRequest.getHeader(HEADER);
        if (StringUtils.isEmpty(bearerToken)) {
            UsernamePasswordAuthenticationToken authToken = new
                    UsernamePasswordAuthenticationToken(httpServletRequest.getParameter("username"),
                    httpServletRequest.getParameter("password"), Collections.emptyList());
            return getAuthenticationManager().authenticate(authToken);
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String username = request.getParameter("username");
        TokenServiceImpl token = new TokenServiceImpl();
        String authToken = token.generateToken(username);
        response.addHeader("Authorization", authToken);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }
}

package app.security.Filter;

import app.security.Service.AccountService;
import app.security.Service.Impl.TokenServiceImpl;
import com.nimbusds.jose.JOSEException;
import org.apache.commons.lang3.StringUtils;
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

/**
 * The type Token filter.
 */
public class TokenFilter extends UsernamePasswordAuthenticationFilter {

    /**
     * The Token.
     */
    @Autowired
    private TokenServiceImpl token;

    /**
     * The Account service.
     */
    @Autowired
    private AccountService accountService;

    /**
     * The constant HEADER.
     */
    private static final String HEADER = "Authorization";

    /**
     * The constant BEARER.
     */
    private static final String BEARER = "Bearer";

    /**
     * The Log.
     */
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final String authToken = request.getHeader(HEADER);

        if (authToken == null) {
            LOG.info("There is no token to get");
            return;
        }
        LOG.info("Get username from token: " + authToken);
        try {
            final String bearerToken = StringUtils.remove(authToken, BEARER).trim();

            if (token.validateTokenLogin(bearerToken)) {
                String username = token.getUserNameFromToken(bearerToken);
                UserDetails user = accountService.loadUserByUsername(username);

                if (user == null) {
                    return;
                }

                final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        } catch (ParseException | JOSEException e) {
            LOG.error(String.valueOf(new IllegalArgumentException("Cannot authenticate because of %s", e)));
        }
        chain.doFilter(req, res);
    }
}

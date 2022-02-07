package app.security.Service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;

import java.text.ParseException;

public interface TokenService {

    String generateToken(String username);

    byte[] generateSharedSecrect();

    JWTClaimsSet getClaimFromToken(String token) throws ParseException, JOSEException;

    String getUserNameFromToken(String token) throws ParseException, JOSEException;

    boolean validateTokenLogin(String token) throws ParseException, JOSEException;
}

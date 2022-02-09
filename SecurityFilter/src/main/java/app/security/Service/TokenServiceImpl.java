package app.security.Service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService {
    private final String SECRECT_KEY = "SECRET";
    private final String USERNAME = "Username";
    private final int EXPIRED_TIME = 86400000;
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    public String generateToken(final String username){
        String token = null;
        LOG.info("Generating token: ");
        try{
            JWSSigner signer = new MACSigner(generateSharedSecrect());
            JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
            builder.expirationTime(generateExpiredTime());
            builder.claim(USERNAME,username);
            JWTClaimsSet claimsSet = builder.build();
            SignedJWT signJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256),claimsSet);
            signJWT.sign(signer);
            token = signJWT.serialize();
        } catch (JOSEException e) {
            LOG.error(String.format("Token cannot be generated due to %s", e));
        }
        return token;
    }

    public byte[] generateSharedSecrect(){
        byte[] share = new byte[32];
        share = SECRECT_KEY.getBytes();
        return share;
    }

    private Date generateExpiredTime(){
        return new Date(System.currentTimeMillis()+EXPIRED_TIME);
    }

    public JWTClaimsSet getClaimFromToken(String token) throws ParseException, JOSEException {
        JWTClaimsSet claimsSet = null;
        LOG.info("Get claim from token: ");
        final SignedJWT signer = SignedJWT.parse(token);
        final MACVerifier verifier = new MACVerifier(generateSharedSecrect());
        if(signer.verify(verifier)){
            claimsSet =signer.getJWTClaimsSet();
        }
        return claimsSet;
    }

    private Date getExpireDateFromToken(String token) throws ParseException, JOSEException {
        final JWTClaimsSet claimsSet = getClaimFromToken(token);
        return claimsSet.getExpirationTime();
    }


    public String getUserNameFromToken(String token) throws ParseException, JOSEException {
        final JWTClaimsSet claimsSet = getClaimFromToken(token);
        return claimsSet.getStringClaim(USERNAME);
    }

    private boolean validateExpireDate(String token) throws ParseException, JOSEException {
        final Date expireDate = getExpireDateFromToken(token);
        return expireDate.before(new Date());
    }

    public boolean validateTokenLogin(String token) throws ParseException, JOSEException {
        if(token == null || token.trim().length() == 0){
            return false;
        }
        String username = getUserNameFromToken(token);
        if(username == null){
            return false;
        }
        return !validateExpireDate(token);
    }
}

package pl.lodz.p.it.zzpj2023.utils.etag;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;


import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.ApplicationException;
import jakarta.ejb.Singleton;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import pl.lodz.p.it.zzpj2023.exceptions.BaseApplicationException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.text.ParseException;

@ApplicationScoped
public class Etag {

   private static String SECRET;

    @PostConstruct
    public void init(){
        try {
            SECRET = new InitialContext().lookup("java:comp/env/ETAG_SECRET").toString();
        } catch (NamingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public boolean verifyTag(SignableEnt entity, String tag){
        String newTag = calculateSignature(entity);
        if (tag.equals(newTag)) {
            return true;
        } else {
            throw BaseApplicationException.eTagException();
        }
    }

    public boolean validateSignature(String signature) {
        try {
            JWSObject object = JWSObject.parse(signature);
            JWSVerifier verifier = new MACVerifier(SECRET);
            return object.verify(verifier);
        } catch (ParseException | JOSEException e) {
            throw BaseApplicationException.eTagException();
        }
    }

    public String calculateSignature(SignableEnt entity){
        try {
            JWSSigner signer = new MACSigner(SECRET);
            JWSObject object = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), new Payload(entity.getPayload()));

            object.sign(signer);
            return object.serialize();
        } catch (JOSEException e) {
            throw BaseApplicationException.eTagException();
        }
    }
}

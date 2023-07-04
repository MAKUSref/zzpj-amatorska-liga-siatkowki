package pl.lodz.p.it.ssbd2023.ssbd04.security;

import org.mindrot.jbcrypt.BCrypt;

public class PassordHasher {

    static public String hashPassword(String unhashedPassword){
        return BCrypt.hashpw(unhashedPassword, BCrypt.gensalt(12));
    }

    static public boolean checkPassword(String passwoordToCheck, String hashedPassword){
        return BCrypt.checkpw(passwoordToCheck, hashedPassword);
    }
}

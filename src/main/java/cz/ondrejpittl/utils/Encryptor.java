package cz.ondrejpittl.utils;

import org.mindrot.jbcrypt.BCrypt;

public class Encryptor {


    public static String bcrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

}

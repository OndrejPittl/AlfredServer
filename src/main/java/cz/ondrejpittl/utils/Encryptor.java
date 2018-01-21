package cz.ondrejpittl.utils;

import org.mindrot.jbcrypt.BCrypt;

public class Encryptor {

    public static String bcrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public static boolean verify(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }


}

package cz.ondrejpittl.business.validation;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ValidationMessages {

    private static final Map<String, String> MESSAGES = createMap();

    private static Map<String, String> createMap() {
        Map<String, String> msgs = new HashMap<>();

        // general
        msgs.put("required",                    "This field is required.");

        // user
        msgs.put("user.id.notfound",            "No User of given ID was found.");
        msgs.put("user.id.negative",            "User ID must be a positive integer.");
        msgs.put("user.firstName",              "Please enter your first name (1 – 50 characters long).");
        msgs.put("user.lastName",               "Please enter your last name (1 – 50 characters long).");
        msgs.put("user.email.unique",           "The e-mail is already taken.");
        msgs.put("user.email.pattern",          "Please enter a valid e-mail address (e.g. you@domain.com).");
        msgs.put("user.password",               "Your password must contain at least 8 and max 50 characters, including at least one letter and one number.");
        msgs.put("user.confirmPassword",        "Passwords must match.");
        msgs.put("user.confirmPassword.empty",  "Password confirmation is required.");
        msgs.put("user.sex",                    "Only male or female allowed.");
        msgs.put("user.slug.length",            "User slug must consist of at least 1 character.");


        // post
        msgs.put("post.offset.negative", "Post feed offset must be a positive integer.");
        msgs.put("post.id.negative", "Post ID must be a positive integer.");
        msgs.put("post.id.notfound", "No Post of given ID was found.");
        msgs.put("", "");
        msgs.put("", "");
        msgs.put("", "");
        msgs.put("", "");


        return Collections.unmodifiableMap(msgs);
    }

    public static String get(String key) {
        return ValidationMessages.MESSAGES.get(key);
    }
}
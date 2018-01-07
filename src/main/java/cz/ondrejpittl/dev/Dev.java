package cz.ondrejpittl.dev;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Dev {

    private static final String PREFIX = "-----> ";

    public static final boolean MOCK_TOKEN_MODE = false;



    public static void print(String msg) {
        System.out.println(Dev.PREFIX + msg);
    }

    public static void print(Object o) {
        Dev.print(String.valueOf(o));
    }

    public static void printObject(Object o) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}

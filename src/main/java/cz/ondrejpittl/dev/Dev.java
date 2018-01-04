package cz.ondrejpittl.dev;

public class Dev {

    private static final String PREFIX = "-----> ";

    public static void print(String msg) {
        System.out.println(Dev.PREFIX + msg);
    }

    public static void print(Object o) {
        Dev.print(String.valueOf(o));
    }

}

package cz.ondrejpittl.utils;

import java.text.Normalizer;

public class StringUtils {

    public static String stripAccents(String str) {
        String normalized = Normalizer.normalize(str, Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

}

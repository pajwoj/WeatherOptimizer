package pl.pajwoj;

import java.text.Normalizer;

public class Utilities {
    private static String normalize(String input) {
        return input == null ? null : Normalizer.normalize(input, Normalizer.Form.NFKD);
    }

    public static String removeDiacritics(String input) {
        String result = normalize(input).replaceAll("\\p{M}", "");
        return result.replaceAll("[łŁ]", "l");
    }
}

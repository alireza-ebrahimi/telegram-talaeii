package utils;

public class StringUtils {
    public static int countWords(String str) {
        String s = str.trim();
        int c = 1;
        int i = 0;
        while (i < s.length()) {
            if (s.charAt(i) == ' ') {
                while (s.charAt(i + 1) == ' ') {
                    i++;
                }
                c++;
            }
            i++;
        }
        return c;
    }

    public static int countWords(CharSequence str) {
        return countWords("" + str);
    }

    public static boolean isNumerical(CharSequence txt) {
        try {
            Double.parseDouble(txt.toString());
            return true;
        } catch (NumberFormatException e) {
            return false;
        } catch (Exception e2) {
            return false;
        }
    }

    public static int convertToNum(CharSequence txt) {
        int result = 0;
        try {
            result = Integer.parseInt(txt.toString());
        } catch (Exception e) {
        }
        return result;
    }
}

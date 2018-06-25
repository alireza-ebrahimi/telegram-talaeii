package utils;

public class ArrayUtil {
    public static boolean isAllEqual(int[] a, int b) {
        if (a.length == 0) {
            return false;
        }
        for (int i : a) {
            if (b != i) {
                return false;
            }
        }
        return true;
    }
}

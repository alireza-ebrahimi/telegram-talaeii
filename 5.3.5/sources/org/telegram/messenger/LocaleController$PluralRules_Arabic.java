package org.telegram.messenger;

public class LocaleController$PluralRules_Arabic extends LocaleController$PluralRules {
    public int quantityForNumber(int count) {
        int rem100 = count % 100;
        if (count == 0) {
            return 1;
        }
        if (count == 1) {
            return 2;
        }
        if (count == 2) {
            return 4;
        }
        if (rem100 >= 3 && rem100 <= 10) {
            return 8;
        }
        if (rem100 < 11 || rem100 > 99) {
            return 0;
        }
        return 16;
    }
}

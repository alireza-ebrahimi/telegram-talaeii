package org.telegram.messenger;

public class LocaleController$PluralRules_Lithuanian extends LocaleController$PluralRules {
    public int quantityForNumber(int count) {
        int rem100 = count % 100;
        int rem10 = count % 10;
        if (rem10 == 1 && (rem100 < 11 || rem100 > 19)) {
            return 2;
        }
        if (rem10 < 2 || rem10 > 9 || (rem100 >= 11 && rem100 <= 19)) {
            return 0;
        }
        return 8;
    }
}

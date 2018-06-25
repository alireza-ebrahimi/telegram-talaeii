package org.telegram.messenger;

public class LocaleController$PluralRules_Polish extends LocaleController$PluralRules {
    public int quantityForNumber(int count) {
        int rem100 = count % 100;
        int rem10 = count % 10;
        if (count == 1) {
            return 2;
        }
        if (rem10 < 2 || rem10 > 4 || ((rem100 >= 12 && rem100 <= 14) || (rem100 >= 22 && rem100 <= 24))) {
            return 0;
        }
        return 8;
    }
}

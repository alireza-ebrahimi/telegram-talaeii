package org.telegram.messenger;

public class LocaleController$PluralRules_Balkan extends LocaleController$PluralRules {
    public int quantityForNumber(int count) {
        int rem100 = count % 100;
        int rem10 = count % 10;
        if (rem10 == 1 && rem100 != 11) {
            return 2;
        }
        if (rem10 >= 2 && rem10 <= 4 && (rem100 < 12 || rem100 > 14)) {
            return 8;
        }
        if (rem10 == 0 || ((rem10 >= 5 && rem10 <= 9) || (rem100 >= 11 && rem100 <= 14))) {
            return 16;
        }
        return 0;
    }
}

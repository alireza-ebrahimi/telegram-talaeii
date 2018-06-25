package org.telegram.messenger;

public class LocaleController$PluralRules_Maltese extends LocaleController$PluralRules {
    public int quantityForNumber(int count) {
        int rem100 = count % 100;
        if (count == 1) {
            return 2;
        }
        if (count == 0 || (rem100 >= 2 && rem100 <= 10)) {
            return 8;
        }
        if (rem100 < 11 || rem100 > 19) {
            return 0;
        }
        return 16;
    }
}

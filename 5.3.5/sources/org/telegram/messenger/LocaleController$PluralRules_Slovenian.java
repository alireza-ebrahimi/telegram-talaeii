package org.telegram.messenger;

public class LocaleController$PluralRules_Slovenian extends LocaleController$PluralRules {
    public int quantityForNumber(int count) {
        int rem100 = count % 100;
        if (rem100 == 1) {
            return 2;
        }
        if (rem100 == 2) {
            return 4;
        }
        if (rem100 < 3 || rem100 > 4) {
            return 0;
        }
        return 8;
    }
}

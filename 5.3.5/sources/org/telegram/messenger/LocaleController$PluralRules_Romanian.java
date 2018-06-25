package org.telegram.messenger;

public class LocaleController$PluralRules_Romanian extends LocaleController$PluralRules {
    public int quantityForNumber(int count) {
        int rem100 = count % 100;
        if (count == 1) {
            return 2;
        }
        if (count == 0 || (rem100 >= 1 && rem100 <= 19)) {
            return 8;
        }
        return 0;
    }
}

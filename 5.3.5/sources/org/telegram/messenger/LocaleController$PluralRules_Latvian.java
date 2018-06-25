package org.telegram.messenger;

public class LocaleController$PluralRules_Latvian extends LocaleController$PluralRules {
    public int quantityForNumber(int count) {
        if (count == 0) {
            return 1;
        }
        if (count % 10 != 1 || count % 100 == 11) {
            return 0;
        }
        return 2;
    }
}

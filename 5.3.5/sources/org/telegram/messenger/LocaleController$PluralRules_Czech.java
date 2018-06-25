package org.telegram.messenger;

public class LocaleController$PluralRules_Czech extends LocaleController$PluralRules {
    public int quantityForNumber(int count) {
        if (count == 1) {
            return 2;
        }
        if (count < 2 || count > 4) {
            return 0;
        }
        return 8;
    }
}

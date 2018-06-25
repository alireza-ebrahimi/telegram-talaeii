package org.telegram.messenger;

public class LocaleController$PluralRules_Two extends LocaleController$PluralRules {
    public int quantityForNumber(int count) {
        if (count == 1) {
            return 2;
        }
        if (count == 2) {
            return 4;
        }
        return 0;
    }
}

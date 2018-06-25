package org.telegram.messenger;

public class LocaleController$PluralRules_Zero extends LocaleController$PluralRules {
    public int quantityForNumber(int count) {
        if (count == 0 || count == 1) {
            return 2;
        }
        return 0;
    }
}

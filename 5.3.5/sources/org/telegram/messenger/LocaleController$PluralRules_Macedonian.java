package org.telegram.messenger;

public class LocaleController$PluralRules_Macedonian extends LocaleController$PluralRules {
    public int quantityForNumber(int count) {
        if (count % 10 != 1 || count == 11) {
            return 0;
        }
        return 2;
    }
}

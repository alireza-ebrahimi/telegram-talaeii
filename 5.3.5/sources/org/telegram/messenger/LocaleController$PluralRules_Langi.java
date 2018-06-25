package org.telegram.messenger;

public class LocaleController$PluralRules_Langi extends LocaleController$PluralRules {
    public int quantityForNumber(int count) {
        if (count == 0) {
            return 1;
        }
        if (count <= 0 || count >= 2) {
            return 0;
        }
        return 2;
    }
}

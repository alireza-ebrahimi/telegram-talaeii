package org.telegram.messenger;

public class LocaleController$PluralRules_Welsh extends LocaleController$PluralRules {
    public int quantityForNumber(int count) {
        if (count == 0) {
            return 1;
        }
        if (count == 1) {
            return 2;
        }
        if (count == 2) {
            return 4;
        }
        if (count == 3) {
            return 8;
        }
        if (count == 6) {
            return 16;
        }
        return 0;
    }
}

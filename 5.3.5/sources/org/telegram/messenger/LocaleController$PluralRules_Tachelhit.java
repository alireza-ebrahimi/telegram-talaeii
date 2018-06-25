package org.telegram.messenger;

public class LocaleController$PluralRules_Tachelhit extends LocaleController$PluralRules {
    public int quantityForNumber(int count) {
        if (count >= 0 && count <= 1) {
            return 2;
        }
        if (count < 2 || count > 10) {
            return 0;
        }
        return 8;
    }
}

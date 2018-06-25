package org.telegram.messenger;

public class LocaleController$PluralRules_One extends LocaleController$PluralRules {
    public int quantityForNumber(int count) {
        return count == 1 ? 2 : 0;
    }
}

package com.stripe.android.util;

import android.support.annotation.Nullable;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

public class StripeTextUtils {
    public static boolean hasAnyPrefix(String number, String... prefixes) {
        if (number == null) {
            return false;
        }
        for (String prefix : prefixes) {
            if (number.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isWholePositiveNumber(String value) {
        if (value == null) {
            return false;
        }
        for (int i = 0; i < value.length(); i++) {
            if (!Character.isDigit(value.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static String nullIfBlank(String value) {
        if (isBlank(value)) {
            return null;
        }
        return value;
    }

    public static boolean isBlank(String value) {
        return value == null || value.trim().length() == 0;
    }

    @Nullable
    public static String asCardBrand(@Nullable String possibleCardType) {
        if (isBlank(possibleCardType)) {
            return null;
        }
        if (Card.AMERICAN_EXPRESS.equalsIgnoreCase(possibleCardType)) {
            return Card.AMERICAN_EXPRESS;
        }
        if (Card.MASTERCARD.equalsIgnoreCase(possibleCardType)) {
            return Card.MASTERCARD;
        }
        if (Card.DINERS_CLUB.equalsIgnoreCase(possibleCardType)) {
            return Card.DINERS_CLUB;
        }
        if (Card.DISCOVER.equalsIgnoreCase(possibleCardType)) {
            return Card.DISCOVER;
        }
        if (Card.JCB.equalsIgnoreCase(possibleCardType)) {
            return Card.JCB;
        }
        if (Card.VISA.equalsIgnoreCase(possibleCardType)) {
            return Card.VISA;
        }
        return Card.UNKNOWN;
    }

    @Nullable
    public static String asFundingType(@Nullable String possibleFundingType) {
        if (isBlank(possibleFundingType)) {
            return null;
        }
        if (Card.FUNDING_CREDIT.equalsIgnoreCase(possibleFundingType)) {
            return Card.FUNDING_CREDIT;
        }
        if (Card.FUNDING_DEBIT.equalsIgnoreCase(possibleFundingType)) {
            return Card.FUNDING_DEBIT;
        }
        if (Card.FUNDING_PREPAID.equalsIgnoreCase(possibleFundingType)) {
            return Card.FUNDING_PREPAID;
        }
        return "unknown";
    }

    @Nullable
    public static String asTokenType(@Nullable String possibleTokenType) {
        if (Token.TYPE_CARD.equals(possibleTokenType)) {
            return Token.TYPE_CARD;
        }
        return null;
    }
}

package com.stripe.android.util;

import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class StripeNetworkUtils {
    public static Map<String, Object> hashMapFromCard(Card card) {
        Map<String, Object> tokenParams = new HashMap();
        Map<String, Object> cardParams = new HashMap();
        cardParams.put("number", StripeTextUtils.nullIfBlank(card.getNumber()));
        cardParams.put("cvc", StripeTextUtils.nullIfBlank(card.getCVC()));
        cardParams.put("exp_month", card.getExpMonth());
        cardParams.put("exp_year", card.getExpYear());
        cardParams.put("name", StripeTextUtils.nullIfBlank(card.getName()));
        cardParams.put(Param.CURRENCY, StripeTextUtils.nullIfBlank(card.getCurrency()));
        cardParams.put("address_line1", StripeTextUtils.nullIfBlank(card.getAddressLine1()));
        cardParams.put("address_line2", StripeTextUtils.nullIfBlank(card.getAddressLine2()));
        cardParams.put("address_city", StripeTextUtils.nullIfBlank(card.getAddressCity()));
        cardParams.put("address_zip", StripeTextUtils.nullIfBlank(card.getAddressZip()));
        cardParams.put("address_state", StripeTextUtils.nullIfBlank(card.getAddressState()));
        cardParams.put("address_country", StripeTextUtils.nullIfBlank(card.getAddressCountry()));
        Iterator it = new HashSet(cardParams.keySet()).iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            if (cardParams.get(key) == null) {
                cardParams.remove(key);
            }
        }
        tokenParams.put(Token.TYPE_CARD, cardParams);
        return tokenParams;
    }
}

package com.stripe.android.net;

import android.support.annotation.NonNull;
import com.stripe.android.model.Card;
import com.stripe.android.util.StripeJsonUtils;
import com.stripe.android.util.StripeTextUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class CardParser {
    static final String FIELD_ADDRESS_CITY = "address_city";
    static final String FIELD_ADDRESS_COUNTRY = "address_country";
    static final String FIELD_ADDRESS_LINE1 = "address_line1";
    static final String FIELD_ADDRESS_LINE2 = "address_line2";
    static final String FIELD_ADDRESS_STATE = "address_state";
    static final String FIELD_ADDRESS_ZIP = "address_zip";
    static final String FIELD_BRAND = "brand";
    static final String FIELD_COUNTRY = "country";
    static final String FIELD_CURRENCY = "currency";
    static final String FIELD_EXP_MONTH = "exp_month";
    static final String FIELD_EXP_YEAR = "exp_year";
    static final String FIELD_FINGERPRINT = "fingerprint";
    static final String FIELD_FUNDING = "funding";
    static final String FIELD_LAST4 = "last4";
    static final String FIELD_NAME = "name";

    @NonNull
    public static Card parseCard(String jsonCard) throws JSONException {
        return parseCard(new JSONObject(jsonCard));
    }

    @NonNull
    public static Card parseCard(@NonNull JSONObject objectCard) throws JSONException {
        return new Card(null, Integer.valueOf(objectCard.getInt(FIELD_EXP_MONTH)), Integer.valueOf(objectCard.getInt(FIELD_EXP_YEAR)), null, StripeJsonUtils.optString(objectCard, FIELD_NAME), StripeJsonUtils.optString(objectCard, FIELD_ADDRESS_LINE1), StripeJsonUtils.optString(objectCard, FIELD_ADDRESS_LINE2), StripeJsonUtils.optString(objectCard, FIELD_ADDRESS_CITY), StripeJsonUtils.optString(objectCard, FIELD_ADDRESS_STATE), StripeJsonUtils.optString(objectCard, FIELD_ADDRESS_ZIP), StripeJsonUtils.optString(objectCard, FIELD_ADDRESS_COUNTRY), StripeTextUtils.asCardBrand(StripeJsonUtils.optString(objectCard, FIELD_BRAND)), StripeJsonUtils.optString(objectCard, FIELD_LAST4), StripeJsonUtils.optString(objectCard, FIELD_FINGERPRINT), StripeTextUtils.asFundingType(StripeJsonUtils.optString(objectCard, FIELD_FUNDING)), StripeJsonUtils.optString(objectCard, FIELD_COUNTRY), StripeJsonUtils.optString(objectCard, "currency"));
    }
}

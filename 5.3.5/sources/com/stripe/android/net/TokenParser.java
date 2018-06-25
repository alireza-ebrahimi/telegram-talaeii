package com.stripe.android.net;

import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.util.StripeJsonUtils;
import com.stripe.android.util.StripeTextUtils;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

public class TokenParser {
    private static final String FIELD_CARD = "card";
    private static final String FIELD_CREATED = "created";
    private static final String FIELD_ID = "id";
    private static final String FIELD_LIVEMODE = "livemode";
    private static final String FIELD_TYPE = "type";
    private static final String FIELD_USED = "used";

    public static Token parseToken(String jsonToken) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonToken);
        String tokenId = StripeJsonUtils.getString(jsonObject, "id");
        Long createdTimeStamp = Long.valueOf(jsonObject.getLong(FIELD_CREATED));
        Boolean liveMode = Boolean.valueOf(jsonObject.getBoolean(FIELD_LIVEMODE));
        String tokenType = StripeTextUtils.asTokenType(StripeJsonUtils.getString(jsonObject, "type"));
        Boolean used = Boolean.valueOf(jsonObject.getBoolean(FIELD_USED));
        Card card = CardParser.parseCard(jsonObject.getJSONObject("card"));
        return new Token(tokenId, liveMode.booleanValue(), new Date(createdTimeStamp.longValue() * 1000), used, card, tokenType);
    }
}

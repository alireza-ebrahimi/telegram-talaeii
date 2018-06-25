package com.stripe.android.model;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Date;

public class Token {
    public static final String TYPE_CARD = "card";
    private final Card mCard;
    private final Date mCreated;
    private final String mId;
    private final boolean mLivemode;
    private final String mType;
    private final boolean mUsed;

    @Retention(RetentionPolicy.SOURCE)
    public @interface TokenType {
    }

    public Token(String id, boolean livemode, Date created, Boolean used, Card card, String type) {
        this.mId = id;
        this.mType = type;
        this.mCreated = created;
        this.mLivemode = livemode;
        this.mCard = card;
        this.mUsed = used.booleanValue();
    }

    public Date getCreated() {
        return this.mCreated;
    }

    public String getId() {
        return this.mId;
    }

    public boolean getLivemode() {
        return this.mLivemode;
    }

    public boolean getUsed() {
        return this.mUsed;
    }

    public String getType() {
        return this.mType;
    }

    public Card getCard() {
        return this.mCard;
    }
}

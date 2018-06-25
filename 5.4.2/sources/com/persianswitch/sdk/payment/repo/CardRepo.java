package com.persianswitch.sdk.payment.repo;

import android.content.Context;
import com.persianswitch.sdk.base.db.phoenix.repo.PhoenixRepo;
import com.persianswitch.sdk.payment.database.SDKDatabase;
import com.persianswitch.sdk.payment.model.UserCard;
import com.persianswitch.sdk.payment.model.UserCard.CardTable;

public final class CardRepo extends PhoenixRepo<Long, UserCard> {
    public CardRepo(Context context) {
        super(new SDKDatabase(context), new CardTable());
    }
}

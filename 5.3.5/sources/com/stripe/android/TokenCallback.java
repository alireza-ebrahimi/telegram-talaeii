package com.stripe.android;

import com.stripe.android.model.Token;

public interface TokenCallback {
    void onError(Exception exception);

    void onSuccess(Token token);
}

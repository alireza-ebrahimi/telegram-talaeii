package com.persianswitch.sdk.payment;

import android.os.Bundle;

public interface StateRecoverable {
    void onRecoverInstanceState(Bundle bundle);

    void onSaveInstanceState(Bundle bundle);
}

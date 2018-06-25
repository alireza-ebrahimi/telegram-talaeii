package com.persianswitch.sdk.base;

import android.content.Context;

public interface BaseContract {

    public interface ActionListener<V extends View> {
        V getView();
    }

    public interface View<P extends ActionListener> {
        Context getApplicationContext();

        P getPresenter();
    }
}

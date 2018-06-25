package com.persianswitch.sdk.payment.managers.suggestion;

public interface SuggestionListener<T> {
    void onClear();

    void onSelect(T t);
}

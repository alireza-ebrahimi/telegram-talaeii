package com.persianswitch.sdk.payment.managers;

public interface ProtocolConverter<T> {
    T deserialize(String str);

    String serialize(T t);
}

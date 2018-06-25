package com.persianswitch.sdk.base.utils.func;

public class Option<T> implements Functional {
    T wrapperObject;

    public Option(T wrapperObject) {
        this.wrapperObject = wrapperObject;
    }

    public T getOrElse(T defaultValue) {
        return this.wrapperObject == null ? defaultValue : this.wrapperObject;
    }

    public static <E> Option from(E wrapperObject) {
        return new Option(wrapperObject);
    }

    public static <E> E getOrDefault(E wrapperObject, E defaultObject) {
        return new Option(wrapperObject).getOrElse(defaultObject);
    }
}

package com.persianswitch.sdk.base.utils.func.tuple;

public class Tuple2<T, E> implements Tuple {
    public final T first;
    public final E second;

    public Tuple2(T first, E second) {
        this.first = first;
        this.second = second;
    }

    public static <T, E> Tuple2<T, E> with(T first, E second) {
        return new Tuple2(first, second);
    }

    public String toString() {
        return this.first.toString() + "," + this.second.toString();
    }
}

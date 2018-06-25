package com.persianswitch.sdk.base.utils.func.tuple;

public class Tuple3<T, E, F> extends Tuple2<T, E> {
    public F third;

    public Tuple3(T first, E second, F third) {
        super(first, second);
        this.third = third;
    }

    public static <T, E, F> Tuple3<T, E, F> with(T first, E second, F third) {
        return new Tuple3(first, second, third);
    }

    public String toString() {
        return super.toString() + "," + this.third.toString();
    }
}

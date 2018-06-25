package com.persianswitch.sdk.base.utils.func;

public final class Try<T> implements Functional {
    private final Func0<T> mThrowableBlock;

    Try(Func0<T> throwableBlock) {
        this.mThrowableBlock = throwableBlock;
    }

    public static <T> Try<T> block(Func0<T> throwableBlock) {
        if (throwableBlock != null) {
            return new Try(throwableBlock);
        }
        throw new IllegalStateException("block can not be null");
    }

    public T evaluateOrElse(T defaultValueInException) {
        try {
            defaultValueInException = evaluate();
        } catch (Exception e) {
        }
        return defaultValueInException;
    }

    public T evaluate() {
        return this.mThrowableBlock.apply();
    }
}

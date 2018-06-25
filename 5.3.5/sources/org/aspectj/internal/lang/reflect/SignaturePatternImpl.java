package org.aspectj.internal.lang.reflect;

import org.aspectj.lang.reflect.SignaturePattern;

public class SignaturePatternImpl implements SignaturePattern {
    private String sigPattern;

    public SignaturePatternImpl(String pattern) {
        this.sigPattern = pattern;
    }

    public String asString() {
        return this.sigPattern;
    }

    public String toString() {
        return asString();
    }
}

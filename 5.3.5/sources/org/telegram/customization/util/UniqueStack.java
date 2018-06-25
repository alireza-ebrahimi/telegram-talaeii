package org.telegram.customization.util;

import java.util.Stack;

public class UniqueStack<E> extends Stack<E> {
    public E push(E object) {
        remove(object);
        return super.push(object);
    }
}

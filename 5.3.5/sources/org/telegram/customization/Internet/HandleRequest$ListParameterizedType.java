package org.telegram.customization.Internet;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

class HandleRequest$ListParameterizedType implements ParameterizedType {
    private Type type;

    private HandleRequest$ListParameterizedType(Type type) {
        this.type = type;
    }

    public Type[] getActualTypeArguments() {
        return new Type[]{this.type};
    }

    public Type getRawType() {
        return BaseResponseModel.class;
    }

    public Type getOwnerType() {
        return null;
    }

    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}

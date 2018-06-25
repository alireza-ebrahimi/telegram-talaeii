package org.aspectj.internal.lang.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.StringTokenizer;
import org.aspectj.lang.reflect.AjTypeSystem;

public class StringToType {
    public static Type[] commaSeparatedListToTypeArray(String typeNames, Class classScope) throws ClassNotFoundException {
        StringTokenizer strTok = new StringTokenizer(typeNames, ",");
        Type[] ret = new Type[strTok.countTokens()];
        int index = 0;
        while (strTok.hasMoreTokens()) {
            int index2 = index + 1;
            ret[index] = stringToType(strTok.nextToken().trim(), classScope);
            index = index2;
        }
        return ret;
    }

    public static Type stringToType(String typeName, Class classScope) throws ClassNotFoundException {
        try {
            if (typeName.indexOf("<") == -1) {
                return AjTypeSystem.getAjType(Class.forName(typeName, false, classScope.getClassLoader()));
            }
            return makeParameterizedType(typeName, classScope);
        } catch (ClassNotFoundException e) {
            TypeVariable[] tVars = classScope.getTypeParameters();
            for (int i = 0; i < tVars.length; i++) {
                if (tVars[i].getName().equals(typeName)) {
                    return tVars[i];
                }
            }
            throw new ClassNotFoundException(typeName);
        }
    }

    private static Type makeParameterizedType(String typeName, Class classScope) throws ClassNotFoundException {
        int paramStart = typeName.indexOf(60);
        final Class baseClass = Class.forName(typeName.substring(0, paramStart), false, classScope.getClassLoader());
        final Type[] typeParams = commaSeparatedListToTypeArray(typeName.substring(paramStart + 1, typeName.lastIndexOf(62)), classScope);
        return new ParameterizedType() {
            public Type[] getActualTypeArguments() {
                return typeParams;
            }

            public Type getRawType() {
                return baseClass;
            }

            public Type getOwnerType() {
                return baseClass.getEnclosingClass();
            }
        };
    }
}

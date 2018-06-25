package org.aspectj.lang.reflect;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import org.aspectj.internal.lang.reflect.AjTypeImpl;

public class AjTypeSystem {
    private static Map<Class, WeakReference<AjType>> ajTypes = Collections.synchronizedMap(new WeakHashMap());

    public static <T> AjType<T> getAjType(Class<T> fromClass) {
        WeakReference<AjType> weakRefToAjType = (WeakReference) ajTypes.get(fromClass);
        AjType<T> theAjType;
        if (weakRefToAjType != null) {
            theAjType = (AjType) weakRefToAjType.get();
            if (theAjType != null) {
                return theAjType;
            }
            theAjType = new AjTypeImpl(fromClass);
            ajTypes.put(fromClass, new WeakReference(theAjType));
            return theAjType;
        }
        theAjType = new AjTypeImpl(fromClass);
        ajTypes.put(fromClass, new WeakReference(theAjType));
        return theAjType;
    }
}

package org.aspectj.lang;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Aspects {
    private static final String ASPECTOF = "aspectOf";
    private static final Class[] EMPTY_CLASS_ARRAY = new Class[0];
    private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
    private static final String HASASPECT = "hasAspect";
    private static final Class[] PEROBJECT_CLASS_ARRAY = new Class[]{Object.class};
    private static final Class[] PERTYPEWITHIN_CLASS_ARRAY = new Class[]{Class.class};

    public static <T> T aspectOf(Class<T> aspectClass) throws NoAspectBoundException {
        try {
            return getSingletonOrThreadAspectOf(aspectClass).invoke(null, EMPTY_OBJECT_ARRAY);
        } catch (InvocationTargetException e) {
            throw new NoAspectBoundException(aspectClass.getName(), e);
        } catch (Exception e2) {
            throw new NoAspectBoundException(aspectClass.getName(), e2);
        }
    }

    public static <T> T aspectOf(Class<T> aspectClass, Object perObject) throws NoAspectBoundException {
        try {
            return getPerObjectAspectOf(aspectClass).invoke(null, new Object[]{perObject});
        } catch (InvocationTargetException e) {
            throw new NoAspectBoundException(aspectClass.getName(), e);
        } catch (Exception e2) {
            throw new NoAspectBoundException(aspectClass.getName(), e2);
        }
    }

    public static <T> T aspectOf(Class<T> aspectClass, Class<?> perTypeWithin) throws NoAspectBoundException {
        try {
            return getPerTypeWithinAspectOf(aspectClass).invoke(null, new Object[]{perTypeWithin});
        } catch (InvocationTargetException e) {
            throw new NoAspectBoundException(aspectClass.getName(), e);
        } catch (Exception e2) {
            throw new NoAspectBoundException(aspectClass.getName(), e2);
        }
    }

    public static boolean hasAspect(Class<?> aspectClass) throws NoAspectBoundException {
        try {
            return ((Boolean) getSingletonOrThreadHasAspect(aspectClass).invoke(null, EMPTY_OBJECT_ARRAY)).booleanValue();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean hasAspect(Class<?> aspectClass, Object perObject) throws NoAspectBoundException {
        try {
            return ((Boolean) getPerObjectHasAspect(aspectClass).invoke(null, new Object[]{perObject})).booleanValue();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean hasAspect(Class<?> aspectClass, Class<?> perTypeWithin) throws NoAspectBoundException {
        try {
            return ((Boolean) getPerTypeWithinHasAspect(aspectClass).invoke(null, new Object[]{perTypeWithin})).booleanValue();
        } catch (Exception e) {
            return false;
        }
    }

    private static Method getSingletonOrThreadAspectOf(Class<?> aspectClass) throws NoSuchMethodException {
        return checkAspectOf(aspectClass.getDeclaredMethod(ASPECTOF, EMPTY_CLASS_ARRAY), aspectClass);
    }

    private static Method getPerObjectAspectOf(Class<?> aspectClass) throws NoSuchMethodException {
        return checkAspectOf(aspectClass.getDeclaredMethod(ASPECTOF, PEROBJECT_CLASS_ARRAY), aspectClass);
    }

    private static Method getPerTypeWithinAspectOf(Class<?> aspectClass) throws NoSuchMethodException {
        return checkAspectOf(aspectClass.getDeclaredMethod(ASPECTOF, PERTYPEWITHIN_CLASS_ARRAY), aspectClass);
    }

    private static Method checkAspectOf(Method method, Class<?> aspectClass) throws NoSuchMethodException {
        method.setAccessible(true);
        if (method.isAccessible() && Modifier.isPublic(method.getModifiers()) && Modifier.isStatic(method.getModifiers())) {
            return method;
        }
        throw new NoSuchMethodException(aspectClass.getName() + ".aspectOf(..) is not accessible public static");
    }

    private static Method getSingletonOrThreadHasAspect(Class aspectClass) throws NoSuchMethodException {
        return checkHasAspect(aspectClass.getDeclaredMethod(HASASPECT, EMPTY_CLASS_ARRAY), aspectClass);
    }

    private static Method getPerObjectHasAspect(Class aspectClass) throws NoSuchMethodException {
        return checkHasAspect(aspectClass.getDeclaredMethod(HASASPECT, PEROBJECT_CLASS_ARRAY), aspectClass);
    }

    private static Method getPerTypeWithinHasAspect(Class aspectClass) throws NoSuchMethodException {
        return checkHasAspect(aspectClass.getDeclaredMethod(HASASPECT, PERTYPEWITHIN_CLASS_ARRAY), aspectClass);
    }

    private static Method checkHasAspect(Method method, Class aspectClass) throws NoSuchMethodException {
        method.setAccessible(true);
        if (method.isAccessible() && Modifier.isPublic(method.getModifiers()) && Modifier.isStatic(method.getModifiers())) {
            return method;
        }
        throw new NoSuchMethodException(aspectClass.getName() + ".hasAspect(..) is not accessible public static");
    }
}

package org.aspectj.lang;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Aspects14 {
    private static final String ASPECTOF = "aspectOf";
    private static final Class[] EMPTY_CLASS_ARRAY = new Class[0];
    private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
    private static final String HASASPECT = "hasAspect";
    private static final Class[] PEROBJECT_CLASS_ARRAY;
    private static final Class[] PERTYPEWITHIN_CLASS_ARRAY;
    static Class class$java$lang$Class;
    static Class class$java$lang$Object;

    static {
        Class class$;
        Class[] clsArr = new Class[1];
        if (class$java$lang$Object == null) {
            class$ = class$("java.lang.Object");
            class$java$lang$Object = class$;
        } else {
            class$ = class$java$lang$Object;
        }
        clsArr[0] = class$;
        PEROBJECT_CLASS_ARRAY = clsArr;
        clsArr = new Class[1];
        if (class$java$lang$Class == null) {
            class$ = class$("java.lang.Class");
            class$java$lang$Class = class$;
        } else {
            class$ = class$java$lang$Class;
        }
        clsArr[0] = class$;
        PERTYPEWITHIN_CLASS_ARRAY = clsArr;
    }

    static Class class$(String x0) {
        try {
            return Class.forName(x0);
        } catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError(x1.getMessage());
        }
    }

    public static Object aspectOf(Class aspectClass) throws NoAspectBoundException {
        try {
            return getSingletonOrThreadAspectOf(aspectClass).invoke(null, EMPTY_OBJECT_ARRAY);
        } catch (InvocationTargetException e) {
            throw new NoAspectBoundException(aspectClass.getName(), e);
        } catch (Exception e2) {
            throw new NoAspectBoundException(aspectClass.getName(), e2);
        }
    }

    public static Object aspectOf(Class aspectClass, Object perObject) throws NoAspectBoundException {
        try {
            return getPerObjectAspectOf(aspectClass).invoke(null, new Object[]{perObject});
        } catch (InvocationTargetException e) {
            throw new NoAspectBoundException(aspectClass.getName(), e);
        } catch (Exception e2) {
            throw new NoAspectBoundException(aspectClass.getName(), e2);
        }
    }

    public static Object aspectOf(Class aspectClass, Class perTypeWithin) throws NoAspectBoundException {
        try {
            return getPerTypeWithinAspectOf(aspectClass).invoke(null, new Object[]{perTypeWithin});
        } catch (InvocationTargetException e) {
            throw new NoAspectBoundException(aspectClass.getName(), e);
        } catch (Exception e2) {
            throw new NoAspectBoundException(aspectClass.getName(), e2);
        }
    }

    public static boolean hasAspect(Class aspectClass) throws NoAspectBoundException {
        try {
            return ((Boolean) getSingletonOrThreadHasAspect(aspectClass).invoke(null, EMPTY_OBJECT_ARRAY)).booleanValue();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean hasAspect(Class aspectClass, Object perObject) throws NoAspectBoundException {
        try {
            return ((Boolean) getPerObjectHasAspect(aspectClass).invoke(null, new Object[]{perObject})).booleanValue();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean hasAspect(Class aspectClass, Class perTypeWithin) throws NoAspectBoundException {
        try {
            return ((Boolean) getPerTypeWithinHasAspect(aspectClass).invoke(null, new Object[]{perTypeWithin})).booleanValue();
        } catch (Exception e) {
            return false;
        }
    }

    private static Method getSingletonOrThreadAspectOf(Class aspectClass) throws NoSuchMethodException {
        return checkAspectOf(aspectClass.getDeclaredMethod(ASPECTOF, EMPTY_CLASS_ARRAY), aspectClass);
    }

    private static Method getPerObjectAspectOf(Class aspectClass) throws NoSuchMethodException {
        return checkAspectOf(aspectClass.getDeclaredMethod(ASPECTOF, PEROBJECT_CLASS_ARRAY), aspectClass);
    }

    private static Method getPerTypeWithinAspectOf(Class aspectClass) throws NoSuchMethodException {
        return checkAspectOf(aspectClass.getDeclaredMethod(ASPECTOF, PERTYPEWITHIN_CLASS_ARRAY), aspectClass);
    }

    private static Method checkAspectOf(Method method, Class aspectClass) throws NoSuchMethodException {
        method.setAccessible(true);
        if (method.isAccessible() && Modifier.isPublic(method.getModifiers()) && Modifier.isStatic(method.getModifiers())) {
            return method;
        }
        throw new NoSuchMethodException(new StringBuffer().append(aspectClass.getName()).append(".aspectOf(..) is not accessible public static").toString());
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
        throw new NoSuchMethodException(new StringBuffer().append(aspectClass.getName()).append(".hasAspect(..) is not accessible public static").toString());
    }
}

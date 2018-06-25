package com.coremedia.iso;

import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.UserBox;
import com.google.android.gms.measurement.AppMeasurement.Param;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropertyBoxParserImpl extends AbstractBoxParser {
    static String[] EMPTY_STRING_ARRAY = new String[0];
    StringBuilder buildLookupStrings = new StringBuilder();
    String clazzName;
    Pattern constuctorPattern = Pattern.compile("(.*)\\((.*?)\\)");
    Properties mapping;
    String[] param;

    public PropertyBoxParserImpl(String... customProperties) {
        InputStream customIS;
        InputStream is = getClass().getResourceAsStream("/isoparser-default.properties");
        try {
            this.mapping = new Properties();
            this.mapping.load(is);
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            if (cl == null) {
                cl = ClassLoader.getSystemClassLoader();
            }
            Enumeration<URL> enumeration = cl.getResources("isoparser-custom.properties");
            while (enumeration.hasMoreElements()) {
                customIS = ((URL) enumeration.nextElement()).openStream();
                this.mapping.load(customIS);
                customIS.close();
            }
            for (String customProperty : customProperties) {
                this.mapping.load(getClass().getResourceAsStream(customProperty));
            }
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e2) {
            throw new RuntimeException(e2);
        } catch (Throwable th) {
            try {
                is.close();
            } catch (IOException e22) {
                e22.printStackTrace();
            }
        }
    }

    public PropertyBoxParserImpl(Properties mapping) {
        this.mapping = mapping;
    }

    public Box createBox(String type, byte[] userType, String parent) {
        invoke(type, userType, parent);
        try {
            Class<Box> clazz = Class.forName(this.clazzName);
            if (this.param.length <= 0) {
                return (Box) clazz.newInstance();
            }
            Class[] constructorArgsClazz = new Class[this.param.length];
            Object[] constructorArgs = new Object[this.param.length];
            for (int i = 0; i < this.param.length; i++) {
                if ("userType".equals(this.param[i])) {
                    constructorArgs[i] = userType;
                    constructorArgsClazz[i] = byte[].class;
                } else if (Param.TYPE.equals(this.param[i])) {
                    constructorArgs[i] = type;
                    constructorArgsClazz[i] = String.class;
                } else if ("parent".equals(this.param[i])) {
                    constructorArgs[i] = parent;
                    constructorArgsClazz[i] = String.class;
                } else {
                    throw new InternalError("No such param: " + this.param[i]);
                }
            }
            return (Box) clazz.getConstructor(constructorArgsClazz).newInstance(constructorArgs);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e2) {
            throw new RuntimeException(e2);
        } catch (IllegalAccessException e3) {
            throw new RuntimeException(e3);
        } catch (InvocationTargetException e4) {
            throw new RuntimeException(e4);
        } catch (NoSuchMethodException e5) {
            throw new RuntimeException(e5);
        }
    }

    public void invoke(String type, byte[] userType, String parent) {
        String constructor;
        if (userType == null) {
            constructor = this.mapping.getProperty(type);
            if (constructor == null) {
                String lookup = this.buildLookupStrings.append(parent).append('-').append(type).toString();
                this.buildLookupStrings.setLength(0);
                constructor = this.mapping.getProperty(lookup);
            }
        } else if (UserBox.TYPE.equals(type)) {
            constructor = this.mapping.getProperty("uuid[" + Hex.encodeHex(userType).toUpperCase() + "]");
            if (constructor == null) {
                constructor = this.mapping.getProperty(new StringBuilder(String.valueOf(parent)).append("-uuid[").append(Hex.encodeHex(userType).toUpperCase()).append("]").toString());
            }
            if (constructor == null) {
                constructor = this.mapping.getProperty(UserBox.TYPE);
            }
        } else {
            throw new RuntimeException("we have a userType but no uuid box type. Something's wrong");
        }
        if (constructor == null) {
            constructor = this.mapping.getProperty("default");
        }
        if (constructor == null) {
            throw new RuntimeException("No box object found for " + type);
        } else if (constructor.endsWith(")")) {
            Matcher m = this.constuctorPattern.matcher(constructor);
            if (m.matches()) {
                this.clazzName = m.group(1);
                if (m.group(2).length() == 0) {
                    this.param = EMPTY_STRING_ARRAY;
                    return;
                } else {
                    this.param = m.group(2).length() > 0 ? m.group(2).split(",") : new String[0];
                    return;
                }
            }
            throw new RuntimeException("Cannot work with that constructor: " + constructor);
        } else {
            this.param = EMPTY_STRING_ARRAY;
            this.clazzName = constructor;
        }
    }
}

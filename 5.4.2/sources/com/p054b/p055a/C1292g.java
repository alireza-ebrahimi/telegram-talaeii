package com.p054b.p055a;

import com.google.android.gms.measurement.AppMeasurement.Param;
import com.p054b.p055a.p056a.C1248b;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* renamed from: com.b.a.g */
public class C1292g extends C1287a {
    /* renamed from: g */
    static String[] f3845g = new String[0];
    /* renamed from: b */
    Properties f3846b;
    /* renamed from: c */
    Pattern f3847c = Pattern.compile("(.*)\\((.*?)\\)");
    /* renamed from: d */
    StringBuilder f3848d = new StringBuilder();
    /* renamed from: e */
    String f3849e;
    /* renamed from: f */
    String[] f3850f;

    public C1292g(String... strArr) {
        InputStream openStream;
        InputStream resourceAsStream = getClass().getResourceAsStream("/isoparser-default.properties");
        try {
            this.f3846b = new Properties();
            this.f3846b.load(resourceAsStream);
            ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
            if (contextClassLoader == null) {
                contextClassLoader = ClassLoader.getSystemClassLoader();
            }
            Enumeration resources = contextClassLoader.getResources("isoparser-custom.properties");
            while (resources.hasMoreElements()) {
                openStream = ((URL) resources.nextElement()).openStream();
                this.f3846b.load(openStream);
                openStream.close();
            }
            for (String resourceAsStream2 : strArr) {
                this.f3846b.load(getClass().getResourceAsStream(resourceAsStream2));
            }
            try {
                resourceAsStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Throwable e2) {
            throw new RuntimeException(e2);
        } catch (Throwable th) {
            try {
                resourceAsStream.close();
            } catch (IOException e3) {
                e3.printStackTrace();
            }
        }
    }

    /* renamed from: a */
    public C1248b mo1119a(String str, byte[] bArr, String str2) {
        m6689b(str, bArr, str2);
        try {
            Class cls = Class.forName(this.f3849e);
            if (this.f3850f.length <= 0) {
                return (C1248b) cls.newInstance();
            }
            Class[] clsArr = new Class[this.f3850f.length];
            Object[] objArr = new Object[this.f3850f.length];
            for (int i = 0; i < this.f3850f.length; i++) {
                if ("userType".equals(this.f3850f[i])) {
                    objArr[i] = bArr;
                    clsArr[i] = byte[].class;
                } else if (Param.TYPE.equals(this.f3850f[i])) {
                    objArr[i] = str;
                    clsArr[i] = String.class;
                } else if ("parent".equals(this.f3850f[i])) {
                    objArr[i] = str2;
                    clsArr[i] = String.class;
                } else {
                    throw new InternalError("No such param: " + this.f3850f[i]);
                }
            }
            return (C1248b) cls.getConstructor(clsArr).newInstance(objArr);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } catch (Throwable e2) {
            throw new RuntimeException(e2);
        } catch (Throwable e22) {
            throw new RuntimeException(e22);
        } catch (Throwable e222) {
            throw new RuntimeException(e222);
        } catch (Throwable e2222) {
            throw new RuntimeException(e2222);
        }
    }

    /* renamed from: b */
    public void m6689b(String str, byte[] bArr, String str2) {
        Object property;
        if (bArr == null) {
            property = this.f3846b.getProperty(str);
            if (property == null) {
                String stringBuilder = this.f3848d.append(str2).append('-').append(str).toString();
                this.f3848d.setLength(0);
                property = this.f3846b.getProperty(stringBuilder);
            }
        } else if ("uuid".equals(str)) {
            property = this.f3846b.getProperty("uuid[" + C1288c.m6663a(bArr).toUpperCase() + "]");
            if (property == null) {
                property = this.f3846b.getProperty(new StringBuilder(String.valueOf(str2)).append("-uuid[").append(C1288c.m6663a(bArr).toUpperCase()).append("]").toString());
            }
            if (property == null) {
                property = this.f3846b.getProperty("uuid");
            }
        } else {
            throw new RuntimeException("we have a userType but no uuid box type. Something's wrong");
        }
        if (property == null) {
            property = this.f3846b.getProperty("default");
        }
        if (property == null) {
            throw new RuntimeException("No box object found for " + str);
        } else if (property.endsWith(")")) {
            Matcher matcher = this.f3847c.matcher(property);
            if (matcher.matches()) {
                this.f3849e = matcher.group(1);
                if (matcher.group(2).length() == 0) {
                    this.f3850f = f3845g;
                    return;
                } else {
                    this.f3850f = matcher.group(2).length() > 0 ? matcher.group(2).split(",") : new String[0];
                    return;
                }
            }
            throw new RuntimeException("Cannot work with that constructor: " + property);
        } else {
            this.f3850f = f3845g;
            this.f3849e = property;
        }
    }
}

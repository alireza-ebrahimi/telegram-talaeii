package com.google.gms.googleservices;

import android.support.v4.media.TransportMediator;
import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.lang.Reference;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import java.io.File;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.BytecodeInterface8;
import org.codehaus.groovy.runtime.GStringImpl;
import org.codehaus.groovy.runtime.GeneratedClosure;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;
import org.codehaus.groovy.transform.ImmutableASTTransformation;
import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.telegram.messenger.exoplayer2.extractor.ts.TsExtractor;
import utils.view.Constants;

/* compiled from: GoogleServicesPlugin.groovy */
public class GoogleServicesPlugin implements Plugin<Project>, GroovyObject {
    private static /* synthetic */ SoftReference $callSiteArray = null;
    private static /* synthetic */ ClassInfo $staticClassInfo = null;
    private static /* synthetic */ ClassInfo $staticClassInfo$ = null;
    public static final String JSON_FILE_NAME = "google-services.json";
    public static final String MINIMUM_VERSION = "9.0.0";
    public static final String MODULE_CORE = "firebase-core";
    public static final String MODULE_GROUP = "com.google.android.gms";
    public static final String MODULE_GROUP_FIREBASE = "com.google.firebase";
    public static final String MODULE_VERSION = "9.0.0";
    public static transient /* synthetic */ boolean __$stMC;
    private static String targetVersion;
    private static String targetVersionRaw;
    private transient /* synthetic */ MetaClass metaClass = $getStaticMetaClass();

    /* compiled from: GoogleServicesPlugin.groovy */
    enum PluginType implements GroovyObject {
        ;
        
        public static final PluginType MAX_VALUE = null;
        public static final PluginType MIN_VALUE = null;

        public PluginType(LinkedHashMap __namedArgs) {
            CallSite[] $getCallSiteArray = $getCallSiteArray();
            this.metaClass = $getStaticMetaClass();
            if (ScriptBytecodeAdapter.compareEqual(__namedArgs, null)) {
                throw ((Throwable) $getCallSiteArray[0].callConstructor(IllegalArgumentException.class, "One of the enum constants for enum com.google.gms.googleservices.GoogleServicesPlugin$PluginType was initialized with null. Please use a non-null value or define your own constructor."));
            }
            $getCallSiteArray[1].callStatic(ImmutableASTTransformation.class, this, __namedArgs);
        }
    }

    /* compiled from: GoogleServicesPlugin.groovy */
    class _apply_closure1 extends Closure implements GeneratedClosure {
        private static /* synthetic */ SoftReference $callSiteArray;
        private static /* synthetic */ ClassInfo $staticClassInfo;
        public static transient /* synthetic */ boolean __$stMC;
        private /* synthetic */ Reference project;

        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
            String[] strArr = new String[2];
            $createCallSiteArray_1(strArr);
            return new CallSiteArray(_apply_closure1.class, strArr);
        }

        private static /* synthetic */ void $createCallSiteArray_1(String[] strArr) {
            strArr[0] = "setupPlugin";
            strArr[1] = "APPLICATION";
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private static /* synthetic */ org.codehaus.groovy.runtime.callsite.CallSite[] $getCallSiteArray() {
            /*
            r0 = $callSiteArray;
            if (r0 == 0) goto L_0x000e;
        L_0x0004:
            r0 = $callSiteArray;
            r0 = r0.get();
            r0 = (org.codehaus.groovy.runtime.callsite.CallSiteArray) r0;
            if (r0 != 0) goto L_0x0019;
        L_0x000e:
            r0 = $createCallSiteArray();
            r1 = new java.lang.ref.SoftReference;
            r1.<init>(r0);
            $callSiteArray = r1;
        L_0x0019:
            r0 = r0.array;
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.gms.googleservices.GoogleServicesPlugin._apply_closure1.$getCallSiteArray():org.codehaus.groovy.runtime.callsite.CallSite[]");
        }

        public _apply_closure1(Object _outerInstance, Object _thisObject, Reference project) {
            $getCallSiteArray();
            super(_outerInstance, _thisObject);
            this.project = project;
        }

        protected /* synthetic */ MetaClass $getStaticMetaClass() {
            if (getClass() != _apply_closure1.class) {
                return ScriptBytecodeAdapter.initMetaClass(this);
            }
            ClassInfo classInfo = $staticClassInfo;
            if (classInfo == null) {
                classInfo = ClassInfo.getClassInfo(getClass());
                $staticClassInfo = classInfo;
            }
            return classInfo.getMetaClass();
        }

        public Object doCall() {
            $getCallSiteArray();
            return doCall(null);
        }

        public Project getProject() {
            $getCallSiteArray();
            return (Project) ScriptBytecodeAdapter.castToType(this.project.get(), Project.class);
        }

        public Object doCall(Object it) {
            CallSite[] $getCallSiteArray = $getCallSiteArray();
            return $getCallSiteArray[0].callCurrent(this, this.project.get(), $getCallSiteArray[1].callGetProperty(PluginType.class));
        }
    }

    /* compiled from: GoogleServicesPlugin.groovy */
    class _apply_closure2 extends Closure implements GeneratedClosure {
        private static /* synthetic */ SoftReference $callSiteArray;
        private static /* synthetic */ ClassInfo $staticClassInfo;
        public static transient /* synthetic */ boolean __$stMC;
        private /* synthetic */ Reference project;

        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
            String[] strArr = new String[2];
            $createCallSiteArray_1(strArr);
            return new CallSiteArray(_apply_closure2.class, strArr);
        }

        private static /* synthetic */ void $createCallSiteArray_1(String[] strArr) {
            strArr[0] = "setupPlugin";
            strArr[1] = "LIBRARY";
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private static /* synthetic */ org.codehaus.groovy.runtime.callsite.CallSite[] $getCallSiteArray() {
            /*
            r0 = $callSiteArray;
            if (r0 == 0) goto L_0x000e;
        L_0x0004:
            r0 = $callSiteArray;
            r0 = r0.get();
            r0 = (org.codehaus.groovy.runtime.callsite.CallSiteArray) r0;
            if (r0 != 0) goto L_0x0019;
        L_0x000e:
            r0 = $createCallSiteArray();
            r1 = new java.lang.ref.SoftReference;
            r1.<init>(r0);
            $callSiteArray = r1;
        L_0x0019:
            r0 = r0.array;
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.gms.googleservices.GoogleServicesPlugin._apply_closure2.$getCallSiteArray():org.codehaus.groovy.runtime.callsite.CallSite[]");
        }

        public _apply_closure2(Object _outerInstance, Object _thisObject, Reference project) {
            $getCallSiteArray();
            super(_outerInstance, _thisObject);
            this.project = project;
        }

        protected /* synthetic */ MetaClass $getStaticMetaClass() {
            if (getClass() != _apply_closure2.class) {
                return ScriptBytecodeAdapter.initMetaClass(this);
            }
            ClassInfo classInfo = $staticClassInfo;
            if (classInfo == null) {
                classInfo = ClassInfo.getClassInfo(getClass());
                $staticClassInfo = classInfo;
            }
            return classInfo.getMetaClass();
        }

        public Object doCall() {
            $getCallSiteArray();
            return doCall(null);
        }

        public Project getProject() {
            $getCallSiteArray();
            return (Project) ScriptBytecodeAdapter.castToType(this.project.get(), Project.class);
        }

        public Object doCall(Object it) {
            CallSite[] $getCallSiteArray = $getCallSiteArray();
            return $getCallSiteArray[0].callCurrent(this, this.project.get(), $getCallSiteArray[1].callGetProperty(PluginType.class));
        }
    }

    /* compiled from: GoogleServicesPlugin.groovy */
    class _apply_closure3 extends Closure implements GeneratedClosure {
        private static /* synthetic */ SoftReference $callSiteArray;
        private static /* synthetic */ ClassInfo $staticClassInfo;
        public static transient /* synthetic */ boolean __$stMC;
        private /* synthetic */ Reference project;

        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
            String[] strArr = new String[2];
            $createCallSiteArray_1(strArr);
            return new CallSiteArray(_apply_closure3.class, strArr);
        }

        private static /* synthetic */ void $createCallSiteArray_1(String[] strArr) {
            strArr[0] = "setupPlugin";
            strArr[1] = "FEATURE";
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private static /* synthetic */ org.codehaus.groovy.runtime.callsite.CallSite[] $getCallSiteArray() {
            /*
            r0 = $callSiteArray;
            if (r0 == 0) goto L_0x000e;
        L_0x0004:
            r0 = $callSiteArray;
            r0 = r0.get();
            r0 = (org.codehaus.groovy.runtime.callsite.CallSiteArray) r0;
            if (r0 != 0) goto L_0x0019;
        L_0x000e:
            r0 = $createCallSiteArray();
            r1 = new java.lang.ref.SoftReference;
            r1.<init>(r0);
            $callSiteArray = r1;
        L_0x0019:
            r0 = r0.array;
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.gms.googleservices.GoogleServicesPlugin._apply_closure3.$getCallSiteArray():org.codehaus.groovy.runtime.callsite.CallSite[]");
        }

        public _apply_closure3(Object _outerInstance, Object _thisObject, Reference project) {
            $getCallSiteArray();
            super(_outerInstance, _thisObject);
            this.project = project;
        }

        protected /* synthetic */ MetaClass $getStaticMetaClass() {
            if (getClass() != _apply_closure3.class) {
                return ScriptBytecodeAdapter.initMetaClass(this);
            }
            ClassInfo classInfo = $staticClassInfo;
            if (classInfo == null) {
                classInfo = ClassInfo.getClassInfo(getClass());
                $staticClassInfo = classInfo;
            }
            return classInfo.getMetaClass();
        }

        public Object doCall() {
            $getCallSiteArray();
            return doCall(null);
        }

        public Project getProject() {
            $getCallSiteArray();
            return (Project) ScriptBytecodeAdapter.castToType(this.project.get(), Project.class);
        }

        public Object doCall(Object it) {
            CallSite[] $getCallSiteArray = $getCallSiteArray();
            return $getCallSiteArray[0].callCurrent(this, this.project.get(), $getCallSiteArray[1].callGetProperty(PluginType.class));
        }
    }

    /* compiled from: GoogleServicesPlugin.groovy */
    class _apply_closure4 extends Closure implements GeneratedClosure {
        private static /* synthetic */ SoftReference $callSiteArray;
        private static /* synthetic */ ClassInfo $staticClassInfo;
        public static transient /* synthetic */ boolean __$stMC;
        private /* synthetic */ Reference project;

        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
            String[] strArr = new String[1];
            strArr[0] = "addDependency";
            return new CallSiteArray(_apply_closure4.class, strArr);
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private static /* synthetic */ org.codehaus.groovy.runtime.callsite.CallSite[] $getCallSiteArray() {
            /*
            r0 = $callSiteArray;
            if (r0 == 0) goto L_0x000e;
        L_0x0004:
            r0 = $callSiteArray;
            r0 = r0.get();
            r0 = (org.codehaus.groovy.runtime.callsite.CallSiteArray) r0;
            if (r0 != 0) goto L_0x0019;
        L_0x000e:
            r0 = $createCallSiteArray();
            r1 = new java.lang.ref.SoftReference;
            r1.<init>(r0);
            $callSiteArray = r1;
        L_0x0019:
            r0 = r0.array;
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.gms.googleservices.GoogleServicesPlugin._apply_closure4.$getCallSiteArray():org.codehaus.groovy.runtime.callsite.CallSite[]");
        }

        public _apply_closure4(Object _outerInstance, Object _thisObject, Reference project) {
            $getCallSiteArray();
            super(_outerInstance, _thisObject);
            this.project = project;
        }

        protected /* synthetic */ MetaClass $getStaticMetaClass() {
            if (getClass() != _apply_closure4.class) {
                return ScriptBytecodeAdapter.initMetaClass(this);
            }
            ClassInfo classInfo = $staticClassInfo;
            if (classInfo == null) {
                classInfo = ClassInfo.getClassInfo(getClass());
                $staticClassInfo = classInfo;
            }
            return classInfo.getMetaClass();
        }

        public Object doCall() {
            $getCallSiteArray();
            return doCall(null);
        }

        public Project getProject() {
            $getCallSiteArray();
            return (Project) ScriptBytecodeAdapter.castToType(this.project.get(), Project.class);
        }

        public Object doCall(Object it) {
            return $getCallSiteArray()[0].callCurrent(this, this.project.get());
        }
    }

    /* compiled from: GoogleServicesPlugin.groovy */
    class _setupPlugin_closure5 extends Closure implements GeneratedClosure {
        private static /* synthetic */ SoftReference $callSiteArray;
        private static /* synthetic */ ClassInfo $staticClassInfo;
        public static transient /* synthetic */ boolean __$stMC;
        private /* synthetic */ Reference project;

        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
            String[] strArr = new String[1];
            strArr[0] = "handleVariant";
            return new CallSiteArray(_setupPlugin_closure5.class, strArr);
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private static /* synthetic */ org.codehaus.groovy.runtime.callsite.CallSite[] $getCallSiteArray() {
            /*
            r0 = $callSiteArray;
            if (r0 == 0) goto L_0x000e;
        L_0x0004:
            r0 = $callSiteArray;
            r0 = r0.get();
            r0 = (org.codehaus.groovy.runtime.callsite.CallSiteArray) r0;
            if (r0 != 0) goto L_0x0019;
        L_0x000e:
            r0 = $createCallSiteArray();
            r1 = new java.lang.ref.SoftReference;
            r1.<init>(r0);
            $callSiteArray = r1;
        L_0x0019:
            r0 = r0.array;
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.gms.googleservices.GoogleServicesPlugin._setupPlugin_closure5.$getCallSiteArray():org.codehaus.groovy.runtime.callsite.CallSite[]");
        }

        public _setupPlugin_closure5(Object _outerInstance, Object _thisObject, Reference project) {
            $getCallSiteArray();
            super(_outerInstance, _thisObject);
            this.project = project;
        }

        protected /* synthetic */ MetaClass $getStaticMetaClass() {
            if (getClass() != _setupPlugin_closure5.class) {
                return ScriptBytecodeAdapter.initMetaClass(this);
            }
            ClassInfo classInfo = $staticClassInfo;
            if (classInfo == null) {
                classInfo = ClassInfo.getClassInfo(getClass());
                $staticClassInfo = classInfo;
            }
            return classInfo.getMetaClass();
        }

        public Project getProject() {
            $getCallSiteArray();
            return (Project) ScriptBytecodeAdapter.castToType(this.project.get(), Project.class);
        }

        public Object doCall(Object variant) {
            return $getCallSiteArray()[0].callCurrent(this, this.project.get(), variant);
        }
    }

    /* compiled from: GoogleServicesPlugin.groovy */
    class _setupPlugin_closure6 extends Closure implements GeneratedClosure {
        private static /* synthetic */ SoftReference $callSiteArray;
        private static /* synthetic */ ClassInfo $staticClassInfo;
        public static transient /* synthetic */ boolean __$stMC;
        private /* synthetic */ Reference project;

        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
            String[] strArr = new String[1];
            strArr[0] = "handleVariant";
            return new CallSiteArray(_setupPlugin_closure6.class, strArr);
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private static /* synthetic */ org.codehaus.groovy.runtime.callsite.CallSite[] $getCallSiteArray() {
            /*
            r0 = $callSiteArray;
            if (r0 == 0) goto L_0x000e;
        L_0x0004:
            r0 = $callSiteArray;
            r0 = r0.get();
            r0 = (org.codehaus.groovy.runtime.callsite.CallSiteArray) r0;
            if (r0 != 0) goto L_0x0019;
        L_0x000e:
            r0 = $createCallSiteArray();
            r1 = new java.lang.ref.SoftReference;
            r1.<init>(r0);
            $callSiteArray = r1;
        L_0x0019:
            r0 = r0.array;
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.gms.googleservices.GoogleServicesPlugin._setupPlugin_closure6.$getCallSiteArray():org.codehaus.groovy.runtime.callsite.CallSite[]");
        }

        public _setupPlugin_closure6(Object _outerInstance, Object _thisObject, Reference project) {
            $getCallSiteArray();
            super(_outerInstance, _thisObject);
            this.project = project;
        }

        protected /* synthetic */ MetaClass $getStaticMetaClass() {
            if (getClass() != _setupPlugin_closure6.class) {
                return ScriptBytecodeAdapter.initMetaClass(this);
            }
            ClassInfo classInfo = $staticClassInfo;
            if (classInfo == null) {
                classInfo = ClassInfo.getClassInfo(getClass());
                $staticClassInfo = classInfo;
            }
            return classInfo.getMetaClass();
        }

        public Project getProject() {
            $getCallSiteArray();
            return (Project) ScriptBytecodeAdapter.castToType(this.project.get(), Project.class);
        }

        public Object doCall(Object variant) {
            return $getCallSiteArray()[0].callCurrent(this, this.project.get(), variant);
        }
    }

    /* compiled from: GoogleServicesPlugin.groovy */
    class _setupPlugin_closure7 extends Closure implements GeneratedClosure {
        private static /* synthetic */ SoftReference $callSiteArray;
        private static /* synthetic */ ClassInfo $staticClassInfo;
        public static transient /* synthetic */ boolean __$stMC;
        private /* synthetic */ Reference project;

        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
            String[] strArr = new String[1];
            strArr[0] = "handleVariant";
            return new CallSiteArray(_setupPlugin_closure7.class, strArr);
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private static /* synthetic */ org.codehaus.groovy.runtime.callsite.CallSite[] $getCallSiteArray() {
            /*
            r0 = $callSiteArray;
            if (r0 == 0) goto L_0x000e;
        L_0x0004:
            r0 = $callSiteArray;
            r0 = r0.get();
            r0 = (org.codehaus.groovy.runtime.callsite.CallSiteArray) r0;
            if (r0 != 0) goto L_0x0019;
        L_0x000e:
            r0 = $createCallSiteArray();
            r1 = new java.lang.ref.SoftReference;
            r1.<init>(r0);
            $callSiteArray = r1;
        L_0x0019:
            r0 = r0.array;
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.gms.googleservices.GoogleServicesPlugin._setupPlugin_closure7.$getCallSiteArray():org.codehaus.groovy.runtime.callsite.CallSite[]");
        }

        public _setupPlugin_closure7(Object _outerInstance, Object _thisObject, Reference project) {
            $getCallSiteArray();
            super(_outerInstance, _thisObject);
            this.project = project;
        }

        protected /* synthetic */ MetaClass $getStaticMetaClass() {
            if (getClass() != _setupPlugin_closure7.class) {
                return ScriptBytecodeAdapter.initMetaClass(this);
            }
            ClassInfo classInfo = $staticClassInfo;
            if (classInfo == null) {
                classInfo = ClassInfo.getClassInfo(getClass());
                $staticClassInfo = classInfo;
            }
            return classInfo.getMetaClass();
        }

        public Project getProject() {
            $getCallSiteArray();
            return (Project) ScriptBytecodeAdapter.castToType(this.project.get(), Project.class);
        }

        public Object doCall(Object variant) {
            return $getCallSiteArray()[0].callCurrent(this, this.project.get(), variant);
        }
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] strArr = new String[170];
        $createCallSiteArray_1(strArr);
        return new CallSiteArray(GoogleServicesPlugin.class, strArr);
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] strArr) {
        strArr[0] = "hasPlugin";
        strArr[1] = "plugins";
        strArr[2] = "hasPlugin";
        strArr[3] = "plugins";
        strArr[4] = "addDependency";
        strArr[5] = "setupPlugin";
        strArr[6] = "APPLICATION";
        strArr[7] = "hasPlugin";
        strArr[8] = "plugins";
        strArr[9] = "hasPlugin";
        strArr[10] = "plugins";
        strArr[11] = "addDependency";
        strArr[12] = "setupPlugin";
        strArr[13] = "LIBRARY";
        strArr[14] = "hasPlugin";
        strArr[15] = "plugins";
        strArr[16] = "hasPlugin";
        strArr[17] = "plugins";
        strArr[18] = "addDependency";
        strArr[19] = "setupPlugin";
        strArr[20] = "FEATURE";
        strArr[21] = "showWarningForPluginLocation";
        strArr[22] = "withId";
        strArr[23] = "plugins";
        strArr[24] = "withId";
        strArr[25] = "plugins";
        strArr[26] = "withId";
        strArr[27] = "plugins";
        strArr[28] = "afterEvaluate";
        strArr[29] = "warn";
        strArr[30] = "getLogger";
        strArr[31] = "split";
        strArr[32] = "split";
        strArr[33] = "length";
        strArr[34] = "length";
        strArr[35] = "valueOf";
        strArr[36] = "getAt";
        strArr[37] = "valueOf";
        strArr[38] = "getAt";
        strArr[39] = Constants.TYPE_DIR_NEXT;
        strArr[40] = "length";
        strArr[41] = "length";
        strArr[42] = "valueOf";
        strArr[43] = "valueOf";
        strArr[44] = "length";
        strArr[45] = "length";
        strArr[46] = "findTargetVersion";
        strArr[47] = "getAt";
        strArr[48] = "split";
        strArr[49] = "checkMinimumVersion";
        strArr[50] = "add";
        strArr[51] = "dependencies";
        strArr[52] = "plus";
        strArr[53] = "plus";
        strArr[54] = "plus";
        strArr[55] = "plus";
        strArr[56] = "<$constructor$>";
        strArr[57] = "plus";
        strArr[58] = "plus";
        strArr[59] = "plus";
        strArr[60] = "plus";
        strArr[61] = "add";
        strArr[62] = "dependencies";
        strArr[63] = "plus";
        strArr[64] = "plus";
        strArr[65] = "plus";
        strArr[66] = "plus";
        strArr[67] = "<$constructor$>";
        strArr[68] = "plus";
        strArr[69] = "plus";
        strArr[70] = "plus";
        strArr[71] = "plus";
        strArr[72] = "getConfigurations";
        strArr[73] = "iterator";
        strArr[74] = "getDependencies";
        strArr[75] = "iterator";
        strArr[76] = "equalsIgnoreCase";
        strArr[77] = "getGroup";
        strArr[78] = "equalsIgnoreCase";
        strArr[79] = "getGroup";
        strArr[80] = "getVersion";
        strArr[81] = "warn";
        strArr[82] = "getLogger";
        strArr[83] = "plus";
        strArr[84] = "plus";
        strArr[85] = "plus";
        strArr[86] = "plus";
        strArr[87] = "plus";
        strArr[88] = "plus";
        strArr[89] = "showWarningForPluginLocation";
        strArr[90] = "APPLICATION";
        strArr[91] = "all";
        strArr[92] = "applicationVariants";
        strArr[93] = AbstractSpiCall.ANDROID_CLIENT_TYPE;
        strArr[94] = "LIBRARY";
        strArr[95] = "all";
        strArr[96] = "LibraryVariants";
        strArr[97] = AbstractSpiCall.ANDROID_CLIENT_TYPE;
        strArr[98] = "FEATURE";
        strArr[99] = "all";
        strArr[100] = "featureVariants";
        strArr[101] = AbstractSpiCall.ANDROID_CLIENT_TYPE;
        strArr[102] = "dirName";
        strArr[103] = "split";
        strArr[104] = "<$constructor$>";
        strArr[105] = "length";
        strArr[106] = "getAt";
        strArr[107] = "getAt";
        strArr[108] = "add";
        strArr[109] = "plus";
        strArr[110] = "plus";
        strArr[111] = "plus";
        strArr[112] = "add";
        strArr[113] = "plus";
        strArr[114] = "plus";
        strArr[115] = "plus";
        strArr[116] = "add";
        strArr[117] = "plus";
        strArr[118] = "add";
        strArr[119] = "plus";
        strArr[120] = "add";
        strArr[121] = "plus";
        strArr[122] = "plus";
        strArr[123] = "capitalize";
        strArr[124] = "length";
        strArr[125] = "add";
        strArr[TransportMediator.KEYCODE_MEDIA_PLAY] = "plus";
        strArr[127] = "getAt";
        strArr[128] = "length";
        strArr[TsExtractor.TS_STREAM_TYPE_AC3] = "add";
        strArr[130] = "plus";
        strArr[131] = "plus";
        strArr[132] = "plus";
        strArr[133] = "add";
        strArr[TsExtractor.TS_STREAM_TYPE_SPLICE_INFO] = "plus";
        strArr[135] = "plus";
        strArr[136] = "plus";
        strArr[137] = "add";
        strArr[TsExtractor.TS_STREAM_TYPE_DTS] = "plus";
        strArr[139] = "add";
        strArr[140] = "plus";
        strArr[141] = "add";
        strArr[142] = "plus";
        strArr[143] = "plus";
        strArr[144] = "capitalize";
        strArr[145] = "length";
        strArr[146] = "add";
        strArr[147] = "plus";
        strArr[148] = "lineSeparator";
        strArr[149] = "iterator";
        strArr[150] = "file";
        strArr[151] = "plus";
        strArr[152] = "plus";
        strArr[153] = "plus";
        strArr[154] = "plus";
        strArr[155] = "getPath";
        strArr[156] = "lineSeparator";
        strArr[157] = "isFile";
        strArr[158] = "file";
        strArr[159] = "plus";
        strArr[160] = "getPath";
        strArr[161] = "file";
        strArr[162] = "buildDir";
        strArr[163] = "dirName";
        strArr[164] = "create";
        strArr[165] = "tasks";
        strArr[166] = "capitalize";
        strArr[167] = "name";
        strArr[168] = "applicationId";
        strArr[169] = "registerResGeneratingTask";
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static /* synthetic */ org.codehaus.groovy.runtime.callsite.CallSite[] $getCallSiteArray() {
        /*
        r0 = $callSiteArray;
        if (r0 == 0) goto L_0x000e;
    L_0x0004:
        r0 = $callSiteArray;
        r0 = r0.get();
        r0 = (org.codehaus.groovy.runtime.callsite.CallSiteArray) r0;
        if (r0 != 0) goto L_0x0019;
    L_0x000e:
        r0 = $createCallSiteArray();
        r1 = new java.lang.ref.SoftReference;
        r1.<init>(r0);
        $callSiteArray = r1;
    L_0x0019:
        r0 = r0.array;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gms.googleservices.GoogleServicesPlugin.$getCallSiteArray():org.codehaus.groovy.runtime.callsite.CallSite[]");
    }

    public GoogleServicesPlugin() {
        $getCallSiteArray();
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (getClass() != GoogleServicesPlugin.class) {
            return ScriptBytecodeAdapter.initMetaClass(this);
        }
        ClassInfo classInfo = $staticClassInfo;
        if (classInfo == null) {
            classInfo = ClassInfo.getClassInfo(getClass());
            $staticClassInfo = classInfo;
        }
        return classInfo.getMetaClass();
    }

    public /* synthetic */ MetaClass getMetaClass() {
        MetaClass metaClass = this.metaClass;
        if (metaClass != null) {
            return metaClass;
        }
        this.metaClass = $getStaticMetaClass();
        return this.metaClass;
    }

    public /* synthetic */ Object getProperty(String str) {
        return getMetaClass().getProperty(this, str);
    }

    public /* synthetic */ Object invokeMethod(String str, Object obj) {
        return getMetaClass().invokeMethod(this, str, obj);
    }

    public /* synthetic */ void setMetaClass(MetaClass metaClass) {
        this.metaClass = metaClass;
    }

    public /* synthetic */ void setProperty(String str, Object obj) {
        getMetaClass().setProperty(this, str, obj);
    }

    public /* synthetic */ Object this$dist$get$1(String name) {
        $getCallSiteArray();
        return ScriptBytecodeAdapter.getGroovyObjectProperty(GoogleServicesPlugin.class, this, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})));
    }

    public /* synthetic */ Object this$dist$invoke$1(String name, Object args) {
        $getCallSiteArray();
        return ScriptBytecodeAdapter.invokeMethodOnCurrentN(GoogleServicesPlugin.class, this, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), ScriptBytecodeAdapter.despreadList(new Object[0], new Object[]{args}, new int[]{0}));
    }

    public /* synthetic */ void this$dist$set$1(String name, Object value) {
        $getCallSiteArray();
        ScriptBytecodeAdapter.setGroovyObjectProperty(value, GoogleServicesPlugin.class, this, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})));
    }

    public void apply(Project project) {
        Reference project2 = new Reference(project);
        CallSite[] $getCallSiteArray = $getCallSiteArray();
        int i = (DefaultTypeTransformation.booleanUnbox($getCallSiteArray[0].call($getCallSiteArray[1].callGetProperty((Project) project2.get()), AbstractSpiCall.ANDROID_CLIENT_TYPE)) || DefaultTypeTransformation.booleanUnbox($getCallSiteArray[2].call($getCallSiteArray[3].callGetProperty((Project) project2.get()), "com.android.application"))) ? 1 : 0;
        if (i != 0) {
            $getCallSiteArray[4].callCurrent(this, (Project) project2.get());
            $getCallSiteArray[5].callCurrent(this, (Project) project2.get(), $getCallSiteArray[6].callGetProperty(PluginType.class));
            return;
        }
        if (DefaultTypeTransformation.booleanUnbox($getCallSiteArray[7].call($getCallSiteArray[8].callGetProperty((Project) project2.get()), "android-library")) || DefaultTypeTransformation.booleanUnbox($getCallSiteArray[9].call($getCallSiteArray[10].callGetProperty((Project) project2.get()), "com.android.library"))) {
            i = 1;
        } else {
            i = 0;
        }
        if (i != 0) {
            $getCallSiteArray[11].callCurrent(this, (Project) project2.get());
            $getCallSiteArray[12].callCurrent(this, (Project) project2.get(), $getCallSiteArray[13].callGetProperty(PluginType.class));
            return;
        }
        if (DefaultTypeTransformation.booleanUnbox($getCallSiteArray[14].call($getCallSiteArray[15].callGetProperty((Project) project2.get()), "android-feature")) || DefaultTypeTransformation.booleanUnbox($getCallSiteArray[16].call($getCallSiteArray[17].callGetProperty((Project) project2.get()), "com.android.feature"))) {
            i = 1;
        } else {
            i = 0;
        }
        if (i != 0) {
            $getCallSiteArray[18].callCurrent(this, (Project) project2.get());
            $getCallSiteArray[19].callCurrent(this, (Project) project2.get(), $getCallSiteArray[20].callGetProperty(PluginType.class));
            return;
        }
        $getCallSiteArray[21].callCurrent(this, (Project) project2.get());
        $getCallSiteArray[22].call($getCallSiteArray[23].callGetProperty((Project) project2.get()), AbstractSpiCall.ANDROID_CLIENT_TYPE, new _apply_closure1(this, this, project2));
        $getCallSiteArray[24].call($getCallSiteArray[25].callGetProperty((Project) project2.get()), "android-library", new _apply_closure2(this, this, project2));
        $getCallSiteArray[26].call($getCallSiteArray[27].callGetProperty((Project) project2.get()), "android-feature", new _apply_closure3(this, this, project2));
        $getCallSiteArray[28].call((Project) project2.get(), new _apply_closure4(this, this, project2));
    }

    private void showWarningForPluginLocation(Project project) {
        CallSite[] $getCallSiteArray = $getCallSiteArray();
        $getCallSiteArray[29].call($getCallSiteArray[30].call(project), "please apply google-services plugin at the bottom of the build file.");
    }

    private boolean checkMinimumVersion() {
        CallSite[] $getCallSiteArray = $getCallSiteArray();
        String[] subTargetVersions = (String[]) ScriptBytecodeAdapter.castToType($getCallSiteArray[31].call(targetVersion, "\\."), String[].class);
        String[] subMinimumVersions = (String[]) ScriptBytecodeAdapter.castToType($getCallSiteArray[32].call(MINIMUM_VERSION, "\\."), String[].class);
        int i;
        boolean z;
        Integer subTargetVersion;
        Integer subMinimumVersion;
        if (BytecodeInterface8.isOrigInt() && BytecodeInterface8.isOrigZ() && !__$stMC && !BytecodeInterface8.disabledStandardMetaClass()) {
            i = 0;
            while (true) {
                if (ScriptBytecodeAdapter.compareLessThan(Integer.valueOf(i), $getCallSiteArray[40].callGetProperty(subTargetVersions)) && ScriptBytecodeAdapter.compareLessThan(Integer.valueOf(i), $getCallSiteArray[41].callGetProperty(subMinimumVersions))) {
                    z = true;
                } else {
                    z = false;
                }
                if (!z) {
                    break;
                }
                subTargetVersion = (Integer) ScriptBytecodeAdapter.castToType($getCallSiteArray[42].call(Integer.class, BytecodeInterface8.objectArrayGet(subTargetVersions, i)), Integer.class);
                subMinimumVersion = (Integer) ScriptBytecodeAdapter.castToType($getCallSiteArray[43].call(Integer.class, BytecodeInterface8.objectArrayGet(subMinimumVersions, i)), Integer.class);
                if (ScriptBytecodeAdapter.compareGreaterThan(subTargetVersion, subMinimumVersion)) {
                    return true;
                }
                if (ScriptBytecodeAdapter.compareLessThan(subTargetVersion, subMinimumVersion)) {
                    return false;
                }
                i++;
            }
        } else {
            i = 0;
            while (true) {
                if (ScriptBytecodeAdapter.compareLessThan(Integer.valueOf(i), $getCallSiteArray[33].callGetProperty(subTargetVersions)) && ScriptBytecodeAdapter.compareLessThan(Integer.valueOf(i), $getCallSiteArray[34].callGetProperty(subMinimumVersions))) {
                    z = true;
                } else {
                    z = false;
                }
                if (!z) {
                    break;
                }
                subTargetVersion = (Integer) ScriptBytecodeAdapter.castToType($getCallSiteArray[35].call(Integer.class, $getCallSiteArray[36].call(subTargetVersions, Integer.valueOf(i))), Integer.class);
                subMinimumVersion = (Integer) ScriptBytecodeAdapter.castToType($getCallSiteArray[37].call(Integer.class, $getCallSiteArray[38].call(subMinimumVersions, Integer.valueOf(i))), Integer.class);
                if (ScriptBytecodeAdapter.compareGreaterThan(subTargetVersion, subMinimumVersion)) {
                    return true;
                }
                if (ScriptBytecodeAdapter.compareLessThan(subTargetVersion, subMinimumVersion)) {
                    return false;
                }
                i = DefaultTypeTransformation.intUnbox($getCallSiteArray[39].call(Integer.valueOf(i)));
            }
        }
        return ScriptBytecodeAdapter.compareGreaterThanEqual($getCallSiteArray[44].callGetProperty(subTargetVersions), $getCallSiteArray[45].callGetProperty(subMinimumVersions));
    }

    private void addDependency(Project project) {
        CallSite[] $getCallSiteArray = $getCallSiteArray();
        targetVersionRaw = ShortTypeHandling.castToString($getCallSiteArray[46].callCurrent(this, project));
        targetVersion = ShortTypeHandling.castToString($getCallSiteArray[47].call($getCallSiteArray[48].call(targetVersionRaw, "-"), Integer.valueOf(0)));
        if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            if (DefaultTypeTransformation.booleanUnbox($getCallSiteArray[49].callCurrent(this))) {
                $getCallSiteArray[50].call($getCallSiteArray[51].callGetProperty(project), "compile", $getCallSiteArray[52].call($getCallSiteArray[53].call($getCallSiteArray[54].call($getCallSiteArray[55].call(MODULE_GROUP_FIREBASE, ":"), MODULE_CORE), ":"), targetVersionRaw));
                return;
            }
            throw ((Throwable) $getCallSiteArray[56].callConstructor(GradleException.class, $getCallSiteArray[57].call($getCallSiteArray[58].call($getCallSiteArray[59].call($getCallSiteArray[60].call("Version: ", targetVersionRaw), " is lower than the minimum version ("), MINIMUM_VERSION), ") required for google-services plugin.")));
        } else if (checkMinimumVersion()) {
            $getCallSiteArray[61].call($getCallSiteArray[62].callGetProperty(project), "compile", $getCallSiteArray[63].call($getCallSiteArray[64].call($getCallSiteArray[65].call($getCallSiteArray[66].call(MODULE_GROUP_FIREBASE, ":"), MODULE_CORE), ":"), targetVersionRaw));
        } else {
            throw ((Throwable) $getCallSiteArray[67].callConstructor(GradleException.class, $getCallSiteArray[68].call($getCallSiteArray[69].call($getCallSiteArray[70].call($getCallSiteArray[71].call("Version: ", targetVersionRaw), " is lower than the minimum version ("), MINIMUM_VERSION), ") required for google-services plugin.")));
        }
    }

    private String findTargetVersion(Project project) {
        CallSite[] $getCallSiteArray = $getCallSiteArray();
        Object configurations = $getCallSiteArray[72].call(project);
        if (ScriptBytecodeAdapter.compareEqual(configurations, null)) {
            return ShortTypeHandling.castToString(null);
        }
        Iterator it = (Iterator) ScriptBytecodeAdapter.castToType($getCallSiteArray[73].call(configurations), Iterator.class);
        while (it.hasNext()) {
            Object configuration = it.next();
            if (!ScriptBytecodeAdapter.compareEqual(configuration, null)) {
                Object dependencies = $getCallSiteArray[74].call(configuration);
                if (ScriptBytecodeAdapter.compareEqual(dependencies, null)) {
                    continue;
                } else {
                    Iterator it2 = (Iterator) ScriptBytecodeAdapter.castToType($getCallSiteArray[75].call(dependencies), Iterator.class);
                    while (it2.hasNext()) {
                        Object dependency = it2.next();
                        if (!ScriptBytecodeAdapter.compareEqual(dependency, null)) {
                            Object obj = (DefaultTypeTransformation.booleanUnbox($getCallSiteArray[76].call(MODULE_GROUP, $getCallSiteArray[77].call(dependency))) || DefaultTypeTransformation.booleanUnbox($getCallSiteArray[78].call(MODULE_GROUP_FIREBASE, $getCallSiteArray[79].call(dependency)))) ? 1 : null;
                            if (obj != null) {
                                return ShortTypeHandling.castToString($getCallSiteArray[80].call(dependency));
                            }
                        }
                    }
                    continue;
                }
            }
        }
        $getCallSiteArray[81].call($getCallSiteArray[82].call(project), $getCallSiteArray[83].call($getCallSiteArray[84].call($getCallSiteArray[85].call($getCallSiteArray[86].call($getCallSiteArray[87].call($getCallSiteArray[88].call("google-services plugin could not detect any version for ", MODULE_GROUP), " or "), MODULE_GROUP_FIREBASE), ", default version: "), MODULE_VERSION), " will be used."));
        $getCallSiteArray[89].callCurrent(this, project);
        return MODULE_VERSION;
    }

    private void setupPlugin(Project project, PluginType pluginType) {
        Reference project2 = new Reference(project);
        CallSite[] $getCallSiteArray = $getCallSiteArray();
        if (ScriptBytecodeAdapter.isCase(pluginType, $getCallSiteArray[90].callGetProperty(PluginType.class))) {
            $getCallSiteArray[91].call($getCallSiteArray[92].callGetProperty($getCallSiteArray[93].callGetProperty((Project) project2.get())), new _setupPlugin_closure5(this, this, project2));
        } else if (ScriptBytecodeAdapter.isCase(pluginType, $getCallSiteArray[94].callGetProperty(PluginType.class))) {
            $getCallSiteArray[95].call($getCallSiteArray[96].callGetProperty($getCallSiteArray[97].callGetProperty((Project) project2.get())), new _setupPlugin_closure6(this, this, project2));
        } else if (ScriptBytecodeAdapter.isCase(pluginType, $getCallSiteArray[98].callGetProperty(PluginType.class))) {
            $getCallSiteArray[99].call($getCallSiteArray[100].callGetProperty($getCallSiteArray[101].callGetProperty((Project) project2.get())), new _setupPlugin_closure7(this, this, project2));
        }
    }

    private static void handleVariant(Project project, Object variant) {
        CallSite[] $getCallSiteArray = $getCallSiteArray();
        File quickstartFile = null;
        String[] variantTokens = (String[]) ScriptBytecodeAdapter.castToType($getCallSiteArray[103].call(ShortTypeHandling.castToString(new GStringImpl(new Object[]{$getCallSiteArray[102].callGetProperty(variant)}, new String[]{"", ""})), "/"), String[].class);
        List fileLocation = (List) ScriptBytecodeAdapter.castToType($getCallSiteArray[104].callConstructor(ArrayList.class), List.class);
        String flavorName;
        String buildType;
        if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            if (ScriptBytecodeAdapter.compareEqual($getCallSiteArray[105].callGetProperty(variantTokens), Integer.valueOf(2))) {
                flavorName = ShortTypeHandling.castToString($getCallSiteArray[106].call(variantTokens, Integer.valueOf(0)));
                buildType = ShortTypeHandling.castToString($getCallSiteArray[107].call(variantTokens, Integer.valueOf(1)));
                $getCallSiteArray[108].call(fileLocation, $getCallSiteArray[109].call($getCallSiteArray[110].call($getCallSiteArray[111].call("src/", flavorName), "/"), buildType));
                $getCallSiteArray[112].call(fileLocation, $getCallSiteArray[113].call($getCallSiteArray[114].call($getCallSiteArray[115].call("src/", buildType), "/"), flavorName));
                $getCallSiteArray[116].call(fileLocation, $getCallSiteArray[117].call("src/", flavorName));
                $getCallSiteArray[118].call(fileLocation, $getCallSiteArray[119].call("src/", buildType));
                $getCallSiteArray[120].call(fileLocation, $getCallSiteArray[121].call($getCallSiteArray[122].call("src/", flavorName), $getCallSiteArray[123].call(buildType)));
            } else if (ScriptBytecodeAdapter.compareEqual($getCallSiteArray[124].callGetProperty(variantTokens), Integer.valueOf(1))) {
                $getCallSiteArray[125].call(fileLocation, $getCallSiteArray[TransportMediator.KEYCODE_MEDIA_PLAY].call("src/", $getCallSiteArray[127].call(variantTokens, Integer.valueOf(0))));
            }
        } else if (ScriptBytecodeAdapter.compareEqual($getCallSiteArray[128].callGetProperty(variantTokens), Integer.valueOf(2))) {
            flavorName = ShortTypeHandling.castToString(BytecodeInterface8.objectArrayGet(variantTokens, 0));
            buildType = ShortTypeHandling.castToString(BytecodeInterface8.objectArrayGet(variantTokens, 1));
            $getCallSiteArray[TsExtractor.TS_STREAM_TYPE_AC3].call(fileLocation, $getCallSiteArray[130].call($getCallSiteArray[131].call($getCallSiteArray[132].call("src/", flavorName), "/"), buildType));
            $getCallSiteArray[133].call(fileLocation, $getCallSiteArray[TsExtractor.TS_STREAM_TYPE_SPLICE_INFO].call($getCallSiteArray[135].call($getCallSiteArray[136].call("src/", buildType), "/"), flavorName));
            $getCallSiteArray[137].call(fileLocation, $getCallSiteArray[TsExtractor.TS_STREAM_TYPE_DTS].call("src/", flavorName));
            $getCallSiteArray[139].call(fileLocation, $getCallSiteArray[140].call("src/", buildType));
            $getCallSiteArray[141].call(fileLocation, $getCallSiteArray[142].call($getCallSiteArray[143].call("src/", flavorName), $getCallSiteArray[144].call(buildType)));
        } else if (ScriptBytecodeAdapter.compareEqual($getCallSiteArray[145].callGetProperty(variantTokens), Integer.valueOf(1))) {
            $getCallSiteArray[146].call(fileLocation, $getCallSiteArray[147].call("src/", BytecodeInterface8.objectArrayGet(variantTokens, 0)));
        }
        String searchedLocation = ShortTypeHandling.castToString($getCallSiteArray[148].call(System.class));
        Iterator it = (Iterator) ScriptBytecodeAdapter.castToType($getCallSiteArray[149].call(fileLocation), Iterator.class);
        while (it.hasNext()) {
            File jsonFile = (File) ScriptBytecodeAdapter.castToType($getCallSiteArray[150].call(project, $getCallSiteArray[151].call($getCallSiteArray[152].call(ShortTypeHandling.castToString(it.next()), "/"), JSON_FILE_NAME)), File.class);
            searchedLocation = ShortTypeHandling.castToString($getCallSiteArray[153].call($getCallSiteArray[154].call(searchedLocation, $getCallSiteArray[155].call(jsonFile)), $getCallSiteArray[156].call(System.class)));
            if (DefaultTypeTransformation.booleanUnbox($getCallSiteArray[157].call(jsonFile))) {
                quickstartFile = jsonFile;
                break;
            }
        }
        if (ScriptBytecodeAdapter.compareEqual(quickstartFile, null)) {
            quickstartFile = (File) ScriptBytecodeAdapter.castToType($getCallSiteArray[158].call(project, JSON_FILE_NAME), File.class);
            searchedLocation = ShortTypeHandling.castToString($getCallSiteArray[159].call(searchedLocation, $getCallSiteArray[160].call(quickstartFile)));
        }
        Project project2 = project;
        File outputDir = (File) ScriptBytecodeAdapter.castToType($getCallSiteArray[161].call(project2, new GStringImpl(new Object[]{$getCallSiteArray[162].callGetProperty(project), $getCallSiteArray[163].callGetProperty(variant)}, new String[]{"", "/generated/res/google-services/", ""})), File.class);
        GoogleServicesTask task = (GoogleServicesTask) ScriptBytecodeAdapter.castToType($getCallSiteArray[164].call($getCallSiteArray[165].callGetProperty(project), new GStringImpl(new Object[]{$getCallSiteArray[166].call($getCallSiteArray[167].callGetProperty(variant))}, new String[]{"process", "GoogleServices"}), GoogleServicesTask.class), GoogleServicesTask.class);
        ScriptBytecodeAdapter.setProperty(quickstartFile, null, task, "quickstartFile");
        ScriptBytecodeAdapter.setProperty(outputDir, null, task, "intermediateDir");
        ScriptBytecodeAdapter.setProperty($getCallSiteArray[168].callGetProperty(variant), null, task, "packageName");
        ScriptBytecodeAdapter.setProperty(MODULE_GROUP, null, task, "moduleGroup");
        ScriptBytecodeAdapter.setProperty(MODULE_GROUP_FIREBASE, null, task, "moduleGroupFirebase");
        ScriptBytecodeAdapter.setProperty(targetVersionRaw, null, task, "moduleVersion");
        $getCallSiteArray[169].call(variant, task, outputDir);
        ScriptBytecodeAdapter.setProperty(searchedLocation, null, task, "searchedLocation");
    }
}

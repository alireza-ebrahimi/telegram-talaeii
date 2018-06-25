package com.google.android.gms.common.annotation;

import com.google.android.gms.common.internal.Hide;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.TYPE})
@Hide
@Deprecated
public @interface KeepForSdkWithMembers {
}

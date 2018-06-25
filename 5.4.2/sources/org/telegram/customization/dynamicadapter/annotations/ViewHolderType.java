package org.telegram.customization.dynamicadapter.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.telegram.customization.dynamicadapter.data.ObjBase;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewHolderType {
    Class<? extends ObjBase> model();

    int type();
}

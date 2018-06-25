package com.nineoldandroids.animation;

import android.view.View;
import com.nineoldandroids.util.FloatProperty;
import com.nineoldandroids.util.IntProperty;
import com.nineoldandroids.util.Property;
import com.nineoldandroids.view.animation.AnimatorProxy;

final class PreHoneycombCompat {
    static Property<View, Float> ALPHA = new FloatProperty<View>("alpha") {
        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setAlpha(value);
        }

        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getAlpha());
        }
    };
    static Property<View, Float> PIVOT_X = new FloatProperty<View>("pivotX") {
        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setPivotX(value);
        }

        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getPivotX());
        }
    };
    static Property<View, Float> PIVOT_Y = new FloatProperty<View>("pivotY") {
        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setPivotY(value);
        }

        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getPivotY());
        }
    };
    static Property<View, Float> ROTATION = new FloatProperty<View>("rotation") {
        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setRotation(value);
        }

        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getRotation());
        }
    };
    static Property<View, Float> ROTATION_X = new FloatProperty<View>("rotationX") {
        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setRotationX(value);
        }

        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getRotationX());
        }
    };
    static Property<View, Float> ROTATION_Y = new FloatProperty<View>("rotationY") {
        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setRotationY(value);
        }

        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getRotationY());
        }
    };
    static Property<View, Float> SCALE_X = new FloatProperty<View>("scaleX") {
        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setScaleX(value);
        }

        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getScaleX());
        }
    };
    static Property<View, Float> SCALE_Y = new FloatProperty<View>("scaleY") {
        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setScaleY(value);
        }

        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getScaleY());
        }
    };
    static Property<View, Integer> SCROLL_X = new IntProperty<View>("scrollX") {
        public void setValue(View object, int value) {
            AnimatorProxy.wrap(object).setScrollX(value);
        }

        public Integer get(View object) {
            return Integer.valueOf(AnimatorProxy.wrap(object).getScrollX());
        }
    };
    static Property<View, Integer> SCROLL_Y = new IntProperty<View>("scrollY") {
        public void setValue(View object, int value) {
            AnimatorProxy.wrap(object).setScrollY(value);
        }

        public Integer get(View object) {
            return Integer.valueOf(AnimatorProxy.wrap(object).getScrollY());
        }
    };
    static Property<View, Float> TRANSLATION_X = new FloatProperty<View>("translationX") {
        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setTranslationX(value);
        }

        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getTranslationX());
        }
    };
    static Property<View, Float> TRANSLATION_Y = new FloatProperty<View>("translationY") {
        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setTranslationY(value);
        }

        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getTranslationY());
        }
    };
    /* renamed from: X */
    static Property<View, Float> f31X = new FloatProperty<View>("x") {
        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setX(value);
        }

        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getX());
        }
    };
    /* renamed from: Y */
    static Property<View, Float> f32Y = new FloatProperty<View>("y") {
        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setY(value);
        }

        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getY());
        }
    };

    private PreHoneycombCompat() {
    }
}

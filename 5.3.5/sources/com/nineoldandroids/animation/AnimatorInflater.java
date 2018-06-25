package com.nineoldandroids.animation;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.util.Xml;
import android.view.animation.AnimationUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class AnimatorInflater {
    private static final int[] Animator = new int[]{16843073, 16843160, 16843198, 16843199, 16843200, 16843486, 16843487, 16843488};
    private static final int[] AnimatorSet = new int[]{16843490};
    private static final int AnimatorSet_ordering = 0;
    private static final int Animator_duration = 1;
    private static final int Animator_interpolator = 0;
    private static final int Animator_repeatCount = 3;
    private static final int Animator_repeatMode = 4;
    private static final int Animator_startOffset = 2;
    private static final int Animator_valueFrom = 5;
    private static final int Animator_valueTo = 6;
    private static final int Animator_valueType = 7;
    private static final int[] PropertyAnimator = new int[]{16843489};
    private static final int PropertyAnimator_propertyName = 0;
    private static final int TOGETHER = 0;
    private static final int VALUE_TYPE_FLOAT = 0;

    public static Animator loadAnimator(Context context, int id) throws NotFoundException {
        NotFoundException rnf;
        XmlResourceParser parser = null;
        try {
            parser = context.getResources().getAnimation(id);
            Animator createAnimatorFromXml = createAnimatorFromXml(context, parser);
            if (parser != null) {
                parser.close();
            }
            return createAnimatorFromXml;
        } catch (XmlPullParserException ex) {
            rnf = new NotFoundException("Can't load animation resource ID #0x" + Integer.toHexString(id));
            rnf.initCause(ex);
            throw rnf;
        } catch (IOException ex2) {
            rnf = new NotFoundException("Can't load animation resource ID #0x" + Integer.toHexString(id));
            rnf.initCause(ex2);
            throw rnf;
        } catch (Throwable th) {
            if (parser != null) {
                parser.close();
            }
        }
    }

    private static Animator createAnimatorFromXml(Context c, XmlPullParser parser) throws XmlPullParserException, IOException {
        return createAnimatorFromXml(c, parser, Xml.asAttributeSet(parser), null, 0);
    }

    private static Animator createAnimatorFromXml(Context c, XmlPullParser parser, AttributeSet attrs, AnimatorSet parent, int sequenceOrdering) throws XmlPullParserException, IOException {
        Animator anim = null;
        ArrayList<Animator> childAnims = null;
        int depth = parser.getDepth();
        while (true) {
            int type = parser.next();
            if ((type != 3 || parser.getDepth() > depth) && type != 1) {
                if (type == 2) {
                    String name = parser.getName();
                    if (name.equals("objectAnimator")) {
                        anim = loadObjectAnimator(c, attrs);
                    } else if (name.equals("animator")) {
                        anim = loadAnimator(c, attrs, null);
                    } else if (name.equals("set")) {
                        anim = new AnimatorSet();
                        TypedArray a = c.obtainStyledAttributes(attrs, AnimatorSet);
                        TypedValue orderingValue = new TypedValue();
                        a.getValue(0, orderingValue);
                        createAnimatorFromXml(c, parser, attrs, (AnimatorSet) anim, orderingValue.type == 16 ? orderingValue.data : 0);
                        a.recycle();
                    } else {
                        throw new RuntimeException("Unknown animator name: " + parser.getName());
                    }
                    if (parent != null) {
                        if (childAnims == null) {
                            childAnims = new ArrayList();
                        }
                        childAnims.add(anim);
                    }
                }
            }
        }
        if (!(parent == null || childAnims == null)) {
            Animator[] animsArray = new Animator[childAnims.size()];
            int index = 0;
            Iterator i$ = childAnims.iterator();
            while (i$.hasNext()) {
                int index2 = index + 1;
                animsArray[index] = (Animator) i$.next();
                index = index2;
            }
            if (sequenceOrdering == 0) {
                parent.playTogether(animsArray);
            } else {
                parent.playSequentially(animsArray);
            }
        }
        return anim;
    }

    private static ObjectAnimator loadObjectAnimator(Context context, AttributeSet attrs) throws NotFoundException {
        ObjectAnimator anim = new ObjectAnimator();
        loadAnimator(context, attrs, anim);
        TypedArray a = context.obtainStyledAttributes(attrs, PropertyAnimator);
        anim.setPropertyName(a.getString(0));
        a.recycle();
        return anim;
    }

    private static ValueAnimator loadAnimator(Context context, AttributeSet attrs, ValueAnimator anim) throws NotFoundException {
        TypedArray a = context.obtainStyledAttributes(attrs, Animator);
        long duration = (long) a.getInt(1, 0);
        long startDelay = (long) a.getInt(2, 0);
        int valueType = a.getInt(7, 0);
        if (anim == null) {
            anim = new ValueAnimator();
        }
        boolean getFloats = valueType == 0;
        TypedValue tvFrom = a.peekValue(5);
        boolean hasFrom = tvFrom != null;
        int fromType = hasFrom ? tvFrom.type : 0;
        TypedValue tvTo = a.peekValue(6);
        boolean hasTo = tvTo != null;
        int toType = hasTo ? tvTo.type : 0;
        if ((hasFrom && fromType >= 28 && fromType <= 31) || (hasTo && toType >= 28 && toType <= 31)) {
            getFloats = false;
            anim.setEvaluator(new ArgbEvaluator());
        }
        if (getFloats) {
            float valueTo;
            if (hasFrom) {
                float valueFrom;
                if (fromType == 5) {
                    valueFrom = a.getDimension(5, 0.0f);
                } else {
                    valueFrom = a.getFloat(5, 0.0f);
                }
                if (hasTo) {
                    if (toType == 5) {
                        valueTo = a.getDimension(6, 0.0f);
                    } else {
                        valueTo = a.getFloat(6, 0.0f);
                    }
                    anim.setFloatValues(valueFrom, valueTo);
                } else {
                    anim.setFloatValues(valueFrom);
                }
            } else {
                if (toType == 5) {
                    valueTo = a.getDimension(6, 0.0f);
                } else {
                    valueTo = a.getFloat(6, 0.0f);
                }
                anim.setFloatValues(valueTo);
            }
        } else if (hasFrom) {
            int valueFrom2;
            if (fromType == 5) {
                valueFrom2 = (int) a.getDimension(5, 0.0f);
            } else if (fromType < 28 || fromType > 31) {
                valueFrom2 = a.getInt(5, 0);
            } else {
                valueFrom2 = a.getColor(5, 0);
            }
            if (hasTo) {
                if (toType == 5) {
                    valueTo = (int) a.getDimension(6, 0.0f);
                } else if (toType < 28 || toType > 31) {
                    valueTo = a.getInt(6, 0);
                } else {
                    valueTo = a.getColor(6, 0);
                }
                anim.setIntValues(valueFrom2, valueTo);
            } else {
                anim.setIntValues(valueFrom2);
            }
        } else if (hasTo) {
            if (toType == 5) {
                valueTo = (int) a.getDimension(6, 0.0f);
            } else if (toType < 28 || toType > 31) {
                valueTo = a.getInt(6, 0);
            } else {
                valueTo = a.getColor(6, 0);
            }
            anim.setIntValues(valueTo);
        }
        anim.setDuration(duration);
        anim.setStartDelay(startDelay);
        if (a.hasValue(3)) {
            anim.setRepeatCount(a.getInt(3, 0));
        }
        if (a.hasValue(4)) {
            anim.setRepeatMode(a.getInt(4, 1));
        }
        int resID = a.getResourceId(0, 0);
        if (resID > 0) {
            anim.setInterpolator(AnimationUtils.loadInterpolator(context, resID));
        }
        a.recycle();
        return anim;
    }
}

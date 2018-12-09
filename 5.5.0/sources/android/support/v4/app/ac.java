package android.support.v4.app;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.transition.Transition;
import android.transition.Transition.EpicenterCallback;
import android.transition.Transition.TransitionListener;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@TargetApi(21)
class ac {
    /* renamed from: a */
    public static Object m1118a(Object obj) {
        return obj != null ? ((Transition) obj).clone() : null;
    }

    /* renamed from: a */
    public static Object m1119a(Object obj, Object obj2, Object obj3) {
        TransitionSet transitionSet = new TransitionSet();
        if (obj != null) {
            transitionSet.addTransition((Transition) obj);
        }
        if (obj2 != null) {
            transitionSet.addTransition((Transition) obj2);
        }
        if (obj3 != null) {
            transitionSet.addTransition((Transition) obj3);
        }
        return transitionSet;
    }

    /* renamed from: a */
    public static ArrayList<String> m1121a(ArrayList<View> arrayList) {
        ArrayList<String> arrayList2 = new ArrayList();
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            View view = (View) arrayList.get(i);
            arrayList2.add(view.getTransitionName());
            view.setTransitionName(null);
        }
        return arrayList2;
    }

    /* renamed from: a */
    public static void m1122a(View view, Rect rect) {
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        rect.set(iArr[0], iArr[1], iArr[0] + view.getWidth(), iArr[1] + view.getHeight());
    }

    /* renamed from: a */
    public static void m1123a(View view, ArrayList<View> arrayList, ArrayList<View> arrayList2, ArrayList<String> arrayList3, Map<String, String> map) {
        final int size = arrayList2.size();
        final ArrayList arrayList4 = new ArrayList();
        for (int i = 0; i < size; i++) {
            View view2 = (View) arrayList.get(i);
            String transitionName = view2.getTransitionName();
            arrayList4.add(transitionName);
            if (transitionName != null) {
                view2.setTransitionName(null);
                String str = (String) map.get(transitionName);
                for (int i2 = 0; i2 < size; i2++) {
                    if (str.equals(arrayList3.get(i2))) {
                        ((View) arrayList2.get(i2)).setTransitionName(transitionName);
                        break;
                    }
                }
            }
        }
        final ArrayList<View> arrayList5 = arrayList2;
        final ArrayList<String> arrayList6 = arrayList3;
        final ArrayList<View> arrayList7 = arrayList;
        ax.m1389a(view, new Runnable() {
            public void run() {
                for (int i = 0; i < size; i++) {
                    ((View) arrayList5.get(i)).setTransitionName((String) arrayList6.get(i));
                    ((View) arrayList7.get(i)).setTransitionName((String) arrayList4.get(i));
                }
            }
        });
    }

    /* renamed from: a */
    public static void m1124a(View view, final ArrayList<View> arrayList, final Map<String, String> map) {
        ax.m1389a(view, new Runnable() {
            public void run() {
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    View view = (View) arrayList.get(i);
                    String transitionName = view.getTransitionName();
                    if (transitionName != null) {
                        view.setTransitionName(ac.m1141b(map, transitionName));
                    }
                }
            }
        });
    }

    /* renamed from: a */
    public static void m1125a(ViewGroup viewGroup, Object obj) {
        TransitionManager.beginDelayedTransition(viewGroup, (Transition) obj);
    }

    /* renamed from: a */
    public static void m1126a(ViewGroup viewGroup, final ArrayList<View> arrayList, final Map<String, String> map) {
        ax.m1389a(viewGroup, new Runnable() {
            public void run() {
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    View view = (View) arrayList.get(i);
                    view.setTransitionName((String) map.get(view.getTransitionName()));
                }
            }
        });
    }

    /* renamed from: a */
    public static void m1127a(Object obj, final Rect rect) {
        if (obj != null) {
            ((Transition) obj).setEpicenterCallback(new EpicenterCallback() {
                public Rect onGetEpicenter(Transition transition) {
                    return (rect == null || rect.isEmpty()) ? null : rect;
                }
            });
        }
    }

    /* renamed from: a */
    public static void m1128a(Object obj, View view) {
        if (view != null) {
            Transition transition = (Transition) obj;
            final Rect rect = new Rect();
            m1122a(view, rect);
            transition.setEpicenterCallback(new EpicenterCallback() {
                public Rect onGetEpicenter(Transition transition) {
                    return rect;
                }
            });
        }
    }

    /* renamed from: a */
    public static void m1129a(Object obj, View view, ArrayList<View> arrayList) {
        obj = (TransitionSet) obj;
        List targets = obj.getTargets();
        targets.clear();
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            m1134a(targets, (View) arrayList.get(i));
        }
        targets.add(view);
        arrayList.add(view);
        m1131a(obj, (ArrayList) arrayList);
    }

    /* renamed from: a */
    public static void m1130a(Object obj, Object obj2, ArrayList<View> arrayList, Object obj3, ArrayList<View> arrayList2, Object obj4, ArrayList<View> arrayList3) {
        final Object obj5 = obj2;
        final ArrayList<View> arrayList4 = arrayList;
        final Object obj6 = obj3;
        final ArrayList<View> arrayList5 = arrayList2;
        final Object obj7 = obj4;
        final ArrayList<View> arrayList6 = arrayList3;
        ((Transition) obj).addListener(new TransitionListener() {
            public void onTransitionCancel(Transition transition) {
            }

            public void onTransitionEnd(Transition transition) {
            }

            public void onTransitionPause(Transition transition) {
            }

            public void onTransitionResume(Transition transition) {
            }

            public void onTransitionStart(Transition transition) {
                if (obj5 != null) {
                    ac.m1144b(obj5, arrayList4, null);
                }
                if (obj6 != null) {
                    ac.m1144b(obj6, arrayList5, null);
                }
                if (obj7 != null) {
                    ac.m1144b(obj7, arrayList6, null);
                }
            }
        });
    }

    /* renamed from: a */
    public static void m1131a(Object obj, ArrayList<View> arrayList) {
        int i = 0;
        Transition transition = (Transition) obj;
        if (transition != null) {
            int transitionCount;
            if (transition instanceof TransitionSet) {
                TransitionSet transitionSet = (TransitionSet) transition;
                transitionCount = transitionSet.getTransitionCount();
                while (i < transitionCount) {
                    m1131a(transitionSet.getTransitionAt(i), (ArrayList) arrayList);
                    i++;
                }
            } else if (!m1136a(transition) && m1137a(transition.getTargets())) {
                int size = arrayList.size();
                for (transitionCount = 0; transitionCount < size; transitionCount++) {
                    transition.addTarget((View) arrayList.get(transitionCount));
                }
            }
        }
    }

    /* renamed from: a */
    public static void m1132a(Object obj, ArrayList<View> arrayList, ArrayList<View> arrayList2) {
        obj = (TransitionSet) obj;
        if (obj != null) {
            obj.getTargets().clear();
            obj.getTargets().addAll(arrayList2);
            m1144b(obj, (ArrayList) arrayList, (ArrayList) arrayList2);
        }
    }

    /* renamed from: a */
    public static void m1133a(ArrayList<View> arrayList, View view) {
        if (view.getVisibility() != 0) {
            return;
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            if (viewGroup.isTransitionGroup()) {
                arrayList.add(viewGroup);
                return;
            }
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                m1133a((ArrayList) arrayList, viewGroup.getChildAt(i));
            }
            return;
        }
        arrayList.add(view);
    }

    /* renamed from: a */
    private static void m1134a(List<View> list, View view) {
        int size = list.size();
        if (!m1138a((List) list, view, size)) {
            list.add(view);
            for (int i = size; i < list.size(); i++) {
                View view2 = (View) list.get(i);
                if (view2 instanceof ViewGroup) {
                    ViewGroup viewGroup = (ViewGroup) view2;
                    int childCount = viewGroup.getChildCount();
                    for (int i2 = 0; i2 < childCount; i2++) {
                        View childAt = viewGroup.getChildAt(i2);
                        if (!m1138a((List) list, childAt, size)) {
                            list.add(childAt);
                        }
                    }
                }
            }
        }
    }

    /* renamed from: a */
    public static void m1135a(Map<String, View> map, View view) {
        if (view.getVisibility() == 0) {
            String transitionName = view.getTransitionName();
            if (transitionName != null) {
                map.put(transitionName, view);
            }
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                int childCount = viewGroup.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    m1135a((Map) map, viewGroup.getChildAt(i));
                }
            }
        }
    }

    /* renamed from: a */
    private static boolean m1136a(Transition transition) {
        return (m1137a(transition.getTargetIds()) && m1137a(transition.getTargetNames()) && m1137a(transition.getTargetTypes())) ? false : true;
    }

    /* renamed from: a */
    private static boolean m1137a(List list) {
        return list == null || list.isEmpty();
    }

    /* renamed from: a */
    private static boolean m1138a(List<View> list, View view, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            if (list.get(i2) == view) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: b */
    public static Object m1139b(Object obj) {
        if (obj == null) {
            return null;
        }
        TransitionSet transitionSet = new TransitionSet();
        transitionSet.addTransition((Transition) obj);
        return transitionSet;
    }

    /* renamed from: b */
    public static Object m1140b(Object obj, Object obj2, Object obj3) {
        Transition transition = null;
        Transition transition2 = (Transition) obj;
        Transition transition3 = (Transition) obj2;
        Transition transition4 = (Transition) obj3;
        if (transition2 != null && transition3 != null) {
            transition = new TransitionSet().addTransition(transition2).addTransition(transition3).setOrdering(1);
        } else if (transition2 != null) {
            transition = transition2;
        } else if (transition3 != null) {
            transition = transition3;
        }
        if (transition4 == null) {
            return transition;
        }
        TransitionSet transitionSet = new TransitionSet();
        if (transition != null) {
            transitionSet.addTransition(transition);
        }
        transitionSet.addTransition(transition4);
        return transitionSet;
    }

    /* renamed from: b */
    private static String m1141b(Map<String, String> map, String str) {
        for (Entry entry : map.entrySet()) {
            if (str.equals(entry.getValue())) {
                return (String) entry.getKey();
            }
        }
        return null;
    }

    /* renamed from: b */
    public static void m1142b(Object obj, View view) {
        if (obj != null) {
            ((Transition) obj).addTarget(view);
        }
    }

    /* renamed from: b */
    public static void m1143b(Object obj, final View view, final ArrayList<View> arrayList) {
        ((Transition) obj).addListener(new TransitionListener() {
            public void onTransitionCancel(Transition transition) {
            }

            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                view.setVisibility(8);
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    ((View) arrayList.get(i)).setVisibility(0);
                }
            }

            public void onTransitionPause(Transition transition) {
            }

            public void onTransitionResume(Transition transition) {
            }

            public void onTransitionStart(Transition transition) {
            }
        });
    }

    /* renamed from: b */
    public static void m1144b(Object obj, ArrayList<View> arrayList, ArrayList<View> arrayList2) {
        int i = 0;
        Transition transition = (Transition) obj;
        int transitionCount;
        if (transition instanceof TransitionSet) {
            TransitionSet transitionSet = (TransitionSet) transition;
            transitionCount = transitionSet.getTransitionCount();
            while (i < transitionCount) {
                m1144b(transitionSet.getTransitionAt(i), (ArrayList) arrayList, (ArrayList) arrayList2);
                i++;
            }
        } else if (!m1136a(transition)) {
            List targets = transition.getTargets();
            if (targets != null && targets.size() == arrayList.size() && targets.containsAll(arrayList)) {
                transitionCount = arrayList2 == null ? 0 : arrayList2.size();
                for (int i2 = 0; i2 < transitionCount; i2++) {
                    transition.addTarget((View) arrayList2.get(i2));
                }
                for (transitionCount = arrayList.size() - 1; transitionCount >= 0; transitionCount--) {
                    transition.removeTarget((View) arrayList.get(transitionCount));
                }
            }
        }
    }

    /* renamed from: c */
    public static void m1145c(Object obj, View view) {
        if (obj != null) {
            ((Transition) obj).removeTarget(view);
        }
    }
}

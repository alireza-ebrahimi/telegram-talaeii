package com.mohamadamin.persianmaterialdatetimepicker.time;

import android.animation.ObjectAnimator;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.mohamadamin.persianmaterialdatetimepicker.C2021a;
import com.mohamadamin.persianmaterialdatetimepicker.C2028b.C2022a;
import com.mohamadamin.persianmaterialdatetimepicker.C2028b.C2024c;
import com.mohamadamin.persianmaterialdatetimepicker.C2028b.C2025d;
import com.mohamadamin.persianmaterialdatetimepicker.C2028b.C2026e;
import com.mohamadamin.persianmaterialdatetimepicker.C2028b.C2027f;
import com.mohamadamin.persianmaterialdatetimepicker.C2029c;
import com.mohamadamin.persianmaterialdatetimepicker.C2030d;
import com.mohamadamin.persianmaterialdatetimepicker.p121a.C2017a;
import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout.C2053a;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

/* renamed from: com.mohamadamin.persianmaterialdatetimepicker.time.e */
public class C2070e extends DialogFragment implements C2053a {
    /* renamed from: A */
    private ArrayList<Integer> f6205A;
    /* renamed from: B */
    private C2068b f6206B;
    /* renamed from: C */
    private int f6207C;
    /* renamed from: D */
    private int f6208D;
    /* renamed from: E */
    private String f6209E;
    /* renamed from: F */
    private String f6210F;
    /* renamed from: G */
    private String f6211G;
    /* renamed from: H */
    private String f6212H;
    /* renamed from: a */
    private C2069c f6213a;
    /* renamed from: b */
    private OnCancelListener f6214b;
    /* renamed from: c */
    private OnDismissListener f6215c;
    /* renamed from: d */
    private C2021a f6216d;
    /* renamed from: e */
    private Button f6217e;
    /* renamed from: f */
    private TextView f6218f;
    /* renamed from: g */
    private TextView f6219g;
    /* renamed from: h */
    private TextView f6220h;
    /* renamed from: i */
    private TextView f6221i;
    /* renamed from: j */
    private TextView f6222j;
    /* renamed from: k */
    private View f6223k;
    /* renamed from: l */
    private RadialPickerLayout f6224l;
    /* renamed from: m */
    private int f6225m;
    /* renamed from: n */
    private int f6226n;
    /* renamed from: o */
    private String f6227o;
    /* renamed from: p */
    private String f6228p;
    /* renamed from: q */
    private boolean f6229q;
    /* renamed from: r */
    private int f6230r;
    /* renamed from: s */
    private int f6231s;
    /* renamed from: t */
    private boolean f6232t;
    /* renamed from: u */
    private String f6233u;
    /* renamed from: v */
    private boolean f6234v;
    /* renamed from: w */
    private char f6235w;
    /* renamed from: x */
    private String f6236x;
    /* renamed from: y */
    private String f6237y;
    /* renamed from: z */
    private boolean f6238z;

    /* renamed from: com.mohamadamin.persianmaterialdatetimepicker.time.e$1 */
    class C20621 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ C2070e f6197a;

        C20621(C2070e c2070e) {
            this.f6197a = c2070e;
        }

        public void onClick(View view) {
            this.f6197a.m9260a(0, true, false, true);
            this.f6197a.m9282a();
        }
    }

    /* renamed from: com.mohamadamin.persianmaterialdatetimepicker.time.e$2 */
    class C20632 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ C2070e f6198a;

        C20632(C2070e c2070e) {
            this.f6198a = c2070e;
        }

        public void onClick(View view) {
            this.f6198a.m9260a(1, true, false, true);
            this.f6198a.m9282a();
        }
    }

    /* renamed from: com.mohamadamin.persianmaterialdatetimepicker.time.e$3 */
    class C20643 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ C2070e f6199a;

        C20643(C2070e c2070e) {
            this.f6199a = c2070e;
        }

        public void onClick(View view) {
            if (this.f6199a.f6238z && this.f6199a.m9273c()) {
                this.f6199a.m9264a(false);
            } else {
                this.f6199a.m9282a();
            }
            if (this.f6199a.f6213a != null) {
                this.f6199a.f6213a.mo3448a(this.f6199a.f6224l, this.f6199a.f6224l.getHours(), this.f6199a.f6224l.getMinutes());
            }
            this.f6199a.dismiss();
        }
    }

    /* renamed from: com.mohamadamin.persianmaterialdatetimepicker.time.e$4 */
    class C20654 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ C2070e f6200a;

        C20654(C2070e c2070e) {
            this.f6200a = c2070e;
        }

        public void onClick(View view) {
            this.f6200a.m9282a();
            this.f6200a.getDialog().cancel();
        }
    }

    /* renamed from: com.mohamadamin.persianmaterialdatetimepicker.time.e$5 */
    class C20665 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ C2070e f6201a;

        C20665(C2070e c2070e) {
            this.f6201a = c2070e;
        }

        public void onClick(View view) {
            int i = 1;
            this.f6201a.m9282a();
            int isCurrentlyAmOrPm = this.f6201a.f6224l.getIsCurrentlyAmOrPm();
            if (isCurrentlyAmOrPm != 0) {
                i = isCurrentlyAmOrPm == 1 ? 0 : isCurrentlyAmOrPm;
            }
            this.f6201a.m9258a(i);
            this.f6201a.f6224l.setAmOrPm(i);
        }
    }

    /* renamed from: com.mohamadamin.persianmaterialdatetimepicker.time.e$a */
    private class C2067a implements OnKeyListener {
        /* renamed from: a */
        final /* synthetic */ C2070e f6202a;

        private C2067a(C2070e c2070e) {
            this.f6202a = c2070e;
        }

        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            return keyEvent.getAction() == 1 ? this.f6202a.m9274c(i) : false;
        }
    }

    /* renamed from: com.mohamadamin.persianmaterialdatetimepicker.time.e$b */
    private static class C2068b {
        /* renamed from: a */
        private int[] f6203a;
        /* renamed from: b */
        private ArrayList<C2068b> f6204b = new ArrayList();

        public C2068b(int... iArr) {
            this.f6203a = iArr;
        }

        /* renamed from: a */
        public void m9253a(C2068b c2068b) {
            this.f6204b.add(c2068b);
        }

        /* renamed from: a */
        public boolean m9254a(int i) {
            for (int i2 : this.f6203a) {
                if (i2 == i) {
                    return true;
                }
            }
            return false;
        }

        /* renamed from: b */
        public C2068b m9255b(int i) {
            if (this.f6204b == null) {
                return null;
            }
            Iterator it = this.f6204b.iterator();
            while (it.hasNext()) {
                C2068b c2068b = (C2068b) it.next();
                if (c2068b.m9254a(i)) {
                    return c2068b;
                }
            }
            return null;
        }
    }

    /* renamed from: com.mohamadamin.persianmaterialdatetimepicker.time.e$c */
    public interface C2069c {
        /* renamed from: a */
        void mo3448a(RadialPickerLayout radialPickerLayout, int i, int i2);
    }

    /* renamed from: a */
    public static C2070e m9257a(C2069c c2069c, int i, int i2, boolean z) {
        C2070e c2070e = new C2070e();
        c2070e.m9284b(c2069c, i, i2, z);
        return c2070e;
    }

    /* renamed from: a */
    private void m9258a(int i) {
        if (i == 0) {
            this.f6222j.setText(this.f6227o);
            C2030d.m9116a(this.f6224l, this.f6227o);
            this.f6223k.setContentDescription(this.f6227o);
        } else if (i == 1) {
            this.f6222j.setText(this.f6228p);
            C2030d.m9116a(this.f6224l, this.f6228p);
            this.f6223k.setContentDescription(this.f6228p);
        } else {
            this.f6222j.setText(this.f6236x);
        }
    }

    /* renamed from: a */
    private void m9259a(int i, boolean z) {
        String str;
        if (this.f6232t) {
            str = "%02d";
        } else {
            str = "%d";
            i %= 12;
            if (i == 0) {
                i = 12;
            }
        }
        CharSequence a = C2017a.m9087a(String.format(str, new Object[]{Integer.valueOf(i)}));
        this.f6218f.setText(a);
        this.f6219g.setText(a);
        if (z) {
            C2030d.m9116a(this.f6224l, a);
        }
    }

    /* renamed from: a */
    private void m9260a(int i, boolean z, boolean z2, boolean z3) {
        int hours;
        View view;
        this.f6224l.m9235a(i, z);
        if (i == 0) {
            hours = this.f6224l.getHours();
            if (!this.f6232t) {
                hours %= 12;
            }
            this.f6224l.setContentDescription(this.f6209E + ": " + hours);
            if (z3) {
                C2030d.m9116a(this.f6224l, this.f6210F);
            }
            view = this.f6218f;
        } else {
            this.f6224l.setContentDescription(this.f6211G + ": " + this.f6224l.getMinutes());
            if (z3) {
                C2030d.m9116a(this.f6224l, this.f6212H);
            }
            view = this.f6220h;
        }
        int i2 = i == 0 ? this.f6225m : this.f6226n;
        hours = i == 1 ? this.f6225m : this.f6226n;
        this.f6218f.setTextColor(i2);
        this.f6220h.setTextColor(hours);
        ObjectAnimator a = C2030d.m9115a(view, 0.85f, 1.1f);
        if (z2) {
            a.setStartDelay(300);
        }
        a.start();
    }

    /* renamed from: a */
    private void m9264a(boolean z) {
        this.f6238z = false;
        if (!this.f6205A.isEmpty()) {
            int[] a = m9266a(null);
            this.f6224l.m9234a(a[0], a[1]);
            if (!this.f6232t) {
                this.f6224l.setAmOrPm(a[2]);
            }
            this.f6205A.clear();
        }
        if (z) {
            m9268b(false);
            this.f6224l.m9238a(true);
        }
    }

    /* renamed from: a */
    private int[] m9266a(Boolean[] boolArr) {
        int i;
        int i2;
        int intValue;
        if (this.f6232t || !m9273c()) {
            i = 1;
            i2 = -1;
        } else {
            intValue = ((Integer) this.f6205A.get(this.f6205A.size() - 1)).intValue();
            if (intValue == m9281g(0)) {
                intValue = 0;
            } else if (intValue == m9281g(1)) {
                boolean z = true;
            } else {
                intValue = -1;
            }
            i = 2;
            i2 = intValue;
        }
        int i3 = -1;
        int i4 = -1;
        for (int i5 = i; i5 <= this.f6205A.size(); i5++) {
            intValue = C2070e.m9280f(((Integer) this.f6205A.get(this.f6205A.size() - i5)).intValue());
            if (i5 == i) {
                i4 = intValue;
            } else if (i5 == i + 1) {
                i4 += intValue * 10;
                if (boolArr != null && intValue == 0) {
                    boolArr[1] = Boolean.valueOf(true);
                }
            } else if (i5 == i + 2) {
                i3 = intValue;
            } else if (i5 == i + 3) {
                i3 += intValue * 10;
                if (boolArr != null && intValue == 0) {
                    boolArr[0] = Boolean.valueOf(true);
                }
            }
        }
        return new int[]{i3, i4, i2};
    }

    /* renamed from: b */
    private void m9267b(int i) {
        if (i == 60) {
            i = 0;
        }
        CharSequence a = C2017a.m9087a(String.format(Locale.getDefault(), "%02d", new Object[]{Integer.valueOf(i)}));
        C2030d.m9116a(this.f6224l, a);
        this.f6220h.setText(a);
        this.f6221i.setText(a);
    }

    /* renamed from: b */
    private void m9268b(boolean z) {
        int i = 0;
        if (z || !this.f6205A.isEmpty()) {
            Boolean[] boolArr = new Boolean[]{Boolean.valueOf(false), Boolean.valueOf(false)};
            int[] a = m9266a(boolArr);
            String str = boolArr[0].booleanValue() ? "%02d" : "%2d";
            String str2 = boolArr[1].booleanValue() ? "%02d" : "%2d";
            str = a[0] == -1 ? this.f6236x : String.format(str, new Object[]{Integer.valueOf(a[0])}).replace(' ', this.f6235w);
            String replace = a[1] == -1 ? this.f6236x : String.format(str2, new Object[]{Integer.valueOf(a[1])}).replace(' ', this.f6235w);
            this.f6218f.setText(C2017a.m9087a(str));
            this.f6219g.setText(C2017a.m9087a(str));
            this.f6218f.setTextColor(this.f6226n);
            this.f6220h.setText(C2017a.m9087a(replace));
            this.f6221i.setText(C2017a.m9087a(replace));
            this.f6220h.setTextColor(this.f6226n);
            if (!this.f6232t) {
                m9258a(a[2]);
                return;
            }
            return;
        }
        int hours = this.f6224l.getHours();
        int minutes = this.f6224l.getMinutes();
        m9259a(hours, true);
        m9267b(minutes);
        if (!this.f6232t) {
            if (hours >= 12) {
                i = 1;
            }
            m9258a(i);
        }
        m9260a(this.f6224l.getCurrentItemShowing(), true, true, true);
        this.f6217e.setEnabled(true);
    }

    /* renamed from: b */
    private boolean m9269b() {
        C2068b c2068b = this.f6206B;
        Iterator it = this.f6205A.iterator();
        C2068b c2068b2 = c2068b;
        while (it.hasNext()) {
            c2068b = c2068b2.m9255b(((Integer) it.next()).intValue());
            if (c2068b == null) {
                return false;
            }
            c2068b2 = c2068b;
        }
        return true;
    }

    /* renamed from: c */
    private boolean m9273c() {
        boolean z = false;
        if (this.f6232t) {
            int[] a = m9266a(null);
            return a[0] >= 0 && a[1] >= 0 && a[1] < 60;
        } else {
            if (this.f6205A.contains(Integer.valueOf(m9281g(0))) || this.f6205A.contains(Integer.valueOf(m9281g(1)))) {
                z = true;
            }
            return z;
        }
    }

    /* renamed from: c */
    private boolean m9274c(int i) {
        if (i == 111 || i == 4) {
            if (isCancelable()) {
                dismiss();
            }
            return true;
        }
        if (i == 61) {
            if (this.f6238z) {
                if (m9273c()) {
                    m9264a(true);
                }
                return true;
            }
        } else if (i == 66) {
            if (this.f6238z) {
                if (!m9273c()) {
                    return true;
                }
                m9264a(false);
            }
            if (this.f6213a != null) {
                this.f6213a.mo3448a(this.f6224l, this.f6224l.getHours(), this.f6224l.getMinutes());
            }
            dismiss();
            return true;
        } else if (i == 67) {
            if (this.f6238z && !this.f6205A.isEmpty()) {
                String str;
                int d = m9275d();
                if (d == m9281g(0)) {
                    str = this.f6227o;
                } else if (d == m9281g(1)) {
                    str = this.f6228p;
                } else {
                    str = String.format("%d", new Object[]{Integer.valueOf(C2070e.m9280f(d))});
                }
                C2030d.m9116a(this.f6224l, String.format(this.f6237y, new Object[]{str}));
                m9268b(true);
            }
        } else if (i == 7 || i == 8 || i == 9 || i == 10 || i == 11 || i == 12 || i == 13 || i == 14 || i == 15 || i == 16 || (!this.f6232t && (i == m9281g(0) || i == m9281g(1)))) {
            if (this.f6238z) {
                if (m9279e(i)) {
                    m9268b(false);
                }
                return true;
            } else if (this.f6224l == null) {
                Log.e("TimePickerDialog", "Unable to initiate keyboard mode, TimePicker was null.");
                return true;
            } else {
                this.f6205A.clear();
                m9277d(i);
                return true;
            }
        }
        return false;
    }

    /* renamed from: d */
    private int m9275d() {
        int intValue = ((Integer) this.f6205A.remove(this.f6205A.size() - 1)).intValue();
        if (!m9273c()) {
            this.f6217e.setEnabled(false);
        }
        return intValue;
    }

    /* renamed from: d */
    private void m9277d(int i) {
        if (!this.f6224l.m9238a(false)) {
            return;
        }
        if (i == -1 || m9279e(i)) {
            this.f6238z = true;
            this.f6217e.setEnabled(false);
            m9268b(false);
        }
    }

    /* renamed from: e */
    private void m9278e() {
        this.f6206B = new C2068b(new int[0]);
        if (this.f6232t) {
            C2068b c2068b = new C2068b(7, 8, 9, 10, 11, 12);
            C2068b c2068b2 = new C2068b(7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
            c2068b.m9253a(c2068b2);
            C2068b c2068b3 = new C2068b(7, 8);
            this.f6206B.m9253a(c2068b3);
            C2068b c2068b4 = new C2068b(7, 8, 9, 10, 11, 12);
            c2068b3.m9253a(c2068b4);
            c2068b4.m9253a(c2068b);
            c2068b4.m9253a(new C2068b(13, 14, 15, 16));
            c2068b4 = new C2068b(13, 14, 15, 16);
            c2068b3.m9253a(c2068b4);
            c2068b4.m9253a(c2068b);
            c2068b3 = new C2068b(9);
            this.f6206B.m9253a(c2068b3);
            c2068b4 = new C2068b(7, 8, 9, 10);
            c2068b3.m9253a(c2068b4);
            c2068b4.m9253a(c2068b);
            C2068b c2068b5 = new C2068b(11, 12);
            c2068b3.m9253a(c2068b5);
            c2068b5.m9253a(c2068b2);
            c2068b5 = new C2068b(10, 11, 12, 13, 14, 15, 16);
            this.f6206B.m9253a(c2068b5);
            c2068b5.m9253a(c2068b);
            return;
        }
        c2068b = new C2068b(m9281g(0), m9281g(1));
        c2068b2 = new C2068b(8);
        this.f6206B.m9253a(c2068b2);
        c2068b2.m9253a(c2068b);
        c2068b3 = new C2068b(7, 8, 9);
        c2068b2.m9253a(c2068b3);
        c2068b3.m9253a(c2068b);
        c2068b4 = new C2068b(7, 8, 9, 10, 11, 12);
        c2068b3.m9253a(c2068b4);
        c2068b4.m9253a(c2068b);
        C2068b c2068b6 = new C2068b(7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
        c2068b4.m9253a(c2068b6);
        c2068b6.m9253a(c2068b);
        c2068b4 = new C2068b(13, 14, 15, 16);
        c2068b3.m9253a(c2068b4);
        c2068b4.m9253a(c2068b);
        c2068b3 = new C2068b(10, 11, 12);
        c2068b2.m9253a(c2068b3);
        c2068b2 = new C2068b(7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
        c2068b3.m9253a(c2068b2);
        c2068b2.m9253a(c2068b);
        c2068b2 = new C2068b(9, 10, 11, 12, 13, 14, 15, 16);
        this.f6206B.m9253a(c2068b2);
        c2068b2.m9253a(c2068b);
        c2068b3 = new C2068b(7, 8, 9, 10, 11, 12);
        c2068b2.m9253a(c2068b3);
        c2068b2 = new C2068b(7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
        c2068b3.m9253a(c2068b2);
        c2068b2.m9253a(c2068b);
    }

    /* renamed from: e */
    private boolean m9279e(int i) {
        if (this.f6232t && this.f6205A.size() == 4) {
            return false;
        }
        if (!this.f6232t && m9273c()) {
            return false;
        }
        this.f6205A.add(Integer.valueOf(i));
        if (m9269b()) {
            int f = C2070e.m9280f(i);
            C2030d.m9116a(this.f6224l, String.format("%d", new Object[]{Integer.valueOf(f)}));
            if (m9273c()) {
                if (!this.f6232t && this.f6205A.size() <= 3) {
                    this.f6205A.add(this.f6205A.size() - 1, Integer.valueOf(7));
                    this.f6205A.add(this.f6205A.size() - 1, Integer.valueOf(7));
                }
                this.f6217e.setEnabled(true);
            }
            return true;
        }
        m9275d();
        return false;
    }

    /* renamed from: f */
    private static int m9280f(int i) {
        switch (i) {
            case 7:
                return 0;
            case 8:
                return 1;
            case 9:
                return 2;
            case 10:
                return 3;
            case 11:
                return 4;
            case 12:
                return 5;
            case 13:
                return 6;
            case 14:
                return 7;
            case 15:
                return 8;
            case 16:
                return 9;
            default:
                return -1;
        }
    }

    /* renamed from: g */
    private int m9281g(int i) {
        if (this.f6207C == -1 || this.f6208D == -1) {
            KeyCharacterMap load = KeyCharacterMap.load(-1);
            int i2 = 0;
            while (i2 < Math.max(this.f6227o.length(), this.f6228p.length())) {
                if ("AM".toLowerCase(Locale.getDefault()).charAt(i2) != "PM".toLowerCase(Locale.getDefault()).charAt(i2)) {
                    KeyEvent[] events = load.getEvents(new char[]{"AM".toLowerCase(Locale.getDefault()).charAt(i2), "PM".toLowerCase(Locale.getDefault()).charAt(i2)});
                    if (events == null || events.length != 4) {
                        Log.e("TimePickerDialog", "Unable to find keycodes for AM and PM.");
                    } else {
                        this.f6207C = events[0].getKeyCode();
                        this.f6208D = events[2].getKeyCode();
                    }
                } else {
                    i2++;
                }
            }
        }
        return i == 0 ? this.f6207C : i == 1 ? this.f6208D : -1;
    }

    /* renamed from: a */
    public void m9282a() {
        this.f6216d.m9112c();
    }

    /* renamed from: a */
    public void mo3087a(int i, int i2, boolean z) {
        if (i == 0) {
            m9259a(i2, false);
            CharSequence format = String.format("%d", new Object[]{Integer.valueOf(i2)});
            if (this.f6229q && z) {
                m9260a(1, true, true, false);
                format = format + ". " + this.f6212H;
            } else {
                this.f6224l.setContentDescription(this.f6209E + ": " + i2);
            }
            C2030d.m9116a(this.f6224l, format);
        } else if (i == 1) {
            m9267b(i2);
            this.f6224l.setContentDescription(this.f6211G + ": " + i2);
        } else if (i == 2) {
            m9258a(i2);
        } else if (i == 3) {
            if (!m9273c()) {
                this.f6205A.clear();
            }
            m9264a(true);
        }
    }

    /* renamed from: b */
    public void m9284b(C2069c c2069c, int i, int i2, boolean z) {
        this.f6213a = c2069c;
        this.f6230r = i;
        this.f6231s = i2;
        this.f6232t = z;
        this.f6238z = false;
        this.f6233u = TtmlNode.ANONYMOUS_REGION_ID;
        this.f6234v = false;
    }

    public void onCancel(DialogInterface dialogInterface) {
        super.onCancel(dialogInterface);
        if (this.f6214b != null) {
            this.f6214b.onCancel(dialogInterface);
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null && bundle.containsKey("hour_of_day") && bundle.containsKey("minute") && bundle.containsKey("is_24_hour_view")) {
            this.f6230r = bundle.getInt("hour_of_day");
            this.f6231s = bundle.getInt("minute");
            this.f6232t = bundle.getBoolean("is_24_hour_view");
            this.f6238z = bundle.getBoolean("in_kb_mode");
            this.f6233u = bundle.getString("dialog_title");
            this.f6234v = bundle.getBoolean("dark_theme");
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        getDialog().getWindow().requestFeature(1);
        View inflate = layoutInflater.inflate(C2026e.mdtp_time_picker_dialog, null);
        OnKeyListener c2067a = new C2067a();
        inflate.findViewById(C2025d.time_picker_dialog).setOnKeyListener(c2067a);
        Resources resources = getResources();
        this.f6209E = resources.getString(C2027f.mdtp_hour_picker_description);
        this.f6210F = resources.getString(C2027f.mdtp_select_hours);
        this.f6211G = resources.getString(C2027f.mdtp_minute_picker_description);
        this.f6212H = resources.getString(C2027f.mdtp_select_minutes);
        this.f6225m = resources.getColor(C2022a.mdtp_white);
        this.f6226n = resources.getColor(C2022a.mdtp_accent_color_focused);
        this.f6218f = (TextView) inflate.findViewById(C2025d.hours);
        this.f6218f.setOnKeyListener(c2067a);
        this.f6219g = (TextView) inflate.findViewById(C2025d.hour_space);
        this.f6221i = (TextView) inflate.findViewById(C2025d.minutes_space);
        this.f6220h = (TextView) inflate.findViewById(C2025d.minutes);
        this.f6220h.setOnKeyListener(c2067a);
        this.f6222j = (TextView) inflate.findViewById(C2025d.ampm_label);
        this.f6222j.setOnKeyListener(c2067a);
        this.f6227o = "قبل‌ازظهر";
        this.f6228p = "بعدازظهر";
        this.f6216d = new C2021a(getActivity());
        this.f6224l = (RadialPickerLayout) inflate.findViewById(C2025d.time_picker);
        this.f6224l.setOnValueSelectedListener(this);
        this.f6224l.setOnKeyListener(c2067a);
        this.f6224l.m9236a(getActivity(), this.f6216d, this.f6230r, this.f6231s, this.f6232t);
        int i = 0;
        if (bundle != null && bundle.containsKey("current_item_showing")) {
            i = bundle.getInt("current_item_showing");
        }
        m9260a(i, false, true, true);
        this.f6224l.invalidate();
        this.f6218f.setOnClickListener(new C20621(this));
        this.f6220h.setOnClickListener(new C20632(this));
        this.f6217e = (Button) inflate.findViewById(C2025d.ok);
        this.f6217e.setOnClickListener(new C20643(this));
        this.f6217e.setOnKeyListener(c2067a);
        this.f6217e.setTypeface(C2029c.m9113a(getDialog().getContext(), "Roboto-Medium"));
        Button button = (Button) inflate.findViewById(C2025d.cancel);
        button.setOnClickListener(new C20654(this));
        button.setTypeface(C2029c.m9113a(getDialog().getContext(), "Roboto-Medium"));
        button.setVisibility(isCancelable() ? 0 : 8);
        this.f6223k = inflate.findViewById(C2025d.ampm_hitspace);
        if (this.f6232t) {
            this.f6222j.setVisibility(8);
            LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
            layoutParams.addRule(13);
            ((TextView) inflate.findViewById(C2025d.separator)).setLayoutParams(layoutParams);
        } else {
            this.f6222j.setVisibility(0);
            m9258a(this.f6230r < 12 ? 0 : 1);
            this.f6223k.setOnClickListener(new C20665(this));
        }
        this.f6229q = true;
        m9259a(this.f6230r, true);
        m9267b(this.f6231s);
        this.f6236x = resources.getString(C2027f.mdtp_time_placeholder);
        this.f6237y = resources.getString(C2027f.mdtp_deleted_key);
        this.f6235w = this.f6236x.charAt(0);
        this.f6208D = -1;
        this.f6207C = -1;
        m9278e();
        if (this.f6238z) {
            this.f6205A = bundle.getIntegerArrayList("typed_times");
            m9277d(-1);
            this.f6218f.invalidate();
        } else if (this.f6205A == null) {
            this.f6205A = new ArrayList();
        }
        TextView textView = (TextView) inflate.findViewById(C2025d.time_picker_header);
        if (!this.f6233u.isEmpty()) {
            textView.setVisibility(0);
            textView.setText(this.f6233u);
        }
        this.f6224l.m9237a(getActivity().getApplicationContext(), this.f6234v);
        resources.getColor(C2022a.mdtp_white);
        resources.getColor(C2022a.mdtp_accent_color);
        int color = resources.getColor(C2022a.mdtp_circle_background);
        resources.getColor(C2022a.mdtp_line_background);
        resources.getColor(C2022a.mdtp_numbers_text_color);
        resources.getColorStateList(C2022a.mdtp_done_text_color);
        i = C2024c.mdtp_done_background_color;
        int color2 = resources.getColor(C2022a.mdtp_background_color);
        int color3 = resources.getColor(C2022a.mdtp_light_gray);
        resources.getColor(C2022a.mdtp_dark_gray);
        i = resources.getColor(C2022a.mdtp_light_gray);
        resources.getColor(C2022a.mdtp_line_dark);
        resources.getColorStateList(C2022a.mdtp_done_text_color_dark);
        int i2 = C2024c.mdtp_done_background_color_dark;
        RadialPickerLayout radialPickerLayout = this.f6224l;
        if (!this.f6234v) {
            i = color;
        }
        radialPickerLayout.setBackgroundColor(i);
        inflate.findViewById(C2025d.time_picker_dialog).setBackgroundColor(this.f6234v ? color3 : color2);
        return inflate;
    }

    public void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        if (this.f6215c != null) {
            this.f6215c.onDismiss(dialogInterface);
        }
    }

    public void onPause() {
        super.onPause();
        this.f6216d.m9111b();
    }

    public void onResume() {
        super.onResume();
        this.f6216d.m9110a();
    }

    public void onSaveInstanceState(Bundle bundle) {
        if (this.f6224l != null) {
            bundle.putInt("hour_of_day", this.f6224l.getHours());
            bundle.putInt("minute", this.f6224l.getMinutes());
            bundle.putBoolean("is_24_hour_view", this.f6232t);
            bundle.putInt("current_item_showing", this.f6224l.getCurrentItemShowing());
            bundle.putBoolean("in_kb_mode", this.f6238z);
            if (this.f6238z) {
                bundle.putIntegerArrayList("typed_times", this.f6205A);
            }
            bundle.putString("dialog_title", this.f6233u);
            bundle.putBoolean("dark_theme", this.f6234v);
        }
    }
}

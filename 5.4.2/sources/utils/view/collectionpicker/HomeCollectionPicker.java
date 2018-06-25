package utils.view.collectionpicker;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Handler;
import android.support.v4.content.C0235a;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.ir.talaeii.R;
import org.telegram.messenger.C3336R;

public class HomeCollectionPicker extends LinearLayout {
    /* renamed from: a */
    private ViewTreeObserver f10502a;
    /* renamed from: b */
    private LayoutInflater f10503b;
    /* renamed from: c */
    private List<Item> f10504c;
    /* renamed from: d */
    private LinearLayout f10505d;
    /* renamed from: e */
    private HashMap<String, Object> f10506e;
    /* renamed from: f */
    private OnItemClickListener f10507f;
    /* renamed from: g */
    private int f10508g;
    /* renamed from: h */
    private int f10509h;
    /* renamed from: i */
    private int f10510i;
    /* renamed from: j */
    private int f10511j;
    /* renamed from: k */
    private int f10512k;
    /* renamed from: l */
    private int f10513l;
    /* renamed from: m */
    private int f10514m;
    /* renamed from: n */
    private int f10515n;
    /* renamed from: o */
    private int f10516o;
    /* renamed from: p */
    private int f10517p;
    /* renamed from: q */
    private int f10518q;
    /* renamed from: r */
    private int f10519r;
    /* renamed from: s */
    private boolean f10520s;
    /* renamed from: t */
    private boolean f10521t;
    /* renamed from: u */
    private int f10522u;

    /* renamed from: utils.view.collectionpicker.HomeCollectionPicker$1 */
    class C53511 implements OnGlobalLayoutListener {
        /* renamed from: a */
        final /* synthetic */ HomeCollectionPicker f10485a;

        C53511(HomeCollectionPicker homeCollectionPicker) {
            this.f10485a = homeCollectionPicker;
        }

        public void onGlobalLayout() {
            if (!this.f10485a.f10520s) {
                this.f10485a.f10520s = true;
                this.f10485a.m14389a();
            }
        }
    }

    /* renamed from: utils.view.collectionpicker.HomeCollectionPicker$3 */
    class C53543 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ Item f10490a;
        /* renamed from: b */
        final /* synthetic */ int f10491b;
        /* renamed from: c */
        final /* synthetic */ HomeCollectionPicker f10492c;

        /* renamed from: utils.view.collectionpicker.HomeCollectionPicker$3$1 */
        class C53531 implements Runnable {
            /* renamed from: a */
            final /* synthetic */ C53543 f10489a;

            C53531(C53543 c53543) {
                this.f10489a = c53543;
            }

            public void run() {
                this.f10489a.f10492c.f10507f.onClick(this.f10489a.f10490a, this.f10489a.f10491b);
            }
        }

        public void onClick(View view) {
            this.f10492c.m14378a(view);
            this.f10490a.f10525c = !this.f10490a.f10525c;
            if (this.f10490a.f10525c) {
                this.f10492c.f10506e.put(this.f10490a.f10523a, this.f10490a);
            } else {
                this.f10492c.f10506e.remove(this.f10490a.f10523a);
            }
            if (this.f10492c.f10507f != null) {
                new Handler().postDelayed(new C53531(this), 200);
            }
        }
    }

    /* renamed from: utils.view.collectionpicker.HomeCollectionPicker$4 */
    class C53554 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ Item f10493a;
        /* renamed from: b */
        final /* synthetic */ View f10494b;
        /* renamed from: c */
        final /* synthetic */ int f10495c;
        /* renamed from: d */
        final /* synthetic */ HomeCollectionPicker f10496d;

        public void onClick(View view) {
            this.f10496d.m14378a(view);
            this.f10493a.f10525c = !this.f10493a.f10525c;
            if (this.f10493a.f10525c) {
                this.f10496d.f10506e.put(this.f10493a.f10523a, this.f10493a);
            } else {
                this.f10496d.f10506e.remove(this.f10493a.f10523a);
            }
            this.f10494b.setSelected(this.f10493a.f10525c);
            ((TextView) this.f10494b.findViewById(R.id.item_name)).setTextColor(this.f10496d.getResources().getColor(this.f10493a.f10525c ? R.color.black : this.f10496d.f10518q));
            if (this.f10496d.f10507f != null) {
                this.f10496d.f10507f.onClick(this.f10493a, this.f10495c);
            }
        }
    }

    public HomeCollectionPicker(Context context) {
        this(context, null);
    }

    public HomeCollectionPicker(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public HomeCollectionPicker(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f10504c = new ArrayList();
        this.f10506e = new HashMap();
        this.f10509h = 10;
        this.f10510i = 0;
        this.f10511j = 0;
        this.f10512k = 0;
        this.f10513l = 0;
        this.f10514m = 17301555;
        this.f10515n = 17301560;
        this.f10516o = C0235a.c(getContext(), R.color.blue);
        this.f10517p = C0235a.c(getContext(), R.color.red);
        this.f10518q = C0235a.c(getContext(), R.color.white);
        this.f10519r = 10;
        this.f10521t = false;
        this.f10522u = R.layout.collection_picker_item_layout_home;
        this.f10503b = (LayoutInflater) context.getSystemService("layout_inflater");
        setResID(R.layout.collection_picker_item_layout_home);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C3336R.styleable.CollectionPicker);
        this.f10509h = (int) obtainStyledAttributes.getDimension(5, (float) Utils.m14394a(getContext(), this.f10509h));
        this.f10510i = (int) obtainStyledAttributes.getDimension(7, (float) Utils.m14394a(getContext(), this.f10510i));
        this.f10511j = (int) obtainStyledAttributes.getDimension(8, (float) Utils.m14394a(getContext(), this.f10511j));
        this.f10512k = (int) obtainStyledAttributes.getDimension(9, (float) Utils.m14394a(getContext(), this.f10512k));
        this.f10513l = (int) obtainStyledAttributes.getDimension(10, (float) Utils.m14394a(getContext(), this.f10513l));
        this.f10514m = obtainStyledAttributes.getResourceId(3, this.f10514m);
        this.f10515n = obtainStyledAttributes.getResourceId(4, this.f10515n);
        this.f10516o = obtainStyledAttributes.getColor(1, this.f10516o);
        this.f10517p = obtainStyledAttributes.getColor(2, this.f10517p);
        this.f10519r = (int) obtainStyledAttributes.getDimension(0, (float) this.f10519r);
        this.f10518q = obtainStyledAttributes.getColor(6, this.f10518q);
        obtainStyledAttributes.recycle();
        setOrientation(1);
        setGravity(1);
        this.f10502a = getViewTreeObserver();
        this.f10502a.addOnGlobalLayoutListener(new C53511(this));
    }

    /* renamed from: a */
    private View m14377a(Item item) {
        return this.f10503b.inflate(this.f10522u, this, false);
    }

    /* renamed from: a */
    private void m14378a(final View view) {
        view.setScaleY(1.0f);
        view.setScaleX(1.0f);
        view.animate().scaleX(1.1f).scaleY(1.1f).setDuration(100).setStartDelay(0).setInterpolator(new DecelerateInterpolator()).setListener(new AnimatorListener(this) {
            /* renamed from: b */
            final /* synthetic */ HomeCollectionPicker f10501b;

            public void onAnimationCancel(Animator animator) {
            }

            public void onAnimationEnd(Animator animator) {
                this.f10501b.m14385b(view);
            }

            public void onAnimationRepeat(Animator animator) {
            }

            public void onAnimationStart(Animator animator) {
            }
        }).start();
    }

    /* renamed from: a */
    private void m14379a(View view, int i) {
        long j = 600 + ((long) (i * 30));
        view.setScaleY(BitmapDescriptorFactory.HUE_RED);
        view.setScaleX(BitmapDescriptorFactory.HUE_RED);
        view.animate().scaleY(1.0f).scaleX(1.0f).setDuration(200).setInterpolator(new DecelerateInterpolator()).setListener(null).setStartDelay(j).start();
    }

    /* renamed from: a */
    private void m14380a(View view, LayoutParams layoutParams, boolean z, int i) {
        if (this.f10505d == null || z) {
            this.f10505d = new LinearLayout(getContext());
            this.f10505d.setGravity(17);
            this.f10505d.setOrientation(0);
            this.f10505d.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
            addView(this.f10505d);
        }
        if (view != null) {
            this.f10505d.addView(view, layoutParams);
            if (!this.f10521t) {
                m14379a(view, i);
            }
        }
    }

    /* renamed from: b */
    private void m14385b(View view) {
        view.setScaleY(1.2f);
        view.setScaleX(1.2f);
        view.animate().scaleX(1.0f).scaleY(1.0f).setDuration(100).setListener(null).start();
    }

    private LinearLayout.LayoutParams getItemLayoutParams() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.bottomMargin = this.f10509h / 2;
        layoutParams.topMargin = this.f10509h / 2;
        return layoutParams;
    }

    private StateListDrawable getSelectorNormal() {
        StateListDrawable stateListDrawable = new StateListDrawable();
        Drawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(this.f10517p);
        gradientDrawable.setCornerRadius((float) this.f10519r);
        stateListDrawable.addState(new int[]{16842919}, gradientDrawable);
        gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(this.f10516o);
        gradientDrawable.setCornerRadius((float) this.f10519r);
        stateListDrawable.addState(new int[0], gradientDrawable);
        return stateListDrawable;
    }

    private StateListDrawable getSelectorSelected() {
        StateListDrawable stateListDrawable = new StateListDrawable();
        Drawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(this.f10516o);
        gradientDrawable.setCornerRadius((float) this.f10519r);
        stateListDrawable.addState(new int[]{16842919}, gradientDrawable);
        gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(this.f10517p);
        gradientDrawable.setCornerRadius((float) this.f10519r);
        stateListDrawable.addState(new int[0], gradientDrawable);
        return stateListDrawable;
    }

    /* renamed from: a */
    public void m14389a() {
        if (this.f10520s) {
            m14391b();
            float paddingLeft = (float) (getPaddingLeft() + getPaddingRight());
            LayoutParams itemLayoutParams = getItemLayoutParams();
            boolean z = false;
            boolean z2 = false;
            float f = paddingLeft;
            while (z < this.f10504c.size()) {
                boolean z3;
                final Item item = (Item) this.f10504c.get(z);
                if (this.f10506e != null && this.f10506e.containsKey(item.f10523a)) {
                    item.f10525c = true;
                }
                View a = m14377a(item);
                try {
                    a.setId(Integer.parseInt(item.m14393a()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                a.setOnClickListener(new OnClickListener(this) {
                    /* renamed from: c */
                    final /* synthetic */ HomeCollectionPicker f10488c;

                    public void onClick(View view) {
                        this.f10488c.m14378a(view);
                        item.f10525c = !item.f10525c;
                        if (item.f10525c) {
                            this.f10488c.f10506e.put(item.f10523a, item);
                        } else {
                            this.f10488c.f10506e.remove(item.f10523a);
                        }
                        if (this.f10488c.f10507f != null) {
                            this.f10488c.f10507f.onClick(item, z);
                        }
                    }
                });
                TextView textView = (TextView) a.findViewById(R.id.item_name);
                textView.setText(item.f10524b);
                textView.setPadding(this.f10510i, this.f10512k, this.f10511j, this.f10513l);
                paddingLeft = (textView.getPaint().measureText(item.f10524b) + ((float) this.f10510i)) + ((float) this.f10511j);
                a.measure(0, 0);
                paddingLeft += (float) a.getMeasuredWidth();
                if (((float) this.f10508g) <= f) {
                    f = (float) (getPaddingLeft() + getPaddingRight());
                    m14380a(a, itemLayoutParams, true, z);
                    z3 = z;
                } else {
                    if (z != z2) {
                        itemLayoutParams.leftMargin = this.f10509h;
                        f += (float) this.f10509h;
                    }
                    m14380a(a, itemLayoutParams, false, z);
                    z3 = z2;
                }
                z++;
                f += paddingLeft;
                z2 = z3;
            }
            this.f10521t = true;
        }
    }

    /* renamed from: a */
    public void m14390a(List<Item> list) {
        if (this.f10520s) {
            float paddingLeft = (float) (getPaddingLeft() + getPaddingRight());
            LayoutParams itemLayoutParams = getItemLayoutParams();
            boolean z = false;
            boolean z2 = false;
            float f = paddingLeft;
            while (z < list.size()) {
                boolean z3;
                final Item item = (Item) list.get(z);
                if (this.f10506e != null && this.f10506e.containsKey(item.f10523a)) {
                    item.f10525c = true;
                }
                View a = m14377a(item);
                a.setSelected(item.f10525c);
                try {
                    a.setId(Integer.parseInt(item.m14393a()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                a.setOnClickListener(new OnClickListener(this) {
                    /* renamed from: c */
                    final /* synthetic */ HomeCollectionPicker f10499c;

                    public void onClick(View view) {
                        this.f10499c.m14378a(view);
                        if (item.f10525c) {
                            this.f10499c.f10506e.put(item.f10523a, item);
                        } else {
                            this.f10499c.f10506e.remove(item.f10523a);
                        }
                        if (this.f10499c.f10507f != null) {
                            this.f10499c.f10507f.onClick(item, z);
                        }
                    }
                });
                TextView textView = (TextView) a.findViewById(R.id.item_name);
                textView.setText(item.f10524b);
                textView.setPadding(this.f10510i, this.f10512k, this.f10511j, this.f10513l);
                paddingLeft = ((textView.getPaint().measureText(item.f10524b) + ((float) this.f10510i)) + ((float) this.f10511j)) + ((float) ((Utils.m14394a(getContext(), 45) + this.f10510i) + this.f10511j));
                this.f10521t = false;
                if (((float) this.f10508g) <= (f + paddingLeft) + ((float) Utils.m14394a(getContext(), 3))) {
                    f = (float) (getPaddingLeft() + getPaddingRight());
                    m14380a(a, itemLayoutParams, true, z);
                    z3 = z;
                } else {
                    if (z != z2) {
                        itemLayoutParams.leftMargin = this.f10509h;
                        f += (float) this.f10509h;
                    }
                    m14380a(a, itemLayoutParams, false, z);
                    z3 = z2;
                }
                z++;
                f += paddingLeft;
                z2 = z3;
            }
            this.f10521t = false;
            m14380a(null, itemLayoutParams, true, list.size());
        }
    }

    /* renamed from: b */
    public void m14391b() {
        removeAllViews();
        this.f10505d = null;
    }

    /* renamed from: c */
    public void m14392c() {
        this.f10504c.clear();
    }

    public HashMap<String, Object> getCheckedItems() {
        return this.f10506e;
    }

    public List<Item> getItems() {
        return this.f10504c;
    }

    public int getmTextColor() {
        return this.f10518q;
    }

    public int getmWidth() {
        return this.f10508g;
    }

    public void setCheckedItems(HashMap<String, Object> hashMap) {
        this.f10506e = hashMap;
    }

    public void setItems(List<Item> list) {
        this.f10504c = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.f10507f = onItemClickListener;
    }

    public void setResID(int i) {
        this.f10522u = i;
    }

    public void setmInitialized(boolean z) {
        this.f10520s = z;
    }

    public void setmTextColor(int i) {
        this.f10518q = i;
    }

    public void setmWidth(int i) {
        this.f10508g = i;
    }
}

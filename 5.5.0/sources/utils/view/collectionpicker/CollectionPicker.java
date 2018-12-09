package utils.view.collectionpicker;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build.VERSION;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.HashMap;
import java.util.List;
import org.ir.talaeii.R;

public class CollectionPicker extends LinearLayout {
    /* renamed from: a */
    private LayoutInflater f10467a;
    /* renamed from: b */
    private List<Item> f10468b;
    /* renamed from: c */
    private LinearLayout f10469c;
    /* renamed from: d */
    private HashMap<String, Object> f10470d;
    /* renamed from: e */
    private OnItemClickListener f10471e;
    /* renamed from: f */
    private int f10472f;
    /* renamed from: g */
    private int f10473g;
    /* renamed from: h */
    private int f10474h;
    /* renamed from: i */
    private int f10475i;
    /* renamed from: j */
    private int f10476j;
    /* renamed from: k */
    private int f10477k;
    /* renamed from: l */
    private int f10478l;
    /* renamed from: m */
    private int f10479m;
    /* renamed from: n */
    private int f10480n;
    /* renamed from: o */
    private int f10481o;
    /* renamed from: p */
    private int f10482p;
    /* renamed from: q */
    private int f10483q;
    /* renamed from: r */
    private boolean f10484r;

    /* renamed from: utils.view.collectionpicker.CollectionPicker$1 */
    class C53471 implements OnGlobalLayoutListener {
        /* renamed from: a */
        final /* synthetic */ CollectionPicker f10456a;

        public void onGlobalLayout() {
            if (!this.f10456a.f10484r) {
                this.f10456a.f10484r = true;
                this.f10456a.m14377a();
            }
        }
    }

    /* renamed from: utils.view.collectionpicker.CollectionPicker$3 */
    class C53493 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ Item f10461a;
        /* renamed from: b */
        final /* synthetic */ View f10462b;
        /* renamed from: c */
        final /* synthetic */ int f10463c;
        /* renamed from: d */
        final /* synthetic */ CollectionPicker f10464d;

        public void onClick(View view) {
            this.f10464d.m14364a(view);
            this.f10461a.f10525c = !this.f10461a.f10525c;
            if (this.f10461a.f10525c) {
                this.f10464d.f10470d.put(this.f10461a.f10523a, this.f10461a);
            } else {
                this.f10464d.f10470d.remove(this.f10461a.f10523a);
            }
            if (this.f10464d.m14374c()) {
                this.f10462b.setBackground(this.f10464d.m14370b(this.f10461a));
            } else {
                this.f10462b.setBackgroundDrawable(this.f10464d.m14370b(this.f10461a));
            }
            ((ImageView) this.f10462b.findViewById(R.id.item_icon)).setBackgroundResource(this.f10464d.m14360a(Boolean.valueOf(this.f10461a.f10525c)));
            if (this.f10464d.f10471e != null) {
                this.f10464d.f10471e.onClick(this.f10461a, this.f10463c);
            }
        }
    }

    /* renamed from: a */
    private int m14360a(Boolean bool) {
        return bool.booleanValue() ? this.f10479m : this.f10478l;
    }

    /* renamed from: a */
    private View m14363a(Item item) {
        View inflate = this.f10467a.inflate(R.layout.collection_picker_item_layout, this, false);
        if (m14374c()) {
            inflate.setBackground(m14370b(item));
        } else {
            inflate.setBackgroundDrawable(m14370b(item));
        }
        return inflate;
    }

    /* renamed from: a */
    private void m14364a(final View view) {
        view.setScaleY(1.0f);
        view.setScaleX(1.0f);
        view.animate().scaleX(1.2f).scaleY(1.2f).setDuration(100).setStartDelay(0).setInterpolator(new DecelerateInterpolator()).setListener(new AnimatorListener(this) {
            /* renamed from: b */
            final /* synthetic */ CollectionPicker f10466b;

            public void onAnimationCancel(Animator animator) {
            }

            public void onAnimationEnd(Animator animator) {
                this.f10466b.m14372b(view);
            }

            public void onAnimationRepeat(Animator animator) {
            }

            public void onAnimationStart(Animator animator) {
            }
        }).start();
    }

    /* renamed from: a */
    private void m14365a(View view, int i) {
        long j = 600 + ((long) (i * 30));
        view.setScaleY(BitmapDescriptorFactory.HUE_RED);
        view.setScaleX(BitmapDescriptorFactory.HUE_RED);
        view.animate().scaleY(1.0f).scaleX(1.0f).setDuration(200).setInterpolator(new DecelerateInterpolator()).setListener(null).setStartDelay(j).start();
    }

    /* renamed from: a */
    private void m14366a(View view, LayoutParams layoutParams, boolean z, int i) {
        if (this.f10469c == null || z) {
            this.f10469c = new LinearLayout(getContext());
            this.f10469c.setGravity(17);
            this.f10469c.setOrientation(0);
            this.f10469c.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
            addView(this.f10469c);
        }
        if (view != null) {
            this.f10469c.addView(view, layoutParams);
            m14365a(view, i);
        }
    }

    /* renamed from: b */
    private StateListDrawable m14370b(Item item) {
        return item.f10525c ? getSelectorSelected() : getSelectorNormal();
    }

    /* renamed from: b */
    private void m14372b(View view) {
        view.setScaleY(1.2f);
        view.setScaleX(1.2f);
        view.animate().scaleX(1.0f).scaleY(1.0f).setDuration(100).setListener(null).start();
    }

    /* renamed from: c */
    private boolean m14374c() {
        return VERSION.SDK_INT >= 16;
    }

    private LinearLayout.LayoutParams getItemLayoutParams() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.bottomMargin = this.f10473g / 2;
        layoutParams.topMargin = this.f10473g / 2;
        return layoutParams;
    }

    private StateListDrawable getSelectorNormal() {
        StateListDrawable stateListDrawable = new StateListDrawable();
        Drawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(this.f10481o);
        gradientDrawable.setCornerRadius((float) this.f10483q);
        stateListDrawable.addState(new int[]{16842919}, gradientDrawable);
        gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(this.f10480n);
        gradientDrawable.setCornerRadius((float) this.f10483q);
        stateListDrawable.addState(new int[0], gradientDrawable);
        return stateListDrawable;
    }

    private StateListDrawable getSelectorSelected() {
        StateListDrawable stateListDrawable = new StateListDrawable();
        Drawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(this.f10480n);
        gradientDrawable.setCornerRadius((float) this.f10483q);
        stateListDrawable.addState(new int[]{16842919}, gradientDrawable);
        gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(this.f10481o);
        gradientDrawable.setCornerRadius((float) this.f10483q);
        stateListDrawable.addState(new int[0], gradientDrawable);
        return stateListDrawable;
    }

    /* renamed from: a */
    public void m14377a() {
        if (this.f10484r) {
            m14378b();
            float paddingLeft = (float) (getPaddingLeft() + getPaddingRight());
            LayoutParams itemLayoutParams = getItemLayoutParams();
            boolean z = false;
            boolean z2 = false;
            float f = paddingLeft;
            while (z < this.f10468b.size()) {
                boolean z3;
                final Item item = (Item) this.f10468b.get(z);
                if (this.f10470d != null && this.f10470d.containsKey(item.f10523a)) {
                    item.f10525c = true;
                }
                final View a = m14363a(item);
                try {
                    a.setId(Integer.parseInt(item.m14395a()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                a.setOnClickListener(new OnClickListener(this) {
                    /* renamed from: d */
                    final /* synthetic */ CollectionPicker f10460d;

                    public void onClick(View view) {
                        this.f10460d.m14364a(view);
                        item.f10525c = !item.f10525c;
                        if (item.f10525c) {
                            this.f10460d.f10470d.put(item.f10523a, item);
                        } else {
                            this.f10460d.f10470d.remove(item.f10523a);
                        }
                        if (this.f10460d.m14374c()) {
                            a.setBackground(this.f10460d.m14370b(item));
                        } else {
                            a.setBackgroundDrawable(this.f10460d.m14370b(item));
                        }
                        ((ImageView) a.findViewById(R.id.item_icon)).setBackgroundResource(this.f10460d.m14360a(Boolean.valueOf(item.f10525c)));
                        if (this.f10460d.f10471e != null) {
                            this.f10460d.f10471e.onClick(item, z);
                        }
                    }
                });
                TextView textView = (TextView) a.findViewById(R.id.item_name);
                textView.setText(item.f10524b);
                textView.setPadding(this.f10474h, this.f10476j, this.f10475i, this.f10477k);
                textView.setTextColor(getResources().getColor(this.f10482p));
                float measureText = ((float) this.f10475i) + (textView.getPaint().measureText(item.f10524b) + ((float) this.f10474h));
                ImageView imageView = (ImageView) a.findViewById(R.id.item_icon);
                imageView.setBackgroundResource(m14360a(Boolean.valueOf(item.f10525c)));
                imageView.setPadding(0, this.f10476j, this.f10475i, this.f10477k);
                paddingLeft = ((float) ((Utils.m14396a(getContext(), 30) + this.f10474h) + this.f10475i)) + measureText;
                if (((float) this.f10472f) <= (f + paddingLeft) + ((float) Utils.m14396a(getContext(), 3))) {
                    f = (float) (getPaddingLeft() + getPaddingRight());
                    m14366a(a, itemLayoutParams, true, z);
                    z3 = z;
                } else {
                    if (z != z2) {
                        itemLayoutParams.leftMargin = this.f10473g;
                        f += (float) this.f10473g;
                    }
                    m14366a(a, itemLayoutParams, false, z);
                    z3 = z2;
                }
                z++;
                f += paddingLeft;
                z2 = z3;
            }
        }
    }

    /* renamed from: b */
    public void m14378b() {
        removeAllViews();
        this.f10469c = null;
    }

    public HashMap<String, Object> getCheckedItems() {
        return this.f10470d;
    }

    public List<Item> getItems() {
        return this.f10468b;
    }

    public int getmTextColor() {
        return this.f10482p;
    }

    public int getmWidth() {
        return this.f10472f;
    }

    public void setCheckedItems(HashMap<String, Object> hashMap) {
        this.f10470d = hashMap;
    }

    public void setItems(List<Item> list) {
        this.f10468b = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.f10471e = onItemClickListener;
    }

    public void setmTextColor(int i) {
        this.f10482p = i;
    }

    public void setmWidth(int i) {
        this.f10472f = i;
    }
}

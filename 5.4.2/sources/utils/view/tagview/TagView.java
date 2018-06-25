package utils.view.tagview;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build.VERSION;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.List;
import org.ir.talaeii.R;
import utils.C3792d;

public class TagView extends RelativeLayout {
    /* renamed from: a */
    private List<TagViewModel> f10539a;
    /* renamed from: b */
    private LayoutInflater f10540b;
    /* renamed from: c */
    private OnTagClickListener f10541c;
    /* renamed from: d */
    private OnTagDeleteListener f10542d;
    /* renamed from: e */
    private int f10543e;
    /* renamed from: f */
    private boolean f10544f;
    /* renamed from: g */
    private int f10545g;
    /* renamed from: h */
    private int f10546h;
    /* renamed from: i */
    private int f10547i;
    /* renamed from: j */
    private int f10548j;
    /* renamed from: k */
    private int f10549k;
    /* renamed from: l */
    private int f10550l;

    /* renamed from: utils.view.tagview.TagView$1 */
    class C53581 implements OnGlobalLayoutListener {
        /* renamed from: a */
        final /* synthetic */ TagView f10532a;

        public void onGlobalLayout() {
            if (!this.f10532a.f10544f) {
                this.f10532a.f10544f = true;
                this.f10532a.m14398a();
            }
        }
    }

    public interface OnTagClickListener {
        /* renamed from: a */
        void m14395a(TagViewModel tagViewModel, int i);
    }

    public interface OnTagDeleteListener {
        /* renamed from: a */
        void m14396a(TagView tagView, TagViewModel tagViewModel, int i);
    }

    /* renamed from: a */
    private Drawable m14397a(TagViewModel tagViewModel) {
        if (tagViewModel.f10563m != null) {
            return tagViewModel.f10563m;
        }
        Drawable stateListDrawable = new StateListDrawable();
        Drawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(tagViewModel.f10554d);
        gradientDrawable.setCornerRadius(tagViewModel.f10559i);
        if (tagViewModel.f10561k > BitmapDescriptorFactory.HUE_RED) {
            gradientDrawable.setStroke(C3792d.a(tagViewModel.f10561k, getContext()), tagViewModel.f10562l);
        }
        Drawable gradientDrawable2 = new GradientDrawable();
        gradientDrawable2.setColor(tagViewModel.f10555e);
        gradientDrawable2.setCornerRadius(tagViewModel.f10559i);
        stateListDrawable.addState(new int[]{16842919}, gradientDrawable2);
        stateListDrawable.addState(new int[0], gradientDrawable);
        return stateListDrawable;
    }

    /* renamed from: a */
    private void m14398a() {
        if (this.f10544f) {
            removeAllViews();
            float paddingLeft = (float) (getPaddingLeft() + getPaddingRight());
            int i = 1;
            int i2 = 1;
            float f = paddingLeft;
            int i3 = 1;
            TagViewModel tagViewModel = null;
            for (final TagViewModel tagViewModel2 : this.f10539a) {
                float measureText;
                int i4;
                int i5;
                final int i6 = i2 - 1;
                View inflate = this.f10540b.inflate(R.layout.tagview_item, null);
                inflate.setId(i2);
                if (VERSION.SDK_INT < 16) {
                    inflate.setBackgroundDrawable(m14397a(tagViewModel2));
                } else {
                    inflate.setBackground(m14397a(tagViewModel2));
                }
                TextView textView = (TextView) inflate.findViewById(R.id.tv_tag_item_contain);
                textView.setText(tagViewModel2.f10551a);
                LayoutParams layoutParams = (LayoutParams) textView.getLayoutParams();
                layoutParams.setMargins(this.f10547i, this.f10549k, this.f10548j, this.f10550l);
                textView.setLayoutParams(layoutParams);
                textView.setTextColor(tagViewModel2.f10552b);
                textView.setTextSize(2, tagViewModel2.f10553c);
                inflate.setOnClickListener(new OnClickListener(this) {
                    /* renamed from: c */
                    final /* synthetic */ TagView f10535c;

                    public void onClick(View view) {
                        if (this.f10535c.f10541c != null) {
                            this.f10535c.f10541c.m14395a(tagViewModel2, i6);
                        }
                    }
                });
                float measureText2 = ((float) this.f10548j) + (textView.getPaint().measureText(tagViewModel2.f10551a) + ((float) this.f10547i));
                textView = (TextView) inflate.findViewById(R.id.tv_tag_item_delete);
                if (tagViewModel2.f10556f) {
                    textView.setVisibility(0);
                    textView.setText(tagViewModel2.f10560j);
                    int a = C3792d.a(2.0f, getContext());
                    textView.setPadding(this.f10547i + a, this.f10549k, a, this.f10550l);
                    textView.setTextColor(tagViewModel2.f10557g);
                    textView.setTextSize(2, tagViewModel2.f10558h);
                    textView.setOnClickListener(new OnClickListener(this) {
                        /* renamed from: c */
                        final /* synthetic */ TagView f10538c;

                        public void onClick(View view) {
                            if (this.f10538c.f10542d != null) {
                                this.f10538c.f10542d.m14396a(this.f10538c, tagViewModel2, i6);
                            }
                        }
                    });
                    measureText = ((textView.getPaint().measureText(tagViewModel2.f10560j) + ((float) this.f10547i)) + ((float) this.f10548j)) + measureText2;
                } else {
                    textView.setVisibility(8);
                    measureText = measureText2;
                }
                ViewGroup.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
                layoutParams2.bottomMargin = this.f10545g;
                if (((float) this.f10543e) <= (f + measureText) + ((float) C3792d.a(2.0f, getContext()))) {
                    layoutParams2.addRule(3, i);
                    f = (float) (getPaddingLeft() + getPaddingRight());
                    i4 = i2;
                    i5 = i2;
                } else {
                    layoutParams2.addRule(6, i3);
                    if (i2 != i3) {
                        layoutParams2.addRule(0, i2 - 1);
                        layoutParams2.rightMargin = this.f10546h;
                        f += (float) this.f10546h;
                        if (tagViewModel.f10553c < tagViewModel2.f10553c) {
                            i4 = i3;
                            i5 = i2;
                        }
                    }
                    i4 = i3;
                    i5 = i;
                }
                paddingLeft = f + measureText;
                addView(inflate, layoutParams2);
                i = i5;
                i2++;
                f = paddingLeft;
                i3 = i4;
                tagViewModel = tagViewModel2;
            }
        }
    }

    public int getLineMargin() {
        return this.f10545g;
    }

    public int getTagMargin() {
        return this.f10546h;
    }

    public List<TagViewModel> getTags() {
        return this.f10539a;
    }

    public int getTextPaddingLeft() {
        return this.f10547i;
    }

    public int getTextPaddingRight() {
        return this.f10548j;
    }

    public int getTextPaddingTop() {
        return this.f10549k;
    }

    public int gettextPaddingBottom() {
        return this.f10550l;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        m14398a();
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (getMeasuredWidth() > 0) {
            this.f10543e = getMeasuredWidth();
        }
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.f10543e = i;
    }

    public void setLineMargin(float f) {
        this.f10545g = C3792d.a(f, getContext());
    }

    public void setOnTagClickListener(OnTagClickListener onTagClickListener) {
        this.f10541c = onTagClickListener;
    }

    public void setOnTagDeleteListener(OnTagDeleteListener onTagDeleteListener) {
        this.f10542d = onTagDeleteListener;
    }

    public void setTagMargin(float f) {
        this.f10546h = C3792d.a(f, getContext());
    }

    public void setTextPaddingLeft(float f) {
        this.f10547i = C3792d.a(f, getContext());
    }

    public void setTextPaddingRight(float f) {
        this.f10548j = C3792d.a(f, getContext());
    }

    public void setTextPaddingTop(float f) {
        this.f10549k = C3792d.a(f, getContext());
    }

    public void settextPaddingBottom(float f) {
        this.f10550l = C3792d.a(f, getContext());
    }
}

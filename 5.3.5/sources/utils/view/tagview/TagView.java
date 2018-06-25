package utils.view.tagview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import org.ir.talaeii.R;
import org.telegram.messenger.C0906R;
import utils.Utilities;

public class TagView extends RelativeLayout {
    private int lineMargin;
    private OnTagClickListener mClickListener;
    private OnTagDeleteListener mDeleteListener;
    private LayoutInflater mInflater;
    private boolean mInitialized = false;
    private List<TagViewModel> mTagViewModels = new ArrayList();
    private ViewTreeObserver mViewTreeObserber;
    private int mWidth;
    private int tagMargin;
    private int textPaddingBottom;
    private int textPaddingLeft;
    private int textPaddingRight;
    private int textPaddingTop;

    /* renamed from: utils.view.tagview.TagView$1 */
    class C35101 implements OnGlobalLayoutListener {
        C35101() {
        }

        public void onGlobalLayout() {
            if (!TagView.this.mInitialized) {
                TagView.this.mInitialized = true;
                TagView.this.drawTags();
            }
        }
    }

    public interface OnTagClickListener {
        void onTagClick(TagViewModel tagViewModel, int i);
    }

    public interface OnTagDeleteListener {
        void onTagDeleted(TagView tagView, TagViewModel tagViewModel, int i);
    }

    public TagView(Context ctx) {
        super(ctx, null);
        initialize(ctx, null, 0);
    }

    public TagView(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
        initialize(ctx, attrs, 0);
    }

    public TagView(Context ctx, AttributeSet attrs, int defStyle) {
        super(ctx, attrs, defStyle);
        initialize(ctx, attrs, defStyle);
    }

    private void initialize(Context ctx, AttributeSet attrs, int defStyle) {
        this.mInflater = (LayoutInflater) ctx.getSystemService("layout_inflater");
        this.mViewTreeObserber = getViewTreeObserver();
        this.mViewTreeObserber.addOnGlobalLayoutListener(new C35101());
        TypedArray typeArray = ctx.obtainStyledAttributes(attrs, C0906R.styleable.TagView, defStyle, defStyle);
        this.lineMargin = (int) typeArray.getDimension(0, (float) Utilities.convertDpToPixel(5.0f, getContext()));
        this.tagMargin = (int) typeArray.getDimension(1, (float) Utilities.convertDpToPixel(5.0f, getContext()));
        this.textPaddingLeft = (int) typeArray.getDimension(2, (float) Utilities.convertDpToPixel(8.0f, getContext()));
        this.textPaddingRight = (int) typeArray.getDimension(3, (float) Utilities.convertDpToPixel(8.0f, getContext()));
        this.textPaddingTop = (int) typeArray.getDimension(4, (float) Utilities.convertDpToPixel(5.0f, getContext()));
        this.textPaddingBottom = (int) typeArray.getDimension(5, (float) Utilities.convertDpToPixel(5.0f, getContext()));
        typeArray.recycle();
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth = w;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getMeasuredWidth() > 0) {
            this.mWidth = getMeasuredWidth();
        }
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawTags();
    }

    private void drawTags() {
        if (this.mInitialized) {
            removeAllViews();
            float total = (float) (getPaddingLeft() + getPaddingRight());
            int listIndex = 1;
            int indexBottom = 1;
            int indexHeader = 1;
            TagViewModel tagViewModelPre = null;
            for (final TagViewModel tagViewModel : this.mTagViewModels) {
                final int position = listIndex - 1;
                View tagLayout = this.mInflater.inflate(R.layout.tagview_item, null);
                tagLayout.setId(listIndex);
                if (VERSION.SDK_INT < 16) {
                    tagLayout.setBackgroundDrawable(getSelector(tagViewModel));
                } else {
                    tagLayout.setBackground(getSelector(tagViewModel));
                }
                TextView tagView = (TextView) tagLayout.findViewById(R.id.tv_tag_item_contain);
                tagView.setText(tagViewModel.text);
                LayoutParams params = (LayoutParams) tagView.getLayoutParams();
                params.setMargins(this.textPaddingLeft, this.textPaddingTop, this.textPaddingRight, this.textPaddingBottom);
                tagView.setLayoutParams(params);
                tagView.setTextColor(tagViewModel.tagTextColor);
                tagView.setTextSize(2, tagViewModel.tagTextSize);
                tagLayout.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        if (TagView.this.mClickListener != null) {
                            TagView.this.mClickListener.onTagClick(tagViewModel, position);
                        }
                    }
                });
                float tagWidth = (tagView.getPaint().measureText(tagViewModel.text) + ((float) this.textPaddingLeft)) + ((float) this.textPaddingRight);
                TextView deletableView = (TextView) tagLayout.findViewById(R.id.tv_tag_item_delete);
                if (tagViewModel.isDeletable) {
                    deletableView.setVisibility(0);
                    deletableView.setText(tagViewModel.deleteIcon);
                    int offset = Utilities.convertDpToPixel(2.0f, getContext());
                    deletableView.setPadding(this.textPaddingLeft + offset, this.textPaddingTop, offset, this.textPaddingBottom);
                    deletableView.setTextColor(tagViewModel.deleteIndicatorColor);
                    deletableView.setTextSize(2, tagViewModel.deleteIndicatorSize);
                    deletableView.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                            if (TagView.this.mDeleteListener != null) {
                                TagView.this.mDeleteListener.onTagDeleted(TagView.this, tagViewModel, position);
                            }
                        }
                    });
                    tagWidth += (deletableView.getPaint().measureText(tagViewModel.deleteIcon) + ((float) this.textPaddingLeft)) + ((float) this.textPaddingRight);
                } else {
                    deletableView.setVisibility(8);
                }
                RelativeLayout.LayoutParams tagParams = new RelativeLayout.LayoutParams(-2, -2);
                tagParams.bottomMargin = this.lineMargin;
                if (((float) this.mWidth) <= (total + tagWidth) + ((float) Utilities.convertDpToPixel(2.0f, getContext()))) {
                    tagParams.addRule(3, indexBottom);
                    total = (float) (getPaddingLeft() + getPaddingRight());
                    indexBottom = listIndex;
                    indexHeader = listIndex;
                } else {
                    tagParams.addRule(6, indexHeader);
                    if (listIndex != indexHeader) {
                        tagParams.addRule(0, listIndex - 1);
                        tagParams.rightMargin = this.tagMargin;
                        total += (float) this.tagMargin;
                        if (tagViewModelPre.tagTextSize < tagViewModel.tagTextSize) {
                            indexBottom = listIndex;
                        }
                    }
                }
                total += tagWidth;
                addView(tagLayout, tagParams);
                tagViewModelPre = tagViewModel;
                listIndex++;
            }
        }
    }

    private Drawable getSelector(TagViewModel tagViewModel) {
        if (tagViewModel.background != null) {
            return tagViewModel.background;
        }
        Drawable states = new StateListDrawable();
        GradientDrawable gdNormal = new GradientDrawable();
        gdNormal.setColor(tagViewModel.layoutColor);
        gdNormal.setCornerRadius(tagViewModel.radius);
        if (tagViewModel.layoutBorderSize > 0.0f) {
            gdNormal.setStroke(Utilities.convertDpToPixel(tagViewModel.layoutBorderSize, getContext()), tagViewModel.layoutBorderColor);
        }
        GradientDrawable gdPress = new GradientDrawable();
        gdPress.setColor(tagViewModel.layoutColorPress);
        gdPress.setCornerRadius(tagViewModel.radius);
        states.addState(new int[]{16842919}, gdPress);
        states.addState(new int[0], gdNormal);
        return states;
    }

    public void addTag(TagViewModel tagViewModel) {
        this.mTagViewModels.add(tagViewModel);
        drawTags();
    }

    public void addTags(List<TagViewModel> tagViewModels) {
        if (tagViewModels != null) {
            this.mTagViewModels = new ArrayList();
            if (tagViewModels.isEmpty()) {
                drawTags();
            }
            for (TagViewModel item : tagViewModels) {
                addTag(item);
            }
        }
    }

    public void addTags(String[] tags) {
        if (tags != null) {
            for (String item : tags) {
                addTag(new TagViewModel(item));
            }
        }
    }

    public List<TagViewModel> getTags() {
        return this.mTagViewModels;
    }

    public void remove(int position) {
        if (position < this.mTagViewModels.size()) {
            this.mTagViewModels.remove(position);
            drawTags();
        }
    }

    public void removeAll() {
        this.mTagViewModels.clear();
        removeAllViews();
    }

    public int getLineMargin() {
        return this.lineMargin;
    }

    public void setLineMargin(float lineMargin) {
        this.lineMargin = Utilities.convertDpToPixel(lineMargin, getContext());
    }

    public int getTagMargin() {
        return this.tagMargin;
    }

    public void setTagMargin(float tagMargin) {
        this.tagMargin = Utilities.convertDpToPixel(tagMargin, getContext());
    }

    public int getTextPaddingLeft() {
        return this.textPaddingLeft;
    }

    public void setTextPaddingLeft(float textPaddingLeft) {
        this.textPaddingLeft = Utilities.convertDpToPixel(textPaddingLeft, getContext());
    }

    public int getTextPaddingRight() {
        return this.textPaddingRight;
    }

    public void setTextPaddingRight(float textPaddingRight) {
        this.textPaddingRight = Utilities.convertDpToPixel(textPaddingRight, getContext());
    }

    public int getTextPaddingTop() {
        return this.textPaddingTop;
    }

    public void setTextPaddingTop(float textPaddingTop) {
        this.textPaddingTop = Utilities.convertDpToPixel(textPaddingTop, getContext());
    }

    public int gettextPaddingBottom() {
        return this.textPaddingBottom;
    }

    public void settextPaddingBottom(float textPaddingBottom) {
        this.textPaddingBottom = Utilities.convertDpToPixel(textPaddingBottom, getContext());
    }

    public void setOnTagClickListener(OnTagClickListener clickListener) {
        this.mClickListener = clickListener;
    }

    public void setOnTagDeleteListener(OnTagDeleteListener deleteListener) {
        this.mDeleteListener = deleteListener;
    }
}

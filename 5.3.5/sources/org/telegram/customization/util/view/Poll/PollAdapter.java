package org.telegram.customization.util.view.Poll;

import android.animation.Animator.AnimatorListener;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.LayerDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import org.ir.talaeii.R;
import org.telegram.customization.util.AppImageLoader;

public class PollAdapter extends BaseAdapter implements OnItemClickListener, OnItemSelectedListener {
    public static final long ProgressAnimDuration = 500;
    public static final long ScaleAnimDuration = 200;
    volatile ArrayList<Boolean> animationFlags = new ArrayList();
    int backgroundColor;
    private Context context;
    volatile List<PollItem> items = new ArrayList();
    int selectedColor;
    int selectedPos;
    int totalCount = 0;
    int unselectedColor;

    /* renamed from: org.telegram.customization.util.view.Poll.PollAdapter$3 */
    class C12523 implements AnimationListener {
        C12523() {
        }

        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationEnd(Animation animation) {
            PollAdapter.this.notifyDataSetChanged();
            PollAdapter.this.animationFlags = new ArrayList();
            for (int i = 0; i < PollAdapter.this.getCount(); i++) {
                PollAdapter.this.animationFlags.add(Boolean.valueOf(false));
            }
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }

    class ProgressAnimation extends Animation {
        private ProgressBar mContent;
        private final float mDeltaProgress;
        private final float mStartProgress;

        public ProgressAnimation(ProgressBar mContent, float startProgress, float endProgress) {
            this.mStartProgress = startProgress;
            this.mDeltaProgress = endProgress - startProgress;
            this.mContent = mContent;
        }

        protected void applyTransformation(float interpolatedTime, Transformation t) {
            this.mContent.setProgress((int) (this.mStartProgress + (this.mDeltaProgress * interpolatedTime)));
        }

        public boolean willChangeBounds() {
            return false;
        }
    }

    class ResizeAnimation extends Animation {
        private View mContent;
        private final float mDeltaWeight;
        private final float mStartWeight;

        public ResizeAnimation(View mContent, float startWeight, float endWeight) {
            this.mStartWeight = startWeight;
            this.mDeltaWeight = endWeight - startWeight;
            this.mContent = mContent;
        }

        protected void applyTransformation(float interpolatedTime, Transformation t) {
            LayoutParams lp = (LayoutParams) this.mContent.getLayoutParams();
            lp.weight = this.mStartWeight + (this.mDeltaWeight * interpolatedTime);
            this.mContent.setLayoutParams(lp);
        }

        public boolean willChangeBounds() {
            return true;
        }
    }

    void fixPercents() {
        this.totalCount = 0;
        for (PollItem item : this.items) {
            this.totalCount += item.getCount();
        }
        if (this.totalCount <= 0) {
            for (PollItem item2 : this.items) {
                item2.setPercent(0);
            }
            return;
        }
        int sum = 100;
        for (int i = 0; i + 1 < this.items.size(); i++) {
            ((PollItem) this.items.get(i)).setPercent((((PollItem) this.items.get(i)).getCount() * 100) / this.totalCount);
            sum -= ((PollItem) this.items.get(i)).getPercent();
        }
        ((PollItem) this.items.get(this.items.size() - 1)).setPercent(sum);
    }

    public int getBackgroundColor() {
        return this.backgroundColor;
    }

    public int getUnselectedColor() {
        return this.unselectedColor;
    }

    public int getSelectedColor() {
        return this.selectedColor;
    }

    public PollAdapter(Context context, List<PollItem> items, int selectedColor, int unselectedColor, int backgroundColor) {
        this.context = context;
        this.items = items;
        this.selectedColor = selectedColor;
        this.unselectedColor = unselectedColor;
        this.backgroundColor = backgroundColor;
        this.selectedPos = -1;
    }

    public List<PollItem> getItems() {
        return this.items;
    }

    public void setItems(List<PollItem> items) {
        this.items = items;
    }

    public int getSelectedPos() {
        return this.selectedPos;
    }

    public void setSelectedPos(int selectedPos) {
        this.selectedPos = selectedPos;
    }

    public synchronized int getCount() {
        int size;
        if (getItems() != null) {
            size = getItems().size();
        } else {
            size = 0;
        }
        return size;
    }

    public Object getItem(int position) {
        return Integer.valueOf(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(this.context, R.layout.poll_item, null);
        }
        try {
            final PollItem item = (PollItem) getItems().get(position);
            ImageView image = (ImageView) convertView.findViewById(R.id.image);
            RelativeLayout rl = (RelativeLayout) convertView.findViewById(R.id.rl);
            final ProgressBar progress = (ProgressBar) convertView.findViewById(R.id.progress);
            View isSelected = convertView.findViewById(R.id.isSelected);
            TextView percentage = (TextView) convertView.findViewById(R.id.percentage);
            ((TextView) convertView.findViewById(R.id.title)).setText(item.getTitle());
            percentage.setText(String.format(this.context.getResources().getString(R.string.percentageText), new Object[]{Integer.valueOf(item.getPercent())}));
            AppImageLoader.loadImage(image, item.getImageUrl());
            try {
                LayerDrawable layers = (LayerDrawable) this.context.getResources().getDrawable(R.drawable.poll_progress_drawable);
                layers.setDrawableByLayerId(16908288, new ColorDrawable(getBackgroundColor()));
                if (this.selectedPos == position) {
                    layers.setDrawableByLayerId(16908301, new ClipDrawable(new ColorDrawable(getSelectedColor()), 3, 1));
                } else {
                    isSelected.setVisibility(8);
                    layers.setDrawableByLayerId(16908301, new ClipDrawable(new ColorDrawable(getUnselectedColor()), 3, 1));
                }
                progress.setProgressDrawable(layers);
                if (this.selectedPos < 0) {
                    progress.setProgress(100);
                    percentage.setVisibility(8);
                } else {
                    percentage.setVisibility(0);
                    try {
                        if (!((Boolean) this.animationFlags.get(position)).booleanValue()) {
                            final int i = position;
                            progress.post(new Runnable() {
                                public void run() {
                                    PollAdapter.this.animationFlags.set(i, Boolean.valueOf(true));
                                    ProgressAnimation progressAnimation = new ProgressAnimation(progress, 0.0f, (float) item.getPercent());
                                    progressAnimation.setDuration(500);
                                    progressAnimation.setInterpolator(new LinearInterpolator());
                                    progress.startAnimation(progressAnimation);
                                }
                            });
                        } else if (progress.getProgress() != item.getPercent()) {
                            final int startProgress = progress.getProgress();
                            final int i2 = position;
                            progress.post(new Runnable() {
                                public void run() {
                                    PollAdapter.this.animationFlags.set(i2, Boolean.valueOf(true));
                                    ProgressAnimation progressAnimation = new ProgressAnimation(progress, (float) startProgress, (float) item.getPercent());
                                    progressAnimation.setDuration(500);
                                    progressAnimation.setInterpolator(new LinearInterpolator());
                                    progress.startAnimation(progressAnimation);
                                }
                            });
                        } else {
                            progress.setProgress(item.getPercent());
                        }
                    } catch (Exception e) {
                        progress.setProgress(item.getPercent());
                        e.printStackTrace();
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } catch (Exception e22) {
            e22.printStackTrace();
        }
        return convertView;
    }

    public synchronized void onItemClick(AdapterView<?> adapterView, View view, int position, long arg3) {
        onItemSelected(adapterView, view, position, arg3);
    }

    public synchronized void onItemSelected(AdapterView<?> adapterView, View view, int position, long arg3) {
        onItemSelected(position, view);
    }

    public synchronized void onItemSelected(int position, View view) {
        try {
            PollItem item = (PollItem) getItems().get(position);
            if (this.selectedPos < 0) {
                this.selectedPos = position;
                ((PollItem) this.items.get(position)).increaseCount(1);
                Animation scaleAnim = new ScaleAnimation(1.0f, 1.0f, 1.0f, 1.0f, 1, 0.5f, 1, 0.5f);
                scaleAnim.setInterpolator(new LinearInterpolator());
                scaleAnim.setRepeatMode(2);
                scaleAnim.setRepeatCount(1);
                scaleAnim.setDuration(200);
                scaleAnim.setAnimationListener(new C12523());
                view.findViewById(R.id.rl).startAnimation(scaleAnim);
            } else if (this.selectedPos == position) {
                ((PollItem) this.items.get(position)).increaseCount(-1);
                this.selectedPos = -1;
                notifyDataSetChanged();
            } else {
                ((PollItem) this.items.get(this.selectedPos)).increaseCount(-1);
                ((PollItem) this.items.get(position)).increaseCount(1);
                this.selectedPos = position;
                notifyDataSetChanged();
            }
        } catch (Exception e) {
        }
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    @TargetApi(11)
    void animateColor(View isSel, final ProgressBar progress, final LayerDrawable layers, long duration, Integer colorFrom, Integer colorTo, AnimatorListener listener) {
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), new Object[]{colorFrom, colorTo});
        colorAnimation.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animator) {
                layers.setDrawableByLayerId(16908301, new ClipDrawable(new ColorDrawable(((Integer) animator.getAnimatedValue()).intValue()), 3, 1));
                progress.setProgressDrawable(layers);
                progress.invalidate();
            }
        });
        colorAnimation.setDuration(duration);
        if (listener != null) {
            colorAnimation.addListener(listener);
        }
        colorAnimation.start();
    }
}

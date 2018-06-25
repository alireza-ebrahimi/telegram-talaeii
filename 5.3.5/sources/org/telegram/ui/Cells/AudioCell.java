package org.telegram.ui.Cells;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.MediaController$AudioEntry;
import org.telegram.messenger.MessageObject;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.CheckBox;
import org.telegram.ui.Components.CombinedDrawable;
import org.telegram.ui.Components.LayoutHelper;

public class AudioCell extends FrameLayout {
    private MediaController$AudioEntry audioEntry;
    private TextView authorTextView;
    private CheckBox checkBox;
    private AudioCellDelegate delegate;
    private TextView genreTextView;
    private boolean needDivider;
    private ImageView playButton;
    private TextView timeTextView;
    private TextView titleTextView;

    public interface AudioCellDelegate {
        void startedPlayingAudio(MessageObject messageObject);
    }

    /* renamed from: org.telegram.ui.Cells.AudioCell$1 */
    class C21581 implements OnClickListener {
        C21581() {
        }

        public void onClick(View v) {
            if (AudioCell.this.audioEntry == null) {
                return;
            }
            if (!MediaController.getInstance().isPlayingMessage(AudioCell.this.audioEntry.messageObject) || MediaController.getInstance().isMessagePaused()) {
                ArrayList<MessageObject> arrayList = new ArrayList();
                arrayList.add(AudioCell.this.audioEntry.messageObject);
                if (MediaController.getInstance().setPlaylist(arrayList, AudioCell.this.audioEntry.messageObject)) {
                    AudioCell.this.setPlayDrawable(true);
                    if (AudioCell.this.delegate != null) {
                        AudioCell.this.delegate.startedPlayingAudio(AudioCell.this.audioEntry.messageObject);
                        return;
                    }
                    return;
                }
                return;
            }
            MediaController.getInstance().pauseMessage(AudioCell.this.audioEntry.messageObject);
            AudioCell.this.setPlayDrawable(false);
        }
    }

    public AudioCell(Context context) {
        float f;
        float f2;
        int i;
        int i2 = 3;
        super(context);
        this.playButton = new ImageView(context);
        View view = this.playButton;
        int i3 = (LocaleController.isRTL ? 5 : 3) | 48;
        if (LocaleController.isRTL) {
            f = 0.0f;
        } else {
            f = 13.0f;
        }
        if (LocaleController.isRTL) {
            f2 = 13.0f;
        } else {
            f2 = 0.0f;
        }
        addView(view, LayoutHelper.createFrame(46, 46.0f, i3, f, 13.0f, f2, 0.0f));
        this.playButton.setOnClickListener(new C21581());
        this.titleTextView = new TextView(context);
        this.titleTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.titleTextView.setTextSize(1, 16.0f);
        this.titleTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.titleTextView.setLines(1);
        this.titleTextView.setMaxLines(1);
        this.titleTextView.setSingleLine(true);
        this.titleTextView.setEllipsize(TruncateAt.END);
        this.titleTextView.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
        view = this.titleTextView;
        if (LocaleController.isRTL) {
            i3 = 5;
        } else {
            i3 = 3;
        }
        addView(view, LayoutHelper.createFrame(-1, -2.0f, i3 | 48, LocaleController.isRTL ? 50.0f : 72.0f, 7.0f, LocaleController.isRTL ? 72.0f : 50.0f, 0.0f));
        this.genreTextView = new TextView(context);
        this.genreTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText2));
        this.genreTextView.setTextSize(1, 14.0f);
        this.genreTextView.setLines(1);
        this.genreTextView.setMaxLines(1);
        this.genreTextView.setSingleLine(true);
        this.genreTextView.setEllipsize(TruncateAt.END);
        TextView textView = this.genreTextView;
        if (LocaleController.isRTL) {
            i = 5;
        } else {
            i = 3;
        }
        textView.setGravity(i | 48);
        view = this.genreTextView;
        if (LocaleController.isRTL) {
            i3 = 5;
        } else {
            i3 = 3;
        }
        addView(view, LayoutHelper.createFrame(-1, -2.0f, i3 | 48, LocaleController.isRTL ? 50.0f : 72.0f, 28.0f, LocaleController.isRTL ? 72.0f : 50.0f, 0.0f));
        this.authorTextView = new TextView(context);
        this.authorTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText2));
        this.authorTextView.setTextSize(1, 14.0f);
        this.authorTextView.setLines(1);
        this.authorTextView.setMaxLines(1);
        this.authorTextView.setSingleLine(true);
        this.authorTextView.setEllipsize(TruncateAt.END);
        textView = this.authorTextView;
        if (LocaleController.isRTL) {
            i = 5;
        } else {
            i = 3;
        }
        textView.setGravity(i | 48);
        view = this.authorTextView;
        if (LocaleController.isRTL) {
            i3 = 5;
        } else {
            i3 = 3;
        }
        addView(view, LayoutHelper.createFrame(-1, -2.0f, i3 | 48, LocaleController.isRTL ? 50.0f : 72.0f, 44.0f, LocaleController.isRTL ? 72.0f : 50.0f, 0.0f));
        this.timeTextView = new TextView(context);
        this.timeTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText3));
        this.timeTextView.setTextSize(1, 13.0f);
        this.timeTextView.setLines(1);
        this.timeTextView.setMaxLines(1);
        this.timeTextView.setSingleLine(true);
        this.timeTextView.setEllipsize(TruncateAt.END);
        textView = this.timeTextView;
        if (LocaleController.isRTL) {
            i = 3;
        } else {
            i = 5;
        }
        textView.setGravity(i | 48);
        View view2 = this.timeTextView;
        if (LocaleController.isRTL) {
            i3 = 3;
        } else {
            i3 = 5;
        }
        addView(view2, LayoutHelper.createFrame(-2, -2.0f, i3 | 48, LocaleController.isRTL ? 18.0f : 0.0f, 11.0f, LocaleController.isRTL ? 0.0f : 18.0f, 0.0f));
        this.checkBox = new CheckBox(context, R.drawable.round_check2);
        this.checkBox.setVisibility(0);
        this.checkBox.setColor(Theme.getColor(Theme.key_musicPicker_checkbox), Theme.getColor(Theme.key_musicPicker_checkboxCheck));
        view2 = this.checkBox;
        if (!LocaleController.isRTL) {
            i2 = 5;
        }
        addView(view2, LayoutHelper.createFrame(22, 22.0f, i2 | 48, LocaleController.isRTL ? 18.0f : 0.0f, 39.0f, LocaleController.isRTL ? 0.0f : 18.0f, 0.0f));
    }

    private void setPlayDrawable(boolean play) {
        Drawable circle = Theme.createSimpleSelectorCircleDrawable(AndroidUtilities.dp(46.0f), Theme.getColor(Theme.key_musicPicker_buttonBackground), Theme.getColor(Theme.key_musicPicker_buttonBackground));
        Drawable drawable = getResources().getDrawable(play ? R.drawable.audiosend_pause : R.drawable.audiosend_play);
        drawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_musicPicker_buttonIcon), Mode.MULTIPLY));
        CombinedDrawable combinedDrawable = new CombinedDrawable(circle, drawable);
        combinedDrawable.setCustomSize(AndroidUtilities.dp(46.0f), AndroidUtilities.dp(46.0f));
        this.playButton.setBackgroundDrawable(combinedDrawable);
    }

    public ImageView getPlayButton() {
        return this.playButton;
    }

    public TextView getTitleTextView() {
        return this.titleTextView;
    }

    public TextView getGenreTextView() {
        return this.genreTextView;
    }

    public TextView getTimeTextView() {
        return this.timeTextView;
    }

    public TextView getAuthorTextView() {
        return this.authorTextView;
    }

    public CheckBox getCheckBox() {
        return this.checkBox;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), 1073741824), MeasureSpec.makeMeasureSpec((this.needDivider ? 1 : 0) + AndroidUtilities.dp(72.0f), 1073741824));
    }

    public void setAudio(MediaController$AudioEntry entry, boolean divider, boolean checked) {
        boolean z = true;
        this.audioEntry = entry;
        this.titleTextView.setText(this.audioEntry.title);
        this.genreTextView.setText(this.audioEntry.genre);
        this.authorTextView.setText(this.audioEntry.author);
        this.timeTextView.setText(String.format("%d:%02d", new Object[]{Integer.valueOf(this.audioEntry.duration / 60), Integer.valueOf(this.audioEntry.duration % 60)}));
        boolean z2 = MediaController.getInstance().isPlayingMessage(this.audioEntry.messageObject) && !MediaController.getInstance().isMessagePaused();
        setPlayDrawable(z2);
        this.needDivider = divider;
        if (divider) {
            z = false;
        }
        setWillNotDraw(z);
        this.checkBox.setChecked(checked, false);
    }

    public void setChecked(boolean value) {
        this.checkBox.setChecked(value, true);
    }

    public void setDelegate(AudioCellDelegate audioCellDelegate) {
        this.delegate = audioCellDelegate;
    }

    public MediaController$AudioEntry getAudioEntry() {
        return this.audioEntry;
    }

    protected void onDraw(Canvas canvas) {
        if (this.needDivider) {
            canvas.drawLine((float) AndroidUtilities.dp(72.0f), (float) (getHeight() - 1), (float) getWidth(), (float) (getHeight() - 1), Theme.dividerPaint);
        }
    }
}

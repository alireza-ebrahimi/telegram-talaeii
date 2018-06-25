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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.MediaController.AudioEntry;
import org.telegram.messenger.MessageObject;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.CheckBox;
import org.telegram.ui.Components.CombinedDrawable;
import org.telegram.ui.Components.LayoutHelper;

public class AudioCell extends FrameLayout {
    private AudioEntry audioEntry;
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
    class C39961 implements OnClickListener {
        C39961() {
        }

        public void onClick(View view) {
            if (AudioCell.this.audioEntry == null) {
                return;
            }
            if (!MediaController.getInstance().isPlayingMessage(AudioCell.this.audioEntry.messageObject) || MediaController.getInstance().isMessagePaused()) {
                ArrayList arrayList = new ArrayList();
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
        int i = 3;
        super(context);
        this.playButton = new ImageView(context);
        addView(this.playButton, LayoutHelper.createFrame(46, 46.0f, (LocaleController.isRTL ? 5 : 3) | 48, LocaleController.isRTL ? BitmapDescriptorFactory.HUE_RED : 13.0f, 13.0f, LocaleController.isRTL ? 13.0f : BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.playButton.setOnClickListener(new C39961());
        this.titleTextView = new TextView(context);
        this.titleTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.titleTextView.setTextSize(1, 16.0f);
        this.titleTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.titleTextView.setLines(1);
        this.titleTextView.setMaxLines(1);
        this.titleTextView.setSingleLine(true);
        this.titleTextView.setEllipsize(TruncateAt.END);
        this.titleTextView.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
        addView(this.titleTextView, LayoutHelper.createFrame(-1, -2.0f, (LocaleController.isRTL ? 5 : 3) | 48, LocaleController.isRTL ? 50.0f : 72.0f, 7.0f, LocaleController.isRTL ? 72.0f : 50.0f, BitmapDescriptorFactory.HUE_RED));
        this.genreTextView = new TextView(context);
        this.genreTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText2));
        this.genreTextView.setTextSize(1, 14.0f);
        this.genreTextView.setLines(1);
        this.genreTextView.setMaxLines(1);
        this.genreTextView.setSingleLine(true);
        this.genreTextView.setEllipsize(TruncateAt.END);
        this.genreTextView.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
        addView(this.genreTextView, LayoutHelper.createFrame(-1, -2.0f, (LocaleController.isRTL ? 5 : 3) | 48, LocaleController.isRTL ? 50.0f : 72.0f, 28.0f, LocaleController.isRTL ? 72.0f : 50.0f, BitmapDescriptorFactory.HUE_RED));
        this.authorTextView = new TextView(context);
        this.authorTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText2));
        this.authorTextView.setTextSize(1, 14.0f);
        this.authorTextView.setLines(1);
        this.authorTextView.setMaxLines(1);
        this.authorTextView.setSingleLine(true);
        this.authorTextView.setEllipsize(TruncateAt.END);
        this.authorTextView.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
        addView(this.authorTextView, LayoutHelper.createFrame(-1, -2.0f, (LocaleController.isRTL ? 5 : 3) | 48, LocaleController.isRTL ? 50.0f : 72.0f, 44.0f, LocaleController.isRTL ? 72.0f : 50.0f, BitmapDescriptorFactory.HUE_RED));
        this.timeTextView = new TextView(context);
        this.timeTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText3));
        this.timeTextView.setTextSize(1, 13.0f);
        this.timeTextView.setLines(1);
        this.timeTextView.setMaxLines(1);
        this.timeTextView.setSingleLine(true);
        this.timeTextView.setEllipsize(TruncateAt.END);
        this.timeTextView.setGravity((LocaleController.isRTL ? 3 : 5) | 48);
        addView(this.timeTextView, LayoutHelper.createFrame(-2, -2.0f, (LocaleController.isRTL ? 3 : 5) | 48, LocaleController.isRTL ? 18.0f : BitmapDescriptorFactory.HUE_RED, 11.0f, LocaleController.isRTL ? BitmapDescriptorFactory.HUE_RED : 18.0f, BitmapDescriptorFactory.HUE_RED));
        this.checkBox = new CheckBox(context, R.drawable.round_check2);
        this.checkBox.setVisibility(0);
        this.checkBox.setColor(Theme.getColor(Theme.key_musicPicker_checkbox), Theme.getColor(Theme.key_musicPicker_checkboxCheck));
        View view = this.checkBox;
        if (!LocaleController.isRTL) {
            i = 5;
        }
        addView(view, LayoutHelper.createFrame(22, 22.0f, i | 48, LocaleController.isRTL ? 18.0f : BitmapDescriptorFactory.HUE_RED, 39.0f, LocaleController.isRTL ? BitmapDescriptorFactory.HUE_RED : 18.0f, BitmapDescriptorFactory.HUE_RED));
    }

    private void setPlayDrawable(boolean z) {
        Drawable createSimpleSelectorCircleDrawable = Theme.createSimpleSelectorCircleDrawable(AndroidUtilities.dp(46.0f), Theme.getColor(Theme.key_musicPicker_buttonBackground), Theme.getColor(Theme.key_musicPicker_buttonBackground));
        Drawable drawable = getResources().getDrawable(z ? R.drawable.audiosend_pause : R.drawable.audiosend_play);
        drawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_musicPicker_buttonIcon), Mode.MULTIPLY));
        Drawable combinedDrawable = new CombinedDrawable(createSimpleSelectorCircleDrawable, drawable);
        combinedDrawable.setCustomSize(AndroidUtilities.dp(46.0f), AndroidUtilities.dp(46.0f));
        this.playButton.setBackgroundDrawable(combinedDrawable);
    }

    public AudioEntry getAudioEntry() {
        return this.audioEntry;
    }

    public TextView getAuthorTextView() {
        return this.authorTextView;
    }

    public CheckBox getCheckBox() {
        return this.checkBox;
    }

    public TextView getGenreTextView() {
        return this.genreTextView;
    }

    public ImageView getPlayButton() {
        return this.playButton;
    }

    public TextView getTimeTextView() {
        return this.timeTextView;
    }

    public TextView getTitleTextView() {
        return this.titleTextView;
    }

    protected void onDraw(Canvas canvas) {
        if (this.needDivider) {
            canvas.drawLine((float) AndroidUtilities.dp(72.0f), (float) (getHeight() - 1), (float) getWidth(), (float) (getHeight() - 1), Theme.dividerPaint);
        }
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(i), 1073741824), MeasureSpec.makeMeasureSpec((this.needDivider ? 1 : 0) + AndroidUtilities.dp(72.0f), 1073741824));
    }

    public void setAudio(AudioEntry audioEntry, boolean z, boolean z2) {
        boolean z3 = true;
        this.audioEntry = audioEntry;
        this.titleTextView.setText(this.audioEntry.title);
        this.genreTextView.setText(this.audioEntry.genre);
        this.authorTextView.setText(this.audioEntry.author);
        this.timeTextView.setText(String.format("%d:%02d", new Object[]{Integer.valueOf(this.audioEntry.duration / 60), Integer.valueOf(this.audioEntry.duration % 60)}));
        boolean z4 = MediaController.getInstance().isPlayingMessage(this.audioEntry.messageObject) && !MediaController.getInstance().isMessagePaused();
        setPlayDrawable(z4);
        this.needDivider = z;
        if (z) {
            z3 = false;
        }
        setWillNotDraw(z3);
        this.checkBox.setChecked(z2, false);
    }

    public void setChecked(boolean z) {
        this.checkBox.setChecked(z, true);
    }

    public void setDelegate(AudioCellDelegate audioCellDelegate) {
        this.delegate = audioCellDelegate;
    }
}

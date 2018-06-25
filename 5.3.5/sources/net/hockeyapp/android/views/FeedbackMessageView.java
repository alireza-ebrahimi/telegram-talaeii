package net.hockeyapp.android.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import net.hockeyapp.android.C0962R;
import net.hockeyapp.android.objects.FeedbackAttachment;
import net.hockeyapp.android.objects.FeedbackMessage;
import net.hockeyapp.android.tasks.AttachmentDownloader;

public class FeedbackMessageView extends LinearLayout {
    @SuppressLint({"SimpleDateFormat"})
    private static final SimpleDateFormat DATE_FORMAT_IN = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    @SuppressLint({"SimpleDateFormat"})
    private static final SimpleDateFormat DATE_FORMAT_OUT = new SimpleDateFormat("d MMM h:mm a");
    private AttachmentListView mAttachmentListView = ((AttachmentListView) findViewById(C0962R.id.list_attachments));
    private TextView mAuthorTextView = ((TextView) findViewById(C0962R.id.label_author));
    private final Context mContext;
    private TextView mDateTextView = ((TextView) findViewById(C0962R.id.label_date));
    private FeedbackMessage mFeedbackMessage;
    private TextView mMessageTextView = ((TextView) findViewById(C0962R.id.label_text));
    @Deprecated
    private boolean ownMessage;

    public FeedbackMessageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        LayoutInflater.from(context).inflate(C0962R.layout.hockeyapp_view_feedback_message, this);
    }

    public void setFeedbackMessage(FeedbackMessage feedbackMessage) {
        this.mFeedbackMessage = feedbackMessage;
        try {
            this.mDateTextView.setText(DATE_FORMAT_OUT.format(DATE_FORMAT_IN.parse(this.mFeedbackMessage.getCreatedAt())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.mAuthorTextView.setText(this.mFeedbackMessage.getName());
        this.mMessageTextView.setText(this.mFeedbackMessage.getText());
        this.mAttachmentListView.removeAllViews();
        for (FeedbackAttachment feedbackAttachment : this.mFeedbackMessage.getFeedbackAttachments()) {
            AttachmentView attachmentView = new AttachmentView(this.mContext, this.mAttachmentListView, feedbackAttachment, false);
            AttachmentDownloader.getInstance().download(feedbackAttachment, attachmentView);
            this.mAttachmentListView.addView(attachmentView);
        }
    }

    public void setIndex(int index) {
        if (index % 2 == 0) {
            setBackgroundColor(getResources().getColor(C0962R.color.hockeyapp_background_light));
            this.mAuthorTextView.setTextColor(getResources().getColor(C0962R.color.hockeyapp_text_white));
            this.mDateTextView.setTextColor(getResources().getColor(C0962R.color.hockeyapp_text_white));
        } else {
            setBackgroundColor(getResources().getColor(C0962R.color.hockeyapp_background_white));
            this.mAuthorTextView.setTextColor(getResources().getColor(C0962R.color.hockeyapp_text_light));
            this.mDateTextView.setTextColor(getResources().getColor(C0962R.color.hockeyapp_text_light));
        }
        this.mMessageTextView.setTextColor(getResources().getColor(C0962R.color.hockeyapp_text_black));
    }
}

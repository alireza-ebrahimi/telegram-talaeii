package net.hockeyapp.android.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import net.hockeyapp.android.objects.FeedbackMessage;
import net.hockeyapp.android.views.FeedbackMessageView;

public class MessagesAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<FeedbackMessage> mMessagesList;

    public MessagesAdapter(Context context, ArrayList<FeedbackMessage> messagesList) {
        this.mContext = context;
        this.mMessagesList = messagesList;
    }

    public int getCount() {
        return this.mMessagesList.size();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        FeedbackMessageView view;
        FeedbackMessage feedbackMessage = (FeedbackMessage) this.mMessagesList.get(position);
        if (convertView == null) {
            view = new FeedbackMessageView(this.mContext, null);
        } else {
            view = (FeedbackMessageView) convertView;
        }
        if (feedbackMessage != null) {
            view.setFeedbackMessage(feedbackMessage);
        }
        view.setIndex(position);
        return view;
    }

    public Object getItem(int position) {
        return this.mMessagesList.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public void clear() {
        if (this.mMessagesList != null) {
            this.mMessagesList.clear();
        }
    }

    public void add(FeedbackMessage message) {
        if (message != null && this.mMessagesList != null) {
            this.mMessagesList.add(message);
        }
    }
}

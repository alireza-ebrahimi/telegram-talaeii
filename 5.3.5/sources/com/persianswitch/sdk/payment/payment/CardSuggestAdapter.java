package com.persianswitch.sdk.payment.payment;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.persianswitch.sdk.C0770R;
import com.persianswitch.sdk.base.fastkit.FastFilterableAdapter;
import com.persianswitch.sdk.base.fastkit.FastViewHolder;
import com.persianswitch.sdk.base.manager.FontManager;
import com.persianswitch.sdk.base.manager.LanguageManager;
import com.persianswitch.sdk.base.webservice.data.WSResponse;
import com.persianswitch.sdk.payment.managers.CardManager;
import com.persianswitch.sdk.payment.managers.CardManager.SyncCardCallback;
import com.persianswitch.sdk.payment.managers.ToastManager;
import com.persianswitch.sdk.payment.model.UserCard;
import com.persianswitch.sdk.payment.repo.CardRepo;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public final class CardSuggestAdapter extends FastFilterableAdapter<UserCard, ViewHolder> {
    static final int SYNC_BUTTON = 1;
    private AtomicBoolean mErrorInSync = new AtomicBoolean(false);
    private AtomicBoolean mInProgress = new AtomicBoolean(false);
    private boolean mIsPersian = LanguageManager.getInstance(getContext()).isPersian();
    private AtomicBoolean mShowSyncButton = new AtomicBoolean(true);

    /* renamed from: com.persianswitch.sdk.payment.payment.CardSuggestAdapter$1 */
    class C08101 implements OnClickListener {

        /* renamed from: com.persianswitch.sdk.payment.payment.CardSuggestAdapter$1$1 */
        class C08091 implements SyncCardCallback {
            C08091() {
            }

            public void onCardsSynced() {
                CardSuggestAdapter.this.mShowSyncButton.set(false);
                CardSuggestAdapter.this.getEntities().clear();
                CardSuggestAdapter.this.getEntities().addAll(new CardRepo(CardSuggestAdapter.this.getContext()).getAll());
                ToastManager.showSharedToast(CardSuggestAdapter.this.getContext(), CardSuggestAdapter.this.getContext().getString(C0770R.string.asanpardakht_info_cards_synced));
                CardSuggestAdapter.this.notifyDataSetChanged();
            }

            public void onError(WSResponse wsResponse) {
                CardSuggestAdapter.this.mInProgress.set(false);
                CardSuggestAdapter.this.mErrorInSync.set(true);
                CardSuggestAdapter.this.notifyDataSetChanged();
            }
        }

        C08101() {
        }

        public void onClick(View v) {
            CardSuggestAdapter.this.mErrorInSync.set(false);
            CardSuggestAdapter.this.mInProgress.set(true);
            CardSuggestAdapter.this.notifyDataSetChanged();
            new CardManager(CardSuggestAdapter.this.getContext()).sync(new C08091());
        }
    }

    static class ViewHolder extends FastViewHolder {
        ImageView mImgBankIcon;
        TextView mTxtCardNo;
        TextView mTxtTitle;

        ViewHolder(View viewItem) {
            super(viewItem);
            this.mTxtTitle = (TextView) viewItem.findViewById(C0770R.id.txt_card_title);
            this.mTxtCardNo = (TextView) viewItem.findViewById(C0770R.id.txt_card_no);
            this.mImgBankIcon = (ImageView) viewItem.findViewById(C0770R.id.img_card_bank_icon);
            FontManager.overrideFonts(viewItem);
        }
    }

    public CardSuggestAdapter(Context context, List<UserCard> entities) {
        super(context, entities);
    }

    public View getView(int position, View view, ViewGroup viewGroup) {
        if (getItemViewType(position) == 1) {
            return getSyncView(viewGroup);
        }
        return super.getView(position, view, viewGroup);
    }

    private View getSyncView(ViewGroup viewGroup) {
        View view = LayoutInflater.from(getContext()).inflate(C0770R.layout.asanpardakht_item_sync_card, viewGroup, false);
        View lytProgress = view.findViewById(C0770R.id.lyt_sync_card_progress);
        AppCompatButton lytNormal = (AppCompatButton) view.findViewById(C0770R.id.lyt_sync_card_normal);
        lytNormal.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(getContext(), C0770R.drawable.asanpardakht_ic_refresh_24dp), null, null, null);
        AppCompatButton lytError = (AppCompatButton) view.findViewById(C0770R.id.lyt_sync_card_error);
        lytError.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(getContext(), C0770R.drawable.asanpardakht_ic_error_outline_24dp), null, null, null);
        if (this.mErrorInSync.get()) {
            lytProgress.setVisibility(8);
            lytNormal.setVisibility(8);
            lytError.setVisibility(0);
        } else if (this.mInProgress.get()) {
            lytProgress.setVisibility(0);
            lytNormal.setVisibility(8);
            lytError.setVisibility(8);
        } else {
            lytProgress.setVisibility(8);
            lytNormal.setVisibility(0);
            lytError.setVisibility(8);
        }
        OnClickListener onClickListener = new C08101();
        lytError.setOnClickListener(onClickListener);
        lytNormal.setOnClickListener(onClickListener);
        FontManager.overrideFonts(view);
        return view;
    }

    protected void onBindViewHolder(ViewHolder viewHolder, int position) {
        UserCard item = getItem(position);
        if (item != null) {
            viewHolder.mTxtTitle.setText(item.getTitle(this.mIsPersian));
            viewHolder.mTxtCardNo.setText(item.getCardDisplayName());
            viewHolder.mImgBankIcon.setImageResource(item.getLogoResource());
        }
    }

    protected ViewHolder onCreateViewHolder(Context context, ViewGroup viewGroup) {
        return new ViewHolder(LayoutInflater.from(context).inflate(C0770R.layout.asanpardakht_item_card_suggest, viewGroup, false));
    }

    @Nullable
    public UserCard getItem(int position) {
        try {
            return (UserCard) super.getItem(position);
        } catch (Exception e) {
            return null;
        }
    }

    public int getCount() {
        if (this.mShowSyncButton.get()) {
            return super.getCount() + 1;
        }
        return super.getCount();
    }

    public int getItemViewType(int position) {
        if (!this.mShowSyncButton.get()) {
            return super.getItemViewType(position);
        }
        if (position == getCount() - 1) {
            return 1;
        }
        return 0;
    }

    public int getViewTypeCount() {
        if (this.mShowSyncButton.get()) {
            return 2;
        }
        return super.getViewTypeCount();
    }
}

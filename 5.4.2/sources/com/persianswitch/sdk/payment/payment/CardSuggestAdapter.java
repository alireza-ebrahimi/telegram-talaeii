package com.persianswitch.sdk.payment.payment;

import android.content.Context;
import android.support.v7.p027c.p028a.C0825b;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.persianswitch.sdk.C2262R;
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
    /* renamed from: b */
    private AtomicBoolean f7652b = new AtomicBoolean(true);
    /* renamed from: c */
    private AtomicBoolean f7653c = new AtomicBoolean(false);
    /* renamed from: d */
    private AtomicBoolean f7654d = new AtomicBoolean(false);
    /* renamed from: e */
    private boolean f7655e = LanguageManager.m10669a(m10621a()).m10677b();

    /* renamed from: com.persianswitch.sdk.payment.payment.CardSuggestAdapter$1 */
    class C23021 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ CardSuggestAdapter f7648a;

        /* renamed from: com.persianswitch.sdk.payment.payment.CardSuggestAdapter$1$1 */
        class C23011 implements SyncCardCallback {
            /* renamed from: a */
            final /* synthetic */ C23021 f7647a;

            C23011(C23021 c23021) {
                this.f7647a = c23021;
            }

            /* renamed from: a */
            public void mo3295a() {
                this.f7647a.f7648a.f7652b.set(false);
                this.f7647a.f7648a.m10624b().clear();
                this.f7647a.f7648a.m10624b().addAll(new CardRepo(this.f7647a.f7648a.m10621a()).m10611a());
                ToastManager.m11108a(this.f7647a.f7648a.m10621a(), this.f7647a.f7648a.m10621a().getString(C2262R.string.asanpardakht_info_cards_synced));
                this.f7647a.f7648a.notifyDataSetChanged();
            }

            /* renamed from: a */
            public void mo3296a(WSResponse wSResponse) {
                this.f7647a.f7648a.f7653c.set(false);
                this.f7647a.f7648a.f7654d.set(true);
                this.f7647a.f7648a.notifyDataSetChanged();
            }
        }

        C23021(CardSuggestAdapter cardSuggestAdapter) {
            this.f7648a = cardSuggestAdapter;
        }

        public void onClick(View view) {
            this.f7648a.f7654d.set(false);
            this.f7648a.f7653c.set(true);
            this.f7648a.notifyDataSetChanged();
            new CardManager(this.f7648a.m10621a()).m11079b(new C23011(this));
        }
    }

    static class ViewHolder extends FastViewHolder {
        /* renamed from: a */
        TextView f7649a;
        /* renamed from: b */
        TextView f7650b;
        /* renamed from: c */
        ImageView f7651c;

        ViewHolder(View view) {
            super(view);
            this.f7649a = (TextView) view.findViewById(C2262R.id.txt_card_title);
            this.f7650b = (TextView) view.findViewById(C2262R.id.txt_card_no);
            this.f7651c = (ImageView) view.findViewById(C2262R.id.img_card_bank_icon);
            FontManager.m10664a(view);
        }
    }

    public CardSuggestAdapter(Context context, List<UserCard> list) {
        super(context, list);
    }

    /* renamed from: a */
    private View m11390a(ViewGroup viewGroup) {
        View inflate = LayoutInflater.from(m10621a()).inflate(C2262R.layout.asanpardakht_item_sync_card, viewGroup, false);
        View findViewById = inflate.findViewById(C2262R.id.lyt_sync_card_progress);
        AppCompatButton appCompatButton = (AppCompatButton) inflate.findViewById(C2262R.id.lyt_sync_card_normal);
        appCompatButton.setCompoundDrawablesWithIntrinsicBounds(C0825b.m3939b(m10621a(), C2262R.drawable.asanpardakht_ic_refresh_24dp), null, null, null);
        AppCompatButton appCompatButton2 = (AppCompatButton) inflate.findViewById(C2262R.id.lyt_sync_card_error);
        appCompatButton2.setCompoundDrawablesWithIntrinsicBounds(C0825b.m3939b(m10621a(), C2262R.drawable.asanpardakht_ic_error_outline_24dp), null, null, null);
        if (this.f7654d.get()) {
            findViewById.setVisibility(8);
            appCompatButton.setVisibility(8);
            appCompatButton2.setVisibility(0);
        } else if (this.f7653c.get()) {
            findViewById.setVisibility(0);
            appCompatButton.setVisibility(8);
            appCompatButton2.setVisibility(8);
        } else {
            findViewById.setVisibility(8);
            appCompatButton.setVisibility(0);
            appCompatButton2.setVisibility(8);
        }
        OnClickListener c23021 = new C23021(this);
        appCompatButton2.setOnClickListener(c23021);
        appCompatButton.setOnClickListener(c23021);
        FontManager.m10664a(inflate);
        return inflate;
    }

    /* renamed from: a */
    protected /* synthetic */ FastViewHolder mo3306a(Context context, ViewGroup viewGroup) {
        return m11398b(context, viewGroup);
    }

    /* renamed from: a */
    public UserCard m11395a(int i) {
        try {
            return (UserCard) super.getItem(i);
        } catch (Exception e) {
            return null;
        }
    }

    /* renamed from: a */
    protected void m11397a(ViewHolder viewHolder, int i) {
        UserCard a = m11395a(i);
        if (a != null) {
            viewHolder.f7649a.setText(a.m11264a(this.f7655e));
            viewHolder.f7650b.setText(a.m11272d());
            viewHolder.f7651c.setImageResource(a.m11274e());
        }
    }

    /* renamed from: b */
    protected ViewHolder m11398b(Context context, ViewGroup viewGroup) {
        return new ViewHolder(LayoutInflater.from(context).inflate(C2262R.layout.asanpardakht_item_card_suggest, viewGroup, false));
    }

    public int getCount() {
        return this.f7652b.get() ? super.getCount() + 1 : super.getCount();
    }

    public /* synthetic */ Object getItem(int i) {
        return m11395a(i);
    }

    public int getItemViewType(int i) {
        return this.f7652b.get() ? i == getCount() + -1 ? 1 : 0 : super.getItemViewType(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        return getItemViewType(i) == 1 ? m11390a(viewGroup) : super.getView(i, view, viewGroup);
    }

    public int getViewTypeCount() {
        return this.f7652b.get() ? 2 : super.getViewTypeCount();
    }
}

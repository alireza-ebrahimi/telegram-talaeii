package org.telegram.customization.p156a;

import android.support.v7.widget.RecyclerView.C0926a;
import android.support.v7.widget.RecyclerView.C0955v;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.customization.Model.ContactChangeLog;
import org.telegram.customization.util.C2872c;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.MessagesController;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.EncryptedChat;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.AvatarDrawable;
import org.telegram.ui.Components.BackupImageView;

/* renamed from: org.telegram.customization.a.c */
public class C2637c extends C0926a<C2636a> {
    /* renamed from: a */
    ArrayList<ContactChangeLog> f8817a = new ArrayList();
    /* renamed from: b */
    private final C2532b f8818b;

    /* renamed from: org.telegram.customization.a.c$b */
    public interface C2532b {
        /* renamed from: a */
        void mo3430a(User user, Chat chat);
    }

    /* renamed from: org.telegram.customization.a.c$a */
    class C2636a extends C0955v {
        /* renamed from: a */
        View f8809a;
        /* renamed from: b */
        BackupImageView f8810b;
        /* renamed from: c */
        AvatarDrawable f8811c = new AvatarDrawable();
        /* renamed from: d */
        TextView f8812d;
        /* renamed from: e */
        TextView f8813e;
        /* renamed from: f */
        TextView f8814f;
        /* renamed from: g */
        View f8815g;
        /* renamed from: h */
        final /* synthetic */ C2637c f8816h;

        public C2636a(C2637c c2637c, View view) {
            this.f8816h = c2637c;
            super(view);
            this.f8809a = view.findViewById(R.id.root);
            this.f8810b = (BackupImageView) view.findViewById(R.id.userAvatar);
            this.f8810b.setRoundRadius(AndroidUtilities.dp(50.0f));
            this.f8813e = (TextView) view.findViewById(R.id.date);
            this.f8814f = (TextView) view.findViewById(R.id.user);
            this.f8812d = (TextView) view.findViewById(R.id.log);
            this.f8815g = view.findViewById(R.id.divider);
        }
    }

    public C2637c(C2532b c2532b, ArrayList<ContactChangeLog> arrayList) {
        this.f8818b = c2532b;
        this.f8817a = arrayList;
    }

    /* renamed from: a */
    public ArrayList<ContactChangeLog> m12482a() {
        return this.f8817a;
    }

    /* renamed from: a */
    public C2636a m12483a(ViewGroup viewGroup, int i) {
        return new C2636a(this, LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.change_log_item, viewGroup, false));
    }

    /* renamed from: a */
    public void m12484a(C2636a c2636a, int i) {
        User user;
        TLObject tLObject = null;
        long chatId = ((ContactChangeLog) m12482a().get(i)).getChatId();
        int i2 = (int) chatId;
        int i3 = (int) (chatId >> 32);
        if (i2 == 0) {
            EncryptedChat encryptedChat = MessagesController.getInstance().getEncryptedChat(Integer.valueOf(i3));
            user = encryptedChat != null ? MessagesController.getInstance().getUser(Integer.valueOf(encryptedChat.user_id)) : null;
        } else if (i3 == 1) {
            MessagesController.getInstance().getChat(Integer.valueOf(i2));
            user = null;
        } else if (i2 < 0) {
            MessagesController.getInstance().getChat(Integer.valueOf(-i2));
            user = null;
        } else {
            user = MessagesController.getInstance().getUser(Integer.valueOf(i2));
        }
        if (user != null) {
            if (user.photo != null) {
                tLObject = user.photo.photo_small;
            }
            c2636a.f8811c.setInfo(user);
            CharSequence charSequence = (user.first_name == null ? TtmlNode.ANONYMOUS_REGION_ID : user.first_name + " ") + (user.last_name == null ? TtmlNode.ANONYMOUS_REGION_ID : user.last_name);
            TextView textView = c2636a.f8814f;
            if (charSequence == null) {
                charSequence = TtmlNode.ANONYMOUS_REGION_ID;
            }
            textView.setText(charSequence);
            c2636a.f8814f.setTextColor(Theme.getColor(Theme.key_chats_name));
            c2636a.f8812d.setText(ContactChangeLog.getLogStringByType(((ContactChangeLog) m12482a().get(i)).getType()));
            c2636a.f8812d.setTextColor(Theme.getColor(Theme.key_chats_message));
            c2636a.f8810b.setImage(tLObject, "50_50", c2636a.f8811c);
            c2636a.itemView.setOnClickListener(new OnClickListener(this) {
                /* renamed from: b */
                final /* synthetic */ C2637c f8808b;

                public void onClick(View view) {
                    this.f8808b.f8818b.mo3430a(user, null);
                }
            });
        }
        c2636a.f8813e.setText(C2872c.m13347b(((ContactChangeLog) m12482a().get(i)).getDate()));
        c2636a.f8809a.setBackgroundColor(Theme.getColor(Theme.key_chat_inBubble));
        c2636a.f8815g.setBackgroundColor(Theme.getColor(Theme.key_divider));
    }

    public int getItemCount() {
        return m12482a() == null ? 0 : m12482a().size();
    }

    public /* synthetic */ void onBindViewHolder(C0955v c0955v, int i) {
        m12484a((C2636a) c0955v, i);
    }

    public /* synthetic */ C0955v onCreateViewHolder(ViewGroup viewGroup, int i) {
        return m12483a(viewGroup, i);
    }
}

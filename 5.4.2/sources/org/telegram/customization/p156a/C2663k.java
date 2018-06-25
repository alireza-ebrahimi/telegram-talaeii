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
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.MessagesController;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_dialog;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.EncryptedChat;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.AvatarDrawable;
import org.telegram.ui.Components.BackupImageView;

/* renamed from: org.telegram.customization.a.k */
public class C2663k extends C0926a<C2662b> {
    /* renamed from: a */
    private final C2661a f8887a;

    /* renamed from: org.telegram.customization.a.k$a */
    public interface C2661a {
        void onClick(User user, Chat chat);
    }

    /* renamed from: org.telegram.customization.a.k$b */
    class C2662b extends C0955v {
        /* renamed from: a */
        BackupImageView f8883a;
        /* renamed from: b */
        AvatarDrawable f8884b = new AvatarDrawable();
        /* renamed from: c */
        TextView f8885c;
        /* renamed from: d */
        final /* synthetic */ C2663k f8886d;

        public C2662b(C2663k c2663k, View view) {
            this.f8886d = c2663k;
            super(view);
            this.f8883a = (BackupImageView) view.findViewById(R.id.userAvatar);
            this.f8883a.setRoundRadius(AndroidUtilities.dp(50.0f));
            this.f8885c = (TextView) view.findViewById(R.id.name);
            this.f8885c.setTextColor(Theme.ACTION_BAR_VIDEO_EDIT_COLOR);
        }
    }

    public C2663k(C2661a c2661a) {
        this.f8887a = c2661a;
    }

    /* renamed from: a */
    ArrayList<TLRPC$TL_dialog> m12509a() {
        return MessagesController.getInstance().dialogs;
    }

    /* renamed from: a */
    public C2662b m12510a(ViewGroup viewGroup, int i) {
        return new C2662b(this, LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.quick_access_to_users_item, viewGroup, false));
    }

    /* renamed from: a */
    public void m12511a(C2662b c2662b, int i) {
        TLObject tLObject = null;
        if (m12509a() != null) {
            Chat chat;
            User user;
            long j = ((TLRPC$TL_dialog) m12509a().get(i)).id;
            int i2 = (int) j;
            int i3 = (int) (j >> 32);
            if (i2 == 0) {
                EncryptedChat encryptedChat = MessagesController.getInstance().getEncryptedChat(Integer.valueOf(i3));
                if (encryptedChat != null) {
                    chat = null;
                    user = MessagesController.getInstance().getUser(Integer.valueOf(encryptedChat.user_id));
                } else {
                    chat = null;
                    user = null;
                }
            } else if (i3 == 1) {
                chat = MessagesController.getInstance().getChat(Integer.valueOf(i2));
                user = null;
            } else if (i2 < 0) {
                chat = MessagesController.getInstance().getChat(Integer.valueOf(-i2));
                user = null;
            } else {
                chat = null;
                user = MessagesController.getInstance().getUser(Integer.valueOf(i2));
            }
            if (user != null) {
                TLObject tLObject2 = user.photo != null ? user.photo.photo_small : null;
                c2662b.f8884b.setInfo(user);
                c2662b.f8885c.setText((user.first_name == null ? TtmlNode.ANONYMOUS_REGION_ID : user.first_name + " ") + (user.last_name == null ? TtmlNode.ANONYMOUS_REGION_ID : user.last_name));
                c2662b.f8883a.setImage(tLObject2, "50_50", c2662b.f8884b);
                c2662b.f8883a.setOnClickListener(new OnClickListener(this) {
                    /* renamed from: b */
                    final /* synthetic */ C2663k f8880b;

                    public void onClick(View view) {
                        this.f8880b.f8887a.onClick(user, null);
                    }
                });
            } else if (chat != null) {
                if (chat.photo != null) {
                    tLObject = chat.photo.photo_small;
                }
                c2662b.f8884b.setInfo(chat);
                c2662b.f8885c.setText(chat.title == null ? TtmlNode.ANONYMOUS_REGION_ID : chat.title);
                c2662b.f8883a.setImage(tLObject, "50_50", c2662b.f8884b);
                c2662b.f8883a.setOnClickListener(new OnClickListener(this) {
                    /* renamed from: b */
                    final /* synthetic */ C2663k f8882b;

                    public void onClick(View view) {
                        this.f8882b.f8887a.onClick(null, chat);
                    }
                });
            }
        }
    }

    public int getItemCount() {
        return m12509a() == null ? 0 : m12509a().size();
    }

    public /* synthetic */ void onBindViewHolder(C0955v c0955v, int i) {
        m12511a((C2662b) c0955v, i);
    }

    public /* synthetic */ C0955v onCreateViewHolder(ViewGroup viewGroup, int i) {
        return m12510a(viewGroup, i);
    }
}

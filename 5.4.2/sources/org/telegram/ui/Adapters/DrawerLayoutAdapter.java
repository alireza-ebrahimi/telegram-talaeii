package org.telegram.ui.Adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.customization.util.C2873d;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.messenger.support.widget.RecyclerView.LayoutParams;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Cells.DividerCell;
import org.telegram.ui.Cells.DrawerActionCell;
import org.telegram.ui.Cells.DrawerProfileCell;
import org.telegram.ui.Cells.EmptyCell;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;
import utils.p178a.C3791b;

public class DrawerLayoutAdapter extends SelectionAdapter {
    private ArrayList<Item> items = new ArrayList();
    private Context mContext;

    private class Item {
        public int icon;
        public int id;
        public String text;

        public Item(int i, String str, int i2) {
            this.icon = i2;
            this.id = i;
            this.text = str;
        }

        public void bind(C2873d c2873d) {
            c2873d.a(this.text, this.icon);
        }

        public void bind(DrawerActionCell drawerActionCell) {
            drawerActionCell.setTextAndIcon(this.text, this.icon);
        }
    }

    public DrawerLayoutAdapter(Context context) {
        this.mContext = context;
        Theme.createDialogsResources(context);
        resetItems();
    }

    private void resetItems() {
        this.items.clear();
        if (UserConfig.isClientActivated()) {
            this.items.add(null);
            this.items.add(null);
            this.items.add(new Item(2, LocaleController.getString("NewGroup", R.string.NewGroup), R.drawable.menu_newgroup));
            this.items.add(new Item(3, LocaleController.getString("NewSecretChat", R.string.NewSecretChat), R.drawable.menu_secret));
            this.items.add(new Item(4, LocaleController.getString("NewChannel", R.string.NewChannel), R.drawable.menu_broadcast));
            this.items.add(new Item(12, LocaleController.getString("Contacts", R.string.Contacts), R.drawable.menu_contacts));
            if (C3791b.f()) {
                this.items.add(new Item(2222, LocaleController.getString("HotgramPayment", R.string.HotgramPayment), R.drawable.ic_square_inc_cash));
            }
            this.items.add(new Item(110, LocaleController.getString("SavedMessages", R.string.SavedMessages), R.drawable.menu_saved));
            this.items.add(new Item(100, TtmlNode.ANONYMOUS_REGION_ID, R.drawable.menu_theming));
            this.items.add(new Item(101, LocaleController.getString("action_settings", R.string.action_settings), R.drawable.menu_theming));
            this.items.add(new Item(15, LocaleController.getString("Settings", R.string.Settings), R.drawable.menu_settings));
            this.items.add(new Item(16, LocaleController.getString("PlusSettings", R.string.PlusSettings), R.drawable.menu_settings));
            this.items.add(new Item(20, LocaleController.getString("DownloadManaging", R.string.DownloadManaging), R.drawable.ic_download_white_48dp));
            this.items.add(new Item(6, LocaleController.getString("Theming", R.string.Theming), R.drawable.menu_theming));
            this.items.add(new Item(11, LocaleController.getString("CategoryDialogs", R.string.CategoryDialogs), R.drawable.categories));
            this.items.add(new Item(102, TtmlNode.ANONYMOUS_REGION_ID, R.drawable.menu_theming));
            this.items.add(new Item(103, LocaleController.getString("tools", R.string.tools), R.drawable.menu_theming));
            this.items.add(new Item(7, LocaleController.getString("IdFinder", R.string.IdFinder), R.drawable.ic_magnify));
            this.items.add(new Item(8, LocaleController.getString("UserChangeLog", R.string.UserChangeLog), R.drawable.menu_contacts));
            this.items.add(new Item(9, LocaleController.getString("ClearCache", R.string.ClearCache), R.drawable.ic_clear_cache));
            if (C3791b.y(this.mContext)) {
                this.items.add(new Item(18, LocaleController.getString("On", R.string.On), R.drawable.ic_power));
            } else {
                this.items.add(new Item(18, LocaleController.getString("Off", R.string.Off), R.drawable.ic_power));
            }
            if (C3791b.c()) {
                this.items.add(new Item(10, LocaleController.getString("Calls", R.string.Calls), R.drawable.menu_calls));
            }
            this.items.add(new Item(13, LocaleController.getString("onlineContacts", R.string.Online_Contacts), R.drawable.menu_contacts));
            this.items.add(new Item(104, TtmlNode.ANONYMOUS_REGION_ID, R.drawable.menu_theming));
            this.items.add(new Item(105, LocaleController.getString("other", R.string.other), R.drawable.menu_theming));
            this.items.add(new Item(17, LocaleController.getString("TelegramFaq", R.string.TelegramFaq), R.drawable.menu_help));
            if (!TextUtils.isEmpty(C3791b.m())) {
                this.items.add(new Item(999, LocaleController.getString("ContactUs", R.string.ContactUs), R.drawable.menu_calls));
            }
            this.items.add(new Item(222, "V-5.4.2" + LocaleController.getString("MyAppName", R.string.MyAppName), R.drawable.transparent));
        }
    }

    public int getId(int i) {
        if (i < 0 || i >= this.items.size()) {
            return -1;
        }
        Item item = (Item) this.items.get(i);
        return item != null ? item.id : -1;
    }

    public int getItemCount() {
        return this.items.size();
    }

    public int getItemViewType(int i) {
        if (i == 0) {
            return 0;
        }
        if (i == 1) {
            return 1;
        }
        if (i != (C3791b.f() ? 8 : 7)) {
            if (i != (C3791b.f() ? 9 : 8)) {
                if (i != (C3791b.f() ? 15 : 14)) {
                    if (i != (C3791b.f() ? 16 : 15)) {
                        if (i != (C3791b.f() ? 23 : 22)) {
                            if (i != (C3791b.f() ? 24 : 23)) {
                                if (i != (C3791b.f() ? 27 : 26)) {
                                    return 3;
                                }
                            }
                        }
                    }
                }
            }
        }
        return 4;
    }

    public boolean isEnabled(ViewHolder viewHolder) {
        return viewHolder.getItemViewType() == 3;
    }

    public void notifyDataSetChanged() {
        resetItems();
        super.notifyDataSetChanged();
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        switch (viewHolder.getItemViewType()) {
            case 0:
                ((DrawerProfileCell) viewHolder.itemView).setUser(MessagesController.getInstance().getUser(Integer.valueOf(UserConfig.getClientUserId())));
                viewHolder.itemView.setBackgroundColor(Theme.getColor(Theme.key_avatar_backgroundActionBarBlue));
                return;
            case 3:
                ((Item) this.items.get(i)).bind((DrawerActionCell) viewHolder.itemView);
                return;
            case 4:
                ((Item) this.items.get(i)).bind((C2873d) viewHolder.itemView);
                return;
            default:
                return;
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View drawerProfileCell;
        switch (i) {
            case 0:
                drawerProfileCell = new DrawerProfileCell(this.mContext);
                break;
            case 2:
                drawerProfileCell = new DividerCell(this.mContext);
                break;
            case 3:
                drawerProfileCell = new DrawerActionCell(this.mContext);
                break;
            case 4:
                drawerProfileCell = new C2873d(this.mContext);
                break;
            default:
                drawerProfileCell = new EmptyCell(this.mContext, AndroidUtilities.dp(8.0f));
                break;
        }
        drawerProfileCell.setLayoutParams(new LayoutParams(-1, -2));
        return new Holder(drawerProfileCell);
    }
}

package org.telegram.ui.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.customization.util.DrawerActionCellHeader;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.support.widget.RecyclerView.LayoutParams;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Cells.DividerCell;
import org.telegram.ui.Cells.DrawerActionCell;
import org.telegram.ui.Cells.DrawerProfileCell;
import org.telegram.ui.Cells.EmptyCell;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;
import utils.app.AppPreferences;

public class DrawerLayoutAdapter extends SelectionAdapter {
    private ArrayList<Item> items = new ArrayList();
    private Context mContext;

    private class Item {
        public int icon;
        public int id;
        public String text;

        public Item(int id, String text, int icon) {
            this.icon = icon;
            this.id = id;
            this.text = text;
        }

        public void bind(DrawerActionCell actionCell) {
            actionCell.setTextAndIcon(this.text, this.icon);
        }

        public void bind(DrawerActionCellHeader itemView) {
            itemView.setTextAndIcon(this.text, this.icon);
        }
    }

    public DrawerLayoutAdapter(Context context) {
        this.mContext = context;
        Theme.createDialogsResources(context);
        resetItems();
    }

    public int getItemCount() {
        return this.items.size();
    }

    public void notifyDataSetChanged() {
        resetItems();
        super.notifyDataSetChanged();
    }

    public boolean isEnabled(ViewHolder holder) {
        return holder.getItemViewType() == 3;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = new DrawerProfileCell(this.mContext);
                break;
            case 2:
                view = new DividerCell(this.mContext);
                break;
            case 3:
                view = new DrawerActionCell(this.mContext);
                break;
            case 4:
                view = new DrawerActionCellHeader(this.mContext);
                break;
            default:
                view = new EmptyCell(this.mContext, AndroidUtilities.dp(8.0f));
                break;
        }
        view.setLayoutParams(new LayoutParams(-1, -2));
        return new Holder(view);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 0:
                ((DrawerProfileCell) holder.itemView).setUser(MessagesController.getInstance().getUser(Integer.valueOf(UserConfig.getClientUserId())));
                holder.itemView.setBackgroundColor(Theme.getColor(Theme.key_avatar_backgroundActionBarBlue));
                return;
            case 3:
                ((Item) this.items.get(position)).bind((DrawerActionCell) holder.itemView);
                return;
            case 4:
                ((Item) this.items.get(position)).bind((DrawerActionCellHeader) holder.itemView);
                return;
            default:
                return;
        }
    }

    public int getItemViewType(int i) {
        if (i == 0) {
            return 0;
        }
        if (i == 1) {
            return 1;
        }
        if (i != (AppPreferences.isPaymentEnable() ? 8 : 7)) {
            int i2;
            if (AppPreferences.isPaymentEnable()) {
                i2 = 9;
            } else {
                i2 = 8;
            }
            if (i != i2) {
                if (i != (AppPreferences.isPaymentEnable() ? 15 : 14)) {
                    if (AppPreferences.isPaymentEnable()) {
                        i2 = 16;
                    } else {
                        i2 = 15;
                    }
                    if (i != i2) {
                        if (i != (AppPreferences.isPaymentEnable() ? 23 : 22)) {
                            if (AppPreferences.isPaymentEnable()) {
                                i2 = 24;
                            } else {
                                i2 = 23;
                            }
                            if (i != i2) {
                                if (i != (AppPreferences.isPaymentEnable() ? 26 : 25)) {
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

    private void resetItems() {
        this.items.clear();
        if (UserConfig.isClientActivated()) {
            this.items.add(null);
            this.items.add(null);
            this.items.add(new Item(2, LocaleController.getString("NewGroup", R.string.NewGroup), R.drawable.menu_newgroup));
            this.items.add(new Item(3, LocaleController.getString("NewSecretChat", R.string.NewSecretChat), R.drawable.menu_secret));
            this.items.add(new Item(4, LocaleController.getString("NewChannel", R.string.NewChannel), R.drawable.menu_broadcast));
            this.items.add(new Item(12, LocaleController.getString("Contacts", R.string.Contacts), R.drawable.menu_contacts));
            if (AppPreferences.isPaymentEnable()) {
                this.items.add(new Item(2222, LocaleController.getString("HotgramPayment", R.string.HotgramPayment), R.drawable.ic_square_inc_cash));
            }
            this.items.add(new Item(110, LocaleController.getString("SavedMessages", R.string.SavedMessages), R.drawable.menu_saved));
            this.items.add(new Item(100, "", R.drawable.menu_theming));
            this.items.add(new Item(101, LocaleController.getString("action_settings", R.string.action_settings), R.drawable.menu_theming));
            this.items.add(new Item(15, LocaleController.getString("Settings", R.string.Settings), R.drawable.menu_settings));
            this.items.add(new Item(16, LocaleController.getString("PlusSettings", R.string.PlusSettings), R.drawable.menu_settings));
            this.items.add(new Item(20, LocaleController.getString("DownloadManaging", R.string.DownloadManaging), R.drawable.ic_download_white_48dp));
            this.items.add(new Item(6, LocaleController.getString("Theming", R.string.Theming), R.drawable.menu_theming));
            this.items.add(new Item(11, LocaleController.getString("CategoryDialogs", R.string.CategoryDialogs), R.drawable.categories));
            this.items.add(new Item(102, "", R.drawable.menu_theming));
            this.items.add(new Item(103, LocaleController.getString("tools", R.string.tools), R.drawable.menu_theming));
            this.items.add(new Item(7, LocaleController.getString("IdFinder", R.string.IdFinder), R.drawable.ic_magnify));
            this.items.add(new Item(8, LocaleController.getString("UserChangeLog", R.string.UserChangeLog), R.drawable.menu_contacts));
            this.items.add(new Item(9, LocaleController.getString("ClearCache", R.string.ClearCache), R.drawable.ic_clear_cache));
            if (AppPreferences.getOffMode(this.mContext)) {
                this.items.add(new Item(18, LocaleController.getString("On", R.string.On), R.drawable.ic_power));
            } else {
                this.items.add(new Item(18, LocaleController.getString("Off", R.string.Off), R.drawable.ic_power));
            }
            if (AppPreferences.isCallEnable()) {
                this.items.add(new Item(10, LocaleController.getString("Calls", R.string.Calls), R.drawable.menu_calls));
            }
            this.items.add(new Item(13, LocaleController.getString("onlineContacts", R.string.Online_Contacts), R.drawable.menu_contacts));
            this.items.add(new Item(104, "", R.drawable.menu_theming));
            this.items.add(new Item(105, LocaleController.getString("other", R.string.other), R.drawable.menu_theming));
            this.items.add(new Item(17, LocaleController.getString("TelegramFaq", R.string.TelegramFaq), R.drawable.menu_help));
            this.items.add(new Item(222, "V-5.3.5" + LocaleController.getString("MyAppName", R.string.MyAppName), R.drawable.transparent));
        }
    }

    public int getId(int position) {
        if (position < 0 || position >= this.items.size()) {
            return -1;
        }
        Item item = (Item) this.items.get(position);
        if (item != null) {
            return item.id;
        }
        return -1;
    }
}

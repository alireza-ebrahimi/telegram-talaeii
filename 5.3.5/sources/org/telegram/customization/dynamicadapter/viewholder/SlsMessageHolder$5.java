package org.telegram.customization.dynamicadapter.viewholder;

import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import org.ir.talaeii.R;
import org.telegram.customization.Model.Favourite;
import org.telegram.messenger.LocaleController;

class SlsMessageHolder$5 implements OnClickListener {
    final /* synthetic */ SlsMessageHolder this$0;

    /* renamed from: org.telegram.customization.dynamicadapter.viewholder.SlsMessageHolder$5$1 */
    class C12041 implements OnMenuItemClickListener {
        C12041() {
        }

        public boolean onMenuItemClick(MenuItem item) {
            if (item.getItemId() == 1) {
                SlsMessageHolder.access$302(SlsMessageHolder$5.this.this$0, 2);
                SlsMessageHolder.access$400(SlsMessageHolder$5.this.this$0);
            } else if (item.getItemId() == 2) {
                try {
                    SlsMessageHolder.access$302(SlsMessageHolder$5.this.this$0, 4);
                    SlsMessageHolder.access$400(SlsMessageHolder$5.this.this$0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return false;
        }
    }

    SlsMessageHolder$5(SlsMessageHolder this$0) {
        this.this$0 = this$0;
    }

    public void onClick(View v) {
        PopupMenu popup = new PopupMenu(this.this$0.getActivity(), v);
        String report = LocaleController.getString("SaveToMusic", R.string.SaveToMusic);
        if (SlsMessageHolder.access$000(this.this$0) != null && SlsMessageHolder.access$000(this.this$0).getMessage() != null && SlsMessageHolder.access$000(this.this$0).getMessage().to_id != null) {
            String fav;
            if (Favourite.isFavouriteMessage(Long.valueOf((long) (-Math.abs(SlsMessageHolder.access$000(this.this$0).getMessage().to_id.channel_id))), (long) SlsMessageHolder.access$000(this.this$0).getMessage().id)) {
                fav = LocaleController.getString("DeleteFromFavorites", R.string.DeleteFromFavorites);
            } else {
                fav = LocaleController.getString("AddToFavorites", R.string.AddToFavorites);
            }
            popup.getMenu().add(0, 1, 0, fav);
            popup.getMenu().add(0, 2, 1, report);
            popup.show();
            popup.setOnMenuItemClickListener(new C12041());
        }
    }
}

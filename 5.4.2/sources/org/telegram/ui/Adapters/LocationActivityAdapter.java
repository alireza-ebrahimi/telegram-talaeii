package org.telegram.ui.Adapters;

import android.content.Context;
import android.location.Location;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Locale;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.LocationController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.TLRPC$TL_messageMediaVenue;
import org.telegram.ui.Cells.EmptyCell;
import org.telegram.ui.Cells.GraySectionCell;
import org.telegram.ui.Cells.LocationCell;
import org.telegram.ui.Cells.LocationLoadingCell;
import org.telegram.ui.Cells.LocationPoweredCell;
import org.telegram.ui.Cells.SendLocationCell;
import org.telegram.ui.Cells.SharingLiveLocationCell;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.LocationActivity.LiveLocation;

public class LocationActivityAdapter extends BaseLocationAdapter {
    private ArrayList<LiveLocation> currentLiveLocations = new ArrayList();
    private MessageObject currentMessageObject;
    private Location customLocation;
    private long dialogId;
    private Location gpsLocation;
    private int liveLocationType;
    private Context mContext;
    private int overScrollHeight;
    private boolean pulledUp;
    private SendLocationCell sendLocationCell;
    private int shareLiveLocationPotistion = -1;

    /* renamed from: org.telegram.ui.Adapters.LocationActivityAdapter$1 */
    class C38661 implements Runnable {
        C38661() {
        }

        public void run() {
            LocationActivityAdapter.this.notifyItemChanged(LocationActivityAdapter.this.liveLocationType == 0 ? 2 : 3);
        }
    }

    public LocationActivityAdapter(Context context, int i, long j) {
        this.mContext = context;
        this.liveLocationType = i;
        this.dialogId = j;
    }

    private void updateCell() {
        if (this.sendLocationCell == null) {
            return;
        }
        if (this.customLocation != null) {
            this.sendLocationCell.setText(LocaleController.getString("SendSelectedLocation", R.string.SendSelectedLocation), String.format(Locale.US, "(%f,%f)", new Object[]{Double.valueOf(this.customLocation.getLatitude()), Double.valueOf(this.customLocation.getLongitude())}));
        } else if (this.gpsLocation != null) {
            this.sendLocationCell.setText(LocaleController.getString("SendLocation", R.string.SendLocation), LocaleController.formatString("AccurateTo", R.string.AccurateTo, new Object[]{LocaleController.formatPluralString("Meters", (int) this.gpsLocation.getAccuracy())}));
        } else {
            this.sendLocationCell.setText(LocaleController.getString("SendLocation", R.string.SendLocation), LocaleController.getString("Loading", R.string.Loading));
        }
    }

    public Object getItem(int i) {
        return this.currentMessageObject != null ? i == 1 ? this.currentMessageObject : (i <= 3 || i >= this.places.size() + 3) ? null : this.currentLiveLocations.get(i - 4) : this.liveLocationType == 2 ? i >= 2 ? this.currentLiveLocations.get(i - 2) : null : this.liveLocationType == 1 ? (i <= 3 || i >= this.places.size() + 3) ? null : this.places.get(i - 4) : (i <= 2 || i >= this.places.size() + 2) ? null : this.places.get(i - 3);
    }

    public int getItemCount() {
        int i = 0;
        if (this.currentMessageObject != null) {
            if (!this.currentLiveLocations.isEmpty()) {
                i = this.currentLiveLocations.size() + 2;
            }
            return i + 2;
        } else if (this.liveLocationType == 2) {
            return this.currentLiveLocations.size() + 2;
        } else {
            if (this.searching || (!this.searching && this.places.isEmpty())) {
                return this.liveLocationType != 0 ? 5 : 4;
            } else {
                int size;
                if (this.liveLocationType == 1) {
                    size = this.places.size() + 4;
                    if (!this.places.isEmpty()) {
                        i = 1;
                    }
                    return i + size;
                }
                size = this.places.size() + 3;
                if (!this.places.isEmpty()) {
                    i = 1;
                }
                return i + size;
            }
        }
    }

    public int getItemViewType(int i) {
        if (i == 0) {
            return 0;
        }
        if (this.currentMessageObject != null) {
            if (i == 2) {
                return 2;
            }
            if (i != 3) {
                return 7;
            }
            this.shareLiveLocationPotistion = i;
            return 6;
        } else if (this.liveLocationType != 2) {
            if (this.liveLocationType == 1) {
                if (i == 1) {
                    return 1;
                }
                if (i == 2) {
                    this.shareLiveLocationPotistion = i;
                    return 6;
                } else if (i == 3) {
                    return 2;
                } else {
                    if (this.searching || (!this.searching && this.places.isEmpty())) {
                        return 4;
                    }
                    if (i == this.places.size() + 4) {
                        return 5;
                    }
                }
            } else if (i == 1) {
                return 1;
            } else {
                if (i == 2) {
                    return 2;
                }
                if (this.searching || (!this.searching && this.places.isEmpty())) {
                    return 4;
                }
                if (i == this.places.size() + 3) {
                    return 5;
                }
            }
            return 3;
        } else if (i != 1) {
            return 7;
        } else {
            this.shareLiveLocationPotistion = i;
            return 6;
        }
    }

    public boolean isEnabled(ViewHolder viewHolder) {
        int itemViewType = viewHolder.getItemViewType();
        return itemViewType == 6 ? (LocationController.getInstance().getSharingLocationInfo(this.dialogId) == null && this.gpsLocation == null) ? false : true : itemViewType == 1 || itemViewType == 3 || itemViewType == 7;
    }

    public boolean isPulledUp() {
        return this.pulledUp;
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        switch (viewHolder.getItemViewType()) {
            case 0:
                ((EmptyCell) viewHolder.itemView).setHeight(this.overScrollHeight);
                return;
            case 1:
                this.sendLocationCell = (SendLocationCell) viewHolder.itemView;
                updateCell();
                return;
            case 2:
                if (this.currentMessageObject != null) {
                    ((GraySectionCell) viewHolder.itemView).setText(LocaleController.getString("LiveLocations", R.string.LiveLocations));
                    return;
                } else if (this.pulledUp) {
                    ((GraySectionCell) viewHolder.itemView).setText(LocaleController.getString("NearbyPlaces", R.string.NearbyPlaces));
                    return;
                } else {
                    ((GraySectionCell) viewHolder.itemView).setText(LocaleController.getString("ShowNearbyPlaces", R.string.ShowNearbyPlaces));
                    return;
                }
            case 3:
                if (this.liveLocationType == 0) {
                    ((LocationCell) viewHolder.itemView).setLocation((TLRPC$TL_messageMediaVenue) this.places.get(i - 3), (String) this.iconUrls.get(i - 3), true);
                    return;
                } else {
                    ((LocationCell) viewHolder.itemView).setLocation((TLRPC$TL_messageMediaVenue) this.places.get(i - 4), (String) this.iconUrls.get(i - 4), true);
                    return;
                }
            case 4:
                ((LocationLoadingCell) viewHolder.itemView).setLoading(this.searching);
                return;
            case 6:
                ((SendLocationCell) viewHolder.itemView).setHasLocation(this.gpsLocation != null);
                return;
            case 7:
                if (this.currentMessageObject == null || i != 1) {
                    ((SharingLiveLocationCell) viewHolder.itemView).setDialog((LiveLocation) this.currentLiveLocations.get(i - (this.currentMessageObject != null ? 4 : 2)), this.gpsLocation);
                    return;
                } else {
                    ((SharingLiveLocationCell) viewHolder.itemView).setDialog(this.currentMessageObject, this.gpsLocation);
                    return;
                }
            default:
                return;
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View emptyCell;
        switch (i) {
            case 0:
                emptyCell = new EmptyCell(this.mContext);
                break;
            case 1:
                emptyCell = new SendLocationCell(this.mContext, false);
                break;
            case 2:
                emptyCell = new GraySectionCell(this.mContext);
                break;
            case 3:
                emptyCell = new LocationCell(this.mContext);
                break;
            case 4:
                emptyCell = new LocationLoadingCell(this.mContext);
                break;
            case 5:
                emptyCell = new LocationPoweredCell(this.mContext);
                break;
            case 6:
                emptyCell = new SendLocationCell(this.mContext, true);
                emptyCell.setDialogId(this.dialogId);
                break;
            default:
                emptyCell = new SharingLiveLocationCell(this.mContext, true);
                break;
        }
        return new Holder(emptyCell);
    }

    public void setCustomLocation(Location location) {
        this.customLocation = location;
        updateCell();
    }

    public void setGpsLocation(Location location) {
        int i = this.gpsLocation == null ? 1 : 0;
        this.gpsLocation = location;
        if (i != 0 && this.shareLiveLocationPotistion > 0) {
            notifyItemChanged(this.shareLiveLocationPotistion);
        }
        if (this.currentMessageObject != null) {
            notifyItemChanged(1);
            updateLiveLocations();
        } else if (this.liveLocationType != 2) {
            updateCell();
        } else {
            updateLiveLocations();
        }
    }

    public void setLiveLocations(ArrayList<LiveLocation> arrayList) {
        this.currentLiveLocations = new ArrayList(arrayList);
        int clientUserId = UserConfig.getClientUserId();
        for (int i = 0; i < this.currentLiveLocations.size(); i++) {
            if (((LiveLocation) this.currentLiveLocations.get(i)).id == clientUserId) {
                this.currentLiveLocations.remove(i);
                break;
            }
        }
        notifyDataSetChanged();
    }

    public void setMessageObject(MessageObject messageObject) {
        this.currentMessageObject = messageObject;
        notifyDataSetChanged();
    }

    public void setOverScrollHeight(int i) {
        this.overScrollHeight = i;
    }

    public void setPulledUp() {
        if (!this.pulledUp) {
            this.pulledUp = true;
            AndroidUtilities.runOnUIThread(new C38661());
        }
    }

    public void updateLiveLocations() {
        if (!this.currentLiveLocations.isEmpty()) {
            notifyItemRangeChanged(2, this.currentLiveLocations.size());
        }
    }
}

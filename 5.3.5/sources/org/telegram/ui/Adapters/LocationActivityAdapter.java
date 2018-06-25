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
    class C20281 implements Runnable {
        C20281() {
        }

        public void run() {
            LocationActivityAdapter.this.notifyItemChanged(LocationActivityAdapter.this.liveLocationType == 0 ? 2 : 3);
        }
    }

    public LocationActivityAdapter(Context context, int live, long did) {
        this.mContext = context;
        this.liveLocationType = live;
        this.dialogId = did;
    }

    public void setOverScrollHeight(int value) {
        this.overScrollHeight = value;
    }

    public void setGpsLocation(Location location) {
        boolean notSet = this.gpsLocation == null;
        this.gpsLocation = location;
        if (notSet && this.shareLiveLocationPotistion > 0) {
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

    public void updateLiveLocations() {
        if (!this.currentLiveLocations.isEmpty()) {
            notifyItemRangeChanged(2, this.currentLiveLocations.size());
        }
    }

    public void setCustomLocation(Location location) {
        this.customLocation = location;
        updateCell();
    }

    public void setLiveLocations(ArrayList<LiveLocation> liveLocations) {
        this.currentLiveLocations = new ArrayList(liveLocations);
        int uid = UserConfig.getClientUserId();
        for (int a = 0; a < this.currentLiveLocations.size(); a++) {
            if (((LiveLocation) this.currentLiveLocations.get(a)).id == uid) {
                this.currentLiveLocations.remove(a);
                break;
            }
        }
        notifyDataSetChanged();
    }

    public void setMessageObject(MessageObject messageObject) {
        this.currentMessageObject = messageObject;
        notifyDataSetChanged();
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

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = new EmptyCell(this.mContext);
                break;
            case 1:
                view = new SendLocationCell(this.mContext, false);
                break;
            case 2:
                view = new GraySectionCell(this.mContext);
                break;
            case 3:
                view = new LocationCell(this.mContext);
                break;
            case 4:
                view = new LocationLoadingCell(this.mContext);
                break;
            case 5:
                view = new LocationPoweredCell(this.mContext);
                break;
            case 6:
                View cell = new SendLocationCell(this.mContext, true);
                cell.setDialogId(this.dialogId);
                view = cell;
                break;
            default:
                view = new SharingLiveLocationCell(this.mContext, true);
                break;
        }
        return new Holder(view);
    }

    public void setPulledUp() {
        if (!this.pulledUp) {
            this.pulledUp = true;
            AndroidUtilities.runOnUIThread(new C20281());
        }
    }

    public boolean isPulledUp() {
        return this.pulledUp;
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 0:
                ((EmptyCell) holder.itemView).setHeight(this.overScrollHeight);
                return;
            case 1:
                this.sendLocationCell = (SendLocationCell) holder.itemView;
                updateCell();
                return;
            case 2:
                if (this.currentMessageObject != null) {
                    ((GraySectionCell) holder.itemView).setText(LocaleController.getString("LiveLocations", R.string.LiveLocations));
                    return;
                } else if (this.pulledUp) {
                    ((GraySectionCell) holder.itemView).setText(LocaleController.getString("NearbyPlaces", R.string.NearbyPlaces));
                    return;
                } else {
                    ((GraySectionCell) holder.itemView).setText(LocaleController.getString("ShowNearbyPlaces", R.string.ShowNearbyPlaces));
                    return;
                }
            case 3:
                if (this.liveLocationType == 0) {
                    ((LocationCell) holder.itemView).setLocation((TLRPC$TL_messageMediaVenue) this.places.get(position - 3), (String) this.iconUrls.get(position - 3), true);
                    return;
                } else {
                    ((LocationCell) holder.itemView).setLocation((TLRPC$TL_messageMediaVenue) this.places.get(position - 4), (String) this.iconUrls.get(position - 4), true);
                    return;
                }
            case 4:
                ((LocationLoadingCell) holder.itemView).setLoading(this.searching);
                return;
            case 6:
                ((SendLocationCell) holder.itemView).setHasLocation(this.gpsLocation != null);
                return;
            case 7:
                if (this.currentMessageObject == null || position != 1) {
                    ((SharingLiveLocationCell) holder.itemView).setDialog((LiveLocation) this.currentLiveLocations.get(position - (this.currentMessageObject != null ? 4 : 2)), this.gpsLocation);
                    return;
                } else {
                    ((SharingLiveLocationCell) holder.itemView).setDialog(this.currentMessageObject, this.gpsLocation);
                    return;
                }
            default:
                return;
        }
    }

    public Object getItem(int i) {
        if (this.currentMessageObject != null) {
            if (i == 1) {
                return this.currentMessageObject;
            }
            if (i <= 3 || i >= this.places.size() + 3) {
                return null;
            }
            return this.currentLiveLocations.get(i - 4);
        } else if (this.liveLocationType == 2) {
            if (i >= 2) {
                return this.currentLiveLocations.get(i - 2);
            }
            return null;
        } else if (this.liveLocationType == 1) {
            if (i <= 3 || i >= this.places.size() + 3) {
                return null;
            }
            return this.places.get(i - 4);
        } else if (i <= 2 || i >= this.places.size() + 2) {
            return null;
        } else {
            return this.places.get(i - 3);
        }
    }

    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        }
        if (this.currentMessageObject != null) {
            if (position == 2) {
                return 2;
            }
            if (position != 3) {
                return 7;
            }
            this.shareLiveLocationPotistion = position;
            return 6;
        } else if (this.liveLocationType != 2) {
            if (this.liveLocationType == 1) {
                if (position == 1) {
                    return 1;
                }
                if (position == 2) {
                    this.shareLiveLocationPotistion = position;
                    return 6;
                } else if (position == 3) {
                    return 2;
                } else {
                    if (this.searching || (!this.searching && this.places.isEmpty())) {
                        return 4;
                    }
                    if (position == this.places.size() + 4) {
                        return 5;
                    }
                }
            } else if (position == 1) {
                return 1;
            } else {
                if (position == 2) {
                    return 2;
                }
                if (this.searching || (!this.searching && this.places.isEmpty())) {
                    return 4;
                }
                if (position == this.places.size() + 3) {
                    return 5;
                }
            }
            return 3;
        } else if (position != 1) {
            return 7;
        } else {
            this.shareLiveLocationPotistion = position;
            return 6;
        }
    }

    public boolean isEnabled(ViewHolder holder) {
        int viewType = holder.getItemViewType();
        if (viewType == 6) {
            if (LocationController.getInstance().getSharingLocationInfo(this.dialogId) == null && this.gpsLocation == null) {
                return false;
            }
            return true;
        } else if (viewType == 1 || viewType == 3 || viewType == 7) {
            return true;
        } else {
            return false;
        }
    }
}

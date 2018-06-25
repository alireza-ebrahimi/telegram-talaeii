package org.telegram.ui.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.HashMap;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.ContactsController.Contact;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_contact;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.Cells.DividerCell;
import org.telegram.ui.Cells.GraySectionCell;
import org.telegram.ui.Cells.LetterSectionCell;
import org.telegram.ui.Cells.TextCell;
import org.telegram.ui.Cells.UserCell;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.SectionsAdapter;

public class ContactsAdapter extends SectionsAdapter {
    private HashMap<Integer, ?> checkedMap;
    private HashMap<Integer, User> ignoreUsers;
    private boolean isAdmin;
    private Context mContext;
    private boolean needPhonebook;
    private int onlyUsers;
    private boolean scrolling;

    public ContactsAdapter(Context context, int i, boolean z, HashMap<Integer, User> hashMap, boolean z2) {
        this.mContext = context;
        this.onlyUsers = i;
        this.needPhonebook = z;
        this.ignoreUsers = hashMap;
        this.isAdmin = z2;
    }

    public int getCountForSection(int i) {
        HashMap hashMap = this.onlyUsers == 2 ? ContactsController.getInstance().usersMutualSectionsDict : ContactsController.getInstance().usersSectionsDict;
        ArrayList arrayList = this.onlyUsers == 2 ? ContactsController.getInstance().sortedUsersMutualSectionsArray : ContactsController.getInstance().sortedUsersSectionsArray;
        int size;
        if (this.onlyUsers == 0 || this.isAdmin) {
            if (i == 0) {
                return (this.needPhonebook || this.isAdmin) ? 2 : 4;
            } else {
                if (i - 1 < arrayList.size()) {
                    size = ((ArrayList) hashMap.get(arrayList.get(i - 1))).size();
                    return (i + -1 != arrayList.size() + -1 || this.needPhonebook) ? size + 1 : size;
                }
            }
        } else if (i < arrayList.size()) {
            size = ((ArrayList) hashMap.get(arrayList.get(i))).size();
            return (i != arrayList.size() + -1 || this.needPhonebook) ? size + 1 : size;
        }
        return this.needPhonebook ? ContactsController.getInstance().phoneBookContacts.size() : 0;
    }

    public Object getItem(int i, int i2) {
        HashMap hashMap = this.onlyUsers == 2 ? ContactsController.getInstance().usersMutualSectionsDict : ContactsController.getInstance().usersSectionsDict;
        ArrayList arrayList = this.onlyUsers == 2 ? ContactsController.getInstance().sortedUsersMutualSectionsArray : ContactsController.getInstance().sortedUsersSectionsArray;
        ArrayList arrayList2;
        if (this.onlyUsers != 0 && !this.isAdmin) {
            if (i < arrayList.size()) {
                arrayList2 = (ArrayList) hashMap.get(arrayList.get(i));
                if (i2 < arrayList2.size()) {
                    return MessagesController.getInstance().getUser(Integer.valueOf(((TLRPC$TL_contact) arrayList2.get(i2)).user_id));
                }
            }
            return null;
        } else if (i == 0) {
            return null;
        } else {
            if (i - 1 >= arrayList.size()) {
                return this.needPhonebook ? ContactsController.getInstance().phoneBookContacts.get(i2) : null;
            } else {
                arrayList2 = (ArrayList) hashMap.get(arrayList.get(i - 1));
                return i2 < arrayList2.size() ? MessagesController.getInstance().getUser(Integer.valueOf(((TLRPC$TL_contact) arrayList2.get(i2)).user_id)) : null;
            }
        }
    }

    public int getItemViewType(int i, int i2) {
        int i3 = 0;
        HashMap hashMap = this.onlyUsers == 2 ? ContactsController.getInstance().usersMutualSectionsDict : ContactsController.getInstance().usersSectionsDict;
        ArrayList arrayList = this.onlyUsers == 2 ? ContactsController.getInstance().sortedUsersMutualSectionsArray : ContactsController.getInstance().sortedUsersSectionsArray;
        if (this.onlyUsers != 0 && !this.isAdmin) {
            return i2 < ((ArrayList) hashMap.get(arrayList.get(i))).size() ? 0 : 3;
        } else {
            if (i == 0) {
                if (((this.needPhonebook || this.isAdmin) && i2 == 1) || i2 == 3) {
                    return 2;
                }
            } else if (i - 1 < arrayList.size()) {
                if (i2 >= ((ArrayList) hashMap.get(arrayList.get(i - 1))).size()) {
                    i3 = 3;
                }
                return i3;
            }
            return 1;
        }
    }

    public String getLetter(int i) {
        ArrayList arrayList = this.onlyUsers == 2 ? ContactsController.getInstance().sortedUsersMutualSectionsArray : ContactsController.getInstance().sortedUsersSectionsArray;
        int sectionForPosition = getSectionForPosition(i);
        if (sectionForPosition == -1) {
            sectionForPosition = arrayList.size() - 1;
        }
        return (sectionForPosition <= 0 || sectionForPosition > arrayList.size()) ? null : (String) arrayList.get(sectionForPosition - 1);
    }

    public int getPositionForScrollProgress(float f) {
        return (int) (((float) getItemCount()) * f);
    }

    public int getSectionCount() {
        int size = (this.onlyUsers == 2 ? ContactsController.getInstance().sortedUsersMutualSectionsArray : ContactsController.getInstance().sortedUsersSectionsArray).size();
        if (this.onlyUsers == 0) {
            size++;
        }
        if (this.isAdmin) {
            size++;
        }
        return this.needPhonebook ? size + 1 : size;
    }

    public View getSectionHeaderView(int i, View view) {
        HashMap hashMap;
        if (this.onlyUsers == 2) {
            hashMap = ContactsController.getInstance().usersMutualSectionsDict;
        } else {
            hashMap = ContactsController.getInstance().usersSectionsDict;
        }
        ArrayList arrayList = this.onlyUsers == 2 ? ContactsController.getInstance().sortedUsersMutualSectionsArray : ContactsController.getInstance().sortedUsersSectionsArray;
        View letterSectionCell = view == null ? new LetterSectionCell(this.mContext) : view;
        LetterSectionCell letterSectionCell2 = (LetterSectionCell) letterSectionCell;
        if (this.onlyUsers == 0 || this.isAdmin) {
            if (i == 0) {
                letterSectionCell2.setLetter(TtmlNode.ANONYMOUS_REGION_ID);
            } else if (i - 1 < arrayList.size()) {
                letterSectionCell2.setLetter((String) arrayList.get(i - 1));
            } else {
                letterSectionCell2.setLetter(TtmlNode.ANONYMOUS_REGION_ID);
            }
        } else if (i < arrayList.size()) {
            letterSectionCell2.setLetter((String) arrayList.get(i));
        } else {
            letterSectionCell2.setLetter(TtmlNode.ANONYMOUS_REGION_ID);
        }
        return letterSectionCell;
    }

    public boolean isEnabled(int i, int i2) {
        HashMap hashMap = this.onlyUsers == 2 ? ContactsController.getInstance().usersMutualSectionsDict : ContactsController.getInstance().usersSectionsDict;
        ArrayList arrayList = this.onlyUsers == 2 ? ContactsController.getInstance().sortedUsersMutualSectionsArray : ContactsController.getInstance().sortedUsersSectionsArray;
        if (this.onlyUsers == 0 || this.isAdmin) {
            return i == 0 ? (this.needPhonebook || this.isAdmin) ? i2 != 1 : i2 != 3 : i + -1 >= arrayList.size() || i2 < ((ArrayList) hashMap.get(arrayList.get(i - 1))).size();
        } else {
            return i2 < ((ArrayList) hashMap.get(arrayList.get(i))).size();
        }
    }

    public void onBindViewHolder(int i, int i2, ViewHolder viewHolder) {
        boolean z = true;
        switch (viewHolder.getItemViewType()) {
            case 0:
                UserCell userCell = (UserCell) viewHolder.itemView;
                HashMap hashMap = this.onlyUsers == 2 ? ContactsController.getInstance().usersMutualSectionsDict : ContactsController.getInstance().usersSectionsDict;
                ArrayList arrayList = this.onlyUsers == 2 ? ContactsController.getInstance().sortedUsersMutualSectionsArray : ContactsController.getInstance().sortedUsersSectionsArray;
                int i3 = (this.onlyUsers == 0 || this.isAdmin) ? 1 : 0;
                TLObject user = MessagesController.getInstance().getUser(Integer.valueOf(((TLRPC$TL_contact) ((ArrayList) hashMap.get(arrayList.get(i - i3))).get(i2)).user_id));
                userCell.setData(user, null, null, 0);
                if (this.checkedMap != null) {
                    boolean containsKey = this.checkedMap.containsKey(Integer.valueOf(user.id));
                    if (this.scrolling) {
                        z = false;
                    }
                    userCell.setChecked(containsKey, z);
                }
                if (this.ignoreUsers != null) {
                    if (this.ignoreUsers.containsKey(Integer.valueOf(user.id))) {
                        userCell.setAlpha(0.5f);
                    } else {
                        userCell.setAlpha(1.0f);
                    }
                }
                userCell.setMutual(user.mutual_contact);
                return;
            case 1:
                TextCell textCell = (TextCell) viewHolder.itemView;
                if (i != 0) {
                    Contact contact = (Contact) ContactsController.getInstance().phoneBookContacts.get(i2);
                    if (contact.first_name != null && contact.last_name != null) {
                        textCell.setText(contact.first_name + " " + contact.last_name);
                        return;
                    } else if (contact.first_name == null || contact.last_name != null) {
                        textCell.setText(contact.last_name);
                        return;
                    } else {
                        textCell.setText(contact.first_name);
                        return;
                    }
                } else if (this.needPhonebook) {
                    textCell.setTextAndIcon(LocaleController.getString("InviteFriends", R.string.InviteFriends), R.drawable.menu_invite);
                    return;
                } else if (this.isAdmin) {
                    textCell.setTextAndIcon(LocaleController.getString("InviteToGroupByLink", R.string.InviteToGroupByLink), R.drawable.menu_invite);
                    return;
                } else if (i2 == 0) {
                    textCell.setTextAndIcon(LocaleController.getString("NewGroup", R.string.NewGroup), R.drawable.menu_newgroup);
                    return;
                } else if (i2 == 1) {
                    textCell.setTextAndIcon(LocaleController.getString("NewSecretChat", R.string.NewSecretChat), R.drawable.menu_secret);
                    return;
                } else if (i2 == 2) {
                    textCell.setTextAndIcon(LocaleController.getString("NewChannel", R.string.NewChannel), R.drawable.menu_broadcast);
                    return;
                } else {
                    return;
                }
            default:
                return;
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View userCell;
        float f = 72.0f;
        switch (i) {
            case 0:
                userCell = new UserCell(this.mContext, 58, 1, false);
                break;
            case 1:
                userCell = new TextCell(this.mContext);
                break;
            case 2:
                userCell = new GraySectionCell(this.mContext);
                ((GraySectionCell) userCell).setText(LocaleController.getString("Contacts", R.string.Contacts).toUpperCase());
                break;
            default:
                View dividerCell = new DividerCell(this.mContext);
                int dp = AndroidUtilities.dp(LocaleController.isRTL ? 28.0f : 72.0f);
                if (!LocaleController.isRTL) {
                    f = 28.0f;
                }
                dividerCell.setPadding(dp, 0, AndroidUtilities.dp(f), 0);
                userCell = dividerCell;
                break;
        }
        return new Holder(userCell);
    }

    public void setCheckedMap(HashMap<Integer, ?> hashMap) {
        this.checkedMap = hashMap;
    }

    public void setIsScrolling(boolean z) {
        this.scrolling = z;
    }
}

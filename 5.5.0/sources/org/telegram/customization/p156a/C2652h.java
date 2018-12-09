package org.telegram.customization.p156a;

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

/* renamed from: org.telegram.customization.a.h */
public class C2652h extends SectionsAdapter {
    /* renamed from: a */
    private Context f8852a;
    /* renamed from: b */
    private int f8853b;
    /* renamed from: c */
    private boolean f8854c;
    /* renamed from: d */
    private HashMap<Integer, User> f8855d;
    /* renamed from: e */
    private HashMap<Integer, ?> f8856e;
    /* renamed from: f */
    private boolean f8857f;
    /* renamed from: g */
    private boolean f8858g;

    public C2652h(Context context, int i, boolean z, HashMap<Integer, User> hashMap, boolean z2) {
        this.f8852a = context;
        this.f8853b = i;
        this.f8854c = z;
        this.f8855d = hashMap;
        this.f8858g = z2;
    }

    public int getCountForSection(int i) {
        HashMap hashMap = this.f8853b == 2 ? ContactsController.getInstance().usersMutualSectionsDict1 : ContactsController.getInstance().usersSectionsDict1;
        ArrayList arrayList = this.f8853b == 2 ? ContactsController.getInstance().sortedUsersMutualSectionsArray1 : ContactsController.getInstance().sortedUsersSectionsArray1;
        int size;
        if (this.f8853b == 0 || this.f8858g) {
            if (i == 0) {
                return (this.f8854c || this.f8858g) ? 2 : 4;
            } else {
                if (i - 1 < arrayList.size()) {
                    size = ((ArrayList) hashMap.get(arrayList.get(i - 1))).size();
                    return (i + -1 != arrayList.size() + -1 || this.f8854c) ? size + 1 : size;
                }
            }
        } else if (i < arrayList.size()) {
            size = ((ArrayList) hashMap.get(arrayList.get(i))).size();
            return (i != arrayList.size() + -1 || this.f8854c) ? size + 1 : size;
        }
        return this.f8854c ? ContactsController.getInstance().phoneBookContacts.size() : 0;
    }

    public Object getItem(int i, int i2) {
        HashMap hashMap = this.f8853b == 2 ? ContactsController.getInstance().usersMutualSectionsDict1 : ContactsController.getInstance().usersSectionsDict1;
        ArrayList arrayList = this.f8853b == 2 ? ContactsController.getInstance().sortedUsersMutualSectionsArray1 : ContactsController.getInstance().sortedUsersSectionsArray1;
        ArrayList arrayList2;
        if (this.f8853b != 0 && !this.f8858g) {
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
                return this.f8854c ? ContactsController.getInstance().phoneBookContacts.get(i2) : null;
            } else {
                arrayList2 = (ArrayList) hashMap.get(arrayList.get(i - 1));
                return i2 < arrayList2.size() ? MessagesController.getInstance().getUser(Integer.valueOf(((TLRPC$TL_contact) arrayList2.get(i2)).user_id)) : null;
            }
        }
    }

    public int getItemViewType(int i, int i2) {
        int i3 = 0;
        HashMap hashMap = this.f8853b == 2 ? ContactsController.getInstance().usersMutualSectionsDict1 : ContactsController.getInstance().usersSectionsDict1;
        ArrayList arrayList = this.f8853b == 2 ? ContactsController.getInstance().sortedUsersMutualSectionsArray1 : ContactsController.getInstance().sortedUsersSectionsArray1;
        if (this.f8853b == 0 || this.f8858g) {
            if (i == 0) {
                if (((this.f8854c || this.f8858g) && i2 == 1) || i2 == 3) {
                    return 2;
                }
            } else if (i - 1 < arrayList.size()) {
                if (i2 >= ((ArrayList) hashMap.get(arrayList.get(i - 1))).size()) {
                    i3 = 3;
                }
                return i3;
            }
        } else if (hashMap.size() > i) {
            return i2 < ((ArrayList) hashMap.get(arrayList.get(i))).size() ? 0 : 3;
        }
        return 1;
    }

    public String getLetter(int i) {
        ArrayList arrayList = this.f8853b == 2 ? ContactsController.getInstance().sortedUsersMutualSectionsArray1 : ContactsController.getInstance().sortedUsersSectionsArray1;
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
        int size = (this.f8853b == 2 ? ContactsController.getInstance().sortedUsersMutualSectionsArray1 : ContactsController.getInstance().sortedUsersSectionsArray1).size();
        if (this.f8853b == 0) {
            size++;
        }
        if (this.f8858g) {
            size++;
        }
        return this.f8854c ? size + 1 : size;
    }

    public View getSectionHeaderView(int i, View view) {
        HashMap hashMap;
        if (this.f8853b == 2) {
            hashMap = ContactsController.getInstance().usersMutualSectionsDict1;
        } else {
            hashMap = ContactsController.getInstance().usersSectionsDict1;
        }
        ArrayList arrayList = this.f8853b == 2 ? ContactsController.getInstance().sortedUsersMutualSectionsArray1 : ContactsController.getInstance().sortedUsersSectionsArray1;
        View letterSectionCell = view == null ? new LetterSectionCell(this.f8852a) : view;
        LetterSectionCell letterSectionCell2 = (LetterSectionCell) letterSectionCell;
        if (this.f8853b == 0 || this.f8858g) {
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
        HashMap hashMap = this.f8853b == 2 ? ContactsController.getInstance().usersMutualSectionsDict1 : ContactsController.getInstance().usersSectionsDict1;
        ArrayList arrayList = this.f8853b == 2 ? ContactsController.getInstance().sortedUsersMutualSectionsArray1 : ContactsController.getInstance().sortedUsersSectionsArray1;
        if (this.f8853b == 0 || this.f8858g) {
            return i == 0 ? (this.f8854c || this.f8858g) ? i2 != 1 : i2 != 3 : i + -1 >= arrayList.size() || i2 < ((ArrayList) hashMap.get(arrayList.get(i - 1))).size();
        } else {
            if (hashMap.size() <= i) {
                return true;
            }
            return i2 < ((ArrayList) hashMap.get(arrayList.get(i))).size();
        }
    }

    public void onBindViewHolder(int i, int i2, ViewHolder viewHolder) {
        boolean z = true;
        switch (viewHolder.getItemViewType()) {
            case 0:
                UserCell userCell = (UserCell) viewHolder.itemView;
                HashMap hashMap = this.f8853b == 2 ? ContactsController.getInstance().usersMutualSectionsDict1 : ContactsController.getInstance().usersSectionsDict1;
                ArrayList arrayList = this.f8853b == 2 ? ContactsController.getInstance().sortedUsersMutualSectionsArray1 : ContactsController.getInstance().sortedUsersSectionsArray1;
                int i3 = (this.f8853b == 0 || this.f8858g) ? 1 : 0;
                TLObject user = MessagesController.getInstance().getUser(Integer.valueOf(((TLRPC$TL_contact) ((ArrayList) hashMap.get(arrayList.get(i - i3))).get(i2)).user_id));
                userCell.setData(user, null, null, 0);
                if (this.f8856e != null) {
                    boolean containsKey = this.f8856e.containsKey(Integer.valueOf(user.id));
                    if (this.f8857f) {
                        z = false;
                    }
                    userCell.setChecked(containsKey, z);
                }
                if (this.f8855d == null) {
                    return;
                }
                if (this.f8855d.containsKey(Integer.valueOf(user.id))) {
                    userCell.setAlpha(0.5f);
                    return;
                } else {
                    userCell.setAlpha(1.0f);
                    return;
                }
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
                } else if (!this.f8854c) {
                    if (this.f8858g) {
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
                userCell = new UserCell(this.f8852a, 58, 1, false);
                break;
            case 1:
                userCell = new TextCell(this.f8852a);
                break;
            case 2:
                userCell = new GraySectionCell(this.f8852a);
                ((GraySectionCell) userCell).setText(LocaleController.getString("Contacts", R.string.Contacts).toUpperCase());
                break;
            default:
                View dividerCell = new DividerCell(this.f8852a);
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
}

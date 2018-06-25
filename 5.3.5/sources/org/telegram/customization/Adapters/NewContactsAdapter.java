package org.telegram.customization.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.HashMap;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.ContactsController$Contact;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.TLRPC$TL_contact;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.Cells.DividerCell;
import org.telegram.ui.Cells.GraySectionCell;
import org.telegram.ui.Cells.LetterSectionCell;
import org.telegram.ui.Cells.TextCell;
import org.telegram.ui.Cells.UserCell;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.SectionsAdapter;

public class NewContactsAdapter extends SectionsAdapter {
    private HashMap<Integer, ?> checkedMap;
    private HashMap<Integer, User> ignoreUsers;
    private boolean isAdmin;
    private Context mContext;
    private boolean needPhonebook;
    private int onlyUsers;
    private boolean scrolling;

    public NewContactsAdapter(Context context, int onlyUsersType, boolean arg2, HashMap<Integer, User> arg3, boolean arg4) {
        this.mContext = context;
        this.onlyUsers = onlyUsersType;
        this.needPhonebook = arg2;
        this.ignoreUsers = arg3;
        this.isAdmin = arg4;
    }

    public void setCheckedMap(HashMap<Integer, ?> map) {
        this.checkedMap = map;
    }

    public void setIsScrolling(boolean value) {
        this.scrolling = value;
    }

    public Object getItem(int section, int position) {
        ArrayList<String> sortedUsersSectionsArray;
        HashMap<String, ArrayList<TLRPC$TL_contact>> usersSectionsDict = this.onlyUsers == 2 ? ContactsController.getInstance().usersMutualSectionsDict1 : ContactsController.getInstance().usersSectionsDict1;
        if (this.onlyUsers == 2) {
            sortedUsersSectionsArray = ContactsController.getInstance().sortedUsersMutualSectionsArray1;
        } else {
            sortedUsersSectionsArray = ContactsController.getInstance().sortedUsersSectionsArray1;
        }
        ArrayList<TLRPC$TL_contact> arr;
        if (this.onlyUsers == 0 || this.isAdmin) {
            if (section == 0) {
                return null;
            }
            if (section - 1 < sortedUsersSectionsArray.size()) {
                arr = (ArrayList) usersSectionsDict.get(sortedUsersSectionsArray.get(section - 1));
                if (position < arr.size()) {
                    return MessagesController.getInstance().getUser(Integer.valueOf(((TLRPC$TL_contact) arr.get(position)).user_id));
                }
                return null;
            } else if (this.needPhonebook) {
                return ContactsController.getInstance().phoneBookContacts.get(position);
            } else {
                return null;
            }
        } else if (section >= sortedUsersSectionsArray.size()) {
            return null;
        } else {
            arr = (ArrayList) usersSectionsDict.get(sortedUsersSectionsArray.get(section));
            if (position < arr.size()) {
                return MessagesController.getInstance().getUser(Integer.valueOf(((TLRPC$TL_contact) arr.get(position)).user_id));
            }
            return null;
        }
    }

    public boolean isEnabled(int section, int row) {
        HashMap<String, ArrayList<TLRPC$TL_contact>> usersSectionsDict = this.onlyUsers == 2 ? ContactsController.getInstance().usersMutualSectionsDict1 : ContactsController.getInstance().usersSectionsDict1;
        ArrayList<String> sortedUsersSectionsArray = this.onlyUsers == 2 ? ContactsController.getInstance().sortedUsersMutualSectionsArray1 : ContactsController.getInstance().sortedUsersSectionsArray1;
        if (this.onlyUsers == 0 || this.isAdmin) {
            if (section == 0) {
                if (this.needPhonebook || this.isAdmin) {
                    if (row == 1) {
                        return false;
                    }
                    return true;
                } else if (row == 3) {
                    return false;
                } else {
                    return true;
                }
            } else if (section - 1 >= sortedUsersSectionsArray.size() || row < ((ArrayList) usersSectionsDict.get(sortedUsersSectionsArray.get(section - 1))).size()) {
                return true;
            } else {
                return false;
            }
        } else if (usersSectionsDict.size() <= section || row < ((ArrayList) usersSectionsDict.get(sortedUsersSectionsArray.get(section))).size()) {
            return true;
        } else {
            return false;
        }
    }

    public int getSectionCount() {
        int count = (this.onlyUsers == 2 ? ContactsController.getInstance().sortedUsersMutualSectionsArray1 : ContactsController.getInstance().sortedUsersSectionsArray1).size();
        if (this.onlyUsers == 0) {
            count++;
        }
        if (this.isAdmin) {
            count++;
        }
        if (this.needPhonebook) {
            return count + 1;
        }
        return count;
    }

    public int getCountForSection(int section) {
        ArrayList<String> sortedUsersSectionsArray;
        HashMap<String, ArrayList<TLRPC$TL_contact>> usersSectionsDict = this.onlyUsers == 2 ? ContactsController.getInstance().usersMutualSectionsDict1 : ContactsController.getInstance().usersSectionsDict1;
        if (this.onlyUsers == 2) {
            sortedUsersSectionsArray = ContactsController.getInstance().sortedUsersMutualSectionsArray1;
        } else {
            sortedUsersSectionsArray = ContactsController.getInstance().sortedUsersSectionsArray1;
        }
        int count;
        if (this.onlyUsers == 0 || this.isAdmin) {
            if (section == 0) {
                if (this.needPhonebook || this.isAdmin) {
                    return 2;
                }
                return 4;
            } else if (section - 1 < sortedUsersSectionsArray.size()) {
                count = ((ArrayList) usersSectionsDict.get(sortedUsersSectionsArray.get(section - 1))).size();
                if (section - 1 != sortedUsersSectionsArray.size() - 1 || this.needPhonebook) {
                    return count + 1;
                }
                return count;
            }
        } else if (section < sortedUsersSectionsArray.size()) {
            count = ((ArrayList) usersSectionsDict.get(sortedUsersSectionsArray.get(section))).size();
            if (section != sortedUsersSectionsArray.size() - 1 || this.needPhonebook) {
                return count + 1;
            }
            return count;
        }
        if (this.needPhonebook) {
            return ContactsController.getInstance().phoneBookContacts.size();
        }
        return 0;
    }

    public View getSectionHeaderView(int section, View view) {
        if (this.onlyUsers == 2) {
            HashMap<String, ArrayList<TLRPC$TL_contact>> usersSectionsDict = ContactsController.getInstance().usersMutualSectionsDict1;
        } else {
            HashMap hashMap = ContactsController.getInstance().usersSectionsDict1;
        }
        ArrayList<String> sortedUsersSectionsArray = this.onlyUsers == 2 ? ContactsController.getInstance().sortedUsersMutualSectionsArray1 : ContactsController.getInstance().sortedUsersSectionsArray1;
        if (view == null) {
            view = new LetterSectionCell(this.mContext);
        }
        LetterSectionCell cell = (LetterSectionCell) view;
        if (this.onlyUsers == 0 || this.isAdmin) {
            if (section == 0) {
                cell.setLetter("");
            } else if (section - 1 < sortedUsersSectionsArray.size()) {
                cell.setLetter((String) sortedUsersSectionsArray.get(section - 1));
            } else {
                cell.setLetter("");
            }
        } else if (section < sortedUsersSectionsArray.size()) {
            cell.setLetter((String) sortedUsersSectionsArray.get(section));
        } else {
            cell.setLetter("");
        }
        return view;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        float f = 72.0f;
        switch (viewType) {
            case 0:
                view = new UserCell(this.mContext, 58, 1, false);
                break;
            case 1:
                view = new TextCell(this.mContext);
                break;
            case 2:
                view = new GraySectionCell(this.mContext);
                ((GraySectionCell) view).setText(LocaleController.getString("Contacts", R.string.Contacts).toUpperCase());
                break;
            default:
                float f2;
                view = new DividerCell(this.mContext);
                if (LocaleController.isRTL) {
                    f2 = 28.0f;
                } else {
                    f2 = 72.0f;
                }
                int dp = AndroidUtilities.dp(f2);
                if (!LocaleController.isRTL) {
                    f = 28.0f;
                }
                view.setPadding(dp, 0, AndroidUtilities.dp(f), 0);
                break;
        }
        return new Holder(view);
    }

    public void onBindViewHolder(int section, int position, ViewHolder holder) {
        boolean z = true;
        switch (holder.getItemViewType()) {
            case 0:
                int i;
                UserCell userCell = holder.itemView;
                HashMap<String, ArrayList<TLRPC$TL_contact>> usersSectionsDict = this.onlyUsers == 2 ? ContactsController.getInstance().usersMutualSectionsDict1 : ContactsController.getInstance().usersSectionsDict1;
                ArrayList<String> sortedUsersSectionsArray = this.onlyUsers == 2 ? ContactsController.getInstance().sortedUsersMutualSectionsArray1 : ContactsController.getInstance().sortedUsersSectionsArray1;
                if (this.onlyUsers == 0 || this.isAdmin) {
                    i = 1;
                } else {
                    i = 0;
                }
                User user = MessagesController.getInstance().getUser(Integer.valueOf(((TLRPC$TL_contact) ((ArrayList) usersSectionsDict.get(sortedUsersSectionsArray.get(section - i))).get(position)).user_id));
                userCell.setData(user, null, null, 0);
                if (this.checkedMap != null) {
                    boolean containsKey = this.checkedMap.containsKey(Integer.valueOf(user.id));
                    if (this.scrolling) {
                        z = false;
                    }
                    userCell.setChecked(containsKey, z);
                }
                if (this.ignoreUsers == null) {
                    return;
                }
                if (this.ignoreUsers.containsKey(Integer.valueOf(user.id))) {
                    userCell.setAlpha(0.5f);
                    return;
                } else {
                    userCell.setAlpha(1.0f);
                    return;
                }
            case 1:
                TextCell textCell = holder.itemView;
                if (section != 0) {
                    ContactsController$Contact contact = (ContactsController$Contact) ContactsController.getInstance().phoneBookContacts.get(position);
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
                } else if (!this.needPhonebook) {
                    if (this.isAdmin) {
                        textCell.setTextAndIcon(LocaleController.getString("InviteToGroupByLink", R.string.InviteToGroupByLink), R.drawable.menu_invite);
                        return;
                    } else if (position == 0) {
                        textCell.setTextAndIcon(LocaleController.getString("NewGroup", R.string.NewGroup), R.drawable.menu_newgroup);
                        return;
                    } else if (position == 1) {
                        textCell.setTextAndIcon(LocaleController.getString("NewSecretChat", R.string.NewSecretChat), R.drawable.menu_secret);
                        return;
                    } else if (position == 2) {
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

    public int getItemViewType(int section, int position) {
        HashMap<String, ArrayList<TLRPC$TL_contact>> usersSectionsDict = this.onlyUsers == 2 ? ContactsController.getInstance().usersMutualSectionsDict1 : ContactsController.getInstance().usersSectionsDict1;
        ArrayList<String> sortedUsersSectionsArray = this.onlyUsers == 2 ? ContactsController.getInstance().sortedUsersMutualSectionsArray1 : ContactsController.getInstance().sortedUsersSectionsArray1;
        if (this.onlyUsers == 0 || this.isAdmin) {
            if (section == 0) {
                if (((this.needPhonebook || this.isAdmin) && position == 1) || position == 3) {
                    return 2;
                }
            } else if (section - 1 < sortedUsersSectionsArray.size()) {
                if (position >= ((ArrayList) usersSectionsDict.get(sortedUsersSectionsArray.get(section - 1))).size()) {
                    return 3;
                }
                return 0;
            }
        } else if (usersSectionsDict.size() > section) {
            if (position < ((ArrayList) usersSectionsDict.get(sortedUsersSectionsArray.get(section))).size()) {
                return 0;
            }
            return 3;
        }
        return 1;
    }

    public String getLetter(int position) {
        ArrayList<String> sortedUsersSectionsArray = this.onlyUsers == 2 ? ContactsController.getInstance().sortedUsersMutualSectionsArray1 : ContactsController.getInstance().sortedUsersSectionsArray1;
        int section = getSectionForPosition(position);
        if (section == -1) {
            section = sortedUsersSectionsArray.size() - 1;
        }
        if (section <= 0 || section > sortedUsersSectionsArray.size()) {
            return null;
        }
        return (String) sortedUsersSectionsArray.get(section - 1);
    }

    public int getPositionForScrollProgress(float progress) {
        return (int) (((float) getItemCount()) * progress);
    }
}

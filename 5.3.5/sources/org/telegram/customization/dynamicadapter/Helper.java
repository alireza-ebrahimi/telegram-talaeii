package org.telegram.customization.dynamicadapter;

import android.app.Activity;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.telegram.customization.dynamicadapter.annotations.ViewHolderType;
import org.telegram.customization.dynamicadapter.data.ExtraData;
import org.telegram.customization.dynamicadapter.data.ObjBase;
import org.telegram.customization.dynamicadapter.data.SlsTag;
import org.telegram.customization.dynamicadapter.viewholder.ExceptionHolder;
import org.telegram.customization.dynamicadapter.viewholder.HolderBase;
import org.telegram.customization.dynamicadapter.viewholder.MoreTagHolder;
import org.telegram.customization.dynamicadapter.viewholder.SlsImportantTagsHolder;
import org.telegram.customization.dynamicadapter.viewholder.SlsLoaderHolder;
import org.telegram.customization.dynamicadapter.viewholder.SlsMediaTypeHolder;
import org.telegram.customization.dynamicadapter.viewholder.SlsMessageHolder;
import org.telegram.customization.dynamicadapter.viewholder.SlsNoResultHolder;
import org.telegram.customization.dynamicadapter.viewholder.SlsSearchBoxHolder;
import org.telegram.customization.dynamicadapter.viewholder.SlsSearchImgClickableHolder;
import org.telegram.customization.dynamicadapter.viewholder.SlsStatisticsHolder;
import org.telegram.customization.dynamicadapter.viewholder.SlsTagHolder;
import org.telegram.customization.dynamicadapter.viewholder.SlsTagsCarouselHolder;
import org.telegram.customization.dynamicadapter.viewholder.SlsThreeTileDefHolder;
import org.telegram.customization.dynamicadapter.viewholder.SlsThreeTileSameHolder;
import org.telegram.customization.dynamicadapter.viewholder.SlsTitleHolder;

public class Helper {
    public static final int HOLDER_TYPE_CATEGORY = 101;
    public static final int HOLDER_TYPE_EXCEPTION = 111;
    public static final int HOLDER_TYPE_IMPORTANT_TAGS = 103;
    public static final int HOLDER_TYPE_LOADING = 107;
    public static final int HOLDER_TYPE_MEDIA_TYPE = 109;
    public static final int HOLDER_TYPE_MESSAGE = 100;
    public static final int HOLDER_TYPE_MORE = 108;
    public static final int HOLDER_TYPE_NO_RESULT = 110;
    public static final int HOLDER_TYPE_SEARCH = 105;
    public static final int HOLDER_TYPE_SEARCH_IMG_CLICKABLE = 112;
    public static final int HOLDER_TYPE_STATISTICS = 104;
    public static final int HOLDER_TYPE_TAG_COLLECTION = 102;
    public static final int HOLDER_TYPE_TITLE = 106;
    private static HashMap<Integer, Class<? extends HolderBase>> viewHolders;

    private static void addViewHolder(Class<? extends HolderBase> cls) {
        try {
            viewHolders.remove(Integer.valueOf(((ViewHolderType) cls.getAnnotation(ViewHolderType.class)).type()));
        } catch (Exception e) {
        }
        try {
            viewHolders.put(Integer.valueOf(((ViewHolderType) cls.getAnnotation(ViewHolderType.class)).type()), cls);
        } catch (Exception e2) {
        }
    }

    public static HolderBase createViewHolder(Activity activity, ViewGroup viewGroup, int type, DynamicAdapter adapter, ExtraData extraData) {
        try {
            return (HolderBase) ((Class) getViewHolders().get(Integer.valueOf(type))).getDeclaredConstructor(new Class[]{Activity.class, ViewGroup.class, DynamicAdapter.class, ExtraData.class}).newInstance(new Object[]{activity, viewGroup, adapter, extraData});
        } catch (Exception e) {
            e.printStackTrace();
            try {
                return (HolderBase) ((Class) getViewHolders().get(Integer.valueOf(111))).getDeclaredConstructor(new Class[]{Activity.class, ViewGroup.class, DynamicAdapter.class, ExtraData.class}).newInstance(new Object[]{activity, viewGroup, adapter, extraData});
            } catch (Exception e2) {
                e2.printStackTrace();
                return null;
            }
        }
    }

    public static Class<? extends ObjBase> getModel(int type) {
        try {
            return ((ViewHolderType) ((Class) getViewHolders().get(Integer.valueOf(type))).getAnnotation(ViewHolderType.class)).model();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static HashMap<Integer, Class<? extends HolderBase>> getViewHolders() {
        if (viewHolders == null || viewHolders.size() == 0) {
            viewHolders = new HashMap();
            addViewHolder(SlsTagHolder.class);
            addViewHolder(SlsMessageHolder.class);
            addViewHolder(SlsThreeTileSameHolder.class);
            addViewHolder(SlsThreeTileDefHolder.class);
            addViewHolder(SlsTitleHolder.class);
            addViewHolder(SlsStatisticsHolder.class);
            addViewHolder(SlsSearchBoxHolder.class);
            addViewHolder(SlsImportantTagsHolder.class);
            addViewHolder(SlsLoaderHolder.class);
            addViewHolder(SlsTagsCarouselHolder.class);
            addViewHolder(MoreTagHolder.class);
            addViewHolder(SlsMediaTypeHolder.class);
            addViewHolder(SlsNoResultHolder.class);
            addViewHolder(ExceptionHolder.class);
            addViewHolder(SlsSearchImgClickableHolder.class);
        }
        return viewHolders;
    }

    public static ObjBase getStatisticsItem(ArrayList<ObjBase> messages) {
        Iterator it = messages.iterator();
        while (it.hasNext()) {
            ObjBase item = (ObjBase) it.next();
            if (item.getType() == 104) {
                return item;
            }
        }
        return null;
    }

    public static ObjBase getSearchItem(ArrayList<ObjBase> messages) {
        Iterator it = messages.iterator();
        while (it.hasNext()) {
            ObjBase item = (ObjBase) it.next();
            if (item.getType() == 105) {
                return item;
            }
        }
        return null;
    }

    public static ObjBase getImportantItem(ArrayList<ObjBase> messages) {
        Iterator it = messages.iterator();
        while (it.hasNext()) {
            ObjBase item = (ObjBase) it.next();
            if (item.getType() == 103) {
                return item;
            }
        }
        return null;
    }

    public static ObjBase getSuggestedSearchItem(ArrayList<ObjBase> messages) {
        Iterator it = messages.iterator();
        while (it.hasNext()) {
            ObjBase item = (ObjBase) it.next();
            if (item.getType() == 112) {
                return item;
            }
        }
        return null;
    }

    public static ArrayList<SlsTag> getFullChannelItems(ArrayList<ObjBase> messages) {
        ArrayList<SlsTag> tags = new ArrayList();
        int counter = 0;
        Iterator it = messages.iterator();
        while (it.hasNext()) {
            ObjBase item = (ObjBase) it.next();
            if (item.getType() == 101) {
                tags.add((SlsTag) item);
                counter++;
            }
        }
        return tags;
    }

    public static ArrayList<SlsTag> getLimitedChannelItems(ArrayList<SlsTag> tags, int limit) {
        ArrayList<SlsTag> ans = new ArrayList();
        int counter = 0;
        Iterator it = tags.iterator();
        while (it.hasNext()) {
            ans.add((SlsTag) it.next());
            counter++;
            if (counter == limit) {
                break;
            }
        }
        return ans;
    }

    public static ArrayList<SlsTag> getChannelItemsAfterIndex(ArrayList<ObjBase> messages, int index) {
        ArrayList<SlsTag> tags = new ArrayList();
        int counter = 0;
        Iterator it = messages.iterator();
        while (it.hasNext()) {
            ObjBase item = (ObjBase) it.next();
            if (item.getType() == 101) {
                counter++;
                if (counter >= index) {
                    tags.add((SlsTag) item);
                }
            }
        }
        return tags;
    }

    public static ArrayList<ObjBase> getMessageItems(ArrayList<ObjBase> messages) {
        ArrayList<ObjBase> ans = new ArrayList();
        Iterator it = messages.iterator();
        while (it.hasNext()) {
            ObjBase item = (ObjBase) it.next();
            if (item.getType() == 100) {
                ans.add(item);
            }
        }
        return ans;
    }
}

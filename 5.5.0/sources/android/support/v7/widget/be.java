package android.support.v7.widget;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.support.v4.content.C0235a;
import android.support.v4.widget.C0728w;
import android.support.v7.p025a.C0748a.C0738a;
import android.support.v7.p025a.C0748a.C0743f;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.analytics.FirebaseAnalytics.C1796a;
import com.google.firebase.analytics.FirebaseAnalytics.C1797b;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.WeakHashMap;

class be extends C0728w implements OnClickListener {
    /* renamed from: j */
    private final SearchManager f3010j = ((SearchManager) this.d.getSystemService(C1796a.SEARCH));
    /* renamed from: k */
    private final SearchView f3011k;
    /* renamed from: l */
    private final SearchableInfo f3012l;
    /* renamed from: m */
    private final Context f3013m;
    /* renamed from: n */
    private final WeakHashMap<String, ConstantState> f3014n;
    /* renamed from: o */
    private final int f3015o;
    /* renamed from: p */
    private boolean f3016p = false;
    /* renamed from: q */
    private int f3017q = 1;
    /* renamed from: r */
    private ColorStateList f3018r;
    /* renamed from: s */
    private int f3019s = -1;
    /* renamed from: t */
    private int f3020t = -1;
    /* renamed from: u */
    private int f3021u = -1;
    /* renamed from: v */
    private int f3022v = -1;
    /* renamed from: w */
    private int f3023w = -1;
    /* renamed from: x */
    private int f3024x = -1;

    /* renamed from: android.support.v7.widget.be$a */
    private static final class C1039a {
        /* renamed from: a */
        public final TextView f3005a;
        /* renamed from: b */
        public final TextView f3006b;
        /* renamed from: c */
        public final ImageView f3007c;
        /* renamed from: d */
        public final ImageView f3008d;
        /* renamed from: e */
        public final ImageView f3009e;

        public C1039a(View view) {
            this.f3005a = (TextView) view.findViewById(16908308);
            this.f3006b = (TextView) view.findViewById(16908309);
            this.f3007c = (ImageView) view.findViewById(16908295);
            this.f3008d = (ImageView) view.findViewById(16908296);
            this.f3009e = (ImageView) view.findViewById(C0743f.edit_query);
        }
    }

    public be(Context context, SearchView searchView, SearchableInfo searchableInfo, WeakHashMap<String, ConstantState> weakHashMap) {
        super(context, searchView.getSuggestionRowLayout(), null, true);
        this.f3011k = searchView;
        this.f3012l = searchableInfo;
        this.f3015o = searchView.getSuggestionCommitIconResId();
        this.f3013m = context;
        this.f3014n = weakHashMap;
    }

    /* renamed from: a */
    private Drawable m5619a(ComponentName componentName) {
        Object obj = null;
        String flattenToShortString = componentName.flattenToShortString();
        if (this.f3014n.containsKey(flattenToShortString)) {
            ConstantState constantState = (ConstantState) this.f3014n.get(flattenToShortString);
            return constantState == null ? null : constantState.newDrawable(this.f3013m.getResources());
        } else {
            Drawable b = m5626b(componentName);
            if (b != null) {
                obj = b.getConstantState();
            }
            this.f3014n.put(flattenToShortString, obj);
            return b;
        }
    }

    /* renamed from: a */
    private Drawable m5620a(String str) {
        Drawable b;
        if (str == null || str.length() == 0 || "0".equals(str)) {
            return null;
        }
        try {
            int parseInt = Integer.parseInt(str);
            String str2 = "android.resource://" + this.f3013m.getPackageName() + "/" + parseInt;
            b = m5628b(str2);
            if (b != null) {
                return b;
            }
            b = C0235a.m1066a(this.f3013m, parseInt);
            m5625a(str2, b);
            return b;
        } catch (NumberFormatException e) {
            b = m5628b(str);
            if (b != null) {
                return b;
            }
            b = m5627b(Uri.parse(str));
            m5625a(str, b);
            return b;
        } catch (NotFoundException e2) {
            Log.w("SuggestionsAdapter", "Icon resource not found: " + str);
            return null;
        }
    }

    /* renamed from: a */
    private static String m5621a(Cursor cursor, int i) {
        String str = null;
        if (i != -1) {
            try {
                str = cursor.getString(i);
            } catch (Throwable e) {
                Log.e("SuggestionsAdapter", "unexpected error retrieving valid column from cursor, did the remote process die?", e);
            }
        }
        return str;
    }

    /* renamed from: a */
    public static String m5622a(Cursor cursor, String str) {
        return m5621a(cursor, cursor.getColumnIndex(str));
    }

    /* renamed from: a */
    private void m5623a(ImageView imageView, Drawable drawable, int i) {
        imageView.setImageDrawable(drawable);
        if (drawable == null) {
            imageView.setVisibility(i);
            return;
        }
        imageView.setVisibility(0);
        drawable.setVisible(false, false);
        drawable.setVisible(true, false);
    }

    /* renamed from: a */
    private void m5624a(TextView textView, CharSequence charSequence) {
        textView.setText(charSequence);
        if (TextUtils.isEmpty(charSequence)) {
            textView.setVisibility(8);
        } else {
            textView.setVisibility(0);
        }
    }

    /* renamed from: a */
    private void m5625a(String str, Drawable drawable) {
        if (drawable != null) {
            this.f3014n.put(str, drawable.getConstantState());
        }
    }

    /* renamed from: b */
    private Drawable m5626b(ComponentName componentName) {
        PackageManager packageManager = this.d.getPackageManager();
        try {
            ActivityInfo activityInfo = packageManager.getActivityInfo(componentName, 128);
            int iconResource = activityInfo.getIconResource();
            if (iconResource == 0) {
                return null;
            }
            Drawable drawable = packageManager.getDrawable(componentName.getPackageName(), iconResource, activityInfo.applicationInfo);
            if (drawable != null) {
                return drawable;
            }
            Log.w("SuggestionsAdapter", "Invalid icon resource " + iconResource + " for " + componentName.flattenToShortString());
            return null;
        } catch (NameNotFoundException e) {
            Log.w("SuggestionsAdapter", e.toString());
            return null;
        }
    }

    /* renamed from: b */
    private Drawable m5627b(Uri uri) {
        InputStream openInputStream;
        try {
            if ("android.resource".equals(uri.getScheme())) {
                return m5636a(uri);
            }
            openInputStream = this.f3013m.getContentResolver().openInputStream(uri);
            if (openInputStream == null) {
                throw new FileNotFoundException("Failed to open " + uri);
            }
            Drawable createFromStream = Drawable.createFromStream(openInputStream, null);
            try {
                openInputStream.close();
                return createFromStream;
            } catch (Throwable e) {
                Log.e("SuggestionsAdapter", "Error closing icon stream for " + uri, e);
                return createFromStream;
            }
        } catch (NotFoundException e2) {
            throw new FileNotFoundException("Resource does not exist: " + uri);
        } catch (FileNotFoundException e3) {
            Log.w("SuggestionsAdapter", "Icon not found: " + uri + ", " + e3.getMessage());
            return null;
        } catch (Throwable th) {
            try {
                openInputStream.close();
            } catch (Throwable e4) {
                Log.e("SuggestionsAdapter", "Error closing icon stream for " + uri, e4);
            }
        }
    }

    /* renamed from: b */
    private Drawable m5628b(String str) {
        ConstantState constantState = (ConstantState) this.f3014n.get(str);
        return constantState == null ? null : constantState.newDrawable();
    }

    /* renamed from: b */
    private CharSequence m5629b(CharSequence charSequence) {
        if (this.f3018r == null) {
            TypedValue typedValue = new TypedValue();
            this.d.getTheme().resolveAttribute(C0738a.textColorSearchUrl, typedValue, true);
            this.f3018r = this.d.getResources().getColorStateList(typedValue.resourceId);
        }
        CharSequence spannableString = new SpannableString(charSequence);
        spannableString.setSpan(new TextAppearanceSpan(null, 0, 0, this.f3018r, null), 0, charSequence.length(), 33);
        return spannableString;
    }

    /* renamed from: d */
    private void m5630d(Cursor cursor) {
        Bundle extras = cursor != null ? cursor.getExtras() : null;
        if (extras != null && !extras.getBoolean("in_progress")) {
        }
    }

    /* renamed from: e */
    private Drawable m5631e(Cursor cursor) {
        if (this.f3022v == -1) {
            return null;
        }
        Drawable a = m5620a(cursor.getString(this.f3022v));
        return a == null ? m5633g(cursor) : a;
    }

    /* renamed from: f */
    private Drawable m5632f(Cursor cursor) {
        return this.f3023w == -1 ? null : m5620a(cursor.getString(this.f3023w));
    }

    /* renamed from: g */
    private Drawable m5633g(Cursor cursor) {
        Drawable a = m5619a(this.f3012l.getSearchActivity());
        return a != null ? a : this.d.getPackageManager().getDefaultActivityIcon();
    }

    /* renamed from: a */
    Cursor m5634a(SearchableInfo searchableInfo, String str, int i) {
        if (searchableInfo == null) {
            return null;
        }
        String suggestAuthority = searchableInfo.getSuggestAuthority();
        if (suggestAuthority == null) {
            return null;
        }
        String[] strArr;
        Builder fragment = new Builder().scheme(C1797b.CONTENT).authority(suggestAuthority).query(TtmlNode.ANONYMOUS_REGION_ID).fragment(TtmlNode.ANONYMOUS_REGION_ID);
        String suggestPath = searchableInfo.getSuggestPath();
        if (suggestPath != null) {
            fragment.appendEncodedPath(suggestPath);
        }
        fragment.appendPath("search_suggest_query");
        String suggestSelection = searchableInfo.getSuggestSelection();
        if (suggestSelection != null) {
            strArr = new String[]{str};
        } else {
            fragment.appendPath(str);
            strArr = null;
        }
        if (i > 0) {
            fragment.appendQueryParameter("limit", String.valueOf(i));
        }
        return this.d.getContentResolver().query(fragment.build(), null, suggestSelection, strArr, null);
    }

    /* renamed from: a */
    public Cursor mo571a(CharSequence charSequence) {
        String charSequence2 = charSequence == null ? TtmlNode.ANONYMOUS_REGION_ID : charSequence.toString();
        if (this.f3011k.getVisibility() != 0 || this.f3011k.getWindowVisibility() != 0) {
            return null;
        }
        try {
            Cursor a = m5634a(this.f3012l, charSequence2, 50);
            if (a != null) {
                a.getCount();
                return a;
            }
        } catch (Throwable e) {
            Log.w("SuggestionsAdapter", "Search suggestions query threw an exception.", e);
        }
        return null;
    }

    /* renamed from: a */
    Drawable m5636a(Uri uri) {
        String authority = uri.getAuthority();
        if (TextUtils.isEmpty(authority)) {
            throw new FileNotFoundException("No authority: " + uri);
        }
        try {
            Resources resourcesForApplication = this.d.getPackageManager().getResourcesForApplication(authority);
            List pathSegments = uri.getPathSegments();
            if (pathSegments == null) {
                throw new FileNotFoundException("No path: " + uri);
            }
            int size = pathSegments.size();
            if (size == 1) {
                try {
                    size = Integer.parseInt((String) pathSegments.get(0));
                } catch (NumberFormatException e) {
                    throw new FileNotFoundException("Single path segment is not a resource ID: " + uri);
                }
            } else if (size == 2) {
                size = resourcesForApplication.getIdentifier((String) pathSegments.get(1), (String) pathSegments.get(0), authority);
            } else {
                throw new FileNotFoundException("More than two path segments: " + uri);
            }
            if (size != 0) {
                return resourcesForApplication.getDrawable(size);
            }
            throw new FileNotFoundException("No resource found for: " + uri);
        } catch (NameNotFoundException e2) {
            throw new FileNotFoundException("No package found for authority: " + uri);
        }
    }

    /* renamed from: a */
    public View mo593a(Context context, Cursor cursor, ViewGroup viewGroup) {
        View a = super.mo593a(context, cursor, viewGroup);
        a.setTag(new C1039a(a));
        ((ImageView) a.findViewById(C0743f.edit_query)).setImageResource(this.f3015o);
        return a;
    }

    /* renamed from: a */
    public void m5638a(int i) {
        this.f3017q = i;
    }

    /* renamed from: a */
    public void mo572a(Cursor cursor) {
        if (this.f3016p) {
            Log.w("SuggestionsAdapter", "Tried to change cursor after adapter was closed.");
            if (cursor != null) {
                cursor.close();
                return;
            }
            return;
        }
        try {
            super.mo572a(cursor);
            if (cursor != null) {
                this.f3019s = cursor.getColumnIndex("suggest_text_1");
                this.f3020t = cursor.getColumnIndex("suggest_text_2");
                this.f3021u = cursor.getColumnIndex("suggest_text_2_url");
                this.f3022v = cursor.getColumnIndex("suggest_icon_1");
                this.f3023w = cursor.getColumnIndex("suggest_icon_2");
                this.f3024x = cursor.getColumnIndex("suggest_flags");
            }
        } catch (Throwable e) {
            Log.e("SuggestionsAdapter", "error changing cursor and caching columns", e);
        }
    }

    /* renamed from: a */
    public void mo960a(View view, Context context, Cursor cursor) {
        C1039a c1039a = (C1039a) view.getTag();
        int i = this.f3024x != -1 ? cursor.getInt(this.f3024x) : 0;
        if (c1039a.f3005a != null) {
            m5624a(c1039a.f3005a, m5621a(cursor, this.f3019s));
        }
        if (c1039a.f3006b != null) {
            CharSequence a = m5621a(cursor, this.f3021u);
            a = a != null ? m5629b(a) : m5621a(cursor, this.f3020t);
            if (TextUtils.isEmpty(a)) {
                if (c1039a.f3005a != null) {
                    c1039a.f3005a.setSingleLine(false);
                    c1039a.f3005a.setMaxLines(2);
                }
            } else if (c1039a.f3005a != null) {
                c1039a.f3005a.setSingleLine(true);
                c1039a.f3005a.setMaxLines(1);
            }
            m5624a(c1039a.f3006b, a);
        }
        if (c1039a.f3007c != null) {
            m5623a(c1039a.f3007c, m5631e(cursor), 4);
        }
        if (c1039a.f3008d != null) {
            m5623a(c1039a.f3008d, m5632f(cursor), 8);
        }
        if (this.f3017q == 2 || (this.f3017q == 1 && (i & 1) != 0)) {
            c1039a.f3009e.setVisibility(0);
            c1039a.f3009e.setTag(c1039a.f3005a.getText());
            c1039a.f3009e.setOnClickListener(this);
            return;
        }
        c1039a.f3009e.setVisibility(8);
    }

    /* renamed from: c */
    public CharSequence mo573c(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        String a = m5622a(cursor, "suggest_intent_query");
        if (a != null) {
            return a;
        }
        if (this.f3012l.shouldRewriteQueryFromData()) {
            a = m5622a(cursor, "suggest_intent_data");
            if (a != null) {
                return a;
            }
        }
        if (!this.f3012l.shouldRewriteQueryFromText()) {
            return null;
        }
        a = m5622a(cursor, "suggest_text_1");
        return a != null ? a : null;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        try {
            return super.getView(i, view, viewGroup);
        } catch (Throwable e) {
            Log.w("SuggestionsAdapter", "Search suggestions cursor threw exception.", e);
            View a = mo593a(this.d, this.c, viewGroup);
            if (a != null) {
                ((C1039a) a.getTag()).f3005a.setText(e.toString());
            }
            return a;
        }
    }

    public boolean hasStableIds() {
        return false;
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        m5630d(mo570a());
    }

    public void notifyDataSetInvalidated() {
        super.notifyDataSetInvalidated();
        m5630d(mo570a());
    }

    public void onClick(View view) {
        Object tag = view.getTag();
        if (tag instanceof CharSequence) {
            this.f3011k.m4998a((CharSequence) tag);
        }
    }
}

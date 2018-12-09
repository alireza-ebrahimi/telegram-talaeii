package utils.view.bottombar;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.support.v4.content.C0235a;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.xmlpull.v1.XmlPullParserException;
import utils.view.bottombar.BottomBarTab.Config;

class TabParser {
    /* renamed from: a */
    private final Context f10451a;
    /* renamed from: b */
    private final Config f10452b;
    /* renamed from: c */
    private final XmlResourceParser f10453c;
    /* renamed from: d */
    private ArrayList<BottomBarTab> f10454d = new ArrayList();
    /* renamed from: e */
    private BottomBarTab f10455e;

    private class TabParserException extends RuntimeException {
        /* renamed from: a */
        final /* synthetic */ TabParser f10450a;

        private TabParserException(TabParser tabParser) {
            this.f10450a = tabParser;
        }
    }

    TabParser(Context context, Config config, int i) {
        this.f10451a = context;
        this.f10452b = config;
        this.f10453c = context.getResources().getXml(i);
        m14357b();
    }

    /* renamed from: a */
    private String m14354a(int i, XmlResourceParser xmlResourceParser) {
        int attributeResourceValue = xmlResourceParser.getAttributeResourceValue(i, 0);
        return attributeResourceValue != 0 ? this.f10451a.getString(attributeResourceValue) : xmlResourceParser.getAttributeValue(i);
    }

    /* renamed from: a */
    private void m14355a(XmlResourceParser xmlResourceParser) {
        if (this.f10455e == null) {
            this.f10455e = m14358c();
        }
        this.f10455e.setIndexInContainer(this.f10454d.size());
        for (int i = 0; i < xmlResourceParser.getAttributeCount(); i++) {
            String attributeName = xmlResourceParser.getAttributeName(i);
            int i2 = -1;
            switch (attributeName.hashCode()) {
                case -1765033179:
                    if (attributeName.equals("barColorWhenSelected")) {
                        i2 = 5;
                        break;
                    }
                    break;
                case -1077332995:
                    if (attributeName.equals("activeColor")) {
                        i2 = 4;
                        break;
                    }
                    break;
                case -424740686:
                    if (attributeName.equals("badgeBackgroundColor")) {
                        i2 = 6;
                        break;
                    }
                    break;
                case 3355:
                    if (attributeName.equals(TtmlNode.ATTR_ID)) {
                        i2 = 0;
                        break;
                    }
                    break;
                case 3226745:
                    if (attributeName.equals("icon")) {
                        i2 = 1;
                        break;
                    }
                    break;
                case 110371416:
                    if (attributeName.equals("title")) {
                        i2 = 2;
                        break;
                    }
                    break;
                case 1162188184:
                    if (attributeName.equals("inActiveColor")) {
                        i2 = 3;
                        break;
                    }
                    break;
            }
            Integer b;
            switch (i2) {
                case 0:
                    this.f10455e.setId(xmlResourceParser.getIdAttributeResourceValue(i));
                    break;
                case 1:
                    this.f10455e.setIconResId(xmlResourceParser.getAttributeResourceValue(i, 0));
                    break;
                case 2:
                    this.f10455e.setTitle(m14354a(i, xmlResourceParser));
                    break;
                case 3:
                    b = m14356b(i, xmlResourceParser);
                    if (b == null) {
                        break;
                    }
                    this.f10455e.setInActiveColor(b.intValue());
                    break;
                case 4:
                    b = m14356b(i, xmlResourceParser);
                    if (b == null) {
                        break;
                    }
                    this.f10455e.setActiveColor(b.intValue());
                    break;
                case 5:
                    b = m14356b(i, xmlResourceParser);
                    if (b == null) {
                        break;
                    }
                    this.f10455e.setBarColorWhenSelected(b.intValue());
                    break;
                case 6:
                    b = m14356b(i, xmlResourceParser);
                    if (b == null) {
                        break;
                    }
                    this.f10455e.setBadgeBackgroundColor(b.intValue());
                    break;
                default:
                    break;
            }
        }
    }

    /* renamed from: b */
    private Integer m14356b(int i, XmlResourceParser xmlResourceParser) {
        int attributeResourceValue = xmlResourceParser.getAttributeResourceValue(i, 0);
        if (attributeResourceValue != 0) {
            return Integer.valueOf(C0235a.c(this.f10451a, attributeResourceValue));
        }
        try {
            return Integer.valueOf(Color.parseColor(xmlResourceParser.getAttributeValue(i)));
        } catch (Exception e) {
            return null;
        }
    }

    /* renamed from: b */
    private void m14357b() {
        Exception e;
        try {
            this.f10453c.next();
            int eventType = this.f10453c.getEventType();
            while (eventType != 1) {
                if (eventType == 2) {
                    m14355a(this.f10453c);
                } else if (eventType == 3 && this.f10453c.getName().equals("tab") && this.f10455e != null) {
                    this.f10454d.add(this.f10455e);
                    this.f10455e = null;
                }
                eventType = this.f10453c.next();
            }
        } catch (IOException e2) {
            e = e2;
            e.printStackTrace();
            throw new TabParserException();
        } catch (XmlPullParserException e3) {
            e = e3;
            e.printStackTrace();
            throw new TabParserException();
        }
    }

    /* renamed from: c */
    private BottomBarTab m14358c() {
        BottomBarTab bottomBarTab = new BottomBarTab(this.f10451a);
        bottomBarTab.setConfig(this.f10452b);
        return bottomBarTab;
    }

    /* renamed from: a */
    List<BottomBarTab> m14359a() {
        return this.f10454d;
    }
}

package utils.view.bottombar;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.support.annotation.XmlRes;
import android.support.v4.content.ContextCompat;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParserException;
import utils.view.bottombar.BottomBarTab.Config;

class TabParser {
    private final Context context;
    private final Config defaultTabConfig;
    private final XmlResourceParser parser;
    private ArrayList<BottomBarTab> tabs = new ArrayList();
    private BottomBarTab workingTab;

    private class TabParserException extends RuntimeException {
        private TabParserException() {
        }
    }

    TabParser(Context context, Config defaultTabConfig, @XmlRes int tabsXmlResId) {
        this.context = context;
        this.defaultTabConfig = defaultTabConfig;
        this.parser = context.getResources().getXml(tabsXmlResId);
        parse();
    }

    private void parse() {
        Exception e;
        try {
            this.parser.next();
            int eventType = this.parser.getEventType();
            while (eventType != 1) {
                if (eventType == 2) {
                    parseNewTab(this.parser);
                } else if (eventType == 3 && this.parser.getName().equals("tab") && this.workingTab != null) {
                    this.tabs.add(this.workingTab);
                    this.workingTab = null;
                }
                eventType = this.parser.next();
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

    private void parseNewTab(XmlResourceParser parser) {
        if (this.workingTab == null) {
            this.workingTab = tabWithDefaults();
        }
        this.workingTab.setIndexInContainer(this.tabs.size());
        for (int i = 0; i < parser.getAttributeCount(); i++) {
            String attrName = parser.getAttributeName(i);
            int i2 = -1;
            switch (attrName.hashCode()) {
                case -1765033179:
                    if (attrName.equals("barColorWhenSelected")) {
                        i2 = 5;
                        break;
                    }
                    break;
                case -1077332995:
                    if (attrName.equals("activeColor")) {
                        i2 = 4;
                        break;
                    }
                    break;
                case -424740686:
                    if (attrName.equals("badgeBackgroundColor")) {
                        i2 = 6;
                        break;
                    }
                    break;
                case 3355:
                    if (attrName.equals("id")) {
                        i2 = 0;
                        break;
                    }
                    break;
                case 3226745:
                    if (attrName.equals(SettingsJsonConstants.APP_ICON_KEY)) {
                        i2 = 1;
                        break;
                    }
                    break;
                case 110371416:
                    if (attrName.equals("title")) {
                        i2 = 2;
                        break;
                    }
                    break;
                case 1162188184:
                    if (attrName.equals("inActiveColor")) {
                        i2 = 3;
                        break;
                    }
                    break;
            }
            switch (i2) {
                case 0:
                    this.workingTab.setId(parser.getIdAttributeResourceValue(i));
                    break;
                case 1:
                    this.workingTab.setIconResId(parser.getAttributeResourceValue(i, 0));
                    break;
                case 2:
                    this.workingTab.setTitle(getTitleValue(i, parser));
                    break;
                case 3:
                    Integer inActiveColor = getColorValue(i, parser);
                    if (inActiveColor == null) {
                        break;
                    }
                    this.workingTab.setInActiveColor(inActiveColor.intValue());
                    break;
                case 4:
                    Integer activeColor = getColorValue(i, parser);
                    if (activeColor == null) {
                        break;
                    }
                    this.workingTab.setActiveColor(activeColor.intValue());
                    break;
                case 5:
                    Integer barColorWhenSelected = getColorValue(i, parser);
                    if (barColorWhenSelected == null) {
                        break;
                    }
                    this.workingTab.setBarColorWhenSelected(barColorWhenSelected.intValue());
                    break;
                case 6:
                    Integer badgeBackgroundColor = getColorValue(i, parser);
                    if (badgeBackgroundColor == null) {
                        break;
                    }
                    this.workingTab.setBadgeBackgroundColor(badgeBackgroundColor.intValue());
                    break;
                default:
                    break;
            }
        }
    }

    private BottomBarTab tabWithDefaults() {
        BottomBarTab tab = new BottomBarTab(this.context);
        tab.setConfig(this.defaultTabConfig);
        return tab;
    }

    private String getTitleValue(int attrIndex, XmlResourceParser parser) {
        int titleResource = parser.getAttributeResourceValue(attrIndex, 0);
        if (titleResource != 0) {
            return this.context.getString(titleResource);
        }
        return parser.getAttributeValue(attrIndex);
    }

    private Integer getColorValue(int attrIndex, XmlResourceParser parser) {
        int colorResource = parser.getAttributeResourceValue(attrIndex, 0);
        if (colorResource != 0) {
            return Integer.valueOf(ContextCompat.getColor(this.context, colorResource));
        }
        try {
            return Integer.valueOf(Color.parseColor(parser.getAttributeValue(attrIndex)));
        } catch (Exception e) {
            return null;
        }
    }

    List<BottomBarTab> getTabs() {
        return this.tabs;
    }
}

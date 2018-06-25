package org.telegram.messenger.exoplayer2.text.ttml;

import android.text.SpannableStringBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;
import org.telegram.messenger.exoplayer2.C0907C;
import org.telegram.messenger.exoplayer2.text.Cue;
import org.telegram.messenger.exoplayer2.util.Assertions;

final class TtmlNode {
    public static final String ANONYMOUS_REGION_ID = "";
    public static final String ATTR_ID = "id";
    public static final String ATTR_TTS_BACKGROUND_COLOR = "backgroundColor";
    public static final String ATTR_TTS_COLOR = "color";
    public static final String ATTR_TTS_DISPLAY_ALIGN = "displayAlign";
    public static final String ATTR_TTS_EXTENT = "extent";
    public static final String ATTR_TTS_FONT_FAMILY = "fontFamily";
    public static final String ATTR_TTS_FONT_SIZE = "fontSize";
    public static final String ATTR_TTS_FONT_STYLE = "fontStyle";
    public static final String ATTR_TTS_FONT_WEIGHT = "fontWeight";
    public static final String ATTR_TTS_ORIGIN = "origin";
    public static final String ATTR_TTS_TEXT_ALIGN = "textAlign";
    public static final String ATTR_TTS_TEXT_DECORATION = "textDecoration";
    public static final String BOLD = "bold";
    public static final String CENTER = "center";
    public static final String END = "end";
    public static final String ITALIC = "italic";
    public static final String LEFT = "left";
    public static final String LINETHROUGH = "linethrough";
    public static final String NO_LINETHROUGH = "nolinethrough";
    public static final String NO_UNDERLINE = "nounderline";
    public static final String RIGHT = "right";
    public static final String START = "start";
    public static final String TAG_BODY = "body";
    public static final String TAG_BR = "br";
    public static final String TAG_DIV = "div";
    public static final String TAG_HEAD = "head";
    public static final String TAG_LAYOUT = "layout";
    public static final String TAG_METADATA = "metadata";
    public static final String TAG_P = "p";
    public static final String TAG_REGION = "region";
    public static final String TAG_SMPTE_DATA = "smpte:data";
    public static final String TAG_SMPTE_IMAGE = "smpte:image";
    public static final String TAG_SMPTE_INFORMATION = "smpte:information";
    public static final String TAG_SPAN = "span";
    public static final String TAG_STYLE = "style";
    public static final String TAG_STYLING = "styling";
    public static final String TAG_TT = "tt";
    public static final String UNDERLINE = "underline";
    private List<TtmlNode> children;
    public final long endTimeUs;
    public final boolean isTextNode;
    private final HashMap<String, Integer> nodeEndsByRegion;
    private final HashMap<String, Integer> nodeStartsByRegion;
    public final String regionId;
    public final long startTimeUs;
    public final TtmlStyle style;
    private final String[] styleIds;
    public final String tag;
    public final String text;

    public static TtmlNode buildTextNode(String text) {
        return new TtmlNode(null, TtmlRenderUtil.applyTextElementSpacePolicy(text), C0907C.TIME_UNSET, C0907C.TIME_UNSET, null, null, "");
    }

    public static TtmlNode buildNode(String tag, long startTimeUs, long endTimeUs, TtmlStyle style, String[] styleIds, String regionId) {
        return new TtmlNode(tag, null, startTimeUs, endTimeUs, style, styleIds, regionId);
    }

    private TtmlNode(String tag, String text, long startTimeUs, long endTimeUs, TtmlStyle style, String[] styleIds, String regionId) {
        this.tag = tag;
        this.text = text;
        this.style = style;
        this.styleIds = styleIds;
        this.isTextNode = text != null;
        this.startTimeUs = startTimeUs;
        this.endTimeUs = endTimeUs;
        this.regionId = (String) Assertions.checkNotNull(regionId);
        this.nodeStartsByRegion = new HashMap();
        this.nodeEndsByRegion = new HashMap();
    }

    public boolean isActive(long timeUs) {
        return (this.startTimeUs == C0907C.TIME_UNSET && this.endTimeUs == C0907C.TIME_UNSET) || ((this.startTimeUs <= timeUs && this.endTimeUs == C0907C.TIME_UNSET) || ((this.startTimeUs == C0907C.TIME_UNSET && timeUs < this.endTimeUs) || (this.startTimeUs <= timeUs && timeUs < this.endTimeUs)));
    }

    public void addChild(TtmlNode child) {
        if (this.children == null) {
            this.children = new ArrayList();
        }
        this.children.add(child);
    }

    public TtmlNode getChild(int index) {
        if (this.children != null) {
            return (TtmlNode) this.children.get(index);
        }
        throw new IndexOutOfBoundsException();
    }

    public int getChildCount() {
        return this.children == null ? 0 : this.children.size();
    }

    public long[] getEventTimesUs() {
        TreeSet<Long> eventTimeSet = new TreeSet();
        getEventTimes(eventTimeSet, false);
        long[] eventTimes = new long[eventTimeSet.size()];
        int i = 0;
        Iterator it = eventTimeSet.iterator();
        while (it.hasNext()) {
            int i2 = i + 1;
            eventTimes[i] = ((Long) it.next()).longValue();
            i = i2;
        }
        return eventTimes;
    }

    private void getEventTimes(TreeSet<Long> out, boolean descendsPNode) {
        boolean isPNode = TAG_P.equals(this.tag);
        if (descendsPNode || isPNode) {
            if (this.startTimeUs != C0907C.TIME_UNSET) {
                out.add(Long.valueOf(this.startTimeUs));
            }
            if (this.endTimeUs != C0907C.TIME_UNSET) {
                out.add(Long.valueOf(this.endTimeUs));
            }
        }
        if (this.children != null) {
            for (int i = 0; i < this.children.size(); i++) {
                TtmlNode ttmlNode = (TtmlNode) this.children.get(i);
                boolean z = descendsPNode || isPNode;
                ttmlNode.getEventTimes(out, z);
            }
        }
    }

    public String[] getStyleIds() {
        return this.styleIds;
    }

    public List<Cue> getCues(long timeUs, Map<String, TtmlStyle> globalStyles, Map<String, TtmlRegion> regionMap) {
        TreeMap<String, SpannableStringBuilder> regionOutputs = new TreeMap();
        traverseForText(timeUs, false, this.regionId, regionOutputs);
        traverseForStyle(globalStyles, regionOutputs);
        List<Cue> cues = new ArrayList();
        for (Entry<String, SpannableStringBuilder> entry : regionOutputs.entrySet()) {
            TtmlRegion region = (TtmlRegion) regionMap.get(entry.getKey());
            cues.add(new Cue(cleanUpText((SpannableStringBuilder) entry.getValue()), null, region.line, region.lineType, region.lineAnchor, region.position, Integer.MIN_VALUE, region.width));
        }
        return cues;
    }

    private void traverseForText(long timeUs, boolean descendsPNode, String inheritedRegion, Map<String, SpannableStringBuilder> regionOutputs) {
        this.nodeStartsByRegion.clear();
        this.nodeEndsByRegion.clear();
        String resolvedRegionId = this.regionId;
        if ("".equals(resolvedRegionId)) {
            resolvedRegionId = inheritedRegion;
        }
        if (this.isTextNode && descendsPNode) {
            getRegionOutput(resolvedRegionId, regionOutputs).append(this.text);
        } else if (TAG_BR.equals(this.tag) && descendsPNode) {
            getRegionOutput(resolvedRegionId, regionOutputs).append('\n');
        } else if (!TAG_METADATA.equals(this.tag) && isActive(timeUs)) {
            boolean isPNode = TAG_P.equals(this.tag);
            for (Entry<String, SpannableStringBuilder> entry : regionOutputs.entrySet()) {
                this.nodeStartsByRegion.put(entry.getKey(), Integer.valueOf(((SpannableStringBuilder) entry.getValue()).length()));
            }
            for (int i = 0; i < getChildCount(); i++) {
                TtmlNode child = getChild(i);
                boolean z = descendsPNode || isPNode;
                child.traverseForText(timeUs, z, resolvedRegionId, regionOutputs);
            }
            if (isPNode) {
                TtmlRenderUtil.endParagraph(getRegionOutput(resolvedRegionId, regionOutputs));
            }
            for (Entry<String, SpannableStringBuilder> entry2 : regionOutputs.entrySet()) {
                this.nodeEndsByRegion.put(entry2.getKey(), Integer.valueOf(((SpannableStringBuilder) entry2.getValue()).length()));
            }
        }
    }

    private static SpannableStringBuilder getRegionOutput(String resolvedRegionId, Map<String, SpannableStringBuilder> regionOutputs) {
        if (!regionOutputs.containsKey(resolvedRegionId)) {
            regionOutputs.put(resolvedRegionId, new SpannableStringBuilder());
        }
        return (SpannableStringBuilder) regionOutputs.get(resolvedRegionId);
    }

    private void traverseForStyle(Map<String, TtmlStyle> globalStyles, Map<String, SpannableStringBuilder> regionOutputs) {
        for (Entry<String, Integer> entry : this.nodeEndsByRegion.entrySet()) {
            String regionId = (String) entry.getKey();
            applyStyleToOutput(globalStyles, (SpannableStringBuilder) regionOutputs.get(regionId), this.nodeStartsByRegion.containsKey(regionId) ? ((Integer) this.nodeStartsByRegion.get(regionId)).intValue() : 0, ((Integer) entry.getValue()).intValue());
            for (int i = 0; i < getChildCount(); i++) {
                getChild(i).traverseForStyle(globalStyles, regionOutputs);
            }
        }
    }

    private void applyStyleToOutput(Map<String, TtmlStyle> globalStyles, SpannableStringBuilder regionOutput, int start, int end) {
        if (start != end) {
            TtmlStyle resolvedStyle = TtmlRenderUtil.resolveStyle(this.style, this.styleIds, globalStyles);
            if (resolvedStyle != null) {
                TtmlRenderUtil.applyStylesToSpan(regionOutput, start, end, resolvedStyle);
            }
        }
    }

    private SpannableStringBuilder cleanUpText(SpannableStringBuilder builder) {
        int i;
        int builderLength = builder.length();
        for (i = 0; i < builderLength; i++) {
            if (builder.charAt(i) == ' ') {
                int j = i + 1;
                while (j < builder.length() && builder.charAt(j) == ' ') {
                    j++;
                }
                int spacesToDelete = j - (i + 1);
                if (spacesToDelete > 0) {
                    builder.delete(i, i + spacesToDelete);
                    builderLength -= spacesToDelete;
                }
            }
        }
        if (builderLength > 0 && builder.charAt(0) == ' ') {
            builder.delete(0, 1);
            builderLength--;
        }
        i = 0;
        while (i < builderLength - 1) {
            if (builder.charAt(i) == '\n' && builder.charAt(i + 1) == ' ') {
                builder.delete(i + 1, i + 2);
                builderLength--;
            }
            i++;
        }
        if (builderLength > 0 && builder.charAt(builderLength - 1) == ' ') {
            builder.delete(builderLength - 1, builderLength);
            builderLength--;
        }
        i = 0;
        while (i < builderLength - 1) {
            if (builder.charAt(i) == ' ' && builder.charAt(i + 1) == '\n') {
                builder.delete(i, i + 1);
                builderLength--;
            }
            i++;
        }
        if (builderLength > 0 && builder.charAt(builderLength - 1) == '\n') {
            builder.delete(builderLength - 1, builderLength);
        }
        return builder;
    }
}

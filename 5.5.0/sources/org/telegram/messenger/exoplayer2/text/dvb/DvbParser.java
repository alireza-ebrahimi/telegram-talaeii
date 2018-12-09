package org.telegram.messenger.exoplayer2.text.dvb;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Region.Op;
import android.util.Log;
import android.util.SparseArray;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.telegram.messenger.exoplayer2.text.Cue;
import org.telegram.messenger.exoplayer2.util.ParsableBitArray;
import org.telegram.messenger.exoplayer2.util.Util;
import org.telegram.ui.ActionBar.Theme;

final class DvbParser {
    private static final int DATA_TYPE_24_TABLE_DATA = 32;
    private static final int DATA_TYPE_28_TABLE_DATA = 33;
    private static final int DATA_TYPE_2BP_CODE_STRING = 16;
    private static final int DATA_TYPE_48_TABLE_DATA = 34;
    private static final int DATA_TYPE_4BP_CODE_STRING = 17;
    private static final int DATA_TYPE_8BP_CODE_STRING = 18;
    private static final int DATA_TYPE_END_LINE = 240;
    private static final int OBJECT_CODING_PIXELS = 0;
    private static final int OBJECT_CODING_STRING = 1;
    private static final int PAGE_STATE_NORMAL = 0;
    private static final int REGION_DEPTH_4_BIT = 2;
    private static final int REGION_DEPTH_8_BIT = 3;
    private static final int SEGMENT_TYPE_CLUT_DEFINITION = 18;
    private static final int SEGMENT_TYPE_DISPLAY_DEFINITION = 20;
    private static final int SEGMENT_TYPE_OBJECT_DATA = 19;
    private static final int SEGMENT_TYPE_PAGE_COMPOSITION = 16;
    private static final int SEGMENT_TYPE_REGION_COMPOSITION = 17;
    private static final String TAG = "DvbParser";
    private static final byte[] defaultMap2To4 = new byte[]{(byte) 0, (byte) 7, (byte) 8, (byte) 15};
    private static final byte[] defaultMap2To8 = new byte[]{(byte) 0, (byte) 119, (byte) -120, (byte) -1};
    private static final byte[] defaultMap4To8 = new byte[]{(byte) 0, (byte) 17, (byte) 34, (byte) 51, (byte) 68, (byte) 85, (byte) 102, (byte) 119, (byte) -120, (byte) -103, (byte) -86, (byte) -69, (byte) -52, (byte) -35, (byte) -18, (byte) -1};
    private Bitmap bitmap;
    private final Canvas canvas;
    private final ClutDefinition defaultClutDefinition;
    private final DisplayDefinition defaultDisplayDefinition;
    private final Paint defaultPaint = new Paint();
    private final Paint fillRegionPaint;
    private final SubtitleService subtitleService;

    private static final class ClutDefinition {
        public final int[] clutEntries2Bit;
        public final int[] clutEntries4Bit;
        public final int[] clutEntries8Bit;
        public final int id;

        public ClutDefinition(int i, int[] iArr, int[] iArr2, int[] iArr3) {
            this.id = i;
            this.clutEntries2Bit = iArr;
            this.clutEntries4Bit = iArr2;
            this.clutEntries8Bit = iArr3;
        }
    }

    private static final class DisplayDefinition {
        public final int height;
        public final int horizontalPositionMaximum;
        public final int horizontalPositionMinimum;
        public final int verticalPositionMaximum;
        public final int verticalPositionMinimum;
        public final int width;

        public DisplayDefinition(int i, int i2, int i3, int i4, int i5, int i6) {
            this.width = i;
            this.height = i2;
            this.horizontalPositionMinimum = i3;
            this.horizontalPositionMaximum = i4;
            this.verticalPositionMinimum = i5;
            this.verticalPositionMaximum = i6;
        }
    }

    private static final class ObjectData {
        public final byte[] bottomFieldData;
        public final int id;
        public final boolean nonModifyingColorFlag;
        public final byte[] topFieldData;

        public ObjectData(int i, boolean z, byte[] bArr, byte[] bArr2) {
            this.id = i;
            this.nonModifyingColorFlag = z;
            this.topFieldData = bArr;
            this.bottomFieldData = bArr2;
        }
    }

    private static final class PageComposition {
        public final SparseArray<PageRegion> regions;
        public final int state;
        public final int timeOutSecs;
        public final int version;

        public PageComposition(int i, int i2, int i3, SparseArray<PageRegion> sparseArray) {
            this.timeOutSecs = i;
            this.version = i2;
            this.state = i3;
            this.regions = sparseArray;
        }
    }

    private static final class PageRegion {
        public final int horizontalAddress;
        public final int verticalAddress;

        public PageRegion(int i, int i2) {
            this.horizontalAddress = i;
            this.verticalAddress = i2;
        }
    }

    private static final class RegionComposition {
        public final int clutId;
        public final int depth;
        public final boolean fillFlag;
        public final int height;
        public final int id;
        public final int levelOfCompatibility;
        public final int pixelCode2Bit;
        public final int pixelCode4Bit;
        public final int pixelCode8Bit;
        public final SparseArray<RegionObject> regionObjects;
        public final int width;

        public RegionComposition(int i, boolean z, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, SparseArray<RegionObject> sparseArray) {
            this.id = i;
            this.fillFlag = z;
            this.width = i2;
            this.height = i3;
            this.levelOfCompatibility = i4;
            this.depth = i5;
            this.clutId = i6;
            this.pixelCode8Bit = i7;
            this.pixelCode4Bit = i8;
            this.pixelCode2Bit = i9;
            this.regionObjects = sparseArray;
        }

        public void mergeFrom(RegionComposition regionComposition) {
            if (regionComposition != null) {
                SparseArray sparseArray = regionComposition.regionObjects;
                for (int i = 0; i < sparseArray.size(); i++) {
                    this.regionObjects.put(sparseArray.keyAt(i), sparseArray.valueAt(i));
                }
            }
        }
    }

    private static final class RegionObject {
        public final int backgroundPixelCode;
        public final int foregroundPixelCode;
        public final int horizontalPosition;
        public final int provider;
        public final int type;
        public final int verticalPosition;

        public RegionObject(int i, int i2, int i3, int i4, int i5, int i6) {
            this.type = i;
            this.provider = i2;
            this.horizontalPosition = i3;
            this.verticalPosition = i4;
            this.foregroundPixelCode = i5;
            this.backgroundPixelCode = i6;
        }
    }

    private static final class SubtitleService {
        public final SparseArray<ClutDefinition> ancillaryCluts = new SparseArray();
        public final SparseArray<ObjectData> ancillaryObjects = new SparseArray();
        public final int ancillaryPageId;
        public final SparseArray<ClutDefinition> cluts = new SparseArray();
        public DisplayDefinition displayDefinition;
        public final SparseArray<ObjectData> objects = new SparseArray();
        public PageComposition pageComposition;
        public final SparseArray<RegionComposition> regions = new SparseArray();
        public final int subtitlePageId;

        public SubtitleService(int i, int i2) {
            this.subtitlePageId = i;
            this.ancillaryPageId = i2;
        }

        public void reset() {
            this.regions.clear();
            this.cluts.clear();
            this.objects.clear();
            this.ancillaryCluts.clear();
            this.ancillaryObjects.clear();
            this.displayDefinition = null;
            this.pageComposition = null;
        }
    }

    public DvbParser(int i, int i2) {
        this.defaultPaint.setStyle(Style.FILL_AND_STROKE);
        this.defaultPaint.setXfermode(new PorterDuffXfermode(Mode.SRC));
        this.defaultPaint.setPathEffect(null);
        this.fillRegionPaint = new Paint();
        this.fillRegionPaint.setStyle(Style.FILL);
        this.fillRegionPaint.setXfermode(new PorterDuffXfermode(Mode.DST_OVER));
        this.fillRegionPaint.setPathEffect(null);
        this.canvas = new Canvas();
        this.defaultDisplayDefinition = new DisplayDefinition(719, 575, 0, 719, 0, 575);
        this.defaultClutDefinition = new ClutDefinition(0, generateDefault2BitClutEntries(), generateDefault4BitClutEntries(), generateDefault8BitClutEntries());
        this.subtitleService = new SubtitleService(i, i2);
    }

    private static byte[] buildClutMapTable(int i, int i2, ParsableBitArray parsableBitArray) {
        byte[] bArr = new byte[i];
        for (int i3 = 0; i3 < i; i3++) {
            bArr[i3] = (byte) parsableBitArray.readBits(i2);
        }
        return bArr;
    }

    private static int[] generateDefault2BitClutEntries() {
        return new int[]{0, -1, Theme.ACTION_BAR_VIDEO_EDIT_COLOR, -8421505};
    }

    private static int[] generateDefault4BitClutEntries() {
        int[] iArr = new int[16];
        iArr[0] = 0;
        for (int i = 1; i < iArr.length; i++) {
            if (i < 8) {
                iArr[i] = getColor(255, (i & 1) != 0 ? 255 : 0, (i & 2) != 0 ? 255 : 0, (i & 4) != 0 ? 255 : 0);
            } else {
                iArr[i] = getColor(255, (i & 1) != 0 ? 127 : 0, (i & 2) != 0 ? 127 : 0, (i & 4) != 0 ? 127 : 0);
            }
        }
        return iArr;
    }

    private static int[] generateDefault8BitClutEntries() {
        int[] iArr = new int[256];
        iArr[0] = 0;
        for (int i = 0; i < iArr.length; i++) {
            if (i >= 8) {
                switch (i & 136) {
                    case 0:
                        iArr[i] = getColor(255, ((i & 1) != 0 ? 85 : 0) + ((i & 16) != 0 ? 170 : 0), ((i & 2) != 0 ? 85 : 0) + ((i & 32) != 0 ? 170 : 0), ((i & 64) != 0 ? 170 : 0) + ((i & 4) != 0 ? 85 : 0));
                        break;
                    case 8:
                        iArr[i] = getColor(127, ((i & 1) != 0 ? 85 : 0) + ((i & 16) != 0 ? 170 : 0), ((i & 2) != 0 ? 85 : 0) + ((i & 32) != 0 ? 170 : 0), ((i & 64) != 0 ? 170 : 0) + ((i & 4) != 0 ? 85 : 0));
                        break;
                    case 128:
                        iArr[i] = getColor(255, (((i & 1) != 0 ? 43 : 0) + 127) + ((i & 16) != 0 ? 85 : 0), (((i & 2) != 0 ? 43 : 0) + 127) + ((i & 32) != 0 ? 85 : 0), ((i & 64) != 0 ? 85 : 0) + (((i & 4) != 0 ? 43 : 0) + 127));
                        break;
                    case 136:
                        iArr[i] = getColor(255, ((i & 1) != 0 ? 43 : 0) + ((i & 16) != 0 ? 85 : 0), ((i & 2) != 0 ? 43 : 0) + ((i & 32) != 0 ? 85 : 0), ((i & 64) != 0 ? 85 : 0) + ((i & 4) != 0 ? 43 : 0));
                        break;
                    default:
                        break;
                }
            }
            iArr[i] = getColor(63, (i & 1) != 0 ? 255 : 0, (i & 2) != 0 ? 255 : 0, (i & 4) != 0 ? 255 : 0);
        }
        return iArr;
    }

    private static int getColor(int i, int i2, int i3, int i4) {
        return (((i << 24) | (i2 << 16)) | (i3 << 8)) | i4;
    }

    private static int paint2BitPixelCodeString(ParsableBitArray parsableBitArray, int[] iArr, byte[] bArr, int i, int i2, Paint paint, Canvas canvas) {
        Object obj = null;
        while (true) {
            int i3;
            int i4;
            Object obj2;
            int readBits = parsableBitArray.readBits(2);
            if (readBits == 0) {
                if (!parsableBitArray.readBit()) {
                    if (!parsableBitArray.readBit()) {
                        switch (parsableBitArray.readBits(2)) {
                            case 0:
                                i3 = 0;
                                int i5 = 1;
                                i4 = 0;
                                break;
                            case 1:
                                i3 = 2;
                                obj2 = obj;
                                i4 = 0;
                                break;
                            case 2:
                                i3 = parsableBitArray.readBits(4) + 12;
                                obj2 = obj;
                                i4 = parsableBitArray.readBits(2);
                                break;
                            case 3:
                                i3 = parsableBitArray.readBits(8) + 29;
                                obj2 = obj;
                                i4 = parsableBitArray.readBits(2);
                                break;
                            default:
                                i3 = 0;
                                obj2 = obj;
                                i4 = 0;
                                break;
                        }
                    }
                    i3 = 1;
                    obj2 = obj;
                    i4 = 0;
                } else {
                    i3 = parsableBitArray.readBits(3) + 3;
                    obj2 = obj;
                    i4 = parsableBitArray.readBits(2);
                }
            } else {
                i3 = 1;
                obj2 = obj;
                i4 = readBits;
            }
            if (!(i3 == 0 || paint == null)) {
                if (bArr != null) {
                    i4 = bArr[i4];
                }
                paint.setColor(iArr[i4]);
                Canvas canvas2 = canvas;
                canvas2.drawRect((float) i, (float) i2, (float) (i + i3), (float) (i2 + 1), paint);
            }
            i += i3;
            if (obj2 != null) {
                return i;
            }
            obj = obj2;
        }
    }

    private static int paint4BitPixelCodeString(ParsableBitArray parsableBitArray, int[] iArr, byte[] bArr, int i, int i2, Paint paint, Canvas canvas) {
        Object obj = null;
        while (true) {
            int i3;
            Object obj2;
            int i4;
            int readBits = parsableBitArray.readBits(4);
            if (readBits == 0) {
                if (parsableBitArray.readBit()) {
                    if (parsableBitArray.readBit()) {
                        switch (parsableBitArray.readBits(2)) {
                            case 0:
                                i3 = 1;
                                obj2 = obj;
                                i4 = 0;
                                break;
                            case 1:
                                i3 = 2;
                                obj2 = obj;
                                i4 = 0;
                                break;
                            case 2:
                                i3 = parsableBitArray.readBits(4) + 9;
                                obj2 = obj;
                                i4 = parsableBitArray.readBits(4);
                                break;
                            case 3:
                                i3 = parsableBitArray.readBits(8) + 25;
                                obj2 = obj;
                                i4 = parsableBitArray.readBits(4);
                                break;
                            default:
                                i3 = 0;
                                obj2 = obj;
                                i4 = 0;
                                break;
                        }
                    }
                    i3 = parsableBitArray.readBits(2) + 4;
                    obj2 = obj;
                    i4 = parsableBitArray.readBits(4);
                } else {
                    readBits = parsableBitArray.readBits(3);
                    if (readBits != 0) {
                        i3 = readBits + 2;
                        obj2 = obj;
                        i4 = 0;
                    } else {
                        i3 = 0;
                        int i5 = 1;
                        i4 = 0;
                    }
                }
            } else {
                i3 = 1;
                obj2 = obj;
                i4 = readBits;
            }
            if (!(i3 == 0 || paint == null)) {
                if (bArr != null) {
                    i4 = bArr[i4];
                }
                paint.setColor(iArr[i4]);
                Canvas canvas2 = canvas;
                canvas2.drawRect((float) i, (float) i2, (float) (i + i3), (float) (i2 + 1), paint);
            }
            i += i3;
            if (obj2 != null) {
                return i;
            }
            obj = obj2;
        }
    }

    private static int paint8BitPixelCodeString(ParsableBitArray parsableBitArray, int[] iArr, byte[] bArr, int i, int i2, Paint paint, Canvas canvas) {
        Object obj = null;
        while (true) {
            int i3;
            Object obj2;
            int i4;
            int readBits = parsableBitArray.readBits(8);
            if (readBits != 0) {
                i3 = 1;
                obj2 = obj;
                i4 = readBits;
            } else if (parsableBitArray.readBit()) {
                i3 = parsableBitArray.readBits(7);
                obj2 = obj;
                i4 = parsableBitArray.readBits(8);
            } else {
                int readBits2 = parsableBitArray.readBits(7);
                if (readBits2 != 0) {
                    i3 = readBits2;
                    obj2 = obj;
                    i4 = 0;
                } else {
                    i3 = 0;
                    int i5 = 1;
                    i4 = 0;
                }
            }
            if (!(i3 == 0 || paint == null)) {
                if (bArr != null) {
                    i4 = bArr[i4];
                }
                paint.setColor(iArr[i4]);
                Canvas canvas2 = canvas;
                canvas2.drawRect((float) i, (float) i2, (float) (i + i3), (float) (i2 + 1), paint);
            }
            i += i3;
            if (obj2 != null) {
                return i;
            }
            obj = obj2;
        }
    }

    private static void paintPixelDataSubBlock(byte[] bArr, int[] iArr, int i, int i2, int i3, Paint paint, Canvas canvas) {
        ParsableBitArray parsableBitArray = new ParsableBitArray(bArr);
        byte[] bArr2 = null;
        byte[] bArr3 = null;
        int i4 = i3;
        int i5 = i2;
        while (parsableBitArray.bitsLeft() != 0) {
            byte[] bArr4;
            byte[] bArr5;
            switch (parsableBitArray.readBits(8)) {
                case 16:
                    if (i == 3) {
                        bArr4 = bArr3 == null ? defaultMap2To8 : bArr3;
                    } else if (i == 2) {
                        bArr4 = bArr2 == null ? defaultMap2To4 : bArr2;
                    } else {
                        bArr4 = null;
                    }
                    i5 = paint2BitPixelCodeString(parsableBitArray, iArr, bArr4, i5, i4, paint, canvas);
                    parsableBitArray.byteAlign();
                    bArr5 = bArr3;
                    bArr4 = bArr2;
                    break;
                case 17:
                    if (i == 3) {
                        bArr4 = null == null ? defaultMap4To8 : null;
                    } else {
                        bArr4 = null;
                    }
                    i5 = paint4BitPixelCodeString(parsableBitArray, iArr, bArr4, i5, i4, paint, canvas);
                    parsableBitArray.byteAlign();
                    bArr5 = bArr3;
                    bArr4 = bArr2;
                    break;
                case 18:
                    i5 = paint8BitPixelCodeString(parsableBitArray, iArr, null, i5, i4, paint, canvas);
                    bArr5 = bArr3;
                    bArr4 = bArr2;
                    break;
                case 32:
                    bArr4 = buildClutMapTable(4, 4, parsableBitArray);
                    bArr5 = bArr3;
                    break;
                case 33:
                    bArr5 = buildClutMapTable(4, 8, parsableBitArray);
                    bArr4 = bArr2;
                    break;
                case 34:
                    bArr5 = buildClutMapTable(16, 8, parsableBitArray);
                    bArr4 = bArr2;
                    break;
                case 240:
                    i4 += 2;
                    bArr5 = bArr3;
                    bArr4 = bArr2;
                    i5 = i2;
                    break;
                default:
                    bArr5 = bArr3;
                    bArr4 = bArr2;
                    break;
            }
            bArr3 = bArr5;
            bArr2 = bArr4;
        }
    }

    private static void paintPixelDataSubBlocks(ObjectData objectData, ClutDefinition clutDefinition, int i, int i2, int i3, Paint paint, Canvas canvas) {
        int[] iArr = i == 3 ? clutDefinition.clutEntries8Bit : i == 2 ? clutDefinition.clutEntries4Bit : clutDefinition.clutEntries2Bit;
        paintPixelDataSubBlock(objectData.topFieldData, iArr, i, i2, i3, paint, canvas);
        paintPixelDataSubBlock(objectData.bottomFieldData, iArr, i, i2, i3 + 1, paint, canvas);
    }

    private static ClutDefinition parseClutDefinition(ParsableBitArray parsableBitArray, int i) {
        int readBits = parsableBitArray.readBits(8);
        parsableBitArray.skipBits(8);
        int i2 = i - 2;
        int[] generateDefault2BitClutEntries = generateDefault2BitClutEntries();
        int[] generateDefault4BitClutEntries = generateDefault4BitClutEntries();
        int[] generateDefault8BitClutEntries = generateDefault8BitClutEntries();
        while (i2 > 0) {
            int readBits2;
            int readBits3;
            int readBits4;
            int readBits5 = parsableBitArray.readBits(8);
            int readBits6 = parsableBitArray.readBits(8);
            i2 -= 2;
            int[] iArr = (readBits6 & 128) != 0 ? generateDefault2BitClutEntries : (readBits6 & 64) != 0 ? generateDefault4BitClutEntries : generateDefault8BitClutEntries;
            if ((readBits6 & 1) != 0) {
                readBits2 = parsableBitArray.readBits(8);
                readBits3 = parsableBitArray.readBits(8);
                readBits4 = parsableBitArray.readBits(8);
                readBits6 = parsableBitArray.readBits(8);
                i2 -= 4;
            } else {
                readBits2 = parsableBitArray.readBits(6) << 2;
                readBits3 = parsableBitArray.readBits(4) << 4;
                readBits4 = parsableBitArray.readBits(4) << 4;
                readBits6 = parsableBitArray.readBits(2) << 6;
                i2 -= 2;
            }
            if (readBits2 == 0) {
                readBits3 = 0;
                readBits4 = 0;
                readBits6 = 255;
            }
            iArr[readBits5] = getColor((byte) (255 - (readBits6 & 255)), Util.constrainValue((int) (((double) readBits2) + (1.402d * ((double) (readBits3 - 128)))), 0, 255), Util.constrainValue((int) ((((double) readBits2) - (0.34414d * ((double) (readBits4 - 128)))) - (0.71414d * ((double) (readBits3 - 128)))), 0, 255), Util.constrainValue((int) (((double) readBits2) + (1.772d * ((double) (readBits4 - 128)))), 0, 255));
        }
        return new ClutDefinition(readBits, generateDefault2BitClutEntries, generateDefault4BitClutEntries, generateDefault8BitClutEntries);
    }

    private static DisplayDefinition parseDisplayDefinition(ParsableBitArray parsableBitArray) {
        int readBits;
        int readBits2;
        int readBits3;
        int i = 0;
        parsableBitArray.skipBits(4);
        boolean readBit = parsableBitArray.readBit();
        parsableBitArray.skipBits(3);
        int readBits4 = parsableBitArray.readBits(16);
        int readBits5 = parsableBitArray.readBits(16);
        if (readBit) {
            readBits = parsableBitArray.readBits(16);
            readBits2 = parsableBitArray.readBits(16);
            i = parsableBitArray.readBits(16);
            readBits3 = parsableBitArray.readBits(16);
        } else {
            readBits3 = readBits5;
            readBits2 = readBits4;
            readBits = 0;
        }
        return new DisplayDefinition(readBits4, readBits5, readBits, readBits2, i, readBits3);
    }

    private static ObjectData parseObjectData(ParsableBitArray parsableBitArray) {
        byte[] bArr;
        byte[] bArr2 = null;
        int readBits = parsableBitArray.readBits(16);
        parsableBitArray.skipBits(4);
        int readBits2 = parsableBitArray.readBits(2);
        boolean readBit = parsableBitArray.readBit();
        parsableBitArray.skipBits(1);
        if (readBits2 == 1) {
            parsableBitArray.skipBits(parsableBitArray.readBits(8) * 16);
            bArr = null;
        } else if (readBits2 == 0) {
            int readBits3 = parsableBitArray.readBits(16);
            int readBits4 = parsableBitArray.readBits(16);
            if (readBits3 > 0) {
                bArr = new byte[readBits3];
                parsableBitArray.readBytes(bArr, 0, readBits3);
            } else {
                bArr = null;
            }
            if (readBits4 > 0) {
                bArr2 = new byte[readBits4];
                parsableBitArray.readBytes(bArr2, 0, readBits4);
            } else {
                bArr2 = bArr;
            }
        } else {
            bArr = null;
        }
        return new ObjectData(readBits, readBit, bArr, bArr2);
    }

    private static PageComposition parsePageComposition(ParsableBitArray parsableBitArray, int i) {
        int readBits = parsableBitArray.readBits(8);
        int readBits2 = parsableBitArray.readBits(4);
        int readBits3 = parsableBitArray.readBits(2);
        parsableBitArray.skipBits(2);
        int i2 = i - 2;
        SparseArray sparseArray = new SparseArray();
        while (i2 > 0) {
            int readBits4 = parsableBitArray.readBits(8);
            parsableBitArray.skipBits(8);
            i2 -= 6;
            sparseArray.put(readBits4, new PageRegion(parsableBitArray.readBits(16), parsableBitArray.readBits(16)));
        }
        return new PageComposition(readBits, readBits2, readBits3, sparseArray);
    }

    private static RegionComposition parseRegionComposition(ParsableBitArray parsableBitArray, int i) {
        int readBits = parsableBitArray.readBits(8);
        parsableBitArray.skipBits(4);
        boolean readBit = parsableBitArray.readBit();
        parsableBitArray.skipBits(3);
        int readBits2 = parsableBitArray.readBits(16);
        int readBits3 = parsableBitArray.readBits(16);
        int readBits4 = parsableBitArray.readBits(3);
        int readBits5 = parsableBitArray.readBits(3);
        parsableBitArray.skipBits(2);
        int readBits6 = parsableBitArray.readBits(8);
        int readBits7 = parsableBitArray.readBits(8);
        int readBits8 = parsableBitArray.readBits(4);
        int readBits9 = parsableBitArray.readBits(2);
        parsableBitArray.skipBits(2);
        int i2 = i - 10;
        SparseArray sparseArray = new SparseArray();
        while (i2 > 0) {
            int readBits10 = parsableBitArray.readBits(16);
            int readBits11 = parsableBitArray.readBits(2);
            int readBits12 = parsableBitArray.readBits(2);
            int readBits13 = parsableBitArray.readBits(12);
            parsableBitArray.skipBits(4);
            int readBits14 = parsableBitArray.readBits(12);
            int i3 = i2 - 6;
            int i4 = 0;
            int i5 = 0;
            if (readBits11 == 1 || readBits11 == 2) {
                i4 = parsableBitArray.readBits(8);
                i5 = parsableBitArray.readBits(8);
                i2 = i3 - 2;
            } else {
                i2 = i3;
            }
            sparseArray.put(readBits10, new RegionObject(readBits11, readBits12, readBits13, readBits14, i4, i5));
        }
        return new RegionComposition(readBits, readBit, readBits2, readBits3, readBits4, readBits5, readBits6, readBits7, readBits8, readBits9, sparseArray);
    }

    private static void parseSubtitlingSegment(ParsableBitArray parsableBitArray, SubtitleService subtitleService) {
        int readBits = parsableBitArray.readBits(8);
        int readBits2 = parsableBitArray.readBits(16);
        int readBits3 = parsableBitArray.readBits(16);
        int bytePosition = parsableBitArray.getBytePosition() + readBits3;
        if (readBits3 * 8 > parsableBitArray.bitsLeft()) {
            Log.w(TAG, "Data field length exceeds limit");
            parsableBitArray.skipBits(parsableBitArray.bitsLeft());
            return;
        }
        PageComposition pageComposition;
        switch (readBits) {
            case 16:
                if (readBits2 == subtitleService.subtitlePageId) {
                    pageComposition = subtitleService.pageComposition;
                    PageComposition parsePageComposition = parsePageComposition(parsableBitArray, readBits3);
                    if (parsePageComposition.state == 0) {
                        if (!(pageComposition == null || pageComposition.version == parsePageComposition.version)) {
                            subtitleService.pageComposition = parsePageComposition;
                            break;
                        }
                    }
                    subtitleService.pageComposition = parsePageComposition;
                    subtitleService.regions.clear();
                    subtitleService.cluts.clear();
                    subtitleService.objects.clear();
                    break;
                }
                break;
            case 17:
                pageComposition = subtitleService.pageComposition;
                if (readBits2 == subtitleService.subtitlePageId && pageComposition != null) {
                    RegionComposition parseRegionComposition = parseRegionComposition(parsableBitArray, readBits3);
                    if (pageComposition.state == 0) {
                        parseRegionComposition.mergeFrom((RegionComposition) subtitleService.regions.get(parseRegionComposition.id));
                    }
                    subtitleService.regions.put(parseRegionComposition.id, parseRegionComposition);
                    break;
                }
            case 18:
                ClutDefinition parseClutDefinition;
                if (readBits2 != subtitleService.subtitlePageId) {
                    if (readBits2 == subtitleService.ancillaryPageId) {
                        parseClutDefinition = parseClutDefinition(parsableBitArray, readBits3);
                        subtitleService.ancillaryCluts.put(parseClutDefinition.id, parseClutDefinition);
                        break;
                    }
                }
                parseClutDefinition = parseClutDefinition(parsableBitArray, readBits3);
                subtitleService.cluts.put(parseClutDefinition.id, parseClutDefinition);
                break;
                break;
            case 19:
                ObjectData parseObjectData;
                if (readBits2 != subtitleService.subtitlePageId) {
                    if (readBits2 == subtitleService.ancillaryPageId) {
                        parseObjectData = parseObjectData(parsableBitArray);
                        subtitleService.ancillaryObjects.put(parseObjectData.id, parseObjectData);
                        break;
                    }
                }
                parseObjectData = parseObjectData(parsableBitArray);
                subtitleService.objects.put(parseObjectData.id, parseObjectData);
                break;
                break;
            case 20:
                if (readBits2 == subtitleService.subtitlePageId) {
                    subtitleService.displayDefinition = parseDisplayDefinition(parsableBitArray);
                    break;
                }
                break;
        }
        parsableBitArray.skipBytes(bytePosition - parsableBitArray.getBytePosition());
    }

    public List<Cue> decode(byte[] bArr, int i) {
        ParsableBitArray parsableBitArray = new ParsableBitArray(bArr, i);
        while (parsableBitArray.bitsLeft() >= 48 && parsableBitArray.readBits(8) == 15) {
            parseSubtitlingSegment(parsableBitArray, this.subtitleService);
        }
        if (this.subtitleService.pageComposition == null) {
            return Collections.emptyList();
        }
        DisplayDefinition displayDefinition = this.subtitleService.displayDefinition != null ? this.subtitleService.displayDefinition : this.defaultDisplayDefinition;
        if (!(this.bitmap != null && displayDefinition.width + 1 == this.bitmap.getWidth() && displayDefinition.height + 1 == this.bitmap.getHeight())) {
            this.bitmap = Bitmap.createBitmap(displayDefinition.width + 1, displayDefinition.height + 1, Config.ARGB_8888);
            this.canvas.setBitmap(this.bitmap);
        }
        List<Cue> arrayList = new ArrayList();
        SparseArray sparseArray = this.subtitleService.pageComposition.regions;
        for (int i2 = 0; i2 < sparseArray.size(); i2++) {
            ClutDefinition clutDefinition;
            SparseArray sparseArray2;
            int i3;
            int keyAt;
            RegionObject regionObject;
            ObjectData objectData;
            PageRegion pageRegion = (PageRegion) sparseArray.valueAt(i2);
            RegionComposition regionComposition = (RegionComposition) this.subtitleService.regions.get(sparseArray.keyAt(i2));
            int i4 = pageRegion.horizontalAddress + displayDefinition.horizontalPositionMinimum;
            int i5 = pageRegion.verticalAddress + displayDefinition.verticalPositionMinimum;
            this.canvas.clipRect((float) i4, (float) i5, (float) Math.min(regionComposition.width + i4, displayDefinition.horizontalPositionMaximum), (float) Math.min(regionComposition.height + i5, displayDefinition.verticalPositionMaximum), Op.REPLACE);
            ClutDefinition clutDefinition2 = (ClutDefinition) this.subtitleService.cluts.get(regionComposition.clutId);
            if (clutDefinition2 == null) {
                clutDefinition2 = (ClutDefinition) this.subtitleService.ancillaryCluts.get(regionComposition.clutId);
                if (clutDefinition2 == null) {
                    clutDefinition = this.defaultClutDefinition;
                    sparseArray2 = regionComposition.regionObjects;
                    for (i3 = 0; i3 < sparseArray2.size(); i3++) {
                        keyAt = sparseArray2.keyAt(i3);
                        regionObject = (RegionObject) sparseArray2.valueAt(i3);
                        objectData = (ObjectData) this.subtitleService.objects.get(keyAt);
                        if (objectData == null) {
                            objectData = (ObjectData) this.subtitleService.ancillaryObjects.get(keyAt);
                        }
                        if (objectData == null) {
                            paintPixelDataSubBlocks(objectData, clutDefinition, regionComposition.depth, regionObject.horizontalPosition + i4, regionObject.verticalPosition + i5, objectData.nonModifyingColorFlag ? null : this.defaultPaint, this.canvas);
                        }
                    }
                    if (!regionComposition.fillFlag) {
                        int i6 = regionComposition.depth != 3 ? clutDefinition.clutEntries8Bit[regionComposition.pixelCode8Bit] : regionComposition.depth != 2 ? clutDefinition.clutEntries4Bit[regionComposition.pixelCode4Bit] : clutDefinition.clutEntries2Bit[regionComposition.pixelCode2Bit];
                        this.fillRegionPaint.setColor(i6);
                        this.canvas.drawRect((float) i4, (float) i5, (float) (regionComposition.width + i4), (float) (regionComposition.height + i5), this.fillRegionPaint);
                    }
                    arrayList.add(new Cue(Bitmap.createBitmap(this.bitmap, i4, i5, regionComposition.width, regionComposition.height), ((float) i4) / ((float) displayDefinition.width), 0, ((float) i5) / ((float) displayDefinition.height), 0, ((float) regionComposition.width) / ((float) displayDefinition.width), ((float) regionComposition.height) / ((float) displayDefinition.height)));
                    this.canvas.drawColor(0, Mode.CLEAR);
                }
            }
            clutDefinition = clutDefinition2;
            sparseArray2 = regionComposition.regionObjects;
            for (i3 = 0; i3 < sparseArray2.size(); i3++) {
                keyAt = sparseArray2.keyAt(i3);
                regionObject = (RegionObject) sparseArray2.valueAt(i3);
                objectData = (ObjectData) this.subtitleService.objects.get(keyAt);
                if (objectData == null) {
                    objectData = (ObjectData) this.subtitleService.ancillaryObjects.get(keyAt);
                }
                if (objectData == null) {
                    if (objectData.nonModifyingColorFlag) {
                    }
                    paintPixelDataSubBlocks(objectData, clutDefinition, regionComposition.depth, regionObject.horizontalPosition + i4, regionObject.verticalPosition + i5, objectData.nonModifyingColorFlag ? null : this.defaultPaint, this.canvas);
                }
            }
            if (!regionComposition.fillFlag) {
                if (regionComposition.depth != 3) {
                    if (regionComposition.depth != 2) {
                    }
                }
                this.fillRegionPaint.setColor(i6);
                this.canvas.drawRect((float) i4, (float) i5, (float) (regionComposition.width + i4), (float) (regionComposition.height + i5), this.fillRegionPaint);
            }
            arrayList.add(new Cue(Bitmap.createBitmap(this.bitmap, i4, i5, regionComposition.width, regionComposition.height), ((float) i4) / ((float) displayDefinition.width), 0, ((float) i5) / ((float) displayDefinition.height), 0, ((float) regionComposition.width) / ((float) displayDefinition.width), ((float) regionComposition.height) / ((float) displayDefinition.height)));
            this.canvas.drawColor(0, Mode.CLEAR);
        }
        return arrayList;
    }

    public void reset() {
        this.subtitleService.reset();
    }
}

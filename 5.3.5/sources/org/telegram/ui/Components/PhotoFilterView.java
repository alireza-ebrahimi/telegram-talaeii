package org.telegram.ui.Components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Matrix;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.Drawable;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.os.Build.VERSION;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.gcm.Task;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.DispatchQueue;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaController$SavedFilterState;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Cells.PhotoEditRadioCell;
import org.telegram.ui.Cells.PhotoEditToolCell;
import org.telegram.ui.Components.PhotoEditorSeekBar.PhotoEditorSeekBarDelegate;
import org.telegram.ui.Components.PhotoFilterBlurControl.PhotoFilterLinearBlurControlDelegate;
import org.telegram.ui.Components.PhotoFilterCurvesControl.PhotoFilterCurvesControlDelegate;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;

@SuppressLint({"NewApi"})
public class PhotoFilterView extends FrameLayout {
    private static final int curveDataStep = 2;
    private static final int curveGranularity = 100;
    private Bitmap bitmapToEdit;
    private float blurAngle;
    private PhotoFilterBlurControl blurControl;
    private float blurExcludeBlurSize;
    private Point blurExcludePoint;
    private float blurExcludeSize;
    private ImageView blurItem;
    private FrameLayout blurLayout;
    private TextView blurLinearButton;
    private TextView blurOffButton;
    private TextView blurRadialButton;
    private int blurType;
    private TextView cancelTextView;
    private int contrastTool = 2;
    private float contrastValue;
    private ImageView curveItem;
    private FrameLayout curveLayout;
    private RadioButton[] curveRadioButton = new RadioButton[4];
    private PhotoFilterCurvesControl curvesControl;
    private CurvesToolValue curvesToolValue;
    private TextView doneTextView;
    private EGLThread eglThread;
    private int enhanceTool = 0;
    private float enhanceValue;
    private int exposureTool = 1;
    private float exposureValue;
    private int fadeTool = 5;
    private float fadeValue;
    private int grainTool = 9;
    private float grainValue;
    private int highlightsTool = 6;
    private float highlightsValue;
    private MediaController$SavedFilterState lastState;
    private int orientation;
    private RecyclerListView recyclerListView;
    private int saturationTool = 3;
    private float saturationValue;
    private int selectedTool;
    private int shadowsTool = 7;
    private float shadowsValue;
    private int sharpenTool = 10;
    private float sharpenValue;
    private boolean showOriginal;
    private TextureView textureView;
    private int tintHighlightsColor;
    private int tintHighlightsTool = 12;
    private int tintShadowsColor;
    private int tintShadowsTool = 11;
    private FrameLayout toolsView;
    private ImageView tuneItem;
    private int vignetteTool = 8;
    private float vignetteValue;
    private int warmthTool = 4;
    private float warmthValue;

    /* renamed from: org.telegram.ui.Components.PhotoFilterView$1 */
    class C26741 implements SurfaceTextureListener {

        /* renamed from: org.telegram.ui.Components.PhotoFilterView$1$1 */
        class C26731 implements Runnable {
            C26731() {
            }

            public void run() {
                if (PhotoFilterView.this.eglThread != null) {
                    PhotoFilterView.this.eglThread.requestRender(false, true);
                }
            }
        }

        C26741() {
        }

        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            if (PhotoFilterView.this.eglThread == null && surface != null) {
                PhotoFilterView.this.eglThread = new EGLThread(surface, PhotoFilterView.this.bitmapToEdit);
                PhotoFilterView.this.eglThread.setSurfaceTextureSize(width, height);
                PhotoFilterView.this.eglThread.requestRender(true, true);
            }
        }

        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
            if (PhotoFilterView.this.eglThread != null) {
                PhotoFilterView.this.eglThread.setSurfaceTextureSize(width, height);
                PhotoFilterView.this.eglThread.requestRender(false, true);
                PhotoFilterView.this.eglThread.postRunnable(new C26731());
            }
        }

        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            if (PhotoFilterView.this.eglThread != null) {
                PhotoFilterView.this.eglThread.shutdown();
                PhotoFilterView.this.eglThread = null;
            }
            return true;
        }

        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        }
    }

    /* renamed from: org.telegram.ui.Components.PhotoFilterView$2 */
    class C26752 implements PhotoFilterLinearBlurControlDelegate {
        C26752() {
        }

        public void valueChanged(Point centerPoint, float falloff, float size, float angle) {
            PhotoFilterView.this.blurExcludeSize = size;
            PhotoFilterView.this.blurExcludePoint = centerPoint;
            PhotoFilterView.this.blurExcludeBlurSize = falloff;
            PhotoFilterView.this.blurAngle = angle;
            if (PhotoFilterView.this.eglThread != null) {
                PhotoFilterView.this.eglThread.requestRender(false);
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.PhotoFilterView$3 */
    class C26763 implements PhotoFilterCurvesControlDelegate {
        C26763() {
        }

        public void valueChanged() {
            if (PhotoFilterView.this.eglThread != null) {
                PhotoFilterView.this.eglThread.requestRender(false);
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.PhotoFilterView$4 */
    class C26774 implements OnClickListener {
        C26774() {
        }

        public void onClick(View v) {
            PhotoFilterView.this.selectedTool = 0;
            PhotoFilterView.this.tuneItem.setColorFilter(new PorterDuffColorFilter(-9649153, Mode.MULTIPLY));
            PhotoFilterView.this.blurItem.setColorFilter(null);
            PhotoFilterView.this.curveItem.setColorFilter(null);
            PhotoFilterView.this.switchMode();
        }
    }

    /* renamed from: org.telegram.ui.Components.PhotoFilterView$5 */
    class C26785 implements OnClickListener {
        C26785() {
        }

        public void onClick(View v) {
            PhotoFilterView.this.selectedTool = 1;
            PhotoFilterView.this.tuneItem.setColorFilter(null);
            PhotoFilterView.this.blurItem.setColorFilter(new PorterDuffColorFilter(-9649153, Mode.MULTIPLY));
            PhotoFilterView.this.curveItem.setColorFilter(null);
            PhotoFilterView.this.switchMode();
        }
    }

    /* renamed from: org.telegram.ui.Components.PhotoFilterView$6 */
    class C26796 implements OnClickListener {
        C26796() {
        }

        public void onClick(View v) {
            PhotoFilterView.this.selectedTool = 2;
            PhotoFilterView.this.tuneItem.setColorFilter(null);
            PhotoFilterView.this.blurItem.setColorFilter(null);
            PhotoFilterView.this.curveItem.setColorFilter(new PorterDuffColorFilter(-9649153, Mode.MULTIPLY));
            PhotoFilterView.this.switchMode();
        }
    }

    /* renamed from: org.telegram.ui.Components.PhotoFilterView$7 */
    class C26807 implements OnClickListener {
        C26807() {
        }

        public void onClick(View v) {
            int num = ((Integer) v.getTag()).intValue();
            PhotoFilterView.this.curvesToolValue.activeType = num;
            int a = 0;
            while (a < 4) {
                PhotoFilterView.this.curveRadioButton[a].setChecked(a == num, true);
                a++;
            }
            PhotoFilterView.this.curvesControl.invalidate();
        }
    }

    /* renamed from: org.telegram.ui.Components.PhotoFilterView$8 */
    class C26818 implements OnClickListener {
        C26818() {
        }

        public void onClick(View v) {
            PhotoFilterView.this.blurType = 0;
            PhotoFilterView.this.updateSelectedBlurType();
            PhotoFilterView.this.blurControl.setVisibility(4);
            if (PhotoFilterView.this.eglThread != null) {
                PhotoFilterView.this.eglThread.requestRender(false);
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.PhotoFilterView$9 */
    class C26829 implements OnClickListener {
        C26829() {
        }

        public void onClick(View v) {
            PhotoFilterView.this.blurType = 1;
            PhotoFilterView.this.updateSelectedBlurType();
            PhotoFilterView.this.blurControl.setVisibility(0);
            PhotoFilterView.this.blurControl.setType(1);
            if (PhotoFilterView.this.eglThread != null) {
                PhotoFilterView.this.eglThread.requestRender(false);
            }
        }
    }

    public static class CurvesToolValue {
        public static final int CurvesTypeBlue = 3;
        public static final int CurvesTypeGreen = 2;
        public static final int CurvesTypeLuminance = 0;
        public static final int CurvesTypeRed = 1;
        public int activeType;
        public CurvesValue blueCurve;
        public ByteBuffer curveBuffer;
        public CurvesValue greenCurve;
        public CurvesValue luminanceCurve;
        public CurvesValue redCurve;

        public CurvesToolValue() {
            this.luminanceCurve = new CurvesValue();
            this.redCurve = new CurvesValue();
            this.greenCurve = new CurvesValue();
            this.blueCurve = new CurvesValue();
            this.curveBuffer = null;
            this.curveBuffer = ByteBuffer.allocateDirect(800);
            this.curveBuffer.order(ByteOrder.LITTLE_ENDIAN);
        }

        public void fillBuffer() {
            this.curveBuffer.position(0);
            float[] luminanceCurveData = this.luminanceCurve.getDataPoints();
            float[] redCurveData = this.redCurve.getDataPoints();
            float[] greenCurveData = this.greenCurve.getDataPoints();
            float[] blueCurveData = this.blueCurve.getDataPoints();
            for (int a = 0; a < 200; a++) {
                this.curveBuffer.put((byte) ((int) (redCurveData[a] * 255.0f)));
                this.curveBuffer.put((byte) ((int) (greenCurveData[a] * 255.0f)));
                this.curveBuffer.put((byte) ((int) (blueCurveData[a] * 255.0f)));
                this.curveBuffer.put((byte) ((int) (luminanceCurveData[a] * 255.0f)));
            }
            this.curveBuffer.position(0);
        }

        public boolean shouldBeSkipped() {
            return this.luminanceCurve.isDefault() && this.redCurve.isDefault() && this.greenCurve.isDefault() && this.blueCurve.isDefault();
        }
    }

    public static class CurvesValue {
        public float blacksLevel = 0.0f;
        public float[] cachedDataPoints;
        public float highlightsLevel = 75.0f;
        public float midtonesLevel = 50.0f;
        public float previousBlacksLevel = 0.0f;
        public float previousHighlightsLevel = 75.0f;
        public float previousMidtonesLevel = 50.0f;
        public float previousShadowsLevel = 25.0f;
        public float previousWhitesLevel = 100.0f;
        public float shadowsLevel = 25.0f;
        public float whitesLevel = 100.0f;

        public float[] getDataPoints() {
            if (this.cachedDataPoints == null) {
                interpolateCurve();
            }
            return this.cachedDataPoints;
        }

        public void saveValues() {
            this.previousBlacksLevel = this.blacksLevel;
            this.previousShadowsLevel = this.shadowsLevel;
            this.previousMidtonesLevel = this.midtonesLevel;
            this.previousHighlightsLevel = this.highlightsLevel;
            this.previousWhitesLevel = this.whitesLevel;
        }

        public void restoreValues() {
            this.blacksLevel = this.previousBlacksLevel;
            this.shadowsLevel = this.previousShadowsLevel;
            this.midtonesLevel = this.previousMidtonesLevel;
            this.highlightsLevel = this.previousHighlightsLevel;
            this.whitesLevel = this.previousWhitesLevel;
            interpolateCurve();
        }

        public float[] interpolateCurve() {
            int a;
            float[] points = new float[]{-0.001f, this.blacksLevel / 100.0f, 0.0f, this.blacksLevel / 100.0f, 0.25f, this.shadowsLevel / 100.0f, 0.5f, this.midtonesLevel / 100.0f, 0.75f, this.highlightsLevel / 100.0f, 1.0f, this.whitesLevel / 100.0f, 1.001f, this.whitesLevel / 100.0f};
            ArrayList<Float> dataPoints = new ArrayList(100);
            ArrayList<Float> interpolatedPoints = new ArrayList(100);
            interpolatedPoints.add(Float.valueOf(points[0]));
            interpolatedPoints.add(Float.valueOf(points[1]));
            for (int index = 1; index < (points.length / 2) - 2; index++) {
                float point0x = points[(index - 1) * 2];
                float point0y = points[((index - 1) * 2) + 1];
                float point1x = points[index * 2];
                float point1y = points[(index * 2) + 1];
                float point2x = points[(index + 1) * 2];
                float point2y = points[((index + 1) * 2) + 1];
                float point3x = points[(index + 2) * 2];
                float point3y = points[((index + 2) * 2) + 1];
                for (int i = 1; i < 100; i++) {
                    float t = ((float) i) * 0.01f;
                    float tt = t * t;
                    float ttt = tt * t;
                    float pix = 0.5f * ((((2.0f * point1x) + ((point2x - point0x) * t)) + (((((2.0f * point0x) - (5.0f * point1x)) + (4.0f * point2x)) - point3x) * tt)) + (((((3.0f * point1x) - point0x) - (3.0f * point2x)) + point3x) * ttt));
                    float piy = Math.max(0.0f, Math.min(1.0f, 0.5f * ((((2.0f * point1y) + ((point2y - point0y) * t)) + (((((2.0f * point0y) - (5.0f * point1y)) + (4.0f * point2y)) - point3y) * tt)) + (((((3.0f * point1y) - point0y) - (3.0f * point2y)) + point3y) * ttt))));
                    if (pix > point0x) {
                        interpolatedPoints.add(Float.valueOf(pix));
                        interpolatedPoints.add(Float.valueOf(piy));
                    }
                    if ((i - 1) % 2 == 0) {
                        dataPoints.add(Float.valueOf(piy));
                    }
                }
                interpolatedPoints.add(Float.valueOf(point2x));
                interpolatedPoints.add(Float.valueOf(point2y));
            }
            interpolatedPoints.add(Float.valueOf(points[12]));
            interpolatedPoints.add(Float.valueOf(points[13]));
            this.cachedDataPoints = new float[dataPoints.size()];
            for (a = 0; a < this.cachedDataPoints.length; a++) {
                this.cachedDataPoints[a] = ((Float) dataPoints.get(a)).floatValue();
            }
            float[] retValue = new float[interpolatedPoints.size()];
            for (a = 0; a < retValue.length; a++) {
                retValue[a] = ((Float) interpolatedPoints.get(a)).floatValue();
            }
            return retValue;
        }

        public boolean isDefault() {
            return ((double) Math.abs(this.blacksLevel - 0.0f)) < 1.0E-5d && ((double) Math.abs(this.shadowsLevel - 25.0f)) < 1.0E-5d && ((double) Math.abs(this.midtonesLevel - 50.0f)) < 1.0E-5d && ((double) Math.abs(this.highlightsLevel - 75.0f)) < 1.0E-5d && ((double) Math.abs(this.whitesLevel - 100.0f)) < 1.0E-5d;
        }
    }

    public class EGLThread extends DispatchQueue {
        private static final int PGPhotoEnhanceHistogramBins = 256;
        private static final int PGPhotoEnhanceSegments = 4;
        private static final String blurFragmentShaderCode = "uniform sampler2D sourceImage;varying highp vec2 blurCoordinates[9];void main() {lowp vec4 sum = vec4(0.0);sum += texture2D(sourceImage, blurCoordinates[0]) * 0.133571;sum += texture2D(sourceImage, blurCoordinates[1]) * 0.233308;sum += texture2D(sourceImage, blurCoordinates[2]) * 0.233308;sum += texture2D(sourceImage, blurCoordinates[3]) * 0.135928;sum += texture2D(sourceImage, blurCoordinates[4]) * 0.135928;sum += texture2D(sourceImage, blurCoordinates[5]) * 0.051383;sum += texture2D(sourceImage, blurCoordinates[6]) * 0.051383;sum += texture2D(sourceImage, blurCoordinates[7]) * 0.012595;sum += texture2D(sourceImage, blurCoordinates[8]) * 0.012595;gl_FragColor = sum;}";
        private static final String blurVertexShaderCode = "attribute vec4 position;attribute vec4 inputTexCoord;uniform highp float texelWidthOffset;uniform highp float texelHeightOffset;varying vec2 blurCoordinates[9];void main() {gl_Position = position;vec2 singleStepOffset = vec2(texelWidthOffset, texelHeightOffset);blurCoordinates[0] = inputTexCoord.xy;blurCoordinates[1] = inputTexCoord.xy + singleStepOffset * 1.458430;blurCoordinates[2] = inputTexCoord.xy - singleStepOffset * 1.458430;blurCoordinates[3] = inputTexCoord.xy + singleStepOffset * 3.403985;blurCoordinates[4] = inputTexCoord.xy - singleStepOffset * 3.403985;blurCoordinates[5] = inputTexCoord.xy + singleStepOffset * 5.351806;blurCoordinates[6] = inputTexCoord.xy - singleStepOffset * 5.351806;blurCoordinates[7] = inputTexCoord.xy + singleStepOffset * 7.302940;blurCoordinates[8] = inputTexCoord.xy - singleStepOffset * 7.302940;}";
        private static final String enhanceFragmentShaderCode = "precision highp float;varying vec2 texCoord;uniform sampler2D sourceImage;uniform sampler2D inputImageTexture2;uniform float intensity;float enhance(float value) {const vec2 offset = vec2(0.001953125, 0.03125);value = value + offset.x;vec2 coord = (clamp(texCoord, 0.125, 1.0 - 0.125001) - 0.125) * 4.0;vec2 frac = fract(coord);coord = floor(coord);float p00 = float(coord.y * 4.0 + coord.x) * 0.0625 + offset.y;float p01 = float(coord.y * 4.0 + coord.x + 1.0) * 0.0625 + offset.y;float p10 = float((coord.y + 1.0) * 4.0 + coord.x) * 0.0625 + offset.y;float p11 = float((coord.y + 1.0) * 4.0 + coord.x + 1.0) * 0.0625 + offset.y;vec3 c00 = texture2D(inputImageTexture2, vec2(value, p00)).rgb;vec3 c01 = texture2D(inputImageTexture2, vec2(value, p01)).rgb;vec3 c10 = texture2D(inputImageTexture2, vec2(value, p10)).rgb;vec3 c11 = texture2D(inputImageTexture2, vec2(value, p11)).rgb;float c1 = ((c00.r - c00.g) / (c00.b - c00.g));float c2 = ((c01.r - c01.g) / (c01.b - c01.g));float c3 = ((c10.r - c10.g) / (c10.b - c10.g));float c4 = ((c11.r - c11.g) / (c11.b - c11.g));float c1_2 = mix(c1, c2, frac.x);float c3_4 = mix(c3, c4, frac.x);return mix(c1_2, c3_4, frac.y);}vec3 hsv_to_rgb(vec3 c) {vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);}void main() {vec4 texel = texture2D(sourceImage, texCoord);vec4 hsv = texel;hsv.y = min(1.0, hsv.y * 1.2);hsv.z = min(1.0, enhance(hsv.z) * 1.1);gl_FragColor = vec4(hsv_to_rgb(mix(texel.xyz, hsv.xyz, intensity)), texel.w);}";
        private static final String linearBlurFragmentShaderCode = "varying highp vec2 texCoord;uniform sampler2D sourceImage;uniform sampler2D inputImageTexture2;uniform lowp float excludeSize;uniform lowp vec2 excludePoint;uniform lowp float excludeBlurSize;uniform highp float angle;uniform highp float aspectRatio;void main() {lowp vec4 sharpImageColor = texture2D(sourceImage, texCoord);lowp vec4 blurredImageColor = texture2D(inputImageTexture2, texCoord);highp vec2 texCoordToUse = vec2(texCoord.x, (texCoord.y * aspectRatio + 0.5 - 0.5 * aspectRatio));highp float distanceFromCenter = abs((texCoordToUse.x - excludePoint.x) * aspectRatio * cos(angle) + (texCoordToUse.y - excludePoint.y) * sin(angle));gl_FragColor = mix(sharpImageColor, blurredImageColor, smoothstep(excludeSize - excludeBlurSize, excludeSize, distanceFromCenter));}";
        private static final String radialBlurFragmentShaderCode = "varying highp vec2 texCoord;uniform sampler2D sourceImage;uniform sampler2D inputImageTexture2;uniform lowp float excludeSize;uniform lowp vec2 excludePoint;uniform lowp float excludeBlurSize;uniform highp float aspectRatio;void main() {lowp vec4 sharpImageColor = texture2D(sourceImage, texCoord);lowp vec4 blurredImageColor = texture2D(inputImageTexture2, texCoord);highp vec2 texCoordToUse = vec2(texCoord.x, (texCoord.y * aspectRatio + 0.5 - 0.5 * aspectRatio));highp float distanceFromCenter = distance(excludePoint, texCoordToUse);gl_FragColor = mix(sharpImageColor, blurredImageColor, smoothstep(excludeSize - excludeBlurSize, excludeSize, distanceFromCenter));}";
        private static final String rgbToHsvFragmentShaderCode = "precision highp float;varying vec2 texCoord;uniform sampler2D sourceImage;vec3 rgb_to_hsv(vec3 c) {vec4 K = vec4(0.0, -1.0 / 3.0, 2.0 / 3.0, -1.0);vec4 p = c.g < c.b ? vec4(c.bg, K.wz) : vec4(c.gb, K.xy);vec4 q = c.r < p.x ? vec4(p.xyw, c.r) : vec4(c.r, p.yzx);float d = q.x - min(q.w, q.y);float e = 1.0e-10;return vec3(abs(q.z + (q.w - q.y) / (6.0 * d + e)), d / (q.x + e), q.x);}void main() {vec4 texel = texture2D(sourceImage, texCoord);gl_FragColor = vec4(rgb_to_hsv(texel.rgb), texel.a);}";
        private static final String sharpenFragmentShaderCode = "precision highp float;varying vec2 texCoord;varying vec2 leftTexCoord;varying vec2 rightTexCoord;varying vec2 topTexCoord;varying vec2 bottomTexCoord;uniform sampler2D sourceImage;uniform float sharpen;void main() {vec4 result = texture2D(sourceImage, texCoord);vec3 leftTextureColor = texture2D(sourceImage, leftTexCoord).rgb;vec3 rightTextureColor = texture2D(sourceImage, rightTexCoord).rgb;vec3 topTextureColor = texture2D(sourceImage, topTexCoord).rgb;vec3 bottomTextureColor = texture2D(sourceImage, bottomTexCoord).rgb;result.rgb = result.rgb * (1.0 + 4.0 * sharpen) - (leftTextureColor + rightTextureColor + topTextureColor + bottomTextureColor) * sharpen;gl_FragColor = result;}";
        private static final String sharpenVertexShaderCode = "attribute vec4 position;attribute vec2 inputTexCoord;varying vec2 texCoord;uniform highp float inputWidth;uniform highp float inputHeight;varying vec2 leftTexCoord;varying vec2 rightTexCoord;varying vec2 topTexCoord;varying vec2 bottomTexCoord;void main() {gl_Position = position;texCoord = inputTexCoord;highp vec2 widthStep = vec2(1.0 / inputWidth, 0.0);highp vec2 heightStep = vec2(0.0, 1.0 / inputHeight);leftTexCoord = inputTexCoord - widthStep;rightTexCoord = inputTexCoord + widthStep;topTexCoord = inputTexCoord + heightStep;bottomTexCoord = inputTexCoord - heightStep;}";
        private static final String simpleFragmentShaderCode = "varying highp vec2 texCoord;uniform sampler2D sourceImage;void main() {gl_FragColor = texture2D(sourceImage, texCoord);}";
        private static final String simpleVertexShaderCode = "attribute vec4 position;attribute vec2 inputTexCoord;varying vec2 texCoord;void main() {gl_Position = position;texCoord = inputTexCoord;}";
        private static final String toolsFragmentShaderCode = "varying highp vec2 texCoord;uniform sampler2D sourceImage;uniform highp float width;uniform highp float height;uniform sampler2D curvesImage;uniform lowp float skipTone;uniform lowp float shadows;const mediump vec3 hsLuminanceWeighting = vec3(0.3, 0.3, 0.3);uniform lowp float highlights;uniform lowp float contrast;uniform lowp float fadeAmount;const mediump vec3 satLuminanceWeighting = vec3(0.2126, 0.7152, 0.0722);uniform lowp float saturation;uniform lowp float shadowsTintIntensity;uniform lowp float highlightsTintIntensity;uniform lowp vec3 shadowsTintColor;uniform lowp vec3 highlightsTintColor;uniform lowp float exposure;uniform lowp float warmth;uniform lowp float grain;const lowp float permTexUnit = 1.0 / 256.0;const lowp float permTexUnitHalf = 0.5 / 256.0;const lowp float grainsize = 2.3;uniform lowp float vignette;highp float getLuma(highp vec3 rgbP) {return (0.299 * rgbP.r) + (0.587 * rgbP.g) + (0.114 * rgbP.b);}lowp vec3 rgbToHsv(lowp vec3 c) {highp vec4 K = vec4(0.0, -1.0 / 3.0, 2.0 / 3.0, -1.0);highp vec4 p = c.g < c.b ? vec4(c.bg, K.wz) : vec4(c.gb, K.xy);highp vec4 q = c.r < p.x ? vec4(p.xyw, c.r) : vec4(c.r, p.yzx);highp float d = q.x - min(q.w, q.y);highp float e = 1.0e-10;return vec3(abs(q.z + (q.w - q.y) / (6.0 * d + e)), d / (q.x + e), q.x);}lowp vec3 hsvToRgb(lowp vec3 c) {highp vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);highp vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);}highp vec3 rgbToHsl(highp vec3 color) {highp vec3 hsl;highp float fmin = min(min(color.r, color.g), color.b);highp float fmax = max(max(color.r, color.g), color.b);highp float delta = fmax - fmin;hsl.z = (fmax + fmin) / 2.0;if (delta == 0.0) {hsl.x = 0.0;hsl.y = 0.0;} else {if (hsl.z < 0.5) {hsl.y = delta / (fmax + fmin);} else {hsl.y = delta / (2.0 - fmax - fmin);}highp float deltaR = (((fmax - color.r) / 6.0) + (delta / 2.0)) / delta;highp float deltaG = (((fmax - color.g) / 6.0) + (delta / 2.0)) / delta;highp float deltaB = (((fmax - color.b) / 6.0) + (delta / 2.0)) / delta;if (color.r == fmax) {hsl.x = deltaB - deltaG;} else if (color.g == fmax) {hsl.x = (1.0 / 3.0) + deltaR - deltaB;} else if (color.b == fmax) {hsl.x = (2.0 / 3.0) + deltaG - deltaR;}if (hsl.x < 0.0) {hsl.x += 1.0;} else if (hsl.x > 1.0) {hsl.x -= 1.0;}}return hsl;}highp float hueToRgb(highp float f1, highp float f2, highp float hue) {if (hue < 0.0) {hue += 1.0;} else if (hue > 1.0) {hue -= 1.0;}highp float res;if ((6.0 * hue) < 1.0) {res = f1 + (f2 - f1) * 6.0 * hue;} else if ((2.0 * hue) < 1.0) {res = f2;} else if ((3.0 * hue) < 2.0) {res = f1 + (f2 - f1) * ((2.0 / 3.0) - hue) * 6.0;} else {res = f1;} return res;}highp vec3 hslToRgb(highp vec3 hsl) {if (hsl.y == 0.0) {return vec3(hsl.z);} else {highp float f2;if (hsl.z < 0.5) {f2 = hsl.z * (1.0 + hsl.y);} else {f2 = (hsl.z + hsl.y) - (hsl.y * hsl.z);}highp float f1 = 2.0 * hsl.z - f2;return vec3(hueToRgb(f1, f2, hsl.x + (1.0/3.0)), hueToRgb(f1, f2, hsl.x), hueToRgb(f1, f2, hsl.x - (1.0/3.0)));}}highp vec3 rgbToYuv(highp vec3 inP) {highp float luma = getLuma(inP);return vec3(luma, (1.0 / 1.772) * (inP.b - luma), (1.0 / 1.402) * (inP.r - luma));}lowp vec3 yuvToRgb(highp vec3 inP) {return vec3(1.402 * inP.b + inP.r, (inP.r - (0.299 * 1.402 / 0.587) * inP.b - (0.114 * 1.772 / 0.587) * inP.g), 1.772 * inP.g + inP.r);}lowp float easeInOutSigmoid(lowp float value, lowp float strength) {if (value > 0.5) {return 1.0 - pow(2.0 - 2.0 * value, 1.0 / (1.0 - strength)) * 0.5;} else {return pow(2.0 * value, 1.0 / (1.0 - strength)) * 0.5;}}lowp vec3 applyLuminanceCurve(lowp vec3 pixel) {highp float index = floor(clamp(pixel.z / (1.0 / 200.0), 0.0, 199.0));pixel.y = mix(0.0, pixel.y, smoothstep(0.0, 0.1, pixel.z) * (1.0 - smoothstep(0.8, 1.0, pixel.z)));pixel.z = texture2D(curvesImage, vec2(1.0 / 200.0 * index, 0)).a;return pixel;}lowp vec3 applyRGBCurve(lowp vec3 pixel) {highp float index = floor(clamp(pixel.r / (1.0 / 200.0), 0.0, 199.0));pixel.r = texture2D(curvesImage, vec2(1.0 / 200.0 * index, 0)).r;index = floor(clamp(pixel.g / (1.0 / 200.0), 0.0, 199.0));pixel.g = clamp(texture2D(curvesImage, vec2(1.0 / 200.0 * index, 0)).g, 0.0, 1.0);index = floor(clamp(pixel.b / (1.0 / 200.0), 0.0, 199.0));pixel.b = clamp(texture2D(curvesImage, vec2(1.0 / 200.0 * index, 0)).b, 0.0, 1.0);return pixel;}highp vec3 fadeAdjust(highp vec3 color, highp float fadeVal) {return (color * (1.0 - fadeVal)) + ((color + (vec3(-0.9772) * pow(vec3(color), vec3(3.0)) + vec3(1.708) * pow(vec3(color), vec3(2.0)) + vec3(-0.1603) * vec3(color) + vec3(0.2878) - color * vec3(0.9))) * fadeVal);}lowp vec3 tintRaiseShadowsCurve(lowp vec3 color) {return vec3(-0.003671) * pow(color, vec3(3.0)) + vec3(0.3842) * pow(color, vec3(2.0)) + vec3(0.3764) * color + vec3(0.2515);}lowp vec3 tintShadows(lowp vec3 texel, lowp vec3 tintColor, lowp float tintAmount) {return clamp(mix(texel, mix(texel, tintRaiseShadowsCurve(texel), tintColor), tintAmount), 0.0, 1.0);} lowp vec3 tintHighlights(lowp vec3 texel, lowp vec3 tintColor, lowp float tintAmount) {return clamp(mix(texel, mix(texel, vec3(1.0) - tintRaiseShadowsCurve(vec3(1.0) - texel), (vec3(1.0) - tintColor)), tintAmount), 0.0, 1.0);}highp vec4 rnm(in highp vec2 tc) {highp float noise = sin(dot(tc, vec2(12.9898, 78.233))) * 43758.5453;return vec4(fract(noise), fract(noise * 1.2154), fract(noise * 1.3453), fract(noise * 1.3647)) * 2.0 - 1.0;}highp float fade(in highp float t) {return t * t * t * (t * (t * 6.0 - 15.0) + 10.0);}highp float pnoise3D(in highp vec3 p) {highp vec3 pi = permTexUnit * floor(p) + permTexUnitHalf;highp vec3 pf = fract(p);highp float perm = rnm(pi.xy).a;highp float n000 = dot(rnm(vec2(perm, pi.z)).rgb * 4.0 - 1.0, pf);highp float n001 = dot(rnm(vec2(perm, pi.z + permTexUnit)).rgb * 4.0 - 1.0, pf - vec3(0.0, 0.0, 1.0));perm = rnm(pi.xy + vec2(0.0, permTexUnit)).a;highp float n010 = dot(rnm(vec2(perm, pi.z)).rgb * 4.0 - 1.0, pf - vec3(0.0, 1.0, 0.0));highp float n011 = dot(rnm(vec2(perm, pi.z + permTexUnit)).rgb * 4.0 - 1.0, pf - vec3(0.0, 1.0, 1.0));perm = rnm(pi.xy + vec2(permTexUnit, 0.0)).a;highp float n100 = dot(rnm(vec2(perm, pi.z)).rgb * 4.0 - 1.0, pf - vec3(1.0, 0.0, 0.0));highp float n101 = dot(rnm(vec2(perm, pi.z + permTexUnit)).rgb * 4.0 - 1.0, pf - vec3(1.0, 0.0, 1.0));perm = rnm(pi.xy + vec2(permTexUnit, permTexUnit)).a;highp float n110 = dot(rnm(vec2(perm, pi.z)).rgb * 4.0 - 1.0, pf - vec3(1.0, 1.0, 0.0));highp float n111 = dot(rnm(vec2(perm, pi.z + permTexUnit)).rgb * 4.0 - 1.0, pf - vec3(1.0, 1.0, 1.0));highp vec4 n_x = mix(vec4(n000, n001, n010, n011), vec4(n100, n101, n110, n111), fade(pf.x));highp vec2 n_xy = mix(n_x.xy, n_x.zw, fade(pf.y));return mix(n_xy.x, n_xy.y, fade(pf.z));}lowp vec2 coordRot(in lowp vec2 tc, in lowp float angle) {return vec2(((tc.x * 2.0 - 1.0) * cos(angle) - (tc.y * 2.0 - 1.0) * sin(angle)) * 0.5 + 0.5, ((tc.y * 2.0 - 1.0) * cos(angle) + (tc.x * 2.0 - 1.0) * sin(angle)) * 0.5 + 0.5);}void main() {lowp vec4 source = texture2D(sourceImage, texCoord);lowp vec4 result = source;const lowp float toolEpsilon = 0.005;if (skipTone < toolEpsilon) {result = vec4(applyRGBCurve(hslToRgb(applyLuminanceCurve(rgbToHsl(result.rgb)))), result.a);}mediump float hsLuminance = dot(result.rgb, hsLuminanceWeighting);mediump float shadow = clamp((pow(hsLuminance, 1.0 / shadows) + (-0.76) * pow(hsLuminance, 2.0 / shadows)) - hsLuminance, 0.0, 1.0);mediump float highlight = clamp((1.0 - (pow(1.0 - hsLuminance, 1.0 / (2.0 - highlights)) + (-0.8) * pow(1.0 - hsLuminance, 2.0 / (2.0 - highlights)))) - hsLuminance, -1.0, 0.0);lowp vec3 hsresult = vec3(0.0, 0.0, 0.0) + ((hsLuminance + shadow + highlight) - 0.0) * ((result.rgb - vec3(0.0, 0.0, 0.0)) / (hsLuminance - 0.0));mediump float contrastedLuminance = ((hsLuminance - 0.5) * 1.5) + 0.5;mediump float whiteInterp = contrastedLuminance * contrastedLuminance * contrastedLuminance;mediump float whiteTarget = clamp(highlights, 1.0, 2.0) - 1.0;hsresult = mix(hsresult, vec3(1.0), whiteInterp * whiteTarget);mediump float invContrastedLuminance = 1.0 - contrastedLuminance;mediump float blackInterp = invContrastedLuminance * invContrastedLuminance * invContrastedLuminance;mediump float blackTarget = 1.0 - clamp(shadows, 0.0, 1.0);hsresult = mix(hsresult, vec3(0.0), blackInterp * blackTarget);result = vec4(hsresult.rgb, result.a);result = vec4(clamp(((result.rgb - vec3(0.5)) * contrast + vec3(0.5)), 0.0, 1.0), result.a);if (abs(fadeAmount) > toolEpsilon) {result.rgb = fadeAdjust(result.rgb, fadeAmount);}lowp float satLuminance = dot(result.rgb, satLuminanceWeighting);lowp vec3 greyScaleColor = vec3(satLuminance);result = vec4(clamp(mix(greyScaleColor, result.rgb, saturation), 0.0, 1.0), result.a);if (abs(shadowsTintIntensity) > toolEpsilon) {result.rgb = tintShadows(result.rgb, shadowsTintColor, shadowsTintIntensity * 2.0);}if (abs(highlightsTintIntensity) > toolEpsilon) {result.rgb = tintHighlights(result.rgb, highlightsTintColor, highlightsTintIntensity * 2.0);}if (abs(exposure) > toolEpsilon) {mediump float mag = exposure * 1.045;mediump float exppower = 1.0 + abs(mag);if (mag < 0.0) {exppower = 1.0 / exppower;}result.r = 1.0 - pow((1.0 - result.r), exppower);result.g = 1.0 - pow((1.0 - result.g), exppower);result.b = 1.0 - pow((1.0 - result.b), exppower);}if (abs(warmth) > toolEpsilon) {highp vec3 yuvVec;if (warmth > 0.0 ) {yuvVec = vec3(0.1765, -0.1255, 0.0902);} else {yuvVec = -vec3(0.0588, 0.1569, -0.1255);}highp vec3 yuvColor = rgbToYuv(result.rgb);highp float luma = yuvColor.r;highp float curveScale = sin(luma * 3.14159);yuvColor += 0.375 * warmth * curveScale * yuvVec;result.rgb = yuvToRgb(yuvColor);}if (abs(grain) > toolEpsilon) {highp vec3 rotOffset = vec3(1.425, 3.892, 5.835);highp vec2 rotCoordsR = coordRot(texCoord, rotOffset.x);highp vec3 noise = vec3(pnoise3D(vec3(rotCoordsR * vec2(width / grainsize, height / grainsize),0.0)));lowp vec3 lumcoeff = vec3(0.299,0.587,0.114);lowp float luminance = dot(result.rgb, lumcoeff);lowp float lum = smoothstep(0.2, 0.0, luminance);lum += luminance;noise = mix(noise,vec3(0.0),pow(lum,4.0));result.rgb = result.rgb + noise * grain;}if (abs(vignette) > toolEpsilon) {const lowp float midpoint = 0.7;const lowp float fuzziness = 0.62;lowp float radDist = length(texCoord - 0.5) / sqrt(0.5);lowp float mag = easeInOutSigmoid(radDist * midpoint, fuzziness) * vignette * 0.645;result.rgb = mix(pow(result.rgb, vec3(1.0 / (1.0 - mag))), vec3(0.0), mag * mag);}gl_FragColor = result;}";
        private final int EGL_CONTEXT_CLIENT_VERSION = 12440;
        private final int EGL_OPENGL_ES2_BIT = 4;
        private int blurHeightHandle;
        private int blurInputTexCoordHandle;
        private int blurPositionHandle;
        private int blurShaderProgram;
        private int blurSourceImageHandle;
        private int blurWidthHandle;
        private boolean blured;
        private int contrastHandle;
        private Bitmap currentBitmap;
        private int[] curveTextures = new int[1];
        private int curvesImageHandle;
        private Runnable drawRunnable = new C26831();
        private EGL10 egl10;
        private EGLConfig eglConfig;
        private EGLContext eglContext;
        private EGLDisplay eglDisplay;
        private EGLSurface eglSurface;
        private int enhanceInputImageTexture2Handle;
        private int enhanceInputTexCoordHandle;
        private int enhanceIntensityHandle;
        private int enhancePositionHandle;
        private int enhanceShaderProgram;
        private int enhanceSourceImageHandle;
        private int[] enhanceTextures = new int[2];
        private int exposureHandle;
        private int fadeAmountHandle;
        private GL gl;
        private int grainHandle;
        private int heightHandle;
        private int highlightsHandle;
        private int highlightsTintColorHandle;
        private int highlightsTintIntensityHandle;
        private boolean hsvGenerated;
        private boolean initied;
        private int inputTexCoordHandle;
        private long lastRenderCallTime;
        private int linearBlurAngleHandle;
        private int linearBlurAspectRatioHandle;
        private int linearBlurExcludeBlurSizeHandle;
        private int linearBlurExcludePointHandle;
        private int linearBlurExcludeSizeHandle;
        private int linearBlurInputTexCoordHandle;
        private int linearBlurPositionHandle;
        private int linearBlurShaderProgram;
        private int linearBlurSourceImage2Handle;
        private int linearBlurSourceImageHandle;
        private boolean needUpdateBlurTexture = true;
        private int positionHandle;
        private int radialBlurAspectRatioHandle;
        private int radialBlurExcludeBlurSizeHandle;
        private int radialBlurExcludePointHandle;
        private int radialBlurExcludeSizeHandle;
        private int radialBlurInputTexCoordHandle;
        private int radialBlurPositionHandle;
        private int radialBlurShaderProgram;
        private int radialBlurSourceImage2Handle;
        private int radialBlurSourceImageHandle;
        private int renderBufferHeight;
        private int renderBufferWidth;
        private int[] renderFrameBuffer = new int[3];
        private int[] renderTexture = new int[3];
        private int rgbToHsvInputTexCoordHandle;
        private int rgbToHsvPositionHandle;
        private int rgbToHsvShaderProgram;
        private int rgbToHsvSourceImageHandle;
        private int saturationHandle;
        private int shadowsHandle;
        private int shadowsTintColorHandle;
        private int shadowsTintIntensityHandle;
        private int sharpenHandle;
        private int sharpenHeightHandle;
        private int sharpenInputTexCoordHandle;
        private int sharpenPositionHandle;
        private int sharpenShaderProgram;
        private int sharpenSourceImageHandle;
        private int sharpenWidthHandle;
        private int simpleInputTexCoordHandle;
        private int simplePositionHandle;
        private int simpleShaderProgram;
        private int simpleSourceImageHandle;
        private int skipToneHandle;
        private int sourceImageHandle;
        private volatile int surfaceHeight;
        private SurfaceTexture surfaceTexture;
        private volatile int surfaceWidth;
        private FloatBuffer textureBuffer;
        private int toolsShaderProgram;
        private FloatBuffer vertexBuffer;
        private FloatBuffer vertexInvertBuffer;
        private int vignetteHandle;
        private int warmthHandle;
        private int widthHandle;

        /* renamed from: org.telegram.ui.Components.PhotoFilterView$EGLThread$1 */
        class C26831 implements Runnable {
            C26831() {
            }

            public void run() {
                if (!EGLThread.this.initied) {
                    return;
                }
                if ((EGLThread.this.eglContext.equals(EGLThread.this.egl10.eglGetCurrentContext()) && EGLThread.this.eglSurface.equals(EGLThread.this.egl10.eglGetCurrentSurface(12377))) || EGLThread.this.egl10.eglMakeCurrent(EGLThread.this.eglDisplay, EGLThread.this.eglSurface, EGLThread.this.eglSurface, EGLThread.this.eglContext)) {
                    GLES20.glViewport(0, 0, EGLThread.this.renderBufferWidth, EGLThread.this.renderBufferHeight);
                    EGLThread.this.drawEnhancePass();
                    EGLThread.this.drawSharpenPass();
                    EGLThread.this.drawCustomParamsPass();
                    EGLThread.this.blured = EGLThread.this.drawBlurPass();
                    GLES20.glViewport(0, 0, EGLThread.this.surfaceWidth, EGLThread.this.surfaceHeight);
                    GLES20.glBindFramebuffer(36160, 0);
                    GLES20.glClear(0);
                    GLES20.glUseProgram(EGLThread.this.simpleShaderProgram);
                    GLES20.glActiveTexture(33984);
                    GLES20.glBindTexture(3553, EGLThread.this.renderTexture[EGLThread.this.blured ? 0 : 1]);
                    GLES20.glUniform1i(EGLThread.this.simpleSourceImageHandle, 0);
                    GLES20.glEnableVertexAttribArray(EGLThread.this.simpleInputTexCoordHandle);
                    GLES20.glVertexAttribPointer(EGLThread.this.simpleInputTexCoordHandle, 2, 5126, false, 8, EGLThread.this.textureBuffer);
                    GLES20.glEnableVertexAttribArray(EGLThread.this.simplePositionHandle);
                    GLES20.glVertexAttribPointer(EGLThread.this.simplePositionHandle, 2, 5126, false, 8, EGLThread.this.vertexBuffer);
                    GLES20.glDrawArrays(5, 0, 4);
                    EGLThread.this.egl10.eglSwapBuffers(EGLThread.this.eglDisplay, EGLThread.this.eglSurface);
                    return;
                }
                FileLog.e("eglMakeCurrent failed " + GLUtils.getEGLErrorString(EGLThread.this.egl10.eglGetError()));
            }
        }

        /* renamed from: org.telegram.ui.Components.PhotoFilterView$EGLThread$3 */
        class C26853 implements Runnable {
            C26853() {
            }

            public void run() {
                EGLThread.this.finish();
                EGLThread.this.currentBitmap = null;
                Looper looper = Looper.myLooper();
                if (looper != null) {
                    looper.quit();
                }
            }
        }

        public EGLThread(SurfaceTexture surface, Bitmap bitmap) {
            super("EGLThread");
            this.surfaceTexture = surface;
            this.currentBitmap = bitmap;
        }

        private int loadShader(int type, String shaderCode) {
            int shader = GLES20.glCreateShader(type);
            GLES20.glShaderSource(shader, shaderCode);
            GLES20.glCompileShader(shader);
            int[] compileStatus = new int[1];
            GLES20.glGetShaderiv(shader, 35713, compileStatus, 0);
            if (compileStatus[0] != 0) {
                return shader;
            }
            FileLog.e(GLES20.glGetShaderInfoLog(shader));
            GLES20.glDeleteShader(shader);
            return 0;
        }

        private boolean initGL() {
            this.egl10 = (EGL10) EGLContext.getEGL();
            this.eglDisplay = this.egl10.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
            if (this.eglDisplay == EGL10.EGL_NO_DISPLAY) {
                FileLog.e("eglGetDisplay failed " + GLUtils.getEGLErrorString(this.egl10.eglGetError()));
                finish();
                return false;
            }
            if (this.egl10.eglInitialize(this.eglDisplay, new int[2])) {
                int[] configsCount = new int[1];
                EGLConfig[] configs = new EGLConfig[1];
                if (!this.egl10.eglChooseConfig(this.eglDisplay, new int[]{12352, 4, 12324, 8, 12323, 8, 12322, 8, 12321, 8, 12325, 0, 12326, 0, 12344}, configs, 1, configsCount)) {
                    FileLog.e("eglChooseConfig failed " + GLUtils.getEGLErrorString(this.egl10.eglGetError()));
                    finish();
                    return false;
                } else if (configsCount[0] > 0) {
                    this.eglConfig = configs[0];
                    this.eglContext = this.egl10.eglCreateContext(this.eglDisplay, this.eglConfig, EGL10.EGL_NO_CONTEXT, new int[]{12440, 2, 12344});
                    if (this.eglContext == null) {
                        FileLog.e("eglCreateContext failed " + GLUtils.getEGLErrorString(this.egl10.eglGetError()));
                        finish();
                        return false;
                    } else if (this.surfaceTexture instanceof SurfaceTexture) {
                        this.eglSurface = this.egl10.eglCreateWindowSurface(this.eglDisplay, this.eglConfig, this.surfaceTexture, null);
                        if (this.eglSurface == null || this.eglSurface == EGL10.EGL_NO_SURFACE) {
                            FileLog.e("createWindowSurface failed " + GLUtils.getEGLErrorString(this.egl10.eglGetError()));
                            finish();
                            return false;
                        }
                        if (this.egl10.eglMakeCurrent(this.eglDisplay, this.eglSurface, this.eglSurface, this.eglContext)) {
                            this.gl = this.eglContext.getGL();
                            float[] squareCoordinates = new float[]{-1.0f, 1.0f, 1.0f, 1.0f, -1.0f, -1.0f, 1.0f, -1.0f};
                            ByteBuffer bb = ByteBuffer.allocateDirect(squareCoordinates.length * 4);
                            bb.order(ByteOrder.nativeOrder());
                            this.vertexBuffer = bb.asFloatBuffer();
                            this.vertexBuffer.put(squareCoordinates);
                            this.vertexBuffer.position(0);
                            float[] squareCoordinates2 = new float[]{-1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f};
                            bb = ByteBuffer.allocateDirect(squareCoordinates2.length * 4);
                            bb.order(ByteOrder.nativeOrder());
                            this.vertexInvertBuffer = bb.asFloatBuffer();
                            this.vertexInvertBuffer.put(squareCoordinates2);
                            this.vertexInvertBuffer.position(0);
                            float[] textureCoordinates = new float[]{0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f};
                            bb = ByteBuffer.allocateDirect(textureCoordinates.length * 4);
                            bb.order(ByteOrder.nativeOrder());
                            this.textureBuffer = bb.asFloatBuffer();
                            this.textureBuffer.put(textureCoordinates);
                            this.textureBuffer.position(0);
                            GLES20.glGenTextures(1, this.curveTextures, 0);
                            GLES20.glGenTextures(2, this.enhanceTextures, 0);
                            int vertexShader = loadShader(35633, simpleVertexShaderCode);
                            int fragmentShader = loadShader(35632, toolsFragmentShaderCode);
                            if (vertexShader == 0 || fragmentShader == 0) {
                                finish();
                                return false;
                            }
                            this.toolsShaderProgram = GLES20.glCreateProgram();
                            GLES20.glAttachShader(this.toolsShaderProgram, vertexShader);
                            GLES20.glAttachShader(this.toolsShaderProgram, fragmentShader);
                            GLES20.glBindAttribLocation(this.toolsShaderProgram, 0, "position");
                            GLES20.glBindAttribLocation(this.toolsShaderProgram, 1, "inputTexCoord");
                            GLES20.glLinkProgram(this.toolsShaderProgram);
                            int[] linkStatus = new int[1];
                            GLES20.glGetProgramiv(this.toolsShaderProgram, 35714, linkStatus, 0);
                            if (linkStatus[0] == 0) {
                                GLES20.glDeleteProgram(this.toolsShaderProgram);
                                this.toolsShaderProgram = 0;
                            } else {
                                this.positionHandle = GLES20.glGetAttribLocation(this.toolsShaderProgram, "position");
                                this.inputTexCoordHandle = GLES20.glGetAttribLocation(this.toolsShaderProgram, "inputTexCoord");
                                this.sourceImageHandle = GLES20.glGetUniformLocation(this.toolsShaderProgram, "sourceImage");
                                this.shadowsHandle = GLES20.glGetUniformLocation(this.toolsShaderProgram, "shadows");
                                this.highlightsHandle = GLES20.glGetUniformLocation(this.toolsShaderProgram, "highlights");
                                this.exposureHandle = GLES20.glGetUniformLocation(this.toolsShaderProgram, "exposure");
                                this.contrastHandle = GLES20.glGetUniformLocation(this.toolsShaderProgram, "contrast");
                                this.saturationHandle = GLES20.glGetUniformLocation(this.toolsShaderProgram, "saturation");
                                this.warmthHandle = GLES20.glGetUniformLocation(this.toolsShaderProgram, "warmth");
                                this.vignetteHandle = GLES20.glGetUniformLocation(this.toolsShaderProgram, "vignette");
                                this.grainHandle = GLES20.glGetUniformLocation(this.toolsShaderProgram, "grain");
                                this.widthHandle = GLES20.glGetUniformLocation(this.toolsShaderProgram, SettingsJsonConstants.ICON_WIDTH_KEY);
                                this.heightHandle = GLES20.glGetUniformLocation(this.toolsShaderProgram, "height");
                                this.curvesImageHandle = GLES20.glGetUniformLocation(this.toolsShaderProgram, "curvesImage");
                                this.skipToneHandle = GLES20.glGetUniformLocation(this.toolsShaderProgram, "skipTone");
                                this.fadeAmountHandle = GLES20.glGetUniformLocation(this.toolsShaderProgram, "fadeAmount");
                                this.shadowsTintIntensityHandle = GLES20.glGetUniformLocation(this.toolsShaderProgram, "shadowsTintIntensity");
                                this.highlightsTintIntensityHandle = GLES20.glGetUniformLocation(this.toolsShaderProgram, "highlightsTintIntensity");
                                this.shadowsTintColorHandle = GLES20.glGetUniformLocation(this.toolsShaderProgram, "shadowsTintColor");
                                this.highlightsTintColorHandle = GLES20.glGetUniformLocation(this.toolsShaderProgram, "highlightsTintColor");
                            }
                            vertexShader = loadShader(35633, sharpenVertexShaderCode);
                            fragmentShader = loadShader(35632, sharpenFragmentShaderCode);
                            if (vertexShader == 0 || fragmentShader == 0) {
                                finish();
                                return false;
                            }
                            this.sharpenShaderProgram = GLES20.glCreateProgram();
                            GLES20.glAttachShader(this.sharpenShaderProgram, vertexShader);
                            GLES20.glAttachShader(this.sharpenShaderProgram, fragmentShader);
                            GLES20.glBindAttribLocation(this.sharpenShaderProgram, 0, "position");
                            GLES20.glBindAttribLocation(this.sharpenShaderProgram, 1, "inputTexCoord");
                            GLES20.glLinkProgram(this.sharpenShaderProgram);
                            linkStatus = new int[1];
                            GLES20.glGetProgramiv(this.sharpenShaderProgram, 35714, linkStatus, 0);
                            if (linkStatus[0] == 0) {
                                GLES20.glDeleteProgram(this.sharpenShaderProgram);
                                this.sharpenShaderProgram = 0;
                            } else {
                                this.sharpenPositionHandle = GLES20.glGetAttribLocation(this.sharpenShaderProgram, "position");
                                this.sharpenInputTexCoordHandle = GLES20.glGetAttribLocation(this.sharpenShaderProgram, "inputTexCoord");
                                this.sharpenSourceImageHandle = GLES20.glGetUniformLocation(this.sharpenShaderProgram, "sourceImage");
                                this.sharpenWidthHandle = GLES20.glGetUniformLocation(this.sharpenShaderProgram, "inputWidth");
                                this.sharpenHeightHandle = GLES20.glGetUniformLocation(this.sharpenShaderProgram, "inputHeight");
                                this.sharpenHandle = GLES20.glGetUniformLocation(this.sharpenShaderProgram, "sharpen");
                            }
                            vertexShader = loadShader(35633, blurVertexShaderCode);
                            fragmentShader = loadShader(35632, blurFragmentShaderCode);
                            if (vertexShader == 0 || fragmentShader == 0) {
                                finish();
                                return false;
                            }
                            this.blurShaderProgram = GLES20.glCreateProgram();
                            GLES20.glAttachShader(this.blurShaderProgram, vertexShader);
                            GLES20.glAttachShader(this.blurShaderProgram, fragmentShader);
                            GLES20.glBindAttribLocation(this.blurShaderProgram, 0, "position");
                            GLES20.glBindAttribLocation(this.blurShaderProgram, 1, "inputTexCoord");
                            GLES20.glLinkProgram(this.blurShaderProgram);
                            linkStatus = new int[1];
                            GLES20.glGetProgramiv(this.blurShaderProgram, 35714, linkStatus, 0);
                            if (linkStatus[0] == 0) {
                                GLES20.glDeleteProgram(this.blurShaderProgram);
                                this.blurShaderProgram = 0;
                            } else {
                                this.blurPositionHandle = GLES20.glGetAttribLocation(this.blurShaderProgram, "position");
                                this.blurInputTexCoordHandle = GLES20.glGetAttribLocation(this.blurShaderProgram, "inputTexCoord");
                                this.blurSourceImageHandle = GLES20.glGetUniformLocation(this.blurShaderProgram, "sourceImage");
                                this.blurWidthHandle = GLES20.glGetUniformLocation(this.blurShaderProgram, "texelWidthOffset");
                                this.blurHeightHandle = GLES20.glGetUniformLocation(this.blurShaderProgram, "texelHeightOffset");
                            }
                            vertexShader = loadShader(35633, simpleVertexShaderCode);
                            fragmentShader = loadShader(35632, linearBlurFragmentShaderCode);
                            if (vertexShader == 0 || fragmentShader == 0) {
                                finish();
                                return false;
                            }
                            this.linearBlurShaderProgram = GLES20.glCreateProgram();
                            GLES20.glAttachShader(this.linearBlurShaderProgram, vertexShader);
                            GLES20.glAttachShader(this.linearBlurShaderProgram, fragmentShader);
                            GLES20.glBindAttribLocation(this.linearBlurShaderProgram, 0, "position");
                            GLES20.glBindAttribLocation(this.linearBlurShaderProgram, 1, "inputTexCoord");
                            GLES20.glLinkProgram(this.linearBlurShaderProgram);
                            linkStatus = new int[1];
                            GLES20.glGetProgramiv(this.linearBlurShaderProgram, 35714, linkStatus, 0);
                            if (linkStatus[0] == 0) {
                                GLES20.glDeleteProgram(this.linearBlurShaderProgram);
                                this.linearBlurShaderProgram = 0;
                            } else {
                                this.linearBlurPositionHandle = GLES20.glGetAttribLocation(this.linearBlurShaderProgram, "position");
                                this.linearBlurInputTexCoordHandle = GLES20.glGetAttribLocation(this.linearBlurShaderProgram, "inputTexCoord");
                                this.linearBlurSourceImageHandle = GLES20.glGetUniformLocation(this.linearBlurShaderProgram, "sourceImage");
                                this.linearBlurSourceImage2Handle = GLES20.glGetUniformLocation(this.linearBlurShaderProgram, "inputImageTexture2");
                                this.linearBlurExcludeSizeHandle = GLES20.glGetUniformLocation(this.linearBlurShaderProgram, "excludeSize");
                                this.linearBlurExcludePointHandle = GLES20.glGetUniformLocation(this.linearBlurShaderProgram, "excludePoint");
                                this.linearBlurExcludeBlurSizeHandle = GLES20.glGetUniformLocation(this.linearBlurShaderProgram, "excludeBlurSize");
                                this.linearBlurAngleHandle = GLES20.glGetUniformLocation(this.linearBlurShaderProgram, "angle");
                                this.linearBlurAspectRatioHandle = GLES20.glGetUniformLocation(this.linearBlurShaderProgram, "aspectRatio");
                            }
                            vertexShader = loadShader(35633, simpleVertexShaderCode);
                            fragmentShader = loadShader(35632, radialBlurFragmentShaderCode);
                            if (vertexShader == 0 || fragmentShader == 0) {
                                finish();
                                return false;
                            }
                            this.radialBlurShaderProgram = GLES20.glCreateProgram();
                            GLES20.glAttachShader(this.radialBlurShaderProgram, vertexShader);
                            GLES20.glAttachShader(this.radialBlurShaderProgram, fragmentShader);
                            GLES20.glBindAttribLocation(this.radialBlurShaderProgram, 0, "position");
                            GLES20.glBindAttribLocation(this.radialBlurShaderProgram, 1, "inputTexCoord");
                            GLES20.glLinkProgram(this.radialBlurShaderProgram);
                            linkStatus = new int[1];
                            GLES20.glGetProgramiv(this.radialBlurShaderProgram, 35714, linkStatus, 0);
                            if (linkStatus[0] == 0) {
                                GLES20.glDeleteProgram(this.radialBlurShaderProgram);
                                this.radialBlurShaderProgram = 0;
                            } else {
                                this.radialBlurPositionHandle = GLES20.glGetAttribLocation(this.radialBlurShaderProgram, "position");
                                this.radialBlurInputTexCoordHandle = GLES20.glGetAttribLocation(this.radialBlurShaderProgram, "inputTexCoord");
                                this.radialBlurSourceImageHandle = GLES20.glGetUniformLocation(this.radialBlurShaderProgram, "sourceImage");
                                this.radialBlurSourceImage2Handle = GLES20.glGetUniformLocation(this.radialBlurShaderProgram, "inputImageTexture2");
                                this.radialBlurExcludeSizeHandle = GLES20.glGetUniformLocation(this.radialBlurShaderProgram, "excludeSize");
                                this.radialBlurExcludePointHandle = GLES20.glGetUniformLocation(this.radialBlurShaderProgram, "excludePoint");
                                this.radialBlurExcludeBlurSizeHandle = GLES20.glGetUniformLocation(this.radialBlurShaderProgram, "excludeBlurSize");
                                this.radialBlurAspectRatioHandle = GLES20.glGetUniformLocation(this.radialBlurShaderProgram, "aspectRatio");
                            }
                            vertexShader = loadShader(35633, simpleVertexShaderCode);
                            fragmentShader = loadShader(35632, rgbToHsvFragmentShaderCode);
                            if (vertexShader == 0 || fragmentShader == 0) {
                                finish();
                                return false;
                            }
                            this.rgbToHsvShaderProgram = GLES20.glCreateProgram();
                            GLES20.glAttachShader(this.rgbToHsvShaderProgram, vertexShader);
                            GLES20.glAttachShader(this.rgbToHsvShaderProgram, fragmentShader);
                            GLES20.glBindAttribLocation(this.rgbToHsvShaderProgram, 0, "position");
                            GLES20.glBindAttribLocation(this.rgbToHsvShaderProgram, 1, "inputTexCoord");
                            GLES20.glLinkProgram(this.rgbToHsvShaderProgram);
                            linkStatus = new int[1];
                            GLES20.glGetProgramiv(this.rgbToHsvShaderProgram, 35714, linkStatus, 0);
                            if (linkStatus[0] == 0) {
                                GLES20.glDeleteProgram(this.rgbToHsvShaderProgram);
                                this.rgbToHsvShaderProgram = 0;
                            } else {
                                this.rgbToHsvPositionHandle = GLES20.glGetAttribLocation(this.rgbToHsvShaderProgram, "position");
                                this.rgbToHsvInputTexCoordHandle = GLES20.glGetAttribLocation(this.rgbToHsvShaderProgram, "inputTexCoord");
                                this.rgbToHsvSourceImageHandle = GLES20.glGetUniformLocation(this.rgbToHsvShaderProgram, "sourceImage");
                            }
                            vertexShader = loadShader(35633, simpleVertexShaderCode);
                            fragmentShader = loadShader(35632, enhanceFragmentShaderCode);
                            if (vertexShader == 0 || fragmentShader == 0) {
                                finish();
                                return false;
                            }
                            this.enhanceShaderProgram = GLES20.glCreateProgram();
                            GLES20.glAttachShader(this.enhanceShaderProgram, vertexShader);
                            GLES20.glAttachShader(this.enhanceShaderProgram, fragmentShader);
                            GLES20.glBindAttribLocation(this.enhanceShaderProgram, 0, "position");
                            GLES20.glBindAttribLocation(this.enhanceShaderProgram, 1, "inputTexCoord");
                            GLES20.glLinkProgram(this.enhanceShaderProgram);
                            linkStatus = new int[1];
                            GLES20.glGetProgramiv(this.enhanceShaderProgram, 35714, linkStatus, 0);
                            if (linkStatus[0] == 0) {
                                GLES20.glDeleteProgram(this.enhanceShaderProgram);
                                this.enhanceShaderProgram = 0;
                            } else {
                                this.enhancePositionHandle = GLES20.glGetAttribLocation(this.enhanceShaderProgram, "position");
                                this.enhanceInputTexCoordHandle = GLES20.glGetAttribLocation(this.enhanceShaderProgram, "inputTexCoord");
                                this.enhanceSourceImageHandle = GLES20.glGetUniformLocation(this.enhanceShaderProgram, "sourceImage");
                                this.enhanceIntensityHandle = GLES20.glGetUniformLocation(this.enhanceShaderProgram, "intensity");
                                this.enhanceInputImageTexture2Handle = GLES20.glGetUniformLocation(this.enhanceShaderProgram, "inputImageTexture2");
                            }
                            vertexShader = loadShader(35633, simpleVertexShaderCode);
                            fragmentShader = loadShader(35632, simpleFragmentShaderCode);
                            if (vertexShader == 0 || fragmentShader == 0) {
                                finish();
                                return false;
                            }
                            this.simpleShaderProgram = GLES20.glCreateProgram();
                            GLES20.glAttachShader(this.simpleShaderProgram, vertexShader);
                            GLES20.glAttachShader(this.simpleShaderProgram, fragmentShader);
                            GLES20.glBindAttribLocation(this.simpleShaderProgram, 0, "position");
                            GLES20.glBindAttribLocation(this.simpleShaderProgram, 1, "inputTexCoord");
                            GLES20.glLinkProgram(this.simpleShaderProgram);
                            linkStatus = new int[1];
                            GLES20.glGetProgramiv(this.simpleShaderProgram, 35714, linkStatus, 0);
                            if (linkStatus[0] == 0) {
                                GLES20.glDeleteProgram(this.simpleShaderProgram);
                                this.simpleShaderProgram = 0;
                            } else {
                                this.simplePositionHandle = GLES20.glGetAttribLocation(this.simpleShaderProgram, "position");
                                this.simpleInputTexCoordHandle = GLES20.glGetAttribLocation(this.simpleShaderProgram, "inputTexCoord");
                                this.simpleSourceImageHandle = GLES20.glGetUniformLocation(this.simpleShaderProgram, "sourceImage");
                            }
                            if (this.currentBitmap != null) {
                                loadTexture(this.currentBitmap);
                            }
                            return true;
                        }
                        FileLog.e("eglMakeCurrent failed " + GLUtils.getEGLErrorString(this.egl10.eglGetError()));
                        finish();
                        return false;
                    } else {
                        finish();
                        return false;
                    }
                } else {
                    FileLog.e("eglConfig not initialized");
                    finish();
                    return false;
                }
            }
            FileLog.e("eglInitialize failed " + GLUtils.getEGLErrorString(this.egl10.eglGetError()));
            finish();
            return false;
        }

        public void finish() {
            if (this.eglSurface != null) {
                this.egl10.eglMakeCurrent(this.eglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
                this.egl10.eglDestroySurface(this.eglDisplay, this.eglSurface);
                this.eglSurface = null;
            }
            if (this.eglContext != null) {
                this.egl10.eglDestroyContext(this.eglDisplay, this.eglContext);
                this.eglContext = null;
            }
            if (this.eglDisplay != null) {
                this.egl10.eglTerminate(this.eglDisplay);
                this.eglDisplay = null;
            }
        }

        private void drawEnhancePass() {
            if (!this.hsvGenerated) {
                GLES20.glBindFramebuffer(36160, this.renderFrameBuffer[0]);
                GLES20.glFramebufferTexture2D(36160, 36064, 3553, this.renderTexture[0], 0);
                GLES20.glClear(0);
                GLES20.glUseProgram(this.rgbToHsvShaderProgram);
                GLES20.glActiveTexture(33984);
                GLES20.glBindTexture(3553, this.renderTexture[1]);
                GLES20.glUniform1i(this.rgbToHsvSourceImageHandle, 0);
                GLES20.glEnableVertexAttribArray(this.rgbToHsvInputTexCoordHandle);
                GLES20.glVertexAttribPointer(this.rgbToHsvInputTexCoordHandle, 2, 5126, false, 8, this.textureBuffer);
                GLES20.glEnableVertexAttribArray(this.rgbToHsvPositionHandle);
                GLES20.glVertexAttribPointer(this.rgbToHsvPositionHandle, 2, 5126, false, 8, this.vertexBuffer);
                GLES20.glDrawArrays(5, 0, 4);
                Buffer hsvBuffer = ByteBuffer.allocateDirect((this.renderBufferWidth * this.renderBufferHeight) * 4);
                GLES20.glReadPixels(0, 0, this.renderBufferWidth, this.renderBufferHeight, 6408, 5121, hsvBuffer);
                GLES20.glBindTexture(3553, this.enhanceTextures[0]);
                GLES20.glTexParameteri(3553, 10241, 9729);
                GLES20.glTexParameteri(3553, Task.EXTRAS_LIMIT_BYTES, 9729);
                GLES20.glTexParameteri(3553, 10242, 33071);
                GLES20.glTexParameteri(3553, 10243, 33071);
                GLES20.glTexImage2D(3553, 0, 6408, this.renderBufferWidth, this.renderBufferHeight, 0, 6408, 5121, hsvBuffer);
                ByteBuffer byteBuffer = null;
                try {
                    byteBuffer = ByteBuffer.allocateDirect(16384);
                    Utilities.calcCDT(hsvBuffer, this.renderBufferWidth, this.renderBufferHeight, byteBuffer);
                } catch (Exception e) {
                    FileLog.e(e);
                }
                GLES20.glBindTexture(3553, this.enhanceTextures[1]);
                GLES20.glTexParameteri(3553, 10241, 9729);
                GLES20.glTexParameteri(3553, Task.EXTRAS_LIMIT_BYTES, 9729);
                GLES20.glTexParameteri(3553, 10242, 33071);
                GLES20.glTexParameteri(3553, 10243, 33071);
                GLES20.glTexImage2D(3553, 0, 6408, 256, 16, 0, 6408, 5121, byteBuffer);
                this.hsvGenerated = true;
            }
            GLES20.glBindFramebuffer(36160, this.renderFrameBuffer[1]);
            GLES20.glFramebufferTexture2D(36160, 36064, 3553, this.renderTexture[1], 0);
            GLES20.glClear(0);
            GLES20.glUseProgram(this.enhanceShaderProgram);
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(3553, this.enhanceTextures[0]);
            GLES20.glUniform1i(this.enhanceSourceImageHandle, 0);
            GLES20.glActiveTexture(33985);
            GLES20.glBindTexture(3553, this.enhanceTextures[1]);
            GLES20.glUniform1i(this.enhanceInputImageTexture2Handle, 1);
            if (PhotoFilterView.this.showOriginal) {
                GLES20.glUniform1f(this.enhanceIntensityHandle, 0.0f);
            } else {
                GLES20.glUniform1f(this.enhanceIntensityHandle, PhotoFilterView.this.getEnhanceValue());
            }
            GLES20.glEnableVertexAttribArray(this.enhanceInputTexCoordHandle);
            GLES20.glVertexAttribPointer(this.enhanceInputTexCoordHandle, 2, 5126, false, 8, this.textureBuffer);
            GLES20.glEnableVertexAttribArray(this.enhancePositionHandle);
            GLES20.glVertexAttribPointer(this.enhancePositionHandle, 2, 5126, false, 8, this.vertexBuffer);
            GLES20.glDrawArrays(5, 0, 4);
        }

        private void drawSharpenPass() {
            GLES20.glBindFramebuffer(36160, this.renderFrameBuffer[0]);
            GLES20.glFramebufferTexture2D(36160, 36064, 3553, this.renderTexture[0], 0);
            GLES20.glClear(0);
            GLES20.glUseProgram(this.sharpenShaderProgram);
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(3553, this.renderTexture[1]);
            GLES20.glUniform1i(this.sharpenSourceImageHandle, 0);
            if (PhotoFilterView.this.showOriginal) {
                GLES20.glUniform1f(this.sharpenHandle, 0.0f);
            } else {
                GLES20.glUniform1f(this.sharpenHandle, PhotoFilterView.this.getSharpenValue());
            }
            GLES20.glUniform1f(this.sharpenWidthHandle, (float) this.renderBufferWidth);
            GLES20.glUniform1f(this.sharpenHeightHandle, (float) this.renderBufferHeight);
            GLES20.glEnableVertexAttribArray(this.sharpenInputTexCoordHandle);
            GLES20.glVertexAttribPointer(this.sharpenInputTexCoordHandle, 2, 5126, false, 8, this.textureBuffer);
            GLES20.glEnableVertexAttribArray(this.sharpenPositionHandle);
            GLES20.glVertexAttribPointer(this.sharpenPositionHandle, 2, 5126, false, 8, this.vertexInvertBuffer);
            GLES20.glDrawArrays(5, 0, 4);
        }

        private void drawCustomParamsPass() {
            float f = 1.0f;
            GLES20.glBindFramebuffer(36160, this.renderFrameBuffer[1]);
            GLES20.glFramebufferTexture2D(36160, 36064, 3553, this.renderTexture[1], 0);
            GLES20.glClear(0);
            GLES20.glUseProgram(this.toolsShaderProgram);
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(3553, this.renderTexture[0]);
            GLES20.glUniform1i(this.sourceImageHandle, 0);
            if (PhotoFilterView.this.showOriginal) {
                GLES20.glUniform1f(this.shadowsHandle, 1.0f);
                GLES20.glUniform1f(this.highlightsHandle, 1.0f);
                GLES20.glUniform1f(this.exposureHandle, 0.0f);
                GLES20.glUniform1f(this.contrastHandle, 1.0f);
                GLES20.glUniform1f(this.saturationHandle, 1.0f);
                GLES20.glUniform1f(this.warmthHandle, 0.0f);
                GLES20.glUniform1f(this.vignetteHandle, 0.0f);
                GLES20.glUniform1f(this.grainHandle, 0.0f);
                GLES20.glUniform1f(this.fadeAmountHandle, 0.0f);
                GLES20.glUniform3f(this.highlightsTintColorHandle, 0.0f, 0.0f, 0.0f);
                GLES20.glUniform1f(this.highlightsTintIntensityHandle, 0.0f);
                GLES20.glUniform3f(this.shadowsTintColorHandle, 0.0f, 0.0f, 0.0f);
                GLES20.glUniform1f(this.shadowsTintIntensityHandle, 0.0f);
                GLES20.glUniform1f(this.skipToneHandle, 1.0f);
            } else {
                GLES20.glUniform1f(this.shadowsHandle, PhotoFilterView.this.getShadowsValue());
                GLES20.glUniform1f(this.highlightsHandle, PhotoFilterView.this.getHighlightsValue());
                GLES20.glUniform1f(this.exposureHandle, PhotoFilterView.this.getExposureValue());
                GLES20.glUniform1f(this.contrastHandle, PhotoFilterView.this.getContrastValue());
                GLES20.glUniform1f(this.saturationHandle, PhotoFilterView.this.getSaturationValue());
                GLES20.glUniform1f(this.warmthHandle, PhotoFilterView.this.getWarmthValue());
                GLES20.glUniform1f(this.vignetteHandle, PhotoFilterView.this.getVignetteValue());
                GLES20.glUniform1f(this.grainHandle, PhotoFilterView.this.getGrainValue());
                GLES20.glUniform1f(this.fadeAmountHandle, PhotoFilterView.this.getFadeValue());
                GLES20.glUniform3f(this.highlightsTintColorHandle, ((float) ((PhotoFilterView.this.tintHighlightsColor >> 16) & 255)) / 255.0f, ((float) ((PhotoFilterView.this.tintHighlightsColor >> 8) & 255)) / 255.0f, ((float) (PhotoFilterView.this.tintHighlightsColor & 255)) / 255.0f);
                GLES20.glUniform1f(this.highlightsTintIntensityHandle, PhotoFilterView.this.getTintHighlightsIntensityValue());
                GLES20.glUniform3f(this.shadowsTintColorHandle, ((float) ((PhotoFilterView.this.tintShadowsColor >> 16) & 255)) / 255.0f, ((float) ((PhotoFilterView.this.tintShadowsColor >> 8) & 255)) / 255.0f, ((float) (PhotoFilterView.this.tintShadowsColor & 255)) / 255.0f);
                GLES20.glUniform1f(this.shadowsTintIntensityHandle, PhotoFilterView.this.getTintShadowsIntensityValue());
                boolean skipTone = PhotoFilterView.this.curvesToolValue.shouldBeSkipped();
                int i = this.skipToneHandle;
                if (!skipTone) {
                    f = 0.0f;
                }
                GLES20.glUniform1f(i, f);
                if (!skipTone) {
                    PhotoFilterView.this.curvesToolValue.fillBuffer();
                    GLES20.glActiveTexture(33985);
                    GLES20.glBindTexture(3553, this.curveTextures[0]);
                    GLES20.glTexParameteri(3553, 10241, 9729);
                    GLES20.glTexParameteri(3553, Task.EXTRAS_LIMIT_BYTES, 9729);
                    GLES20.glTexParameteri(3553, 10242, 33071);
                    GLES20.glTexParameteri(3553, 10243, 33071);
                    GLES20.glTexImage2D(3553, 0, 6408, 200, 1, 0, 6408, 5121, PhotoFilterView.this.curvesToolValue.curveBuffer);
                    GLES20.glUniform1i(this.curvesImageHandle, 1);
                }
            }
            GLES20.glUniform1f(this.widthHandle, (float) this.renderBufferWidth);
            GLES20.glUniform1f(this.heightHandle, (float) this.renderBufferHeight);
            GLES20.glEnableVertexAttribArray(this.inputTexCoordHandle);
            GLES20.glVertexAttribPointer(this.inputTexCoordHandle, 2, 5126, false, 8, this.textureBuffer);
            GLES20.glEnableVertexAttribArray(this.positionHandle);
            GLES20.glVertexAttribPointer(this.positionHandle, 2, 5126, false, 8, this.vertexInvertBuffer);
            GLES20.glDrawArrays(5, 0, 4);
        }

        private boolean drawBlurPass() {
            if (PhotoFilterView.this.showOriginal || PhotoFilterView.this.blurType == 0) {
                return false;
            }
            if (this.needUpdateBlurTexture) {
                GLES20.glUseProgram(this.blurShaderProgram);
                GLES20.glUniform1i(this.blurSourceImageHandle, 0);
                GLES20.glEnableVertexAttribArray(this.blurInputTexCoordHandle);
                GLES20.glVertexAttribPointer(this.blurInputTexCoordHandle, 2, 5126, false, 8, this.textureBuffer);
                GLES20.glEnableVertexAttribArray(this.blurPositionHandle);
                GLES20.glVertexAttribPointer(this.blurPositionHandle, 2, 5126, false, 8, this.vertexInvertBuffer);
                GLES20.glBindFramebuffer(36160, this.renderFrameBuffer[0]);
                GLES20.glFramebufferTexture2D(36160, 36064, 3553, this.renderTexture[0], 0);
                GLES20.glClear(0);
                GLES20.glActiveTexture(33984);
                GLES20.glBindTexture(3553, this.renderTexture[1]);
                GLES20.glUniform1f(this.blurWidthHandle, 0.0f);
                GLES20.glUniform1f(this.blurHeightHandle, 1.0f / ((float) this.renderBufferHeight));
                GLES20.glDrawArrays(5, 0, 4);
                GLES20.glBindFramebuffer(36160, this.renderFrameBuffer[2]);
                GLES20.glFramebufferTexture2D(36160, 36064, 3553, this.renderTexture[2], 0);
                GLES20.glClear(0);
                GLES20.glActiveTexture(33984);
                GLES20.glBindTexture(3553, this.renderTexture[0]);
                GLES20.glUniform1f(this.blurWidthHandle, 1.0f / ((float) this.renderBufferWidth));
                GLES20.glUniform1f(this.blurHeightHandle, 0.0f);
                GLES20.glDrawArrays(5, 0, 4);
                this.needUpdateBlurTexture = false;
            }
            GLES20.glBindFramebuffer(36160, this.renderFrameBuffer[0]);
            GLES20.glFramebufferTexture2D(36160, 36064, 3553, this.renderTexture[0], 0);
            GLES20.glClear(0);
            if (PhotoFilterView.this.blurType == 1) {
                GLES20.glUseProgram(this.radialBlurShaderProgram);
                GLES20.glUniform1i(this.radialBlurSourceImageHandle, 0);
                GLES20.glUniform1i(this.radialBlurSourceImage2Handle, 1);
                GLES20.glUniform1f(this.radialBlurExcludeSizeHandle, PhotoFilterView.this.blurExcludeSize);
                GLES20.glUniform1f(this.radialBlurExcludeBlurSizeHandle, PhotoFilterView.this.blurExcludeBlurSize);
                GLES20.glUniform2f(this.radialBlurExcludePointHandle, PhotoFilterView.this.blurExcludePoint.f105x, PhotoFilterView.this.blurExcludePoint.f106y);
                GLES20.glUniform1f(this.radialBlurAspectRatioHandle, ((float) this.renderBufferHeight) / ((float) this.renderBufferWidth));
                GLES20.glEnableVertexAttribArray(this.radialBlurInputTexCoordHandle);
                GLES20.glVertexAttribPointer(this.radialBlurInputTexCoordHandle, 2, 5126, false, 8, this.textureBuffer);
                GLES20.glEnableVertexAttribArray(this.radialBlurPositionHandle);
                GLES20.glVertexAttribPointer(this.radialBlurPositionHandle, 2, 5126, false, 8, this.vertexInvertBuffer);
            } else if (PhotoFilterView.this.blurType == 2) {
                GLES20.glUseProgram(this.linearBlurShaderProgram);
                GLES20.glUniform1i(this.linearBlurSourceImageHandle, 0);
                GLES20.glUniform1i(this.linearBlurSourceImage2Handle, 1);
                GLES20.glUniform1f(this.linearBlurExcludeSizeHandle, PhotoFilterView.this.blurExcludeSize);
                GLES20.glUniform1f(this.linearBlurExcludeBlurSizeHandle, PhotoFilterView.this.blurExcludeBlurSize);
                GLES20.glUniform1f(this.linearBlurAngleHandle, PhotoFilterView.this.blurAngle);
                GLES20.glUniform2f(this.linearBlurExcludePointHandle, PhotoFilterView.this.blurExcludePoint.f105x, PhotoFilterView.this.blurExcludePoint.f106y);
                GLES20.glUniform1f(this.linearBlurAspectRatioHandle, ((float) this.renderBufferHeight) / ((float) this.renderBufferWidth));
                GLES20.glEnableVertexAttribArray(this.linearBlurInputTexCoordHandle);
                GLES20.glVertexAttribPointer(this.linearBlurInputTexCoordHandle, 2, 5126, false, 8, this.textureBuffer);
                GLES20.glEnableVertexAttribArray(this.linearBlurPositionHandle);
                GLES20.glVertexAttribPointer(this.linearBlurPositionHandle, 2, 5126, false, 8, this.vertexInvertBuffer);
            }
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(3553, this.renderTexture[1]);
            GLES20.glActiveTexture(33985);
            GLES20.glBindTexture(3553, this.renderTexture[2]);
            GLES20.glDrawArrays(5, 0, 4);
            return true;
        }

        private Bitmap getRenderBufferBitmap() {
            ByteBuffer buffer = ByteBuffer.allocateDirect((this.renderBufferWidth * this.renderBufferHeight) * 4);
            GLES20.glReadPixels(0, 0, this.renderBufferWidth, this.renderBufferHeight, 6408, 5121, buffer);
            Bitmap bitmap = Bitmap.createBitmap(this.renderBufferWidth, this.renderBufferHeight, Config.ARGB_8888);
            bitmap.copyPixelsFromBuffer(buffer);
            return bitmap;
        }

        public Bitmap getTexture() {
            if (!this.initied) {
                return null;
            }
            final Semaphore semaphore = new Semaphore(0);
            final Bitmap[] object = new Bitmap[1];
            try {
                postRunnable(new Runnable() {
                    public void run() {
                        int i = 1;
                        GLES20.glBindFramebuffer(36160, EGLThread.this.renderFrameBuffer[1]);
                        int[] access$3700 = EGLThread.this.renderTexture;
                        if (EGLThread.this.blured) {
                            i = 0;
                        }
                        GLES20.glFramebufferTexture2D(36160, 36064, 3553, access$3700[i], 0);
                        GLES20.glClear(0);
                        object[0] = EGLThread.this.getRenderBufferBitmap();
                        semaphore.release();
                        GLES20.glBindFramebuffer(36160, 0);
                        GLES20.glClear(0);
                    }
                });
                semaphore.acquire();
            } catch (Exception e) {
                FileLog.e(e);
            }
            return object[0];
        }

        private Bitmap createBitmap(Bitmap bitmap, int w, int h, float scale) {
            Matrix matrix = new Matrix();
            matrix.setScale(scale, scale);
            matrix.postRotate((float) PhotoFilterView.this.orientation);
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }

        private void loadTexture(Bitmap bitmap) {
            this.renderBufferWidth = bitmap.getWidth();
            this.renderBufferHeight = bitmap.getHeight();
            float maxSize = (float) AndroidUtilities.getPhotoSize();
            if (((float) this.renderBufferWidth) > maxSize || ((float) this.renderBufferHeight) > maxSize || PhotoFilterView.this.orientation % 360 != 0) {
                float scale = 1.0f;
                if (((float) this.renderBufferWidth) > maxSize || ((float) this.renderBufferHeight) > maxSize) {
                    float scaleX = maxSize / ((float) bitmap.getWidth());
                    float scaleY = maxSize / ((float) bitmap.getHeight());
                    if (scaleX < scaleY) {
                        this.renderBufferWidth = (int) maxSize;
                        this.renderBufferHeight = (int) (((float) bitmap.getHeight()) * scaleX);
                        scale = scaleX;
                    } else {
                        this.renderBufferHeight = (int) maxSize;
                        this.renderBufferWidth = (int) (((float) bitmap.getWidth()) * scaleY);
                        scale = scaleY;
                    }
                }
                if (PhotoFilterView.this.orientation % 360 == 90 || PhotoFilterView.this.orientation % 360 == 270) {
                    int temp = this.renderBufferWidth;
                    this.renderBufferWidth = this.renderBufferHeight;
                    this.renderBufferHeight = temp;
                }
                this.currentBitmap = createBitmap(bitmap, this.renderBufferWidth, this.renderBufferHeight, scale);
            }
            GLES20.glGenFramebuffers(3, this.renderFrameBuffer, 0);
            GLES20.glGenTextures(3, this.renderTexture, 0);
            GLES20.glBindTexture(3553, this.renderTexture[0]);
            GLES20.glTexParameteri(3553, 10241, 9729);
            GLES20.glTexParameteri(3553, Task.EXTRAS_LIMIT_BYTES, 9729);
            GLES20.glTexParameteri(3553, 10242, 33071);
            GLES20.glTexParameteri(3553, 10243, 33071);
            GLES20.glTexImage2D(3553, 0, 6408, this.renderBufferWidth, this.renderBufferHeight, 0, 6408, 5121, null);
            GLES20.glBindTexture(3553, this.renderTexture[1]);
            GLES20.glTexParameteri(3553, 10241, 9729);
            GLES20.glTexParameteri(3553, Task.EXTRAS_LIMIT_BYTES, 9729);
            GLES20.glTexParameteri(3553, 10242, 33071);
            GLES20.glTexParameteri(3553, 10243, 33071);
            GLUtils.texImage2D(3553, 0, this.currentBitmap, 0);
            GLES20.glBindTexture(3553, this.renderTexture[2]);
            GLES20.glTexParameteri(3553, 10241, 9729);
            GLES20.glTexParameteri(3553, Task.EXTRAS_LIMIT_BYTES, 9729);
            GLES20.glTexParameteri(3553, 10242, 33071);
            GLES20.glTexParameteri(3553, 10243, 33071);
            GLES20.glTexImage2D(3553, 0, 6408, this.renderBufferWidth, this.renderBufferHeight, 0, 6408, 5121, null);
        }

        public void shutdown() {
            postRunnable(new C26853());
        }

        public void setSurfaceTextureSize(int width, int height) {
            this.surfaceWidth = width;
            this.surfaceHeight = height;
        }

        public void run() {
            this.initied = initGL();
            super.run();
        }

        public void requestRender(boolean updateBlur) {
            requestRender(updateBlur, false);
        }

        public void requestRender(final boolean updateBlur, final boolean force) {
            postRunnable(new Runnable() {
                public void run() {
                    if (!EGLThread.this.needUpdateBlurTexture) {
                        EGLThread.this.needUpdateBlurTexture = updateBlur;
                    }
                    long newTime = System.currentTimeMillis();
                    if (force || Math.abs(EGLThread.this.lastRenderCallTime - newTime) > 30) {
                        EGLThread.this.lastRenderCallTime = newTime;
                        EGLThread.this.drawRunnable.run();
                    }
                }
            });
        }
    }

    public class ToolsAdapter extends SelectionAdapter {
        private Context mContext;

        /* renamed from: org.telegram.ui.Components.PhotoFilterView$ToolsAdapter$1 */
        class C26871 implements PhotoEditorSeekBarDelegate {
            C26871() {
            }

            public void onProgressChanged(int i, int progress) {
                if (i == PhotoFilterView.this.enhanceTool) {
                    PhotoFilterView.this.enhanceValue = (float) progress;
                } else if (i == PhotoFilterView.this.highlightsTool) {
                    PhotoFilterView.this.highlightsValue = (float) progress;
                } else if (i == PhotoFilterView.this.contrastTool) {
                    PhotoFilterView.this.contrastValue = (float) progress;
                } else if (i == PhotoFilterView.this.exposureTool) {
                    PhotoFilterView.this.exposureValue = (float) progress;
                } else if (i == PhotoFilterView.this.warmthTool) {
                    PhotoFilterView.this.warmthValue = (float) progress;
                } else if (i == PhotoFilterView.this.saturationTool) {
                    PhotoFilterView.this.saturationValue = (float) progress;
                } else if (i == PhotoFilterView.this.vignetteTool) {
                    PhotoFilterView.this.vignetteValue = (float) progress;
                } else if (i == PhotoFilterView.this.shadowsTool) {
                    PhotoFilterView.this.shadowsValue = (float) progress;
                } else if (i == PhotoFilterView.this.grainTool) {
                    PhotoFilterView.this.grainValue = (float) progress;
                } else if (i == PhotoFilterView.this.sharpenTool) {
                    PhotoFilterView.this.sharpenValue = (float) progress;
                } else if (i == PhotoFilterView.this.fadeTool) {
                    PhotoFilterView.this.fadeValue = (float) progress;
                }
                if (PhotoFilterView.this.eglThread != null) {
                    PhotoFilterView.this.eglThread.requestRender(true);
                }
            }
        }

        /* renamed from: org.telegram.ui.Components.PhotoFilterView$ToolsAdapter$2 */
        class C26882 implements OnClickListener {
            C26882() {
            }

            public void onClick(View v) {
                PhotoEditRadioCell cell = (PhotoEditRadioCell) v;
                if (((Integer) cell.getTag()).intValue() == PhotoFilterView.this.tintShadowsTool) {
                    PhotoFilterView.this.tintShadowsColor = cell.getCurrentColor();
                } else {
                    PhotoFilterView.this.tintHighlightsColor = cell.getCurrentColor();
                }
                if (PhotoFilterView.this.eglThread != null) {
                    PhotoFilterView.this.eglThread.requestRender(false);
                }
            }
        }

        public ToolsAdapter(Context context) {
            this.mContext = context;
        }

        public int getItemCount() {
            return 13;
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view;
            if (i == 0) {
                View cell = new PhotoEditToolCell(this.mContext);
                view = cell;
                cell.setSeekBarDelegate(new C26871());
            } else {
                view = new PhotoEditRadioCell(this.mContext);
                view.setOnClickListener(new C26882());
            }
            return new Holder(view);
        }

        public boolean isEnabled(ViewHolder holder) {
            return false;
        }

        public void onBindViewHolder(ViewHolder holder, int i) {
            switch (holder.getItemViewType()) {
                case 0:
                    PhotoEditToolCell cell = holder.itemView;
                    cell.setTag(Integer.valueOf(i));
                    if (i == PhotoFilterView.this.enhanceTool) {
                        cell.setIconAndTextAndValue(LocaleController.getString("Enhance", R.string.Enhance), PhotoFilterView.this.enhanceValue, 0, 100);
                        return;
                    } else if (i == PhotoFilterView.this.highlightsTool) {
                        cell.setIconAndTextAndValue(LocaleController.getString("Highlights", R.string.Highlights), PhotoFilterView.this.highlightsValue, -100, 100);
                        return;
                    } else if (i == PhotoFilterView.this.contrastTool) {
                        cell.setIconAndTextAndValue(LocaleController.getString("Contrast", R.string.Contrast), PhotoFilterView.this.contrastValue, -100, 100);
                        return;
                    } else if (i == PhotoFilterView.this.exposureTool) {
                        cell.setIconAndTextAndValue(LocaleController.getString("Exposure", R.string.Exposure), PhotoFilterView.this.exposureValue, -100, 100);
                        return;
                    } else if (i == PhotoFilterView.this.warmthTool) {
                        cell.setIconAndTextAndValue(LocaleController.getString("Warmth", R.string.Warmth), PhotoFilterView.this.warmthValue, -100, 100);
                        return;
                    } else if (i == PhotoFilterView.this.saturationTool) {
                        cell.setIconAndTextAndValue(LocaleController.getString("Saturation", R.string.Saturation), PhotoFilterView.this.saturationValue, -100, 100);
                        return;
                    } else if (i == PhotoFilterView.this.vignetteTool) {
                        cell.setIconAndTextAndValue(LocaleController.getString("Vignette", R.string.Vignette), PhotoFilterView.this.vignetteValue, 0, 100);
                        return;
                    } else if (i == PhotoFilterView.this.shadowsTool) {
                        cell.setIconAndTextAndValue(LocaleController.getString("Shadows", R.string.Shadows), PhotoFilterView.this.shadowsValue, -100, 100);
                        return;
                    } else if (i == PhotoFilterView.this.grainTool) {
                        cell.setIconAndTextAndValue(LocaleController.getString("Grain", R.string.Grain), PhotoFilterView.this.grainValue, 0, 100);
                        return;
                    } else if (i == PhotoFilterView.this.sharpenTool) {
                        cell.setIconAndTextAndValue(LocaleController.getString("Sharpen", R.string.Sharpen), PhotoFilterView.this.sharpenValue, 0, 100);
                        return;
                    } else if (i == PhotoFilterView.this.fadeTool) {
                        cell.setIconAndTextAndValue(LocaleController.getString("Fade", R.string.Fade), PhotoFilterView.this.fadeValue, 0, 100);
                        return;
                    } else {
                        return;
                    }
                case 1:
                    PhotoEditRadioCell cell2 = holder.itemView;
                    cell2.setTag(Integer.valueOf(i));
                    if (i == PhotoFilterView.this.tintShadowsTool) {
                        cell2.setIconAndTextAndValue(LocaleController.getString("TintShadows", R.string.TintShadows), 0, PhotoFilterView.this.tintShadowsColor);
                        return;
                    } else if (i == PhotoFilterView.this.tintHighlightsTool) {
                        cell2.setIconAndTextAndValue(LocaleController.getString("TintHighlights", R.string.TintHighlights), 0, PhotoFilterView.this.tintHighlightsColor);
                        return;
                    } else {
                        return;
                    }
                default:
                    return;
            }
        }

        public int getItemViewType(int position) {
            if (position == PhotoFilterView.this.tintShadowsTool || position == PhotoFilterView.this.tintHighlightsTool) {
                return 1;
            }
            return 0;
        }
    }

    public PhotoFilterView(Context context, Bitmap bitmap, int rotation, MediaController$SavedFilterState state) {
        super(context);
        if (state != null) {
            this.enhanceValue = state.enhanceValue;
            this.exposureValue = state.exposureValue;
            this.contrastValue = state.contrastValue;
            this.warmthValue = state.warmthValue;
            this.saturationValue = state.saturationValue;
            this.fadeValue = state.fadeValue;
            this.tintShadowsColor = state.tintShadowsColor;
            this.tintHighlightsColor = state.tintHighlightsColor;
            this.highlightsValue = state.highlightsValue;
            this.shadowsValue = state.shadowsValue;
            this.vignetteValue = state.vignetteValue;
            this.grainValue = state.grainValue;
            this.blurType = state.blurType;
            this.sharpenValue = state.sharpenValue;
            this.curvesToolValue = state.curvesToolValue;
            this.blurExcludeSize = state.blurExcludeSize;
            this.blurExcludePoint = state.blurExcludePoint;
            this.blurExcludeBlurSize = state.blurExcludeBlurSize;
            this.blurAngle = state.blurAngle;
            this.lastState = state;
        } else {
            this.curvesToolValue = new CurvesToolValue();
            this.blurExcludeSize = 0.35f;
            this.blurExcludePoint = new Point(0.5f, 0.5f);
            this.blurExcludeBlurSize = 0.15f;
            this.blurAngle = 1.5707964f;
        }
        this.bitmapToEdit = bitmap;
        this.orientation = rotation;
        this.textureView = new TextureView(context);
        addView(this.textureView, LayoutHelper.createFrame(-1, -1, 51));
        this.textureView.setVisibility(4);
        this.textureView.setSurfaceTextureListener(new C26741());
        this.blurControl = new PhotoFilterBlurControl(context);
        this.blurControl.setVisibility(4);
        addView(this.blurControl, LayoutHelper.createFrame(-1, -1, 51));
        this.blurControl.setDelegate(new C26752());
        this.curvesControl = new PhotoFilterCurvesControl(context, this.curvesToolValue);
        this.curvesControl.setDelegate(new C26763());
        this.curvesControl.setVisibility(4);
        addView(this.curvesControl, LayoutHelper.createFrame(-1, -1, 51));
        this.toolsView = new FrameLayout(context);
        addView(this.toolsView, LayoutHelper.createFrame(-1, 186, 83));
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setBackgroundColor(-16777216);
        this.toolsView.addView(frameLayout, LayoutHelper.createFrame(-1, 48, 83));
        this.cancelTextView = new TextView(context);
        this.cancelTextView.setTextSize(1, 14.0f);
        this.cancelTextView.setTextColor(-1);
        this.cancelTextView.setGravity(17);
        this.cancelTextView.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.ACTION_BAR_PICKER_SELECTOR_COLOR, 0));
        this.cancelTextView.setPadding(AndroidUtilities.dp(20.0f), 0, AndroidUtilities.dp(20.0f), 0);
        this.cancelTextView.setText(LocaleController.getString("Cancel", R.string.Cancel).toUpperCase());
        this.cancelTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        frameLayout.addView(this.cancelTextView, LayoutHelper.createFrame(-2, -1, 51));
        this.doneTextView = new TextView(context);
        this.doneTextView.setTextSize(1, 14.0f);
        this.doneTextView.setTextColor(-11420173);
        this.doneTextView.setGravity(17);
        this.doneTextView.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.ACTION_BAR_PICKER_SELECTOR_COLOR, 0));
        this.doneTextView.setPadding(AndroidUtilities.dp(20.0f), 0, AndroidUtilities.dp(20.0f), 0);
        this.doneTextView.setText(LocaleController.getString("Done", R.string.Done).toUpperCase());
        this.doneTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        frameLayout.addView(this.doneTextView, LayoutHelper.createFrame(-2, -1, 53));
        LinearLayout linearLayout = new LinearLayout(context);
        frameLayout.addView(linearLayout, LayoutHelper.createFrame(-2, -1, 1));
        this.tuneItem = new ImageView(context);
        this.tuneItem.setScaleType(ScaleType.CENTER);
        this.tuneItem.setImageResource(R.drawable.photo_tools);
        this.tuneItem.setColorFilter(new PorterDuffColorFilter(-9649153, Mode.MULTIPLY));
        this.tuneItem.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.ACTION_BAR_WHITE_SELECTOR_COLOR));
        linearLayout.addView(this.tuneItem, LayoutHelper.createLinear(56, 48));
        this.tuneItem.setOnClickListener(new C26774());
        this.blurItem = new ImageView(context);
        this.blurItem.setScaleType(ScaleType.CENTER);
        this.blurItem.setImageResource(R.drawable.tool_blur);
        this.blurItem.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.ACTION_BAR_WHITE_SELECTOR_COLOR));
        linearLayout.addView(this.blurItem, LayoutHelper.createLinear(56, 48));
        this.blurItem.setOnClickListener(new C26785());
        this.curveItem = new ImageView(context);
        this.curveItem.setScaleType(ScaleType.CENTER);
        this.curveItem.setImageResource(R.drawable.tool_curve);
        this.curveItem.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.ACTION_BAR_WHITE_SELECTOR_COLOR));
        linearLayout.addView(this.curveItem, LayoutHelper.createLinear(56, 48));
        this.curveItem.setOnClickListener(new C26796());
        this.recyclerListView = new RecyclerListView(context);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(1);
        this.recyclerListView.setLayoutManager(layoutManager);
        this.recyclerListView.setClipToPadding(false);
        this.recyclerListView.setOverScrollMode(2);
        this.recyclerListView.setAdapter(new ToolsAdapter(context));
        this.toolsView.addView(this.recyclerListView, LayoutHelper.createFrame(-1, 120, 51));
        this.curveLayout = new FrameLayout(context);
        this.curveLayout.setVisibility(4);
        this.toolsView.addView(this.curveLayout, LayoutHelper.createFrame(-1, 78.0f, 1, 0.0f, 40.0f, 0.0f, 0.0f));
        LinearLayout curveTextViewContainer = new LinearLayout(context);
        curveTextViewContainer.setOrientation(0);
        this.curveLayout.addView(curveTextViewContainer, LayoutHelper.createFrame(-2, -2, 1));
        int a = 0;
        while (a < 4) {
            FrameLayout frameLayout1 = new FrameLayout(context);
            frameLayout1.setTag(Integer.valueOf(a));
            this.curveRadioButton[a] = new RadioButton(context);
            this.curveRadioButton[a].setSize(AndroidUtilities.dp(20.0f));
            frameLayout1.addView(this.curveRadioButton[a], LayoutHelper.createFrame(30, 30, 49));
            TextView curveTextView = new TextView(context);
            curveTextView.setTextSize(1, 12.0f);
            curveTextView.setGravity(16);
            String str;
            if (a == 0) {
                str = LocaleController.getString("CurvesAll", R.string.CurvesAll);
                curveTextView.setText(str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase());
                curveTextView.setTextColor(-1);
                this.curveRadioButton[a].setColor(-1, -1);
            } else if (a == 1) {
                str = LocaleController.getString("CurvesRed", R.string.CurvesRed);
                curveTextView.setText(str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase());
                curveTextView.setTextColor(-1684147);
                this.curveRadioButton[a].setColor(-1684147, -1684147);
            } else if (a == 2) {
                str = LocaleController.getString("CurvesGreen", R.string.CurvesGreen);
                curveTextView.setText(str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase());
                curveTextView.setTextColor(-10831009);
                this.curveRadioButton[a].setColor(-10831009, -10831009);
            } else if (a == 3) {
                str = LocaleController.getString("CurvesBlue", R.string.CurvesBlue);
                curveTextView.setText(str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase());
                curveTextView.setTextColor(-12734994);
                this.curveRadioButton[a].setColor(-12734994, -12734994);
            }
            frameLayout1.addView(curveTextView, LayoutHelper.createFrame(-2, -2.0f, 49, 0.0f, 38.0f, 0.0f, 0.0f));
            curveTextViewContainer.addView(frameLayout1, LayoutHelper.createLinear(-2, -2, a == 0 ? 0.0f : 30.0f, 0.0f, 0.0f, 0.0f));
            frameLayout1.setOnClickListener(new C26807());
            a++;
        }
        this.blurLayout = new FrameLayout(context);
        this.blurLayout.setVisibility(4);
        this.toolsView.addView(this.blurLayout, LayoutHelper.createFrame(280, 60.0f, 1, 0.0f, 40.0f, 0.0f, 0.0f));
        this.blurOffButton = new TextView(context);
        this.blurOffButton.setCompoundDrawablePadding(AndroidUtilities.dp(2.0f));
        this.blurOffButton.setTextSize(1, 13.0f);
        this.blurOffButton.setGravity(1);
        this.blurOffButton.setText(LocaleController.getString("BlurOff", R.string.BlurOff));
        this.blurLayout.addView(this.blurOffButton, LayoutHelper.createFrame(80, 60.0f));
        this.blurOffButton.setOnClickListener(new C26818());
        this.blurRadialButton = new TextView(context);
        this.blurRadialButton.setCompoundDrawablePadding(AndroidUtilities.dp(2.0f));
        this.blurRadialButton.setTextSize(1, 13.0f);
        this.blurRadialButton.setGravity(1);
        this.blurRadialButton.setText(LocaleController.getString("BlurRadial", R.string.BlurRadial));
        this.blurLayout.addView(this.blurRadialButton, LayoutHelper.createFrame(80, 80.0f, 51, 100.0f, 0.0f, 0.0f, 0.0f));
        this.blurRadialButton.setOnClickListener(new C26829());
        this.blurLinearButton = new TextView(context);
        this.blurLinearButton.setCompoundDrawablePadding(AndroidUtilities.dp(2.0f));
        this.blurLinearButton.setTextSize(1, 13.0f);
        this.blurLinearButton.setGravity(1);
        this.blurLinearButton.setText(LocaleController.getString("BlurLinear", R.string.BlurLinear));
        this.blurLayout.addView(this.blurLinearButton, LayoutHelper.createFrame(80, 80.0f, 51, 200.0f, 0.0f, 0.0f, 0.0f));
        this.blurLinearButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                PhotoFilterView.this.blurType = 2;
                PhotoFilterView.this.updateSelectedBlurType();
                PhotoFilterView.this.blurControl.setVisibility(0);
                PhotoFilterView.this.blurControl.setType(0);
                if (PhotoFilterView.this.eglThread != null) {
                    PhotoFilterView.this.eglThread.requestRender(false);
                }
            }
        });
        updateSelectedBlurType();
        if (VERSION.SDK_INT >= 21) {
            ((LayoutParams) this.textureView.getLayoutParams()).topMargin = AndroidUtilities.statusBarHeight;
            ((LayoutParams) this.curvesControl.getLayoutParams()).topMargin = AndroidUtilities.statusBarHeight;
        }
    }

    private void updateSelectedBlurType() {
        Drawable drawable;
        if (this.blurType == 0) {
            drawable = this.blurOffButton.getContext().getResources().getDrawable(R.drawable.blur_off).mutate();
            drawable.setColorFilter(new PorterDuffColorFilter(-11420173, Mode.MULTIPLY));
            this.blurOffButton.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
            this.blurOffButton.setTextColor(-11420173);
            this.blurRadialButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.blur_radial, 0, 0);
            this.blurRadialButton.setTextColor(-1);
            this.blurLinearButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.blur_linear, 0, 0);
            this.blurLinearButton.setTextColor(-1);
        } else if (this.blurType == 1) {
            this.blurOffButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.blur_off, 0, 0);
            this.blurOffButton.setTextColor(-1);
            drawable = this.blurOffButton.getContext().getResources().getDrawable(R.drawable.blur_radial).mutate();
            drawable.setColorFilter(new PorterDuffColorFilter(-11420173, Mode.MULTIPLY));
            this.blurRadialButton.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
            this.blurRadialButton.setTextColor(-11420173);
            this.blurLinearButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.blur_linear, 0, 0);
            this.blurLinearButton.setTextColor(-1);
        } else if (this.blurType == 2) {
            this.blurOffButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.blur_off, 0, 0);
            this.blurOffButton.setTextColor(-1);
            this.blurRadialButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.blur_radial, 0, 0);
            this.blurRadialButton.setTextColor(-1);
            drawable = this.blurOffButton.getContext().getResources().getDrawable(R.drawable.blur_linear).mutate();
            drawable.setColorFilter(new PorterDuffColorFilter(-11420173, Mode.MULTIPLY));
            this.blurLinearButton.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
            this.blurLinearButton.setTextColor(-11420173);
        }
    }

    public MediaController$SavedFilterState getSavedFilterState() {
        MediaController$SavedFilterState state = new MediaController$SavedFilterState();
        state.enhanceValue = this.enhanceValue;
        state.exposureValue = this.exposureValue;
        state.contrastValue = this.contrastValue;
        state.warmthValue = this.warmthValue;
        state.saturationValue = this.saturationValue;
        state.fadeValue = this.fadeValue;
        state.tintShadowsColor = this.tintShadowsColor;
        state.tintHighlightsColor = this.tintHighlightsColor;
        state.highlightsValue = this.highlightsValue;
        state.shadowsValue = this.shadowsValue;
        state.vignetteValue = this.vignetteValue;
        state.grainValue = this.grainValue;
        state.blurType = this.blurType;
        state.sharpenValue = this.sharpenValue;
        state.curvesToolValue = this.curvesToolValue;
        state.blurExcludeSize = this.blurExcludeSize;
        state.blurExcludePoint = this.blurExcludePoint;
        state.blurExcludeBlurSize = this.blurExcludeBlurSize;
        state.blurAngle = this.blurAngle;
        return state;
    }

    public boolean hasChanges() {
        if (this.lastState != null) {
            if (this.enhanceValue == this.lastState.enhanceValue && this.contrastValue == this.lastState.contrastValue && this.highlightsValue == this.lastState.highlightsValue && this.exposureValue == this.lastState.exposureValue && this.warmthValue == this.lastState.warmthValue && this.saturationValue == this.lastState.saturationValue && this.vignetteValue == this.lastState.vignetteValue && this.shadowsValue == this.lastState.shadowsValue && this.grainValue == this.lastState.grainValue && this.sharpenValue == this.lastState.sharpenValue && this.fadeValue == this.lastState.fadeValue && this.tintHighlightsColor == this.lastState.tintHighlightsColor && this.tintShadowsColor == this.lastState.tintShadowsColor && this.curvesToolValue.shouldBeSkipped()) {
                return false;
            }
            return true;
        } else if (this.enhanceValue == 0.0f && this.contrastValue == 0.0f && this.highlightsValue == 0.0f && this.exposureValue == 0.0f && this.warmthValue == 0.0f && this.saturationValue == 0.0f && this.vignetteValue == 0.0f && this.shadowsValue == 0.0f && this.grainValue == 0.0f && this.sharpenValue == 0.0f && this.fadeValue == 0.0f && this.tintHighlightsColor == 0 && this.tintShadowsColor == 0 && this.curvesToolValue.shouldBeSkipped()) {
            return false;
        } else {
            return true;
        }
    }

    public void onTouch(MotionEvent event) {
        if (event.getActionMasked() == 0 || event.getActionMasked() == 5) {
            LayoutParams layoutParams = (LayoutParams) this.textureView.getLayoutParams();
            if (layoutParams != null && event.getX() >= ((float) layoutParams.leftMargin) && event.getY() >= ((float) layoutParams.topMargin) && event.getX() <= ((float) (layoutParams.leftMargin + layoutParams.width)) && event.getY() <= ((float) (layoutParams.topMargin + layoutParams.height))) {
                setShowOriginal(true);
            }
        } else if (event.getActionMasked() == 1 || event.getActionMasked() == 6) {
            setShowOriginal(false);
        }
    }

    private void setShowOriginal(boolean value) {
        if (this.showOriginal != value) {
            this.showOriginal = value;
            if (this.eglThread != null) {
                this.eglThread.requestRender(false);
            }
        }
    }

    public void switchMode() {
        if (this.selectedTool == 0) {
            this.blurControl.setVisibility(4);
            this.blurLayout.setVisibility(4);
            this.curveLayout.setVisibility(4);
            this.curvesControl.setVisibility(4);
            this.recyclerListView.setVisibility(0);
        } else if (this.selectedTool == 1) {
            this.recyclerListView.setVisibility(4);
            this.curveLayout.setVisibility(4);
            this.curvesControl.setVisibility(4);
            this.blurLayout.setVisibility(0);
            if (this.blurType != 0) {
                this.blurControl.setVisibility(0);
            }
            updateSelectedBlurType();
        } else if (this.selectedTool == 2) {
            this.recyclerListView.setVisibility(4);
            this.blurLayout.setVisibility(4);
            this.blurControl.setVisibility(4);
            this.curveLayout.setVisibility(0);
            this.curvesControl.setVisibility(0);
            this.curvesToolValue.activeType = 0;
            for (int a = 0; a < 4; a++) {
                boolean z;
                RadioButton radioButton = this.curveRadioButton[a];
                if (a == 0) {
                    z = true;
                } else {
                    z = false;
                }
                radioButton.setChecked(z, false);
            }
        }
    }

    public void shutdown() {
        if (this.eglThread != null) {
            this.eglThread.shutdown();
            this.eglThread = null;
        }
        this.textureView.setVisibility(8);
    }

    public void init() {
        this.textureView.setVisibility(0);
    }

    public Bitmap getBitmap() {
        return this.eglThread != null ? this.eglThread.getTexture() : null;
    }

    private void fixLayout(int viewWidth, int viewHeight) {
        if (this.bitmapToEdit != null) {
            float bitmapW;
            float bitmapH;
            viewWidth -= AndroidUtilities.dp(28.0f);
            viewHeight -= (VERSION.SDK_INT >= 21 ? AndroidUtilities.statusBarHeight : 0) + AndroidUtilities.dp(214.0f);
            if (this.orientation % 360 == 90 || this.orientation % 360 == 270) {
                bitmapW = (float) this.bitmapToEdit.getHeight();
                bitmapH = (float) this.bitmapToEdit.getWidth();
            } else {
                bitmapW = (float) this.bitmapToEdit.getWidth();
                bitmapH = (float) this.bitmapToEdit.getHeight();
            }
            float scaleX = ((float) viewWidth) / bitmapW;
            float scaleY = ((float) viewHeight) / bitmapH;
            if (scaleX > scaleY) {
                bitmapH = (float) viewHeight;
                bitmapW = (float) ((int) Math.ceil((double) (bitmapW * scaleY)));
            } else {
                bitmapW = (float) viewWidth;
                bitmapH = (float) ((int) Math.ceil((double) (bitmapH * scaleX)));
            }
            int bitmapX = (int) Math.ceil((double) (((((float) viewWidth) - bitmapW) / 2.0f) + ((float) AndroidUtilities.dp(14.0f))));
            int bitmapY = (int) Math.ceil((double) (((float) (VERSION.SDK_INT >= 21 ? AndroidUtilities.statusBarHeight : 0)) + (((float) AndroidUtilities.dp(14.0f)) + ((((float) viewHeight) - bitmapH) / 2.0f))));
            LayoutParams layoutParams = (LayoutParams) this.textureView.getLayoutParams();
            layoutParams.leftMargin = bitmapX;
            layoutParams.topMargin = bitmapY;
            layoutParams.width = (int) bitmapW;
            layoutParams.height = (int) bitmapH;
            this.curvesControl.setActualArea((float) bitmapX, (float) (bitmapY - (VERSION.SDK_INT >= 21 ? AndroidUtilities.statusBarHeight : 0)), (float) layoutParams.width, (float) layoutParams.height);
            this.blurControl.setActualAreaSize((float) layoutParams.width, (float) layoutParams.height);
            ((LayoutParams) this.blurControl.getLayoutParams()).height = AndroidUtilities.dp(38.0f) + viewHeight;
            ((LayoutParams) this.curvesControl.getLayoutParams()).height = AndroidUtilities.dp(28.0f) + viewHeight;
            if (AndroidUtilities.isTablet()) {
                int total = AndroidUtilities.dp(86.0f) * 10;
                layoutParams = (LayoutParams) this.recyclerListView.getLayoutParams();
                if (total < viewWidth) {
                    layoutParams.width = total;
                    layoutParams.leftMargin = (viewWidth - total) / 2;
                    return;
                }
                layoutParams.width = -1;
                layoutParams.leftMargin = 0;
            }
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        fixLayout(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private float getShadowsValue() {
        return ((this.shadowsValue * 0.55f) + 100.0f) / 100.0f;
    }

    private float getHighlightsValue() {
        return ((this.highlightsValue * 0.75f) + 100.0f) / 100.0f;
    }

    private float getEnhanceValue() {
        return this.enhanceValue / 100.0f;
    }

    private float getExposureValue() {
        return this.exposureValue / 100.0f;
    }

    private float getContrastValue() {
        return ((this.contrastValue / 100.0f) * 0.3f) + 1.0f;
    }

    private float getWarmthValue() {
        return this.warmthValue / 100.0f;
    }

    private float getVignetteValue() {
        return this.vignetteValue / 100.0f;
    }

    private float getSharpenValue() {
        return 0.11f + ((this.sharpenValue / 100.0f) * 0.6f);
    }

    private float getGrainValue() {
        return (this.grainValue / 100.0f) * 0.04f;
    }

    private float getFadeValue() {
        return this.fadeValue / 100.0f;
    }

    private float getTintHighlightsIntensityValue() {
        return this.tintHighlightsColor == 0 ? 0.0f : 50.0f / 100.0f;
    }

    private float getTintShadowsIntensityValue() {
        return this.tintShadowsColor == 0 ? 0.0f : 50.0f / 100.0f;
    }

    private float getSaturationValue() {
        float parameterValue = this.saturationValue / 100.0f;
        if (parameterValue > 0.0f) {
            parameterValue *= 1.05f;
        }
        return 1.0f + parameterValue;
    }

    public FrameLayout getToolsView() {
        return this.toolsView;
    }

    public TextView getDoneTextView() {
        return this.doneTextView;
    }

    public TextView getCancelTextView() {
        return this.cancelTextView;
    }
}

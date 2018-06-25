package org.telegram.ui.Components.Paint;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.opengl.GLES20;
import com.google.android.gms.gcm.Task;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Map;
import java.util.UUID;
import org.telegram.messenger.DispatchQueue;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.ui.Components.Size;

public class Painting {
    private Path activePath;
    private RectF activeStrokeBounds;
    private Slice backupSlice;
    private Texture bitmapTexture;
    private Brush brush;
    private Texture brushTexture;
    private int[] buffers = new int[1];
    private ByteBuffer dataBuffer;
    private PaintingDelegate delegate;
    private int paintTexture;
    private boolean paused;
    private float[] projection;
    private float[] renderProjection;
    private RenderState renderState = new RenderState();
    private RenderView renderView;
    private int reusableFramebuffer;
    private Map<String, Shader> shaders;
    private Size size;
    private int suppressChangesCounter;
    private ByteBuffer textureBuffer;
    private ByteBuffer vertexBuffer;

    public class PaintingData {
        public Bitmap bitmap;
        public ByteBuffer data;

        PaintingData(Bitmap bitmap, ByteBuffer byteBuffer) {
            this.bitmap = bitmap;
            this.data = byteBuffer;
        }
    }

    public interface PaintingDelegate {
        void contentChanged(RectF rectF);

        DispatchQueue requestDispatchQueue();

        UndoStore requestUndoStore();

        void strokeCommited();
    }

    public Painting(Size size) {
        this.size = size;
        this.dataBuffer = ByteBuffer.allocateDirect((((int) this.size.width) * ((int) this.size.height)) * 4);
        this.projection = GLMatrix.LoadOrtho(BitmapDescriptorFactory.HUE_RED, this.size.width, BitmapDescriptorFactory.HUE_RED, this.size.height, -1.0f, 1.0f);
        if (this.vertexBuffer == null) {
            this.vertexBuffer = ByteBuffer.allocateDirect(32);
            this.vertexBuffer.order(ByteOrder.nativeOrder());
        }
        this.vertexBuffer.putFloat(BitmapDescriptorFactory.HUE_RED);
        this.vertexBuffer.putFloat(BitmapDescriptorFactory.HUE_RED);
        this.vertexBuffer.putFloat(this.size.width);
        this.vertexBuffer.putFloat(BitmapDescriptorFactory.HUE_RED);
        this.vertexBuffer.putFloat(BitmapDescriptorFactory.HUE_RED);
        this.vertexBuffer.putFloat(this.size.height);
        this.vertexBuffer.putFloat(this.size.width);
        this.vertexBuffer.putFloat(this.size.height);
        this.vertexBuffer.rewind();
        if (this.textureBuffer == null) {
            this.textureBuffer = ByteBuffer.allocateDirect(32);
            this.textureBuffer.order(ByteOrder.nativeOrder());
            this.textureBuffer.putFloat(BitmapDescriptorFactory.HUE_RED);
            this.textureBuffer.putFloat(BitmapDescriptorFactory.HUE_RED);
            this.textureBuffer.putFloat(1.0f);
            this.textureBuffer.putFloat(BitmapDescriptorFactory.HUE_RED);
            this.textureBuffer.putFloat(BitmapDescriptorFactory.HUE_RED);
            this.textureBuffer.putFloat(1.0f);
            this.textureBuffer.putFloat(1.0f);
            this.textureBuffer.putFloat(1.0f);
            this.textureBuffer.rewind();
        }
    }

    private void beginSuppressingChanges() {
        this.suppressChangesCounter++;
    }

    private void endSuppressingChanges() {
        this.suppressChangesCounter--;
    }

    private int getPaintTexture() {
        if (this.paintTexture == 0) {
            this.paintTexture = Texture.generateTexture(this.size);
        }
        return this.paintTexture;
    }

    private int getReusableFramebuffer() {
        if (this.reusableFramebuffer == 0) {
            int[] iArr = new int[1];
            GLES20.glGenFramebuffers(1, iArr, 0);
            this.reusableFramebuffer = iArr[0];
            Utils.HasGLError();
        }
        return this.reusableFramebuffer;
    }

    private int getTexture() {
        return this.bitmapTexture != null ? this.bitmapTexture.texture() : 0;
    }

    private boolean isSuppressingChanges() {
        return this.suppressChangesCounter > 0;
    }

    private void registerUndo(RectF rectF) {
        if (rectF != null && rectF.setIntersect(rectF, getBounds())) {
            final Slice slice = new Slice(getPaintingData(rectF, true).data, rectF, this.delegate.requestDispatchQueue());
            this.delegate.requestUndoStore().registerUndo(UUID.randomUUID(), new Runnable() {
                public void run() {
                    Painting.this.restoreSlice(slice);
                }
            });
        }
    }

    private void render(int i, int i2) {
        Shader shader = (Shader) this.shaders.get(this.brush.isLightSaber() ? "blitWithMaskLight" : "blitWithMask");
        if (shader != null) {
            GLES20.glUseProgram(shader.program);
            GLES20.glUniformMatrix4fv(shader.getUniform("mvpMatrix"), 1, false, FloatBuffer.wrap(this.renderProjection));
            GLES20.glUniform1i(shader.getUniform("texture"), 0);
            GLES20.glUniform1i(shader.getUniform("mask"), 1);
            Shader.SetColorUniform(shader.getUniform(TtmlNode.ATTR_TTS_COLOR), i2);
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(3553, getTexture());
            GLES20.glActiveTexture(33985);
            GLES20.glBindTexture(3553, i);
            GLES20.glBlendFunc(1, 771);
            GLES20.glVertexAttribPointer(0, 2, 5126, false, 8, this.vertexBuffer);
            GLES20.glEnableVertexAttribArray(0);
            GLES20.glVertexAttribPointer(1, 2, 5126, false, 8, this.textureBuffer);
            GLES20.glEnableVertexAttribArray(1);
            GLES20.glDrawArrays(5, 0, 4);
            Utils.HasGLError();
        }
    }

    private void renderBlit() {
        Shader shader = (Shader) this.shaders.get("blit");
        if (shader != null) {
            GLES20.glUseProgram(shader.program);
            GLES20.glUniformMatrix4fv(shader.getUniform("mvpMatrix"), 1, false, FloatBuffer.wrap(this.renderProjection));
            GLES20.glUniform1i(shader.getUniform("texture"), 0);
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(3553, getTexture());
            GLES20.glBlendFunc(1, 771);
            GLES20.glVertexAttribPointer(0, 2, 5126, false, 8, this.vertexBuffer);
            GLES20.glEnableVertexAttribArray(0);
            GLES20.glVertexAttribPointer(1, 2, 5126, false, 8, this.textureBuffer);
            GLES20.glEnableVertexAttribArray(1);
            GLES20.glDrawArrays(5, 0, 4);
            Utils.HasGLError();
        }
    }

    private void restoreSlice(final Slice slice) {
        this.renderView.performInContext(new Runnable() {
            public void run() {
                Buffer data = slice.getData();
                GLES20.glBindTexture(3553, Painting.this.getTexture());
                GLES20.glTexSubImage2D(3553, 0, slice.getX(), slice.getY(), slice.getWidth(), slice.getHeight(), 6408, 5121, data);
                if (!(Painting.this.isSuppressingChanges() || Painting.this.delegate == null)) {
                    Painting.this.delegate.contentChanged(slice.getBounds());
                }
                slice.cleanResources();
            }
        });
    }

    private void update(RectF rectF, Runnable runnable) {
        GLES20.glBindFramebuffer(36160, getReusableFramebuffer());
        GLES20.glFramebufferTexture2D(36160, 36064, 3553, getTexture(), 0);
        if (GLES20.glCheckFramebufferStatus(36160) == 36053) {
            GLES20.glViewport(0, 0, (int) this.size.width, (int) this.size.height);
            runnable.run();
        }
        GLES20.glBindFramebuffer(36160, 0);
        if (!isSuppressingChanges() && this.delegate != null) {
            this.delegate.contentChanged(rectF);
        }
    }

    public void cleanResources(boolean z) {
        if (this.reusableFramebuffer != 0) {
            this.buffers[0] = this.reusableFramebuffer;
            GLES20.glDeleteFramebuffers(1, this.buffers, 0);
            this.reusableFramebuffer = 0;
        }
        this.bitmapTexture.cleanResources(z);
        if (this.paintTexture != 0) {
            this.buffers[0] = this.paintTexture;
            GLES20.glDeleteTextures(1, this.buffers, 0);
            this.paintTexture = 0;
        }
        if (this.brushTexture != null) {
            this.brushTexture.cleanResources(true);
            this.brushTexture = null;
        }
        if (this.shaders != null) {
            for (Shader cleanResources : this.shaders.values()) {
                cleanResources.cleanResources();
            }
            this.shaders = null;
        }
    }

    public void commitStroke(final int i) {
        this.renderView.performInContext(new Runnable() {

            /* renamed from: org.telegram.ui.Components.Paint.Painting$2$1 */
            class C44711 implements Runnable {
                C44711() {
                }

                public void run() {
                    if (Painting.this.shaders != null) {
                        Shader shader = (Shader) Painting.this.shaders.get(Painting.this.brush.isLightSaber() ? "compositeWithMaskLight" : "compositeWithMask");
                        if (shader != null) {
                            GLES20.glUseProgram(shader.program);
                            GLES20.glUniformMatrix4fv(shader.getUniform("mvpMatrix"), 1, false, FloatBuffer.wrap(Painting.this.projection));
                            GLES20.glUniform1i(shader.getUniform("mask"), 0);
                            Shader.SetColorUniform(shader.getUniform(TtmlNode.ATTR_TTS_COLOR), i);
                            GLES20.glActiveTexture(33984);
                            GLES20.glBindTexture(3553, Painting.this.getPaintTexture());
                            GLES20.glBlendFuncSeparate(770, 771, 770, 1);
                            GLES20.glVertexAttribPointer(0, 2, 5126, false, 8, Painting.this.vertexBuffer);
                            GLES20.glEnableVertexAttribArray(0);
                            GLES20.glVertexAttribPointer(1, 2, 5126, false, 8, Painting.this.textureBuffer);
                            GLES20.glEnableVertexAttribArray(1);
                            GLES20.glDrawArrays(5, 0, 4);
                        }
                    }
                }
            }

            public void run() {
                Painting.this.registerUndo(Painting.this.activeStrokeBounds);
                Painting.this.beginSuppressingChanges();
                Painting.this.update(null, new C44711());
                Painting.this.endSuppressingChanges();
                Painting.this.renderState.reset();
                Painting.this.activeStrokeBounds = null;
                Painting.this.activePath = null;
            }
        });
    }

    public RectF getBounds() {
        return new RectF(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, this.size.width, this.size.height);
    }

    public PaintingData getPaintingData(RectF rectF, boolean z) {
        int i = (int) rectF.left;
        int i2 = (int) rectF.top;
        int width = (int) rectF.width();
        int height = (int) rectF.height();
        GLES20.glGenFramebuffers(1, this.buffers, 0);
        int i3 = this.buffers[0];
        GLES20.glBindFramebuffer(36160, i3);
        GLES20.glGenTextures(1, this.buffers, 0);
        int i4 = this.buffers[0];
        GLES20.glBindTexture(3553, i4);
        GLES20.glTexParameteri(3553, 10241, 9729);
        GLES20.glTexParameteri(3553, Task.EXTRAS_LIMIT_BYTES, 9729);
        GLES20.glTexParameteri(3553, 10242, 33071);
        GLES20.glTexParameteri(3553, 10243, 33071);
        GLES20.glTexImage2D(3553, 0, 6408, width, height, 0, 6408, 5121, null);
        GLES20.glFramebufferTexture2D(36160, 36064, 3553, i4, 0);
        GLES20.glViewport(0, 0, (int) this.size.width, (int) this.size.height);
        if (this.shaders == null) {
            return null;
        }
        Shader shader = (Shader) this.shaders.get(z ? "nonPremultipliedBlit" : "blit");
        if (shader == null) {
            return null;
        }
        PaintingData paintingData;
        GLES20.glUseProgram(shader.program);
        Matrix matrix = new Matrix();
        matrix.preTranslate((float) (-i), (float) (-i2));
        GLES20.glUniformMatrix4fv(shader.getUniform("mvpMatrix"), 1, false, FloatBuffer.wrap(GLMatrix.MultiplyMat4f(this.projection, GLMatrix.LoadGraphicsMatrix(matrix))));
        if (z) {
            GLES20.glUniform1i(shader.getUniform("texture"), 0);
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(3553, getTexture());
        } else {
            GLES20.glUniform1i(shader.getUniform("texture"), 0);
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(3553, this.bitmapTexture.texture());
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(3553, getTexture());
        }
        GLES20.glClearColor(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
        GLES20.glClear(MessagesController.UPDATE_MASK_CHAT_ADMINS);
        GLES20.glBlendFunc(1, 771);
        GLES20.glVertexAttribPointer(0, 2, 5126, false, 8, this.vertexBuffer);
        GLES20.glEnableVertexAttribArray(0);
        GLES20.glVertexAttribPointer(1, 2, 5126, false, 8, this.textureBuffer);
        GLES20.glEnableVertexAttribArray(1);
        GLES20.glDrawArrays(5, 0, 4);
        this.dataBuffer.limit((width * height) * 4);
        GLES20.glReadPixels(0, 0, width, height, 6408, 5121, this.dataBuffer);
        if (z) {
            paintingData = new PaintingData(null, this.dataBuffer);
        } else {
            Bitmap createBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
            createBitmap.copyPixelsFromBuffer(this.dataBuffer);
            paintingData = new PaintingData(createBitmap, null);
        }
        this.buffers[0] = i3;
        GLES20.glDeleteFramebuffers(1, this.buffers, 0);
        this.buffers[0] = i4;
        GLES20.glDeleteTextures(1, this.buffers, 0);
        return paintingData;
    }

    public Size getSize() {
        return this.size;
    }

    public boolean isPaused() {
        return this.paused;
    }

    public void onPause(final Runnable runnable) {
        this.renderView.performInContext(new Runnable() {
            public void run() {
                Painting.this.paused = true;
                Painting.this.backupSlice = new Slice(Painting.this.getPaintingData(Painting.this.getBounds(), true).data, Painting.this.getBounds(), Painting.this.delegate.requestDispatchQueue());
                Painting.this.cleanResources(false);
                if (runnable != null) {
                    runnable.run();
                }
            }
        });
    }

    public void onResume() {
        restoreSlice(this.backupSlice);
        this.backupSlice = null;
        this.paused = false;
    }

    public void paintStroke(final Path path, final boolean z, final Runnable runnable) {
        this.renderView.performInContext(new Runnable() {
            public void run() {
                Painting.this.activePath = path;
                RectF rectF = null;
                GLES20.glBindFramebuffer(36160, Painting.this.getReusableFramebuffer());
                GLES20.glFramebufferTexture2D(36160, 36064, 3553, Painting.this.getPaintTexture(), 0);
                Utils.HasGLError();
                if (GLES20.glCheckFramebufferStatus(36160) == 36053) {
                    GLES20.glViewport(0, 0, (int) Painting.this.size.width, (int) Painting.this.size.height);
                    if (z) {
                        GLES20.glClearColor(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
                        GLES20.glClear(MessagesController.UPDATE_MASK_CHAT_ADMINS);
                    }
                    if (Painting.this.shaders != null) {
                        Shader shader = (Shader) Painting.this.shaders.get(Painting.this.brush.isLightSaber() ? "brushLight" : "brush");
                        if (shader != null) {
                            GLES20.glUseProgram(shader.program);
                            if (Painting.this.brushTexture == null) {
                                Painting.this.brushTexture = new Texture(Painting.this.brush.getStamp());
                            }
                            GLES20.glActiveTexture(33984);
                            GLES20.glBindTexture(3553, Painting.this.brushTexture.texture());
                            GLES20.glUniformMatrix4fv(shader.getUniform("mvpMatrix"), 1, false, FloatBuffer.wrap(Painting.this.projection));
                            GLES20.glUniform1i(shader.getUniform("texture"), 0);
                            rectF = Render.RenderPath(path, Painting.this.renderState);
                        } else {
                            return;
                        }
                    }
                    return;
                }
                GLES20.glBindFramebuffer(36160, 0);
                if (Painting.this.delegate != null) {
                    Painting.this.delegate.contentChanged(rectF);
                }
                if (Painting.this.activeStrokeBounds != null) {
                    Painting.this.activeStrokeBounds.union(rectF);
                } else {
                    Painting.this.activeStrokeBounds = rectF;
                }
                if (runnable != null) {
                    runnable.run();
                }
            }
        });
    }

    public void render() {
        if (this.shaders != null) {
            if (this.activePath != null) {
                render(getPaintTexture(), this.activePath.getColor());
            } else {
                renderBlit();
            }
        }
    }

    public void setBitmap(Bitmap bitmap) {
        if (this.bitmapTexture == null) {
            this.bitmapTexture = new Texture(bitmap);
        }
    }

    public void setBrush(Brush brush) {
        this.brush = brush;
        if (this.brushTexture != null) {
            this.brushTexture.cleanResources(true);
            this.brushTexture = null;
        }
    }

    public void setDelegate(PaintingDelegate paintingDelegate) {
        this.delegate = paintingDelegate;
    }

    public void setRenderProjection(float[] fArr) {
        this.renderProjection = fArr;
    }

    public void setRenderView(RenderView renderView) {
        this.renderView = renderView;
    }

    public void setupShaders() {
        this.shaders = ShaderSet.setup();
    }
}

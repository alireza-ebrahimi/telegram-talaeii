package org.telegram.customization.compression.lz4;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import org.telegram.customization.compression.util.Native;
import org.telegram.customization.compression.util.Utils;

public final class LZ4Factory {
    private static LZ4Factory JAVA_SAFE_INSTANCE;
    private static LZ4Factory JAVA_UNSAFE_INSTANCE;
    private static LZ4Factory NATIVE_INSTANCE;
    private final LZ4Compressor fastCompressor;
    private final LZ4FastDecompressor fastDecompressor;
    private final LZ4Compressor highCompressor;
    private final LZ4Compressor[] highCompressors = new LZ4Compressor[18];
    private final String impl;
    private final LZ4SafeDecompressor safeDecompressor;

    private static LZ4Factory instance(String impl) {
        try {
            return new LZ4Factory(impl);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    public static synchronized LZ4Factory nativeInstance() {
        LZ4Factory lZ4Factory;
        synchronized (LZ4Factory.class) {
            if (NATIVE_INSTANCE == null) {
                NATIVE_INSTANCE = instance("JNI");
            }
            lZ4Factory = NATIVE_INSTANCE;
        }
        return lZ4Factory;
    }

    public static synchronized LZ4Factory safeInstance() {
        LZ4Factory lZ4Factory;
        synchronized (LZ4Factory.class) {
            if (JAVA_SAFE_INSTANCE == null) {
                JAVA_SAFE_INSTANCE = instance("JavaSafe");
            }
            lZ4Factory = JAVA_SAFE_INSTANCE;
        }
        return lZ4Factory;
    }

    public static synchronized LZ4Factory unsafeInstance() {
        LZ4Factory lZ4Factory;
        synchronized (LZ4Factory.class) {
            if (JAVA_UNSAFE_INSTANCE == null) {
                JAVA_UNSAFE_INSTANCE = instance("JavaUnsafe");
            }
            lZ4Factory = JAVA_UNSAFE_INSTANCE;
        }
        return lZ4Factory;
    }

    public static LZ4Factory fastestJavaInstance() {
        if (!Utils.isUnalignedAccessAllowed()) {
            return safeInstance();
        }
        try {
            return unsafeInstance();
        } catch (Throwable th) {
            return safeInstance();
        }
    }

    public static LZ4Factory fastestInstance() {
        if (!Native.isLoaded() && Native.class.getClassLoader() != ClassLoader.getSystemClassLoader()) {
            return fastestJavaInstance();
        }
        try {
            return nativeInstance();
        } catch (Throwable th) {
            return fastestJavaInstance();
        }
    }

    private static <T> T classInstance(String cls) throws NoSuchFieldException, SecurityException, ClassNotFoundException, IllegalArgumentException, IllegalAccessException {
        ClassLoader loader = LZ4Factory.class.getClassLoader();
        if (loader == null) {
            loader = ClassLoader.getSystemClassLoader();
        }
        return loader.loadClass(cls).getField("INSTANCE").get(null);
    }

    private LZ4Factory(String impl) throws ClassNotFoundException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        this.impl = impl;
        this.fastCompressor = (LZ4Compressor) classInstance("org.telegram.customization.compression.lz4.LZ4" + impl + "Compressor");
        this.highCompressor = (LZ4Compressor) classInstance("org.telegram.customization.compression.lz4.LZ4HC" + impl + "Compressor");
        this.fastDecompressor = (LZ4FastDecompressor) classInstance("org.telegram.customization.compression.lz4.LZ4" + impl + "FastDecompressor");
        this.safeDecompressor = (LZ4SafeDecompressor) classInstance("org.telegram.customization.compression.lz4.LZ4" + impl + "SafeDecompressor");
        Constructor<? extends LZ4Compressor> highConstructor = this.highCompressor.getClass().getDeclaredConstructor(new Class[]{Integer.TYPE});
        this.highCompressors[9] = this.highCompressor;
        for (int level = 1; level <= 17; level++) {
            if (level != 9) {
                this.highCompressors[level] = (LZ4Compressor) highConstructor.newInstance(new Object[]{Integer.valueOf(level)});
            }
        }
        byte[] original = new byte[]{(byte) 97, (byte) 98, (byte) 99, (byte) 100, (byte) 32, (byte) 32, (byte) 32, (byte) 32, (byte) 32, (byte) 32, (byte) 97, (byte) 98, (byte) 99, (byte) 100, (byte) 101, (byte) 102, (byte) 103, (byte) 104, (byte) 105, (byte) 106};
        for (LZ4Compressor compressor : Arrays.asList(new LZ4Compressor[]{this.fastCompressor, this.highCompressor})) {
            int maxCompressedLength = compressor.maxCompressedLength(original.length);
            byte[] compressed = new byte[maxCompressedLength];
            int compressedLength = compressor.compress(original, 0, original.length, compressed, 0, maxCompressedLength);
            byte[] restored = new byte[original.length];
            this.fastDecompressor.decompress(compressed, 0, restored, 0, original.length);
            if (Arrays.equals(original, restored)) {
                Arrays.fill(restored, (byte) 0);
                if (this.safeDecompressor.decompress(compressed, 0, compressedLength, restored, 0) == original.length) {
                    if (!Arrays.equals(original, restored)) {
                    }
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
    }

    public LZ4Compressor fastCompressor() {
        return this.fastCompressor;
    }

    public LZ4Compressor highCompressor() {
        return this.highCompressor;
    }

    public LZ4Compressor highCompressor(int compressionLevel) {
        if (compressionLevel > 17) {
            compressionLevel = 17;
        } else if (compressionLevel < 1) {
            compressionLevel = 9;
        }
        return this.highCompressors[compressionLevel];
    }

    public LZ4FastDecompressor fastDecompressor() {
        return this.fastDecompressor;
    }

    public LZ4SafeDecompressor safeDecompressor() {
        return this.safeDecompressor;
    }

    public LZ4UnknownSizeDecompressor unknownSizeDecompressor() {
        return safeDecompressor();
    }

    public LZ4Decompressor decompressor() {
        return fastDecompressor();
    }

    public String toString() {
        return getClass().getSimpleName() + ":" + this.impl;
    }
}

package org.telegram.customization.compression.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public enum Native {
    ;
    
    private static boolean loaded;

    private enum OS {
        private static final /* synthetic */ OS[] $VALUES = null;
        public static final OS LINUX = null;
        public static final OS MAC = null;
        public static final OS SOLARIS = null;
        public static final OS WINDOWS = null;
        public final String libExtension;
        public final String name;

        public static OS valueOf(String name) {
            return (OS) Enum.valueOf(OS.class, name);
        }

        public static OS[] values() {
            return (OS[]) $VALUES.clone();
        }

        static {
            WINDOWS = new OS("WINDOWS", 0, "win32", "so");
            LINUX = new OS("LINUX", 1, "linux", "so");
            MAC = new OS("MAC", 2, "darwin", "dylib");
            SOLARIS = new OS("SOLARIS", 3, "solaris", "so");
            $VALUES = new OS[]{WINDOWS, LINUX, MAC, SOLARIS};
        }

        private OS(String str, int i, String name, String libExtension) {
            this.name = name;
            this.libExtension = libExtension;
        }
    }

    static {
        loaded = false;
    }

    private static String arch() {
        return System.getProperty("os.arch");
    }

    private static OS os() {
        String osName = System.getProperty("os.name");
        if (osName.contains("Linux")) {
            return OS.LINUX;
        }
        if (osName.contains("Mac")) {
            return OS.MAC;
        }
        if (osName.contains("Windows")) {
            return OS.WINDOWS;
        }
        if (osName.contains("Solaris") || osName.contains("SunOS")) {
            return OS.SOLARIS;
        }
        throw new UnsupportedOperationException("Unsupported operating system: " + osName);
    }

    private static String resourceName() {
        OS os = os();
        return "/" + os.name + "/" + arch() + "/liblz4-java." + os.libExtension;
    }

    public static synchronized boolean isLoaded() {
        boolean z;
        synchronized (Native.class) {
            z = loaded;
        }
        return z;
    }

    public static synchronized void load() {
        synchronized (Native.class) {
            if (!loaded) {
                String resourceName = resourceName();
                InputStream is = null;
                if (is == null) {
                    throw new UnsupportedOperationException("Unsupported OS/arch, cannot find " + resourceName + ". Please try building from source.");
                }
                File tempLib;
                FileOutputStream out;
                try {
                    tempLib = File.createTempFile("liblz4-java", "." + os().libExtension);
                    out = new FileOutputStream(tempLib);
                    byte[] buf = new byte[4096];
                    while (true) {
                        int read = is.read(buf);
                        if (read == -1) {
                            try {
                                break;
                            } catch (IOException e) {
                            }
                        } else {
                            out.write(buf, 0, read);
                        }
                    }
                    out.close();
                    out = null;
                    System.load(tempLib.getAbsolutePath());
                    loaded = true;
                    if (out != null) {
                        try {
                            out.close();
                        } catch (IOException e2) {
                        }
                    }
                    if (tempLib != null) {
                        if (tempLib.exists()) {
                            if (loaded) {
                                tempLib.deleteOnExit();
                            } else {
                                tempLib.delete();
                            }
                        }
                    }
                } catch (IOException e3) {
                    throw new ExceptionInInitializerError("Cannot unpack liblz4-java");
                } catch (Throwable th) {
                    if (out != null) {
                        try {
                            out.close();
                        } catch (IOException e4) {
                        }
                    }
                    if (tempLib != null) {
                        if (tempLib.exists()) {
                            if (loaded) {
                                tempLib.deleteOnExit();
                            } else {
                                tempLib.delete();
                            }
                        }
                    }
                }
            }
        }
    }
}

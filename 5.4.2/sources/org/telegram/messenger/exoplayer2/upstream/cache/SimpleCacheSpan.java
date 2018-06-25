package org.telegram.messenger.exoplayer2.upstream.cache;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.util.Assertions;
import org.telegram.messenger.exoplayer2.util.Util;

final class SimpleCacheSpan extends CacheSpan {
    private static final Pattern CACHE_FILE_PATTERN_V1 = Pattern.compile("^(.+)\\.(\\d+)\\.(\\d+)\\.v1\\.exo$", 32);
    private static final Pattern CACHE_FILE_PATTERN_V2 = Pattern.compile("^(.+)\\.(\\d+)\\.(\\d+)\\.v2\\.exo$", 32);
    private static final Pattern CACHE_FILE_PATTERN_V3 = Pattern.compile("^(\\d+)\\.(\\d+)\\.(\\d+)\\.v3\\.exo$", 32);
    private static final String SUFFIX = ".v3.exo";

    private SimpleCacheSpan(String str, long j, long j2, long j3, File file) {
        super(str, j, j2, j3, file);
    }

    public static SimpleCacheSpan createCacheEntry(File file, CachedContentIndex cachedContentIndex) {
        File file2;
        CharSequence name = file.getName();
        if (name.endsWith(SUFFIX)) {
            file2 = file;
        } else {
            file2 = upgradeFile(file, cachedContentIndex);
            if (file2 == null) {
                return null;
            }
            name = file2.getName();
        }
        Matcher matcher = CACHE_FILE_PATTERN_V3.matcher(name);
        if (!matcher.matches()) {
            return null;
        }
        long length = file2.length();
        String keyForId = cachedContentIndex.getKeyForId(Integer.parseInt(matcher.group(1)));
        return keyForId == null ? null : new SimpleCacheSpan(keyForId, Long.parseLong(matcher.group(2)), length, Long.parseLong(matcher.group(3)), file2);
    }

    public static SimpleCacheSpan createClosedHole(String str, long j, long j2) {
        return new SimpleCacheSpan(str, j, j2, C3446C.TIME_UNSET, null);
    }

    public static SimpleCacheSpan createLookup(String str, long j) {
        return new SimpleCacheSpan(str, j, -1, C3446C.TIME_UNSET, null);
    }

    public static SimpleCacheSpan createOpenHole(String str, long j) {
        return new SimpleCacheSpan(str, j, -1, C3446C.TIME_UNSET, null);
    }

    public static File getCacheFile(File file, int i, long j, long j2) {
        return new File(file, i + "." + j + "." + j2 + SUFFIX);
    }

    private static File upgradeFile(File file, CachedContentIndex cachedContentIndex) {
        String unescapeFileName;
        Matcher matcher;
        CharSequence name = file.getName();
        Matcher matcher2 = CACHE_FILE_PATTERN_V2.matcher(name);
        if (matcher2.matches()) {
            unescapeFileName = Util.unescapeFileName(matcher2.group(1));
            if (unescapeFileName == null) {
                return null;
            }
            matcher = matcher2;
        } else {
            matcher2 = CACHE_FILE_PATTERN_V1.matcher(name);
            if (!matcher2.matches()) {
                return null;
            }
            unescapeFileName = matcher2.group(1);
            matcher = matcher2;
        }
        File cacheFile = getCacheFile(file.getParentFile(), cachedContentIndex.assignIdForKey(unescapeFileName), Long.parseLong(matcher.group(2)), Long.parseLong(matcher.group(3)));
        return !file.renameTo(cacheFile) ? null : cacheFile;
    }

    public SimpleCacheSpan copyWithUpdatedLastAccessTime(int i) {
        Assertions.checkState(this.isCached);
        long currentTimeMillis = System.currentTimeMillis();
        return new SimpleCacheSpan(this.key, this.position, this.length, currentTimeMillis, getCacheFile(this.file.getParentFile(), i, this.position, currentTimeMillis));
    }
}

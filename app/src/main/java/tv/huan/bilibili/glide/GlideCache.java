package tv.huan.bilibili.glide;

import android.content.Context;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;

import java.io.File;

@Keep
@GlideModule
public class GlideCache extends AppGlideModule {

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        File filesDir = context.getFilesDir();
        if (null == filesDir || !filesDir.exists()) {
            filesDir.mkdirs();
        }
        String absolutePath = filesDir.getAbsolutePath();
        File filesGlide = new File(absolutePath, "glide");
        if (null == filesGlide || !filesGlide.exists()) {
            filesGlide.mkdir();
        }
        String diskCacheFolder = filesGlide.getAbsolutePath();
        DiskLruCacheFactory diskLruCacheFactory = new DiskLruCacheFactory(diskCacheFolder, 200 * 1024 * 1024);
        builder.setDiskCache(diskLruCacheFactory);
        builder.setMemoryCache(new LruResourceCache(100 * 1024 * 1024));
    }
}

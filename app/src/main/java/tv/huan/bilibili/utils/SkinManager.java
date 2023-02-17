package tv.huan.bilibili.utils;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * 皮肤薄下载
 */
public final class SkinManager {

    public static void downloadSkin(Context context, int prod, String dirs) {

        String[] skins = {
                "skin_background.png",
                "skin_secondbar_logo.png",
                "skin_secondbar_vip.png",
                "skin_secondbar_vip_backgroung_focus.png",
                "skin_secondbar_search_focus.png",
                "skin_nine_tab_focus.9.png",
                "skin_nine_background_focus.9.png",
                "skin_nine_keyboard_focus.9.png"};

        for (int i = 0; i < 8; i++) {

            File files = context.getFilesDir();
            if (files.exists() && files.isFile()) {
                files.delete();
            }
            if (!files.exists()) {
                files.mkdirs();
            }

            File prods = new File(files, "/skin/" + prod);
            if (prods.exists() && prods.isFile()) {
                prods.delete();
            }
            if (!prods.exists()) {
                prods.mkdirs();
            }

            File image = new File(prods, skins[i]);
            if (image.exists() && image.isFile())
                continue;

            if (image.exists()) {
                image.delete();
            }

            // 下载图片
            try {

                String path = image.getAbsolutePath();
                String url = dirs + File.separator + skins[i];

                File file = Glide.with(context)
                        .asFile()
                        .skipMemoryCache(true)//跳过内存缓存
                        .diskCacheStrategy(DiskCacheStrategy.NONE)//不缓冲disk硬盘中
                        .load(url)
                        .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();

                if (null == file)
                    continue;

                long length = file.length();
                if (length <= 0)
                    continue;

                FileInputStream fis = new FileInputStream(file);
                FileOutputStream fos = new FileOutputStream(path);
                int len;
                byte[] bytes = new byte[4096];
                while ((len = fis.read(bytes)) != -1) {
                    fos.write(bytes, 0, len);
                }
                fos.flush();
                fis.close();
                fos.close();
            } catch (Exception e) {
            }
        }
    }
}
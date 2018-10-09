package nbsix.com.duskycvre.Utils.GlideTransformation;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

import nbsix.com.duskycvre.Utils.BitmapUtils;

/**
 * @AUTHOR: dsy
 * @TIME: 2018/5/2
 * @DESCRIPTION:
 */
public class Reflected extends BitmapTransformation {
    private static final String ID = "nbsix.com.duskycvre.Utils.GlideTransformation.Reflected";
    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {

        return  BitmapUtils.createReflectedBitmap(toTransform);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Reflected;
    }

    @Override
    public int hashCode() {
        return ID.hashCode();
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {

    }
}

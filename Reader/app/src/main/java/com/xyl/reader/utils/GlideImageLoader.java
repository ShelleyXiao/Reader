package com.xyl.reader.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xyl.reader.R;
import com.youth.banner.loader.ImageLoader;

/**
 * User: ShaudXiao
 * Date: 2017-01-18
 * Time: 15:25
 * Company: zx
 * Description:
 * FIXME
 */


public class GlideImageLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context)
                .load(path)
                .placeholder(R.drawable.img_two_bi_one)
                .error(R.drawable.img_two_bi_one)
                .crossFade(1000)
                .into(imageView);
    }
}

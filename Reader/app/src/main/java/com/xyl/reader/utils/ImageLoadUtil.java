package com.xyl.reader.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xyl.reader.R;

/**
 * User: ShaudXiao
 * Date: 2017-01-12
 * Time: 14:26
 * Company: zx
 * Description:
 * FIXME
 */


public class ImageLoadUtil {

    private static ImageLoadUtil instance;

    // 六图的随机图
    private static int[] homeSix = {R.drawable.home_six_1, R.drawable.home_six_2, R.drawable.home_six_3, R.drawable.home_six_4,
            R.drawable.home_six_5, R.drawable.home_six_6, R.drawable.home_six_7, R.drawable.home_six_8, R.drawable.home_six_9,
            R.drawable.home_six_10, R.drawable.home_six_11, R.drawable.home_six_12, R.drawable.home_six_13, R.drawable.home_six_14,
            R.drawable.home_six_15, R.drawable.home_six_16, R.drawable.home_six_17, R.drawable.home_six_18, R.drawable.home_six_19,
            R.drawable.home_six_20, R.drawable.home_six_21, R.drawable.home_six_22, R.drawable.home_six_23
    };

    // 2张图的随机图
    private static int[] homeTwo = {R.drawable.home_two_one, R.drawable.home_two_two, R.drawable.home_two_three, R.drawable.home_two_four,
            R.drawable.home_two_five, R.drawable.home_two_six, R.drawable.home_two_eleven, R.drawable.home_two_eight, R.drawable.home_two_nine};

    // 一张图的随机图
    private static int[] homeOne = {R.drawable.home_one_1, R.drawable.home_one_2, R.drawable.home_one_3,
            R.drawable.home_one_4, R.drawable.home_one_5, R.drawable.home_one_6, R.drawable.home_one_7,
            R.drawable.home_one_9, R.drawable.home_one_10, R.drawable.home_one_11, R.drawable.home_one_12
    };

    public static void displayCircle(ImageView imageView, int imageUrl) {
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .transform(new GlideCircleTransform(imageView.getContext()))
                .into(imageView);
    }
}

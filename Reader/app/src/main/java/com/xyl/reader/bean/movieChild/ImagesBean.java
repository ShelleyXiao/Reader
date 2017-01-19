package com.xyl.reader.bean.movieChild;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.io.Serializable;

/**
 * User: ShaudXiao
 * Date: 2017-01-17
 * Time: 11:52
 * Company: zx
 * Description:
 * FIXME
 */


public  class ImagesBean extends BaseObservable implements Serializable {
    /**
     * small : https://img1.doubanio.com/view/movie_poster_cover/ipst/public/p494268647.jpg
     * large : https://img1.doubanio.com/view/movie_poster_cover/lpst/public/p494268647.jpg
     * medium : https://img1.doubanio.com/view/movie_poster_cover/spst/public/p494268647.jpg
     */

    private String small;
    private String large;
    private String medium;

    public void setSmall(String small) {
        this.small = small;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    @Bindable
    public String getSmall() {
        return small;
    }

    @Bindable
    public String getLarge() {
        return large;
    }

    @Bindable
    public String getMedium() {
        return medium;
    }
}

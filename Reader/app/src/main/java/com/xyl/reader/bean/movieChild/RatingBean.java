package com.xyl.reader.bean.movieChild;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.io.Serializable;

/**
 * User: ShaudXiao
 * Date: 2017-01-17
 * Time: 11:54
 * Company: zx
 * Description:
 * FIXME
 */


public  class RatingBean extends BaseObservable implements Serializable{
    /**
     * max : 10
     * average : 6.9
     * stars : 35
     * min : 0
     */

    private int max;
    private double average;
    private String stars;
    private int min;

    public void setMax(int max) {
        this.max = max;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public void setMin(int min) {
        this.min = min;
    }

    @Bindable
    public int getMax() {
        return max;
    }

    @Bindable
    public double getAverage() {
        return average;
    }

    @Bindable
    public String getStars() {
        return stars;
    }

    @Bindable
    public int getMin() {
        return min;
    }
}

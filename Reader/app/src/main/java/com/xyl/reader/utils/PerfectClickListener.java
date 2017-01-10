package com.xyl.reader.utils;

import android.icu.util.Calendar;
import android.view.View;

import static android.R.attr.id;
import static android.R.attr.onClick;

/**
 * User: ShaudXiao
 * Date: 2017-01-10
 * Time: 16:04
 * Company: zx
 * Description:
 * FIXME
 */


public abstract class PerfectClickListener implements View.OnClickListener {

    public static final int MIN_CLICK_DELAY_TIME = 1000;

    private long lastClickTime = 0;
    private long id = -1;

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        long mId = v.getId();
        if(mId != id) {
            id = mId;
            lastClickTime = currentTime;
            onNoDoubleClick(v);
            return;
        }

        if(currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoDoubleClick(v);
        }
    }

    protected abstract void onNoDoubleClick(View v);
}

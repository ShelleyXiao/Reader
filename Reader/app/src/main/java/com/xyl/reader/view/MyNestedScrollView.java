package com.xyl.reader.view;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;

/**
 * User: ShaudXiao
 * Date: 2017-01-13
 * Time: 10:31
 * Company: zx
 * Description:
 * FIXME
 */


public class MyNestedScrollView extends NestedScrollView {

    private ScrollInterface mScrollInterface;

    public MyNestedScrollView(Context context) {
        super(context);
    }

    public MyNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollInterface(ScrollInterface scrollInterface) {
        mScrollInterface = scrollInterface;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        if(null != mScrollInterface) {
            mScrollInterface.onScrollChanage(l, t, oldl, oldt);
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }

    public interface ScrollInterface {
        void onScrollChanage(int scrollX, int scrollY, int oldScrollX, int oldScrollY);
    }


}

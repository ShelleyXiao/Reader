package com.xyl.architectrue.xrecylerview;

/**
 * User: ShaudXiao
 * Date: 2017-01-11
 * Time: 14:42
 * Company: zx
 * Description:
 * FIXME
 */


public interface BaseRefreshHeader {

    int STATE_NORMAL = 0;
    int STATE_RELEASE_TO_REFRESH = 1;
    int STATE_REFRESHING = 2;
    int STATE_DONE = 3;

    void onMore(float delta);

    boolean releaseAction();

    void refreshComplete();

    int getVisiableHeight();
}

package com.xyl.reader.app;

import com.xyl.architectrue.App;
import com.xyl.reader.http.HttpUtils;

/**
 * User: ShaudXiao
 * Date: 2017-01-12
 * Time: 14:22
 * Company: zx
 * Description:
 * FIXME
 */


public class ReaderApplication extends App {

    @Override
    public void onCreate() {
        super.onCreate();

        HttpUtils.getHttpUtils().setContext(getApplicationContext());
    }
}

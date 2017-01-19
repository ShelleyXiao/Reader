package com.xyl.reader.http.rx;

/**
 * User: ShaudXiao
 * Date: 2017-01-17
 * Time: 15:53
 * Company: zx
 * Description:
 * FIXME
 */


public class RxBusBaseMessage {
    private int code;
    private Object mObject;

    public RxBusBaseMessage(int code, Object object) {
        this.code = code;
        mObject = object;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getObject() {
        return mObject;
    }

    public void setObject(Object object) {
        mObject = object;
    }
}

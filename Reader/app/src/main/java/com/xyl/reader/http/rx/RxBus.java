package com.xyl.reader.http.rx;

import rx.Observable;
import rx.functions.Func1;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * User: ShaudXiao
 * Date: 2017-01-17
 * Time: 15:40
 * Company: zx
 * Description:
 * FIXME
 */


public class RxBus {

    private static volatile RxBus defaultInstance;

    private final Subject<Object, Object> bus = new SerializedSubject<>(PublishSubject.create());

    private RxBus() {

    }

    public static RxBus getBus() {
        if(null == defaultInstance) {
            synchronized (RxBus.class) {
                if(null == defaultInstance) {
                    defaultInstance = new RxBus();
                }
            }
        }

        return defaultInstance;
    }

    public void send(Object o) {
        bus.onNext(o);
    }

    public Observable<Object> toObservable() {
        return bus;
    }

    /**
     * 根据evnetType的类型返回特定类型的Obserable
     *@param eventType 事件类型
     *
     * */
    public <T> Observable<T> toObservable(Class<T> eventType) {
        return bus.ofType(eventType);
    }

    /**
     * 提供一个新事件，根据code分发
     *@param code 事件代码
     *
     * */
    public void post(int code, Object object) {
        bus.onNext(new RxBusBaseMessage(code, object));
    }

    /**
     * 根据传递的code和evnetType的类型返回特定类型的Obserable（被观察者）
     *
     * @param code 事件代码
     * @param eventType 事件类型
     *
     * */
    public <T> Observable<T> toObservable(final int code, final Class<T> eventType) {
        return bus.ofType(RxBusBaseMessage.class)
                .filter(new Func1<RxBusBaseMessage, Boolean>() {
                    @Override
                    public Boolean call(RxBusBaseMessage rxBusBaseMessage) {
                        return rxBusBaseMessage.getCode() == code && eventType.isInstance(rxBusBaseMessage.getObject());
                    }
                })
                .map(new Func1<RxBusBaseMessage, Object>() {
                    @Override
                    public Object call(RxBusBaseMessage rxBusBaseMessage) {
                        return rxBusBaseMessage.getObject();
                    }
                }).cast(eventType);
    }

    /*
    * 是否有observer
    *
    * **/
    public boolean haObserver() {
        return bus.hasObservers();
    }

}

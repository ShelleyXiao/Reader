package com.xyl.reader.model;

import com.xyl.architectrue.utils.LogUtils;
import com.xyl.reader.bean.AndroidBean;
import com.xyl.reader.bean.FrontpageBean;
import com.xyl.reader.bean.GankIODayBean;
import com.xyl.reader.http.HttpUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * User: ShaudXiao
 * Date: 2017-01-18
 * Time: 10:49
 * Company: zx
 * Description:
 * FIXME
 */


public class EvevryDayModel {

    private String year = "2017";
    private String month = "1";
    private String day = "18";

    private List<List<AndroidBean>> lists;

    public void setData(String year, String month, String day) {
        this.month = month;
        this.year = year;
        this.day = day;
    }

    public interface HomeImpl {
        void loadSuccess(Object object);

        void loadFaild();

        void addSubscription(Subscription subscription);
    }

    /**
     * 获取轮播图
     * */
    public void showBannerPage(final HomeImpl impl) {
        Subscription subscription = HttpUtils.getHttpUtils().getDongtingHttpServer().getFrontpage()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FrontpageBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        impl.loadFaild();
                    }

                    @Override
                    public void onNext(FrontpageBean frontpageBean) {
                        impl.loadSuccess(frontpageBean);
                    }
                });

        impl.addSubscription(subscription);
    }

    public void showRecylerViewData(final HomeImpl impl) {
        LogUtils.e("showRecylerViewData   ---------");
        Func1<GankIODayBean, Observable<List<List<AndroidBean>>>> func1 = new Func1<GankIODayBean, Observable<List<List<AndroidBean>>>>() {
            @Override
            public Observable<List<List<AndroidBean>>> call(GankIODayBean gankIODayBean) {
                LogUtils.e(" bean: " + gankIODayBean.toString());
                lists = new ArrayList<>();
                GankIODayBean.ResultsBean bean = gankIODayBean.getResults();
                if(bean.getAndroid() != null && bean.getAndroid().size() > 0) {
                    addList(bean.getAndroid(), "Android");
                }
                if(bean.getWelfare() != null && bean.getWelfare().size() > 0) {
                    addList(bean.getWelfare(), "福利");
                }

                if(bean.getRestMovie() != null && bean.getRestMovie().size() > 0) {
                    addList(bean.getRestMovie(), "休息视频");
                }

                if(bean.getResource() != null && bean.getResource().size() > 0) {
                    addList(bean.getResource(), "拓展资源");
                }

                if(bean.getRecommend() != null && bean.getRecommend().size() > 0) {
                    addList(bean.getRecommend(), "瞎推荐");
                }

                if(bean.getFront() != null && bean.getFront().size() > 0) {
                    addList(bean.getFront(), "前端");
                }

                if(bean.getApp() != null && bean.getApp().size() > 0) {
                    addList(bean.getApp(), "App");
                }



                return Observable.just(lists);
            }
        };
        Observer<List<List<AndroidBean>>> observer = new Observer<List<List<AndroidBean>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                impl.loadFaild();
            }

            @Override
            public void onNext(List<List<AndroidBean>> lists) {
                impl.loadSuccess(lists);
            }
        };

        Subscription subscription = HttpUtils.getHttpUtils().getGanIoHttpServer().getGankIoDayData(year, month, day)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(func1)
                .subscribe(observer);
        impl.addSubscription(subscription);

    }

    private void addList(List<AndroidBean> arrayList, String typeTitle) {
        AndroidBean bean = new AndroidBean();
        bean.setType_title(typeTitle);
        ArrayList<AndroidBean> androidBeen = new ArrayList<>();
        androidBeen.add(bean);

        lists.add(androidBeen);

        int androidSize = arrayList.size();

        if(androidSize > 0 && androidSize < 4) {
            lists.add(arrayList);
        } else if(androidSize >= 4){
            List<AndroidBean> list1 = new ArrayList<>();
            List<AndroidBean> list2 = new ArrayList<>();
            for(int i = 0; i < androidSize; i++) {
                if(i < 3) {
                    list1.add(arrayList.get(i));
                } else if(i < 6) {
                    list2.add(arrayList.get(i));
                }

            }
            lists.add(list1);
            lists.add(list2);
        }
    }
}

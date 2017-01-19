package com.xyl.reader.ui.gank.child;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import com.xyl.architectrue.utils.LogUtils;
import com.xyl.architectrue.utils.TimeUtils;
import com.xyl.architectrue.xrecylerview.XRecyclerView;
import com.xyl.reader.R;
import com.xyl.reader.adapter.EverydayAdapter;
import com.xyl.reader.base.BaseFragment;
import com.xyl.reader.bean.AndroidBean;
import com.xyl.reader.bean.FrontpageBean;
import com.xyl.reader.databinding.FooterItemEverydayBinding;
import com.xyl.reader.databinding.FragmentEverydayBinding;
import com.xyl.reader.databinding.HeaderEverydayBinding;
import com.xyl.reader.model.EvevryDayModel;
import com.xyl.reader.utils.GlideImageLoader;
import com.xyl.reader.utils.PerfectClickListener;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

/**
 * User: ShaudXiao
 * Date: 2017-01-12
 * Time: 15:31
 * Company: zx
 * Description:
 * FIXME
 */


public class EeveryDayFragment extends BaseFragment<FragmentEverydayBinding> {


    private HeaderEverydayBinding mHeaderViewDataBinding;
    private FooterItemEverydayBinding mFooterViewDataBinding;
    private XRecyclerView mRecyclerView;

    private View mHeaderView;
    private View mFootView;

    private Animation mAnimation;

    private EvevryDayModel mEvevryDayModel;
    private ArrayList<String> mBannerPicList;
    private ArrayList<List<AndroidBean>> mDataLists;

    private EverydayAdapter mEverydayAdapter;

    private boolean isLoading = false;

    private boolean isPrepare = false;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtils.e("onActivityCreated");
        showContentView();
        bindingContentView.llProgressBar.setVisibility(View.VISIBLE);
        mAnimation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mAnimation.setDuration(3000);
        mAnimation.setInterpolator(new LinearInterpolator());
        mAnimation.setRepeatCount(10);
        bindingContentView.imgProcess.setAnimation(mAnimation);
        mAnimation.start();

        mEvevryDayModel = new EvevryDayModel();

        initRecylerView();
        initLocalSetting();

        isPrepare = true;

        loadData();
    }

    @Override
    protected int setContent() {
        return R.layout.fragment_everyday;
    }

    @Override
    protected void onRefresh() {
        showContentView();
        showRotaLoading(true);

        showContentData();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initRecylerView() {
        mRecyclerView = bindingContentView.llRecylerview;
        mRecyclerView.setPullRefreshEnabled(false);
        mRecyclerView.setLoadingMoreEnabled(false);

        mHeaderViewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.header_everyday, null, false);
        if(mHeaderView == null) {
            mHeaderView = mHeaderViewDataBinding.getRoot();
            mRecyclerView.addHeaderView(mHeaderView);
        }
        mFooterViewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.footer_item_everyday, null, false);
        if(mFootView == null) {
            mFootView = mFooterViewDataBinding.getRoot();
            mRecyclerView.addFootView(mFootView, true);
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


    }
    private void initLocalSetting() {
        mEvevryDayModel.setData(getDate().get(0), getDate().get(1), getDate().get(2));
        mHeaderViewDataBinding.homeMiddleLayout.ivSourceDailyRecommand.setText(
            getDate().get(2).indexOf("0") == 0 ? getDate().get(2).replace("0", "") : getDate().get(2)
        );

        mHeaderViewDataBinding.homeMiddleLayout.sourceReader.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {

            }
        });

        mHeaderViewDataBinding.homeMiddleLayout.sourceHotMovie.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {

            }
        });

    }

    private ArrayList<String> getDate() {
        String dateStr = TimeUtils.getNowDateString();
        String[] dateArr = dateStr.split("-");
        ArrayList<String> dateList = new ArrayList<>();

        dateList.add(dateArr[0]);
        dateList.add(dateArr[1]);
        dateList.add(dateArr[2]);

        return dateList;
    }

    private void showRotaLoading(boolean loading) {
        if(isLoading) {
            bindingContentView.llProgressBar.setVisibility(View.VISIBLE);
            bindingContentView.llRecylerview.setVisibility(View.GONE);
            mAnimation.startNow();
        } else {
            bindingContentView.llProgressBar.setVisibility(View.GONE);
            bindingContentView.llRecylerview.setVisibility(View.VISIBLE);
            mAnimation.cancel();
        }
    }

    private void loadBannerPic() {
        mEvevryDayModel.showBannerPage(new EvevryDayModel.HomeImpl() {
            @Override
            public void loadSuccess(Object object) {
                if(mBannerPicList == null) {
                    mBannerPicList = new ArrayList<String>();
                } else {
                    mBannerPicList.clear();
                }

                FrontpageBean bean = (FrontpageBean)object;
                List<FrontpageBean.DataBeanX.DataBean> data = null;
                if(bean != null
                        && bean.getData() != null
                        && bean.getData().get(0) != null
                        && bean.getData().get(0).getData() != null) {
                    data = bean.getData().get(0).getData();
                }

                if(data != null && data.size() > 0) {
                    for(int i = 0; i < data.size(); i++) {
                        FrontpageBean.DataBeanX.DataBean dataBean = data.get(i);
                        mBannerPicList.add(dataBean.getPicUrl());
                    }
                }
                mHeaderViewDataBinding.banner.setImages(mBannerPicList).setImageLoader(new GlideImageLoader()).start();


            }

            @Override
            public void loadFaild() {

            }

            @Override
            public void addSubscription(Subscription subscription) {
                EeveryDayFragment.this.addSubscription(subscription);
            }
        });
    }

    @Override
    protected void loadData() {
        if(mHeaderViewDataBinding != null && mHeaderViewDataBinding.banner != null) {
            mHeaderViewDataBinding.banner.startAutoPlay();
            mHeaderViewDataBinding.banner.setDelayTime(5000);
        }

        if(!mIsVisiable || !isPrepare) {
            return;
        }

        showRotaLoading(true);
        loadBannerPic();
        showContentData();
    }

    private void showContentData() {
        LogUtils.e("showContentData");
        mEvevryDayModel.showRecylerViewData(new EvevryDayModel.HomeImpl() {
            @Override
            public void loadSuccess(Object object) {
                LogUtils.e("showcontentdata ---------- loadSuccess");
                if(mDataLists != null) {
                    mDataLists.clear();
                }
                mDataLists = (ArrayList<List<AndroidBean>>)object;
                if(mDataLists.size() > 0 && mDataLists.get(0).size() > 0) {
                    LogUtils.e("ssss " + mDataLists.get(0).size());
                    setAdapter(mDataLists);
                } else {

                }

            }

            @Override
            public void loadFaild() {
                LogUtils.e("showcontentdata ---------- loadFaild");
            }

            @Override
            public void addSubscription(Subscription subscription) {
                LogUtils.e("showcontentdata ---------- addSubscription");
                EeveryDayFragment.this.addSubscription(subscription);
            }
        });
    }

    private void setAdapter(ArrayList<List<AndroidBean>> datas) {
        showRotaLoading(false);
        if(mEverydayAdapter == null) {
            mEverydayAdapter = new EverydayAdapter();
        } else {
            mEverydayAdapter.clear();
        }

        mEverydayAdapter.addAll(datas);
        mRecyclerView.setAdapter(mEverydayAdapter);

        mEverydayAdapter.notifyDataSetChanged();


    }


}

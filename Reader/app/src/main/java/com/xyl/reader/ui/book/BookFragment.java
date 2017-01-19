package com.xyl.reader.ui.book;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.xyl.reader.R;
import com.xyl.reader.adapter.MyFragmentPaperAdapter;
import com.xyl.reader.base.BaseFragment;
import com.xyl.reader.databinding.FragmentBookLayoutBinding;
import com.xyl.reader.ui.gank.child.CustomFragment;
import com.xyl.reader.ui.gank.child.EeveryDayFragment;
import com.xyl.reader.ui.gank.child.WelfareFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * User: ShaudXiao
 * Date: 2017-01-12
 * Time: 15:31
 * Company: zx
 * Description:
 * FIXME
 */


public class BookFragment extends BaseFragment<FragmentBookLayoutBinding> {

    private TabLayout mTabLayout;
    private MyFragmentPaperAdapter mPaperAdapter;
    private ViewPager mViewPager;

    private List<Fragment> mFragments = new ArrayList<>();
    private List<String> mTitles = new ArrayList<>();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showLoading();

        mTabLayout = bindingContentView.tabBook;
        mViewPager = bindingContentView.vpBook;

        initFragments();
        initTitle();

        mPaperAdapter = new MyFragmentPaperAdapter(getChildFragmentManager(), mFragments, mTitles);
        mViewPager.setAdapter(mPaperAdapter);
        // 注意内存别溢出
        mViewPager.setOffscreenPageLimit(3);

        mPaperAdapter.notifyDataSetChanged();

        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setupWithViewPager(mViewPager);


        showContentView();


    }

    @Override
    protected int setContent() {
        return R.layout.fragment_book_layout;
    }

    private void initFragments() {
        mFragments.add(new EeveryDayFragment());
        mFragments.add(new WelfareFragment());
        mFragments.add(new CustomFragment());
    }

    private void initTitle() {
        mTitles.add("综合");
        mTitles.add("文学");
        mTitles.add("生活");

    }
}

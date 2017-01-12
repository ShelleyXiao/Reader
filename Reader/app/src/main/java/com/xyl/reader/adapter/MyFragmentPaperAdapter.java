package com.xyl.reader.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * User: ShaudXiao
 * Date: 2017-01-12
 * Time: 15:39
 * Company: zx
 * Description:
 * FIXME
 */


public class MyFragmentPaperAdapter extends FragmentPagerAdapter {

    private List<?> mFragment;
    private List<String> mFragmentTitle;


    /**
     * 主页用
     * */
    public MyFragmentPaperAdapter(FragmentManager fm, List<?> fragment) {
        super(fm);
        mFragment = fragment;
    }

    public MyFragmentPaperAdapter(FragmentManager fm, List<?> fragment, List<String> frgmentTitle) {
        super(fm);
        mFragment = fragment;
        mFragmentTitle = frgmentTitle;
    }

    @Override
    public Fragment getItem(int position) {
        return (Fragment) mFragment.get(position);
    }

    @Override
    public int getCount() {
        return mFragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(mFragmentTitle != null) {
            return mFragmentTitle.get(position);
        } else {
            return "";
        }
    }

    public void addFragment(List<?> fragments) {
        this.mFragment.clear();
        this.mFragment = null;
        this.mFragment = fragments;
        notifyDataSetChanged();
    }
}

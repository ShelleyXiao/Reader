package com.xyl.reader;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jaeger.library.StatusBarUtil;
import com.xyl.architectrue.utils.CommonUtils;
import com.xyl.reader.adapter.MyFragmentPaperAdapter;
import com.xyl.reader.databinding.ActivityMainBinding;
import com.xyl.reader.ui.book.BookFragment;
import com.xyl.reader.ui.gank.GankFragment;
import com.xyl.reader.ui.one.OneFragment;
import com.xyl.reader.utils.ImageLoadUtil;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private FrameLayout lTitleMenu;
    private Toolbar mToolbar;
    private FloatingActionButton fab;
    private NavigationView navView;
    private DrawerLayout mDrawerLayout;
    private ViewPager mViewPager;

    private ImageView mTitleGank;
    private ImageView mTitleOne;
    private ImageView mTitleDouban;

    private ActivityMainBinding mActivityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initId();
        StatusBarUtil.setColorNoTranslucentForDrawerLayout(this, mDrawerLayout, CommonUtils.getColor(R.color.colorTheme));

        initDrawlayout();
        initContentFragment();
    }

    private void initId() {
        lTitleMenu = mActivityMainBinding.include.llTitleMenu;
        mToolbar = mActivityMainBinding.include.tbTitlte;
        fab = mActivityMainBinding.include.fab;
        navView = mActivityMainBinding.navView;
        mDrawerLayout = mActivityMainBinding.drawerLayout;
        mViewPager = mActivityMainBinding.include.vpContent;

        mTitleGank = mActivityMainBinding.include.ivTilteGank;
        mTitleOne =mActivityMainBinding.include.ivTilteOne;
        mTitleDouban = mActivityMainBinding.include.ivTilteDou;

        lTitleMenu.setOnClickListener(this);

        mTitleGank.setOnClickListener(this);
        mTitleOne.setOnClickListener(this);
        mTitleDouban.setOnClickListener(this);
    }

    private void initDrawlayout() {
        navView.inflateHeaderView(R.layout.nav_header_main);

        View headView = navView.getHeaderView(0);
        ImageView avatrView = (ImageView) headView.findViewById(R.id.iv_avatar);
        ImageLoadUtil.displayCircle(avatrView, R.drawable.ic_avatar);

        LinearLayout llNavHomePage = (LinearLayout) headView.findViewById(R.id.ll_nav_homepage);
        LinearLayout llNavScanDownload = (LinearLayout) headView.findViewById(R.id.ll_nav_scan_download);
        LinearLayout llNavDeedBack = (LinearLayout) headView.findViewById(R.id.ll_nav_deedback);
        LinearLayout llNavAbout = (LinearLayout) headView.findViewById(R.id.ll_nav_about);

        llNavHomePage.setOnClickListener(this);
        llNavScanDownload.setOnClickListener(this);
        llNavDeedBack.setOnClickListener(this);
        llNavAbout.setOnClickListener(this);
    }

    private void initContentFragment() {
        ArrayList<Fragment>  fragments = new ArrayList<>();
        fragments.add(new GankFragment());
        fragments.add(new OneFragment());
        fragments.add(new BookFragment());

        MyFragmentPaperAdapter adapter = new MyFragmentPaperAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.addOnPageChangeListener(this);
        mActivityMainBinding.include.ivTilteGank.setSelected(true);
        mViewPager.setCurrentItem(0);

        setSupportActionBar(mToolbar);
        ActionBar bar = getSupportActionBar();
        if(bar != null) {
            bar.setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_title_menu:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.iv_tilte_gank:
                if(mViewPager.getCurrentItem() != 0) {
                    mTitleGank.setSelected(true);
                    mTitleOne.setSelected(false);
                    mTitleDouban.setSelected(false);
                    mViewPager.setCurrentItem(0);
                }
                break;

            case R.id.iv_tilte_one:
                if(mViewPager.getCurrentItem() != 1) {
                    mTitleGank.setSelected(false);
                    mTitleOne.setSelected(true);
                    mTitleDouban.setSelected(false);
                    mViewPager.setCurrentItem(1);
                }
                break;

            case R.id.iv_tilte_dou:
                if(mViewPager.getCurrentItem() != 2) {
                    mTitleGank.setSelected(false);
                    mTitleOne.setSelected(false);
                    mTitleDouban.setSelected(true);
                    mViewPager.setCurrentItem(2);
                }
                break;

         }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                mTitleGank.setSelected(true);
                mTitleOne.setSelected(false);
                mTitleDouban.setSelected(false);
                break;
            case 1:
                mTitleGank.setSelected(false);
                mTitleOne.setSelected(true);
                mTitleDouban.setSelected(false);
                break;
            case 2:
                mTitleGank.setSelected(false);
                mTitleOne.setSelected(false);
                mTitleDouban.setSelected(true);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}

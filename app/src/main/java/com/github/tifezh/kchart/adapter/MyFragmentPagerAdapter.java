package com.github.tifezh.kchart.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.List;

/**
 * Created by lvzhihao on 17-4-27.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private String[] title;
    private List<Fragment> views;

    public MyFragmentPagerAdapter(FragmentManager fm, String[] title, List<Fragment> views) {
        super(fm);
        this.title = title;
        this.views = views;
    }

    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> views) {
        super(fm);
        this.views = views;
    }

    @Override
    public Fragment getItem(int position) {
        return views.get(position);
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    //配置标题的方法
    @Override
    public CharSequence getPageTitle(int position) {
        return title == null ? "" : title[position];
    }
}

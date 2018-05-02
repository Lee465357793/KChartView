package com.github.tifezh.kchart;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.github.tifezh.kchart.adapter.MyFragmentPagerAdapter;
import com.github.tifezh.kchart.chart.KChartAdapter;
import com.github.tifezh.kchartlib.chart.KChartView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChartActivity extends AppCompatActivity {

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    NoScrollViewPager mViewpager;

    private KChartAdapter mAdapter;
    private KChartView mKChartView;
    private String[] mTitles;
    private List<Fragment> mFragments = new ArrayList<Fragment>();;
    private MyFragmentPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        ButterKnife.bind(this);
        mTitles = new String[]{"分时", "15分", "30分", "一小时", "天时"};
        for (int i = 0; i < mTitles.length; ++i) {
            ChartFragment chartFragment = new ChartFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("index", i);
            chartFragment.setArguments(bundle);
            mFragments.add(chartFragment);
        }
        mPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mTitles, mFragments);
        mViewpager.setAdapter(mPagerAdapter);
//        mViewpager.requestDisallowInterceptTouchEvent(true);


        mTabLayout.setupWithViewPager(mViewpager);

    }


}

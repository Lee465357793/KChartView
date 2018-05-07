package com.github.tifezh.kchart;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.tifezh.kchart.chart.KChartAdapter;
import com.github.tifezh.kchart.chart.KLineEntity;
import com.github.tifezh.kchartlib.chart.BaseKChartView;
import com.github.tifezh.kchartlib.chart.KChartView;
import com.github.tifezh.kchartlib.chart.formatter.DateFormatter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * ProjectName：（项目名称）
 * DESC: (类描述)
 * Created by 李岩 on 2018/4/28 0028
 * updateName:(修改人名称)
 * updateTime:(修改时间)
 * updateDesc:(修改内容)
 */
public class ChartFragment extends Fragment {
    @BindView(R.id.chart_view)
    KChartView mChartView;
    Unbinder unbinder;
    private KChartAdapter mAdapter;
    private int mIndex;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_chart, null);
        unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        initData();
    }

    private void initView() {
        mIndex = getArguments().getInt("index");
        if (mIndex == 0){
            mChartView.setDrawMinuteStyle(true);
            mChartView.setDrawTabView(false);
            mChartView.setDrawGirdLine(false);
//            mChartView.setGridColumns();
            mChartView.setLeftTitleOutward(true);
        }
        mAdapter = new KChartAdapter();
        mChartView.setAdapter(mAdapter);
        mChartView.setDateTimeFormatter(new DateFormatter());
        mChartView.setGridRows(4);
        mChartView.setGridColumns(4);
        mChartView.setOnSelectedChangedListener(new BaseKChartView.OnSelectedChangedListener() {
            @Override
            public void onSelectedChanged(BaseKChartView view, Object point, int index) {
                KLineEntity data = (KLineEntity) point;
                Log.i("onSelectedChanged", "index:" + index + " closePrice:" + data.getClosePrice());
            }
        });
    }

    private void initData() {
        mChartView.showLoading();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<KLineEntity> data = DataRequest.getALL(getActivity());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.addFooterData(data);
                        mChartView.startAnimation();
                        mChartView.refreshEnd();
                    }
                });
            }
        }).start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

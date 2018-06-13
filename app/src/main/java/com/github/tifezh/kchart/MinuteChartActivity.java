package com.github.tifezh.kchart;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.github.tifezh.kchart.chart.MinuteLineEntity;
import com.github.tifezh.kchartlib.chart.MinuteChartView;
import com.github.tifezh.kchartlib.chart.entity.IMinuteLine;
import com.github.tifezh.kchartlib.utils.DateUtil;

import java.text.ParseException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tifezh on 2017/7/20.
 */

public class MinuteChartActivity extends AppCompatActivity {


    @BindView(R.id.minuteChartView)
    MinuteChartView mMinuteChartView;
    @BindView(R.id.btn_add)
    Button mBtnAdd;
    private List<MinuteLineEntity> mMinuteData;
    private LinkedHashMap<String, String> mStringStringHashMap = new LinkedHashMap<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minute_chart);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        ButterKnife.bind(this);

        initView();
        initData();

        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemSize = mMinuteChartView.getItemSize();
                IMinuteLine item = mMinuteData.get(itemSize < mMinuteData.size() ? itemSize + 1 : itemSize);
                mMinuteChartView.addPoint(item);
            }
        });
    }

    private void initView() {
    }

    private void initData() {
        try {
            //整体开始时间
            Date startTime = DateUtil.shortTimeFormat.parse("09:30");
            //整体的结束时间
            Date endTime = DateUtil.shortTimeFormat.parse("16:00");
            //休息开始时间
            Date firstEndTime = DateUtil.shortTimeFormat.parse("12:00");
            //休息结束时间
            Date secondStartTime = DateUtil.shortTimeFormat.parse("13:00");
            //获取随机生成的数据
            mMinuteData = DataRequest.getMinuteData(startTime,
                    endTime,
                    firstEndTime,
                    secondStartTime);

            List<MinuteLineEntity> minuteLineEntities = mMinuteData.subList(0, mMinuteData.size() - 50);

            mStringStringHashMap.put("06:30", "23:59");
//          mStringStringHashMap.put("10:00", "10:30");
            mStringStringHashMap.put("12:00", "13:00");
//          mStringStringHashMap.put("15:30", "16:00");
            mStringStringHashMap.put("19:30", "20:00");

            mMinuteChartView.initData(minuteLineEntities, mStringStringHashMap,
                    (float) (mMinuteData.get(0).price - 0.5 + Math.random()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}

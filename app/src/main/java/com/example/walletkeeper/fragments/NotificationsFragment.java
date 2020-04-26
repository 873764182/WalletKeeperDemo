package com.example.walletkeeper.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.walletkeeper.R;
import com.example.walletkeeper.packages.Database;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.*;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.*;

public class NotificationsFragment extends Fragment {

    int all = 0;
    int Syear = 0;
    int Smonth = 0;

    String[] Years;
    String[] Months;

    Spinner ySpinner;
    Spinner mSpinner;
    ArrayList<String> types = new ArrayList<String>(
            Arrays.asList(
                    "food", "shopping", "vehicle",
                    "entertainment", "commodity", "social",
                    "study", "medical", "others"
            )
    );
    //数据库创建
    private Database database;
    private SQLiteDatabase db;
    private Button button1;

    //    -----------------------设置fragment视图--------------------------------

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        return root;
    }
//    ------------------------结束--------------------------

    //    ==================开始activity========================
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

        database = new Database(getActivity(), "account", null, 4);
        db = database.getReadableDatabase();
        all = 0;


//        ---------------设置年月spinner-----------
        Years = getResources().getStringArray(R.array.years_array);
        Months = getResources().getStringArray(R.array.months_array);
        ySpinner = (Spinner) getActivity().findViewById(R.id.spinnerYear2);
        mSpinner = (Spinner) getActivity().findViewById(R.id.spinnerMonth2);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, Years);
        ySpinner.setAdapter(adapter1);
        ySpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0,
                                               View arg1, int arg2, long arg3) {
                        int index = arg0.getSelectedItemPosition();
                        if (index != 0) {
                            Syear = 2021 - index;
                        } else {
                            Syear = 0;
                        }
                        if (Smonth != 0 || Syear != 0) {
                            all = 1;
                        } else {
                            all = 0;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        Syear = 0;
                    }
                });

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, Months);
        mSpinner.setAdapter(adapter2);
        mSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0,
                                               View arg1, int arg2, long arg3) {
                        int index = arg0.getSelectedItemPosition();
                        Smonth = index;
                        if (Smonth != 0 || Syear != 0) {
                            all = 1;
                        } else {
                            all = 0;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        Smonth = 0;
                    }
                });

        //        ----------------设置年月spinner结束------------


//---------------------根据年月查询数据------------
        button1 = getActivity().findViewById(R.id.button2);

        show_data();

        //从数据库查询获取所有数据
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == button1) {
                    show_data();
                }
            }
        });

    }


    public void show_data() {
        Map<String, Integer> typeMoney = new HashMap<>();
        Map<Integer, Integer> monthMoney = new HashMap<>();
        Map<Integer, Integer> dayMoney = new HashMap<>();
        Map<Integer, Integer> yearMoney = new HashMap<>();

        int ok = 0;
        String timeOrder = "year desc, month desc, day desc, hour desc, minute desc, second desc";

        String selection = null;

        if (all != 0) {
            if (Syear != 0 && Smonth != 0) {
                selection = "year='" + Syear + "' and month='" + Smonth + "'";
            } else if (Syear != 0) {
                selection = "year='" + Syear + "'";
            } else if (Smonth != 0) {
                selection = "month='" + Smonth + "'";
            }
        }
        Cursor cursor = db.query("account", null, selection, null, null, null, timeOrder);


        boolean succeed = cursor.moveToFirst();  //从数据库第一行开始查
        while (succeed) {
            ok = 1;
            int year = cursor.getInt(cursor.getColumnIndex("year"));
            int month = cursor.getInt(cursor.getColumnIndex("month"));
            int day = cursor.getInt(cursor.getColumnIndex("day"));
            int money = cursor.getInt(cursor.getColumnIndex("money"));
            int typeNum = cursor.getInt(cursor.getColumnIndex("type"));

//            获取饼图数据
            String typeName = types.get(typeNum - 1);
            if (typeMoney.containsKey(typeName)) {
                int sum = typeMoney.get(typeName);
                typeMoney.put(typeName, sum + money);
            } else {
                typeMoney.put(typeName, money);
            }

//            获取柱状图数据
            if (Smonth != 0) {
                if (dayMoney.containsKey(day)) {
                    int sums = dayMoney.get(day);
                    dayMoney.put(day, sums + money);
                } else {
                    dayMoney.put(day, money);
                }
            } else if (Syear != 0) {
                if (monthMoney.containsKey(month)) {
                    int sums = monthMoney.get(month);
                    monthMoney.put(month, sums + money);
                } else {
                    monthMoney.put(month, money);
                }
            } else {
                if (yearMoney.containsKey(year)) {
                    int sums = yearMoney.get(year);
                    yearMoney.put(year, sums + money);
                } else {
                    yearMoney.put(year, money);
                }
            }


            //查完一条之后调用cursor.moveToNext()把cursor的位置移动到下一条
            if (cursor.isLast()) {
                break;
            }
            cursor.moveToNext();
        }
        cursor.close();

//            ---画饼图---
        pieChart(typeMoney);
        //       -----------画柱图---------
        if (Smonth != 0) {
            barChart(dayMoney);
        } else if (Syear != 0) {
            barChart(monthMoney);
        } else {
            barChart(yearMoney);
        }

        if (ok == 0) {
            Toast.makeText(getActivity(), "no recoird!", Toast.LENGTH_LONG).show();
        }
    }

    public void pieChart(Map<String, Integer> typeMoney) {
        //        -----------画饼图---------
        PieChart pieChart = getActivity().findViewById(R.id.piechart);

        List<PieEntry> entries = new ArrayList<>();

//        遍历map
        for (Map.Entry<String, Integer> entry : typeMoney.entrySet()) {
            entries.add(new PieEntry(entry.getValue(), entry.getKey()));
        }

        PieDataSet pieDataSet = new PieDataSet(entries, "");
        PieData data = new PieData(pieDataSet);
        pieChart.setData(data);
        pieChart.invalidate(); // refresh

        pieDataSet.setValueTextSize(16f);
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieChart.animateXY(2000, 2000);

        Description description = pieChart.getDescription();
// enable or disable the description
        description.setEnabled(false);
// set the description text
//        description.setText("usage");
// set the position of the description on the screen
//        description.setPosition(float x, float y);

        Legend l = pieChart.getLegend();
        l.setFormSize(10f); // set the size of the legend forms/shapes
        l.setForm(Legend.LegendForm.CIRCLE); // set what type of form/shape should be used
//        l.set(LegendPosition.BELOW_CHART_LEFT);
//        l.setTypeface(...);
        l.setTextSize(16f);
        l.setTextColor(Color.BLACK);
        l.setXEntrySpace(5f); // space between the legend entries on the x-axis
        l.setYEntrySpace(5f); // space between the legend entries on the y-axis
    }

    public void barChart(Map<Integer, Integer> map) {
        BarChart barChart = getActivity().findViewById(R.id.barchart);
        initBarChart(barChart);

        List<BarEntry> barEntries = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            barEntries.add(new BarEntry(entry.getKey(), entry.getValue()));
        }
//            final String[] quarters = new String[] { "Q1", "Q2", "Q3", "Q4" };
//            ValueFormatter formatter = new ValueFormatter() {
//                @Override
//                public String getAxisLabel(float value, AxisBase axis) {
//                    return quarters[(int) value];
//                }
//            };
//            XAxis xAxis = barChart.getXAxis();
//            xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
//            xAxis.setValueFormatter(formatter);

        BarDataSet barDataSet = new BarDataSet(barEntries, "money");
        initBarDataSet(barDataSet);

        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.6f); // set custom bar width
        barChart.setData(barData);
//        barChart.setFitBars(true); // make the x-axis fit exactly all bars
        barChart.invalidate(); // refresh
    }

    private void initBarChart(BarChart barChart) {
        /***图表设置***/
//        不显示图表边框
        barChart.setDrawBorders(false);
        //不显示右下角描述内容
        Description description = new Description();
        description.setEnabled(false);
        barChart.setDescription(description);

        //背景颜色
        barChart.setBackgroundColor(Color.WHITE);
        //不显示图表网格
        barChart.setDrawGridBackground(false);
        //背景阴影
        barChart.setDrawBarShadow(false);
        barChart.setHighlightFullBarEnabled(false);
        //显示边框
        barChart.setDrawBorders(true);
        //设置动画效果
        barChart.animateY(1000, Easing.Linear);
        barChart.animateX(1000, Easing.Linear);

        /***XY轴的设置***/
        //X轴设置显示位置在底部
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);


        YAxis leftAxis = barChart.getAxisLeft();
        YAxis rightAxis = barChart.getAxisRight();

        //保证Y轴从0开始，不然会上移一点
        leftAxis.setAxisMinimum(0f);
        rightAxis.setAxisMinimum(0f);

//        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1

//        不显示X轴 Y轴线条
        xAxis.setDrawAxisLine(false);
        leftAxis.setDrawAxisLine(false);
        rightAxis.setDrawAxisLine(false);

//        不显示左侧Y轴
        leftAxis.setEnabled(false);

//不显示X轴网格线
        xAxis.setDrawGridLines(false);
//右侧Y轴网格线设置为虚线
        rightAxis.enableGridDashedLine(10f, 10f, 0f);


        //折线图例 标签 设置
        Legend legend = barChart.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(12);
        //显示位置
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //是否绘制在图表里面
        legend.setDrawInside(false);
    }

    private void initBarDataSet(BarDataSet barDataSet) {
//        barDataSet.setColor(color);
        barDataSet.setFormLineWidth(1f);
        barDataSet.setFormSize(15.f);
        //不显示柱状图顶部值
        barDataSet.setDrawValues(true);
//        barDataSet.setValueTextSize(10f);
    }


}

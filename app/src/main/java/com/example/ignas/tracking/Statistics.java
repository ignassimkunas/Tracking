package com.example.ignas.tracking;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Statistics extends Fragment implements OnChartValueSelectedListener {

    private int [] colors;
    private int position = 0;
    private PieChart pieChart;
    private TextView monthName;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.data_menu, menu);

        for (int x = 0; x < 3; x++) {

            menu.getItem(x).setTitle(ToPay.monthsList.get(x).name);

        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        super.onOptionsItemSelected(item);

        switch (item.getItemId()){

            case R.id.action0:
                position = 0;
                updateChart();
                Toast.makeText(getActivity(), ToPay.monthsList.get(position).name, Toast.LENGTH_SHORT).show();
                break;
            case R.id.action1:
                position = 1;
                updateChart();
                Toast.makeText(getActivity(), ToPay.monthsList.get(position).name, Toast.LENGTH_SHORT).show();
                break;
            case R.id.action2:
                position = 2;
                updateChart();
                Toast.makeText(getActivity(), ToPay.monthsList.get(position).name, Toast.LENGTH_SHORT).show();
                break;
            case R.id.general:
                updateChartGeneral();
                break;
        }

        return true;
    }

    public void updateChartGeneral() {

        monthName.setText("General");

        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.TRANSPARENT);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setTransparentCircleRadius(61f);

        ArrayList<PieEntry> yValues = new ArrayList<>();

        for (int i = 0; i < ToPay.monthsList.size(); i++){

            yValues.add(new PieEntry((float)ToPay.monthsList.get(i).getSum(), ToPay.monthsList.get(i).name));

        }

        PieDataSet dataSet = new PieDataSet(yValues, "Months");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.createColors(colors));

        PieData data = new PieData(dataSet);

        data.setValueTextSize(10f);
        data.setValueTextColor(Color.BLUE);

        pieChart.setData(data);
        pieChart.setTouchEnabled(true);
        pieChart.invalidate();

        pieChart.animateY(1200, Easing.EasingOption.EaseOutBack);

    }

    public void updateChart() {

        monthName.setText(ToPay.monthsList.get(position).name);

        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.TRANSPARENT);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setTransparentCircleRadius(61f);

        ArrayList<PieEntry> yValues = new ArrayList<>();

        Months months = ToPay.monthsList.get(position);

        yValues.add(new PieEntry((float)months.dujos, "Dujos"));
        yValues.add(new PieEntry((float)months.sildymas, "Sildymas"));
        yValues.add(new PieEntry((float)months.vanduo, "Vanduo"));
        yValues.add(new PieEntry((float)months.elektra, "Elektra"));
        yValues.add(new PieEntry((float)months.bustas, "Bustas"));

        PieDataSet dataSet = new PieDataSet(yValues, "Taxes");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.createColors(colors));

        PieData data = new PieData(dataSet);

        data.setValueTextSize(10f);
        data.setValueTextColor(Color.BLUE);

        pieChart.setData(data);
        pieChart.setTouchEnabled(true);
        pieChart.invalidate();

        pieChart.animateY(1200, Easing.EasingOption.EaseOutBack);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_statistics, container, false);

        setHasOptionsMenu(true);

        colors = new int[]{

                Color.parseColor("#34bfdb"),
                Color.parseColor("#345bdb"),
                Color.parseColor("#1abc9c")

        };

        monthName = v.findViewById(R.id.monthName);
        pieChart = v.findViewById(R.id.piechart);

        updateChart();

        return v;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
    }

    @Override
    public void onNothingSelected() {
    }
}

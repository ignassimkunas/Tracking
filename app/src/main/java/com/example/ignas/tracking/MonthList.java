package com.example.ignas.tracking;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MonthList extends ArrayAdapter<Months> {

    private Activity context;
    private List<Months> monthsList;

    public MonthList(Activity context, List<Months> monthsList) {

        super(context, R.layout.list_layout, monthsList);
        this.context = context;
        this.monthsList = monthsList;

    }

    @Override
    public int getCount() {

        super.getCount();
        return 3;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        TextView textViewName = listViewItem.findViewById(R.id.textViewName);
        TextView textViewBool = listViewItem.findViewById(R.id.textViewBool);
        TextView monthNumber = listViewItem.findViewById(R.id.monthNumber);
        TextView tax = listViewItem.findViewById(R.id.tax);

        String[] monthNames;

        monthNames = new String[]{

                "January",
                "February",
                "March",
                "April",
                "May",
                "June",
                "July",
                "August",
                "September",
                "October",
                "November",
                "December"
        };

        Months months = monthsList.get(position);

        for (int x = 0; x < 11; x++) {

            if (months.name.equals(monthNames[x])) {

                monthNumber.setText(Integer.toString(x + 1));

            }

        }

        boolean ifPaid = months.ifPaid;
        String ifPaidString = "";

        if (ifPaid) {

            ifPaidString = "Paid";
            tax.setTextColor(Color.parseColor("#FF00D921"));

        } else {

            ifPaidString = "Not paid";
            tax.setTextColor(Color.parseColor("#FFE1000F"));
        }

        textViewName.setText(months.getName());
        textViewBool.setText(ifPaidString);

        if (months.getSum() == 0) {

            tax.setText("0€");

        } else {

            tax.setText(Double.toString(months.getSum()) + "€");

        }

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in);
        listViewItem.startAnimation(animation);

        return listViewItem;
    }
}

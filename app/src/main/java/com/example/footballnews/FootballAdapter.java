package com.example.footballnews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class FootballAdapter extends ArrayAdapter<Report> {

    public FootballAdapter(@NonNull Context context, List<Report> report) {
        super(context, 0, report);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        Report currentReport = getItem(position);

        //get title from Current Report-and passing value
        TextView tv_title = listItemView.findViewById(R.id.title);
        tv_title.setText(currentReport.getmTitle());

        //get section from Current Report-and passing value
        TextView tv_sectionName = listItemView.findViewById(R.id.sectionName);
        tv_sectionName.setText(currentReport.getmSection());

        //get writer name from Current Report-and passing value
        TextView tv_writer = listItemView.findViewById(R.id.writer);
        tv_writer.setText(currentReport.getmWriter());

        //get date from Current Report -and passing value
        TextView tv_date = listItemView.findViewById(R.id.date);
        tv_date.setText(currentReport.getmdate());

        return listItemView;

    }
}

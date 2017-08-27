package com.example.amirl2.myapplication.Accessories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.amirl2.myapplication.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by AmirL2 on 8/26/2017.
 */

public class ListAdapter extends ArrayAdapter<LogListRowObj> implements View.OnClickListener{

    private ArrayList<LogListRowObj> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView tvDate;
        TextView tvEntryTime;
        TextView tvExitTime;
        TextView tvTotalTime;
    }

    public ListAdapter(ArrayList<LogListRowObj> data, Context context) {
        super(context, R.layout.row_list, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        LogListRowObj logListRowObj =(LogListRowObj)object;

    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        LogListRowObj logListRowObj = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_list, parent, false);
            viewHolder.tvDate = convertView.findViewById(R.id.tv_date);
            viewHolder.tvEntryTime = convertView.findViewById(R.id.tv_entry_time);
            viewHolder.tvExitTime = convertView.findViewById(R.id.tv_exit_time);
            viewHolder.tvTotalTime = convertView.findViewById(R.id.tv_total_time);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvDate.setText(logListRowObj.getDate());
        viewHolder.tvEntryTime.setText(logListRowObj.getEntryTime());
        viewHolder.tvExitTime.setText(logListRowObj.getExitTime());
        viewHolder.tvTotalTime.setText(logListRowObj.getTotalTime());
        return convertView;
    }
}




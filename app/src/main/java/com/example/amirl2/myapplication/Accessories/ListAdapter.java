package com.example.amirl2.myapplication.Accessories;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.amirl2.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by AmirL2 on 8/26/2017.
 */

public class ListAdapter extends ArrayAdapter<LogListRowObj> implements View.OnClickListener{

    private ArrayList<LogListRowObj> dataSet;
    Context mContext;
    private HashMap<Integer, Boolean> mSelection = new HashMap<Integer, Boolean>();

    // View lookup cache
    private static class ViewHolder {
        TextView tvDate;
        TextView tvEntryTime;
        TextView tvExitTime;
        TextView tvTotalTime;
        TextView tvNotes;

    }

    public ListAdapter(ArrayList<LogListRowObj> data, Context context) {
        super(context, R.layout.row_list, data);
        this.dataSet = data;
        this.mContext=context;

    }

    public void setNewSelection(int position, boolean value) {
        mSelection.put(position, value);
        notifyDataSetChanged();
    }

    public boolean isPositionChecked(int position) {
        Boolean result = mSelection.get(position);
        return result == null ? false : result;
    }

    public Set<Integer> getCurrentCheckedPosition() {
        return mSelection.keySet();
    }

    public void removeSelection(int position) {
        mSelection.remove(position);
        notifyDataSetChanged();
    }

    public void clearSelection() {
        mSelection = new HashMap<Integer, Boolean>();
        notifyDataSetChanged();
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
            viewHolder.tvNotes = convertView.findViewById(R.id.tv_notes);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }

        viewHolder.tvDate.setText(logListRowObj.getDate());
        viewHolder.tvEntryTime.setText(logListRowObj.getEntryTime());
        viewHolder.tvExitTime.setText(logListRowObj.getExitTime());
        viewHolder.tvTotalTime.setText(logListRowObj.getTotalTime());
        viewHolder.tvNotes.setText("" + logListRowObj.getNotes());

        if (mSelection.get(position) != null)
            convertView.setBackgroundColor(Color.RED);// this is a selected position so make it red

        else
            convertView.setBackgroundColor(17170451);

        return convertView;
    }
}

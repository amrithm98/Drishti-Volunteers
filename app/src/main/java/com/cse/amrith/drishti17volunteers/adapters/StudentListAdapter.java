package com.cse.amrith.drishti17volunteers.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.cse.amrith.drishti17volunteers.Models.EventAdminStudentModel;
import com.cse.amrith.drishti17volunteers.R;

import java.util.ArrayList;

/**
 * Created by kevin on 3/16/17.
 */

public class StudentListAdapter extends BaseAdapter {
    ArrayList<EventAdminStudentModel> students;
    Context context;
    public static LayoutInflater inflater=null;
    public StudentListAdapter(Context context,ArrayList<EventAdminStudentModel> students){
        this.students=students;
        this.context=context;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class Holder{
        TextView name;
        TextView contact;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder=new Holder();
        Log.d("here",position+"");
        View rowView=inflater.inflate(R.layout.student_list,null);
        holder.name=(TextView)rowView.findViewById(R.id.name);
        holder.contact=(TextView)rowView.findViewById(R.id.contact);
        holder.name.setText(students.get(position).name);
        holder.contact.setText(students.get(position).phone);
        holder.name.setTextColor(0xffff0000);
        holder.contact.setTextColor(0xffff0000);
        if(students.get(position).paid) {
            holder.name.setTextColor(0xff00ff00);
            holder.contact.setTextColor(0xff00ff00);
        }
        return rowView;
    }
}

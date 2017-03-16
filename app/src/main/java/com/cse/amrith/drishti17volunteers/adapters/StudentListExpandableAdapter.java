package com.cse.amrith.drishti17volunteers.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.cse.amrith.drishti17volunteers.Models.EventAdminStudentModel;
import com.cse.amrith.drishti17volunteers.R;

import java.util.ArrayList;

/**
 * Created by kevin on 3/16/17.
 */

public class StudentListExpandableAdapter extends BaseExpandableListAdapter {
    Context context;
    ArrayList<EventAdminStudentModel> students;
    public static LayoutInflater inflater=null;
    public StudentListExpandableAdapter(Context context,ArrayList<EventAdminStudentModel> students){
        this.context=context;
        this.students=students;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getGroupCount() {
        return students.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return students.get(groupPosition).group.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return students.get(groupPosition).group.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
    public class Holder{
        TextView name;
        TextView contact;
    }
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Holder holder=new Holder();
        View rowView=inflater.inflate(R.layout.student_list,null);
        holder.name=(TextView)rowView.findViewById(R.id.name);
        holder.contact=(TextView)rowView.findViewById(R.id.contact);
        holder.name.setText(students.get(groupPosition).name);
        holder.contact.setText(students.get(groupPosition).phone);

        holder.name.setTextColor(0xffff0000);
        holder.contact.setTextColor(0xffff0000);
        if(students.get(groupPosition).paid) {
            holder.name.setTextColor(0xff0000ff);
            holder.contact.setTextColor(0xff0000ff);
        }
        return rowView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Holder holder=new Holder();
        View rowView=inflater.inflate(R.layout.student_list,null);
        holder.name=(TextView)rowView.findViewById(R.id.name);
        holder.contact=(TextView)rowView.findViewById(R.id.contact);
        holder.name.setText(students.get(groupPosition).group.get(childPosition).name);
        holder.contact.setText(students.get(groupPosition).group.get(childPosition).phone);
        holder.name.setTextColor(0xffff0000);
        holder.contact.setTextColor(0xffff0000);
        rowView.setBackgroundColor(0xffd3d3d3);
        if(students.get(groupPosition).paid) {
            holder.name.setTextColor(0xff0000ff);
            holder.contact.setTextColor(0xff0000ff);
        }
        return rowView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}

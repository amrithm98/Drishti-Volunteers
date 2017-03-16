package com.cse.amrith.drishti17volunteers.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cse.amrith.drishti17volunteers.Models.EventAdminStudentModel;
import com.cse.amrith.drishti17volunteers.R;
import com.cse.amrith.drishti17volunteers.Utils.ui.Util;

import java.util.ArrayList;

/**
 * Created by kevin on 3/16/17.
 */

public class StudentListExpandableAdapter extends BaseExpandableListAdapter {
    Context context;
    Activity activity;
    ArrayList<EventAdminStudentModel> students;
    public static LayoutInflater inflater = null;

    public StudentListExpandableAdapter(Activity activity, Context context, ArrayList<EventAdminStudentModel> students) {
        this.activity = activity;
        this.context = context;
        this.students = students;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

    public class Holder {
        TextView name;
        ImageView contact;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        View rowView = inflater.inflate(R.layout.student_list, null);
        holder.name = (TextView) rowView.findViewById(R.id.name);
        holder.contact = (ImageView) rowView.findViewById(R.id.contact);
        holder.name.setText(students.get(groupPosition).name);

        holder.contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.callIntent(activity, students.get(groupPosition).phone);
            }
        });

        //  holder.contact.setText(students.get(groupPosition).phone);

        holder.name.setTextColor(0xffff0000);
        //  holder.contact.setTextColor(0xffff0000);
        if (students.get(groupPosition).paid) {
            holder.name.setTextColor(0xff0000ff);
            //   holder.contact.setTextColor(0xff0000ff);
        }
        return rowView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, final View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        View rowView = inflater.inflate(R.layout.student_list, null);
        holder.name = (TextView) rowView.findViewById(R.id.name);
        holder.contact = (ImageView) rowView.findViewById(R.id.contact);
        holder.name.setText(students.get(groupPosition).group.get(childPosition).name);

        holder.contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.callIntent(activity, students.get(groupPosition).group.get(childPosition).phone);
            }
        });

        //  holder.contact.setText();
        holder.name.setTextColor(0xffff0000);
        //holder.contact.setTextColor(0xffff0000);
        rowView.setBackgroundColor(0xffd3d3d3);
        if (students.get(groupPosition).paid) {
            holder.name.setTextColor(0xff0000ff);
            // holder.contact.setTextColor(0xff0000ff);
        }
        return rowView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}

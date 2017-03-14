package com.cse.amrith.drishti17volunteers.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.cse.amrith.drishti17volunteers.Models.RegisteredEvents;
import com.cse.amrith.drishti17volunteers.R;

import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by amrith on 3/14/17.
 */

public class EventListAdapter extends BaseAdapter {

    List<RegisteredEvents> registeredEvents;
    Context context;

    public EventListAdapter(List<RegisteredEvents> events, Context c) {
        registeredEvents = events;
        context = c;
    }
    @Override
    public int getCount() {
        return registeredEvents.size();
    }
    public List<RegisteredEvents> returnList()
    {
        return registeredEvents;
    }

    @Override
    public Object getItem(int position) {
        return registeredEvents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Tag tag;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_registeredevent, parent, false);
            tag = new Tag(convertView);
        } else {
            tag = (Tag) convertView.getTag();
        }
        final RegisteredEvents event = (RegisteredEvents) getItem(position);
        tag.tvName.setText(event.name);
        tag.cbPaid.setChecked(event.paid);
        tag.cbPaid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                event.paid = isChecked;
            }
        });
        convertView.setTag(tag);
        return convertView;
    }
    public List<RegisteredEvents> getRegisteredEvents() {
        return registeredEvents;
    }

    static class Tag {
        TextView tvName;
        CheckBox cbPaid;

        Tag(View v) {
            tvName = (TextView) v.findViewById(R.id.event_name);
            cbPaid = (CheckBox) v.findViewById(R.id.event_paid);
        }
    }

}

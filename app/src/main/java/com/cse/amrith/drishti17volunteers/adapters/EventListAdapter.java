package com.cse.amrith.drishti17volunteers.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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
    long userId;

    public EventListAdapter(List<RegisteredEvents> events, Context c, long aLong) {
        registeredEvents = events;
        context = c;
        userId = aLong;
    }

    @Override
    public int getCount() {
        return registeredEvents.size();
    }

    public List<RegisteredEvents> returnList() {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_confirm, parent, false);
            tag = new Tag(convertView);
        } else {
            tag = (Tag) convertView.getTag();
        }
        final RegisteredEvents event = (RegisteredEvents) getItem(position);
        tag.tvName.setText(event.name);
        if (event.paid)
            tag.tvName.setTextColor(Color.parseColor("#00897b"));
        else
            tag.tvName.setTextColor(Color.BLACK);
        if (userId == event.registeredStudent) {
            tag.bConf.setVisibility(View.VISIBLE);
            if (event.paid) {
                tag.bConf.setText("Refund");
            } else
                tag.bConf.setText("Pay");
            tag.bConf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("paying", event.id + "");
                }
            });
        } else {
            tag.bConf.setVisibility(View.INVISIBLE);
        }
        convertView.setTag(tag);
        return convertView;
    }

    static class Tag {
        TextView tvName;
        Button bConf;

        Tag(View v) {
            tvName = (TextView) v.findViewById(R.id.tv_name_conf);
            bConf = (Button) v.findViewById(R.id.button_conf);
        }
    }

}

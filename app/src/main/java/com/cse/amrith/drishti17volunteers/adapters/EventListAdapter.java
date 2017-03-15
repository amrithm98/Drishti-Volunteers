package com.cse.amrith.drishti17volunteers.adapters;

import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.cse.amrith.drishti17volunteers.Models.PaymentModel;
import com.cse.amrith.drishti17volunteers.Models.RegisteredEvents;
import com.cse.amrith.drishti17volunteers.R;
import com.cse.amrith.drishti17volunteers.Utils.ApiClient;
import com.cse.amrith.drishti17volunteers.Utils.AuthUtil;
import com.cse.amrith.drishti17volunteers.Utils.NetworkUtil;
import com.cse.amrith.drishti17volunteers.Utils.RestApiInterface;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    @Override
    public Object getItem(int position) {
        return registeredEvents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
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
                    pay(event, position);
                }
            });
        } else {
            tag.bConf.setVisibility(View.INVISIBLE);
        }
        convertView.setTag(tag);
        return convertView;
    }

    private void pay(final RegisteredEvents event, final int position) {
        if (NetworkUtil.isNetworkAvailable(context)) {
            ((EventListListener) context).showLoading();
            AuthUtil.getFirebaseToken(new AuthUtil.Listener() {
                @Override
                public void tokenObtained(String token) {
                    RestApiInterface service = ApiClient.getService();
                    Call<PaymentModel> call = service.confirmPayment(token, userId, event.id, !event.paid);
                    call.enqueue(new Callback<PaymentModel>() {
                        @Override
                        public void onResponse(Call<PaymentModel> call, Response<PaymentModel> response) {
                            ((EventListListener) context).hideLoading();
                            if (response.code() == 200) {
                                Toast.makeText(context, "Payment Updated", Toast.LENGTH_SHORT).show();
                                PaymentModel result = response.body();
                                Log.d("Response", new Gson().toJson(result));
                                event.paid = result.paid;
                                notifyDataSetChanged();
                            } else {
                                Toast.makeText(context, "Unable to update", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<PaymentModel> call, Throwable t) {
                            Log.d("ERROR", t.toString());
                            ((EventListListener) context).hideLoading();
                            Toast.makeText(context, "Network Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        } else {
            Toast.makeText(context, "Network Unavailable", Toast.LENGTH_SHORT);
        }
    }

    static class Tag {
        TextView tvName;
        Button bConf;

        Tag(View v) {
            tvName = (TextView) v.findViewById(R.id.tv_name_conf);
            bConf = (Button) v.findViewById(R.id.button_conf);
        }
    }

    public interface EventListListener {
        void showLoading();
        void hideLoading();
    }

}

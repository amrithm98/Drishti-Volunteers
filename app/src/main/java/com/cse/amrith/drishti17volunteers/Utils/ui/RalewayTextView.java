package com.cse.amrith.drishti17volunteers.Utils.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;


public class RalewayTextView extends TextView {
    public RalewayTextView(Context context) {
        this(context, null);
    }

    public RalewayTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RalewayTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Util.settypefaces(context,"Raleway-Regular.ttf",this);
    }

}

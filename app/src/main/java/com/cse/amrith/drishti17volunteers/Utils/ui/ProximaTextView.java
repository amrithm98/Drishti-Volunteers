package com.cse.amrith.drishti17volunteers.Utils.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;


public class ProximaTextView extends TextView {
    public ProximaTextView(Context context) {
        this(context, null);
    }

    public ProximaTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProximaTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Util.settypefaces(context,"ProximaNovaSoft-Regular.otf",this);
    }

}

package com.cse.amrith.drishti17volunteers.Utils.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;


public class CanaroTextView extends TextView {
    public CanaroTextView(Context context) {
        this(context, null);
    }

    public CanaroTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CanaroTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Util.settypefaces(context,"canaro_extra_bold.otf",this);
        //setAllCaps(true);
    }

}

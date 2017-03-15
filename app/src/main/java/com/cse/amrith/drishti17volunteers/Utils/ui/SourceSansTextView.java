package com.cse.amrith.drishti17volunteers.Utils.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;


public class SourceSansTextView extends TextView {

    private static final String TAG = SourceSansTextView.class.getSimpleName();
    public SourceSansTextView(Context context) {
        this(context, null);
    }

    public SourceSansTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SourceSansTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Util.settypefaces(context,"SourceSansPro-Regular.otf",this);
    }

    @Override
    public void scrollTo(int x, int y) {
    }
}

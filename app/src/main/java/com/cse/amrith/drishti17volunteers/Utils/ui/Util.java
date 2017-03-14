package com.cse.amrith.drishti17volunteers.Utils.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by droidcafe on 3/15/2017.
 */

public class Util {

    public static void settypefaces(Context context, String typefaceName, TextView textview) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), typefaceName);
        textview.setTypeface(typeface);

    }

}

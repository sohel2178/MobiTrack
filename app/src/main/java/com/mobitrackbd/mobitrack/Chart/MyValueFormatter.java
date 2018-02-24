package com.mobitrackbd.mobitrack.Chart;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.utils.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

/**
 * Created by IMATPC-12 on 22-Feb-18.
 */

public class MyValueFormatter implements ValueFormatter {

    private DecimalFormat mFormat;

    public MyValueFormatter(){
        mFormat = new DecimalFormat("###,###,##0.0"); // use one decimal
    }

    @Override
    public String getFormattedValue(float value) {
        return mFormat.format(value)+" KM";// e.g. append a dollar-sign

    }
}

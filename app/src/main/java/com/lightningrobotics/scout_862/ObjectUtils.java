package com.lightningrobotics.scout_862;

import static com.lightningrobotics.scout_862.CycleData.addToCycleArray;
import static com.lightningrobotics.scout_862.CycleData.cycleNum;
import static com.lightningrobotics.scout_862.CycleData.highCycleData;
import static com.lightningrobotics.scout_862.CycleData.lowCycleData;
import static com.lightningrobotics.scout_862.FileUtils.addToArray;
import static com.lightningrobotics.scout_862.FileUtils.appData;
import static com.lightningrobotics.scout_862.FileUtils.matchCounter;
import static java.lang.Integer.parseInt;

import android.widget.CheckBox;
import android.widget.RadioButton;

public class ObjectUtils
{
    public static CheckBox readCheckBox(CheckBox cb, int xValue)
    {
        if(cb == null)
        {
            /**
             * In some cases, we do not care if a checkbox returns a null.
             * A checkbox returning a null is most likely because the fragment
             * it is a part of has not yet been created.
             */
            System.out.println("cb is null");
        }
        if(appData[xValue][matchCounter].equals("1"))
            cb.setChecked(true);
        else
            cb.setChecked(false);

        return cb;
    }

    public static void writeCheckBox(CheckBox cb, int xValue)
    {
        if(cb.isChecked())
            addToArray(xValue,matchCounter,"1");
        else
            addToArray(xValue,matchCounter,"0");
    }
}
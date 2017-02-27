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

    public static RadioButton readRadioButton(RadioButton rb)
    {
        if(rb == null)
        {
            System.out.println("rb is null");
        }
        String idAsString = rb.getResources().getResourceName(rb.getId());
        String[] splitIDdAsString = idAsString.split("_");
        int cbNum = Integer.parseInt(splitIDdAsString[3]);
        int[][]array = lowCycleData;
        if(splitIDdAsString[2].equals("high"))
            array = highCycleData;
        if(array[cycleNum][matchCounter] == cbNum)
            rb.setChecked(true);
        else
            rb.setChecked(false);
        return rb;
    }

    public static void writeRadioButton(RadioButton rb)
    {
        String idAsString = rb.getResources().getResourceName(rb.getId());
        String[] splitIDdAsString = idAsString.split("_");
        int cbNum = Integer.parseInt(splitIDdAsString[3]);
        boolean high = false;
        if (splitIDdAsString[2].equals("high"))
           high = true;
        if (rb.isChecked())
            addToCycleArray(cbNum, high);
    }
}
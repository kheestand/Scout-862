package com.lightningrobotics.scout_862;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import static com.lightningrobotics.scout_862.CycleData.cycleNum;
import static com.lightningrobotics.scout_862.CycleData.getHighAverage;
import static com.lightningrobotics.scout_862.CycleData.getLowAverage;
import static com.lightningrobotics.scout_862.CycleData.readLowAndHighArray;
import static com.lightningrobotics.scout_862.CycleData.writeLowAndHighArray;
import static com.lightningrobotics.scout_862.FileUtils.addToArray;
import static com.lightningrobotics.scout_862.FileUtils.appData;
import static com.lightningrobotics.scout_862.FileUtils.matchCounter;
import static com.lightningrobotics.scout_862.FullscreenActivity.importedData;
import static com.lightningrobotics.scout_862.ObjectUtils.writeCheckBox;
import static com.lightningrobotics.scout_862.PagerAdapter.fragmentArray;
import static java.lang.String.valueOf;

public class TeleopActivity extends Fragment implements View.OnClickListener {

    private static int gearCount = 0;
    public static View teleopView;
    public static Button lastCycleButton;
    public static Button nextCycleButton;
    private static Button addGearDel;
    private static Button subGearDel;
    public static TextView tv_cycleNumber;
    public static TextView tv_gearCount;
    private static ToggleButton highButton;
    private static ToggleButton lowButton;
    public static RadioButton low_0;
    public static RadioButton low_1;
    public static RadioButton low_2;
    public static RadioButton low_3;
    public static RadioButton low_4;
    public static RadioButton low_5;
    public static RadioButton low_6;
    public static RadioButton low_7;
    public static RadioButton low_8;
    public static RadioButton low_9;
    public static RadioButton low_10;
    public static RadioButton low_11;
    public static RadioButton high_0;
    public static RadioButton high_1;
    public static RadioButton high_2;
    public static RadioButton high_3;
    public static RadioButton high_4;
    public static RadioButton high_5;
    public static RadioButton high_6;
    public static RadioButton high_7;
    public static RadioButton high_8;
    public static RadioButton high_9;
    public static RadioButton high_10;
    public static RadioButton high_11;
    private static RadioGroup highRadioGroup;
    private static RadioGroup lowRadioGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("onCreateView: Teleop");
        fragmentArray[1] = true;
        // Inflate the layout for this fragment
        teleopView = inflater.inflate(R.layout.fragment_teleop, container, false);
        lastCycleButton = (Button) teleopView.findViewById(R.id.btn_previous_cycle);
        nextCycleButton = (Button) teleopView.findViewById(R.id.btn_next_cycle);
        addGearDel = (Button) teleopView.findViewById(R.id.btn_teleop_addGearsDel);
        subGearDel = (Button) teleopView.findViewById(R.id.btn_teleop_subGearsDelivered);

        low_0 = (RadioButton) teleopView.findViewById(R.id.rb_low_0);
        low_1 = (RadioButton) teleopView.findViewById(R.id.rb_low_1);
        low_2 = (RadioButton) teleopView.findViewById(R.id.rb_low_2);
        low_3 = (RadioButton) teleopView.findViewById(R.id.rb_low_3);
        low_4 = (RadioButton) teleopView.findViewById(R.id.rb_low_4);
        low_5 = (RadioButton) teleopView.findViewById(R.id.rb_low_5);
        low_6 = (RadioButton) teleopView.findViewById(R.id.rb_low_6);
        low_7 = (RadioButton) teleopView.findViewById(R.id.rb_low_7);
        low_8 = (RadioButton) teleopView.findViewById(R.id.rb_low_8);
        low_9 = (RadioButton) teleopView.findViewById(R.id.rb_low_9);
        low_10 = (RadioButton) teleopView.findViewById(R.id.rb_low_10);
        low_11 = (RadioButton) teleopView.findViewById(R.id.rb_low_11);

        high_0 = (RadioButton) teleopView.findViewById(R.id.rb_high_0);
        high_1 = (RadioButton) teleopView.findViewById(R.id.rb_high_1);
        high_2 = (RadioButton) teleopView.findViewById(R.id.rb_high_2);
        high_3 = (RadioButton) teleopView.findViewById(R.id.rb_high_3);
        high_4 = (RadioButton) teleopView.findViewById(R.id.rb_high_4);
        high_5 = (RadioButton) teleopView.findViewById(R.id.rb_high_5);
        high_6 = (RadioButton) teleopView.findViewById(R.id.rb_high_6);
        high_7 = (RadioButton) teleopView.findViewById(R.id.rb_high_7);
        high_8 = (RadioButton) teleopView.findViewById(R.id.rb_high_8);
        high_9 = (RadioButton) teleopView.findViewById(R.id.rb_high_9);
        high_10 = (RadioButton) teleopView.findViewById(R.id.rb_high_10);
        high_11 = (RadioButton) teleopView.findViewById(R.id.rb_high_11);

        tv_cycleNumber = (TextView) teleopView.findViewById(R.id.tv_cycle_number);
        tv_gearCount = (TextView) teleopView.findViewById(R.id.tv_teleop_gearsDel);
        highButton = (ToggleButton) teleopView.findViewById(R.id.tb_scoreHigh);
        lowButton = (ToggleButton) teleopView.findViewById(R.id.tb_scoreLow);

        highRadioGroup = (RadioGroup) teleopView.findViewById(R.id.rg_fuelHigh);
        lowRadioGroup = (RadioGroup) teleopView.findViewById(R.id.rg_fuelLow);

        lastCycleButton.setOnClickListener(this);
        nextCycleButton.setOnClickListener(this);
        addGearDel.setOnClickListener(this);
        subGearDel.setOnClickListener(this);
        highButton.setOnClickListener(this);
        lowButton.setOnClickListener(this);

        lowRadioGroup.setVisibility(View.GONE);
        highRadioGroup.setVisibility(View.GONE);
        return teleopView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentArray[1] = false;
    }

    public void writeTeleop()
    {
        writeLowAndHighArray();
        addToArray(12,matchCounter,valueOf(tv_gearCount.getText()));
        if(highButton.isChecked())
            appData[17][matchCounter] = "1";
        else
            appData[17][matchCounter] = "0";
        addToArray(15,matchCounter,valueOf(getHighAverage()));
        if(lowButton.isChecked())
            appData[17][matchCounter] = "1";
        else
            appData[17][matchCounter] = "0";
        addToArray(18,matchCounter,valueOf(getLowAverage()));
        addToArray(19,matchCounter,valueOf(cycleNum));
    }

    public void readTeleop()
    {
        readLowAndHighArray();
        gearCount = Integer.parseInt(appData[12][matchCounter]);
        tv_gearCount.setText(appData[12][matchCounter]);
        cycleNum = Integer.parseInt(appData[19][matchCounter]);
        tv_cycleNumber.setText(appData[19][matchCounter]);
        if(appData[14][matchCounter].equals("1"))
        {
            highRadioGroup.setVisibility(View.VISIBLE);
            highButton.setChecked(true);
        }
        else
        {
            highRadioGroup.setVisibility(View.GONE);
            highButton.setChecked(false);
        }
        if(appData[17][matchCounter].equals("1"))
        {
            lowRadioGroup.setVisibility(View.VISIBLE);
            lowButton.setChecked(true);
        }
        else
        {
            lowRadioGroup.setVisibility(View.GONE);
            lowButton.setChecked(false);
        }
    }

    @Override
    public void onClick(View v) {

        if(importedData != false) {
            if (v == highButton) {
                if (highButton.isChecked()) {
                    highRadioGroup.setVisibility(View.VISIBLE);
                    appData[14][matchCounter] = "1";
                } else {
                    highRadioGroup.setVisibility(View.GONE);
                    appData[14][matchCounter] = "0";
                }
            }

            if (v == lowButton) {
                if (lowButton.isChecked()) {
                    lowRadioGroup.setVisibility(View.VISIBLE);
                    appData[17][matchCounter] = "1";
                } else {
                    lowRadioGroup.setVisibility(View.GONE);
                    appData[17][matchCounter] = "0";
                }
            }


            if ((v == nextCycleButton && cycleNum < 7) || (v == lastCycleButton && cycleNum > 1)) {
                writeLowAndHighArray();
                if (v == nextCycleButton)
                    cycleNum++;
                if (v == lastCycleButton)
                    cycleNum--;
                readLowAndHighArray();
                tv_cycleNumber.setText(String.format("%01d", cycleNum));
            }
            if ((v == addGearDel && gearCount < 20) || (v == subGearDel && gearCount > 0)) {
                if (v == addGearDel)
                    gearCount++;
                if (v == subGearDel)
                    gearCount--;

                tv_gearCount.setText(valueOf(gearCount));
            }
        }
        else {
            matchCounter = 1;
        }
    }
}

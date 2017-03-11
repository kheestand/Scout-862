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

import com.jesusm.holocircleseekbar.lib.HoloCircleSeekBar;

import static com.lightningrobotics.scout_862.CycleData.addToCycleArray;
import static com.lightningrobotics.scout_862.CycleData.cycleNum;
import static com.lightningrobotics.scout_862.CycleData.getHighAverage;
import static com.lightningrobotics.scout_862.CycleData.getLowAverage;
import static com.lightningrobotics.scout_862.CycleData.highCycleData;
import static com.lightningrobotics.scout_862.CycleData.lowCycleData;
import static com.lightningrobotics.scout_862.CycleData.maxCycleNum;
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
    private static HoloCircleSeekBar teleopLowNum;
    private static HoloCircleSeekBar teleopHighNum;

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

        tv_cycleNumber = (TextView) teleopView.findViewById(R.id.tv_cycle_number);
        tv_gearCount = (TextView) teleopView.findViewById(R.id.tv_teleop_gearsDel);
        highButton = (ToggleButton) teleopView.findViewById(R.id.tb_scoreHigh);
        lowButton = (ToggleButton) teleopView.findViewById(R.id.tb_scoreLow);

        teleopLowNum = (HoloCircleSeekBar) teleopView.findViewById(R.id.picker_teleop_low);
        teleopHighNum = (HoloCircleSeekBar) teleopView.findViewById(R.id.picker_teleop_high);

        lastCycleButton.setOnClickListener(this);
        nextCycleButton.setOnClickListener(this);
        addGearDel.setOnClickListener(this);
        subGearDel.setOnClickListener(this);
        highButton.setOnClickListener(this);
        lowButton.setOnClickListener(this);

        teleopLowNum.setVisibility(View.GONE);
        teleopHighNum.setVisibility(View.GONE);
        return teleopView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentArray[1] = false;
    }

    public void writeTeleop()
    {
        addToCycleArray(teleopLowNum.getValue(),false);
        addToCycleArray(teleopHighNum.getValue(),true);
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
        teleopLowNum.setValue(lowCycleData[cycleNum][matchCounter]);
        teleopHighNum.setValue(highCycleData[cycleNum][matchCounter]);
        gearCount = Integer.parseInt(appData[12][matchCounter]);
        tv_gearCount.setText(appData[12][matchCounter]);
        cycleNum = Integer.parseInt(appData[19][matchCounter]);
        tv_cycleNumber.setText(appData[19][matchCounter]);
        if(appData[14][matchCounter].equals("1"))
        {
            teleopHighNum.setVisibility(View.VISIBLE);
            highButton.setChecked(true);
        }
        else
        {
            teleopHighNum.setVisibility(View.GONE);
            highButton.setChecked(false);
        }
        if(appData[17][matchCounter].equals("1"))
        {
            teleopLowNum.setVisibility(View.VISIBLE);
            lowButton.setChecked(true);
        }
        else
        {
            teleopLowNum.setVisibility(View.GONE);
            lowButton.setChecked(false);
        }
    }

    @Override
    public void onClick(View v) {

        if(importedData != false) {
            if (v == highButton) {
                if (highButton.isChecked()) {
                    teleopHighNum.setVisibility(View.VISIBLE);
                    appData[14][matchCounter] = "1";
                } else {
                    teleopHighNum.setVisibility(View.GONE);
                    appData[14][matchCounter] = "0";
                }
            }

            if (v == lowButton) {
                if (lowButton.isChecked()) {
                    teleopLowNum.setVisibility(View.VISIBLE);
                    appData[17][matchCounter] = "1";
                } else {
                    teleopLowNum.setVisibility(View.GONE);
                    appData[17][matchCounter] = "0";
                }
            }


            if ((v == nextCycleButton && cycleNum < maxCycleNum - 1) || (v == lastCycleButton && cycleNum > 1)) {
                addToCycleArray(teleopLowNum.getValue(),false);
                addToCycleArray(teleopHighNum.getValue(),true);
                if (v == nextCycleButton)
                    cycleNum++;
                if (v == lastCycleButton)
                    cycleNum--;
                System.out.println("lowCycleData setValue " + lowCycleData[cycleNum][matchCounter]);
                teleopLowNum.setValue(lowCycleData[cycleNum][matchCounter]);
                System.out.println("highCycleData setValue " + highCycleData[cycleNum][matchCounter]);
                teleopHighNum.setValue(highCycleData[cycleNum][matchCounter]);
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

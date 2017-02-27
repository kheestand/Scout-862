package com.lightningrobotics.scout_862;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.media.RatingCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import static com.lightningrobotics.scout_862.FileUtils.addToArray;
import static com.lightningrobotics.scout_862.FileUtils.appData;
import static com.lightningrobotics.scout_862.FileUtils.matchCounter;
import static com.lightningrobotics.scout_862.ObjectUtils.readCheckBox;
import static com.lightningrobotics.scout_862.ObjectUtils.writeCheckBox;
import static com.lightningrobotics.scout_862.PagerAdapter.fragmentArray;

public class AutoActivity extends Fragment implements View.OnClickListener {
    // Called once the Fragment has been created in order for it to
    // create its user interface.

    private static View autoView;
    private static CheckBox autoLowBool;
    private static CheckBox autoHighBool;
    private static CheckBox autoGearBool;
    private static LinearLayout lowGoalLayout;
    private static LinearLayout highGoalLayout;
    private static LinearLayout gearLayout;
    private static EditText autoLowNum;
    private static EditText autoHighNum;
    private static EditText autoGearNum;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("onCreateView: Auto");
        fragmentArray[0] = true;
        autoView = inflater.inflate(R.layout.fragment_auto, container, false);
        // Inflate the layout for this fragment
        //Set both autoView to view
        autoLowBool = (CheckBox) autoView.findViewById(R.id.cb_auto_scoreLow);
        autoHighBool = (CheckBox) autoView.findViewById(R.id.cb_auto_scoreHigh);
        autoGearBool = (CheckBox) autoView.findViewById(R.id.cb_auto_delGear);
        lowGoalLayout = (LinearLayout) autoView.findViewById(R.id.AUTOLOW_CONTENT);
        highGoalLayout = (LinearLayout) autoView.findViewById(R.id.AUTOHIGH_CONTENT);
        gearLayout = (LinearLayout) autoView.findViewById(R.id.AUTOGEAR_CONTENT);
        autoLowNum = (EditText) autoView.findViewById(R.id.et_auto_lowScore);
        autoHighNum = (EditText) autoView.findViewById(R.id.et_auto_highScore);
        autoGearNum = (EditText) autoView.findViewById(R.id.et_auto_gearScore);

        autoLowBool.setOnClickListener(this);
        autoHighBool.setOnClickListener(this);
        autoGearBool.setOnClickListener(this);
        return autoView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentArray[0] = false;
    }

    public void writeAuto()
    {
        writeCheckBox(autoLowBool,3);
        addToArray(4,matchCounter,autoLowNum.getText().toString());
        writeCheckBox(autoHighBool,5);
        addToArray(6,matchCounter,autoHighNum.getText().toString());
        writeCheckBox(autoGearBool,9);
        addToArray(10,matchCounter,autoGearNum.getText().toString());
    }

    public void readAuto()
    {
        autoLowBool = readCheckBox(autoLowBool,3);
        autoLowNum.setText(appData[4][matchCounter]);
        autoHighBool = readCheckBox(autoHighBool,5);
        autoHighNum.setText(appData[6][matchCounter]);
        autoGearBool = readCheckBox(autoGearBool,9);
        autoGearNum.setText(appData[10][matchCounter]);

        if(appData[3][matchCounter].equals("1"))
        {
            lowGoalLayout.setVisibility(View.VISIBLE);
            autoLowBool.setChecked(true);
        }
        else
        {
            lowGoalLayout.setVisibility(View.GONE);
            autoLowBool.setChecked(false);
        }
        if(appData[5][matchCounter].equals("1"))
        {
            highGoalLayout.setVisibility(View.VISIBLE);
            autoHighBool.setChecked(true);
        }
        else
        {
            highGoalLayout.setVisibility(View.GONE);
            autoHighBool.setChecked(false);
        }
        if(appData[9][matchCounter].equals("1"))
        {
            gearLayout.setVisibility(View.VISIBLE);
            autoGearBool.setChecked(true);
        }
        else
        {
            gearLayout.setVisibility(View.GONE);
            autoGearBool.setChecked(false);
        }
	}

    @Override
    public void onClick(View v) {
        if (v == autoLowBool) {
            if(autoLowBool.isChecked())
                lowGoalLayout.setVisibility(View.VISIBLE);
            else
                lowGoalLayout.setVisibility(View.GONE);
        }

        if (v == autoHighBool) {
            if(autoHighBool.isChecked())
                highGoalLayout.setVisibility(View.VISIBLE);
            else
                highGoalLayout.setVisibility(View.GONE);
        }

        if (v == autoGearBool) {
            if(autoGearBool.isChecked())
                gearLayout.setVisibility(View.VISIBLE);
            else
                gearLayout.setVisibility(View.GONE);
        }
    }
}
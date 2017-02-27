package com.lightningrobotics.scout_862;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import static com.lightningrobotics.scout_862.FileUtils.addToArray;
import static com.lightningrobotics.scout_862.FileUtils.appData;
import static com.lightningrobotics.scout_862.FileUtils.matchCounter;
import static com.lightningrobotics.scout_862.ObjectUtils.readCheckBox;
import static com.lightningrobotics.scout_862.ObjectUtils.writeCheckBox;
import static com.lightningrobotics.scout_862.PagerAdapter.fragmentArray;
import static java.lang.String.valueOf;

public class EndActivity extends Fragment {

    public static View endView;
    private static CheckBox climb;
    private static CheckBox collectFuel;
    private static CheckBox stuckOnFuel;
    private static CheckBox defence;
    private static EditText comments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("onCreateView: End");
        fragmentArray[2] = true;
        // Inflate the layout for this fragment
        endView = inflater.inflate(R.layout.fragment_end, container, false);
        climb = (CheckBox) endView.findViewById(R.id.cb_end_climb);
        collectFuel = (CheckBox) endView.findViewById(R.id.cb_end_collectFuel);
        stuckOnFuel = (CheckBox) endView.findViewById(R.id.cb_end_stuck);
        defence = (CheckBox) endView.findViewById(R.id.cb_end_def);
        comments = (EditText) endView.findViewById(R.id.et_otherCpmments);
        return endView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentArray[2] = false;
    }

    public void writeEnd()
    {
        writeCheckBox(climb, 20);
        writeCheckBox(collectFuel, 23);
        writeCheckBox(stuckOnFuel, 21);
        writeCheckBox(defence, 22);
        addToArray(24,matchCounter,valueOf(comments.getText()));
    }

    public void readEnd()
    {
        climb = readCheckBox(climb, 20);
        collectFuel = readCheckBox(collectFuel, 23);
        stuckOnFuel = readCheckBox(stuckOnFuel, 21);
        defence = readCheckBox(defence, 22);
        comments.setText(appData[24][matchCounter]);
    }
}

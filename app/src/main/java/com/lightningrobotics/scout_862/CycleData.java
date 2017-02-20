package com.lightningrobotics.scout_862;

import static com.lightningrobotics.scout_862.FileUtils.matchCounter;
import static com.lightningrobotics.scout_862.FileUtils.maxX;
import static com.lightningrobotics.scout_862.FileUtils.maxY;
import static com.lightningrobotics.scout_862.ObjectUtils.readRadioButton;
import static com.lightningrobotics.scout_862.ObjectUtils.writeRadioButton;
import static com.lightningrobotics.scout_862.TeleopActivity.high_0;
import static com.lightningrobotics.scout_862.TeleopActivity.high_1;
import static com.lightningrobotics.scout_862.TeleopActivity.high_10;
import static com.lightningrobotics.scout_862.TeleopActivity.high_11;
import static com.lightningrobotics.scout_862.TeleopActivity.high_2;
import static com.lightningrobotics.scout_862.TeleopActivity.high_3;
import static com.lightningrobotics.scout_862.TeleopActivity.high_4;
import static com.lightningrobotics.scout_862.TeleopActivity.high_5;
import static com.lightningrobotics.scout_862.TeleopActivity.high_6;
import static com.lightningrobotics.scout_862.TeleopActivity.high_7;
import static com.lightningrobotics.scout_862.TeleopActivity.high_8;
import static com.lightningrobotics.scout_862.TeleopActivity.high_9;
import static com.lightningrobotics.scout_862.TeleopActivity.low_0;
import static com.lightningrobotics.scout_862.TeleopActivity.low_1;
import static com.lightningrobotics.scout_862.TeleopActivity.low_10;
import static com.lightningrobotics.scout_862.TeleopActivity.low_11;
import static com.lightningrobotics.scout_862.TeleopActivity.low_2;
import static com.lightningrobotics.scout_862.TeleopActivity.low_3;
import static com.lightningrobotics.scout_862.TeleopActivity.low_4;
import static com.lightningrobotics.scout_862.TeleopActivity.low_5;
import static com.lightningrobotics.scout_862.TeleopActivity.low_6;
import static com.lightningrobotics.scout_862.TeleopActivity.low_7;
import static com.lightningrobotics.scout_862.TeleopActivity.low_8;
import static com.lightningrobotics.scout_862.TeleopActivity.low_9;

/**
 * Created by khees on 2/11/2017.
 */

public class CycleData {
    public static int[][]lowCycleData;
    public static int[][]highCycleData;
    public static int cycleNum = 0;
    public static int maxCycleNum = 0;

    public static void makeCycleArrays()
    {
        lowCycleData = new int[10][maxY];
        highCycleData = new int [10][maxY];
    }

    public static void addToCycleArray(int dataByte, Boolean high) {
        if(dataByte != 0) {
            if (high == true)
                highCycleData[cycleNum][matchCounter] = dataByte;
            if (high == false)
                lowCycleData[cycleNum][matchCounter] = dataByte;
        }
    }

    public static double getHighAverage() {
        double average = 0;
        int cycleCount = 0;
        int total = 0;
        for (int y = 0; y < cycleNum; y++) {
            if (highCycleData[matchCounter][y] != 0) {
                total = highCycleData[y][matchCounter] + total;
                cycleCount++;
            }
        }

        if (cycleCount == 0)
            cycleCount = 1;

        average = (double) (total / cycleCount);
        return average;
    }

    public static double getLowAverage() {
        double average = 0;
        int cycleCount = 0;
        int total = 0;
        for (int y = 0; y < cycleNum; y++) {
            if (lowCycleData[y][matchCounter] != 0) {
                total = lowCycleData[y][matchCounter] + total;
                cycleCount++;
            }
        }

        if (cycleCount == 0)
            cycleCount = 1;

        average = (double) (total / cycleCount);
        return average;
    }

    public static void readLowAndHighArray()
    {
        low_0 = readRadioButton(low_0);
        low_1 = readRadioButton(low_1);
        low_2 = readRadioButton(low_2);
        low_3 = readRadioButton(low_3);
        low_4 = readRadioButton(low_4);
        low_5 = readRadioButton(low_5);
        low_6 = readRadioButton(low_6);
        low_7 = readRadioButton(low_7);
        low_8 = readRadioButton(low_8);
        low_9 = readRadioButton(low_9);
        low_10 = readRadioButton(low_10);
        low_11 = readRadioButton(low_11);
        high_0 = readRadioButton(high_0);
        high_1 = readRadioButton(high_1);
        high_2 = readRadioButton(high_2);
        high_3 = readRadioButton(high_3);
        high_4 = readRadioButton(high_4);
        high_5 = readRadioButton(high_5);
        high_6 = readRadioButton(high_6);
        high_7 = readRadioButton(high_7);
        high_8 = readRadioButton(high_8);
        high_9 = readRadioButton(high_9);
        high_10 = readRadioButton(high_10);
        high_11 = readRadioButton(high_11);
    }

    public static void writeLowAndHighArray()
    {
        writeRadioButton(low_0);
        writeRadioButton(low_1);
        writeRadioButton(low_2);
        writeRadioButton(low_3);
        writeRadioButton(low_4);
        writeRadioButton(low_5);
        writeRadioButton(low_6);
        writeRadioButton(low_7);
        writeRadioButton(low_8);
        writeRadioButton(low_9);
        writeRadioButton(low_10);
        writeRadioButton(low_11);
        writeRadioButton(high_0);
        writeRadioButton(high_1);
        writeRadioButton(high_2);
        writeRadioButton(high_3);
        writeRadioButton(high_4);
        writeRadioButton(high_5);
        writeRadioButton(high_6);
        writeRadioButton(high_7);
        writeRadioButton(high_8);
        writeRadioButton(high_9);
        writeRadioButton(high_10);
        writeRadioButton(high_11);
    }
}
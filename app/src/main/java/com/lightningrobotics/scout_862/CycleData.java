package com.lightningrobotics.scout_862;

import static com.lightningrobotics.scout_862.FileUtils.matchCounter;
import static com.lightningrobotics.scout_862.FileUtils.maxY;

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

        for(int x = 0; x < lowCycleData.length; x++)
        {
            for(int y = 0; y < lowCycleData[0].length; y++)
                lowCycleData[x][y] = 0;
        }

        for(int x = 0; x < highCycleData.length; x++)
        {
            for(int y = 0; y < highCycleData[0].length; y++)
                highCycleData[x][y] = 0;
        }
    }

    public static void addToCycleArray(int dataByte, Boolean high) {
        if (high == true)
            highCycleData[cycleNum][matchCounter] = dataByte;
        if (high == false)
            lowCycleData[cycleNum][matchCounter] = dataByte;
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
}
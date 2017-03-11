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
    public static int maxCycleNum = 10;

    public static void makeCycleArrays()
    {
        lowCycleData = new int[maxCycleNum][maxY];
        highCycleData = new int [maxCycleNum][maxY];

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
        System.out.println("Add to cycle dataByte is " + dataByte);
        if (high == true)
            highCycleData[cycleNum][matchCounter] = dataByte;
        if (high == false)
            lowCycleData[cycleNum][matchCounter] = dataByte;
    }

    public static double getHighAverage() {
        double average = 0;
        int cycleCount = 1;
        int total = 0;
        for (int y = 0; y < cycleNum; y++) {
               if (highCycleData[y][matchCounter] != 0) {
                total = highCycleData[y][matchCounter] + total;
                cycleCount++;
            }
        }

        average = (double) (total / cycleCount);
        return average;
    }

    public static double getLowAverage() {
        double average = 0;
        int cycleCount = 1;
        int total = 0;
        for (int y = 0; y < cycleNum; y++) {
            if (lowCycleData[y][matchCounter] != 0) {
                total = lowCycleData[y][matchCounter] + total;
                cycleCount++;
            }
        }

        average = (double) (total / cycleCount);
        return average;
    }
}
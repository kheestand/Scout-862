package com.lightningrobotics.scout_862;

import android.content.Context;
import 	android.content.res.Resources;
/**
 * Created by khees on 1/1/2017.
 */

public class ImageUtils {

    private int imgId;
    private Context context;

    public ImageUtils()
    {
        imgId = 0;
    }

    public ImageUtils(Context current)
    {
        this.context = current;
    }

    public int getImageId()
    {
        return imgId;
    }

    public int getDefaultImageId()
    {
        return context.getResources().getIdentifier("default_robot", "drawable"
                , FullscreenActivity.PACKAGE_NAME);
    }

    public void setImageId(int teamNum)
    {
        imgId = context.getResources().getIdentifier("robot_" + teamNum, "drawable"
                , FullscreenActivity.PACKAGE_NAME);

    }

    public void setImageId(String teamNum)
    {
        imgId = context.getResources().getIdentifier("robot_" + teamNum, "drawable"
                , FullscreenActivity.PACKAGE_NAME);
    }
}

package com.lightningrobotics.scout_862;

import android.content.Context;
import 	android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;

import static android.support.v4.content.ContextCompat.getDrawable;
import static java.security.AccessController.getContext;

/**
 * Created by khees on 1/1/2017.
 */

public class ImageUtils {

    private Context context;

    public ImageUtils(Context current)
    {
        this.context = current;
    }

    public Bitmap getDefaultImage()
    {
        return BitmapFactory.decodeResource(context.getResources(), R.drawable.default_robot);
    }

    public Bitmap getTeamPicture(int teamNum)
    {
        return BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/Pictures/" + "robot_" + teamNum + ".jpg");
    }

    public Bitmap getTeamPicture(String teamNum)
    {
        return BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/Pictures/" + "robot_" + teamNum + ".jpg");
    }

    public String getTeamPicturePath(int teamNum)
    {
        return Environment.getExternalStorageDirectory() + "/Pictures/" + "robot_" + teamNum + ".jpg";
    }

    public String getTeamPicturePath(String teamNum)
    {
        return Environment.getExternalStorageDirectory() + "/Pictures/" + "robot_" + teamNum + ".jpg";
    }
}

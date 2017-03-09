package com.lightningrobotics.scout_862;

import android.content.Context;
import 	android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;

import java.io.File;

import static android.support.v4.content.ContextCompat.getDrawable;
import static java.security.AccessController.getContext;

/**
 * Created by khees on 1/1/2017.
 */

public class ImageUtils {

    private Context context;
    private String fileExtention = ".jpg";
    private String[] validFileExtentions = {".jpg","jpeg",".png",".bmp",".gif"};

    public ImageUtils(Context current)
    {
        this.context = current;
    }

    public Bitmap getDefaultImage()
    {
        return BitmapFactory.decodeResource(context.getResources(), R.drawable.default_robot);
    }

    public File getFile(String teamNum)
    {
        for(int i = 0; i < validFileExtentions.length; i++)
        {
            String path = Environment.getExternalStorageDirectory() + "/Pictures/" + "robot_" + teamNum + validFileExtentions[i];
            File picture = new File(path);
            if(picture.exists())
                return picture;
        }
        return null;
    }
}

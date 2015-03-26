package dev.claudiordgz.instragramexample.util;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.util.Pair;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by Claudiordgz on 1/24/2015.
 */
public class OrientationHelper {

    /** Return a pair with the Configuration.Orientation and
     the screen's width */
    public static Pair<Integer, Integer> getScreenOrientationAndHeight(Context ctx)
    {
        WindowManager wm = (WindowManager) ctx
                .getSystemService(Context.WINDOW_SERVICE);
        Display getOrient = wm.getDefaultDisplay();
        Point size = new Point();
        getOrient.getSize(size);
        int orientation = Configuration.ORIENTATION_UNDEFINED;
        if(size.x < size.y){
            orientation = Configuration.ORIENTATION_PORTRAIT;
        }else {
            orientation = Configuration.ORIENTATION_LANDSCAPE;
        }
        return new Pair<>(orientation, size.y);
    }
}

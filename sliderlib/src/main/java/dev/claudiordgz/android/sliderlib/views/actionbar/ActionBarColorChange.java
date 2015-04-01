package dev.claudiordgz.android.sliderlib.views.actionbar;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.support.v7.app.ActionBar;


/**
 * Created by Android1 on 1/28/2015.
 */
public class ActionBarColorChange {
    private final android.os.Handler handler = new android.os.Handler();
    private ActionBar mActionBar;
    private Drawable oldBackground = null;

    public void setActionBar(ActionBar actionBar) {
        mActionBar = actionBar;
    }

    private Drawable.Callback drawableCallback = new Drawable.Callback() {
        @Override
        public void invalidateDrawable(Drawable who) {
            ActionBar actionBar = mActionBar;
            if(actionBar == null){
                throw new NullPointerException("Callback ActionBar is null");
            }
            actionBar.setBackgroundDrawable(who);
        }

        @Override
        public void scheduleDrawable(Drawable who, Runnable what, long when) {
            handler.postAtTime(what, when);
        }

        @Override
        public void unscheduleDrawable(Drawable who, Runnable what) {
            handler.removeCallbacks(what);
        }
    };

    private void setCallback(Drawable drawable, ActionBar abr){
        // workaround for broken ActionBarContainer drawable handling on
        // pre-API 17 builds
        // https://github.com/android/platform_frameworks_base/commit/a7cc06d82e45918c37429a59b14545c6a57db4e4
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            drawable.setCallback(drawableCallback);
        } else {
            abr.setBackgroundDrawable(drawable);
        }
    }

    // change ActionBar color just if an ActionBar is available
    public void changeColor(int newColor, Drawable bottomDrawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

            Drawable colorDrawable = new ColorDrawable(newColor);
            LayerDrawable ld = new LayerDrawable(new Drawable[] { colorDrawable, bottomDrawable });
            if(mActionBar == null){
                throw new NullPointerException("Callback ActionBar is null");
            }
            if (oldBackground == null) {
                setCallback(ld, mActionBar);
            } else {
                TransitionDrawable td = new TransitionDrawable(new Drawable[] { oldBackground, ld });
                setCallback(td, mActionBar);
                td.startTransition(200);
            }
            oldBackground = ld;
            // http://stackoverflow.com/questions/11002691/actionbar-setbackgrounddrawable-nulling-background-from-thread-handler
            mActionBar.setDisplayShowTitleEnabled(false);
            mActionBar.setDisplayShowCustomEnabled(true);
        }
    }
}

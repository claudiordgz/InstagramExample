package dev.claudiordgz.instragramexample.listeners;

import android.content.ClipData;
import android.text.method.Touch;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Claudio on 3/26/2015.
 */
public class TouchListener implements View.OnTouchListener {
    private String TAG = this.getClass().getName();
    private float mDownX;
    private float mDownY;
    private boolean isOnClick;

    public TouchListener() {}

    public boolean onTouch(View view, MotionEvent ev) {
        final float SCROLL_THRESHOLD = 10;
        switch (ev.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getX();
                mDownY = ev.getY();
                isOnClick = true;
                break;
            case MotionEvent.ACTION_CANCEL:
                view.setAlpha(1);
                break;
            case MotionEvent.ACTION_UP:
                view.setAlpha(1);
                break;
            case MotionEvent.ACTION_MOVE:
                if (isOnClick && (Math.abs(mDownX - ev.getX()) > SCROLL_THRESHOLD || Math.abs(mDownY - ev.getY()) > SCROLL_THRESHOLD)) {
                    Log.d(TAG, "movement");
                    isOnClick = false;
                    ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                    view.startDrag(data, shadowBuilder, view, 0);
                    view.setAlpha(.5f);
                    return true;
                }
                break;
            default: break;
        }
        return false;
    }
}

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
        switch (ev.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                return ActionDown(ev);
            case MotionEvent.ACTION_CANCEL:
                return ActionCancel(view);
            case MotionEvent.ACTION_UP:
                return ActionUp(view);
            case MotionEvent.ACTION_MOVE:
                return ActionMove(view, ev);
        }
        return false;
    }

    private boolean ActionDown(MotionEvent ev){
        mDownX = ev.getX();
        mDownY = ev.getY();
        isOnClick = true;
        return false;
    }

    private boolean ActionCancel(View view){
        view.setAlpha(1);
        return false;
    }

    private boolean ActionUp(View view){
        view.setAlpha(1);
        return false;
    }

    private boolean ActionMove(View view, MotionEvent ev){
        float SCROLL_THRESHOLD = 10;
        if (isOnClick && (Math.abs(mDownX - ev.getX()) > SCROLL_THRESHOLD || Math.abs(mDownY - ev.getY()) > SCROLL_THRESHOLD)) {
            Log.d(TAG, "movement");
            isOnClick = false;
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(data, shadowBuilder, view, 0);
            view.setAlpha(.5f);
            return true;
        }
        return false;
    }

}

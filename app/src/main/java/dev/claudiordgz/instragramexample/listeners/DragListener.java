package dev.claudiordgz.instragramexample.listeners;

import android.content.Context;
import android.graphics.Color;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Claudio on 3/26/2015.
 */
public class DragListener implements View.OnDragListener {

    private Context context;
    private int smallSize;
    private int bigSize;

    public DragListener(Context ctx, int bSize, int sSize){
        context = ctx;
        bigSize = bSize;
        smallSize = sSize;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED: break;
            case DragEvent.ACTION_DRAG_ENTERED:
                ChangeBackgroundPadding(v, Color.GREEN, 2);
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                ChangeBackgroundPadding(v, Color.TRANSPARENT, 0);
                break;
            case DragEvent.ACTION_DROP:
                ChangeBackgroundPadding(v, Color.TRANSPARENT, 0);
                SwitchImages(v, event);
                break;
            case DragEvent.ACTION_DRAG_ENDED:
            default:
                break;
        }
        return true;
    }

    private void SwitchImages(View v, DragEvent event){
        View view = (View) event.getLocalState();
        ImageView container = (ImageView) v;
        String sourceUrl = view.getTag().toString(), containerUrl = container.getTag().toString();
        int sourceSize = view.getHeight(), containerSize = container.getHeight();
        //Swap Images
        if(containerSize > smallSize + 50) {
            AlternateImage(sourceUrl, bigSize, container);
        } else {
            AlternateImage(sourceUrl, smallSize, container);
        }

        if(sourceSize > smallSize + 50) {
            AlternateImage(containerUrl, bigSize, (ImageView) view);
        } else {
            AlternateImage(containerUrl, smallSize, (ImageView)view);
        }
        container.setTag(sourceUrl);
        view.setTag(containerUrl);
    }

    private void AlternateImage(String containerUrl, int size, ImageView view){
        Picasso.with(context)
                .load(containerUrl)
                .resize(size, size)
                .centerCrop()
                .into(view);
    }

    private void ChangeBackgroundPadding(View v, int color, int padding){
        v.setBackgroundColor(color);
        v.setPadding(padding,padding,padding,padding);
    }

}
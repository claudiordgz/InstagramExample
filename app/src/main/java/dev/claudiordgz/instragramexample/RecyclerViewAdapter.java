package dev.claudiordgz.instragramexample;


import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Claudio on 3/25/2015.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ImageHolder> implements View.OnClickListener {

    public static class ImageHolder extends RecyclerView.ViewHolder {

        public ImageView bigImage, firstSmallImage, secondSmallImage;

        public ImageHolder(View imageView) {
            super(imageView);
            bigImage = (ImageView) itemView.findViewById(R.id.bigImage);
            firstSmallImage = (ImageView) itemView.findViewById(R.id.firstSmallImage);
            secondSmallImage = (ImageView) itemView.findViewById(R.id.secondSmallImage);
        }
    }
    private ArrayList<ViewHolderModel> mData = new ArrayList<>();
    private Context context;
    private ImageView referenceFullImage;
    private int bigSize;
    private int smallSize;

    public RecyclerViewAdapter(Context ctx, ImageView reference) {
        context = ctx;
        referenceFullImage = reference;
        Pair<Integer, Integer> sizes = OrientationHelper.getScreenOrientationAndHeight(ctx);
        bigSize = sizes.second/3;
        smallSize = (bigSize)/2;
    }

    public void updateList(ArrayList<ViewHolderModel> data) {
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mData == null) return 0;
        return mData.size();
    }

    @Override
    public RecyclerViewAdapter.ImageHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.view_holder_layout, viewGroup, false);
        return new RecyclerViewAdapter.ImageHolder(itemView);
    }

    public void onClick(View V) {
        ImageView clicked = (ImageView) V;
        double size = bigSize * 1.8;
        Picasso.with(context)
                .load(clicked.getTag().toString())
                .resize((int)size, (int)size)
                .centerCrop()
                .into(referenceFullImage);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ImageHolder viewHolder, int position) {

        int[] sizes = {bigSize, smallSize, smallSize};
        ImageView[] models = { viewHolder.bigImage, viewHolder.firstSmallImage, viewHolder.secondSmallImage };
        String[] imageUrls = {mData.get(position).getBigImage(), mData.get(position).getFirstSmallImage(), mData.get(position).getSecondSmallImage()};
        for(int i = 0; i != models.length; ++i) {
            Picasso.with(context)
                    .load(imageUrls[i])
                    .resize(sizes[i], sizes[i])
                    .centerCrop()
                    .into(models[i]);

            models[i].setOnClickListener(this);
            models[i].setTag(imageUrls[i]);
            models[i].setOnTouchListener(new MyTouchListener());
            models[i].setOnDragListener(new MyDragListener());
        }
    }

    private float mDownX;
    private float mDownY;
    private final float SCROLL_THRESHOLD = 10;
    private boolean isOnClick;

    // This defines your touch listener
    private final class MyTouchListener implements View.OnTouchListener {
        private String TAG = this.getClass().getName();
        public boolean onTouch(View view, MotionEvent ev) {

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
                    if (isOnClick) {
                        Log.d(TAG, "onClick ");
                    }
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

    class MyDragListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED: break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackgroundColor(Color.GREEN);
                    v.setPadding(2,2,2,2);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundColor(Color.TRANSPARENT);
                    v.setPadding(0,0,0,0);
                    break;
                case DragEvent.ACTION_DROP:
                    v.setBackgroundColor(Color.TRANSPARENT);
                    v.setPadding(0,0,0,0);
                    View view = (View) event.getLocalState();
                    ImageView container = (ImageView) v;
                    String sourceUrl = view.getTag().toString();
                    String containerUrl = container.getTag().toString();
                    int sourceSize = view.getHeight();
                    int containerSize = container.getHeight();
                    //Swap Images
                    if(containerSize > smallSize + 50) {
                        Picasso.with(context)
                                .load(sourceUrl)
                                .resize(bigSize, bigSize)
                                .centerCrop()
                                .into(container);
                    }
                    else{
                        Picasso.with(context)
                                .load(sourceUrl)
                                .resize(smallSize, smallSize)
                                .centerCrop()
                                .into(container);
                    }

                    if(sourceSize > smallSize + 50) {
                        Picasso.with(context)
                                .load(containerUrl)
                                .resize(bigSize, bigSize)
                                .centerCrop()
                                .into((ImageView)view);
                    }
                    else{
                        Picasso.with(context)
                                .load(containerUrl)
                                .resize(smallSize, smallSize)
                                .centerCrop()
                                .into((ImageView)view);
                    }
                    container.setTag(sourceUrl);
                    view.setTag(containerUrl);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                default:
                    break;
            }
            return true;
        }
    }

    public void addItem(int position, ViewHolderModel data) {
        mData.add(data);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

}


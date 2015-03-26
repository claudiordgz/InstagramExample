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
import android.widget.ProgressBar;
import android.widget.ViewAnimator;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import dev.claudiordgz.instragramexample.listeners.DragListener;
import dev.claudiordgz.instragramexample.listeners.TouchListener;

/**
 * Created by Claudio on 3/25/2015.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ImageHolder> implements View.OnClickListener {

    public static class ImageHolder extends RecyclerView.ViewHolder {

        public ImageView bigImage, firstSmallImage, secondSmallImage;
        public ViewAnimator mViewAnimatorBigImage, mViewAnimatorFirstSmallImage, mViewAnimatorSecondSmallImage;

        public ImageHolder(View imageView) {
            super(imageView);
            bigImage = (ImageView) itemView.findViewById(R.id.bigImage);
            firstSmallImage = (ImageView) itemView.findViewById(R.id.firstSmallImage);
            secondSmallImage = (ImageView) itemView.findViewById(R.id.secondSmallImage);
            mViewAnimatorBigImage = (ViewAnimator) itemView.findViewById(R.id.animatorBigImage);
            mViewAnimatorFirstSmallImage = (ViewAnimator) itemView.findViewById(R.id.animatorFirstSmallImage);
            mViewAnimatorSecondSmallImage = (ViewAnimator) itemView.findViewById(R.id.animatorSecondSmallImage);
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
        final ViewAnimator[] animators = {viewHolder.mViewAnimatorBigImage, viewHolder.mViewAnimatorFirstSmallImage,
            viewHolder.mViewAnimatorSecondSmallImage};
        String[] imageUrls = {mData.get(position).getBigImage(), mData.get(position).getFirstSmallImage(), mData.get(position).getSecondSmallImage()};
        for(int i = 0; i != models.length; ++i) {
            animators[i].setDisplayedChild(1);
            final int index = i;
            Picasso.with(context)
                    .load(imageUrls[i])
                    .resize(sizes[i], sizes[i])
                    .centerCrop()
                    .into(models[i], new Callback.EmptyCallback(){
                        @Override public void onSuccess() {
                            animators[index].setDisplayedChild(0);
                        }
                    });

            models[i].setOnClickListener(this);
            models[i].setTag(imageUrls[i]);
            models[i].setOnTouchListener(new TouchListener());
            models[i].setOnDragListener(new DragListener(context, bigSize, smallSize));
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


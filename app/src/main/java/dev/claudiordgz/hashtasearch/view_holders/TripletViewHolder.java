package dev.claudiordgz.hashtasearch.view_holders;

import android.util.Pair;
import android.view.View;
import android.widget.ImageView;

import dev.claudiordgz.android.sliderlib.common.OrientationHelper;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectViews;
import dev.claudiordgz.hashtasearch.R;
import dev.claudiordgz.hashtasearch.model.TripletImages;
import uk.co.ribot.easyadapter.ItemViewHolder;
import uk.co.ribot.easyadapter.PositionInfo;
import uk.co.ribot.easyadapter.annotations.LayoutId;

/**
 * Created by Claudio on 3/30/2015.
 */
@LayoutId(R.layout.triplet_cardview)
public class TripletViewHolder extends ItemViewHolder<TripletImages> {

    @InjectViews({R.id.bigImage, R.id.firstSmallImage, R.id.secondSmallImage})
    List<ImageView> mImageList;

    private int bigSize;
    private int smallSize;

    public TripletViewHolder(View view) {
        super(view);
        ButterKnife.inject(this, view);
        Pair<Integer, Integer> sizes = OrientationHelper.getScreenOrientationAndSize(getContext()).second;
        bigSize = sizes.first/3;
        smallSize = (bigSize)/2;
    }

    @Override
    public void onSetValues(TripletImages images, PositionInfo positionInfo) {
        mImageList.get(0).setTag(images.getBigImage().imageUrl);
        mImageList.get(1).setTag(images.getFirstSmallImage().imageUrl);
        mImageList.get(2).setTag(images.getSecondSmallImage().imageUrl);
        int[] sizes = {bigSize, smallSize, smallSize};
        for(int i = 0; i != mImageList.size(); ++i) {
                Picasso.with(getContext())
                        .load(mImageList.get(i).getTag().toString())
                        .resize(sizes[i], sizes[i])
                        .centerInside()
                        .into(mImageList.get(i));
        }
    }
}

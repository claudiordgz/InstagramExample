package dev.claudiordgz.hashtasearch.view_holders;

import android.util.Pair;
import android.view.View;

import dev.claudiordgz.adaptablecard.ItemCard;
import dev.claudiordgz.android.sliderlib.common.OrientationHelper;

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
    List<ItemCard> mImageList;

    private int bigSizeWidth;
    private int bigSizeHeight;
    private int smallSizeWidth;
    private int smallSizeHeight;

    public TripletViewHolder(View view) {
        super(view);
        ButterKnife.inject(this, view);
        Pair<Integer, Integer> sizes = OrientationHelper.getScreenOrientationAndSize(getContext()).second;
        bigSizeWidth = sizes.first/3;
        bigSizeHeight = ((sizes.first/3)/4)*5;
        smallSizeWidth = (bigSizeWidth)/2;
        smallSizeHeight = bigSizeHeight/2;
    }

    @Override
    public void onSetValues(TripletImages images, PositionInfo positionInfo) {
        mImageList.get(0).SetFromInstagramData(images.getBigImage(), bigSizeWidth, bigSizeHeight);
        mImageList.get(1).SetFromInstagramData(images.getFirstSmallImage(), smallSizeWidth, smallSizeHeight);
        mImageList.get(2).SetFromInstagramData(images.getSecondSmallImage(), smallSizeWidth, smallSizeHeight);
    }
}

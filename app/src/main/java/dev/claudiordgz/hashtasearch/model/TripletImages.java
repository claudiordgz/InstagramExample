package dev.claudiordgz.hashtasearch.model;

import dev.claudiordgz.common.model.InstagramData;

/**
 * Created by Claudio on 3/25/2015.
 */
public class TripletImages {

    private InstagramData mBigImage;
    private InstagramData mFirstSmallImage;
    private InstagramData mSecondSmallImage;

    public TripletImages() {}

    public TripletImages(InstagramData big, InstagramData firstSmall, InstagramData secondSmall) {
        setBigImage(big);
        setFirstSmallImage(firstSmall);
        setSecondSmallImage(secondSmall);
    }

    public InstagramData getBigImage() {
        return mBigImage;
    }

    public void setBigImage(InstagramData mBigImage) {
        this.mBigImage = mBigImage;
    }

    public InstagramData getFirstSmallImage() {
        return mFirstSmallImage;
    }

    public void setFirstSmallImage(InstagramData mFirstSmallImage) {
        this.mFirstSmallImage = mFirstSmallImage;
    }

    public InstagramData getSecondSmallImage() {
        return mSecondSmallImage;
    }

    public void setSecondSmallImage(InstagramData mSecondSmallImage) {
        this.mSecondSmallImage = mSecondSmallImage;
    }
}

package dev.claudiordgz.hashtasearch.model;

import java.util.ArrayList;

import dev.claudiordgz.common.model.InstagramData;

/**
 * Created by Claudio on 3/30/2015.
 */
public class TripletImagesManager {

    private ArrayList<TripletImages> mImageList = new ArrayList<>();

    public enum ImageType {
        BIG_IMAGE,
        FIRST_SMALL_IMAGE,
        SECOND_SMALL_IMAGE
    }
    ImageType type = ImageType.BIG_IMAGE;
    TripletImages triplet = null;
    boolean reset = false;


    public void add(InstagramData data){
        if(reset) {
            mImageList.clear();
            reset = false;
        }
        switch (type){
            case BIG_IMAGE:
                triplet = new TripletImages();
                triplet.setBigImage(data);
                type = ImageType.FIRST_SMALL_IMAGE;
                break;
            case FIRST_SMALL_IMAGE:
                triplet.setFirstSmallImage(data);
                type = ImageType.SECOND_SMALL_IMAGE;
                break;
            case SECOND_SMALL_IMAGE:
                triplet.setSecondSmallImage(data);
                type = ImageType.BIG_IMAGE;
                mImageList.add(triplet);
                break;
        }
    }

    public ArrayList<TripletImages> getImageList() {
        reset = true;
        return mImageList;
    }

}

package dev.claudiordgz.instragramexample;

/**
 * Created by Claudio on 3/25/2015.
 */
public class ViewHolderModel {

    private String mBigImage;
    private String mFirstSmallImage;
    private String mSecondSmallImage;

    ViewHolderModel(String big, String firstSmall, String secondSmall) {
        mBigImage = big.replace("\"", "");
        mFirstSmallImage = firstSmall.replace("\"", "");
        mSecondSmallImage = secondSmall.replace("\"", "");
    }


    public String getBigImage() {
        return mBigImage;
    }

    public void setBigImage(String mBigImage) {
        this.mBigImage = mBigImage;
    }

    public String getFirstSmallImage() {
        return mFirstSmallImage;
    }

    public void setFirstSmallImage(String mFirstSmallImage) {
        this.mFirstSmallImage = mFirstSmallImage;
    }

    public String getSecondSmallImage() {
        return mSecondSmallImage;
    }

    public void setSecondSmallImage(String mSecondSmallImage) {
        this.mSecondSmallImage = mSecondSmallImage;
    }
}

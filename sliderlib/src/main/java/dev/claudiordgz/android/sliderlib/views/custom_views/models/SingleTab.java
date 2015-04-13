package dev.claudiordgz.android.sliderlib.views.custom_views.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This class represents a tab to be displayed by implements Parcelable and it's associated.
 */
public class SingleTab implements Parcelable {
  private final String mTitle;
  private final int mIndicatorColor;
  private final int mDividerColor;
  private final int mBackgroundColor;
  private final int mStatusBarColor;

  public SingleTab(String title, int indicatorColor, int dividerColor, int backgroundColor, int statusBarColor) {
    mTitle = title;
    mIndicatorColor = indicatorColor;
    mDividerColor = dividerColor;
    mBackgroundColor = backgroundColor;
    mStatusBarColor = statusBarColor;
  }

  /**
   * @return the title which represents this tab. In this sample this is used directly by
   */
  public CharSequence getTitle() {
    return mTitle;
  }

  /**
   * @return the color to be used for indicator on the {link SlidingTabLayout}
   */
  public int getIndicatorColor() {
    return mIndicatorColor;
  }

  /**
   * @return the color to be used for right divider on the {link com.example.android.common.view.action_bar.SlidingTabLayout}
   */
  public int getDividerColor() {
    return mDividerColor;
  }

  public int getBackgroundColor() {
    return mBackgroundColor;
  }

  public int getStatusBarColor() {
    return mStatusBarColor;
  }

  protected SingleTab(Parcel in) {
    mTitle = in.readString();
    mIndicatorColor = in.readInt();
    mDividerColor = in.readInt();
    mBackgroundColor = in.readInt();
    mStatusBarColor = in.readInt();
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(mTitle);
    dest.writeInt(mIndicatorColor);
    dest.writeInt(mDividerColor);
    dest.writeInt(mBackgroundColor);
    dest.writeInt(mStatusBarColor);
  }

  @SuppressWarnings("unused")
  public static final Creator<SingleTab> CREATOR = new Creator<SingleTab>() {
    @Override
    public SingleTab createFromParcel(Parcel in) {
      return new SingleTab(in);
    }

    @Override
    public SingleTab[] newArray(int size) {
      return new SingleTab[size];
    }
  };
}
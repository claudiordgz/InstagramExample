package dev.claudiordgz.android.sliderlib.views.custom_views.models;

/**
 * Created by Android1 on 1/27/2015.
 */
public class CTabColorizer implements TabColorizer {
  private int[] mIndicatorColors;
  private int[] mDividerColors;
  private int[] mBackgroundColors;
  private int[] mStatusBarColors;

  @Override
  public final int getIndicatorColor(int position) {
    return mIndicatorColors[position % mIndicatorColors.length];
  }

  @Override
  public final int getDividerColor(int position) {
    return mDividerColors[position % mDividerColors.length];
  }

  @Override
  public final int getBackgroundColor(int position) {
    return mBackgroundColors[position % mBackgroundColors.length];
  }

  @Override
  public final int getStatusBarColor(int position) {
    return mStatusBarColors[position % mStatusBarColors.length];
  }

  public void setIndicatorColors(int... colors) {
    mIndicatorColors = colors;
  }

  public void setDividerColors(int... colors) {
    mDividerColors = colors;
  }

  public void setBackgroundColor(int... colors) {
    mBackgroundColors = colors;
  }

  public void setStatusBarColors(int... colors) {
    mStatusBarColors = colors;
  }

}
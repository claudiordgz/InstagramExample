package dev.claudiordgz.android.sliderlib.views.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.support.v4.app.FragmentStatePagerAdapter;

import dev.claudiordgz.android.sliderlib.fragments.TraineeCardFragment;
import dev.claudiordgz.android.sliderlib.views.custom_views.models.SingleTab;

import java.util.ArrayList;
import java.util.List;


public class TraineeCardAdapter extends FragmentStatePagerAdapter {

  private List<SingleTab> mTabs = new ArrayList<>();

  public TraineeCardAdapter(FragmentManager fm) {
    super(fm);
  }

  public void setTabs(List<SingleTab> tabs) {
    mTabs = tabs;
  }

  /**
   * Return the {@link android.support.v4.app.Fragment} to be displayed at {@code position}.
   * <p/>
   * Here we return the value returned from
   */
  @Override
  public Fragment getItem(int i) {
    return TraineeCardFragment.newInstance(i);
  }

  @Override
  public int getCount() {
    return mTabs.size();
  }


  @Override
  public CharSequence getPageTitle(int position) {
    return mTabs.get(position).getTitle();
  }
  // END_INCLUDE (pageradapter_getpagetitle)

}

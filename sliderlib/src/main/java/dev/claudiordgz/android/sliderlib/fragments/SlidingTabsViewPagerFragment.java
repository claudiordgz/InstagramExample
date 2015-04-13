package dev.claudiordgz.android.sliderlib.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dev.claudiordgz.android.sliderlib.R;
import dev.claudiordgz.android.sliderlib.views.custom_views.SlidingTabLayout;
import dev.claudiordgz.android.sliderlib.views.custom_views.models.ListOfTabs;
import dev.claudiordgz.android.sliderlib.views.custom_views.models.SingleTab;
import dev.claudiordgz.android.sliderlib.views.custom_views.models.TabColorizer;
import dev.claudiordgz.android.sliderlib.views.listeners.ColorChangeListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A basic sample which shows how to use
 * to display a custom {@link ViewPager} title strip which gives continuous feedback to the user
 * when scrolling.
 */
public class SlidingTabsViewPagerFragment extends Fragment {

  public static final String TAB_ARGUMENTS = "SlidingTabsColorsFragment.TabList";

  /**
   * A custom {@link ViewPager} title strip which looks much like Tabs present in Android v4.0 and
   * above, but is designed to give continuous feedback to the user when scrolling.
   */
  private SlidingTabLayout mSlidingTabLayout;
  private ColorChangeListener mColorListener;
  /**
   * A {@link ViewPager} which will be used in conjunction with the {@link SlidingTabLayout} above.
   */
  private ViewPager mViewPager;

  private List<SingleTab> mTabs = new ArrayList<>();

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  public void setColorListener(ColorChangeListener colorListener) {
    mColorListener = colorListener;
  }

  /**
   * Inflates the {@link android.view.View} which will be displayed by this {@link Fragment}, from the app's
   * resources.
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    Bundle bundle = getArguments();
    ListOfTabs tabs = bundle.getParcelable(TAB_ARGUMENTS);
    mTabs = tabs.getTabs();
    return inflater.inflate(R.layout.sliding_tab_layout, container, false);
  }

  public void setViewPager(ViewPager pager) {
    mViewPager = pager;
  }

  // BEGIN_INCLUDE (fragment_onviewcreated)

  /**
   * This is called after the {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)} has finished.
   * Here we can pick out the {@link View}s we need to configure from the content view.
   * <p/>
   * We set the {@link ViewPager}'s adapter to be an instance of
   * . The {@link SlidingTabLayout} is then given the
   * {@link ViewPager} so that it can populate itself.
   *
   * @param view View created in {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
   */
  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {

    // BEGIN_INCLUDE (setup_slidingtablayout)
    // Give the SlidingTabLayout the ViewPager, this must be done AFTER the ViewPager has had
    // it's PagerAdapter set.
    mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
    mSlidingTabLayout.setColorListener(mColorListener);
    mSlidingTabLayout.setViewPager(mViewPager);

    // END_INCLUDE (tab_colorizer)
    // END_INCLUDE (setup_slidingtablayout)
  }
  // END_INCLUDE (fragment_onviewcreated)

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    // BEGIN_INCLUDE (tab_colorizer)
    // Set a TabColorizer to customize the indicator and divider colors. Here we just retrieve
    // the tab at the position, and return it's set color
    mSlidingTabLayout.setCustomTabColorizer(new TabColorizer() {

      @Override
      public int getIndicatorColor(int position) {
        return mTabs.get(position).getIndicatorColor();
      }

      @Override
      public int getDividerColor(int position) {
        return mTabs.get(position).getDividerColor();
      }


      @Override
      public int getBackgroundColor(int position) {
        return mTabs.get(position).getBackgroundColor();
      }

      @Override
      public int getStatusBarColor(int position) {
        return mTabs.get(position).getStatusBarColor();
      }

    });
  }
}

package dev.claudiordgz.android.sliderlib.views.listeners;

import android.support.v4.view.ViewPager;
import android.view.View;

import dev.claudiordgz.android.sliderlib.common.ICommand;
import dev.claudiordgz.android.sliderlib.views.custom_views.SlidingTabStrip;


public class InternalViewPagerListener implements ViewPager.OnPageChangeListener {
    private int mScrollState;
    private SlidingTabStrip mTabStrip;
    private ICommand mScrollToTabAction;
    private ViewPager.OnPageChangeListener mViewPagerPageChangeListener;

    public void setup(SlidingTabStrip tabStrip, ICommand scrollToTab,
                      ViewPager.OnPageChangeListener listener) {
        setTabStrip(tabStrip);
        setScrollToTabAction(scrollToTab);
        setViewPagerPageChangeListener(listener);
    }

    public void setTabStrip(SlidingTabStrip tabStrip) {
        mTabStrip = tabStrip;
    }

    public void setScrollToTabAction(ICommand scrollToTab) {
        mScrollToTabAction = scrollToTab;
    }

    public void setViewPagerPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mViewPagerPageChangeListener = listener;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int tabStripChildCount = mTabStrip.getChildCount();
        if ((tabStripChildCount == 0) || (position < 0) || (position >= tabStripChildCount)) {
            return;
        }

        mTabStrip.onViewPagerPageChanged(position, positionOffset);

        View selectedTitle = mTabStrip.getChildAt(position);
        int extraOffset = (selectedTitle != null)
                ? (int) (positionOffset * selectedTitle.getWidth())
                : 0;
        mScrollToTabAction.apply(position, extraOffset);

        if (mViewPagerPageChangeListener != null) {
            mViewPagerPageChangeListener.onPageScrolled(position, positionOffset,
                    positionOffsetPixels);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        mScrollState = state;
        if (mViewPagerPageChangeListener != null) {
            mViewPagerPageChangeListener.onPageScrollStateChanged(state);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (mScrollState == ViewPager.SCROLL_STATE_IDLE) {
            mTabStrip.onViewPagerPageChanged(position, 0f);
            mScrollToTabAction.apply(position, 0);
        }

        if (mViewPagerPageChangeListener != null) {
            mViewPagerPageChangeListener.onPageSelected(position);
        }
    }

}
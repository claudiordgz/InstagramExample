package dev.claudiordgz.android.sliderlib.views.custom_views;

import android.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import dev.claudiordgz.android.sliderlib.common.ColorChanger;
import dev.claudiordgz.android.sliderlib.views.custom_views.models.CTabColorizer;
import dev.claudiordgz.android.sliderlib.views.custom_views.models.TabColorizer;
import dev.claudiordgz.android.sliderlib.views.listeners.ColorChangeListener;

public class SlidingTabStrip extends LinearLayout {

    private static final int DEFAULT_BOTTOM_BORDER_THICKNESS_DIPS = 2;
    private static final byte DEFAULT_BOTTOM_BORDER_COLOR_ALPHA = 0x26;
    private static final int SELECTED_INDICATOR_THICKNESS_DIPS = 8;
    private static final int DEFAULT_SELECTED_INDICATOR_COLOR = 0xFF33B5E5;

    private static final int DEFAULT_DIVIDER_THICKNESS_DIPS = 1;
    private static final byte DEFAULT_DIVIDER_COLOR_ALPHA = 0x20;
    private static final float DEFAULT_DIVIDER_HEIGHT = 0.5f;

    private final int mBottomBorderThickness;
    private final Paint mBottomBorderPaint;

    private final int mSelectedIndicatorThickness;
    private final Paint mSelectedIndicatorPaint;

    private final Paint mDividerPaint;
    private final float mDividerHeight;

    private int mSelectedPosition;
    private float mSelectionOffset;

    private TabColorizer mCustomTabColorizer;
    private final CTabColorizer mDefaultTabColorizer;
    private ColorChangeListener mColorListener;

    public void setColorListener(ColorChangeListener colorListener){
        mColorListener = colorListener;
    }

    public SlidingTabStrip(Context context) {
        this(context, null);
    }

    SlidingTabStrip(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);

        final float density = getResources().getDisplayMetrics().density;

        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorForeground, outValue, true);
        final int themeForegroundColor =  outValue.data;

        final int mDefaultBottomBorderColor = ColorChanger.setColorAlpha(themeForegroundColor,
                DEFAULT_BOTTOM_BORDER_COLOR_ALPHA);

        mDefaultTabColorizer = new CTabColorizer();
        mDefaultTabColorizer.setIndicatorColors(DEFAULT_SELECTED_INDICATOR_COLOR);
        mDefaultTabColorizer.setDividerColors(ColorChanger.setColorAlpha(themeForegroundColor,
                DEFAULT_DIVIDER_COLOR_ALPHA));

        mBottomBorderThickness = (int) (DEFAULT_BOTTOM_BORDER_THICKNESS_DIPS * density);
        mBottomBorderPaint = new Paint();
        mBottomBorderPaint.setColor(mDefaultBottomBorderColor);

        mSelectedIndicatorThickness = (int) (SELECTED_INDICATOR_THICKNESS_DIPS * density);
        mSelectedIndicatorPaint = new Paint();

        mDividerHeight = DEFAULT_DIVIDER_HEIGHT;
        mDividerPaint = new Paint();
        mDividerPaint.setStrokeWidth((int) (DEFAULT_DIVIDER_THICKNESS_DIPS * density));
    }

    public void setCustomTabColorizer(TabColorizer customTabColorizer) {
        mCustomTabColorizer = customTabColorizer;
        invalidate();
    }

    public void setSelectedIndicatorColors(int... colors) {
        // Make sure that the custom colorizer is removed
        mCustomTabColorizer = null;
        mDefaultTabColorizer.setIndicatorColors(colors);
        invalidate();
    }

    public void setDividerColors(int... colors) {
        // Make sure that the custom colorizer is removed
        mCustomTabColorizer = null;
        mDefaultTabColorizer.setDividerColors(colors);
        invalidate();
    }

    public void onViewPagerPageChanged(int position, float positionOffset) {
        mSelectedPosition = position;
        mSelectionOffset = positionOffset;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        final int height = getHeight();
        final int childCount = getChildCount();
        final int dividerHeightPx = (int) (Math.min(Math.max(0f, mDividerHeight), 1f) * height);
        final TabColorizer tabColorizer = mCustomTabColorizer != null
                ? mCustomTabColorizer
                : mDefaultTabColorizer;

        // Thick colored underline below the current selection
        if (childCount > 0) {
            View selectedTitle = getChildAt(mSelectedPosition);
            int left = selectedTitle.getLeft();
            int right = selectedTitle.getRight();
            int color = tabColorizer.getIndicatorColor(mSelectedPosition);
            int backgroundColor = tabColorizer.getBackgroundColor(mSelectedPosition);
            int statusBarColor = tabColorizer.getStatusBarColor(mSelectedPosition);

            if (mSelectionOffset > 0f && mSelectedPosition < (getChildCount() - 1)) {
                int nextColor = tabColorizer.getIndicatorColor(mSelectedPosition + 1);
                if (color != nextColor) {
                    color = ColorChanger.blendColors(nextColor, color, mSelectionOffset);
                }
                int nextBackgroundColor  = tabColorizer.getBackgroundColor(mSelectedPosition + 1);
                if (backgroundColor != nextBackgroundColor){
                    backgroundColor = ColorChanger.blendColors(nextBackgroundColor, backgroundColor, mSelectionOffset);
                }
                int nextStatusBarColor  = tabColorizer.getStatusBarColor(mSelectedPosition + 1);
                if (statusBarColor != nextStatusBarColor){
                    statusBarColor = ColorChanger.blendColors(nextStatusBarColor, statusBarColor, mSelectionOffset);
                }

                // Draw the selection partway between the tabs
                View nextTitle = getChildAt(mSelectedPosition + 1);
                left = (int) (mSelectionOffset * nextTitle.getLeft() +
                        (1.0f - mSelectionOffset) * left);
                right = (int) (mSelectionOffset * nextTitle.getRight() +
                        (1.0f - mSelectionOffset) * right);
            }

            mSelectedIndicatorPaint.setColor(color);
            mColorListener.notify(color, backgroundColor, statusBarColor);
            canvas.drawRect(left, height - mSelectedIndicatorThickness, right,
                    height, mSelectedIndicatorPaint);
        }
        changeLinesUnderBottomEdge(canvas, height);
        changeSeparatorsBetweenTitles(canvas, height, dividerHeightPx, childCount, tabColorizer);

    }

    // Thin underline along the entire bottom edge
    private void changeLinesUnderBottomEdge(Canvas canvas, int height){
        canvas.drawRect(0, height - mBottomBorderThickness, getWidth(), height, mBottomBorderPaint);
    }

    // Vertical separators between the titles
    private void changeSeparatorsBetweenTitles(Canvas canvas, int height,
                                               int dividerHeightPx, int childCount,
                                               TabColorizer tabColorizer) {
        int separatorTop = (height - dividerHeightPx) / 2;
        for (int i = 0; i < childCount - 1; i++) {
            View child = getChildAt(i);
            mDividerPaint.setColor(tabColorizer.getDividerColor(i));
            canvas.drawLine(child.getRight(), separatorTop, child.getRight(),
                    separatorTop + dividerHeightPx, mDividerPaint);
        }
    }
}
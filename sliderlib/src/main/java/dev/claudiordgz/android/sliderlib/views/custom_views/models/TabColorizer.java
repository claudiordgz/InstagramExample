package dev.claudiordgz.android.sliderlib.views.custom_views.models;

/**
 * Allows complete control over the colors drawn in the tab layout.
 */
public interface TabColorizer {

    // @return return the color of the indicator used when {@code position} is selected.
    int getIndicatorColor(int position);


     // @return return the color of the divider drawn to the right of {@code position}.
    int getDividerColor(int position);


    int getBackgroundColor(int position);

    int getStatusBarColor(int position);

}
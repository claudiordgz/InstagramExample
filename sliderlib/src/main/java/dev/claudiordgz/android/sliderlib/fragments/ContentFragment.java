package dev.claudiordgz.android.sliderlib.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * Simple Fragment used to display some meaningful content for each page in the sample's
 * {@link android.support.v4.view.ViewPager}.
 */
public class ContentFragment extends Fragment {

    private static final String KEY_TITLE = "title";
    private static final String KEY_INDICATOR_COLOR = "indicator_color";
    private static final String KEY_DIVIDER_COLOR = "divider_color";

    /**
     * @return a new instance of {@link ContentFragment}, adding the parameters into a bundle and
     * setting them as arguments.
     */
    public static ContentFragment newInstance(CharSequence title, int indicatorColor,
                                              int dividerColor) {
        Bundle bundle = new Bundle();
        bundle.putCharSequence(KEY_TITLE, title);
        bundle.putInt(KEY_INDICATOR_COLOR, indicatorColor);
        bundle.putInt(KEY_DIVIDER_COLOR, dividerColor);

        ContentFragment fragment = new ContentFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(dev.claudiordgz.android.sliderlib.R.layout.view_pager_item, container, false);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        final int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources()
                .getDisplayMetrics());
        params.setMargins(margin, margin, margin, margin);
        view.setBackgroundResource(dev.claudiordgz.android.sliderlib.R.drawable.background_card);
        view.setLayoutParams(params);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        if(args == null) {
            throw new NullPointerException();
        }
        TextView title = (TextView) view
                .findViewById(dev.claudiordgz.android.sliderlib.R.id.item_title);
        title.setText("Title: " + args.getCharSequence(KEY_TITLE));

        int indicatorColor = args.getInt(KEY_INDICATOR_COLOR);
        TextView indicatorColorView = (TextView) view
                .findViewById(dev.claudiordgz.android.sliderlib.R.id.item_indicator_color);
        indicatorColorView.setText("Indicator: #" + Integer.toHexString(indicatorColor));
        indicatorColorView.setTextColor(indicatorColor);

        int dividerColor = args.getInt(KEY_DIVIDER_COLOR);
        TextView dividerColorView = (TextView) view
                .findViewById(dev.claudiordgz.android.sliderlib.R.id.item_divider_color);
        dividerColorView.setText("Divider: #" + Integer.toHexString(dividerColor));
        dividerColorView.setTextColor(dividerColor);
    }
}
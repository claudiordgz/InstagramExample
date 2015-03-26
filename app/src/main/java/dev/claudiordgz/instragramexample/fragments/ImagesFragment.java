package dev.claudiordgz.instragramexample.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import dev.claudiordgz.instragramexample.R;
import dev.claudiordgz.instragramexample.adapters.RecyclerViewAdapter;
import dev.claudiordgz.instragramexample.model.ViewHolderModel;
import dev.claudiordgz.instragramexample.network.LoadImages;
import dev.claudiordgz.instragramexample.network.SelfiesRequest;
import dev.claudiordgz.instragramexample.util.OrientationHelper;

/**
 * Created by Claudio on 3/25/2015.
 */
public class ImagesFragment extends Fragment {

    @InjectView(R.id.fullImage) ImageView fullView;
    @InjectView(R.id.recycler_view) RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private RecyclerViewAdapter mAdapter;
    private int previousTotal = 0;
    private boolean loading = true;
    int firstVisibleItem;
    String nextMaxTagId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        final Activity activity = getActivity();
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(fullView.getLayoutParams());
        lp.setMargins(0, OrientationHelper.getScreenOrientationAndHeight(getActivity().getBaseContext()).second/2, 0, 0);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(lp);
        fullView.setLayoutParams(layoutParams);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new RecyclerViewAdapter(activity.getApplicationContext(), fullView);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleThreshold = 12;
                int visibleItemCount = mRecyclerView.getChildCount();
                int totalItemCount = mLayoutManager.getItemCount();
                firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();
                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                } else if (totalItemCount - visibleItemCount <= (firstVisibleItem + visibleThreshold)) {
                    new LoadImages(activity.getApplicationContext(), nextMaxTagId, mAdapter).execute("60",nextMaxTagId);
                    loading = true;
                }
            }
        });
        new LoadImages(activity.getApplicationContext(), nextMaxTagId, mAdapter).execute("60");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, parent, false);
        ButterKnife.inject(this, v);
        return v;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}

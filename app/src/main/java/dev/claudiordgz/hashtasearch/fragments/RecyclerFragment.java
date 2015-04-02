package dev.claudiordgz.hashtasearch.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import dev.claudiordgz.android.sliderlib.common.OrientationHelper;

import java.util.LinkedHashSet;

import butterknife.ButterKnife;
import butterknife.InjectView;
import dev.claudiordgz.common.model.InstagramData;
import dev.claudiordgz.hashtasearch.R;
import dev.claudiordgz.hashtasearch.model.TripletImages;
import dev.claudiordgz.hashtasearch.data_providers.BackgroundImageTask;
import dev.claudiordgz.hashtasearch.model.TripletImagesManager;
import dev.claudiordgz.hashtasearch.view_holders.TripletViewHolder;
import uk.co.ribot.easyadapter.EasyRecyclerAdapter;

/**
 * Created by Claudio on 3/25/2015.
 */
public class RecyclerFragment extends Fragment {

    public static String argHashTag = "hashTag";

    @InjectView(R.id.recycler_view) RecyclerView mRecyclerView;
    private boolean loading = true;
    private int firstVisibleItem;
    private LinkedHashSet<InstagramData> urlCache;
    private TripletImagesManager mImageManager;
    private String nextBatchId = "";
    String hashTag;

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        final Activity activity = getActivity();

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        int width = OrientationHelper.getScreenOrientationAndSize(getActivity().getBaseContext()).second.first;
        String wid = "" + width;
        Toast.makeText(getActivity(), wid, Toast.LENGTH_SHORT).show();
        int recyclerWidth = width / 2;
        if(recyclerWidth > 800){

        }



        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width / 2,
                LinearLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(width / 4,0,width / 4,0);
        mRecyclerView.setLayoutParams(params);
        mRecyclerView.setHasFixedSize(true);

        final EasyRecyclerAdapter<TripletImages> adapter = new EasyRecyclerAdapter<>(
                getActivity(),
                TripletViewHolder.class);

        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = mLayoutManager.getChildCount();
                int totalItemCount = mLayoutManager.getItemCount();
                firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();
                if (loading) {
                    if ((visibleItemCount + firstVisibleItem) >= totalItemCount) {
                        loading = false;
                    }
                } else {
                    new BackgroundImageTask(activity.getApplicationContext(), adapter, urlCache, mImageManager, nextBatchId, hashTag).execute();
                    loading = true;
                }
            }
        });
        new BackgroundImageTask(activity.getApplicationContext(), adapter, urlCache, mImageManager, nextBatchId, hashTag).execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, parent, false);
        ButterKnife.inject(this, v);
        hashTag = this.getArguments().getString(argHashTag);
        urlCache = new LinkedHashSet<>();
        mImageManager = new TripletImagesManager();
        return v;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}

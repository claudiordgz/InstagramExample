package dev.claudiordgz.instragramexample.network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;

import java.util.ArrayList;

import dev.claudiordgz.instragramexample.adapters.RecyclerViewAdapter;
import dev.claudiordgz.instragramexample.model.ViewHolderModel;

/**
 * Created by Claudio on 3/26/2015.
 */
public class LoadImages extends AsyncTask<String, Void, Pair<String, ArrayList<String>>> {
    Context context;
    String nextMaxTagId;
    RecyclerViewAdapter mAdapter;

    public LoadImages(Context context, String nextMaxTagId, RecyclerViewAdapter mAdapter) {
        this.context = context;
        this.nextMaxTagId = nextMaxTagId;
        this.mAdapter = mAdapter;
    }

    @Override
    protected Pair<String, ArrayList<String>> doInBackground(String... params) {
        SelfiesRequest request = new SelfiesRequest(context);
        Pair<String, ArrayList<String>> results;
        if(params.length >= 2) {
            results = request.GetImages(null, params[1]);
        } else {
            results = request.GetImages(null, null);
        }
        return results;
    }

    @Override
    protected void onPostExecute(Pair<String, ArrayList<String>> result) {
        nextMaxTagId = result.first;
        int idx = 0;
        for(int i = 0; i <= result.second.size()-3; i+=3) {
            mAdapter.addItem(idx, new ViewHolderModel(result.second.get(i), result.second.get(i + 1), result.second.get(i + 2)));
        }
    }

    @Override
    protected void onPreExecute() {}

    @Override
    protected void onProgressUpdate(Void... values) {}
}
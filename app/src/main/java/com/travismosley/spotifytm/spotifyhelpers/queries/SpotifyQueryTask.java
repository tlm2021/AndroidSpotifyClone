package com.travismosley.spotifytm.spotifyhelpers.queries;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Pager;

/**
 * An AsyncTask class for Spotify Queries. Handles generating tasks and setting options.
 */
public abstract class SpotifyQueryTask<Param, Result> extends AsyncTask<Param, Void, Pager<Result>> {

    public final String LOG_TAG = SpotifyQueryTask.class.getSimpleName();

    public SpotifyQueryOptions mOptions = new SpotifyQueryOptions();
    public ArrayAdapter<Result> mResultsAdapter;
    public SpotifyService mSpotify = new SpotifyApi().getService();

    public int maxItems = -1;

    @Override
    protected void onPostExecute(Pager<Result> results) {
        Log.i(LOG_TAG, "Processing query results");
        Log.i(LOG_TAG, "Query Offset: " + results.offset);
        Log.i(LOG_TAG, "Max items:" + results.total);
        Log.i(LOG_TAG, "Limit: " + results.limit);

        maxItems = results.total;

        if (results.items == null) {
            Log.i(LOG_TAG, "No items returned.");
            return;
        }
        Log.i(LOG_TAG, "Items returned: " + results.items.size());
        for (int i = 0; i < results.items.size(); i++) {
            Result result = results.items.get(i);
            Log.i(LOG_TAG, "Adding item: " + result);
            mResultsAdapter.add(result);
        }
    }

    @Override
    protected Pager<Result> doInBackground(Param... params) {
        Log.i(LOG_TAG, "Executing query for Params: " + params.toString());
        if (maxItems != -1 && mOptions.offset() >= maxItems) {
            return new Pager<>();
        } else {
            return query(params);
        }
    }

    // TODO: Clean up possible heap pollution warning
    protected abstract Pager<Result> query(Param... params);
}

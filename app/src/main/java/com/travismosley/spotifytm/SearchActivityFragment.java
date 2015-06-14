package com.travismosley.spotifytm;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.Pager;



public class SearchActivityFragment extends Fragment {

    private final String LOG_TAG = SearchActivityFragment.class.getSimpleName();
    private ArtistSearchResultAdapter mSearchResultsAdapter;

    public SearchActivityFragment() {
    }

    private class ArtistSearchResultAdapter extends ArrayAdapter<Artist>{

        private final Context context;
        private ArrayList<Artist> artists;

        public ArtistSearchResultAdapter(Context context, ArrayList<Artist> artists){
            super(context, -1, artists);
            this.context = context;
            this.artists = artists;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View resultView = inflater.inflate(R.layout.list_item_search_result, parent, false);
            TextView nameView = (TextView) resultView.findViewById(R.id.list_item_search_result_textview);
            nameView.setText(artists.get(position).name);

            ImageView imageView = (ImageView) resultView.findViewById(R.id.list_item_search_result_imageview);
            List<Image> artistImages = artists.get(position).images;
            if (artistImages.size() > 0){
                Picasso.with(context).load(artistImages.get(artistImages.size()-1).url).into(imageView);
            }
            return resultView;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mSearchResultsAdapter =
                new ArtistSearchResultAdapter(
                        // The current context (this fragment's parent activity)
                        getActivity(),
                        new ArrayList<Artist>()
                );


        View searchView = inflater.inflate(R.layout.fragment_main, container, false);
        ListView searchResultView = (ListView)searchView.findViewById(R.id.listview_search_results);
        searchResultView.setAdapter(mSearchResultsAdapter);

        // Add the search listener
        EditText search=(EditText)searchView.findViewById(R.id.text_search);
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchForArtist(String.valueOf(v.getText()));
                    handled = true;
                }
                return handled;
            }
        });
        Log.v(LOG_TAG, "Added search listener.");

        Log.v(LOG_TAG, "Ran onCreateView");
        return searchView;
    }

    private void searchForArtist(String artist){
        Log.v(LOG_TAG, "Executing search for '" + artist + "'.");

        mSearchResultsAdapter.clear();
        FetchSearchResultsTask searchTask = new FetchSearchResultsTask();
        searchTask.execute(artist);
    }

    public class FetchSearchResultsTask extends AsyncTask<String, Void, Pager<Artist>> {

        @Override
        protected void onPostExecute(Pager<Artist> artists) {
            int numResults = Math.min(artists.total, 10);
            for (int i=0; i < numResults; i++){
                Artist artist = artists.items.get(i);
                mSearchResultsAdapter.add(artist);
            }
        }

        @Override
        protected Pager<Artist> doInBackground(String... query){

            if (query.length == 0){
                return null;
            }

            SpotifyApi api = new SpotifyApi();
            SpotifyService spotify = api.getService();
            ArtistsPager results = spotify.searchArtists(query[0]);
            return results.artists;
        }
    }
}

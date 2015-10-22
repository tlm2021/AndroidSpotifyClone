package com.travismosley.spotifytm;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;


/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistDetailFragment extends Fragment {

    Artist mArtist;

    public ArtistDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_artist_detail, container, false);

        // Get the artist object
        SpotifyApi api = new SpotifyApi();
        SpotifyService spotify = api.getService();
        mArtist = getActivity().getIntent().getParcelableExtra(getString(R.string.INTENT_EXTRA_ARTIST));

        // Populate the artist details
        TextView artistView = (TextView) rootView.findViewById(R.id.textView_artist_name);
        artistView.setText(mArtist.name);

        TextView followerView = (TextView) rootView.findViewById(R.id.textView_artist_follower_count);
        followerView.setText(String.format("%d Followers", mArtist.followers.total));

        TextView genreView = (TextView) rootView.findViewById(R.id.textView_artist_genre);
        genreView.setText(mArtist.genres.get(0));

        return rootView;
    }
}

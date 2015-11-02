package com.travismosley.spotifytm;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.travismosley.spotifytm.spotifyhelpers.queries.AlbumsForArtistTask;
import com.travismosley.spotifytm.spotifyhelpers.queries.SpotifyTaskScrollListener;
import com.travismosley.spotifytm.spotifyhelpers.queries.TaskFactoryInterface;

import java.util.ArrayList;
import java.util.List;

import kaaes.spotify.webapi.android.models.Album;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Image;


/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistDetailFragment extends Fragment {

    public final String LOG_TAG = ArtistDetailFragment.class.getSimpleName();
    Artist mArtist;
    private AlbumSearchResultsAdapter mAlbumResultsAdapter;

    public ArtistDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mAlbumResultsAdapter = new AlbumSearchResultsAdapter(
                getActivity(),
                new ArrayList<Album>()
        );

        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_artist_detail, container, false);

        // Get the artist object
        mArtist = getActivity().getIntent().getParcelableExtra(getString(R.string.INTENT_EXTRA_ARTIST));

        // Populate the artist details
        TextView artistView = (TextView) rootView.findViewById(R.id.textView_artist_name);
        artistView.setText(mArtist.name);

        TextView followerView = (TextView) rootView.findViewById(R.id.textView_artist_follower_count);
        followerView.setText(String.format("%d Followers", mArtist.followers.total));

        TextView genreView = (TextView) rootView.findViewById(R.id.textView_artist_genre);
        if (mArtist.genres.size() > 0){
            genreView.setText(mArtist.genres.get(0));
        }

        if (mArtist.images.size() > 0){
            ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView_artist_detail);
            Picasso.with(getActivity()).load(mArtist.images.get(0).url).into(imageView);
        }

        ListView albumsView = (ListView) rootView.findViewById(R.id.listView_albums);
        albumsView.setAdapter(mAlbumResultsAdapter);

        TaskFactoryInterface taskFactory = AlbumsForArtistTask.factory();
        Log.v(LOG_TAG, "Setting Album Adapter" + mAlbumResultsAdapter);
        AlbumScrollListener scrollListener = new AlbumScrollListener(mAlbumResultsAdapter, taskFactory);

        scrollListener.getTask().execute(mArtist);

        albumsView.setOnScrollListener(scrollListener);
        return rootView;
    }

    private class AlbumSearchResultsAdapter extends ArrayAdapter<Album> {

        private final Context context;
        private ArrayList<Album> albums;

        public AlbumSearchResultsAdapter(Context context, ArrayList<Album> albums) {
            super(context, -1, albums);
            this.context = context;
            this.albums = albums;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View resultView = inflater.inflate(R.layout.list_item_album, parent, false);

            // Set the album name
            TextView nameView = (TextView) resultView.findViewById(R.id.list_item_album_name_textview);
            nameView.setText(albums.get(position).name);

            // Set the artist name
            TextView artistView = (TextView) resultView.findViewById(R.id.list_item_album_artist_textview);
            artistView.setText(mArtist.name);

            // Set the album image
            ImageView imageView = (ImageView) resultView.findViewById(R.id.list_item_album_imageview);
            List<Image> albumImages = albums.get(position).images;
            if (albumImages.size() > 0) {
                Picasso.with(context).load(albumImages.get(albumImages.size() - 1).url).into(imageView);
            }
            return resultView;
        }
    }

    private class AlbumScrollListener extends SpotifyTaskScrollListener<Album, AlbumsForArtistTask> {

        public <Adapter extends ArrayAdapter<Album>,
                Factory extends TaskFactoryInterface<AlbumsForArtistTask>>
        AlbumScrollListener(Adapter resultsAdapter, Factory taskFactory) {
            super(resultsAdapter, taskFactory);
        }

        public boolean onLoadMore(int firstIdx) {
            AlbumsForArtistTask task = getTask(firstIdx - 1);
            task.execute(mArtist);
            return true;
        }
    }
}

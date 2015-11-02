package com.travismosley.spotifytm.spotifyhelpers.queries;

import android.util.Log;

import kaaes.spotify.webapi.android.models.Album;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Pager;

/**
 * A SpotifyQueryTask for returning albums for a given Artist object.
 */
public class AlbumsForArtistTask extends SpotifyQueryTask<Artist, Album> {

    public final String LOG_TAG = AlbumsForArtistTask.class.getSimpleName();

    public static AlbumsForArtistTaskFactory factory() {
        return new AlbumsForArtistTaskFactory();
    }

    protected Pager<Album> query(Artist... artist) {
        Log.i(LOG_TAG, "Executing query for artist: " + artist[0].href);
        Pager<Album> results = mSpotify.getArtistAlbums(artist[0].id, mOptions);
        return results;
    }

    public static class AlbumsForArtistTaskFactory implements TaskFactoryInterface<AlbumsForArtistTask> {
        public AlbumsForArtistTask get() {
            return new AlbumsForArtistTask();
        }
    }
}
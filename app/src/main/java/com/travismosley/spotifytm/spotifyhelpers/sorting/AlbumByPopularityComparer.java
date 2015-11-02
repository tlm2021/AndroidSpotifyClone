package com.travismosley.spotifytm.spotifyhelpers.sorting;

import java.util.Comparator;

import kaaes.spotify.webapi.android.models.Album;

/**
 * A simple Comparator for Spotify Albums to sort by popularity
 */
public class AlbumByPopularityComparer implements Comparator<Album> {

    @Override
    public int compare(Album albumA, Album albumB) {
        return compare(albumA.popularity, albumB.popularity);
    }

    private int compare(int popularityA, int popularityB) {
        return popularityA < popularityB ? -1
                : popularityA > popularityB ? 1
                : 0;
    }
}
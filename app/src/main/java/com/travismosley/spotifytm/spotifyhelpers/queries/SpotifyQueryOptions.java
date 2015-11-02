package com.travismosley.spotifytm.spotifyhelpers.queries;

import java.util.HashMap;

import kaaes.spotify.webapi.android.SpotifyService;

/**
 * An options class for setting, fetching, and passing around Spotify query options.
 * Can be passed to most SpotifyService methods.
 */
public class SpotifyQueryOptions extends HashMap {

    private int mDefaultLimit = 20;
    private int mDefaultOffset = 0;

    public SpotifyQueryOptions() {
        setLimit(mDefaultLimit);
        setOffset(mDefaultOffset);
    }

    public SpotifyQueryOptions(int offset) {
        setLimit(mDefaultLimit);
        setOffset(offset);
    }

    public SpotifyQueryOptions(int offset, int limit) {
        setLimit(limit);
        setOffset(offset);
    }

    public int limit() {
        return (int) get(SpotifyService.LIMIT);
    }

    public void setLimit(int limit) {
        put(SpotifyService.LIMIT, limit);
    }

    public int offset() {
        return (int) get(SpotifyService.OFFSET);
    }

    public void setOffset(int offset) {
        put(SpotifyService.OFFSET, offset);
    }
}
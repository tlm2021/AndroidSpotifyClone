package com.travismosley.spotifytm.spotifyhelpers.queries;

/**
 * A generic interface for returing class instances
 */
public interface TaskFactoryInterface<T> {
    T get();
}

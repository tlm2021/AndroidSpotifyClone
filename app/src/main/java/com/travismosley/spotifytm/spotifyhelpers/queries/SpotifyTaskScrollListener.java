package com.travismosley.spotifytm.spotifyhelpers.queries;

import android.util.Log;
import android.widget.ArrayAdapter;

import com.travismosley.spotifytm.InfiniteScrollListener;

/**
 * An OnScrollListener that integrates with SpotifyQueryTask objects to combine the event handling
 * with infinite scrolling. Generic, to it can accept any SpotifyQueryTask and expected return type
 */
public abstract class SpotifyTaskScrollListener<Result, Task extends SpotifyQueryTask> extends InfiniteScrollListener {

    public final String LOG_TAG = SpotifyTaskScrollListener.class.getSimpleName();

    private ArrayAdapter<Result> mResultsAdapter;
    private TaskFactoryInterface<Task> mTaskFactory;

    public <Adapter extends ArrayAdapter<Result>,
            Factory extends TaskFactoryInterface<Task>> SpotifyTaskScrollListener(Adapter resultsAdapter,
                                                                                  Factory taskFactory) {
        mResultsAdapter = resultsAdapter;
        mTaskFactory = taskFactory;
    }

    public Task getTask() {
        Task task = mTaskFactory.get();

        Log.v(LOG_TAG, "Using Adapater " + mResultsAdapter);
        task.mResultsAdapter = mResultsAdapter;
        task.mOptions.setLimit(mQueryLimit);
        return task;
    }

    public Task getTask(int offset) {
        Task task = getTask();
        task.mOptions.setOffset(offset);
        return task;
    }
}
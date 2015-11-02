package com.travismosley.spotifytm;

import android.widget.AbsListView;

/**
 * An OnScrollListener for supporting InfiniteScroll
 */
public abstract class InfiniteScrollListener implements AbsListView.OnScrollListener {

    public final String LOG_TAG = InfiniteScrollListener.class.getSimpleName();

    public int mQueryLimit = 20;
    public int minBufferItems = 5;

    private int previousTotalItems = 0;
    private boolean loading = true;

    public InfiniteScrollListener() {
    }

    @Override
    public void onScroll(AbsListView view, int firstItem, int visibleItemCount, int totalItems) {

        // Detect and handle a list that has been invalidated.
        if (totalItems < previousTotalItems) {
            this.previousTotalItems = totalItems;
            if (totalItems == 0) {
                this.loading = true;
            }
        }

        // Detect and handle a completed page load.
        if (loading && (totalItems > previousTotalItems)) {
            loading = false;
            previousTotalItems = totalItems;
        }

        if (!loading && (totalItems - visibleItemCount) <= (firstItem + minBufferItems)) {
            loading = onLoadMore(previousTotalItems + 1);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    public abstract boolean onLoadMore(int firstItemIdx);

}

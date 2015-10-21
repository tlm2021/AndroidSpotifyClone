package com.travismosley.spotifytm;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class ArtistDetailActivity extends Activity {

    public final String LOG_TAG = ArtistDetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_detail);

        Log.v(LOG_TAG, "Ran onCreate");
    }
}

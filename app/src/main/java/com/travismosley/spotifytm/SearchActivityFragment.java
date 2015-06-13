package com.travismosley.spotifytm;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A placeholder fragment containing a simple view.
 */
public class SearchActivityFragment extends Fragment {

    private final String LOG_TAG = SearchActivityFragment.class.getSimpleName();

    public SearchActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        EditText search=(EditText)view.findViewById(R.id.text_search);
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchForArtist(v.getText());
                    handled = true;
                }
                return handled;
            }
        });
        Log.v(LOG_TAG, "Added search listener.");
        
        Log.v(LOG_TAG, "Ran onCreateView");
        return view;
    }

    private void searchForArtist(CharSequence artist){
        Toast toast = Toast.makeText(getActivity(), artist, Toast.LENGTH_SHORT);
        toast.show();
    }

}

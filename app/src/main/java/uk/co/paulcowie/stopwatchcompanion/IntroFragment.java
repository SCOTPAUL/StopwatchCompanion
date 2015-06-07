package uk.co.paulcowie.stopwatchcompanion;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.getpebble.android.kit.PebbleKit;


/**
 * Fragment which provides minimal information on the Pebble's status
 */
public class IntroFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FrameLayout layout = (FrameLayout) inflater.inflate(R.layout.fragment_intro, container, false);
        TextView introText = (TextView)layout.findViewById(R.id.intro_text_view);

        boolean connected = PebbleKit.isWatchConnected(getActivity().getApplicationContext());

        if(connected){
            introText.setText(R.string.pebble_connected);
        }
        else{
            introText.setText(R.string.pebble_not_connected);
        }

        return layout;

    }
}

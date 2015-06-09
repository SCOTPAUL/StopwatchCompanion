package uk.co.paulcowie.stopwatchcompanion;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    private final String LOG_TAG = this.getClass().getSimpleName();

    private void setupPebbleConnectionListeners(final TextView introText){
        PebbleKit.registerPebbleConnectedReceiver(getActivity().getApplicationContext(), new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                Log.i(LOG_TAG, "Pebble connected!");
                introText.setText(R.string.pebble_connected);
            }

        });

        PebbleKit.registerPebbleDisconnectedReceiver(getActivity().getApplicationContext(), new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                Log.i(LOG_TAG, "Pebble disconnected!");
                introText.setText(R.string.pebble_not_connected);
            }

        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FrameLayout layout = (FrameLayout) inflater.inflate(R.layout.fragment_intro, container, false);
        TextView introText = (TextView)layout.findViewById(R.id.intro_text_view);
        setupPebbleConnectionListeners(introText);

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

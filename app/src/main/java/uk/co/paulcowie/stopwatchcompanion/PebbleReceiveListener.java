package uk.co.paulcowie.stopwatchcompanion;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.getpebble.android.kit.Constants;
import com.getpebble.android.kit.PebbleKit;
import com.getpebble.android.kit.util.PebbleDictionary;

import org.json.JSONException;

import java.util.UUID;

import uk.co.paulcowie.stopwatchcompanion.parsers.StopwatchParser;

/**
 * Created by paul on 07/06/15.
 *
 * Listens for {@code INTENT_APP_RECEIVE} from Stopwatch watchapp, and sends an intent containing a
 * formatted String of the recorded time in mm:ss:cc
 */
public class PebbleReceiveListener extends BroadcastReceiver {
    private final String LOG_TAG = this.getClass().getSimpleName();

    /**
     * Send a timestamp intent
     * @param context The context of the calling method
     * @param data The {@code PebbleDictionary} containing the time in milliseconds
     */
    private void sendTimeIntentFromData(Context context, PebbleDictionary data){
        long time_ms = data.getUnsignedIntegerAsLong(AppConstants.KEY_TIME);
        String timestamp = new StopwatchParser(time_ms).parse_ms();

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, timestamp);
        sendIntent.setType("text/plain");
        sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK       |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK     |
                            Intent.FLAG_FROM_BACKGROUND
        );

        Log.v(LOG_TAG, "Sending timestamp intent: " + timestamp);
        context.startActivity(sendIntent);

    }

    /**
     * Handle Pebble APP_RECEIVE intents from the Stopwatch watchapp
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Constants.INTENT_APP_RECEIVE)){
            final UUID receivedUuid = (UUID) intent.getSerializableExtra(Constants.APP_UUID);

            if(!AppConstants.PEBBLE_APP_UUID.equals(receivedUuid)){
                return;
            }

            final int transactionId = intent.getIntExtra(Constants.TRANSACTION_ID, -1);
            final String jsonData = intent.getStringExtra(Constants.MSG_DATA);
            if (jsonData == null || jsonData.isEmpty()) {
                Log.i(LOG_TAG, "jsonData null");
                return;
            }

            try {
                final PebbleDictionary data = PebbleDictionary.fromJson(jsonData);
                if(data.contains(AppConstants.KEY_TIME)) {
                    sendTimeIntentFromData(context, data);
                }

                PebbleKit.sendAckToPebble(context, transactionId);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage());
            }
        }
    }
}


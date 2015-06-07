package uk.co.paulcowie.stopwatchcompanion;

import android.app.Activity;
import android.os.Bundle;

public class IntroActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new IntroFragment())
                    .commit();
        }
    }
}

package com.apps.ayushvyas.applock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;

public class PasswordActivity extends AppCompatActivity {

    private static String TAG = "PasswordActivity.class";
    private String savedPin;

    PinLockView mPinLockView;
    private IndicatorDots mIndicatorDots;
    private SharedPreferences preferences = null;
    private boolean inApp = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "insidencreate");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_password);
        inApp = getIntent().getBooleanExtra("App", false);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        savedPin = preferences.getString("Pin", null);
        mPinLockView = (PinLockView) findViewById(R.id.pin_lock_view);
        mIndicatorDots = (IndicatorDots) findViewById(R.id.indicator_dots);
        mPinLockView.attachIndicatorDots(mIndicatorDots);
        mPinLockView.setPinLockListener(mPinLockListener);
        mPinLockView.setPinLength(4);
        mPinLockView.setTextColor(ContextCompat.getColor(this, R.color.white));
        mIndicatorDots.setIndicatorType(IndicatorDots.IndicatorType.FILL_WITH_ANIMATION);
    }

    private PinLockListener mPinLockListener = new PinLockListener() {
        @Override
        public void onComplete(String pin) {
            Log.d(TAG, "Pin complete: " + pin);
            if(savedPin== null) {
                savePin(pin);
            }
            else{

                //User inputs correct pin
                if (pin.equals(savedPin)) {
                    if(inApp)
                   showHomePage();
                    else finish();
                } else {
                    Toast.makeText(PasswordActivity.this, "Incorrect code, try again!", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onEmpty() {
            Log.d(TAG, "Pin empty");
        }

        @Override
        public void onPinChange(int pinLength, String intermediatePin) {
            Log.d(TAG, "Pin changed, new length " + pinLength + " with intermediate pin " + intermediatePin);
        }
    };

    private void savePin(String pin) {

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Pin",pin);
        editor.apply();
        showHomePage();
    }

    private void showHomePage() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

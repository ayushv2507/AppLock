package com.apps.ayushvyas.applock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {
    SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        prefs = getSharedPreferences("com.apps.ayushvyas.applock", MODE_PRIVATE);
        if (prefs.getBoolean("firstrun", true)) {
            
            showHomepage();
            prefs.edit().putBoolean("firstrun", false).commit();
        }
        else{
            showPasswordScreen();
        }
    }

    private void showPasswordScreen() {
        Intent password = new Intent(this, PasswordActivity.class);
        password.putExtra("App", true);
        startActivity(password);
        finish();
    }

    private void showHomepage() {
        Intent homePage = new Intent(this, MainActivity.class);
        startActivity(homePage);
        finish();
    }
}

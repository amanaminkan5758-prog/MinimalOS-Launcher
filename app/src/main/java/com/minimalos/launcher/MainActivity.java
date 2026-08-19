package com.minimalos.launcher;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MinimalOS";

    private final Handler clockHandler = new Handler();
    private final Runnable clockRunnable = new Runnable() {
        @Override
        public void run() {
            updateClock();
            clockHandler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "MainActivity created");
        setContentView(R.layout.activity_main);

        // ===== 1. Get references to buttons =====
        Button btnDialer = findViewById(R.id.btn_dialer);
        Button btnWhatsapp = findViewById(R.id.btn_whatsapp);
        Button btnBrowser = findViewById(R.id.btn_browser);

        Log.d(TAG, "Dialer button: " + (btnDialer != null ? "Found" : "Not found"));

        // ===== 2. Set click listeners =====
        btnDialer.setOnClickListener(view -> {
            animateButtonPress(view);
            openDialer();
        });
        btnWhatsapp.setOnClickListener(view -> {
            animateButtonPress(view);
            openWhatsApp();
        });
        btnBrowser.setOnClickListener(view -> {
            animateButtonPress(view);
            openBrowser();
        });

        // ===== 3. Start live clock in status bar =====
        updateClock();
        clockHandler.postDelayed(clockRunnable, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clockHandler.removeCallbacks(clockRunnable);
    }

    private void updateClock() {
        TextView timeView = findViewById(R.id.time);
        if (timeView != null) {
            String now = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
            timeView.setText(now);
        }
    }

    // ===== 4. Method: Open Dialer =====
    private void openDialer() {
        Log.d(TAG, "Opening dialer...");
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            startActivity(intent);
            Log.d(TAG, "Dialer opened successfully");
        } catch (Exception e) {
            Log.e(TAG, "Error opening dialer", e);
            Toast.makeText(this, "Error opening dialer", Toast.LENGTH_SHORT).show();
        }
    }

    // ===== 5. Method: Open WhatsApp =====
    private void openWhatsApp() {
        Log.d(TAG, "Opening WhatsApp...");
        try {
            Intent intent = getPackageManager().getLaunchIntentForPackage("com.whatsapp");

            if (intent != null) {
                startActivity(intent);
                Log.d(TAG, "WhatsApp opened successfully");
            } else {
                Toast.makeText(this, "WhatsApp not installed", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error opening WhatsApp", e);
            Toast.makeText(this, "Error opening WhatsApp", Toast.LENGTH_SHORT).show();
        }
    }

    // ===== 6. Method: Open Browser =====
    private void openBrowser() {
        Log.d(TAG, "Opening browser...");
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.google.com"));
            startActivity(intent);
            Log.d(TAG, "Browser opened successfully");
        } catch (Exception e) {
            Log.e(TAG, "Error opening browser", e);
            Toast.makeText(this, "Error opening browser", Toast.LENGTH_SHORT).show();
        }
    }

    // ===== 7. Button press animation =====
    private void animateButtonPress(View view) {
        AnimationSet animSet = new AnimationSet(true);

        ScaleAnimation scaleDown = new ScaleAnimation(1f, 0.9f, 1f, 0.9f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        scaleDown.setDuration(100);

        animSet.addAnimation(scaleDown);
        view.startAnimation(animSet);
    }
}

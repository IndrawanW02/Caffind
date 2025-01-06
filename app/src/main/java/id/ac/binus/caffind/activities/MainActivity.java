package id.ac.binus.caffind.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import id.ac.binus.caffind.R;

public class MainActivity extends AppCompatActivity {
    Button registerButton, guestButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Check if this is the first time the app is opened
        SharedPreferences preferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        boolean isFirstRun = preferences.getBoolean("isFirstRun", true);

        if (!isFirstRun) {
            // If not the first time, go straight to FragmentHub
            Intent intent = new Intent(this, FragmentHub.class);
            startActivity(intent);
            finish();
            return;
        }

        // Initialize views and listener
        registerButton = findViewById(R.id.btnRegister);
        guestButton = findViewById(R.id.btnGuest);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });

        guestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Saves the application status which the application has been run before
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("isFirstRun", false);
                editor.apply();

                Intent intent = new Intent(MainActivity.this, FragmentHub.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
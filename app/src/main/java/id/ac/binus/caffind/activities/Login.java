package id.ac.binus.caffind.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;

import id.ac.binus.caffind.R;
import id.ac.binus.caffind.models.UserModel;
import id.ac.binus.caffind.utils.DatabaseHelper;

public class Login extends AppCompatActivity {

    private EditText edtEmail, edtPassword;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views and listeners
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        Button loginBtn = findViewById(R.id.loginBtn);
        TextView registerHyperlink = findViewById(R.id.loginHyperlink);

        db = new DatabaseHelper(this);

        // Login button click listener
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        // Register hyperlink click listener
        registerHyperlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void saveUserToPreferences(UserModel user) {
        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(user); // Convert UserModel to JSON

        editor.putString("user", json); // Save user.JSON to SharedPreferences
        editor.apply();
    }

    private void loginUser() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString();

        if (validateInput(email, password)) {
            UserModel currUser = db.authenticateUser(email, password);
            if (currUser != null) {
                saveUserToPreferences(currUser);
                // Saves the application status which the application has been run before
                SharedPreferences preferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("isFirstRun", false);
                editor.apply();

                // Navigate to the main/home activity
                Intent intent = new Intent(Login.this, FragmentHub.class);

                // Clears all previously existing activities in the stack
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Invalid email or password!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean validateInput(String email, String password) {
        String validationResult = validateEmail(email) + validatePassword(password);

        return !validationResult.contains("REJECTED");
    }

    private String validateEmail(String email){
        String inputValidation = "ACCEPTED";

        if(TextUtils.isEmpty(email)){
            edtEmail.setError("Email cannot be empty");
            edtEmail.requestFocus();
            inputValidation = "REJECTED";
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtEmail.setError("Invalid email address");
            edtEmail.requestFocus();
            inputValidation = "REJECTED";
        }
        return inputValidation;
    }

    private String validatePassword(String password){
        String inputValidation = "ACCEPTED";

        if (TextUtils.isEmpty(password)) {
            edtPassword.setError("Password cannot be empty");
            edtPassword.requestFocus();
            inputValidation = "REJECTED";
        }
        return inputValidation;
    }
}
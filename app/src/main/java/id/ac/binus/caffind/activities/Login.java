package id.ac.binus.caffind.activities;

import android.content.Intent;
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

import id.ac.binus.caffind.R;
import id.ac.binus.caffind.utils.DatabaseHelper;

public class Login extends AppCompatActivity {

    private EditText edtEmail, edtPassword;
    private Button loginBtn;
    private TextView registerHyperlink;
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

        // Initialize views
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        loginBtn = findViewById(R.id.registerBtn);
        registerHyperlink = findViewById(R.id.loginHyperlink);

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
            }
        });
    }

    private void loginUser() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString();

        if (validateInput(email, password)) {
            if (db.authenticateUser(email, password)) {
                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();

                // Navigate to the main/home activity
                Intent intent = new Intent(Login.this, CoffeeShopDetail.class); // Replace with your main activity
                startActivity(intent);
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